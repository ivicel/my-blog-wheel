package info.ivicel.blog.core.service.impl;

import info.ivicel.blog.core.exception.AccountOrPasswordNotFoundException;
import info.ivicel.blog.core.model.domain.User;
import info.ivicel.blog.core.repository.UserRepository;
import info.ivicel.blog.core.service.UserService;
import java.util.Optional;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Setter(onMethod = @__(@Autowired))
@Service("backendUserService")
public class UserServiceImpl implements UserDetailsService, UserService {
    private UserRepository userRepository;
    // private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> author;
        if (username.contains("@")) {
            author = userRepository.findByEmail(username);
        } else {
            author = userRepository.findByUsername(username);
        }

        return author.orElseThrow(AccountOrPasswordNotFoundException::new);
    }

    @Override
    public Optional<User> findByUsername(String name) {
        return userRepository.findByUsername(name);
    }

    @Override
    @Transactional
    public User updatePofiles(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void updatePasswordByUsername(String name, String password) {
        userRepository.updatePasswordByUsername(name, new BCryptPasswordEncoder().encode(password));
    }
}
