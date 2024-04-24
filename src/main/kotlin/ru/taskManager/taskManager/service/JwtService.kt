package ru.taskManager.taskManager.service


import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import ru.taskManager.taskManager.entity.user.User
import java.security.Key
import java.util.*
import java.util.function.Function


@Service
class JwtService {
    @Value("\${application.security.jwt.secret-key}")
    private val secretKey: String? = null

    @Value("\${application.security.jwt.expiration}")
    val expirationTime: Long = 0

    fun extractUsername(token: String?): String {
        return extractClaim(token) { obj: Claims -> obj.subject }
    }

    fun <T> extractClaim(token: String?, claimsResolver: Function<Claims, T>): T {
        val claims = extractAllClaims(token)
        return claimsResolver.apply(claims)
    }

    fun generateToken(userDetails: User): String {
        return generateToken(HashMap(), userDetails)
    }

    fun generateToken(extraClaims: Map<String?, Any?>, userDetails: User): String {
        return buildToken(extraClaims, userDetails, expirationTime)
    }

    private fun buildToken(
        extraClaims: Map<String?, Any?>,
        userDetails: User,
        expiration: Long
    ): String {
        return Jwts
            .builder()
            .setClaims(extraClaims)
            .setSubject(userDetails.nickname)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + expiration))
            .signWith(signInKey, SignatureAlgorithm.HS256)
            .compact()
    }

    fun isTokenValid(token: String?, userDetails: User): Boolean {
        val username = extractUsername(token)
        return username == userDetails.nickname && !isTokenExpired(token)
    }

    private fun isTokenExpired(token: String?): Boolean {
        return extractExpiration(token).before(Date())
    }

    private fun extractExpiration(token: String?): Date {
        return extractClaim(token) { obj: Claims -> obj.expiration }
    }

    private fun extractAllClaims(token: String?): Claims {
        return Jwts
            .parserBuilder()
            .setSigningKey(signInKey)
            .build()
            .parseClaimsJws(token)
            .body
    }

    private val signInKey: Key
        private get() {
            val keyBytes = Decoders.BASE64.decode(secretKey)
            return Keys.hmacShaKeyFor(keyBytes)
        }
}

