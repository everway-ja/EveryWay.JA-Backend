package packet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import packet.model.tables.Image;
import java.util.List;

@Repository

public interface Images_Repository extends JpaRepository<Image, Integer> {

    Image findById(int id);

    List<Image> findByImage(String image);
    List<Image> findByDescription(String description);

}