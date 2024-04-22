package fr.llenet.run.model;

import fr.llenet.engine.definition.ProcessedObject;
import lombok.*;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Builder
@Setter
@Document("processedObject")
@TypeAlias("fallenToothOrder")
@NoArgsConstructor
@AllArgsConstructor
public class FallenToothOrder implements ProcessedObject {
    private String id;
    private String childName;
    private Boolean hasCavity;
    private int moneyAmount;
}
