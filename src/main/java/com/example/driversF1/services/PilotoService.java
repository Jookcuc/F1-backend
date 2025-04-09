package com.example.driversF1.services;

import com.example.driversF1.dto.PilotoDTO;
import com.example.driversF1.dto.PilotoRequestDTO;
import com.example.driversF1.models.Piloto;
import com.example.driversF1.repositories.PilotoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PilotoService {

    @Autowired
    private PilotoRepository pilotoRepository;

    // Convertir entidad a DTO
    private PilotoDTO convertToDTO(Piloto piloto) {
        PilotoDTO dto = new PilotoDTO();
        dto.setId(piloto.getId());
        dto.setNombre(piloto.getNombre());
        dto.setDescripcion(piloto.getDescripcion());
        dto.setEscuderia(piloto.getEscuderia());
        dto.setRating(piloto.getRating());


        return dto;
    }

    // Obtener todos los pilotos
    public List<PilotoDTO> obtenerTodos() {
        return pilotoRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Buscar pilotos por nombre
    public List<PilotoDTO> buscarPorNombre(String termino) {
        return pilotoRepository.buscarPorNombre(termino)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Obtener un piloto por ID
    public PilotoDTO obtenerPorId(Long id) {
        Piloto piloto = pilotoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Piloto no encontrado con ID: " + id));
        return convertToDTO(piloto);
    }

    // Crear un nuevo piloto
    public PilotoDTO crear(PilotoRequestDTO pilotoDTO) {
        Piloto piloto = new Piloto();
        piloto.setNombre(pilotoDTO.getNombre());
        piloto.setDescripcion(pilotoDTO.getDescripcion());
        piloto.setEscuderia(pilotoDTO.getEscuderia());
        piloto.setRating(pilotoDTO.getRating());

        Piloto guardado = pilotoRepository.save(piloto);
        return convertToDTO(guardado);
    }

    // Actualizar un piloto
    public PilotoDTO actualizar(Long id, PilotoRequestDTO pilotoDTO) {
        Piloto piloto = pilotoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Piloto no encontrado con ID: " + id));

        piloto.setNombre(pilotoDTO.getNombre());
        piloto.setDescripcion(pilotoDTO.getDescripcion());
        piloto.setEscuderia(pilotoDTO.getEscuderia());
        piloto.setRating(pilotoDTO.getRating());

        // No tocamos la imagen en esta operación

        Piloto actualizado = pilotoRepository.save(piloto);
        return convertToDTO(actualizado);
    }

    // Eliminar un piloto
    @Transactional
    public void eliminar(Long id) {
        // Verificamos que exista el piloto
        if (!pilotoRepository.existsById(id)) {
            throw new RuntimeException("Piloto no encontrado con ID: " + id);
        }

        pilotoRepository.deleteById(id);
    }

    // Subir imagen para un piloto
    public PilotoDTO subirImagen(Long id, MultipartFile imagen) {
        try {
            Piloto piloto = pilotoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Piloto no encontrado con ID: " + id));

            // Guardar la imagen en la entidad
            piloto.setImagen(imagen.getBytes());
            piloto.setImagenContentType(imagen.getContentType());

            Piloto actualizado = pilotoRepository.save(piloto);
            return convertToDTO(actualizado);
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar la imagen", e);
        }
    }

    // Obtener la imagen de un piloto
    public byte[] obtenerImagen(Long id) {
        Piloto piloto = pilotoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Piloto no encontrado con ID: " + id));

        if (piloto.getImagen() == null || piloto.getImagen().length == 0) {
            throw new RuntimeException("Este piloto no tiene imagen");
        }

        return piloto.getImagen();
    }


    public String obtenerImagenContentType(Long id) {
        Piloto piloto = pilotoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Piloto no encontrado con ID: " + id));

        if (piloto.getImagenContentType() == null) {
            return "image/jpeg"; // Valor por defecto
        }

        return piloto.getImagenContentType();
    }
}
