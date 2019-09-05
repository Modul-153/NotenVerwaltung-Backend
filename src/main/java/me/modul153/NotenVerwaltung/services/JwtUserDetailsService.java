package me.modul153.NotenVerwaltung.services;

import me.modul153.NotenVerwaltung.data.abstracts.AbstractUser;
import me.modul153.NotenVerwaltung.data.abstracts.Credential;
import me.modul153.NotenVerwaltung.managers.CredentialManager;
import me.modul153.NotenVerwaltung.managers.UserManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.equalsIgnoreCase("root")) {
            return new User("root", "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6", new ArrayList<>());
        }

        AbstractUser user = UserManager.getInstance().getUser(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        Credential credential = CredentialManager.getInstance().get(user.getUserId());
        return new User(user.getUsername(), credential.getPassword(), new ArrayList<>());
    }
}
