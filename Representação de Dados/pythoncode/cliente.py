'''
"""
Data de criação:25/04/2022
Ultima modificação:10/05/2022
Autores:
    @jeammiguel
    @morpheus6556
"""
'''

import socket
import gerenciamentoNotas_pb2


# Cria um socket TCP
_socket_ = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

# Conectar ao server
_socket_.connect(("localhost", 6667))

#enum opcoes

#criacao
INSERIR_MATRICULA = "1"
#alteracao
ALTERAR_NOTA = "2"
ALTERAR_FALTAS = "3"
#visualizacao (relatorios)
LISTAR_ALUNOS = "4"
LISTAR_DISCIPLINAS_ALUNO = "5"
LISTAR_DISCIPLINAS_CURSO = "6"




#Tamanho > Mensagem > Retorna (Server)

def sendToServer(opcao, mensagem, tamanhoMsg):
    sendToServerType(opcao)
    _socket_.send((str(tamanhoMsg) + "\n").encode())
    _socket_.send(mensagem)

# Função para enviar o tipo de requisição
def sendToServerType(opcao):

    requestType = gerenciamentoNotas_pb2.requestType()
    requestType.type = int(opcao)
    msg = requestType.SerializeToString()
    tamanhoMsg = len(msg)
    # send tamanho para o servidor
    _socket_.send((str(tamanhoMsg) + "\n").encode())
    # send msg type
    _socket_.send(msg)


