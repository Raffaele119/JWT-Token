package it.html.exercises.api;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class JwtFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String path = requestContext.getUriInfo().getPath(); 
        String method = requestContext.getMethod(); 

        if (path.endsWith("login")) {
            return;
        }

        if (method.equals("GET") && (path.equals("exercises") || path.matches("exercises/\\d+"))) {
            return;
        }

        String token = null;
        String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring("Bearer ".length()).trim();
        } else {
            token = requestContext.getUriInfo().getQueryParameters().getFirst("token");
        }

        if (token == null || token.isEmpty()) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Token mancante o malformato").build());
            return;
        }

        try {
            Jws<Claims> claims = JwtUtil.validateToken(token);
            String username = claims.getBody().getSubject();
            requestContext.setSecurityContext(new Authorizer(username));
        } catch (JwtException e) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Token non valido o scaduto").build());
        }
    }
}
