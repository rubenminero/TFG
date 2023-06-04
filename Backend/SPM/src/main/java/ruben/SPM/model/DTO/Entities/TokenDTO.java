package ruben.SPM.model.DTO.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import ruben.SPM.model.Entities.Organizer;
import ruben.SPM.model.Entities.Sports_type;
import ruben.SPM.model.Entities.Tournament;
import ruben.SPM.model.JWT_Token.Token;
import ruben.SPM.model.JWT_Token.TokenType;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class TokenDTO {

    private Integer id;
    private String token;
    private TokenType tokenType;
    private Boolean expired;
    private Boolean revoked;

    public TokenDTO(Token token) {
        this.id = token.getId();
        this.token = token.getToken();
        this.tokenType = token.getTokenType();
        this.expired = token.isExpired();
        this.revoked = token.isRevoked();
    }

    public TokenDTO() {

    }

    public static TokenDTO fromToken(Token token) {
        return new TokenDTO(token);
    }

    public static List<TokenDTO> fromTokens(List<Token> tokens) {
        return tokens.stream().map(TokenDTO::fromToken).collect(Collectors.toList());
    }

    public static Token toToken(TokenDTO tokenDTO) {
        return new Token(tokenDTO);
    }
}
