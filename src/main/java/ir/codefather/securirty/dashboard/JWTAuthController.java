package ir.codefather.securirty.dashboard;

import com.auth0.jwt.JWT;
import ir.codefather.securirty.jwt.JWTProperties;
import ir.codefather.securirty.jwt.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@RestController
public class JWTAuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/api/login")
    public ResponseEntity<String> getToken(UserDTO userDTO) {

        try {
            /**
             * AuthenticationManager Processes an Authentication request.
             */
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword()));
        } catch (Exception e) {
            return new ResponseEntity<>("User or pass is invalid", HttpStatus.FORBIDDEN);
        }

        // Create JWT Token
        String token = JWT.create()
                .withSubject(userDTO.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JWTProperties.EXPIRATION_TIME))
                .sign(HMAC512(JWTProperties.SECRET.getBytes()));

        return new ResponseEntity<>(JWTProperties.TOKEN_PREFIX + " " + token, HttpStatus.OK);
    }

}
