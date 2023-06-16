package com.example.best_travel.domain.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.best_travel.domain.entities.TourEntity;

public interface TourRepository extends CrudRepository<TourEntity,Long> {
    
}
