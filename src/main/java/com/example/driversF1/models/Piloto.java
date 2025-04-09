package com.example.driversF1.models;

import jakarta.persistence.*;
import lombok.*;


// Modificar la entidad Piloto
@Entity
@Table(name = "pilotos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Piloto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(length = 1000)
    private String descripcion;

    private String escuderia;

    private Integer rating;

    // Almacenamos la imagen como un array de bytes
    @Lob
    private byte[] imagen;

    // Guardamos el tipo de contenido para servir la imagen correctamente
    private String imagenContentType;
}

