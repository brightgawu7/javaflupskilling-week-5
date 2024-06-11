package com.qwammy.javaflupskilling_week_5.security;

import com.qwammy.javaflupskilling_week_5.enums.UserRoles;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class SecurityUtils {

    /**
     * Retrieves the email of the current authenticated user.
     *
     * @return the email of the current authenticated user, or null if not authenticated
     */
    public  String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (isAuthenticated(authentication)) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                return ((UserDetails) principal).getUsername();
            }
        }
        return null;
    }

    /**
     * Checks if the current authenticated user has the admin role.
     *
     * @return true if the user has the admin role, false otherwise
     */
    public  boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (isAuthenticated(authentication)) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                Collection<? extends GrantedAuthority> authorities = ((UserDetails) principal).getAuthorities();
                return authorities.stream()
                        .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(UserRoles.ADMIN.name()));
            }
        }
        return false;
    }

    /**
     * Helper method to check if the authentication object is valid and authenticated.
     *
     * @param authentication the authentication object to check
     * @return true if authentication is valid and authenticated, false otherwise
     */
    private boolean isAuthenticated(Authentication authentication) {
        return authentication != null && authentication.isAuthenticated()
                && !(authentication.getPrincipal() instanceof String && "anonymousUser".equals(authentication.getPrincipal()));
    }
}
