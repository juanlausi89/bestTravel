package com.example.best_travel.infraestructure.abstract_services;

import java.util.Set;

import com.example.best_travel.api.models.responses.FlyResponse;

public interface IFlyService extends CatalogService<FlyResponse> {

    Set<FlyResponse> readByOriginDestiny(String origin, String destiny);
    
}
