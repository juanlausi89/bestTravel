package com.example.best_travel.infraestructure.abstract_services;

import java.util.Set;

import com.example.best_travel.api.models.responses.HotelResponse;

public interface IHotelService extends CatalogService<HotelResponse> {

    Set<HotelResponse> readByRating(Integer rating);
    
}
