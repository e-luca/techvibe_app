package com.projects.techvibe.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import java.security.Key

@Service
class JwtService {

    companion object {
        private const val SECRET_KEY = "7B5F2F1A1656C3FE3198E16BD2288"
    }

    fun extractUsername(jwtToken: String): String {
        return extractClaim(jwtToken, Claims::getSubject)
    }

    fun <T> extractClaim(token: String, claimResolver: (Claims) -> T): T {
        val claims = extractAllClaims(token)

        return claimResolver(claims)
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts
            .parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .body
    }

    private fun getSigningKey(): Key {
        val keyBytes = Decoders.BASE64.decode(SECRET_KEY)

        return Keys.hmacShaKeyFor(keyBytes)
    }
}
