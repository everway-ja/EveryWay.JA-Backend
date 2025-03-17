package packet.model.tables;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import packet.model.ids.AccountCategory_LocationReportId;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@IdClass(AccountCategory_LocationReportId.class)
@Table(name = "accountcategories_locationreports")
public class AccountCategory_LocationReport {
    
    @Id @ManyToOne @JoinColumn(name="id_accountcategory") private AccountCategory associatedAccountCategory;
    @Id @ManyToOne @JoinColumn(name="id_locationreport") private LocationReport associatedLocationReport;

}
