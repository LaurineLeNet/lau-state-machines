package fr.llenet.run.dao;

import fr.llenet.run.model.ToothInventory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToothInventoryRepository extends MongoRepository<ToothInventory, String> {
    String TOOTH_REPOSITORY_ID = "65f6fc89a9c26b51f8603ab5";
}
