package com.vittorfraga.estacionamentoapi;

import com.vittorfraga.estacionamentoapi.config.RsaKeyConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication(scanBasePackages = "com.vittorfraga.estacionamentoapi")
@EnableConfigurationProperties(RsaKeyConfigProperties.class)
public class EstacionamentoApiApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(EstacionamentoApiApplication.class);
        //app.setAdditionalProfiles("test");
        app.run(args);

    }


}
