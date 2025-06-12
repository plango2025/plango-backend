package com.example.plango.file.repository;

import com.example.plango.file.model.StorageFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StorageFileRepository extends JpaRepository<StorageFile, Long> {
    List<StorageFile> findByFilename(String filename);
    void deleteByFilename(String filename);
}