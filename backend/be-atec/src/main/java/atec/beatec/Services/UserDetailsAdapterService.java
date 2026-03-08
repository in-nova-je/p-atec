package atec.beatec.Services;

import atec.beatec.Entities.Role;
import atec.beatec.Entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserDetailsAdapterService implements UserDetails {

    private final User user;
    //private final Collection<? extends GrantedAuthority> authorities;

    public UserDetailsAdapterService(User user/*,Collection<? extends GrantedAuthority> authorities*/) {
        this.user = user;
       /* this.authorities=authorities;*/
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Role role = user.getRole() != null ? user.getRole() : Role.USER;
        return Collections.singleton(/*new SimpleGrantedAuthority("USER")*/ new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
