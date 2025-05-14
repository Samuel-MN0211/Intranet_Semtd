package semtd_intranet.semtd_net;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Documentação da API")
                        .version("1.0")
                        .description("Exemplo de documentação com SpringDoc Swagger"));
    }
}
