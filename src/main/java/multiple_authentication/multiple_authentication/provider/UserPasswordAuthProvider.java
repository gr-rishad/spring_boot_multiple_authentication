package multiple_authentication.multiple_authentication.provider;

import multiple_authentication.multiple_authentication.auth.UserPasswordAuthToken;
import multiple_authentication.multiple_authentication.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserPasswordAuthProvider implements AuthenticationProvider {

    @Autowired
    MyUserDetailsService myUserDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) {
        var user = myUserDetailsService.loadUserByUsername(authentication.getName());

        // authentication.something -> come from user. (auth.getCr() --> userInput and user.getPass()--> database data )
        if (passwordEncoder.matches(authentication.getCredentials().toString(), user.getPassword())) {
            return new UserPasswordAuthToken(user.getUsername(), user.getPassword(), user.getAuthorities());
        }
        throw new BadCredentialsException("Failed Authentication");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UserPasswordAuthToken.class.equals(authentication);
    }
}
