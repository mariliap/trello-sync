package mpl.backend.gesps.security;

import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;


public class JWTUtil {

	private JWTUtil() {
	}

	private static RsaJsonWebKey chave;

	private static synchronized void criarChave() {
		try {
			if (chave == null) {
				chave = RsaJwkGenerator.generateJwk(2048);
			}
		} catch (JoseException ex) {
			ex.printStackTrace();
		}
	}
	
	public static RsaJsonWebKey getChave() {
		if (chave == null) {
			criarChave();
		}
		return chave;
	}
	
	public static String criar(String usuario) {
		RsaJsonWebKey rsaJsonWebKey = JWTUtil.getChave();
		
		JwtClaims claims = new JwtClaims();
		claims.setSubject(usuario);
		claims.setIssuedAtToNow();
		claims.setGeneratedJwtId();
		claims.setNotBeforeMinutesInThePast(1);
		claims.setExpirationTimeMinutesInTheFuture(10);

		JsonWebSignature jws = new JsonWebSignature();
		jws.setPayload(claims.toJson());
		jws.setKey(rsaJsonWebKey.getPrivateKey());
		jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);

		String jwt = null;
		try {
			jwt = jws.getCompactSerialization();
		} catch (JoseException ex) {
			ex.printStackTrace();
		}

		return jwt;
	}
	
	public static String validar(String jwt) throws InvalidJwtException {
		RsaJsonWebKey rsaJsonWebKey = getChave();

		JwtConsumer jwtConsumer = new JwtConsumerBuilder()
			.setRequireJwtId()
			.setRequireSubject()
			.setRequireNotBefore()
			.setRequireExpirationTime()
			.setVerificationKey(rsaJsonWebKey.getKey())
			.build();

		// Validate the JWT and process it to the Claims
		JwtClaims jwtClaims = jwtConsumer.processToClaims(jwt);

		return (String) jwtClaims.getClaimValue("sub");
	}
}