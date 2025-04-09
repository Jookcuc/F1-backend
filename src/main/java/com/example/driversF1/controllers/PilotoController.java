package com.example.driversF1.controllers;

import com.example.driversF1.dto.PilotoDTO;
import com.example.driversF1.dto.PilotoRequestDTO;
import com.example.driversF1.services.PilotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/pilotos")
@CrossOrigin(origins = "*")
public class PilotoController {

    @Autowired
    private PilotoService pilotoService;

    @GetMapping
    public ResponseEntity<List<PilotoDTO>> obtenerTodos() {
        return ResponseEntity.ok(pilotoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PilotoDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pilotoService.obtenerPorId(id));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<PilotoDTO>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(pilotoService.buscarPorNombre(nombre));
    }

    @PostMapping
    public ResponseEntity<PilotoDTO> crear(@RequestBody PilotoRequestDTO pilotoDTO) {
        return new ResponseEntity<>(pilotoService.crear(pilotoDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PilotoDTO> actualizar(@PathVariable Long id, @RequestBody PilotoRequestDTO pilotoDTO) {
        return ResponseEntity.ok(pilotoService.actualizar(id, pilotoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        pilotoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/imagen")
    public ResponseEntity<PilotoDTO> subirImagen(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(pilotoService.subirImagen(id, file));
    }

    @GetMapping("/{id}/imagen")
    public ResponseEntity<byte[]> verImagen(@PathVariable Long id) {
        byte[] imagen = pilotoService.obtenerImagen(id);
        String contentType = pilotoService.obtenerImagenContentType(id);

        return ResponseEntity
                .ok()
                .header("Content-Type", contentType)
                .body(imagen);
    }

}