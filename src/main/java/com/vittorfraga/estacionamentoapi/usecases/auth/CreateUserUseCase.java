package com.vittorfraga.estacionamentoapi.usecases.auth;

import com.vittorfraga.estacionamentoapi.domain.exceptions.UserAlreadyExistsException;
import com.vittorfraga.estacionamentoapi.domain.user.User;
import com.vittorfraga.estacionamentoapi.domain.user.UserRepository;
import com.vittorfraga.estacionamentoapi.usecases.UseCase;
import com.vittorfraga.estacionamentoapi.usecases.auth.dto.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateUserUseCase extends UseCase<LoginRequest, User> {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Override
    public User execute(LoginRequest anInput) {
        final var foundUser = userRepository.findByUsername(anInput.username());

        if (foundUser.isPresent()) {
            throw new UserAlreadyExistsException("Username already exists: " + anInput.username());
        }

        User user = new User();
        user.setUsername(anInput.username());
        user.setPassword(passwordEncoder.encode(anInput.password()));
        return userRepository.save(user);
    }
}
