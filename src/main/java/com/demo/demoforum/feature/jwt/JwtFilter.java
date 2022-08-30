package com.demo.demoforum.feature.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    private final TokenProvider tokenProvider;

    // 실제 필터링 로직은 doFilterInternal 에 들어감
    // JWT 토큰의 인증 정보를 현재 쓰레드의 SecurityContext 에 저장하는 역할 수행
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        String[] whiteList = {
//                "/",
                "/members/signin",
                "/members/signup",
                "/questions/list"
        };
        String servletPath = request.getServletPath();
        boolean isWhite = false;
        for (String s : whiteList) {
            if (servletPath.startsWith(s)) {
                isWhite = true;
                break;
            }
        }
        if (isWhite) {
            filterChain.doFilter(request, response);
        } else {
            String token = resolveToken(request);

            log.debug("token  = {}", token);
            if (StringUtils.hasText(token)) {
                int flag = tokenProvider.validateToken(token);

                log.debug("flag = {}", flag);
                // 토큰 유효함
                if (flag == 0) {
                    this.setAuthentication(token);
                } else if (flag == 1) { // 토큰 만료
                    response.setContentType("application/json");
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.setCharacterEncoding("UTF-8");
                    PrintWriter out = response.getWriter();
                    log.debug("doFilterInternal Exception CALL!");
                    out.println("{\"error\": \"ACCESS_TOKEN_EXPIRED\", \"message\" : \"엑세스토큰이 만료되었습니다.\"}");
                } else { //잘못된 토큰
                    response.setContentType("application/json");
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.setCharacterEncoding("UTF-8");
                    PrintWriter out = response.getWriter();
                    log.debug("doFilterInternal Exception CALL!");
                    out.println("{\"error\": \"BAD_TOKEN\", \"message\" : \"잘못된 토큰 값입니다.\"}");
                }
            } else {
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setCharacterEncoding("UTF-8");
                PrintWriter out = response.getWriter();
                out.println("{\"error\": \"EMPTY_TOKEN\", \"message\" : \"토큰 값이 비어있습니다.\"}");
            }
        }
    }

    /**
     * @param token 토큰이 유효한 경우 SecurityContext에 저장
     */
    private void setAuthentication(String token) {
        Authentication authentication = tokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    // Request Header 에서 토큰 정보를 꺼내오기
//    FIXME mvc에서 broser에 cookie는 있는데 Authorization header를 못만들겠음
//    정확히는 client에서 form 요청시 넣어줘야 하는데 그냥 server에서 처리해보자
    private String resolveToken(HttpServletRequest request) {
        String accessToken = null;
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("accessToken")) {
                accessToken = cookie.getValue();
                break;
            }
        }
        return accessToken;
//        // bearer : 123123123123123 -> return 123123123123123123
//        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
//        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
//            return bearerToken.substring(7);
//        }
//        return null;
    }

}
