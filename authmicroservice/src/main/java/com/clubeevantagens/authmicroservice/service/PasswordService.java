package com.clubeevantagens.authmicroservice.service;

import com.clubeevantagens.authmicroservice.exception.general.ClientUnavailableException;
import com.clubeevantagens.authmicroservice.exception.general.EntityNotFoundException;
import com.clubeevantagens.authmicroservice.exception.security.InvalidCredentialsException;
import com.clubeevantagens.authmicroservice.exception.security.InvalidTokenException;
import com.clubeevantagens.authmicroservice.messager.EmailProducer;
import com.clubeevantagens.authmicroservice.model.data.ResetPasswordToken;
import com.clubeevantagens.authmicroservice.model.data.User;
import com.clubeevantagens.authmicroservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PasswordService {

    private final UserRepository userRepository;

    private final EmailProducer emailProducer;

    private final PasswordEncoder passwordEncoder;

    public PasswordService(UserRepository userRepository, EmailProducer emailProducer, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.emailProducer = emailProducer;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public String sendPasswordResetTokenToEmail(String email)  {

        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found!"));

        String token = UUID.randomUUID().toString();
        ResetPasswordToken resetPasswordToken = new ResetPasswordToken();
        resetPasswordToken.setToken(token);
        resetPasswordToken.setExpiryDate(2880); // 48H
        user.setResetPasswordToken(resetPasswordToken);

        userRepository.save(user);

        String emailBody = "To reset your password, use the code below:\n" + token + "\n\n\n" +
                "If you did not request this, you can safely ignore this email.\n\n" +
                "IMPORTANT: This code is valid for 48 hours. After this period, you'll need to request a new password reset by repeating the process.";

        try{
            emailProducer.sendEmailMessage(email, "Password Reset Request", emailBody);
        } catch (Exception e){
            throw new ClientUnavailableException("Failed to send email!");
        }

        return "Password reset request successfully processed. You'll receive an email within a few minutes";
    }

    @Transactional
    public String resetPassword(String token, String newPassword) {

        User user = userRepository.findByResetPasswordTokenToken(token)
                .orElseThrow(() -> new EntityNotFoundException("User not found!"));

        ResetPasswordToken resetToken = user.getResetPasswordToken();
        if (resetToken == null || resetToken.isExpired()) {
            user.setResetPasswordToken(null);
            userRepository.save(user);
            throw new InvalidTokenException("Invalid or expired token.");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetPasswordToken(null);
        userRepository.save(user);

        return "Password successfully updated.";
    }

    @Transactional
    public String updatePassword(Long idUser, String oldPassword, String newPassword) {

        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new EntityNotFoundException("User not found!"));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new InvalidCredentialsException("Invalid password.");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return "Password successfully updated.";
    }
}