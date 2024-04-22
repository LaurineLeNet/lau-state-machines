package fr.llenet.lautinystatemachine;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Log4j2
public class LauTinyStateMachineApplication {

    public static void main(String[] args) {
        Engine engine = new Engine();
        engine.launch();

    }


}
