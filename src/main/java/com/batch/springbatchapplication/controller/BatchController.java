package com.batch.springbatchapplication.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/v1")
public class BatchController {

    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job job;

    @PostMapping("/uploadFile")
    public ResponseEntity<?> receiveFile(@RequestParam MultipartFile file) {
        String fileName = file.getOriginalFilename();
        try {
            Path path = Paths.get("src" + File.separator + "main" + File.separator + "resources"
                    + File.separator + "files"  + File.separator + fileName);
            Files.createDirectories(path.getParent());
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            log.info("-------------------> Inicio del proceso Batch <-------------------");
            JobParameters jobParameters = new JobParametersBuilder()
                    .addDate("fecha",new Date())
                    .addString("name", fileName)
                    .toJobParameters();
            jobLauncher.run(job, jobParameters);

            Map<String,String> response = new HashMap<>();
            response.put("message", "File loaded successfully");
            response.put("path", path.toString());
            return ResponseEntity.ok(response);
        } catch (Exception io) {
            log.error("Error occurred while uploading file {}", io.getMessage());
        }
        return ResponseEntity.ok().build();
    }
}
