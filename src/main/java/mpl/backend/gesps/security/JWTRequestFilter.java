package mpl.backend.gesps.security;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import org.jose4j.jwt.consumer.InvalidJwtException;

@Provider
@JWTRequired
@Priority(Priorities.AUTHENTICATION)
public class JWTRequestFilter implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {

		if (!requestContext.getUriInfo().getPath().endsWith("/login")) {
			try {
				criarContextoSeguranca(requestContext);
			} catch (InvalidJwtException e) {
				//requestContext.setProperty("auth-failed", true);
				requestContext.abortWith(
					Response.status(Response.Status.UNAUTHORIZED)
						.entity("Token de acesso inv�lido")
						.build()
				);
			}
		}
	}

	private void criarContextoSeguranca(ContainerRequestContext requestContext) throws InvalidJwtException {
		String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			throw new InvalidJwtException(null, null, null);
		}

		String token = authorizationHeader.substring("Bearer".length()).trim();
		String usuario = JWTUtil.validar(token);
		SecurityContext securityContext = requestContext.getSecurityContext();
		requestContext.setSecurityContext(new JWTSecurityContext(securityContext, usuario));
	}

}