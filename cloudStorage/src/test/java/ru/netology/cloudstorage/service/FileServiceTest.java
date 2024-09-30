package ru.netology.cloudstorage.service;

import ru.netology.cloudstorage.model.entity.StorageFile;
import ru.netology.cloudstorage.repository.FileRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.io.FileInputStream;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {FileService.class})
@ExtendWith(SpringExtension.class)
public class FileServiceTest {
    @MockBean
    private FileRepository fileRepositoryTest;

    @Autowired
    private FileService fileServiceTest;

    @Test
    @SneakyThrows
    void uploadFile() {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("test_file.txt", new FileInputStream("./src/test/resources/test_file.txt"));
        StorageFile testStorageFile = new StorageFile();
        when(fileRepositoryTest.save(Mockito.any(StorageFile.class))).thenReturn(testStorageFile);
        fileServiceTest.uploadFile(mockMultipartFile);
        verify(fileRepositoryTest).save(Mockito.any());
    }

    @Test
    void deleteFile() {
        when(fileRepositoryTest.existsByFilename(Mockito.any())).thenReturn(true);
        doNothing().when(fileRepositoryTest).deleteFileByFilename(Mockito.any());
        fileServiceTest.deleteFile(Mockito.any());
        verify(fileRepositoryTest).deleteFileByFilename(Mockito.any());
    }
}