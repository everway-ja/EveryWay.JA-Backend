package packet.model.tables;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import packet.model.ids.Image_LocationId;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@IdClass(Image_LocationId.class)
@Table(name = "images_locations")

public class Image_Location {

    @Id @ManyToOne @JoinColumn(name="id_image") private Image associatedImage;
    @Id @ManyToOne @JoinColumn(name="id_itinerary") private Location associatedLocation;

}
