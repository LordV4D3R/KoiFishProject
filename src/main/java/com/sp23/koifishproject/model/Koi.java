package com.sp23.koifishproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "kois")
public class Koi {
    @Id
    private UUID id;

    //ManyToOne
    private UUID pondId;

    //OneToMany
    private List<UUID> koiRecords = new ArrayList<>(); // Không sử dụng @DBRef

    //OneToMany
    private List<UUID> feedingSchedules = new ArrayList<>();

    private String name;
    private int age;
    private Sex sex;
    private String category;
    private LocalDate inPondSince;
    private double purchasePrice;
    private Status status;
    private String imgUrl;
    private String origin;
    private String breed;

    public enum Status {
        ACTIVE,
        DECEASED,
    }

    public enum Sex {
        FEMALE,
        MALE,
        NOT_SPECIFIED
    }
}

