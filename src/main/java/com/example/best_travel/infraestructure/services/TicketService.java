package com.example.best_travel.infraestructure.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.UUID;


import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.best_travel.api.models.request.TicketRequest;
import com.example.best_travel.api.models.responses.FlyResponse;
import com.example.best_travel.api.models.responses.TicketResponse;
import com.example.best_travel.domain.entities.jpa.TicketEntity;
import com.example.best_travel.domain.repositories.jpa.CustomerRepository;
import com.example.best_travel.domain.repositories.jpa.FlyRepository;
import com.example.best_travel.domain.repositories.jpa.TicketRepository;
import com.example.best_travel.infraestructure.abstract_services.ITicketService;
import com.example.best_travel.infraestructure.helpers.CustomerHelper;
import com.example.best_travel.util.Tables;
import com.example.best_travel.util.exceptions.IdNotFoundException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Transactional
@Service
@Slf4j
@AllArgsConstructor
public class TicketService implements ITicketService {

    private final FlyRepository flyRepository;
    private final CustomerRepository customerRepository;
    private final TicketRepository ticketRepository;
    private final CustomerHelper customerHelper;


    @Override
    public TicketResponse create(TicketRequest request) {
        var fly = flyRepository.findById(request.getIdFly()).orElseThrow(()->new IdNotFoundException(Tables.fly.name()));
        var customer = customerRepository.findById(request.getIdClient()).orElseThrow(()->new IdNotFoundException(Tables.customer.name()));

        var ticketToPersist = TicketEntity.builder()
            .id(UUID.randomUUID())
            .fly(fly)
            .customer(customer)
            .price(fly.getPrice().add(fly.getPrice().multiply(charger_price_percentage)))
            .purchaseDate(LocalDate.now())
            .departureDate(LocalDateTime.now())
            .arrivalDate(LocalDateTime.now())
            .build();

         var ticketPersisted = this.ticketRepository.save(ticketToPersist);

         this.customerHelper.incrase(customer.getDni(), TicketService.class);
        
         return this.entityToResponse(ticketPersisted);
    }

    @Override
    public TicketResponse read(UUID id) {
        var ticketFromDB = this.ticketRepository.findById(id).orElseThrow(()->new IdNotFoundException(Tables.ticket.name()));
        return this.entityToResponse(ticketFromDB);
    }

    @Override
    public TicketResponse update(TicketRequest request, UUID id) {
        var ticketToUpdate = ticketRepository.findById(id).orElseThrow(()->new IdNotFoundException(Tables.ticket.name()));
        var fly = flyRepository.findById(request.getIdFly()).orElseThrow(()->new IdNotFoundException(Tables.fly.name()));
        
        ticketToUpdate.setFly(fly);
        ticketToUpdate.setPrice(fly.getPrice().add(fly.getPrice().multiply(charger_price_percentage)));
        ticketToUpdate.setArrivalDate(LocalDateTime.now());
        ticketToUpdate.setDepartureDate(LocalDateTime.now());

        var ticketUpdated = this.ticketRepository.save(ticketToUpdate);

        return this.entityToResponse(ticketUpdated);

    }

    @Override
    public void delete(UUID id) {
        var ticketToDelete = ticketRepository.findById(id).orElseThrow(()->new IdNotFoundException(Tables.ticket.name()));
        this.ticketRepository.delete(ticketToDelete);
    }

    @Override
    public BigDecimal findPrice(Long flyId) {
        var fly = this.flyRepository.findById(flyId).orElseThrow(()->new IdNotFoundException(Tables.fly.name()));
        return fly.getPrice().add(fly.getPrice().multiply(charger_price_percentage));    
    }

    private TicketResponse entityToResponse(TicketEntity entity) {
        var response = new TicketResponse();
        BeanUtils.copyProperties(entity, response);
        var flyResponse = new FlyResponse();
        BeanUtils.copyProperties(entity.getFly(), flyResponse);
        response.setFly(flyResponse);
        return response;
    }

    public final static BigDecimal charger_price_percentage = BigDecimal.valueOf(0.25);
    
}
