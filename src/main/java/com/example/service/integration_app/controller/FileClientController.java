package com.example.service.integration_app.controller;

import com.example.service.integration_app.clients.OpenFeignClient;
import com.example.service.integration_app.clients.WebClientSender;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/v1/client/file")
@RequiredArgsConstructor
public class FileClientController {

    //private final OkHttpClientSender client;
    //private final RestTemplateClient client;
    //private final WebClientSender client;
    private final OpenFeignClient client;


    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestPart MultipartFile file) {
        client.uploadFile(file);

        return ResponseEntity.ok("File was upload");
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        Resource resource = client.downloadFile(filename);

        if (resource == null || !resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();

        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);
        headers.setContentType(MediaType.TEXT_PLAIN);

        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }
}
