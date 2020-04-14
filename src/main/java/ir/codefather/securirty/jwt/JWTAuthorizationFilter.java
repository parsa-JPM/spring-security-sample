package ir.codefather.securirty.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import ir.codefather.securirty.entities.User;
import ir.codefather.securirty.entities.UserRepo;
import ir.codefather.securirty.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

/**
 * BasicAuthenticationFilter In summary, this filter is responsible for processing any request
 * that has a HTTP request header of Authorization with an authentication scheme
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private UserRepo userRepo;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, UserRepo userRepo) {
        super(authenticationManager);
        this.userRepo = userRepo;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader(JWTProperties.HEADER_STRING);

        if (token == null || !token.startsWith(JWTProperties.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        /**
         * Once the request has been authenticated, the Authentication will usually be stored in a thread-local SecurityContext
         * managed by the SecurityContextHolder by the authentication mechanism which is being used. An explicit authentication can be achieved,
         * without using one of Spring Security's authentication mechanisms, by creating an Authentication instance and using the code:
         *
         * SecurityContextHolder.getContext().setAuthentication(anAuthentication);
         */
        Authentication authentication = getUsernamePasswordAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Continue filter execution
        chain.doFilter(request, response);
    }

    /**
     * validate JWT token and make Authentication token for spring security with user's authorities
     *
     * @param request
     * @return UsernamePasswordAuthenticationToken
     */
    private Authentication getUsernamePasswordAuthentication(HttpServletRequest request) {
        String token = request.getHeader(JWTProperties.HEADER_STRING).replace(JWTProperties.TOKEN_PREFIX, "").trim();

        System.out.println(token);

        Algorithm algorithmString = Algorithm.HMAC512(JWTProperties.SECRET);
        String username = JWT.require(algorithmString)
                .build()
                .verify(token)
                .getSubject();

        if (username == null)
            return null;

        User user = userRepo.findByUsername(username);
        UserPrincipal principal = new UserPrincipal(user);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, principal.getAuthorities());

        return authenticationToken;
    }


}
