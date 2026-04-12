# RabbitMQ - Carros

## Integrantes
- Giovanna Mafra Silva - RA 01242025
- Guilherme Serafim - RA 01242097

## Descricao
Projeto com produtor Spring Boot que publica mensagens de carros e vendas em uma exchange Direct do RabbitMQ, e consumidor em Python que le e exibe as mensagens no terminal.

## Endpoints do produtor
Base: http://localhost:8080

### POST /carros
Publica um carro.

- URL: `POST http://localhost:8080/carros`
- Headers: `Content-Type: application/json`
- JSON de exemplo:

```json
{
  "modelo": "Civic",
  "marca": "Honda",
  "ano": 2022,
  "cor": "Prata",
  "preco": 135000.00
}
```

- Retorno esperado:

```text
Carro enviado para processamento
```

### POST /carros/venda
Publica uma venda.

- URL: `POST http://localhost:8080/carros/venda`
- Headers: `Content-Type: application/json`
- JSON de exemplo:

```json
{
  "modelo": "Civic",
  "valor": 130000.00
}
```

- Retorno esperado:

```text
Venda enviada para processamento
```

## Como subir o ambiente
1. Subir o RabbitMQ via Docker:

```bash
docker compose up -d
```

2. Subir o produtor (Spring Boot):

```bash
./mvnw spring-boot:run
```

No Windows PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

3. Subir o consumidor (Python):

```bash
python -m pip install pika
python consumer/consumer.py
```

## Como verificar o consumidor
Nao ha endpoint GET HTTP no consumidor. A verificacao e feita pelo terminal do script `consumer.py`.

Exemplo de saida esperada ao publicar um carro:

```text
🚗 Carro recebido:
  Modelo : Civic
  Marca  : Honda
  Ano    : 2022
  Cor    : Prata
  Preco  : R$ 135000.00
------------------------------
```

Exemplo de saida esperada ao publicar uma venda:

```text
💰 Venda recebida:
  Modelo : Civic
  Valor  : R$ 130000.00
------------------------------
```
