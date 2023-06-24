package com.example.best_travel.infraestructure.abstract_services;

public interface SimpleCrudService<REQUEST,RESPONSE,ID> {
    
    RESPONSE create(REQUEST request);

    RESPONSE read(ID id);

    void delete(ID id);
}
