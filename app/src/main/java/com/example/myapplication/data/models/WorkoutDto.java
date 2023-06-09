package com.example.myapplication.data.models;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutDto {

    private int id;
    private Date workoutDate;
    private String workoutTitle;
    private List<WorkoutItemDto> items;

}
