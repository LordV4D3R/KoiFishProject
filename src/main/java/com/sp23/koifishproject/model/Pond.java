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
@Table(name = "ponds")
public class Pond {
    @Id
    @GeneratedValue
    private UUID id;

    @OneToMany(mappedBy = "pond")
    private List<Koi> koi;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "pond")
    private List<Measurement> measurements;

    private String pondName;
    private int volume;
    private double depth;
    private int drainCount;
    private int skimmerCount;
    private int pumpCapacity;
    private String imgUrl;
    private status status;
    private boolean isQualified;

    public enum status {
        ACTIVE,
        INACTIVE
    }



}
