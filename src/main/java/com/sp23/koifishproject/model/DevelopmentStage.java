package com.sp23.koifishproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "development_stages")
public class DevelopmentStage {
    @Id
    private UUID id;
    private String stageName;
    private double requiredFoodAmount;

    //OneToMany
    @DBRef
    private List<KoiRecord> koiRecords;
}
