package atec.beatec.config;
import atec.beatec.Services.EnterpriseService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StartupConfig {

    private final EnterpriseService enterpriseService;

    public StartupConfig(EnterpriseService enterpriseService) {
        this.enterpriseService = enterpriseService;
    }

    @Bean
    public ApplicationRunner runner() {
        return args -> {
            System.out.println("Cleaning all enterprise images...");
            enterpriseService.cleanAllProfilePictures();
            System.out.println("Done.");
        };
    }
}