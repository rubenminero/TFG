package ruben.SPM.model.JWT_Token;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ruben.SPM.model.DTO.Entities.TokenDTO;
import ruben.SPM.model.Entities.User;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Token {

    @Id
    @GeneratedValue
    public Integer id;

    @Column(unique = true)
    public String token;

    @Enumerated(EnumType.STRING)
    public TokenType tokenType = TokenType.BEARER;

    public boolean revoked;

    public boolean expired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User user;

    public Token(TokenDTO tokenDTO){
        this.id = tokenDTO.getId();
        this.token = tokenDTO.getToken();
        this.tokenType = tokenDTO.getTokenType();
        this.expired = tokenDTO.getExpired();
        this.revoked = tokenDTO.getRevoked();
    }
}