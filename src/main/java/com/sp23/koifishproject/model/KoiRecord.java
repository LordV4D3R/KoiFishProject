package com.sp23.koifishproject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "koi_records")
public class KoiRecord {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "koi_id")
    private Koi koi;

    @ManyToOne
    @JoinColumn(name = "development_stage_id")
    private DevelopmentStage developmentStage;

    private double weight;
    private LocalDateTime recordOn;
    private double length;
    private physique physique;

    public enum physique {
        NORMAL,
        CORPULENT,
        SLIM
    }

}
