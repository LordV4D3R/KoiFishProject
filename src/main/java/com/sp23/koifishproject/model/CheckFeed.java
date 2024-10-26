package com.sp23.koifishproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "check_feeds")
public class CheckFeed {
    @Id
    private UUID id;

    // Liên kết trực tiếp với ID của FeedingSchedule
    private UUID feedingScheduleId;

    private boolean status;
    private LocalDateTime timeCheck;
}
