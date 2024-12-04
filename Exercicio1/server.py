import socket
import sys
import _thread
import os
from os.path import isfile, join
import hashlib
"""Código que fica  responsavel pela parte do servidor e comunicação com o cliente,trata as requisições
do cliente e processa de acordo com cada requisição recebida e em seguida irá retornar uma respostas via socket TCP.
Protocolo: TCP
Data de criação:02/04/2022
Ultima modificação:10/04/2022
Autores:
    @jeammiguel
    @morpheus6556
"""


HEADERSIZE = 10
SERVER_LOCAL = ''
BYTEORDER = 'big'



users= ['root','admin','jean','matheus']
passwords = ['root','admin','123456','lulavsbolsonaroamvlinkinpark']

def send_data(clientsocket, message):
    """codifiaca  mensagem para o padrão de protocolo utilizado e
    enviar para o servidor.
    
    """

    response_message = len(message).to_bytes(1, BYTEORDER)
    response_message += bytes(message, 'utf-8')

    clientsocket.send(response_message)


def client(clientsocket, addr):
    """recebe uma requisição, processa de acordo com o código da requisição
    e enviar uma resposta para o cliente que fez a requisição.
    """

    print(f'Conectado com  {addr}')

    while True:
        msg = clientsocket.recv(1024)

        msg_len = int.from_bytes(msg[0:1], BYTEORDER)
        msg = msg[1:(msg_len + 1)].decode('utf-8')

        # checagem 
        print(f'{addr}, >> {msg}')
        response_message = ''
            
       
        if msg == 'EXIT':
            response_message = 'Saiu da conexão'

        if msg == 'GETFILES':
            #ignora pastas e pega somente os arquivos
            files = [f for f in os.listdir(SERVER_LOCAL) if isfile(join(SERVER_LOCAL, f))]
            #files = os.listdir(SERVER_LOCAL) 
            response_message = str(len(files))
            clientsocket.send(str.encode(response_message))
            response_message = ''
            for i in range(len(files)): 
                response_message = response_message + str(files[i]) + '\n'
            clientsocket.send(str.encode(response_message)) 

        if msg == 'GETDIRS':
            #ignora pastas e pega somente os arquivos
            dirs = [d for d in os.listdir(os.getcwd()) if os.path.isdir(d)]
            #files = os.listdir(SERVER_LOCAL) 
            response_message = str(len(dirs))
            clientsocket.send(str.encode(response_message))
            response_message = ''
            for i in range(len(dirs)):
                response_message = response_message + str(dirs[i]) + '\n'
            clientsocket.send(str.encode(response_message)) 
            

        if msg == 'PWD':
            path = os.getcwd()
            clientsocket.send(str.encode(path))
       
        teste = msg.split()
        if teste[0] == 'CHDIR':
            SERVER_LOCAL = os.getcwd()
            aux = teste[1]
            os.chdir(aux)
            clientsocket.send(str.encode(aux))
            
                                          

        if msg == 'EXIT':
            clientsocket.close()
            break
            
        cmd = msg.split()
        if cmd[0] == 'CONNECT':
            user = cmd[1].split(',')
            password = user[1]
            user = user[0]
            flagU = False
            for userVar in users:
                if userVar == user:
                    flagU = True
                    for passVar in passwords:
                        if password == hashlib.sha512(passVar.encode("utf-8")).hexdigest():
                            clientsocket.send(str.encode('SUCESS'))
            if flagU == False:
                clientsocket.send(str.encode('ERROR'))
        
            flagU = False

        

if __name__ == '__main__':

    SERVER_LOCAL = os.getcwd()

    # Criando o socket TCP/IP
    sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    sock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)


    # Ligar o socket a sua porta TCP
    server_address = ('localhost', 6667)
    print(f'Iniciando: {server_address[0]} porta {server_address[1]}')
    sock.bind(server_address)

    # Esperando por uma conexão
    print('waiting for a connection')

    # Escutando novas conexões
    sock.listen(8)

    # Criar uma thread para cada nova conexão
    while True:
        connection, client_address = sock.accept()
        _thread.start_new_thread(client, (connection, client_address))
