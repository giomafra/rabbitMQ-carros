package atividade_rabbitMQ_carros.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendaDto {
    public String modelo;
    public double valor;
}
