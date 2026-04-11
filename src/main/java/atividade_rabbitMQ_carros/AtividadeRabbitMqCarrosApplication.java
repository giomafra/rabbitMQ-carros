package atividade_rabbitMQ_carros;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"atividade_rabbitMQ_carros", "configuration"})
public class AtividadeRabbitMqCarrosApplication {

	public static void main(String[] args) {
		SpringApplication.run(AtividadeRabbitMqCarrosApplication.class, args);
	}

}
