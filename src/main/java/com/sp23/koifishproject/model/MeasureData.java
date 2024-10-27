package com.sp23.koifishproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "measure_data")
public class MeasureData {
    @Id
    private UUID id;

    // Many-to-One relationship
    private UUID measurementId;

    // One-to-Many relationship
    private List<UUID> unitIds= new ArrayList<>();

    private double volume;
}
