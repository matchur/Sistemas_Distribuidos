#Autores: Jean e Matheus
# Data de criação: 24/05/22
# Data de modificação: 02/06/22
import pika, sys, json, pandas as pd



if __name__ == '__main__':
        # Conexão com o servidor RabbitMQ

    connection = pika.BlockingConnection(pika.ConnectionParameters('localhost'))
    channel = connection.channel()

    # Declaração da fila

    channel.queue_declare(queue='tweetsList')

    data = pd.read_csv('../data/Tweets.csv').to_dict('records')

    for line in data:
        channel.basic_publish(exchange='', routing_key='tweetsList', body=json.dumps(line))

    connection.close()