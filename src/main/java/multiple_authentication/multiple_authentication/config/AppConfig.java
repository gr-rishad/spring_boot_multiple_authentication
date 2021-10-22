package multiple_authentication.multiple_authentication.config;

import multiple_authentication.multiple_authentication.filter.ReceiptAuthFilter;
import multiple_authentication.multiple_authentication.filter.UserPasswordAuthFilter;
import multiple_authentication.multiple_authentication.provider.OTPAuthProvider;
import multiple_authentication.multiple_authentication.provider.ReceiptAuthProvider;
import multiple_authentication.multiple_authentication.provider.UserPasswordAuthProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class AppConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserPasswordAuthFilter filter;
    @Autowired
    UserPasswordAuthProvider userPasswordAuthProvider;
    @Autowired
    OTPAuthProvider otpAuthProvider;
    // @Autowired
    // ReceiptAuthFilter receiptAuthFilter;
    @Autowired
    ReceiptAuthProvider receiptAuthProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(userPasswordAuthProvider)
                .authenticationProvider(otpAuthProvider)
                .authenticationProvider(receiptAuthProvider);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterAt(filter, BasicAuthenticationFilter.class)
                .addFilterBefore(receiptAuthFilter(), BasicAuthenticationFilter.class);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public ReceiptAuthFilter receiptAuthFilter() {
        return new ReceiptAuthFilter();
    }
}
