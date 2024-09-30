package ru.netology.cloudstorage.service;

import ru.netology.cloudstorage.model.entity.StorageFile;
import ru.netology.cloudstorage.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {
    private static final Logger logger = LogManager.getLogger(FileService.class);
    private static final String DESC = "Description";
    private final FileRepository fileRepository;

    private StorageFile convertToStorageFile(MultipartFile multipartFile) {
        StorageFile storageFile;
        try {
            storageFile = StorageFile.builder()
                    .filename(multipartFile.getOriginalFilename())
                    .size(multipartFile.getSize())
                    .bytes(multipartFile.getBytes())
                    .build();
        } catch (IOException e) {
            logger.error("Input data exception");
            throw new RuntimeException("Input data exception");
        }
        return storageFile;
    }

    @Transactional
    public ResponseEntity<?> uploadFile(MultipartFile multipartFile) {
        StorageFile newStorageFile = convertToStorageFile(multipartFile);
        String filename = newStorageFile.getFilename();
        if (fileRepository.existsByFilename(filename)) {
            logger.error("Filename already exists");
            return ResponseEntity.badRequest().header(DESC, "This filename already exists").build();
        }
        fileRepository.save(newStorageFile);
        logger.info("File success upload");
        return ResponseEntity.ok().header(DESC, "Success upload").build();
    }

    @Transactional
    public ResponseEntity<?> deleteFile(String filename) {
        if (!fileRepository.existsByFilename(filename)) {
            logger.error("File does not exists");
            return ResponseEntity.badRequest().header(DESC, "File does not exists").build();
        }
        fileRepository.deleteFileByFilename(filename);
        logger.info("File success delete");
        return ResponseEntity.ok().header(DESC, "Success delete").build();
    }

    @Transactional
    public ResponseEntity<?> downloadFile(String filename) {
        StorageFile storageFile = fileRepository.getFileByFilename(filename).orElse(null);
        if (storageFile == null) {
            logger.error("File does not exists");
            return ResponseEntity.badRequest().header(DESC, "File does not exists").build();
        }
        logger.info("File success download");
        return ResponseEntity.ok().header(DESC, "Success download").body(storageFile.getBytes());
    }

    @Transactional
    public ResponseEntity<?> updateFile(String filename, StorageFile newStorageFile) {
        StorageFile storageFile = fileRepository.getFileByFilename(filename).orElse(null);
        if (storageFile == null) {
            logger.error("File does not exists");
            return ResponseEntity.badRequest().header(DESC, "File does not exists").build();
        }
        storageFile.setFilename(newStorageFile.getFilename());
        fileRepository.save(storageFile);
        logger.info("File success update");
        return ResponseEntity.ok().header(DESC, "Success update").build();
    }

    @Transactional
    public ResponseEntity<List<StorageFile>> getAllFiles(int limit) {
        PageRequest pageRequest = PageRequest.of(0, limit);
        List<StorageFile> listOfFiles = fileRepository.findAll(pageRequest).getContent();
        return ResponseEntity.ok().header(DESC, "Success get list").body(listOfFiles);
    }
}