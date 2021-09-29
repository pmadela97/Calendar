package pawelmadela.calendar.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;
import pawelmadela.calendar.auth.jwt.JwtAuthenticationEntryPoint;
import pawelmadela.calendar.auth.jwt.JwtRequestFilter;
import pawelmadela.calendar.enums.Authority;
import pawelmadela.calendar.services.UserServiceImp;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{

    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    UserServiceImp userServiceImp;
    JwtRequestFilter jwtRequestFilter;

    @Autowired
    SpringSecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                         UserServiceImp userServiceImp,
                         JwtRequestFilter jwtRequestFilter){
        this.userServiceImp = userServiceImp;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtRequestFilter = jwtRequestFilter;
    }

@Bean
PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
}


    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userServiceImp).passwordEncoder(passwordEncoder());
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests().antMatchers("/auth").permitAll()
                .and()
                .authorizeRequests().antMatchers("/registration/**").permitAll()
                .antMatchers("/api/admin/**").hasAuthority(Authority.ADMIN_READ.getAuthority())
                .antMatchers("/api/admin/**").hasAuthority(Authority.ADMIN_WRITE.getAuthority())
                .antMatchers("/api/admin/**").hasAuthority(Authority.ADMIN_DELETE.getAuthority())
                .antMatchers("/api/user/**").hasAuthority(Authority.USER_READ.getAuthority())
                .antMatchers("/api/user/**").hasAuthority(Authority.USER_WRITE.getAuthority())
                .antMatchers("/api/user/**").hasAuthority(Authority.USER_DELETE.getAuthority())
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.cors();

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    }


}