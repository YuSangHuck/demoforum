package com.demo.demoforum.config;

import com.demo.demoforum.feature.jwt.JwtAccessDeniedHandler;
import com.demo.demoforum.feature.jwt.JwtAuthenticationEntryPoint;
import com.demo.demoforum.feature.jwt.JwtFilter;
import com.demo.demoforum.feature.jwt.TokenProvider;
import com.demo.demoforum.feature.member.MemberSecurityService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.savedrequest.CookieRequestCache;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    private final MemberSecurityService memberSecurityService;
    private final TokenProvider tokenProvider;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    public SecurityConfig(MemberSecurityService memberSecurityService, TokenProvider tokenProvider, JwtAccessDeniedHandler jwtAccessDeniedHandler) {
        this.memberSecurityService = memberSecurityService;
        this.tokenProvider = tokenProvider;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
        this.jwtAuthenticationEntryPoint = new JwtAuthenticationEntryPoint("/members/signin");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // CSRF ?????? Disable
                .csrf().disable()
//                .csrfTokenRepository(new CookieCsrfTokenRepository()) // FIXME ??????????????? 403 ?????????

                // exception handling ??? ??? ????????? ?????? ???????????? ??????
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler) // ExceptionTranslationFilter ??? ????????? AccessDeniedHandler ???????????? ????????? custom ?????? ?????????????

                // ??????????????? ??????????????? ????????? ??????
                // ???????????? ????????? ???????????? ?????? ????????? ?????? ????????? Stateless ??? ??????
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // FIXME requestCache ????????????????????? ?????? ??? ??????.
//                // RequestCache ??????
//                // cookie???
//                .and()
//                .requestCache().requestCache(new CookieRequestCache())

                // Authorization ??????. ??????????????? ????????????
                .and()
                .authorizeRequests() // http servletRequest ??? ???????????? ???????????? ?????? ??????????????? ??????
                // answer api
                .antMatchers(HttpMethod.POST, "/answers/create/**").authenticated()
                .antMatchers(HttpMethod.GET, "/answers/modify/**").authenticated()
                .antMatchers(HttpMethod.POST, "/answers/modify/**").authenticated()
                .antMatchers(HttpMethod.GET, "/answers/delete/**").authenticated()
                .antMatchers(HttpMethod.GET, "/answers/vote/**").authenticated()
                // question api
                .antMatchers(HttpMethod.GET, "/questions/create").authenticated()
                .antMatchers(HttpMethod.POST, "/questions/create").authenticated()
                .antMatchers(HttpMethod.GET, "/questions/modify/**").authenticated()
                .antMatchers(HttpMethod.POST, "/questions/modify/**").authenticated()
                .antMatchers(HttpMethod.GET, "/questions/delete/**").authenticated()
                .antMatchers(HttpMethod.GET, "/questions/vote/**").authenticated()
                .antMatchers("/**").permitAll()


                .and()
                .logout()
//        private List<LogoutHandler> logoutHandlers = new ArrayList();
//        private SecurityContextLogoutHandler contextLogoutHandler = new SecurityContextLogoutHandler();
//        private String logoutSuccessUrl = "/login?logout";
//        private LogoutSuccessHandler logoutSuccessHandler;
//        private String logoutUrl = "/logout";
//        private RequestMatcher logoutRequestMatcher;
//        private boolean permitAll;
//        private boolean customLogoutSuccess;
//        private LinkedHashMap<RequestMatcher, LogoutSuccessHandler> defaultLogoutSuccessHandlerMappings = new LinkedHashMap();
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/signout")) // 404
                .deleteCookies("accessToken", "refreshToken") // cookie ??????
                .clearAuthentication(true) // SecurityContextHolder ?????? authentication ??????
                .logoutSuccessUrl("/") // ????????? /??? redirect

                // JwtFilter ??? ????????????.
                // UsernamePasswordAuthenticationFilter ?????? ???????????? ????????? ?????? ?????????
                // SecurityContext??? ???????????? ????????? ????????? ???????????? SecurityContext??? ???????????? ?????? ????????? ??????.
                .and()
                .addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
        ;

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //    NOTE AuthenticationManager, AuthenticationConfiguration // Auth..Man.., Auth..Conf..
//    UserSecurityService??? ????????? di ???????????????????????? ?????????
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
