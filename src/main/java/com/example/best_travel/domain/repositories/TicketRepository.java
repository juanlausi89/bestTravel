package com.example.best_travel.domain.repositories;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.example.best_travel.domain.entities.TicketEntity;

public interface TicketRepository extends CrudRepository<TicketEntity,UUID> {
    
}
