package com.demo.demoforum.config;

import com.demo.demoforum.feature.jwt.JwtAccessDeniedHandler;
import com.demo.demoforum.feature.jwt.JwtAuthenticationEntryPoint;
import com.demo.demoforum.feature.jwt.JwtFilter;
import com.demo.demoforum.feature.jwt.TokenProvider;
import com.demo.demoforum.feature.member.MemberSecurityService;
import lombok.RequiredArgsConstructor;
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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    private final MemberSecurityService memberSecurityService;
    private final TokenProvider tokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // CSRF 설정 Disable
                .csrf().disable()
//                .csrfTokenRepository(new CookieCsrfTokenRepository()) // FIXME 이거떄문에 403 떳던거

                // 시큐리티는 기본적으로 세션을 사용
                // 여기서는 세션을 사용하지 않기 때문에 세션 설정을 Stateless 로 설정
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // Authorization 설정. 위에서부터 순서대로
                .and()
                .authorizeRequests() // http servletRequest 를 사용하는 요청들에 대한 접근제한을 설정
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
                .deleteCookies("accessToken", "refreshToken") // cookie 삭제
                .clearAuthentication(true) // SecurityContextHolder 에서 authentication 제거 
                .logoutSuccessUrl("/") // 성공시 /로 redirect

                // JwtFilter 를 등록한다.
                // UsernamePasswordAuthenticationFilter 앞에 등록하는 이유는 딱히 없지만
                // SecurityContext를 사용하기 때문에 앞단의 필터에서 SecurityContext가 설정되고 난뒤 필터를 둔다.
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
//    UserSecurityService랑 어떻게 di 일어나는거같은데 몰겠넹
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
