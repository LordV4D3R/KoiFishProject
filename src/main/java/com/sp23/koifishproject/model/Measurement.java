package com.sp23.koifishproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "measurements")
public class Measurement {
    @Id
    private UUID id;

    //ManyToOne
    private UUID pondId;

    //OneToMany
    private List<MeasureData> measureData;

    private LocalDateTime measureOn;
    private String note;
}
