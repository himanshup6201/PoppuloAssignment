package com.poppulo.lottery.controller;

import com.poppulo.lottery.model.TicketDTO;
import com.poppulo.lottery.service.LotteryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/lottery")
public class LotteryController {
    private final LotteryService lotteryService;

    public LotteryController(LotteryService lotteryService) {
        this.lotteryService = lotteryService;
    }

    @PostMapping("/ticket")
    public ResponseEntity<TicketDTO> createLotteryTicket() {
        TicketDTO ticket = this.lotteryService.createLotteryTicket();
        return new ResponseEntity<>(ticket, HttpStatus.CREATED);
    }

    @GetMapping("ticket")
    public ResponseEntity<List<TicketDTO>> getTickets() {
        List<TicketDTO> tickets = this.lotteryService.getTickets();
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    @GetMapping("ticket/{id}")
    public ResponseEntity<TicketDTO> getTickets(@PathVariable Integer id) {
        TicketDTO ticket = this.lotteryService.getTicketsById(id);
        return new ResponseEntity<>(ticket, HttpStatus.OK);
    }

    @PutMapping("/ticket/status/{id}")
    public ResponseEntity<List<Integer>> retrieveStatusOfTicket(@PathVariable Integer id) {
        List<Integer> status = this.lotteryService.retrieveStatusOfTicket(id);
        return new ResponseEntity<>(status, HttpStatus.CREATED);
    }

    @PutMapping("/ticket/{id}")
    public ResponseEntity<TicketDTO> amendLines(@PathVariable Integer id) {
        TicketDTO ticket = this.lotteryService.amendLines(id);
        return new ResponseEntity<>(ticket, HttpStatus.CREATED);
    }
}
