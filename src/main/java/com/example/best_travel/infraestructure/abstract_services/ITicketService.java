package com.example.best_travel.infraestructure.abstract_services;

import java.util.UUID;

import com.example.best_travel.api.models.request.TicketRequest;
import com.example.best_travel.api.models.responses.TicketResponse;

public interface ITicketService extends CrudService<TicketRequest,TicketResponse,UUID> {
    
}
