package com.example.myapplication.data.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutItemDto {

    private String type; // обсудить все возможные варианты, составить список и переделать на enum
    private int value;

}
