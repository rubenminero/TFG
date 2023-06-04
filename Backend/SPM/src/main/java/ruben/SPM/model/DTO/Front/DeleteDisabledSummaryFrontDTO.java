package ruben.SPM.model.DTO.Front;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeleteDisabledSummaryFrontDTO {
    private int users_disabled;
    private int tournaments_disabled;
    private int inscriptions_disabled;
    private int watchlists_disabled;
    private int tokens_disabled;
    private int sports_types_disabled;

}
