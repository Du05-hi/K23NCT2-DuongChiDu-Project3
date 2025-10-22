package k23cnt2_duongchidu_Day02.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class K23cnt2DuongchiduDay02AppConfig {
    @Bean
    public String appName() {
        return "<h1>VIỆN CÔNG NGHỆ DEVMaster</h1><h2>Spring Boot Application</h2>";
    }
}
