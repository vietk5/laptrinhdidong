package com.example.category_crud_api.service.impl;

import com.example.category_crud_api.config.StorageProperties;
import com.example.category_crud_api.exception.StorageException;
import com.example.category_crud_api.exception.StorageFileNotFoundException;
import com.example.category_crud_api.service.IStorageService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileSystemStorageServiceImpl implements IStorageService {

    private final Path rootLocation;

    public FileSystemStorageServiceImpl(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    // tạo tên file p<id>.<ext>
    @Override
    public String getSorageFilename(MultipartFile file, String id) {
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        return "p" + id + "." + ext;
    }

    @Override
    public void store(MultipartFile file, String storeFilename) {
        if (file.isEmpty()) {
            throw new StorageException("Không thể lưu file rỗng.");
        }
        try {
            // Đảm bảo thư mục tồn tại
            if (!Files.exists(rootLocation)) {
                Files.createDirectories(rootLocation);
            }

            Path destinationFile = this.rootLocation.resolve(Paths.get(storeFilename))
                    .normalize().toAbsolutePath();

            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                // tránh path traversal
                throw new StorageException("Không thể lưu file bên ngoài thư mục cho phép.");
            }

            try (var inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }

        } catch (IOException e) {
            throw new StorageException("Lỗi khi lưu file", e);
        }
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException("Không thể đọc file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Không tìm thấy file: " + filename, e);
        }
    }

    @Override
    public void delete(String storeFilename) {
        try {
            Path file = load(storeFilename);
            FileSystemUtils.deleteRecursively(file);
        } catch (IOException e) {
            throw new StorageException("Không thể xóa file: " + storeFilename, e);
        }
    }

    @Override
    public void init() {
        try {
            if (!Files.exists(rootLocation)) {
                Files.createDirectories(rootLocation);
            }
        } catch (IOException e) {
            throw new StorageException("Không thể khởi tạo thư mục lưu trữ", e);
        }
    }
}
