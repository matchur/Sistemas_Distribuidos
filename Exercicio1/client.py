import hashlib
import socket
import sys
import os
"""código  responsavel pela parte do cliente e comunicação com o servidor,trata o envio e o
recebimento da mensagem, via socket TCP, de acordo com o código do comando enviado/recebido.
Protocolo:TCP
Data de criação:02/04/2022
Ultima modificação:10/04/2022

Autores:
    @jeanmmiguel
    @morpheus6556

"""

LOCAL_CLIENT = ''
BYTEORDER = 'big'

def send_data(clientsocket, message):
    """Este método é responsavel por codifiar a mensagem para o padrão de protocolo utilizado e
    enviar para o servidor.
    :param clientsocket: TCP Socket que a mensagem será enviada
    :param message: A mensagem que o cliente deseja enviar
    """

    request_message = len(message).to_bytes(1, BYTEORDER)
    request_message += bytes(message, 'utf-8')

    clientsocket.send(request_message)


if __name__ == '__main__':

    LOCAL_CLIENT = os.getcwd()

    # Criando o socket TCP/IP
    sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    sock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)


    # Conecta o socket a porta onde o servidor está escutando
    server_address = ['localhost', 6667]
    # server_address[0] = input('IP adress: ')
    # server_address[1] = int(input('Port: '))
    print(f'connecting to {server_address[0]} port {server_address[1]}')
    sock.connect(tuple(server_address))

    msg = ''
    buffer = 0
    prefix = '$'

    # Loop principal onde irá ocorrer a troca de mensagens entre o cliente e o servidor
    while True:
        message = input(f'{prefix} ')

       # buffer = sock.recv(1024).decode('utf-8')
        #print(buffer)
        
        if message == 'GETFILES' or message == 'GETDIRS':
                send_data(sock, message)
                buffer = sock.recv(1024).decode('utf-8')
                print(buffer)
                file_message = sock.recv(1024)
                print(file_message.decode('utf-8'))
          
        if message == 'EXIT':
            break

        if message == 'PWD':
              send_data(sock, message)
              buffer = sock.recv(1024).decode('utf-8')
              print(buffer)
       
      
        teste = message.split(' ')
        if teste[0] == 'CHDIR':       
            send_data(sock, teste[0] + ' ' + [1])
            buffer = sock.recv(1024).decode('utf-8')
            print(buffer)

                             
              
        message = message.split()
        if message[0] == 'CONNECT':
            auxPass = message[1].split(',')
            userA = auxPass[0]
            auxPass = hashlib.sha512(auxPass[1].encode("utf-8")).hexdigest()           
            send_data(sock,message[0] + ' ' + userA+','+auxPass)
            print(sock.recv(1024).decode('UTF-8'))
            
            
    

        buffer = 0

    print(f'closing socket')
    sock.close()