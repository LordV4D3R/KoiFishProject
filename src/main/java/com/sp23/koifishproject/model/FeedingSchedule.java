package com.sp23.koifishproject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "feeding_schedules")
public class FeedingSchedule {
    @Id
    private UUID id;

    //ManyToOne
    private UUID koiId;

    private LocalDateTime fedding;
    private double foodAmount;


}
