package com.sp23.koifishproject.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "measure_data")
public class MeasureData {
    @Id
    private UUID id;

    //ManyToOne
    private UUID measurementId;

    //ManyToOne
    private UUID unitId;

    private double volume;
}
