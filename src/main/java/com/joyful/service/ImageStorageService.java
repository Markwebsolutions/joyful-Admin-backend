package com.joyful.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.stereotype.Service;

@Service
public class ImageStorageService {

    private static final String LOCAL_IMAGE_DIR = "D:/joyful-img/";
    private static final String FTP_URL_PREFIX = "https://webimage.joyful.co.in/";
    private static final ExecutorService executor = Executors.newFixedThreadPool(5); // Multithreaded uploads

    public String storeImage(String imagePathOrUrl) {
        try {
            String fileName;
            Path localPath;

            // Ensure local directory exists
            File dir = new File(LOCAL_IMAGE_DIR);
            if (!dir.exists()) dir.mkdirs();

            // 1. Handle web or local image
            if (isHttpUrl(imagePathOrUrl)) {
                fileName = extractFileNameFromUrl(imagePathOrUrl);
                localPath = Paths.get(LOCAL_IMAGE_DIR, fileName);

                if (!Files.exists(localPath)) {
                    System.out.println("🔄 Downloading image: " + imagePathOrUrl);
                    URL url = new URL(imagePathOrUrl);
                    URLConnection connection = url.openConnection();
                    connection.setRequestProperty("User-Agent", "Mozilla/5.0");

                    String contentType = connection.getContentType();
                    if (contentType == null || !contentType.startsWith("image/")) {
                        throw new RuntimeException("❌ Invalid image URL. Content-Type: " + contentType);
                    }

                    try (InputStream in = new BufferedInputStream(connection.getInputStream())) {
                        Files.copy(in, localPath, StandardCopyOption.REPLACE_EXISTING);
                    }
                } else {
                    System.out.println("✅ Image exists locally: " + fileName);
                }

            } else {
                // Local file
                File sourceFile = new File(imagePathOrUrl);
                if (!sourceFile.exists()) throw new FileNotFoundException("❌ File not found: " + imagePathOrUrl);

                fileName = sourceFile.getName();
                localPath = Paths.get(LOCAL_IMAGE_DIR, fileName);
                Files.copy(sourceFile.toPath(), localPath, StandardCopyOption.REPLACE_EXISTING);
            }

            // 2. Upload to FTP (async, non-blocking)
            File fileToUpload = localPath.toFile();
            executor.submit(() -> uploadImageToFTPIfNotExists(fileToUpload, fileName));

            // 3. Return URL immediately
            return FTP_URL_PREFIX + fileName;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("❌ Failed to store image: " + e.getMessage(), e);
        }
    }

    private boolean isHttpUrl(String input) {
        return input.startsWith("http://") || input.startsWith("https://");
    }

    private String extractFileNameFromUrl(String urlString) {
        try {
            URL url = new URL(urlString);
            String path = url.getPath();
            return path.substring(path.lastIndexOf('/') + 1);
        } catch (Exception e) {
            throw new RuntimeException("Failed to extract file name from URL: " + urlString, e);
        }
    }

    private void uploadImageToFTPIfNotExists(File file, String fileName) {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect("ftp.joyful.co.in", 21);
            ftpClient.login("u779815092.Joyfulimage", "txDL#tir$zImn&2H");
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.changeWorkingDirectory("webimage.joyful.co.in");

            FTPFile[] existingFiles = ftpClient.listFiles(fileName);
            if (existingFiles != null && existingFiles.length > 0) {
                System.out.println("🟡 FTP already contains: " + fileName + " (skipping upload)");
                return;
            }

            try (InputStream input = new FileInputStream(file)) {
                System.out.println("🚀 Uploading to FTP: " + fileName + " (" + file.length() / 1024 + " KB)");
                ftpClient.storeFile(fileName, input);
                System.out.println("✅ FTP upload complete: " + fileName);
            }

        } catch (IOException e) {
            System.err.println("❌ FTP upload failed for " + fileName + ": " + e.getMessage());
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ignored) {}
        }
    }
}
