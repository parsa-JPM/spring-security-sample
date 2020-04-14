package ir.codefather.securirty.security;

import ir.codefather.securirty.entities.User;
import ir.codefather.securirty.entities.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserPrincipalDetailService implements UserDetailsService {

    private UserRepo repo;

    public UserPrincipalDetailService(UserRepo repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = repo.findByUsername(s);
        if (user == null)
            throw new UsernameNotFoundException(s);
        return new UserPrincipal(user);
    }
}
