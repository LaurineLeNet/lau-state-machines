package fr.llenet.run.dao;

import fr.llenet.engine.dao.ProcessedObjectExecution;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessedObjectExecutionRepository extends MongoRepository<ProcessedObjectExecution, String> {
}
