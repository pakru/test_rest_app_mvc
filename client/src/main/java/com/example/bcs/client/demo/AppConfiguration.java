package com.example.bcs.client.demo;

import com.example.bcs.client.demo.csv.CsvData;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@Configuration
public class AppConfiguration {

    @Autowired
    private ResourceLoader resourceLoader;

    @Value("${client.datafile:classpath:data.csv}")
    private String csvDataFilePath;

    private static final Logger logger = LoggerFactory.getLogger(AppConfiguration.class);

    @Bean(name = "data")
    List<CsvData> getCsvData() throws IOException {
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema schema = CsvSchema.emptySchema().withHeader();

        ObjectReader oReader = csvMapper.readerFor(CsvData.class).with(schema);

        try (Reader reader = new InputStreamReader(resourceLoader.getResource(csvDataFilePath).getInputStream())) {
            MappingIterator<CsvData> mi = oReader.readValues(reader);
            List<CsvData> dataList = mi.readAll();
            logger.info("Data: " + dataList);
            return dataList;
        }
    }
}
