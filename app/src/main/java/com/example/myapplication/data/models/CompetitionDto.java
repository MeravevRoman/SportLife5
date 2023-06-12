package com.example.myapplication.data.models;

import java.util.Calendar;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompetitionDto {

    private long id;
    private String title;
    private String description;
    private Calendar date;
    private String place;
    private String anounce;
    private String result;

}
