package com.sip_calculator.Sip_Calculator.Util;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.sip_calculator.Sip_Calculator.CsvEntities.YearlyInvestmentCsv;
import com.sip_calculator.Sip_Calculator.Dto.YearlyInvestment;
import com.sip_calculator.Sip_Calculator.Mappers.YearlyInvestmentMapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@Component
public class FileUtils {
    @Value("${folder.path}")
    private String fileFolder;

    @Getter
    private String downloadDirectory;

    @Autowired
    private YearlyInvestmentMapper yearlyInvestMentMapper;

    public Mono<Void> setStorageLocation() {
        return Mono.fromRunnable(() -> {
            try {
                String userDir = System.getProperty("user.dir");
                String directory = userDir + File.separator + fileFolder + File.separator;
                if (!Files.exists(Paths.get(directory))) {
                    createDirectory(Paths.get(directory));
                }
                this.downloadDirectory = directory;
            } catch (IOException e) {
                log.error("Error creating directory: {}", e.getMessage());
                throw new RuntimeException("Error setting storage location", e);
            }
        }).then(); // 👈 Ensures the Mono returns Void
    }

    private void createDirectory(Path path) throws IOException {
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }

    public Mono<String> createCsvAndAddOrdering(List<YearlyInvestment> returnList){
        log.info("Creating the CSV file for {}", returnList);
        return Mono.fromCallable(()->{
            String filePath = this.downloadDirectory + "Yearly-Investment.csv";
            Path file = Paths.get(filePath);

            if (Files.exists(file)) {
                Files.delete(file);
            }

            List<YearlyInvestmentCsv> csvList = yearlyInvestMentMapper.sourceToDestination(returnList);
            Files.createFile(file);

            try (Writer writer = new FileWriter(filePath)) {
                CustomMappingStrategyForYearlyInvestments customMappingStrategy = new CustomMappingStrategyForYearlyInvestments();
                customMappingStrategy.setType(YearlyInvestmentCsv.class);
                StatefulBeanToCsv<YearlyInvestmentCsv> beanToCsv = new StatefulBeanToCsvBuilder<YearlyInvestmentCsv>(writer)
                        .withMappingStrategy(customMappingStrategy)
                        .build();
                beanToCsv.write(csvList);
            }

            return filePath;
        }).subscribeOn(Schedulers.boundedElastic());
    }


}
