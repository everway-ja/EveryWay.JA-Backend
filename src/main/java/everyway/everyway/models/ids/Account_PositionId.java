package everyway.everyway.models.ids;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

public class Account_PositionId implements Serializable {

    private int associatedAccount;
    private int associatedPosition;
    private LocalDateTime creationTimestamp;

}
