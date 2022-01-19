package github.com.tombessa.salesportfolio.config;

import github.com.tombessa.salesportfolio.repository.UserAccessRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private AuthenticationManagerBuilder auth;
    private final UserAccessRepository userAccessRepository;

    public SecurityConfiguration(UserAccessRepository userAccessRepository) {
        this.userAccessRepository = userAccessRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/**").hasRole("USER")
                .and()
                .httpBasic();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        this.auth = auth;
        //Root user
        auth.inMemoryAuthentication().withUser("sales").password(passwordEncoder().encode("sales")).roles("USER");
        //Loading all users from database
        this.userAccessRepository.findAll().forEach(userAccess -> {
            try {
                if (userAccess.getRole() != null)
                    auth.inMemoryAuthentication()
                            .withUser(userAccess.getLogin())
                            .password(passwordEncoder().encode(userAccess.getPassword()))
                            .roles(userAccess.getRole().getName());
                else
                    auth.inMemoryAuthentication()
                            .withUser(userAccess.getLogin())
                            .password(passwordEncoder().encode(userAccess.getPassword()))
                            .roles("USER");
            }catch(Exception e){}
        });

    }

    public void changePassword(String username, String password, String roleName) throws Exception {
        auth.inMemoryAuthentication()
                .withUser(username)
                .password(passwordEncoder().encode(password))
                .roles(roleName);
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
