package com.mybus.login.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@ComponentScan(basePackages = "com.mybus")
@EnableWebSecurity
@EnableMongoRepositories(basePackages = "com.mybus.repository")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginService loginService;

    @Autowired
    private PasswordChecker passwordChecker;

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        /*auth.
                jdbcAuthentication()
                .usersByUsernameQuery(usersQuery)
                .authoritiesByUsernameQuery(rolesQuery)
                .dataSource(dataSource)
                .passwordEncoder(bCryptPasswordEncoder);*/
        auth.userDetailsService( loginService).passwordEncoder(passwordChecker);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/admin/**").authenticated().and()
                .formLogin().loginPage("/login")
                .defaultSuccessUrl("/admin/home")
                .failureUrl("/login?error")
                .usernameParameter("username")
                .passwordParameter("password").and()
                .logout().logoutSuccessUrl("/login?logout")
                .and().csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }

}
