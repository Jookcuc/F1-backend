package com.example.driversF1.dto;

import lombok.Data;

@Data
public class PilotoRequestDTO {
    private String nombre;
    private String descripcion;
    private String escuderia;
    private Integer rating;
}