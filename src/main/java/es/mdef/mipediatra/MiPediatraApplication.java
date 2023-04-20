package es.mdef.mipediatra;

import es.mdef.mipediatralib.utils.TestConnect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MiPediatraApplication {
    public static final Logger log = LoggerFactory.getLogger(MiPediatraApplication.class);

    public static void main(String[] args) {
        log.info("Iniciando miPediatraAPI");
        log.info(TestConnect.connect());
        SpringApplication.run(MiPediatraApplication.class, args);
    }
}