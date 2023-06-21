package com.example.best_travel.infraestructure.services;

import java.math.BigDecimal;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.best_travel.api.models.responses.FlyResponse;
import com.example.best_travel.domain.entities.FlyEntity;
import com.example.best_travel.domain.repositories.FlyRepository;
import com.example.best_travel.infraestructure.abstract_services.IFlyService;
import com.example.best_travel.util.SortType;

import lombok.AllArgsConstructor;

@Transactional
@Service
@AllArgsConstructor
public class FlyService implements IFlyService {

    private final FlyRepository flyRepository;

    @Override
    public Page<FlyResponse> realAll(Integer page, Integer size, SortType sortType) {
        
        PageRequest pageRequest = null;
        
        switch(sortType){
            case NONE -> pageRequest = PageRequest.of(page,size);
            case LOWER -> pageRequest = PageRequest.of(page,size,Sort.by(FIELD_BY_SORT).ascending());
            case UPPER -> pageRequest = PageRequest.of(page,size,Sort.by(FIELD_BY_SORT).descending());
        }

        return this.flyRepository.findAll(pageRequest).map(this::entityToResponse);
        
    }

    @Override
    public Set<FlyResponse> readLessPrice(BigDecimal price) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'readLessPrice'");
    }

    @Override
    public Set<FlyResponse> readBetweenPrices(BigDecimal min, BigDecimal max) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'readBetweenPrices'");
    }

    @Override
    public Set<FlyResponse> readByOriginDestiny(String origin, String destiny) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'readByOriginDestiny'");
    }


    private FlyResponse entityToResponse(FlyEntity entity){
        FlyResponse response = new FlyResponse();
        BeanUtils.copyProperties(entity, response);
        return response;
    }
    
}