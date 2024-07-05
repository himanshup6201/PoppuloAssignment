package com.poppulo.lottery.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Ticket {
    private Integer ticketId;
    private Integer lines;
    private Boolean checkedStatus;
    private List<List<Integer>> ticketData;
    private List<Integer> status;
}
