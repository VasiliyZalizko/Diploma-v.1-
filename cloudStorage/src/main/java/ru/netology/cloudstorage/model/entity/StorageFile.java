package ru.netology.cloudstorage.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "files")
public class StorageFile implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String filename;

    @Column
    private Long size;

    @Lob
    private byte[] bytes;
}