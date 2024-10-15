package com.sp23.koifishproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "koi_records")
public class KoiRecord {
    @Id
    private UUID id;

    // @ManyToOne
    private UUID koiId;

    // @ManyToOne
    private UUID developmentStageId;

    private double weight;
    private LocalDateTime recordOn;
    private double length;
    private Physique physique;

    public enum Physique {
        NORMAL,
        CORPULENT,
        SLIM
    }

}
