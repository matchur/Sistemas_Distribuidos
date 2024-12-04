#Autores:
#    Jean Carlos Martins Miguel
#    Matheus Vinicius da Costa

#Data de Criação: 23/04/2022
#Ultima alteração: 04/05/2022


import socket
import database_pb2

# menu de interação com o usuário
def menu():
    
    print("***********MATRÍCULA******************")
    print("(1) Inserir Matricula")
    print("(2) Alterar notas")
    print("(3) Alterar faltas")
    print("(4) Mostrar alunos de uma disciplina")
    print("(5) Notas do aluno")
    print("(6) Sair")
    mensagem = int(input("Escolha uma operacaocao:"))
    print("*************************************")
    return mensagem

if __name__ == '__main__':
    # conexao TCP com o server 
    client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    client_socket.connect(("localhost", 6667))
    
    mensagem = menu()

    while mensagem!=6:
        #Mensagem enviada
        #inputs de acordo com a ação selecionada no menu
        if mensagem == 1:
            print("**********INSERIR MATRÍCULA***********")
            client_socket.send(("1\n").encode('UTF-8'))
            matricula = database_pb2.Matricula()
            matricula.ra = int(input("RA:"));
            matricula.cod_disciplina = input("Codigo da disciplina:");
            matricula.ano = int(input("Ano:"));
            matricula.semestre = int(input("Semestre:"));

            enviar = matricula.SerializeToString() #fazer serializaçao
            tamanho = len(enviar)

            client_socket.send((str(tamanho) + "\n").encode())
        elif mensagem == 2:
            print("************ALTERAR NOTAS*************")
            client_socket.send(("2\n").encode('UTF-8'))
            matricula = database_pb2.Matricula()
            matricula.ra = int(input("RA:"));
            matricula.cod_disciplina = input("Codigo da disciplina:");
            matricula.ano = int(input("Ano:"));
            matricula.semestre = int(input("Semestre:"));
            matricula.nota = float(input("Nota:"));

            enviar = matricula.SerializeToString()
            tamanho = len(enviar)

            client_socket.send((str(tamanho) + "\n").encode())
        elif mensagem == 3:
            print("***********ALTERAR FALTAS*************")

            client_socket.send(("3\n").encode('UTF-8'))
            matricula = database_pb2.Matricula()
            matricula.ra = int(input("RA:"));
            matricula.cod_disciplina = input("Codigo da disciplina:");
            matricula.ano = int(input("Ano:"));
            matricula.semestre = int(input("Semestre:"));
            matricula.faltas = int(input("Faltas:"));

            enviar = matricula.SerializeToString()
            tamanho = len(enviar)

            client_socket.send((str(tamanho) + "\n").encode())
        elif mensagem == 4:
            print("***MOSTRAR ALUNOS DE UMA DISCIPLINA***")

            client_socket.send(("4\n").encode('UTF-8'))
            matricula = database_pb2.Matricula()
            matricula.cod_disciplina = input("Codigo da disciplina:");
            matricula.ano = int(input("Ano:"));
            matricula.semestre = int(input("Semestre:"));

            enviar = matricula.SerializeToString()
            tamanho = len(enviar)

            client_socket.send((str(tamanho) + "\n").encode())
        elif mensagem == 5:
            print("***********NOTAS DO ALUNO*************")

            client_socket.send(("5\n").encode('UTF-8'))
            matricula = database_pb2.Matricula()
            matricula.ra = int(input("RA:"));
            matricula.ano = int(input("Ano:"));
            matricula.semestre = int(input("Semestre:"));

            enviar = matricula.SerializeToString()
            tamanho = len(enviar)

            client_socket.send((str(tamanho) + "\n").encode())
        elif mensagem == 6: 
            print("SAINDO DA APLICAÇÃO!!!!!!!!!")
            break
        client_socket.send(enviar)

        #Mensagem recebida do server
        aux = client_socket.recv(1024)
        if mensagem != 4 and mensagem != 5:
            aux=aux.decode()
            print("\nServidor$ ", aux)
        else:
            aux=aux.decode().split("\n")
            print("\nServidor$ Resultado:", len(aux)-2)
            for i in aux[:-1]:
                print(i)

        mensagem = menu()
    client_socket.close()