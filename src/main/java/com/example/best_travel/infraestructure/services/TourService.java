package com.example.best_travel.infraestructure.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.best_travel.api.models.request.TourRequest;
import com.example.best_travel.api.models.responses.TourResponse;
import com.example.best_travel.domain.entities.jpa.FlyEntity;
import com.example.best_travel.domain.entities.jpa.HotelEntity;
import com.example.best_travel.domain.entities.jpa.ReservationEntity;
import com.example.best_travel.domain.entities.jpa.TicketEntity;
import com.example.best_travel.domain.entities.jpa.TourEntity;
import com.example.best_travel.domain.repositories.jpa.CustomerRepository;
import com.example.best_travel.domain.repositories.jpa.FlyRepository;
import com.example.best_travel.domain.repositories.jpa.HotelRepository;
import com.example.best_travel.domain.repositories.jpa.TourRepository;
import com.example.best_travel.infraestructure.abstract_services.ITourService;
import com.example.best_travel.infraestructure.helpers.CustomerHelper;
import com.example.best_travel.infraestructure.helpers.TourHelper;
import com.example.best_travel.util.Tables;
import com.example.best_travel.util.exceptions.IdNotFoundException;

import lombok.AllArgsConstructor;

@Transactional
@Service
@AllArgsConstructor
public class TourService implements ITourService {
        
    private final TourRepository tourRepository;
    private final FlyRepository flyRepository;
    private final HotelRepository hotelRepository;
    private final CustomerRepository customerRepository;
    private final TourHelper tourHelper;
    private final CustomerHelper customerHelper;
    

    @Override
    public TourResponse create(TourRequest request) {
       
        var customer = customerRepository.findById(request.getCustomerId()).orElseThrow(()->new IdNotFoundException(Tables.customer.name()));
        var flights = new HashSet<FlyEntity>();
        request.getFlights().forEach(fly -> flights.add(this.flyRepository.findById(fly.getId()).orElseThrow(()->new IdNotFoundException(Tables.fly.name()))));
        var hotels = new HashMap<HotelEntity, Integer>();
        request.getHotels().forEach(hotel -> hotels.put(this.hotelRepository.findById(hotel.getId()).orElseThrow(()->new IdNotFoundException(Tables.hotel.name())), hotel.getTotalDays()));

        var tourToSave = TourEntity.builder()
                .tickets(this.tourHelper.createTickets(flights, customer))
                .reservations(this.tourHelper.createReservations(hotels, customer))
                .customer(customer)
                .build();

        //Regresa una entidad
        var tourSaved = this.tourRepository.save(tourToSave);

        this.customerHelper.incrase(customer.getDni(), TourService.class);

        //Tengo que mapear la entidad, al tipo TourResponse
        return TourResponse.builder()
                .reservationIds(tourSaved.getReservations().stream().map(ReservationEntity::getId).collect(Collectors.toSet()))
                .ticketIds(tourSaved.getTickets().stream().map(TicketEntity::getId).collect(Collectors.toSet()))
                .id(tourSaved.getId())
                .build();
    }

    @Override
    public TourResponse read(Long id) {
        var tourFromDb = this.tourRepository.findById(id).orElseThrow(()->new IdNotFoundException(Tables.tour.name()));
        return TourResponse.builder()
                .reservationIds(tourFromDb.getReservations().stream().map(ReservationEntity::getId).collect(Collectors.toSet()))
                .ticketIds(tourFromDb.getTickets().stream().map(TicketEntity::getId).collect(Collectors.toSet()))
                .id(tourFromDb.getId())
                .build();
    }

    @Override
    public void delete(Long id) {
        var tourToDelete = this.tourRepository.findById(id).orElseThrow(()->new IdNotFoundException(Tables.tour.name()));
        this.tourRepository.delete(tourToDelete);
    }

    @Override
    public void removeTicket(Long tourId,UUID ticketId ) {
        var tourUpdate = this.tourRepository.findById(tourId).orElseThrow(()->new IdNotFoundException(Tables.tour.name()));
        tourUpdate.removeTicket(ticketId);
        this.tourRepository.save(tourUpdate);
    }

    @Override
    public UUID addTicket(Long flyId, Long tourId) {
        var tourUpdate = this.tourRepository.findById(tourId).orElseThrow(()->new IdNotFoundException(Tables.tour.name()));
        var fly = this.flyRepository.findById(flyId).orElseThrow(()->new IdNotFoundException(Tables.fly.name()));
        var ticket = this.tourHelper.createTicket(fly, tourUpdate.getCustomer());
        tourUpdate.addTicket(ticket);
        this.tourRepository.save(tourUpdate);
        return ticket.getId();
    }

    @Override
    public void removeReservation(Long tourId, UUID reservationId) {
        var tourUpdate = this.tourRepository.findById(tourId).orElseThrow(()->new IdNotFoundException(Tables.tour.name()));
        tourUpdate.removeReservation(reservationId);
        this.tourRepository.save(tourUpdate);
    }

    @Override
    public UUID addReservation(Long tourId, Long hotelId, Integer totalDays) {
        var tourUpdate = this.tourRepository.findById(tourId).orElseThrow(()->new IdNotFoundException(Tables.tour.name()));
        var hotel = this.hotelRepository.findById(hotelId).orElseThrow(()->new IdNotFoundException(Tables.hotel.name()));
        var reservation = this.tourHelper.createReservation(hotel, tourUpdate.getCustomer(), totalDays);
        tourUpdate.addReservation(reservation);
        this.tourRepository.save(tourUpdate);
        return reservation.getId();
    }
    
}
