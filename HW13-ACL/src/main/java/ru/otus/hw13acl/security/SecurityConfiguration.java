package ru.otus.hw13acl.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import ru.otus.hw13acl.service.UserService;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
  private final UserService userService;

  public SecurityConfiguration(UserService userService) {
    this.userService = userService;
  }

  @Override
  public void configure(WebSecurity web) {
    web.ignoring().antMatchers("/");
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    /*
    http.csrf().disable()
        //.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        //.and()
        .authorizeRequests().antMatchers("/public").permitAll()
        .and()
        .authorizeRequests().antMatchers("/book", "/author", "/genre").hasAnyRole("ADMIN",
                                                                                  "MANAGER", "USER")
        .and()
        .authorizeRequests().antMatchers("/genre/editgenre", "/genre/addgenre",
                                         "/author/editauthor", "/author/addauthor",
                                         "/book/editbook", "/book/addbook").hasAnyRole("ADMIN",
                                                                                       "MANAGER")
        .and()
        .authorizeRequests().antMatchers("/book/delbook", "/author/delauthor", "/genre/delgenre").hasRole("ADMIN")
        .and()
        .formLogin().permitAll()
        .and()
        .logout().permitAll()
        .and()
        .exceptionHandling().accessDeniedPage("/403");
  */
    http.authorizeRequests()
        .antMatchers("/", "/book", "/author", "/genre").hasAnyAuthority("USER", "MANAGER", "ADMIN")
        .antMatchers("/genre/editgenre", "/genre/addgenre",
                     "/author/editauthor", "/author/addauthor",
                     "/book/editbook", "/book/addbook").hasAnyAuthority("ADMIN", "MANAGER")
        .antMatchers("/book/delbook", "/author/delauthor", "/genre/delgenre").hasAuthority("ADMIN")
        .anyRequest().authenticated()
        .and()
        .formLogin().permitAll()
        .and()
        .logout().permitAll()
        .and()
        .exceptionHandling().accessDeniedPage("/accessDenied")
    ;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new PasswordEncoder() {

      @Override
      public String encode(CharSequence rawPassword) {

        return BCrypt.hashpw(rawPassword.toString(), BCrypt.gensalt(10));
      }

      @Override
      public boolean matches(CharSequence rawPassword, String encodedPassword) {

        return BCrypt.checkpw(rawPassword.toString(), encodedPassword);
      }
    };
  }

  @Override
  public void configure(AuthenticationManagerBuilder auth)
      throws Exception {
    auth.authenticationProvider(authenticationProvider());
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userService);
    authProvider.setPasswordEncoder(passwordEncoder());

    return authProvider;
  }
}
