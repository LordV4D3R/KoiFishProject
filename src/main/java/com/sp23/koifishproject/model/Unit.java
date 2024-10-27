package com.sp23.koifishproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "units")
public class Unit {
    @Id
    private UUID id;

    // Many-to-One relationship
    private UUID measureData;

    private String unitName;
    private String unitFullName;
    private double unitValue;
    private String info;
    private double minValue;
    private double maxValue;
}
