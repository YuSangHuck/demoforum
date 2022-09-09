package com.demo.demoforum.global.security.jwt.filter;

import com.demo.demoforum.global.security.jwt.util.TokenProvider;
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
        String token = fakeResolveToken(request);
        if (token == null) {
            log.debug("no token");
            filterChain.doFilter(request, response);
        } else {
            log.debug("token: '{}'", token);

//            FIXME validateToken 에러처리 int 말고 Exception으로
            int result = tokenProvider.validateToken(token);
            if (result == 0) {
                log.debug("valid token");
                this.setAuthentication(token);
                filterChain.doFilter(request, response);
            } else if (result == 1) {
                log.debug("expired token");
//                TODO reissue token
            } else if (result == 2) {
                log.debug("invalid token");
//                TODO 거부해야되나?
//                servlet filter에서 에러처리 best practice 보고 할것
            }
        }
    }

    /**
     * @param token 토큰이 유효한 경우 SecurityContext에 저장
     */
    private void setAuthentication(String token) {
        Authentication authentication = tokenProvider.getAuthentication(token);
//        FIXME prevent race condition in multi-thread
//        SecurityContext context = SecurityContextHolder.createEmptyContext();
//        context.setAuthentication(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    // Request Header 에서 토큰 정보를 꺼내오기
//    FIXME mvc에서 broser에 cookie는 있는데 Authorization header를 못만들겠음
//    정확히는 client에서 form 요청시 넣어줘야 하는데 그냥 server에서 처리해보자
    private String fakeResolveToken(HttpServletRequest request) {
        String accessToken = null;
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("accessToken")) {
                accessToken = cookie.getValue();
                break;
            }
        }
        return accessToken;
    }

    private String resolveToken(HttpServletRequest request) {
        // bearer : 123123123123123 -> return 123123123123123123
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
