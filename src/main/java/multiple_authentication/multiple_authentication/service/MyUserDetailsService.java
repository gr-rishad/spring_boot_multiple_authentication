package multiple_authentication.multiple_authentication.service;

import multiple_authentication.multiple_authentication.model.SecureUser;
import multiple_authentication.multiple_authentication.model.User;
import multiple_authentication.multiple_authentication.repo.UserDetailsJpaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class MyUserDetailsService implements UserDetailsService {


    @Autowired
    UserDetailsJpaRepo userDetailsJpaRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = userDetailsJpaRepo.findByUsername(username).orElse(new User());
        return new SecureUser(u);
    }
}
