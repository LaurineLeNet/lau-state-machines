package fr.llenet.engine;

import fr.llenet.engine.core.Action;
import fr.llenet.engine.core.Condition;
import fr.llenet.engine.core.PostAction;
import fr.llenet.engine.dao.EngineDao;
import fr.llenet.engine.definition.ProcessDefinition;
import fr.llenet.engine.helper.ProcessExecutionHelper;
import fr.llenet.engine.parameter.ParameterHelper;
import fr.llenet.engine.parameter.ParameterName;
import fr.llenet.engine.service.TransitionExecutionService;

import java.util.List;

public class EngineBuilder {
    private final ProcessExecutionHelper processExecutionHelper = new ProcessExecutionHelper();
    private final ParameterHelper parameterHelper = new ParameterHelper();
    private final TransitionExecutionService transitionExecutionService = new TransitionExecutionService(processExecutionHelper);
    private EngineDao engineDao;

    public EngineBuilder registerDefinitionImplementation(ProcessDefinition child) {
        processExecutionHelper.registerDefinitionImplementation(child);
        return this;
    }

    public EngineBuilder registerConditionImplementation(List<Condition> child) {
        processExecutionHelper.registerConditionImplementation(child);
        return this;
    }

    public EngineBuilder registerActionImplementation(List<Action> child) {
        processExecutionHelper.registerActionImplementation(child);
        return this;
    }

    public EngineBuilder registerPostActionImplementation(List<PostAction> child) {
        processExecutionHelper.registerPostActionImplementation(child);
        return this;
    }

    public EngineBuilder setEngineDao(EngineDao dao) {
        engineDao = dao;
        return this;
    }

    public Engine build() {
        if (engineDao == null) {
            throw new RuntimeException("No engineDao set, use setEngineDao to specify one");
        }
        return new EngineImpl(processExecutionHelper, parameterHelper, transitionExecutionService, engineDao);
    }
}
