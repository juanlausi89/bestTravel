package com.example.best_travel.api.controllers;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.best_travel.api.models.request.TicketRequest;
import com.example.best_travel.api.models.responses.TicketResponse;
import com.example.best_travel.infraestructure.abstract_services.ITicketService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "ticket")
@AllArgsConstructor
@Tag(name = "Ticket")
public class TicketController {
    
    private final ITicketService ticketService;

    @PostMapping
    public ResponseEntity<TicketResponse> post(@Valid @RequestBody TicketRequest request){
         return ResponseEntity.ok(ticketService.create(request));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<TicketResponse> get(@PathVariable UUID id){
        return ResponseEntity.ok(ticketService.read(id));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<TicketResponse> put(@Valid @PathVariable UUID id,@RequestBody TicketRequest request){
        return ResponseEntity.ok(this.ticketService.update(request, id));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        this.ticketService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Map<String,BigDecimal>> getFlyPrice(@RequestParam Long flyId){
        return ResponseEntity.ok(Collections.singletonMap("flyPrice",this.ticketService.findPrice(flyId)));
    }
}
