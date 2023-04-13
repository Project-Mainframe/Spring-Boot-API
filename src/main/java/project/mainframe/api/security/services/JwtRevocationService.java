package project.mainframe.api.security.services;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import project.mainframe.api.security.entities.Revocation;
import project.mainframe.api.security.repositories.RevocationRepository;

@Service
public class JwtRevocationService {
    
    /*
     * The repository for revocations.
     */
    private RevocationRepository revocationRepository;    

    public JwtRevocationService(RevocationRepository revocationRepository) {
        this.revocationRepository = revocationRepository;        
    }

    /*
     * Revoke a token.
     * 
     * @param token The token to revoke
     * @param until The time until the token is revoked
     */
    public void revoke(String token, LocalDateTime until) {        
        revocationRepository.save(new Revocation(token, until));
    }    

    /*
     * Clear all expired revocations.
     */
    public void clearExpired() {
        revocationRepository.deleteByUntilBefore(LocalDateTime.now());
    }

    /*
     * Check if a token is revoked.
     * 
     * @param token The token to check
     */
    public boolean isRevoked(String token) {
        return revocationRepository.existsById(token);
    }
}
