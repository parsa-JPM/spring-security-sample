package ir.codefather.securirty.security;

import ir.codefather.securirty.entities.UserRepo;
import ir.codefather.securirty.jwt.JWTAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class MySecurity extends WebSecurityConfigurerAdapter {


    @Autowired
    private UserRepo userRepo;

    UserPrincipalDetailService detailService;

    public MySecurity(UserPrincipalDetailService detailService) {
        this.detailService = detailService;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(detailService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().ignoringAntMatchers("/api/**")
                .and()
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), userRepo))
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin();
    }

    /**
     * It wrote because i was lazy to new project for JWT :)
     *
     * @param http
     * @throws Exception
     */
    private void formBasedConfig(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/admin").hasAuthority("ACCESS_ADMIN")
                .antMatchers("/manage").hasRole("admin")
                .and()
                .formLogin().loginPage("/login").defaultSuccessUrl("/manage")
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        ;

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
