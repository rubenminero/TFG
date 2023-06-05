package ruben.SPM.repository.TokenRepository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ruben.SPM.model.Entities.User;
import ruben.SPM.model.JWT_Token.Token;
import ruben.SPM.model.JWT_Token.TokenType;
import ruben.SPM.model.Whitelist.Role;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {



    Optional<Token> findByToken(String token);

    @Modifying
    @Transactional
    @Query("update Token a set  a.token = ?2, a.tokenType = ?3, a.expired = ?4, a.revoked = ?5, a.user = ?6 where a.id = ?1")
    void update(Integer id,
                String token,
                TokenType tokenType,
                Boolean expired,
                Boolean revoked,
                User user);
}
