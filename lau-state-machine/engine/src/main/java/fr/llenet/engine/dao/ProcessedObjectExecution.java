package fr.llenet.engine.dao;

import fr.llenet.engine.context.ProcessStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Builder
@Setter
@Getter
public class ProcessedObjectExecution {
    private String id;
    private String latestStateName;
    private Map<String, Object> parameters;
    private String errorMessage;
    private ProcessStatus status;
    private List<ProcessedObjectTransition> transitions;
    private Instant creationDate;
    private Instant lastUpdateDate;
    private String lastModifiedBy;
    private Boolean pauseAfterTransition;
}
