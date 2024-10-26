package com.sp23.koifishproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "feeding_schedules")
public class FeedingSchedule {
    @Id
    private UUID id;

    // ManyToOne
    private UUID koiId;

    // Updated field to store multiple feeding times as strings
    private List<String> feedAt;

    private double foodAmount;
    private String foodType;
    private String note;

    // New field for CheckFeed references
    private List<UUID> checkFeedID;
}
