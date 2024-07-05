package com.poppulo.lottery.model;

import lombok.Data;

import java.util.List;

@Data
public class TicketDTO {
    private Integer ticketId;
    private List<List<Integer>> ticketData;
}
