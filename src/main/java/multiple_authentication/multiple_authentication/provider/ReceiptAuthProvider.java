package multiple_authentication.multiple_authentication.provider;

import multiple_authentication.multiple_authentication.auth.ReceiptAuthentication;
import multiple_authentication.multiple_authentication.repo.ReceiptManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class ReceiptAuthProvider implements AuthenticationProvider {

    @Autowired
    private ReceiptManager receiptManager;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String receiptNo = authentication.getName();
        boolean flag = receiptManager.contains(receiptNo);
        if (flag) {
            return new ReceiptAuthentication(receiptNo, null, null);
        }
        throw new BadCredentialsException("Failed !!");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return ReceiptAuthentication.class.equals(authentication);
    }
}
