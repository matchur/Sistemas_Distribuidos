"""
     1 byte             0 - 255 bytes
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
|   Message Types   |      Nick Size | Nick [Nick Size]         | 
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
| Message Size  |   Message [Message Size]                      | 
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+


 autores:
 @jeanmmiguel
 @morpheus
 

Data de Criação: 13/04/2022
Ultima alteração: 19/04/2022
"""

import emoji
import copy
import re
import socket
import sys
import _thread
import os
from datetime import datetime
from random import seed
from random import randint


# GLOBAL VARIABLES
BYTEORDER = 'big'

bufferSize = 1024


def recive_message(sock, server_address):

    global bufferSize

    while True:
        bytesAddressPair = sock.recvfrom(bufferSize)

        message = bytesAddressPair[0]
        address = bytesAddressPair[1]

        len_nickname = int.from_bytes(message[0:1], BYTEORDER)
        nickname = message[1:(len_nickname + 1)].decode('utf-8')
        

        i_index = len_nickname + 1
        f_index = i_index + 1
        len_message = int.from_bytes(message[i_index:f_index], BYTEORDER)

        

        i_index = f_index
        f_index = i_index + len_message
        messageX = message[i_index:f_index].decode('utf-8')



        type_message = int.from_bytes(message[(f_index):(f_index+1)], BYTEORDER)

        #separa qual o tipo de mensagem - (mensagem normal, emoji, URL, ECHO)   
        if type_message == 1:
            print('{}: {}'.format(nickname, messageX))
        if type_message == 2:
            print(nickname+': '+emoji.emojize(messageX))
        if type_message == 3:
            print('{}: ~URL~ {}'.format(nickname, messageX))
        #if type_message == 4:
            

        # FAZER A PARTE DE COMO ELE VAI TRATAR O TYPE MESSAGE ! ! ! !

        

#se a pessoa não colocar apelido, gera um randômico entre 1e 10 ex: apelido 7
if __name__ == '__main__':
    apelido = input('Apelido: ')
    if apelido == '':
        apelido = 'Apelido {}'.format((randint(1, 10)))
    len_nickname = len(apelido)
    adress = input('Endereço IP: ')
    port = int(input('Porta: '))
    #deixa ip local definido
    if adress == '':
        adress = '127.0.0.1'

    # Criar o socket de datagrama UDP
    sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

    # Ligar o socket a sua porta UDP
    server_address = (adress, port)
    listen_address = (adress, (port + 1))
    try:
        sock.bind(listen_address)

        # Conexão criada e esperando
        print('UDP server escutando')

    except OSError:
        listen_address = server_address
        server_address = (adress, (port + 1))
        sock.bind(listen_address)

    # Criar uma thread para recebimento de menssagens
    _thread.start_new_thread(recive_message, (sock, listen_address))

    #thread de mensagem
    while True:


        #coloca o tamanho do nick no primeiro array de byte
        send_message = len_nickname.to_bytes(1, BYTEORDER)
        #coloca o nome no vetor de bytes
        send_message += bytes(apelido, 'utf-8')



        #espera a mensagem do usuario
        user_message = input(f'{apelido}: ')



        #coloca o tamanho da mensagem no array
        send_message += len(user_message).to_bytes(1, BYTEORDER)
        #coloca a mensagem no array 
        send_message += bytes(user_message, 'utf-8')


        #separa qual o tipo de mensagem - (mensagem normal, emoji, URL, ECHO)
        #separa a mensagem
        analyse_message = user_message.split()
        #flag mensagem normal
        flag_msg = True

        #ECHO (4)
        if analyse_message[0] == "ECHO":
            num_type = 4
            flag_msg = False

        #Emoji (2)
        if re.search(":(.+?):",analyse_message[0]):
            num_type = 2
            flag_msg = False

        #URL (3)
        if re.search("((http|https)\:\/\/)?[a-zA-Z0-9\.\/\?\:@\-_=#]+\.([a-zA-Z]){2,6}([a-zA-Z0-9\.\&\/\?\:@\-_=#])*",analyse_message[0]):
            num_type = 3
            flag_msg = False

        #Mensagem Normal (1)
        if flag_msg == True :          
            num_type = 1
        
       


        send_message += num_type.to_bytes(1, BYTEORDER)
        sock.sendto(send_message, server_address)