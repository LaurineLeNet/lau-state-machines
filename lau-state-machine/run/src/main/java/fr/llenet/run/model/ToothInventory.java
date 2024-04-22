package fr.llenet.run.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Builder
@Setter
@Document("toothInventory")
@NoArgsConstructor
@AllArgsConstructor
public class ToothInventory {
    private String id;
    private int teethInTrash;
    private int teethForCastle;
}
