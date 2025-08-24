package com.thinkIndia.backend.dto;


import com.thinkIndia.backend.entities.Events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEventDto {
    private Events eventList;
    private String teamName;
}
