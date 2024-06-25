package com.batch.springbatchapplication.steps;

import com.batch.springbatchapplication.entities.Person;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ItemReaderStep implements Tasklet {

    @Autowired
    private ResourceLoader resourceLoader;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("--------------------------> ItemReader step started <--------------------------");
        Reader reader = new FileReader(resourceLoader.getResource("classpath:files/persons.csv").getFile());
        CSVParser parser = new CSVParserBuilder() // indicar el separado de las columnas
                .withSeparator(',')
                .build();
        CSVReader csvReader = new CSVReaderBuilder(reader)
                .withCSVParser(parser)
                .withSkipLines(1) // ignora pa primer linea
                .build();
        List<Person> personList = new ArrayList<>();
        String[] nextLine;
        while ((nextLine = csvReader.readNext()) != null) {
            Person person = new Person();
            person.setName(nextLine[0]);
            person.setLastName(nextLine[1]);
            person.setAge(Integer.parseInt(nextLine[2]));
            personList.add(person);
        }

        csvReader.close();
        reader.close();
        log.info("--------------------------> ItemReader step completed <--------------------------");
        chunkContext.getStepContext()
                .getStepExecution()
                .getJobExecution()
                .getExecutionContext()
                .put("personsList", personList);
        return RepeatStatus.FINISHED;
    }
}
