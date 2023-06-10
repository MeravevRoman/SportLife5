package com.example.myapplication.ui.calendar;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalendarEvent {

    private String uid;
    private String date;
    private String title;
    private String event;

}
