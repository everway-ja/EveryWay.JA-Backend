package everyway.everyway.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import everyway.everyway.models.tables.Language;
import org.springframework.lang.NonNull;
import java.util.List;

@Repository

public interface Languages_Repository extends JpaRepository<Language, Integer> {

    Language findById(int ID);
    
    List<Language> findByShortDescription(String shortDescription);
    List<Language> findByLongDescription(String longDescription);

    @NonNull List<Language> findAll();

}