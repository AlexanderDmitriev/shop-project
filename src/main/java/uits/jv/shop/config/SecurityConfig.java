package uits.jv.shop.config;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.session.SessionRegistry;
//import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import uits.jv.shop.config.domain.User;
import uits.jv.shop.config.domain.UserDao;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    
    @Autowired
    private UserDao userDao;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

//    @Bean
//    public SessionRegistry sessionRegistry() {
//        return new SessionRegistryImpl();
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            private final ShaPasswordEncoder sha = new ShaPasswordEncoder();

            @Override
            public String encode(CharSequence cs) {
                return sha.encodePassword(cs.toString(), 1);
            }

            @Override
            @SuppressWarnings("PMD")
            public boolean matches(CharSequence cs, String string) {
                return (sha.encodePassword(cs.toString(), 1)).equals(string);
            }
        };
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setUserDetailsService(userDetailsService());
        return authProvider;
    }

    @Override
    public UserDetailsService userDetailsService() {
        UserDetailsService detailsService = new UserDetailsService() {

            @Override
            public UserDetails loadUserByUsername(String string) throws UsernameNotFoundException {
                User user = null;
                try {
                    user = userDao.loadByUsername(string);
                } catch (Exception ex) {
                    System.out.println("user not found or error");
                    throw new UsernameNotFoundException("can't find user@username" + string);
                }
                if (user == null) {
                    throw new UsernameNotFoundException("can't find user@username" + string);
                }
                return (UserDetails) user;                
            }
        };
        return detailsService;
    }

    @Bean
    public AuthenticationDetailsSource authenticationDetailsSource() {
        return new WebAuthenticationDetailsSource() {
            @Override
            public WebAuthenticationDetails buildDetails(HttpServletRequest context) {
                return new WebAuthenticationDetails(context);
            }
        };

    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/static/**").permitAll()
                .antMatchers("/me/**").access("hasRole('USER')")
                .and().formLogin().loginPage("/login").permitAll()
                .defaultSuccessUrl("/me", false)
                .usernameParameter("email")
                .and().logout().logoutUrl("/logout").permitAll();
        http.formLogin();
        http.cors();
        http.headers().frameOptions().disable();
        http.headers().httpStrictTransportSecurity().disable();
    }
}
