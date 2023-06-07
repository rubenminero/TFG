package ruben.SPM.model.DTO.Front;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class DeleteDisabledSummaryFrontDTO {
    private int users_disabled;
    private int tournaments_disabled;
    private int events_disabled;
    private int inscriptions_disabled;
    private int watchlists_disabled;
    private int tokens_disabled;
    private int sports_types_disabled;


    public DeleteDisabledSummaryFrontDTO(){
        this.users_disabled = 0;
        this.tournaments_disabled = 0;
        this.inscriptions_disabled = 0;
        this.watchlists_disabled = 0;
        this.tokens_disabled = 0;
        this.sports_types_disabled = 0;
    }

    public void setEventCount(int i) {
    }
}
