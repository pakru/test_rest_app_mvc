package com.example.bcs.client.demo;

import com.example.bcs.client.demo.cons.AppCons;
import com.example.bcs.client.demo.csv.CsvData;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@Component
public class ClientRunner implements ApplicationRunner {

    @Value("${client.host:localhost}")
    private String host;

    @Value("${client.protocol:http}")
    private String protocol;

    @Value("${client.port:80}")
    private int port;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    @Qualifier("data")
    private List<CsvData> listData;

    private static final Logger logger = LoggerFactory.getLogger(ClientRunner.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("Starting to POST data via REST");

        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme(protocol).setHost(host).setPort(port).setPath(AppCons.ACCOUNTS_PATH).build();

        URI accountsUri = uriBuilder
                .setScheme(protocol)
                .setHost(host)
                .setPort(port)
                .setPath(AppCons.ACCOUNTS_PATH)
                .build();
        logger.info("Accounts URI: " + accountsUri);

        for (CsvData data : listData) {
            try {
                ResponseEntity<CsvData> response = restTemplate.postForEntity(accountsUri, data, CsvData.class);
                logger.info("Response: " + response);
            } catch (ResourceAccessException ex) {
                logger.error("Failed to POST data: " + data + "; Cause: " + ex.getMessage());
                return;
            } catch (HttpClientErrorException ex) {
                logger.warn("Received error response on data: " + data + "; Code: " + ex.getStatusCode());
            }
        }
    }
}
