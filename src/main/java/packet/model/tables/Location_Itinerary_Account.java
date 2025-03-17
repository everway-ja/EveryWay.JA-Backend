package packet.model.tables;

import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.ManyToOne;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "locations&itineraries_accounts")
public class Location_Itinerary_Account {
    
    @Id @ManyToOne @JoinColumn(name="id_location") private Location associatedLocation;
    @Id @ManyToOne @JoinColumn(name="id_itinerary") private Itinerary associatedItinerary;
    @Id @ManyToOne @JoinColumn(name="id_account") private Account associatedAccount;
    @CreationTimestamp @Column(name="creation_timestamp") private LocalDateTime creation_timestamp;
    
}
