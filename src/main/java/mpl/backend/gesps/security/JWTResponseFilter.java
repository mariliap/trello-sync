package mpl.backend.gesps.security;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

@Provider
@JWTRequired
@Priority(Priorities.AUTHENTICATION)
public class JWTResponseFilter implements ContainerResponseFilter {

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		
		SecurityContext securityContext = requestContext.getSecurityContext();
		Principal usuario = securityContext.getUserPrincipal();
		
		// requestContext.getProperty("auth-failed") != null || 
		if (usuario == null) {
			// Autenticacao falhou, nao e necessario gerar JWT
			return;
		}

		String jwt = JWTUtil.criar(usuario.getName());
		List<Object> jwtList = Arrays.asList(jwt);
		responseContext.getHeaders().put("jwt", jwtList);
	}

}
