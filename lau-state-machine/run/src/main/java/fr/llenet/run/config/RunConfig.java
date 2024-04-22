package fr.llenet.run.config;

import fr.llenet.engine.Engine;
import fr.llenet.engine.EngineBuilder;
import fr.llenet.engine.core.Action;
import fr.llenet.engine.core.Condition;
import fr.llenet.engine.core.PostAction;
import fr.llenet.engine.dao.EngineDao;
import fr.llenet.run.definition.ChildMouseDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RunConfig {
    @Autowired
    List<Condition> conditionImplementations;
    @Autowired
    List<Action> actionImplementations;
    @Autowired
    List<PostAction> postActionImplementations;
    @Bean
    public Engine registerDefinitionImplementation(ChildMouseDefinition childMouseDefinition, EngineDao engineDao) {
        return new EngineBuilder()
                .registerDefinitionImplementation(childMouseDefinition)
                .registerConditionImplementation(conditionImplementations)
                .registerActionImplementation(actionImplementations)
                .registerPostActionImplementation(postActionImplementations)
                .setEngineDao(engineDao)
                .build();
    }
}
