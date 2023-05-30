package com.example.myapplication.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainDto {

    private long id;
    private String date;
    private String type;
    private String program;
    private String duration;
    private String health;
    private String introspection;
    private String weather;
    private String heartRateMorning;
    private String heartRateEvening;
    private String heartRateTrainMax;
    private String heartRateTrainMin;
    private long sleepTime;
    private int height;
    private int weight;
    private String comment;

}
