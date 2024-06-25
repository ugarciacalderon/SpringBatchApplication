package com.batch.springbatchapplication.steps;

import com.batch.springbatchapplication.entities.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Slf4j
public class ItemDescompressStep implements Tasklet {
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("--------------------------> ItemDescompress step started <--------------------------");
        Resource resource = new FileSystemResource("src/main/resources/files/persons.zip");
        String filePath = resource.getFile().getAbsolutePath();
        ZipFile zipFile = new ZipFile(filePath);
        // directorio destino
        File desDir = new File(resource.getFile().getParent());
        if (!desDir.exists()) {
            desDir.mkdirs();
        }
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            File file = new File(desDir, entry.getName());

            if (entry.isDirectory()) {
                file.mkdirs();
            } else {
                InputStream in = zipFile.getInputStream(entry);
                FileOutputStream out = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
                out.close();
                in.close();
            }
        }
        zipFile.close();
        log.info("--------------------------> ItemDescompress step completed <--------------------------");

        return RepeatStatus.FINISHED;
    }
}
