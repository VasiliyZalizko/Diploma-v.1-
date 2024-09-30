package ru.netology.cloudstorage.controller;

import ru.netology.cloudstorage.model.entity.StorageFile;
import ru.netology.cloudstorage.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping("/file")
    @Secured("ROLE_USER1")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile multipartFile) {
        return fileService.uploadFile(multipartFile);
    }

    @DeleteMapping("/file")
    @Secured("ROLE_USER1")
    public ResponseEntity<?> deleteFile(@RequestParam("filename") String filename) {
        return fileService.deleteFile(filename);
    }

    @GetMapping("/file")
    public ResponseEntity<?> downloadFile(@RequestParam("filename") String filename) {
        return fileService.downloadFile(filename);
    }

    @PutMapping("/file")
    @Secured("ROLE_USER1")
    public ResponseEntity<?> updateFile(@RequestParam("filename") String filename, @RequestBody() StorageFile storageFile) {
        return fileService.updateFile(filename, storageFile);
    }

    @GetMapping("/list")
    public ResponseEntity<List<StorageFile>> getListOfFiles(@RequestParam int limit) {
        return fileService.getAllFiles(limit);
    }
}