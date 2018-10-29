package by.project.usermanagementsystem.spring.config;

import by.project.usermanagementsystem.spring.auth.AuthHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthHandler authHandler;

    @Autowired
    private DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery(
                        "select name,password,enabled from user_db.client where name = ?")
                .authoritiesByUsernameQuery(
                        "select name, role from user_db.client where name = ?").passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                    .antMatchers("/**").permitAll()
                    .antMatchers("/pages/client.xhtml").hasRole("UNLOCKED")
                    .anyRequest().authenticated()

                .and()

                    .exceptionHandling().accessDeniedPage("/index.xhtml")
                .and()

                    .csrf().disable()

                .formLogin()
                    .loginPage("/index.xhtml")
                    .failureHandler(authHandler)
                    .defaultSuccessUrl("/success")
                    .loginProcessingUrl("/login")
                    .passwordParameter("password")
                    .usernameParameter("username")
                .and()

                .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/index.xhtml")
                    .deleteCookies("JSESSIONID", "SPRING_SECURITY_REMEMBER_ME_COOKIE")
                    .invalidateHttpSession(true);
    }
}
