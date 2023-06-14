package com.example.best_travel.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.best_travel.domain.entities.FlyEntity;

public interface FlyRepository extends JpaRepository<FlyEntity,Long> {
    
}
