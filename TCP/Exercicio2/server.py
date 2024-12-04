"""Código que fica  responsavel pela parte do servidor e comunicação com o cliente,trata as requisições
do cliente e processa de acordo com cada requisição recebida e em seguida irá retornar uma respostas via socket TCP.
Protocolo: TCP
Data de criação:02/04/2022
Ultima modificação:10/04/2022
Autores:
    @jeammiguel
    @morpheus6556
"""

import copy
import socket
import _thread
import os
from datetime import datetime


REQ_CODE = 1 #Requisição
RESP_CODE = 2 #Resposta

COMMANDS_CODES = {
    'DEFAULT': 0,
    'ADDFILE': 1,
    'DELETE': 2,
    'GETFILESLIST': 3,
    'GETFILE': 4,
    'EXIT': 5,
    1: 'ADDFILE',
    2: 'DELETE',
    3: 'GETFILESLIST',
    4: 'GETFILE',
    5: 'EXIT',
    0: 'DEFAULT'
}

VALIDATION_RESPONSE = {
    'SUCCESS': 1,
    'ERROR': 2,
    1: 'SUCCESS',
    2: 'ERROR'
}

BYTEORDER = 'big'
LOCAL = ''


def client(clientsocket, addr):

    global LOCAL
    print(f'O cliente {addr} acabou de se conectar!!')#mostra qual client se conectou

    while True:
        req_message = clientsocket.recv(1024)#espera solicitação do cliente
        print(f'{addr} >> {req_message}')

        message_type = req_message[0:1]
        command = req_message[1:2]
        filename_size = req_message[2:3]
        filename = req_message[3:(3 + int.from_bytes(filename_size, BYTEORDER))]

        # Escreve todas as requisições feitas ao servidor e seus respectivos endereços do cliente
        with open("log.log", "a") as file_object:
            logs = f'{datetime.now()} {addr} >> {message_type+command+filename_size+filename}\n'
            file_object.write(logs)

        resp_message = RESP_CODE.to_bytes(1, BYTEORDER)
        resp_message += int.from_bytes(command, BYTEORDER).to_bytes(1, BYTEORDER)


        #------------- COMANDOS
        #---- EXIT
        if COMMANDS_CODES[int.from_bytes(command, BYTEORDER)] == 'EXIT':
            resp_message += VALIDATION_RESPONSE['SUCCESS'].to_bytes(1, BYTEORDER)
           
            clientsocket.send(resp_message)
            #clientsocket.send(resp_message.decode('utf-8'))
            break

        #---- DELETE
        elif COMMANDS_CODES[int.from_bytes(command, BYTEORDER)] == 'DELETE':
            if os.path.isfile(filename.decode('utf-8')):
                os.remove(filename.decode('utf-8'))

                resp_message += VALIDATION_RESPONSE['SUCCESS'].to_bytes(1, BYTEORDER)
            else:
                resp_message += VALIDATION_RESPONSE['ERROR'].to_bytes(1, BYTEORDER)

            clientsocket.send(resp_message)
        #---- GETFILELIST
        elif COMMANDS_CODES[int.from_bytes(command, BYTEORDER)] == 'GETFILESLIST':
            resp_message += VALIDATION_RESPONSE['SUCCESS'].to_bytes(1, BYTEORDER)

            files = os.listdir(LOCAL)
            resp_message += len(files).to_bytes(2, BYTEORDER)

            repeatable_resp_message = copy.deepcopy(resp_message)
            if len(files) > 0:
                repeatable_resp_message += len(files[0]).to_bytes(1, BYTEORDER)

                repeatable_resp_message += bytes(files[0], 'utf-8')
            else:
                repeatable_resp_message += (0).to_bytes(1, BYTEORDER)
                repeatable_resp_message += bytes('', 'utf-8')

            clientsocket.send(repeatable_resp_message)

            for index in range(1, len(files)):
                repeatable_resp_message = copy.deepcopy(resp_message)
                repeatable_resp_message += len(files[index]).to_bytes(1, BYTEORDER)
                repeatable_resp_message += bytes(files[index], 'utf-8')
                clientsocket.send(repeatable_resp_message)

        #---- GETFILE
        elif COMMANDS_CODES[int.from_bytes(command, BYTEORDER)] == 'GETFILE':
            if os.path.isfile(filename):
                resp_message += VALIDATION_RESPONSE['SUCCESS'].to_bytes(1, BYTEORDER)

                file_size = os.path.getsize(filename.decode('utf-8'))
                resp_message += file_size.to_bytes(4, BYTEORDER)

                with open(filename.decode('utf-8'), 'rb') as file_to_send:
                    for data in file_to_send:
                        resp_message += data

                        clientsocket.send(resp_message)

                        resp_message = bytes()
            else:
                resp_message += VALIDATION_RESPONSE['ERROR'].to_bytes(1, BYTEORDER)

                resp_message += (0).to_bytes(4, BYTEORDER)

                resp_message += bytes(0)


                clientsocket.send(resp_message)


        #---- ADDFILE
        elif COMMANDS_CODES[int.from_bytes(command, BYTEORDER)] == 'ADDFILE':
            resp_message += VALIDATION_RESPONSE['SUCCESS'].to_bytes(1, BYTEORDER)

            bytes_received = 0

            i_index = 3 + int.from_bytes(filename_size, BYTEORDER)
            f_index = i_index + 4
            file_size = int.from_bytes(req_message[i_index:f_index], BYTEORDER)

            data_write = req_message[f_index:]
            bytes_received += len(data_write)

            with open(os.path.join(LOCAL, filename.decode('utf-8')), 'wb') as file_to_write:
                file_to_write.write(data_write)

                while True:
                    if bytes_received >= file_size:
                        break

                    req_message = clientsocket.recv(1024)

                    data_write = req_message
                    bytes_received += len(data_write)

                    file_to_write.write(data_write)

                    if bytes_received >= file_size:
                        break

            clientsocket.send(resp_message)

        else:
            resp_message += VALIDATION_RESPONSE['ERROR'].to_bytes(1, BYTEORDER)
            clientsocket.send(resp_message)

    clientsocket.close()


if __name__ == '__main__':

    LOCAL = os.getcwd()

    # Criando o socket TCP/IP
    sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    # Ligar o socket a sua porta TCP
    server_address = ('localhost', 6667)
    print(f'Endereço: {server_address[0]} Porta {server_address[1]}')
    sock.bind(server_address)

    #socket em modo de escuta, 
    print('Esperando uma conexão...')
    sock.listen(1)

    #a cada nova conexão no servidor, um novo cliente deve ser gerenciado, threads com loop infinito 
    while True:
        connection, client_address = sock.accept()
        _thread.start_new_thread(client, (connection, client_address))