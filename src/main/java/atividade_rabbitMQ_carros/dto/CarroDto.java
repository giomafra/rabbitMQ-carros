package atividade_rabbitMQ_carros.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarroDto {
    private String modelo;
    private String marca;
    private int ano;
    private String cor;
    private double preco;
}