package com.docto.protechdoctolib.user;

import com.docto.protechdoctolib.logging.token.ConnectionToken;
import com.docto.protechdoctolib.logging.token.ConnectionTokenRepository;
import com.docto.protechdoctolib.logging.token.ConnectionTokenService;
import com.docto.protechdoctolib.registration.token.ConfirmationToken;
import com.docto.protechdoctolib.registration.token.ConfirmationTokenService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    private final static String USER_NOT_FOUND =
            "user with email %s not found";
    private UserRepository userRepository;
    private final ConnectionTokenRepository connectionTokenRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    public UserService(@Qualifier("users")UserRepository userRepository, ConnectionTokenService connectionTokenService, ConnectionTokenRepository connectionTokenRepository, BCryptPasswordEncoder bCryptPasswordEncoder, ConfirmationTokenService confirmationTokenService) {
        this.userRepository = userRepository;
        this.connectionTokenRepository = connectionTokenRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.confirmationTokenService = confirmationTokenService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND,email)));
    }

    public String signUpUser(User user){
        boolean userExists = userRepository.findByEmail(user.getEmail())
                .isPresent();

        if (userExists){

            // TODO if email not confirmed send again a confirmation email
            throw new IllegalStateException("email already taken");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());

        user.setPassword(encodedPassword);

        userRepository.save(user);

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);
//      TODO: SEND EMAIL
        return token;
    }

    public String login(String email, String password) {
        Optional<User> userExists = userRepository.login(email, password);

        if (userExists.isPresent()) {

            String token = UUID.randomUUID().toString();
            User user = userExists.get();
            ConnectionToken connectionToken = new ConnectionToken(
                    token,
                    LocalDateTime.now(),
                    LocalDateTime.now().plusMinutes(60),
                    user
            );
            userRepository.save(user);
            return token;
        }
        return "l'utilisateur n'existe pas";
    }


    public Optional findByToken(String token) {
        Optional user = userRepository.findByToken(token);
        if (user.isPresent()) {
            User user1 = (User) user.get();
            User user2 = new User(user1.getNom(), user1.getPrenom(), user1.getEmail(), user1.getPassword(), user1.getPhonenumber(), UserRole.USER);
            return Optional.of(user);
        }
        return Optional.empty();
    }


    public int enableAppUser(String email) {
        return userRepository.enableUser(email);
    }
}
