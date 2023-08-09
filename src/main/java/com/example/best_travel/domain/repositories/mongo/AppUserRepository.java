package com.example.best_travel.domain.repositories.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.best_travel.domain.entities.documents.AppUserDocument;

import java.util.Optional;

public interface  AppUserRepository extends MongoRepository<AppUserDocument, String> {
   Optional<AppUserDocument> findByUsername(String username); 
}
