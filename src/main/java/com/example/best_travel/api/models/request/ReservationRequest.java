package com.example.best_travel.api.models.request;

import java.io.Serializable;

import org.antlr.v4.runtime.misc.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ReservationRequest implements Serializable {
     
    private String idClient;
    private Long idHotel;
    private Integer totalDays;
    private String email;
}
