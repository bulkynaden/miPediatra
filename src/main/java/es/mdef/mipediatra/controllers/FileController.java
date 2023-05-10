package es.mdef.mipediatra.controllers;

import entities.File;
import es.mdef.mipediatra.MiPediatraApplication;
import es.mdef.mipediatra.assemblers.file.FileAssembler;
import es.mdef.mipediatra.models.file.FileModel;
import es.mdef.mipediatra.services.FileStorageService;
import org.slf4j.Logger;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "*")
public class FileController {
    private final FileStorageService fileStorageService;
    private final FileAssembler fileAssembler;
    private final Logger log = MiPediatraApplication.log;

    public FileController(FileStorageService fileStorageService, FileAssembler fileAssembler) {
        this.fileStorageService = fileStorageService;
        this.fileAssembler = fileAssembler;
    }

    @PostMapping("/upload")
    public FileModel uploadFile(@RequestParam("file") MultipartFile file) {
        return fileAssembler.toModel(fileStorageService.storeFile(file));
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) {
        File fileEntity = fileStorageService.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileEntity.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileEntity.getName() + "\"")
                .body(new ByteArrayResource(fileEntity.getData()));
    }

    @GetMapping("/files/{fileId}")
    public FileModel getById(@PathVariable Long fileId) {
        File file = fileStorageService.getFile(fileId);
        return fileAssembler.toModel(file);
    }
}