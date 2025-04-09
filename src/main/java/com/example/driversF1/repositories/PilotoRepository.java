package com.example.driversF1.repositories;

import com.example.driversF1.models.Piloto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PilotoRepository extends JpaRepository<Piloto, Long> {
    @Query("SELECT p FROM Piloto p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :termino, '%'))")
    List<Piloto> buscarPorNombre(@Param("termino") String termino);
}