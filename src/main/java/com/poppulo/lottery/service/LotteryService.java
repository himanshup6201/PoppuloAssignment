package com.poppulo.lottery.service;

import com.poppulo.lottery.exception.AlreadyStatusCheckedException;
import com.poppulo.lottery.exception.EmptyResultException;
import com.poppulo.lottery.model.Ticket;
import com.poppulo.lottery.model.TicketDTO;
import com.poppulo.lottery.repository.LotteryRepository;
import com.poppulo.lottery.util.TicketIdGenerator;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LotteryService {

    private final Integer DEFAULT_NO_OF_LINES_IN_TICKET = 3;
    private final Integer DEFAULT_MAX_RANGE = 2;
    private final Integer DEFAULT_MIN_RANGE = 0;

    private final LotteryRepository lotteryRepository;

    private final ModelMapper modelMapper;

    private final TicketIdGenerator ticketCounter;

    public LotteryService(LotteryRepository lotteryRepository, ModelMapper modelMapper, TicketIdGenerator ticketCounter) {
        this.lotteryRepository = lotteryRepository;
        this.modelMapper = modelMapper;
        this.ticketCounter = ticketCounter;
    }

    public TicketDTO createLotteryTicket() {
        Ticket ticket = buildTicket();
        this.lotteryRepository.createTicket(ticket);
        TicketDTO ticketDTO = this.modelMapper.map(ticket, TicketDTO.class);
        return ticketDTO;
    }

    public List<TicketDTO> getTickets() {
        List<Ticket> tickets = this.lotteryRepository.getTickets();
        if(tickets.isEmpty()){
            throw new EmptyResultException("No Tickets found", "103");
        }
        List<TicketDTO> ticketsDTO = tickets.stream().map(ticket -> {
            TicketDTO ticketDTO = this.modelMapper.map(ticket, TicketDTO.class);
            return ticketDTO;
        }).collect(Collectors.toList());
        return ticketsDTO;
    }

    public TicketDTO getTicketsById(Integer id) {
        Ticket ticket = this.lotteryRepository.getTicketsById(id);
        TicketDTO ticketDTO = this.modelMapper.map(ticket, TicketDTO.class);
        return ticketDTO;

    }

    public List<Integer> retrieveStatusOfTicket(Integer id) {
        Ticket existingTicket = this.lotteryRepository.getTicketsById(id);
        existingTicket.setCheckedStatus(true);
        this.lotteryRepository.updateTicket(id, existingTicket);
        List<Integer> status = this.lotteryRepository.retrieveStatusOfTicket(id);
        Collections.sort(status);
        return status;
    }

    public TicketDTO amendLines(Integer id) {
        Ticket existingTicket = this.lotteryRepository.getTicketsById(id);
        if (existingTicket.getCheckedStatus().equals(Boolean.TRUE)) {
            throw new AlreadyStatusCheckedException("Cannot amend lines as status is already checked", "104");
        }
        Ticket newTicket = updateExistingTicket(existingTicket);
        Ticket ticket = this.lotteryRepository.updateTicket(id, newTicket);
        TicketDTO ticketDTO = this.modelMapper.map(ticket, TicketDTO.class);
        return ticketDTO;
    }

    private Ticket updateExistingTicket(Ticket existingTicket) {
        int line = existingTicket.getLines();
        List<List<Integer>> existingTicketData = existingTicket.getTicketData();
        List<Integer> amendTicketData = buildTicketData(1).get(0);
        existingTicketData.add(amendTicketData);
        List<Integer> newStatus = calculateStatus(existingTicketData);
        existingTicket.setLines(line + 1);
        existingTicket.setTicketData(existingTicketData);
        existingTicket.setStatus(newStatus);
        return existingTicket;
    }

    private Ticket buildTicket() {
        List<List<Integer>> ticketData;
        do {
            ticketData = buildTicketData(DEFAULT_NO_OF_LINES_IN_TICKET);
        } while (checkTicketExistence(ticketCounter.getId(ticketData)));
        List<Integer> ticketStatus = calculateStatus(ticketData);
        return Ticket.builder()
                .ticketId(ticketCounter.getId(ticketData)).ticketData(ticketData).checkedStatus(Boolean.FALSE)
                .lines(DEFAULT_NO_OF_LINES_IN_TICKET).status(ticketStatus)
                .build();
    }

    private boolean checkTicketExistence(int id) {
        return this.lotteryRepository.checkTicketExistence(id);
    }

    private List<Integer> calculateStatus(List<List<Integer>> ticketData) {
        List<Integer> ticketStatus = new ArrayList<>();
        for (List<Integer> row : ticketData) {
            int rowStatus = scoreCalculator(row.get(0), row.get(1), row.get(2));
            ticketStatus.add(rowStatus);
        }
        return ticketStatus;
    }

    private int scoreCalculator(int a, int b, int c) {
        if ((a + b + c) == 2)
            return 10;
        if (a == b && b == c)
            return 5;
        if (a != b && a != c)
            return 1;
        else
            return 0;
    }

    private List<List<Integer>> buildTicketData(int n) {
        List<List<Integer>> ticketData = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            List<Integer> ticketRow = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                int range = DEFAULT_MAX_RANGE - DEFAULT_MIN_RANGE + 1;
                int rand = (int) (Math.random() * range) + DEFAULT_MIN_RANGE;
                ticketRow.add(rand);
            }
            ticketData.add(ticketRow);
        }
        return ticketData;
    }
}
