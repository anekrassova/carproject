package org.example.lab4.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.example.lab4.entity.Role;
import org.example.lab4.entity.User;
import org.example.lab4.repository.RoleRepository;
import org.example.lab4.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;
import java.util.Base64;

@Service
public class AuthService {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public RoleRepository roleRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final SecretKey jwtSecretKey;
    private final long JWT_EXPIRATION_MS = 86400000;

    @Autowired
    public AuthService(SecretKey jwtSecretKey) {
        this.jwtSecretKey = jwtSecretKey;
    }

    public User register(String username, String email, String password) {

        Role defaultRole = roleRepository.findByRoleName("USER")
                .orElseGet(() -> {
                    Role r = new Role();
                    r.setRoleName("USER");
                    return roleRepository.save(r);
                });

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(defaultRole);

        return userRepository.save(user);
    }


    public String login(String email, String password) throws Exception {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return generateToken(user.get());
        }

        throw new Exception("Неверный логин или пароль");
    }

    private String generateToken(User user) {
        String secretKeyBase64 = Base64.getEncoder().encodeToString(jwtSecretKey.getEncoded());
        System.out.println("Secret key for token generation (Base64): " + secretKeyBase64);

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("userId", user.getId())
                //.claim("email", user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_MS))
                .signWith(jwtSecretKey, SignatureAlgorithm.HS512)
                .compact();
    }
}
