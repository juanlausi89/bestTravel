package com.example.best_travel.infraestructure.abstract_services;

import java.math.BigDecimal;
import java.util.Set;

import org.springframework.data.domain.Page;

import com.example.best_travel.util.SortType;

public interface CatalogService<RESPONSE> {
   
    Page<RESPONSE> realAll(Integer page, Integer size,SortType sortType);

    Set<RESPONSE> readLessPrice(BigDecimal price);

    Set<RESPONSE> readBetweenPrices(BigDecimal min, BigDecimal max);

    String FIELD_BY_SORT = "price";

}
