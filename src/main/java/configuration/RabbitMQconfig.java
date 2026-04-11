package configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitMQconfig {

    @Value("${mq.queue.carro}")
    private String queueName;

    @Value("${mq.exchange.carro}")
    private String exchangeName;

    @Value("${mq.routing-key.carro}")
    private String routingKey;

    private final ConnectionFactory connectionFactory;

    // 1. Fila
    @Bean
    public Queue queue() {
        return new Queue(queueName, true);
    }

    // 2. Exchange (Direct)
    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(exchangeName);
    }

    // 3. Binding
    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    // 4. Converter JSON
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // 5. RabbitTemplate (ENVIO)
    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setExchange(exchangeName);
        template.setMessageConverter(messageConverter());
        return template;
    }
}