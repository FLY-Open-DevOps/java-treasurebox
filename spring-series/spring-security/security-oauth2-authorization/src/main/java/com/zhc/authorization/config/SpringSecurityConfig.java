package com.zhc.authorization.config;

import com.zhc.securitycore.authentication.sms.SmsCodeAuthenticationSecurityConfig;
import com.zhc.securitycore.authentication.sms.ValidateCodeFilter;
import com.zhc.securitycore.properties.SecurityConstants;
import com.zhc.securitycore.properties.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @author zhc
 * @date 2019/8/14
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private SecurityProperties securityProperties;

    @Resource
    private FormAuthenticationConfig formAuthenticationConfig;

    @Resource
    private DataSource dataSource;

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private ValidateCodeFilter validateCodeFilter;

    @Resource
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Resource
    private SpringSocialConfigurer  securitySpringSocialConfigurer;


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        // ??????token???????????????????????????????????????????????? persistent_logins???ddl???db???????????? ???????????????????????????????????????????????????????????????
        //tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        formAuthenticationConfig.configure(http);

        http.addFilterBefore(validateCodeFilter, AbstractPreAuthenticatedProcessingFilter.class)
                .apply(smsCodeAuthenticationSecurityConfig)
                .and()
                // social ????????????????????????
                .apply(securitySpringSocialConfigurer)
                .and()
                .authorizeRequests()
                .antMatchers(SecurityConstants.DEFAULT_PAGE_URL,
                        SecurityConstants.DEFAULT_LOGIN_PAGE_URL,
                        "/send/sms/**","/oauth/**","/socialRegister",
                        securityProperties.getLogin().getLoginErrorUrl()).permitAll()
                .anyRequest().authenticated()
                .and()
                // ?????? ??????????????????????????? RememberMeAuthenticationFilter ?????? ???Cookie ?????????token??????
                .rememberMe()
                // ?????? tokenRepository ????????????????????? jdbcTokenRepositoryImpl??????????????????????????????????????????token????????????????????????
                .tokenRepository(persistentTokenRepository())
                // ??????  userDetailsService , ??? ????????????????????????RememberMe ???????????? RememberMeAuthenticationProvider ,????????????????????? ??????UserDetailsService ?????? UserDetails ??????
                .userDetailsService(userDetailsService)
                // ?????? rememberMe ?????????????????????????????? ???????????????
                .tokenValiditySeconds(securityProperties.getLogin().getRememberMeSeconds())
                .and()
                .csrf().disable();
    }
}
