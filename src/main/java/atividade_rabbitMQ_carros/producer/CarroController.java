package atividade_rabbitMQ_carros.producer;

import atividade_rabbitMQ_carros.dto.CarroDto;
import atividade_rabbitMQ_carros.dto.VendaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carros")
@RequiredArgsConstructor
public class CarroController {

    private final RabbitTemplate rabbitTemplate;

    @Value("${mq.exchange.carro}")
    private String exchange;

    @Value("${mq.routing-key.carro}")
    private String routingKey;

    @PostMapping
    public ResponseEntity<String> cadastrar(@RequestBody CarroDto carro) {

        rabbitTemplate.convertAndSend(
                exchange,
                routingKey,
                carro
        );

        return ResponseEntity.ok("Carro enviado para processamento");
    }

    @PostMapping("/venda")
    public ResponseEntity<String> vender(@RequestBody VendaDto venda) {

        rabbitTemplate.convertAndSend(
                exchange,
                "carro.vendido", // nova routing key
                venda
        );

        return ResponseEntity.ok("Venda enviada para processamento");
    }
}
