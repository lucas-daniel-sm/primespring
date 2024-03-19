package dev.lucasmendes.primespring.config;

import dev.lucasmendes.primespring.entities.PrimeSpringGroup;
import dev.lucasmendes.primespring.entities.PrimeSpringUser;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Supplier;

import static dev.lucasmendes.primespring.config.CustomAuthenticationProvider.USER_SESSION_ATTR;

@Component
@Slf4j
@RequiredArgsConstructor
public class OpenPolicyAgentAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {
    private final HttpServletRequest request;


    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        final var user = (PrimeSpringUser) request.getSession().getAttribute(USER_SESSION_ATTR);
        if (user == null) {
            return new AuthorizationDecision(false);
        }

        final var servletPath = request.getServletPath();
        final var allowed = user.getGroups().stream()
                .map(PrimeSpringGroup::getAllowedUrls)
                .flatMap(List::stream)
                .anyMatch(url -> url.equals("*") || servletPath.equals(url));

        return new AuthorizationDecision(allowed);
    }
}
