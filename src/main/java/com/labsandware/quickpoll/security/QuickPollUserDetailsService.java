package com.labsandware.quickpoll.security;

import javax.inject.Inject;

import com.labsandware.quickpoll.domain.User;
import com.labsandware.quickpoll.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuickPollUserDetailsService implements UserDetailsService {

    @Inject
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername (String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) throw new UsernameNotFoundException("User with the username\n" +
                "%s doesn't exist\", username");

        // Create a granted authority based on user's role.
        // Can't pass null authorities to user. Hence initialize with an empty arraylist
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (user.isAdmin()) {
            authorities = AuthorityUtils.createAuthorityList("ROLE_ADMIN");
        }

        // Create a UserDetails object from the data
        UserDetails userDetails;
        userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);

        return  userDetails;
    }
}
