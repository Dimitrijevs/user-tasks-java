package testjwt.demo.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import testjwt.demo.config.JwtService;
import testjwt.demo.user.Role;
import testjwt.demo.user.User;
import testjwt.demo.user.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

        // created
        private final UserRepository repository;

        // built in
        private final PasswordEncoder passwordEncoder;

        // created
        private final JwtService jwtService;

        // built in
        private final AuthenticationManager authenticationManager;

        // return token to a user
        public AuthenticationResponse register(RegisterRequest request) {
                User user = User.builder()
                                .first_name(request.getFirstname())
                                .last_name(request.getLastname())
                                .email(request.getEmail())
                                .password(passwordEncoder.encode(request.getPassword()))
                                .role(Role.USER)
                                .build();

                repository.save(user);

                String jwtToken = jwtService.generateToken(user);

                return generateResponseWithToken(jwtToken);
        }

        // return token to a user
        public AuthenticationResponse authenticate(AuthenticationRequest request) {
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                request.getEmail(),
                                                request.getPassword()));

                User user = repository.findByEmail(request.getEmail()).orElseThrow();

                String jwtToken = jwtService.generateToken(user);

                return generateResponseWithToken(jwtToken);
        }

        private AuthenticationResponse generateResponseWithToken(String jwtToken) {
                return AuthenticationResponse.builder()
                                .token(jwtToken)
                                .build();
        }
}
