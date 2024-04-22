package fr.llenet.engine.core.impl;

import fr.llenet.engine.context.ProcessExecutionContext;
import fr.llenet.engine.core.Action;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class VoidAction implements Action {
    @Override
    public void execute(ProcessExecutionContext context) {
        log.info("[EmptyAction] nothing to execute");
    }
}
