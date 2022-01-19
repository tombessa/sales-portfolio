package github.com.tombessa.salesportfolio.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

@Configuration
public class UserIDAuditorBean implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        if (ctx == null) {
            return Optional.empty();
        }
        if (ctx.getAuthentication() == null) {
            return Optional.empty();
        }
        if (ctx.getAuthentication().getPrincipal() == null) {
            return Optional.empty();
        }
        User principal = ((User) ctx.getAuthentication().getPrincipal());
        return Optional.of(principal.getUsername());
    }
}
