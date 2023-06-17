package com.example.best_travel.infraestructure.abstract_services;

public interface CrudService<REQUEST,RESPONSE,ID> {
    
    RESPONSE create(REQUEST request);

    RESPONSE read(ID id);

    RESPONSE update(REQUEST request, ID id);

    void delete(ID id);
}
