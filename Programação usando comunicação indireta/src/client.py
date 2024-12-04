
 #Autores: Jean e Matheus
# Data de criação: 24/05/22
# Data de modificação: 02/06/22

import pika, sys, os, json

from datetime import datetime


def main():
    request_type = int(input('\nDeseja receber informações de tweets de gêneros Masculino (1), Feminino (2) ou Desconhecido (3): '))
    while request_type > 3 or request_type < 1:
            print("Requisição inválida.\n")
            request_type = int(input('\nDeseja receber informações de tweets de gêneros Masculino (1), Feminino (2) ou Desconhecido (3): '))

    if request_type == 1:
        name_queue = 'male'
    elif request_type == 2:
        name_queue = 'female'
    elif request_type == 3:
        name_queue = 'unknown'
    filename = name_queue + '.log'

    connection = pika.BlockingConnection(pika.ConnectionParameters(host='localhost'))
    channel = connection.channel()

    channel.exchange_declare(exchange='direct_logs', exchange_type='direct')

    result = channel.queue_declare(queue='', exclusive=False)
    queue_name = result.method.queue

    channel.queue_bind(exchange='direct_logs', queue=queue_name, routing_key=name_queue)

    print('  Para sair pressione CTRL+C')
        
        # Exibe o retorno

    def callback(ch, method, properties, body):
        data = json.loads(body)
        message = f'------------------\n' \
            + f'{datetime.now().strftime("%Y-%m-%d %H:%M:%S")}\n' \
            + f'\tName: {data["name"]}\n' \
            + f'\tLocation: {data["tweet_location"]}\n' \
            + f'\tGender: {data["gender"]}\n' \
            + f'\tDescription: {data["description"]}\n' \
            + f'\tText: {data["text"]}\n' \
            + '------------------\n'

        print(message) 

     # Define a função de consumo de mensagens para a fila 'tweets'


    channel.basic_consume(queue=queue_name, on_message_callback=callback, auto_ack=True)
    # Inicia o consumo de mensagens

    channel.start_consuming()


if __name__ == '__main__':
    try:
        main()
    except KeyboardInterrupt:
        print('Interrupted')
        try:
            sys.exit(0)
        except SystemExit:
            os._exit(0)