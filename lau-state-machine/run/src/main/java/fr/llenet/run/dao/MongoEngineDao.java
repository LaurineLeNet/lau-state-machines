package fr.llenet.run.dao;

import fr.llenet.engine.dao.EngineDao;
import fr.llenet.engine.dao.ProcessedObjectExecution;
import fr.llenet.engine.definition.ProcessedObject;
import fr.llenet.run.model.FallenToothOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MongoEngineDao implements EngineDao {
    private final ProcessedObjectExecutionRepository objectExecutionRepository;
    private final FallenToothRepository objectRepository;
    @Override
    public void save(ProcessedObjectExecution processedObjectExecution) {
        objectExecutionRepository.save(processedObjectExecution);
    }

    @Override
    public Optional<ProcessedObjectExecution> getById(String processedObjectExecutionId) {
        return objectExecutionRepository.findById(processedObjectExecutionId);
    }

    @Override
    public <T extends ProcessedObject> T save(T processedObject) {
        return objectRepository.save(processedObject);
    }

//    @Override
//    public <T extends ProcessedObject> Optional<T> getById(String id) {
//        return objectRepository.getById(id);
//    }
}
