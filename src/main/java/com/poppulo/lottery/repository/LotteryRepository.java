package com.poppulo.lottery.repository;


import com.poppulo.lottery.exception.LotteryNotFoundException;
import com.poppulo.lottery.model.Ticket;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class LotteryRepository {
    HashMap<Integer, Ticket> ticketHashMap = new HashMap<>();

    public void createTicket(Ticket ticket) {
        ticketHashMap.put(ticket.getTicketId(), ticket);
    }

    public List<Ticket> getTickets() {
        return ticketHashMap.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
    }

    public Ticket getTicketsById(Integer id) {
        return Optional.ofNullable(ticketHashMap.get(id)).orElseThrow(()->
                new LotteryNotFoundException(String.format("Lottery with id %s does not exist", id), "101"));
    }

    public List<Integer> retrieveStatusOfTicket(Integer id) {
        return ticketHashMap.get(id).getStatus();
    }

    public Ticket updateTicket(Integer id, Ticket existingTicket) {
        ticketHashMap.put(id, existingTicket);
        return ticketHashMap.get(id);
    }

    public boolean checkTicketExistence(int id) {
        return this.ticketHashMap.containsKey(id);
    }
}
