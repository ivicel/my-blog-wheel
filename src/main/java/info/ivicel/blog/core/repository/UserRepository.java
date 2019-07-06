package info.ivicel.blog.core.repository;

import info.ivicel.blog.core.model.domain.User;
import info.ivicel.blog.core.repository.base.BaseRepository;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    @Modifying
    @Query("update User u set u.passwordHash = :password where u.username = :username")
    void updatePasswordByUsername(@Param("username") String username, @Param("password") String password);
}
