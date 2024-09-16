package com.sp23.koifishproject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "kois")
public class Koi {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "pond_id")
    private Pond pond;

    @OneToMany(mappedBy = "koi")
    private List<KoiRecord> koiRecords;

    @OneToMany(mappedBy = "koi")
    private List<FeedingSchedule> feedingSchedules;


    private String name;
    private int age;
    private sex sex;
    private String category;
    private LocalDate inPondSince;
    private double purchasePrice;
    private status status;
    private String imgUrl;
    private String origin;
    private String breed;

    public enum status {
        ACTIVE,
        DECEASED,
    }

    public enum sex {
        FEMALE,
        MALE,
        NOT_SPECIFIED
    }



}
