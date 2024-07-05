package com.poppulo.lottery.util;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TicketIdGenerator {
    public int getId(List<List<Integer>> ticketData) {
        return Math.abs(ticketData.toString().hashCode());
    }
}
