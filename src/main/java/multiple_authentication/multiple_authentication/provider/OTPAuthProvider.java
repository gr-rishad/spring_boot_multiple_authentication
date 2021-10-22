package multiple_authentication.multiple_authentication.provider;

import multiple_authentication.multiple_authentication.auth.OTPAuthToken;
import multiple_authentication.multiple_authentication.repo.UserSecretKeyJpaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class OTPAuthProvider implements AuthenticationProvider {

    @Autowired
    UserSecretKeyJpaRepo userSecretKeyJpaRepo;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        var user = userSecretKeyJpaRepo.findByUsername(authentication.getName());
        if (user.isPresent()) {
            return new OTPAuthToken(authentication.getName(), authentication.getCredentials()
                    , Arrays.asList(() -> "read"));
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OTPAuthToken.class.equals(authentication);
    }
}
