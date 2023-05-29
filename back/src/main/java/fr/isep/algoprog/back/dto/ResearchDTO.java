package fr.isep.algoprog.back.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class ResearchDTO {

    private int stayDuration;
    private double transportSpeed;
    private int startDay;
    private int endDay;
    private int timeSpent;
    private String artworkType;

}
