package com.clubeevantagens.authmicroservice.security;

import com.clubeevantagens.authmicroservice.model.User;
import com.clubeevantagens.authmicroservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    // USER BY MAIL
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<User> userOptional = userRepository.findUserByEmail(email);

        if(!userOptional.isPresent()){ // usuario nao encontrado
            throw new UsernameNotFoundException("Email ou Senha incorreta");
        }

        UserDetail userDetail = new UserDetail(userOptional.get());// converte para UserDetail (Classe do Spring Security)

        return new org.springframework.security.core.userdetails.
                User(userDetail.getUsername(), userDetail.getPassword(), userDetail.getAuthorities());
    }
}
