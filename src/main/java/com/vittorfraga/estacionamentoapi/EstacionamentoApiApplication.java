package com.vittorfraga.estacionamentoapi;

import com.vittorfraga.estacionamentoapi.config.security.RsaKeyConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication(scanBasePackages = "com.vittorfraga.estacionamentoapi")
@EnableConfigurationProperties(RsaKeyConfigProperties.class)
@EnableCaching
public class EstacionamentoApiApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(EstacionamentoApiApplication.class);
        //app.setAdditionalProfiles("test");
        app.run(args);

    }


}
