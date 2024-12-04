"""código  responsavel pela parte do cliente e comunicação com o servidor,trata o envio e o
recebimento da mensagem, via socket TCP, de acordo com o código do comando enviado/recebido.
Protocolo:TCP
Data de criação:02/04/2022
Ultima modificação:10/04/2022

Autores:
    @jeanmmiguel
    @morpheus6556

"""

import socket
import sys
import os

# GLOBAL VARIABLES
REQ_CODE = 1
RESP_CODE = 2

STATUS_CODE = {
    'SUCCESS': 1,
    'ERROR': 2,
    1: 'SUCCESS',
    2: 'ERROR'
}


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


BYTEORDER = 'big'
CLIENT_LOCAL = ''


def send_data(clientsocket, message):

    command = message.split()[0]

    req_message = REQ_CODE.to_bytes(1, BYTEORDER)
    if command not in COMMANDS_CODES:
        req_message += (0).to_bytes(1, BYTEORDER)
    else:
        req_message += COMMANDS_CODES[command].to_bytes(1, BYTEORDER)

    file_name = ''
    if command in ['ADDFILE', 'DELETE', 'GETFILE']:
        file_name = message.split()[1]

    req_message += len(file_name).to_bytes(1, BYTEORDER)
    req_message += bytes(file_name, 'utf-8')

    if command == 'ADDFILE':
        file_size = os.path.getsize(file_name)
        req_message += file_size.to_bytes(4, BYTEORDER)

        with open(file_name, 'rb') as file_to_send:
            for data in file_to_send:
                req_message += data
                clientsocket.send(req_message)
                req_message = bytes()

    else:
        clientsocket.send(req_message)


if __name__ == '__main__':
   #obtem diretório corrente
    CLIENT_LOCAL = os.getcwd()

    # Criando o socket TCP/IP
    sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    # faz a conexão com o socket na porta onde o servidor está escutando
    server_address = ['localhost', 6667]
    print(f'Conectando  {server_address[0]} na porta {server_address[1]}')
    sock.connect(tuple(server_address))

   
    msg_len = 0
    id = 'CLIENT#'
    msg = ''

    # Laço principal onde  acontece a troca de mensagens entre o cliente e o servidor
    while True:
        message = input(f'{id} ')

        send_data(sock, message)

        server_message = ''
        resp_message = sock.recv(1024)

        resp_command = int.from_bytes(resp_message[1:2], BYTEORDER)
        resp_status = int.from_bytes(resp_message[2:3], BYTEORDER)

        if COMMANDS_CODES[resp_command] == 'EXIT':
            break

        elif COMMANDS_CODES[resp_command] == 'DELETE':
            if STATUS_CODE[resp_status] == 'SUCCESS':
                message_server = 'Arquivo deletado com sucesso!'
            else:
                message_server = 'O arquivo solicitado não pode ser excluido.'

        elif COMMANDS_CODES[resp_command] == 'GETFILESLIST':
            number_files = int.from_bytes(resp_message[3:5], BYTEORDER)

            namefile_size = int.from_bytes(resp_message[5:6], BYTEORDER)
            namefile = resp_message[6:6 + namefile_size].decode('utf-8')
            message_server += f'\n\t{namefile}\n'

            for index in range(number_files - 1):
                resp_message = sock.recv(1024)
                namefile_size = int.from_bytes(resp_message[5:6], BYTEORDER)
                namefile = resp_message[6:6 + namefile_size].decode('utf-8')
                if index == number_files - 2:
                    message_server += f'\t{namefile}'
                else:
                    message_server += f'\t{namefile}\n'

        elif COMMANDS_CODES[resp_command] == 'GETFILE':
            if STATUS_CODE[resp_status] == 'SUCCESS':
                
                message_server = 'Download feito com sucesso!'

                bytes_received = 0
                file_size = int.from_bytes(resp_message[3:7], BYTEORDER)

                data_write = resp_message[7:]
                bytes_received += len(data_write)

                with open(os.path.join(CLIENT_LOCAL, message.split()[1]), 'wb') as file_to_write:
                    file_to_write.write(data_write)

                    while True:
                        if bytes_received >= file_size:
                            break

                     
                        resp_message = sock.recv(1024)

                        data_write = resp_message
                        bytes_received += len(data_write)

                        file_to_write.write(data_write)

            else:
                server_message = 'O arquivo solicitado não pode ser baixado.'

        elif COMMANDS_CODES[resp_command] == 'ADDFILE':
            server_message = 'Arquivo enviado com sucesso!'

        else:
            server_message = 'A requisição não pode ser concluída.'

        print(f'SERVER$ {server_message}')


    print(f'fechar socket')
    sock.close()