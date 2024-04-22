package fr.llenet.run.dao;

import fr.llenet.engine.definition.ProcessedObject;
import fr.llenet.run.model.FallenToothOrder;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FallenToothRepository extends MongoRepository<FallenToothOrder, String> {
    <T extends ProcessedObject> T save(T object);
    <T extends ProcessedObject> T getById(String id);
}
