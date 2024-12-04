#Autores: Jean e Matheus
# Data de criação: 24/05/22
# Data de modificação: 02/06/22

import pika, sys, os, json, pandas as pd


def main():
     # Conexão com o servidor RabbitMQ
    connection = pika.BlockingConnection(pika.ConnectionParameters(host='localhost'))
    channel = connection.channel()
     # Criação do canal
    channel.queue_declare(queue='tweetsList')

    def callback(ch, method, properties, body):
        tweet = json.loads(body)
        tweet_id = tweet['_unit_id']
        tweet_gender = tweet['gender']
        

        routing_key = ''
        # topicos que podem ser criados
        if tweet_gender == 'male':
            routing_key = 'male'

        elif tweet_gender == 'female':
            routing_key = 'female'

        elif tweet_gender == 'unknown':
            routing_key = 'unknown'
       # Declaração da fila para troca
       # Cria a fila do topico
        channel.exchange_declare(exchange='direct_logs', exchange_type='direct')
        channel.basic_publish(exchange='direct_logs', routing_key=routing_key, body=body)
        
        print(" [x] Classified %r" % tweet_id)

    channel.basic_consume(queue='tweetsList', on_message_callback=callback, auto_ack=True)

    print(' [*] Waiting for messages.')
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