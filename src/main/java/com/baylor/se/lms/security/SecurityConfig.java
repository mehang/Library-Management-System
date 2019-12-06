package com.baylor.se.lms.security;

import com.baylor.se.lms.data.UserRepository;
import com.baylor.se.lms.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(encoder());
    }

    @Bean
    public JwtAuthenticationFilter authenticationTokenFilterBean() {
        return new JwtAuthenticationFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().
                authorizeRequests()
                .antMatchers("/console/**", "/authenticate", "users/forgot-password",
                        "users/reset-password", "/users/students/verify/**","/books/search").permitAll()
                .antMatchers(HttpMethod.POST, "/users/students").permitAll()
                .antMatchers(HttpMethod.PUT, "/users/students/**").authenticated()
                .antMatchers(HttpMethod.GET, "/users/students").hasRole("LIBRARIAN")
                .antMatchers(HttpMethod.DELETE, "/users/students").hasRole("LIBRARIAN")
                .antMatchers(HttpMethod.POST, "/users/librarians").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/users/librarians/**").authenticated()
                .antMatchers(HttpMethod.GET, "/users/librarians").hasRole("LIBRARIAN")
                .antMatchers(HttpMethod.DELETE, "/users/librarians").hasRole("ADMIN")
                .antMatchers("/users/admins/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/users/**").permitAll()
                .antMatchers("/books/request").hasRole("STUDENT")
                .antMatchers(HttpMethod.GET, "/books/**").hasRole("STUDENT")
                .antMatchers("/books/categories/**").hasRole("LIBRARIAN")
                .antMatchers("/authors/**").hasRole("LIBRARIAN")
                .antMatchers("/bookspecs/**").hasRole("LIBRARIAN")
                .antMatchers("/users/change-password").authenticated()
                .antMatchers("/books/**").hasRole("LIBRARIAN")
                .anyRequest().permitAll()
//                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
        http.csrf().disable();
//        http.headers().frameOptions().disable();
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        //todo: check here future
//        http.authorizeRequests()
//                .antMatchers("/users/students/").authenticated()
//                .and()
//                .authorizeRequests().antMatchers("/console/**").permitAll()
//                .anyRequest().permitAll()
////        .and().httpBasic().realmName(REALM).authenticationEntryPoint(getBasicAuthEntryPoint())
////                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .formLogin().permitAll();
//        http.csrf().disable();
//        //used for h2 console
//        http.headers().frameOptions().disable();
//    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
