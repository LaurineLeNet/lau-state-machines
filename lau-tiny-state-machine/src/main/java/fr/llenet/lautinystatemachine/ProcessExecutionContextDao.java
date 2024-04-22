package fr.llenet.lautinystatemachine;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ProcessExecutionContextDao {
    public void save(ProcessExecutionContext context) {
        int range = (100) + 1;
        context.setId(String.valueOf((int) (range * Math.random())));
        log.info(context.toString());
    }
}
