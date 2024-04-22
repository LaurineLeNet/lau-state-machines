package fr.llenet.engine.dao;

import fr.llenet.engine.definition.ProcessedObject;

import java.util.Optional;

public interface EngineDao {
    void save(ProcessedObjectExecution processedObjectExecution);
    Optional<ProcessedObjectExecution> getById(String processedObjectExecutionId);
    <T extends ProcessedObject> T save(T  processedObject);
    //<T extends ProcessedObject> Optional<T> getById(String id);
}
