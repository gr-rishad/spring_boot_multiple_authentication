package multiple_authentication.multiple_authentication.filter;

import multiple_authentication.multiple_authentication.auth.OTPAuthToken;
import multiple_authentication.multiple_authentication.auth.UserPasswordAuthToken;
import multiple_authentication.multiple_authentication.model.UserSecretKey;
import multiple_authentication.multiple_authentication.repo.ReceiptManager;
import multiple_authentication.multiple_authentication.repo.UserSecretKeyJpaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

@Component
public class UserPasswordAuthFilter extends OncePerRequestFilter {

    @Autowired
    AuthenticationManager manager;
    @Autowired
    UserSecretKeyJpaRepo userSecretKeyJpaRepo;
    @Autowired
    ReceiptManager receiptManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {


        // come from user data (User input data)
        var uname = request.getHeader("uname");
        var password = request.getHeader("password");

        // for 2 step verification. 2nd time come. It's like otp
        var key = request.getHeader("secret-key");

        if (key == null) {
            // create authentication object
            var a = new UserPasswordAuthToken(uname, password);
            var auth = manager.authenticate(a);

            // save generated key into db
            UserSecretKey secretKey = new UserSecretKey();
            secretKey.setSkey((new Random().nextInt(999) * 1000) + "");
            secretKey.setUsername(uname);

            userSecretKeyJpaRepo.save(secretKey);
        } else {
            // through the key
            var auth = manager.authenticate(new OTPAuthToken(uname, key));
            // store inside db
            String str = UUID.randomUUID().toString();
            receiptManager.add(str);
            //generate  a token
            response.setHeader("Authorization", str);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/login");
    }
}
