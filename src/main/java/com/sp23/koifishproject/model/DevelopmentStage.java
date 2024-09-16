package com.sp23.koifishproject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "development_stages")
public class DevelopmentStage {
    @Id
    @GeneratedValue
    private UUID id;

    @OneToMany(mappedBy = "developmentStage")
    private List<KoiRecord> koiRecords;

    private String stageName;
    private double requiredFoodAmount;
}
