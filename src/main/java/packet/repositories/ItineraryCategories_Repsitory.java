package packet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import packet.model.tables.ItineraryCategory;
import java.util.List;
import org.springframework.lang.NonNull;

@Repository

public interface ItineraryCategories_Repsitory extends JpaRepository<ItineraryCategory, Integer> {

    ItineraryCategory findById(int id);
    
    List<ItineraryCategory> findByDescription(String description);

    @NonNull List<ItineraryCategory> findAll();

}