package com.batch.springbatchapplication.steps;

import com.batch.springbatchapplication.entities.Person;
import com.batch.springbatchapplication.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class ItemWriterStep implements Tasklet {

    @Autowired
    private PersonService personService;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("--------------------------> ItemWriter step started <--------------------------");
        List<Person> personsList = (List<Person>) chunkContext.getStepContext()
                .getStepExecution()
                .getJobExecution()
                .getExecutionContext()
                .get("personsList");


        personService.saveAll(personsList);
        log.info("--------------------------> ItemWriter step completed <--------------------------");
        return RepeatStatus.FINISHED;
    }
}
