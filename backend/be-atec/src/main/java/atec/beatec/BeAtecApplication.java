package atec.beatec;

import atec.beatec.config.RsaKeyProprieties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(RsaKeyProprieties.class)
@SpringBootApplication
public class BeAtecApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeAtecApplication.class, args);
    }

}
