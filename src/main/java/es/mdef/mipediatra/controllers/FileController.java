package es.mdef.mipediatra.controllers;

import es.mdef.mipediatra.MiPediatraApplication;
import es.mdef.mipediatra.assemblers.FileAssembler;
import es.mdef.mipediatra.models.ListModel;
import es.mdef.mipediatra.models.PostModel;
import es.mdef.mipediatra.models.PutModel;
import es.mdef.mipediatra.models.file.FileModel;
import es.mdef.mipediatra.services.ControllerService;
import es.mdef.mipediatra.services.CrudService;
import es.mdef.mipediatra.services.FileStorageService;
import es.mdef.mipediatralib.entities.File;
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
public class FileController implements ControllerService<File, FileModel, PostModel, PutModel, ListModel> {
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
        FileModel file = fileStorageService.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(new ByteArrayResource(file.getData()));
    }

    @GetMapping("/files/{fileId}")
    public ResponseEntity<FileModel> getById(@PathVariable Long fileId) {
        return ResponseEntity.ok(fileStorageService.getFile(fileId));
    }

    @Override
    public CrudService<File, FileModel, PostModel, PutModel, ListModel> getService() {
        return fileStorageService;
    }
}