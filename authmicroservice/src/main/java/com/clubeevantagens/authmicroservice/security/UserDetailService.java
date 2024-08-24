package com.clubeevantagens.authmicroservice.security;

import com.clubeevantagens.authmicroservice.exception.UserDisabledException;
import com.clubeevantagens.authmicroservice.model.User;
import com.clubeevantagens.authmicroservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException, UserDisabledException {

        Optional<User> userOptional = userRepository.findUserByEmail(email);

        if(!userOptional.isPresent()){ // usuario nao encontrado
            throw new UsernameNotFoundException("Email ou Senha incorreta");
        }

        if(!userOptional.get().isActive()){// usuario desativado
            throw new UserDisabledException("Usuario Desativado");
        }

        UserDetail userDetail = new UserDetail(userOptional.get());// converte para UserDetail (Classe do Spring Security)

        return new org.springframework.security.core.userdetails.
                User(userDetail.getUsername(), userDetail.getPassword(), userDetail.getAuthorities());
    }
}
