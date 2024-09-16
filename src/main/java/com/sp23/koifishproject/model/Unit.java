package com.sp23.koifishproject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "units")
public class Unit {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToMany(mappedBy = "unit")
    private List<Measurement> measurement;

    private String unitName;
    private String unitFullName;
    private double unitValue;
    private String info;
    private double minValue;
    private double maxValue;


}
