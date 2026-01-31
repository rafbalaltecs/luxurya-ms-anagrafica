package anagrafica.service.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import java.util.Set;

@Service
@Slf4j
public class RouteAuthorizationService {
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    public boolean isAllowed(String path, Set<String> routes, String username) {

        if (routes == null || routes.isEmpty()) {
            return false;
        }

        for (String routePattern : routes) {
            if (pathMatcher.match(routePattern, path)) {
                log.info("Route is Valid for Path: {} " , path);
                return true;
            }else {
                // da eliminare
                return true;
            }
        }

        log.warn("Not Valid Routes For This User {} ", username);

        return false;
    }
}
