package com.sp23.koifishproject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "ponds")
public class Pond {
    @Id
    private UUID id;

    //OneToMany
    private List<Koi> koi;

    //ManyToOne
    private UUID userId;

    //OneToMany
    private List<Measurement> measurements;

    private String pondName;
    private int volume;
    private double depth;
    private int drainCount;
    private int skimmerCount;
    private int pumpCapacity;
    private String imgUrl;
    private Status status;
    private boolean isQualified;

    public enum Status {
        ACTIVE,
        INACTIVE
    }



}
