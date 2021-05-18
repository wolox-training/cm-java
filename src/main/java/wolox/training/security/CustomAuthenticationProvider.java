package wolox.training.security;

import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import wolox.training.common.Constants;
import wolox.training.exceptions.UserNotFoundException;
import wolox.training.models.UserBook;
import wolox.training.repositories.UserRepository;

@Component
public class CustomAuthenticationProvider implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserBook user = userRepository.findByUsername(username).
                orElseThrow(() -> new UserNotFoundException(Constants.MESSAGE_ERROR_NOT_FOUND_USER));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                Collections.emptyList());
    }

}
