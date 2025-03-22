package com.clubeevantagens.authmicroservice.security;

import com.clubeevantagens.authmicroservice.exception.user.UserDisabledException;
import com.clubeevantagens.authmicroservice.model.data.Client;
import com.clubeevantagens.authmicroservice.repository.ClientRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final ClientRepository clientRepository;

    public UserDetailsServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Client> client = clientRepository.findByUserEmail(email);
        if(client.isEmpty()){
            throw new UsernameNotFoundException("Invalid email.");
        }

        return client.get().getUser();
    }
}