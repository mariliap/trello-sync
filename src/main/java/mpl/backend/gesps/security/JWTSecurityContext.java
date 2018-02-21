package mpl.backend.gesps.security;

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;

public class JWTSecurityContext implements SecurityContext {

	private Principal principal;

	private SecurityContext contextoOriginal;

	public JWTSecurityContext(SecurityContext contextoOriginal, String usuario) {
		this.contextoOriginal = contextoOriginal;
		principal = new Principal() {
			@Override
			public String getName() {
				return usuario;
			}
		};
	}

	@Override
	public String getAuthenticationScheme() {
		return contextoOriginal.getAuthenticationScheme();
	}

	@Override
	public boolean isUserInRole(String arg0) {
		return false;
	}

	@Override
	public Principal getUserPrincipal() {
		return principal;
	}

	@Override
	public boolean isSecure() {
		return contextoOriginal.isSecure();
	}

}
