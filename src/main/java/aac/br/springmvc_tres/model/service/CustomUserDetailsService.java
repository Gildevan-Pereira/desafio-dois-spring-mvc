package aac.br.springmvc_tres.model.service;

import aac.br.springmvc_tres.config.security.custom.CustomUserDetails;
import aac.br.springmvc_tres.model.entity.UserEntity;
import aac.br.springmvc_tres.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByName(username).orElseThrow();

        return new CustomUserDetails(
                user.getId(),
                user.getName(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("USER"))
        );
    }
}