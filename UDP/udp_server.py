""" 

 autores:
 @jeanmmiguel
 @morpheus
 

Data de Criação: 13/04/2022
Ultima alteração: 19/04/2022
    
"""


import os
import socket
import hashlib


bufferSize = 4096
serverAddressPort = ("127.0.0.1", 6667)
SERVER_LOCAL = ''
BYTEORDER = 'big'


if __name__ == '__main__':
    
    SERVER_LOCAL = os.getcwd()
    # Criando o socket de datagrama UDP
    UDPServerSocket = socket.socket(family=socket.AF_INET, type=socket.SOCK_DGRAM)

    # Bind to address and ip
    UDPServerSocket.bind(serverAddressPort)

    # Listen for incoming datagrams
    print("UDP server escutando")

    while (True):
        bytesAddressPair = UDPServerSocket.recvfrom(bufferSize)

        request_message = bytesAddressPair[0]
        address = bytesAddressPair[1]

        i_index = 0
        f_index = i_index + 1
        len_filename = int.from_bytes(request_message[i_index:f_index], BYTEORDER)

        i_index = 1
        f_index = i_index + len_filename
        filename = request_message[i_index:f_index]

        i_index = f_index
        f_index = i_index + 4
        file_size = int.from_bytes(request_message[i_index:f_index], BYTEORDER)

        bytes_recebidos = 0
        with open(os.path.join(SERVER_LOCAL, filename.decode('utf-8')), 'wb') as file_to_write:
            while True:
                if bytes_recebidos >= file_size:
                    
                    break

                request_message = UDPServerSocket.recvfrom(bufferSize)[0]

                data_write = request_message
                bytes_recebidos += len(data_write)

                file_to_write.write(data_write)

        # Criando hash SHA-1 do arquivo recebido para o checksum
        hash_sha1 = hashlib.sha1()
        
        #abrir arquivo
        a_file = open(filename.decode('utf-8'), "rb")
        content = a_file.read()
        hash_sha1.update(content)

        checksum_recived = hash_sha1.hexdigest()
        checksum_enviado = UDPServerSocket.recvfrom(bufferSize)[0].decode('utf-8')
      
        #verifica se o codigo recebido é igual ao código enviado
        if checksum_recived == checksum_enviado:
            response_message = 'Arquivo recebido com sucesso!'
            UDPServerSocket.sendto(bytes(response_message, 'utf-8'), address)
            #caso o checksum esteja errado, manda uma mensagem informando que algo aconteceu com o sdados
        else:
            response_message = 'Checksum não bate. Dados foram perdido no caminho!'
            UDPServerSocket.sendto(bytes(response_message, 'utf-8'), address)
