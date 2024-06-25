package com.batch.springbatchapplication.config;

import com.batch.springbatchapplication.steps.ItemDescompressStep;
import com.batch.springbatchapplication.steps.ItemProcessorStep;
import com.batch.springbatchapplication.steps.ItemReaderStep;
import com.batch.springbatchapplication.steps.ItemWriterStep;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Bean
    @JobScope // indica que este paso, va estar disponible en el job, y cuando finalice se destruye el objeto
    public ItemDescompressStep itemDescompress() {
        return new ItemDescompressStep();
    }
    @Bean
    @JobScope // indica que este paso, va estar disponible en el job, y cuando finalice se destruye el objeto
    public ItemReaderStep itemReader() {
        return new ItemReaderStep();
    }
    @Bean
    @JobScope // indica que este paso, va estar disponible en el job, y cuando finalice se destruye el objeto
    public ItemProcessorStep itemProcessor() {
        return new ItemProcessorStep();
    }
    @Bean
    @JobScope // indica que este paso, va estar disponible en el job, y cuando finalice se destruye el objeto
    public ItemWriterStep itemWriter() {
        return new ItemWriterStep();
    }

    /* ************************************ Builders Construct  *************************************/
    /** Item Tasklet & Step Descompress **/
    @Bean
    public Tasklet taskletItemDescompressStep() {
        return new ItemDescompressStep();
    }
    @Bean
    public Step descompressStep(JobRepository jobRepository, Tasklet taskletItemDescompressStep, PlatformTransactionManager transactionManager) {
        return new StepBuilder("itemDescompressStep", jobRepository)
                .tasklet(taskletItemDescompressStep, transactionManager)
                .build();
    }

    /** Item Tasklet & Step Reader **/
    @Bean
    public Tasklet taskletItemReaderStep() {
        return new ItemReaderStep();
    }
    @Bean
    public Step readerStep(JobRepository jobRepository, Tasklet taskletItemReaderStep, PlatformTransactionManager transactionManager) {
        return new StepBuilder("itemReaderStep", jobRepository)
                .tasklet(taskletItemReaderStep, transactionManager)
                .build();
    }

    /** Item Tasklet & Step Processor **/

    @Bean
    public Tasklet taskletItemProcessorStep() {
        return new ItemProcessorStep();
    }
    @Bean
    public Step processorStep(JobRepository jobRepository, Tasklet taskletItemProcessorStep, PlatformTransactionManager transactionManager) {
        return new StepBuilder("itemProcessorStep", jobRepository)
                .tasklet(taskletItemProcessorStep, transactionManager)
                .build();
    }

    /** Item Tasklet & Step Writer **/
    @Bean
    public Tasklet taskletItemWriterStep() {
        return new ItemWriterStep();
    }
    @Bean
    public Step writerStep(JobRepository jobRepository, Tasklet taskletItemWriterStep, PlatformTransactionManager transactionManager) {
        return new StepBuilder("itemWriterStep", jobRepository)
                .tasklet(taskletItemWriterStep, transactionManager)
                .build();
    }
    /** Job execution steps **/
    @Bean
    public Job itemWriterJob(JobRepository jobRepository, Step descompressStep, Step readerStep, Step processorStep, Step writerStep) {
        return new JobBuilder("readCSVJob", jobRepository)
                .start(descompressStep)
                .next(readerStep)
                .next(processorStep)
                .next(writerStep)
                .build();
    }
}
