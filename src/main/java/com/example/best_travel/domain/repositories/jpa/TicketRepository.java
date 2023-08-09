package com.example.best_travel.domain.repositories.jpa;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.example.best_travel.domain.entities.jpa.TicketEntity;

public interface TicketRepository extends CrudRepository<TicketEntity,UUID> {
    
}
