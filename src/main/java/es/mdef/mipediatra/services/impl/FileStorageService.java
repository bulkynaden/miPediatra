package es.mdef.mipediatra.services.impl;

import es.mdef.mipediatra.assemblers.FileAssembler;
import es.mdef.mipediatra.exceptions.EntityNotFoundException;
import es.mdef.mipediatra.exceptions.FileStorageException;
import es.mdef.mipediatra.models.ListModel;
import es.mdef.mipediatra.models.PostModel;
import es.mdef.mipediatra.models.PutModel;
import es.mdef.mipediatra.models.file.FileModel;
import es.mdef.mipediatra.repositories.FileRepository;
import es.mdef.mipediatra.services.AssemblerService;
import es.mdef.mipediatra.services.CrudService;
import es.mdef.mipediatralib.entities.File;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class FileStorageService implements CrudService<File, FileModel, PostModel, PutModel, ListModel> {
    private final FileRepository fileRepository;
    private final FileAssembler assembler;

    public FileStorageService(FileRepository fileRepository, FileAssembler assembler) {
        this.fileRepository = fileRepository;
        this.assembler = assembler;
    }

    public File storeFile(MultipartFile file) {
        try {
            InputStream inputStream = new ByteArrayInputStream(file.getBytes());

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Thumbnails.of(inputStream)
                    .size(320, 320)
                    .outputFormat("jpeg")
                    .toOutputStream(baos);

            byte[] compressedImage = baos.toByteArray();

            File fileEntity = new File();
            fileEntity.setName(file.getOriginalFilename());
            fileEntity.setType(file.getContentType());
            fileEntity.setData(compressedImage);
            fileEntity.setSize(compressedImage.length);

            return fileRepository.save(fileEntity);
        } catch (IOException e) {
            throw new FileStorageException("Error al guardar " + file.getName() + ".", e);
        }
    }

    public FileModel getFile(Long fileId) {
        File file = fileRepository.findById(fileId)
                .orElseThrow(() -> new EntityNotFoundException(fileId, "archivo"));
        return assembler.toModel(file);
    }

    public void deleteFile(File file) {
        fileRepository.delete(file);
    }

    @Override
    public String getEntityName() {
        return "file";
    }

    @Override
    public JpaRepository<File, Long> getRepository() {
        return null;
    }

    @Override
    public AssemblerService<File, FileModel, PostModel, PutModel, ListModel> getAssembler() {
        return assembler;
    }
}
