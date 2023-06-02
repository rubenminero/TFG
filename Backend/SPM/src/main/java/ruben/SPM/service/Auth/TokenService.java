package ruben.SPM.service.Auth;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ruben.SPM.model.JWT_Token.Token;
import ruben.SPM.repository.TokenRepository.TokenRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;


    public void deleteTokensUser(Long id){
        List<Token> tokens = this.tokenRepository.findAll();

        for (Token t : tokens) {
            if (id == t.getUser().getId()){
                this.tokenRepository.delete(t);
            }
        }
    }
}
