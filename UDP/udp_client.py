"""
 autores:
 @jeanmmiguel
 @morpheus
 

Data de Criação: 13/04/2022
Ultima alteração: 19/04/2022
"""
import hashlib
import time
import socket
import os

#define hard coded o endereço e porta
serverAddress = ("127.0.0.1", 6667)
CLIENT_LOCAL = ''
BYTEORDER = 'big'
TamanhoBuffer = 4096

if __name__ == '__main__':
    
   #pegar o diretorio atual do cliente
    CLIENT_LOCAL = os.getcwd()
   
    # Criar o socket de datagrama UDP
    UDPClientSocket = socket.socket(family=socket.AF_INET, type=socket.SOCK_DGRAM)

    while True:
        request_message = input('Cliente$ ')

        if request_message.find('SENDFILE') != -1:
            filename = request_message.split()[1]
            len_filename = len(filename)

            if os.path.isfile(filename):
                request_message = len_filename.to_bytes(1, BYTEORDER)
                request_message += bytes(filename, 'utf-8')

                #pegar tamanho do arquivo
                file_size = os.path.getsize(filename)
                #converte em bytes
                request_message += file_size.to_bytes(4, BYTEORDER)

                #manda para o servidor atraves do socket udp criado
                UDPClientSocket.sendto(request_message, serverAddress)

                with open(filename, 'rb') as file_to_send:
                    for data in file_to_send:
                        request_message = data
                        #manda mensagem usp para o servidor
                        UDPClientSocket.sendto(request_message, serverAddress)
                      
                        print('sleeping')
                        
                        time.sleep(0.2)

                # Criando a hash SHA-1 do arquivo enviado para o checksum
                sha_hash = hashlib.sha1()

                a_file = open(filename, "rb")
                content = a_file.read()
                sha_hash.update(content)

                checksum_enviado = sha_hash.hexdigest()
                UDPClientSocket.sendto(bytes(checksum_enviado, 'utf-8'), serverAddress)

                msgFromServer = UDPClientSocket.recvfrom(TamanhoBuffer = 4096)[0].decode('utf-8')
                print('Servidor# {}'.format(msgFromServer))

            else:
                print('O arquivo não existe!')
        else:
            print('O comando não foi encontrado')
