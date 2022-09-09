package com.demo.demoforum.domain.jwt;


import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

// FIXME LoginUrlAuthenticationEntryPoint 참조
@Slf4j
public class JwtAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {
    public JwtAuthenticationEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
    }
    //    @Override
//    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
//        // 유효한 자격증명을 제공하지 않고 접근하려 할때 401
//        response.setContentType("application/json");
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        response.setCharacterEncoding("UTF-8");
//        PrintWriter out = response.getWriter();
//        out.println("{\"error\": \"NO_AUTHORIZATION\", \"message\" : \"인증정보가 없습니다.\"}");
//    }
}
