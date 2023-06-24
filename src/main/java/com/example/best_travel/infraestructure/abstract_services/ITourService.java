package com.example.best_travel.infraestructure.abstract_services;

import java.util.UUID;

import com.example.best_travel.api.models.request.TourRequest;
import com.example.best_travel.api.models.responses.TourResponse;

public interface ITourService extends SimpleCrudService<TourRequest,TourResponse,Long> {
    
    void removeTicket(Long tourId, UUID ticketId );
    
    UUID addTicket(Long flyId, Long tourId);

    void removeReservation(Long tourId, UUID reservationId );
    
    UUID addReservation(Long tourId, Long hotelId, Integer totalDays);

}
