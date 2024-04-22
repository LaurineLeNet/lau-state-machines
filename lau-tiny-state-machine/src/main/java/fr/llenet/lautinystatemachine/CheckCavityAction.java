package fr.llenet.lautinystatemachine;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class CheckCavityAction implements Action
{
    @Override
    public void run() {
        log.info("Check cavity");
    }
}
