package com.sp23.koifishproject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "measurements")
public class Measurement {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "Pond_id")
    private Pond pond;

    @ManyToMany
    @JoinTable(
            name = "measurement_unit",
            joinColumns = @JoinColumn(name = "measurement_id"),
            inverseJoinColumns = @JoinColumn(name = "unit_id")
    )
    private List<Unit> unit;


    private LocalDateTime measureOn;
    private String note;
}
