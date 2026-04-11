import pika
import json

QUEUE           = 'carro.queue'
EXCHANGE        = 'carro.exchange'
ROUTING_KEY     = 'carro.criado'
ROUTING_KEY_VENDA = 'carro.vendido'

def consumir():
    connection = pika.BlockingConnection(
        pika.ConnectionParameters(
            host='localhost',
            credentials=pika.PlainCredentials('myuser', 'mypassword')
        )
    )

    channel = connection.channel()

    channel.queue_declare(queue=QUEUE, durable=True)
    channel.exchange_declare(exchange=EXCHANGE, exchange_type='direct', durable=True)
    channel.queue_bind(exchange=EXCHANGE, queue=QUEUE, routing_key=ROUTING_KEY)
    channel.queue_bind(exchange=EXCHANGE, queue=QUEUE, routing_key=ROUTING_KEY_VENDA)

    def callback_carro(ch, method, properties, body):
        carro = json.loads(body)
        print("🚗 Carro recebido:")
        print(f"  Modelo : {carro.get('modelo')}")
        print(f"  Marca  : {carro.get('marca')}")
        print(f"  Ano    : {carro.get('ano')}")
        print(f"  Cor    : {carro.get('cor')}")
        print(f"  Preço  : R$ {carro.get('preco'):.2f}")
        print("-" * 30)
        ch.basic_ack(delivery_tag=method.delivery_tag)

    def callback_venda(ch, method, properties, body):
        venda = json.loads(body)
        print("💰 Venda recebida:")
        print(f"  Modelo : {venda.get('modelo')}")
        print(f"  Valor  : R$ {venda.get('valor'):.2f}")
        print("-" * 30)
        ch.basic_ack(delivery_tag=method.delivery_tag)

    def callback(ch, method, properties, body):
        if method.routing_key == ROUTING_KEY:
            callback_carro(ch, method, properties, body)
        elif method.routing_key == ROUTING_KEY_VENDA:
            callback_venda(ch, method, properties, body)

    channel.basic_consume(queue=QUEUE, on_message_callback=callback)

    print("✅ Aguardando mensagens. CTRL+C para sair.\n")
    channel.start_consuming()

if __name__ == '__main__':
    consumir()