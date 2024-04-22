package fr.llenet.lautinystatemachine;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Builder
public class ProcessExecutionContext {
    private String id;
    private String latestStateName;
    private Instant creationDate;
    private final List<Transition> transitions = new ArrayList<>();

    public String toString
            () {
        StringBuilder formatted = new StringBuilder(String.format("""
                                \n
                ProcessExecutionContext :
                    Execution Id: %s
                    Latest State: %s
                    Creation Date: %s
                    Transitions:
                                """.formatted(id, latestStateName, creationDate.toString())));

        for (int i = 0; i < transitions.size(); i++) {
            Transition transition = transitions.get(i);
            formatted.append("""
                    \t\t%d:
                    \t\t\tfrom state: %s
                    \t\t\tto state: %s
                    \t\t\twith action: %s
                                 """.formatted(i, transition.getFromState().getName(),
                    transition.getToState().getName(), transition.getAction()));
        }
        return formatted.toString();
    }
}
