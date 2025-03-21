package everyway.everyway.models.tables;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "itinerarycategories")

public class ItineraryCategory {
    
    @Id @Column(name="ID") @GeneratedValue(strategy = GenerationType.IDENTITY) private int id;
    @Column(name="description") private String description;
    @OneToOne @JoinColumn(name="id_image") private Image associatedImage;

}
