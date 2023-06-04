package ruben.SPM.service.Auth;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ruben.SPM.model.Entities.Watchlist;
import ruben.SPM.model.JWT_Token.Token;
import ruben.SPM.repository.TokenRepository.TokenRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;


    public List<Token> getAllTokens(){
        return this.tokenRepository.findAll();
    }


    public Token getToken(Integer id){
        return this.tokenRepository.findById(id).orElse(null);
    }

    public void changeStateToken(Integer id){
        Token token = this.getToken(id);
        token.setExpired(true);
        token.setRevoked(true);
        this.tokenRepository.update(token.getId(),
                token.getToken(),
                token.getTokenType(),
                token.isExpired(),
                token.isRevoked(),
                token.getUser());
    }

    public void deleteToken(Token token){
        this.tokenRepository.delete(token);
    }


    public void deleteTokensUser(Long id){
        List<Token> tokens = this.tokenRepository.findAll();

        for (Token t : tokens) {
            if (id == t.getUser().getId()){
                this.tokenRepository.delete(t);
            }
        }
    }
}
