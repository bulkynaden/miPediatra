package es.mdef.mipediatra.services;

import entities.File;
import es.mdef.mipediatra.MiPediatraApplication;
import es.mdef.mipediatra.exceptions.EntityNotFoundException;
import es.mdef.mipediatra.exceptions.FileStorageException;
import es.mdef.mipediatra.repositories.FileRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileStorageService {
    private final Logger log = MiPediatraApplication.log;
    private final FileRepository fileRepository;

    public FileStorageService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public File storeFile(MultipartFile file) {
        try {
            File fileEntity = new File();
            fileEntity.setName(file.getOriginalFilename());
            fileEntity.setType(file.getContentType());
            fileEntity.setData(file.getBytes());
            fileEntity.setSize(file.getSize());

            return fileRepository.save(fileEntity);
        } catch (IOException ex) {
            throw new FileStorageException("Error al guardar " + file.getName() + ".", ex);
        }
    }

    public File getFile(Long fileId) {
        return fileRepository.findById(fileId)
                .orElseThrow(() -> new EntityNotFoundException(fileId, "archivo"));
    }

    public void deleteFile(File file) {
        fileRepository.delete(file);
    }
}
