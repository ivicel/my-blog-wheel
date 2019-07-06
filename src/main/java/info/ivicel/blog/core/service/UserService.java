package info.ivicel.blog.core.service;

import info.ivicel.blog.core.model.domain.User;
import java.util.Optional;

public interface UserService {

    Optional<User> findByUsername(String name);

    User updatePofiles(User user);

    void updatePasswordByUsername(String name, String password);
}
