package fr.llenet.run.controller;

import fr.llenet.engine.dao.ProcessedObjectExecution;
import fr.llenet.run.dao.ProcessedObjectExecutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("processExecution")
@CrossOrigin
public class GetterController {
    private final ProcessedObjectExecutionRepository processedObjectExecutionRepository;

    @GetMapping
    public List<ProcessedObjectExecution> getAll() {
        return processedObjectExecutionRepository.findAll();
    }

}
