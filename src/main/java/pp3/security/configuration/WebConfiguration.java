package pp3.security.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pp3.security.service.UserServiceImpl;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebConfiguration extends WebSecurityConfigurerAdapter {

    private final SuccessUserHandler successUserHandler;
    private final UserServiceImpl userServiceImpl;
    private final BCryptPasswordEncoder passwordEncoder;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/users", "/updateInfo/{id}", "/deleteUser/{id}").hasRole("ADMIN")
                .antMatchers("all-users").hasRole("ADMIN")
                .antMatchers("/addNewUser").hasRole("ADMIN")
                .antMatchers("user-info").hasRole("ADMIN")
                .antMatchers("/saveUser").hasRole("ADMIN")
                .antMatchers("/user").hasRole("USER")
                .anyRequest().authenticated()
                .and().formLogin().successHandler(successUserHandler)
                .permitAll()
                .and().logout().permitAll();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userServiceImpl).passwordEncoder(passwordEncoder);
    }
}
