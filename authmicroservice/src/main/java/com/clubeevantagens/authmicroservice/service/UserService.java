package com.clubeevantagens.authmicroservice.service;

import com.clubeevantagens.authmicroservice.model.Company;
import com.clubeevantagens.authmicroservice.model.dto.UserDto;
import com.clubeevantagens.authmicroservice.security.UserDetail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import com.clubeevantagens.authmicroservice.model.User;
import com.clubeevantagens.authmicroservice.repository.ClientRepository;
import com.clubeevantagens.authmicroservice.repository.UserRepository;
import com.clubeevantagens.authmicroservice.security.JwtUtils;
import com.clubeevantagens.authmicroservice.security.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Value("${jwt.token.expiry}")
    private Long expiryToken;

    // DELETAR USER
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            userRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // LOGIN USER
    @Transactional
    public ResponseEntity<?> loginUser(UserDto user){

        try {
            UserDetails userDetails = userDetailService.loadUserByUsername(user.getEmail());// valida email

            if (passwordEncoder.matches(user.getPassword(), userDetails.getPassword())) {// valida password

                var accessToken = jwtUtils.generateAccessToken(userDetails);// gera "accessToken"
                var refreshToken = jwtUtils.generateRefreshToken(accessToken); // gera "refreshToken"

                userRepository.updateRefreshTokenByEmail(
                        user.getEmail(), Integer.parseInt(refreshToken.get("key")));// salva "key" no banco

                Map<String, String> payload = new HashMap<>();// retorno
                payload.put("accessToken",accessToken);
                payload.put("refreshToken", refreshToken.get("refreshToken"));

                return ResponseEntity.status(HttpStatus.ACCEPTED).body(payload);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email ou Senha incorreta");
            }

        } catch (UsernameNotFoundException e) {// email nao encontrado
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email ou Senha incorreta");
        } catch (Exception e1){ // outros erros
            e1.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro inesperado");
        }
    }


    // NEW TOKEN
    @Transactional
    public ResponseEntity<?> newToken(String expiredAccessToken, String refreshToken) {

        Map<String, String> accessTokenMap = jwtUtils.extractAccessToken(expiredAccessToken);// extrair accessToken
        var accessIssuedAt =  accessTokenMap.get("iat");
        var accessExpiredAt = accessTokenMap.get("exp");
        var accessSub = accessTokenMap.get("sub");

        Map<String, String> refreshTokenMap = jwtUtils.extractRefreshToken(refreshToken);// extrair refreshToken
        var refreshKey = refreshTokenMap.get("key");
        var refreshSignature = refreshTokenMap.get("signature");
        var refreshSub = refreshTokenMap.get("sub");

        var signatureRaw = ""+refreshKey + accessIssuedAt + accessExpiredAt;// assinatura

        if(refreshSub.equals(accessSub) && new BCryptPasswordEncoder().matches(signatureRaw, refreshSignature)){// valida "sub" e "signature"

            var key = userRepository.findRefreshTokenById(Long.valueOf(refreshSub));// pega "key" do banco

            if(refreshKey.equals(key.get().toString())){// compara "key" do banco com "key" do client

                var userOptional = userRepository.findById(Long.valueOf(accessSub));
                UserDetails userDetails = new UserDetail(userOptional.get());

                var accessToken = jwtUtils.generateAccessToken(userDetails);// gera "accessToken"
                var refreshTokenn = jwtUtils.generateRefreshToken(accessToken); // gera "refreshToken"

                userRepository.updateRefreshTokenByEmail(
                        userOptional.get().getEmail(), Integer.parseInt(refreshTokenn.get("key")));// salva "key" no banco

                Map<String, String> payload = new HashMap<>();// retorno
                payload.put("newAccessToken",accessToken);
                payload.put("newRefreshToken", refreshTokenn.get("refreshToken"));

                return ResponseEntity.status(HttpStatus.ACCEPTED).body(payload);

            }else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("access-token ou refresh-token invalido ");
            }

        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("access-token ou refresh-token invalido ");
        }

    }
}
