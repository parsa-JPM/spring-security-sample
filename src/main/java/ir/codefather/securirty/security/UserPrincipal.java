package ir.codefather.securirty.security;

import ir.codefather.securirty.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserPrincipal implements UserDetails {

   final private User user;

    public UserPrincipal(User user) {
        this.user = user;
    }

    /**
     * In this method we've mixed user's roles and authorities because in Spring security
     * both of these store in a collection with different name convention that roles have ROLE_ in the first of string
     *
     * @return Collection
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        this.user.getAuthoritiesList().forEach(authority -> {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority);
            authorities.add(grantedAuthority);
        });

        this.user.getRolesList().forEach(role -> {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_" + role);
            authorities.add(grantedAuthority);
        });

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
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
        return this.user.getActive() == 1;
    }
}
