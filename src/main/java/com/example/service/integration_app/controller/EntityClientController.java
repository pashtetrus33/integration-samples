package com.example.service.integration_app.controller;

import com.example.service.integration_app.clients.OpenFeignClient;
import com.example.service.integration_app.clients.WebClientSender;
import com.example.service.integration_app.entity.DatabaseEntity;
import com.example.service.integration_app.model.EntityModel;
import com.example.service.integration_app.model.UpsertEntityRequest;
import com.example.service.integration_app.service.DatabaseEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/client/entity")
@RequiredArgsConstructor
public class EntityClientController {

    //private final OkHttpClientSender client;
    //private final RestTemplateClient client;
    //private final WebClientSender client;
    private final OpenFeignClient client;

    private final DatabaseEntityService service;

//    @GetMapping
//    public ResponseEntity<List<EntityModel>> entityList() {
//        return ResponseEntity.ok(client.getEntityList());
//    }

    @GetMapping
    public ResponseEntity<List<EntityModel>> entityList() {
        return ResponseEntity.ok(service.findAll().stream().map(EntityModel::from).toList());
    }

    @GetMapping("/{name}")
    public ResponseEntity<EntityModel> entityByName(@PathVariable String name) {
        return ResponseEntity.ok(EntityModel.from(service.findByName(name)));
    }

    @PostMapping
    public ResponseEntity<EntityModel> createEntity(@RequestBody UpsertEntityRequest request) {
        var newEntity = client.createEntity(request);
        var savedEntity = service.create(DatabaseEntity.from(newEntity));
        return ResponseEntity.status(HttpStatus.CREATED).body(EntityModel.from(savedEntity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel> updateEntity(@PathVariable UUID id, @RequestBody UpsertEntityRequest request) {
        var updatedEntity = client.updateEntity(id, request);
        var updatedDbEntity = service.update(id, DatabaseEntity.from(updatedEntity));
        return ResponseEntity.ok(EntityModel.from(updatedDbEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntity(@PathVariable UUID id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
