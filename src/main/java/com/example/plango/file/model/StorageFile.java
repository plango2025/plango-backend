package com.example.plango.file.model;

import com.example.plango.common.model.BaseEntity;
import com.example.plango.user.model.UserInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "storage_file")
public class StorageFile extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "filename", nullable = false, updatable = false)
    private String filename;
    @Column(name = "original_filename", nullable = false, updatable = false)
    private String originalFilename;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploader_id", nullable = false, updatable = false)
    private UserInfo uploader;
}
