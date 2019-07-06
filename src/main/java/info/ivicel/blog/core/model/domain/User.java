package info.ivicel.blog.core.model.domain;

import info.ivicel.blog.core.model.domain.base.BaseDomain;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseDomain implements UserDetails {

    private static final long serialVersionUID = 2753264161304207495L;

    private String username;

    private String nickName;

    private String passwordHash;

    private String email;

    private String avatar;

    private Date lastLoginFailedDate;

    private Integer tryCount;

    private String secret;

    /**###################################
     * UserDetails 的实现
     * ###################################
     */

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton((GrantedAuthority) () -> "ROLE_ADMIN");
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