def Show_Send(opcao):
    if opcao == LISTAR_ALUNOS:
        request = gerenciamentoNotas_pb2.listarAlunosRequest()
        codigoDisciplina = (input("Informe o código da disciplina: "))
        ano = input("Informe o ano: ")
        semestre = input("Informe o semestre: ")
        

        if ano.isdigit() and semestre.isdigit() and codigoDisciplina != "" and ano != "" and semestre != "":
           
           
            request.codigoDisciplina = codigoDisciplina
            request.ano = int(ano)
            request.semestre = int(semestre)
            
            sendToServer(opcao, request.SerializeToString(), len(request.SerializeToString()))
            tamanhoMsg = ''
            

            while True:
                tamanhoMsg += _socket_.recv(1).decode()
                if tamanhoMsg.endswith('\n'):
                    break
            tamanhoMsg = int(tamanhoMsg)

            response = _socket_.recv(tamanhoMsg)

            responseParsed = gerenciamentoNotas_pb2.listarAlunosResponse()
            responseParsed.ParseFromString(response)            

            if responseParsed.mensagem != "":                
                print(responseParsed.mensagem)
            else:                
                for aluno in responseParsed.alunos:
                    print(aluno, end="")
                    print("----------")
        else:
            print("Informe corretamente os campos")

    elif opcao == ALTERAR_NOTA:
        request = gerenciamentoNotas_pb2.alterarNotaRequest()
       
        #inputs dos dados
        ra = input("Informe o RA do aluno: ")
        codigoDisciplina = input("Informe o código da disciplina: ")
        ano = input("Informe o ano da disciplina: ")
        semestre = input("Informe o semestre da disciplina: ")
        nota = input("Informe a nota do aluno: ")
        

        if ra.isdigit() and ano.isdigit() and semestre.isdigit() and nota.isdigit() and codigoDisciplina != "" and ano != "" and semestre != "" and nota != "" and ra != "":
            request.ra = int(ra)
            request.codigoDisciplina = codigoDisciplina
            request.ano = int(ano)
            request.semestre = int(semestre)
            request.nota = float(nota)
            #pra mandar pelo servidor tem que fazer a serializacao
            sendToServer(opcao, request.SerializeToString(), len(request.SerializeToString()))
            tamanhoMsg = ''
            while True:
                tamanhoMsg += _socket_.recv(1).decode()
                if tamanhoMsg.endswith('\n'):
                    break
            tamanhoMsg = int(tamanhoMsg)
            response = _socket_.recv(tamanhoMsg)
            #auxilar
            responseParsed = gerenciamentoNotas_pb2.alterarNotaResponse()
            responseParsed.ParseFromString(response)
            if responseParsed.mensagem != "":
                print(responseParsed.mensagem)
            else:
                print("Ra: " + str(responseParsed.ra) + "\nCodigo da Disciplina:" + str(responseParsed.codigoDisciplina) + "\nAno: " + str(responseParsed.ano) + "\nSemestre: " + str(responseParsed.semestre) + "\nNota: " + str(responseParsed.nota))
        else:
            print("Informe corretamente os campos")

    elif opcao == ALTERAR_FALTAS:
        request = gerenciamentoNotas_pb2.alterarFaltasRequest()

        #inputs dos dados
        ra = input("Informe o RA do aluno: ")
        codigoDisciplina = input("Informe o código da disciplina: ")
        ano = input("Informe o ano da disciplina: ")
        semestre = input("Informe o semestre da disciplina: ")
        faltas = input("Informe a quantidade de faltas do aluno: ")


        #verifica fields
        if ra != '' and codigoDisciplina != '' and ano != '' and semestre != '' and faltas != '' and ra.isdigit() and ano.isdigit() and semestre.isdigit() and faltas.isdigit():
            request.ra = int(ra)
            request.codigoDisciplina = codigoDisciplina
            request.ano = int(ano)
            request.semestre = int(semestre)
            request.faltas = int(faltas)

            # Envia os dados da requisição para o servidor
            sendToServer(opcao, request.SerializeToString(), len(request.SerializeToString()))
            tamanhoMsg = ''
            # Recebe o tamanho da mensagem
            while True:
                tamanhoMsg += _socket_.recv(1).decode()
                if tamanhoMsg.endswith('\n'):
                    break
            tamanhoMsg = int(tamanhoMsg)
            # Recebe a mensagem
            response = _socket_.recv(tamanhoMsg)
            # Unmarshalling da mensagem
            responseParsed = gerenciamentoNotas_pb2.alterarFaltasResponse()
            responseParsed.ParseFromString(response)
            # Verifica se a requisição foi bem sucedida
            if responseParsed.mensagem != "":
                print(responseParsed.mensagem)
            else:
                print("Ra: " + str(responseParsed.ra) + "\nCodigo da Disciplina:" + str(responseParsed.codigoDisciplina) + "\nAno: " + str(responseParsed.ano) + "\nSemestre: " + str(responseParsed.semestre) + "\nFaltas: " + str(responseParsed.faltas))
        else:
            print("Informe corretamente os campos")

    elif opcao == LISTAR_DISCIPLINAS_CURSO:
        # Variável para armazenar os dados da requisição
        request = gerenciamentoNotas_pb2.listarDisciplinasCursoRequest()
        # Recebe os dados da requisição
        codigoCurso = input("Informe o código do curso: ")
        # Verifica se os dados da requisição são válidos
        if codigoCurso.isdigit() and codigoCurso != "":
            request.codigoCurso = int(codigoCurso)
            # Envia os dados da requisição para o servidor
            sendToServer(opcao, request.SerializeToString(), len(request.SerializeToString()))
            tamanhoMsg = ''
            # Recebe o tamanho da mensagem
            while True:
                tamanhoMsg += _socket_.recv(1).decode()
                if tamanhoMsg.endswith('\n'):
                    break
            tamanhoMsg = int(tamanhoMsg)
            # Recebe a mensagem
            response = _socket_.recv(tamanhoMsg)  
            # Unmarshalling da mensagem
            responseParsed = gerenciamentoNotas_pb2.listarDisciplinasCursoResponse()
            responseParsed.ParseFromString(response)
            # Verifica se a requisição foi bem sucedida
            if responseParsed.mensagem != "":
                print(responseParsed.mensagem)
            else:
                # Imprime os dados da resposta
                for i in range(len(responseParsed.disciplinas)):
                    print("Código da Disciplina: " + str(responseParsed.disciplinas[i].codigoDisciplina) + "\nNome: " + str(responseParsed.disciplinas[i].nome) + "\nProfessor: " + str(responseParsed.disciplinas[i].professor))
                    print("----------")
        else:
            print("Informe corretamente os campos")

    elif opcao == LISTAR_DISCIPLINAS_ALUNO:
        # Variável para armazenar os dados da requisição
        request = gerenciamentoNotas_pb2.listarDisciplinasAlunoRequest()


        # Recebe os dados da requisição
        ra = input("Informe o RA do aluno: ")
        ano = input("Informe o ano: ")
        semestre = input("Informe o semestre: ")



        # Verifica se os dados da requisição são válidos
        if ra.isdigit() and ano.isdigit() and semestre.isdigit() and ra != '' and ano != '' and semestre != '':
            request.ra = int(ra)
            request.ano = int(ano)
            request.semestre = int(semestre)
            # Envia os dados da requisição para o servidor
            sendToServer(opcao, request.SerializeToString(), len(request.SerializeToString()))
            tamanhoMsg = ''
            # Recebe o tamanho da mensagem
            while True:
                tamanhoMsg += _socket_.recv(1).decode()
                if tamanhoMsg.endswith('\n'):
                    break
            tamanhoMsg = int(tamanhoMsg)
            # Recebe a mensagem
            response = _socket_.recv(tamanhoMsg)
            # Unmarshalling da mensagem
            responseParsed = gerenciamentoNotas_pb2.listarDisciplinasAlunoResponse()
            responseParsed.ParseFromString(response)
            # Verifica se a requisição foi bem sucedida
            if responseParsed.mensagem != "":
                print(responseParsed.mensagem)
            else:
                # Imprime os dados da resposta
                for i in range(len(responseParsed.disciplinas)):
                        print("Ra: " + str(responseParsed.disciplinas[i].ra) + "\nCódigo da Disciplina: " + str(responseParsed.disciplinas[i].codigoDisciplina) + "\nNota: " + str(responseParsed.disciplinas[i].nota) + "\nFaltas: " + str(responseParsed.disciplinas[i].faltas))
                        print("----------")
        else:
            print("Informe corretamente os campos")

    elif opcao == INSERIR_MATRICULA:
        # Variável para armazenar os dados da requisição
        request = gerenciamentoNotas_pb2.inserirMatriculaRequest()


        # Recebe os dados da requisição
        ra = input("Informe o código do aluno: ")
        codigoDisciplina = input("Informe o código da disciplina: ")
        ano = input("Informe o ano da disciplina: ")
        semestre = input("Informe o semestre da disciplina: ")


        if ra.isdigit() and ano.isdigit() and semestre.isdigit() and codigoDisciplina != "" and ano != "" and semestre != "" and ra != "":
            request.matricula.ra = int(ra)
            request.matricula.codigoDisciplina = codigoDisciplina
            request.matricula.ano = int(ano)
            request.matricula.semestre = int(semestre)
            # Envia os dados da requisição para o servidor
            sendToServer(opcao, request.SerializeToString(), len(request.SerializeToString()))
            tamanhoMsg = ''
            # Recebe o tamanho da mensagem
            while True:
                tamanhoMsg += _socket_.recv(1).decode()
                if tamanhoMsg.endswith('\n'):
                    break
            tamanhoMsg = int(tamanhoMsg)
            # Recebe a mensagem
            response = _socket_.recv(tamanhoMsg)
            # Unmarshalling da mensagem
            responseParsed = gerenciamentoNotas_pb2.inserirMatriculaResponse()
            responseParsed.ParseFromString(response)
            # Verifica se a requisição foi bem sucedida
            if responseParsed.mensagem:
                print(responseParsed.mensagem)
            else:
                print("Ra: " + str(responseParsed.matricula.ra) + "\nCodigo da Disciplina:" + str(responseParsed.matricula.codigoDisciplina) + "\nAno: " + str(responseParsed.matricula.ano) + "\nSemestre: " + str(responseParsed.matricula.semestre))
        else:
            print("Informe corretamente os campos")
    else:
        print("Opção inválida")

def main():
    #OPCOES
    #laço infinito, opcao 7 sair
    while True:
        print("----- Selecione uma opcao -----")
        print("(1) Inserir Matricula")
        print("(2) Alterar Nota")
        print("(3) Alterar Faltas")
        print("(4) Listar Alunos")
        print("(5) Listar Disciplinas do Curso")
        print("(6) Listar Disciplinas do Aluno")
        #print("(7) SAIR")

    
        
        opcao = input("> ")
        if(opcao.isdigit()):
            Show_Send(opcao)
        else:
            print("Opção inválida")

main()

