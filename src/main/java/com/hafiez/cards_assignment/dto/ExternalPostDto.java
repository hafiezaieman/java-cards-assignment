package com.hafiez.cards_assignment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExternalPostDto {
    private Integer userId;
    private Integer id;
    private String title;
    private String body;
}