package com.batch.springbatchapplication.steps;

import com.batch.springbatchapplication.entities.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.util.Date;
import java.util.List;

@Slf4j
public class ItemProcessorStep implements Tasklet {
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("--------------------------> ItemProcessor step started <--------------------------");
        List<Person> personsList = (List<Person>) chunkContext.getStepContext()
                        .getStepExecution()
                                .getJobExecution()
                                        .getExecutionContext()
                                                .get("personsList");

        assert personsList != null;
        for (Person person : personsList) {
            person.setInsertionDate(new Date());
        }

        chunkContext.getStepContext()
                .getStepExecution()
                .getJobExecution()
                .getExecutionContext()
                .put("personList", personsList);

        log.info("--------------------------> ItemProcessor step completed <--------------------------");
        return RepeatStatus.FINISHED;
    }
}
