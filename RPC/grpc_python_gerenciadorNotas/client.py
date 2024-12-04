'''
  Jean Carlos Martins Miguel
  Matheus Vinicius da Costa
Data de Criação: 12/05/2022
Ultima alteração: 23/05/2022
'''
import grpc
import socket
import gerenciamentoNotas_pb2_grpc
import gerenciamentoNotas_pb2

canal_comunicacao = grpc.insecure_channel('localhost:6667')
stub = gerenciamentoNotas_pb2_grpc.GerenciadorDeNotasStub(canal_comunicacao)

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


def dadosRequisicao(opcao):
    if opcao == LISTAR_ALUNOS:
        request = gerenciamentoNotas_pb2.ListarAlunosRequest()
        codigoDisciplina = (input("Informe o código da disciplina: "))
        ano = input("Informe o ano: ")
        semestre = input("Informe o semestre: ")
        # Verificação
        if ano.isdigit() and semestre.isdigit() and codigoDisciplina != "" and ano != "" and semestre != "":
            request.codigoDisciplina = codigoDisciplina
            request.ano = int(ano)
            request.semestre = int(semestre)
            #envia p/ o server e pega uma resposta
            response = stub.ListarAlunos(request)
            if response.mensagem != "":
                print(response.mensagem)
            else:
                for aluno in response.alunos:
                    print(aluno, end="")
                    print("-------------------------")
        else:
            print("Algum dos campos não foi preenchido corretamente")

    elif opcao == ALTERAR_NOTA:
        request = gerenciamentoNotas_pb2.AlterarNotaRequest()
        ra = input("Digite o RA do aluno: ")
        codigoDisciplina = input("Digite o código da disciplina: ")
        ano = input("Digite o ano da disciplina: ")
        semestre = input("Digite o semestre da disciplina: ")
        nota = input("Digite a nota do aluno: ")
        if ra.isdigit() and ano.isdigit() and semestre.isdigit() and nota.isdigit() and codigoDisciplina != "" and ano != "" and semestre != "" and nota != "" and ra != "":
            request.ra = int(ra)
            request.codigoDisciplina = codigoDisciplina
            request.ano = int(ano)
            request.semestre = int(semestre)
            request.nota = float(nota)
            #envia p/ server
            response = stub.AlterarNota(request)
            if response.mensagem != "":
                print(response.mensagem)
            else:
                print("Ra: " + str(response.ra) + "\nCodigo da Disciplina:" + str(response.codigoDisciplina) + "\nAno: " + str(response.ano) + "\nSemestre: " + str(response.semestre) + "\nNota: " + str(response.nota))
        else:
            print("Algum dos campos não foi preenchido corretamente")

    elif opcao == ALTERAR_FALTAS:
        request = gerenciamentoNotas_pb2.AlterarFaltasRequest()
        ra = input("Digite o RA do aluno: ")
        codigoDisciplina = input("Digite o código da disciplina: ")
        ano = input("Digite o ano da disciplina: ")
        semestre = input("Digite o semestre da disciplina: ")
        faltas = input("Digite a quantidade de faltas do aluno: ")
        if ra != '' and codigoDisciplina != '' and ano != '' and semestre != '' and faltas != '' and ra.isdigit() and ano.isdigit() and semestre.isdigit() and faltas.isdigit():
            request.ra = int(ra)
            request.codigoDisciplina = codigoDisciplina
            request.ano = int(ano)
            request.semestre = int(semestre)
            request.faltas = int(faltas)

            response = stub.AlterarFaltas(request)
            if response.mensagem != "":
                print(response.mensagem)
            else:
                print("Ra: " + str(response.ra) + "\nCodigo da Disciplina:" + str(response.codigoDisciplina) + "\nAno: " + str(response.ano) + "\nSemestre: " + str(response.semestre) + "\nFaltas: " + str(response.faltas))
        else:
            print("Algum dos campos não foi preenchido corretamente")

    elif opcao == LISTAR_DISCIPLINAS_CURSO:
        request = gerenciamentoNotas_pb2.ListarDisciplinasCursoRequest()
        codigoCurso = input("Digite o código do curso: ")
        if codigoCurso.isdigit() and codigoCurso != "":
            request.codigoCurso = int(codigoCurso)
            response = stub.ListarDisciplinasCurso(request)
            if response.mensagem != "":
                print(response.mensagem)
            else:
                for i in range(len(response.disciplinas)):
                    print("Código da Disciplina: " + str(response.disciplinas[i].codigoDisciplina) + "\nNome: " + str(response.disciplinas[i].nome) + "\nProfessor: " + str(response.disciplinas[i].professor))
                    print("-------------------------------")
        else:
            print("Algum dos campos não foi preenchido corretamente")

    elif opcao == LISTAR_DISCIPLINAS_ALUNO:
        request = gerenciamentoNotas_pb2.ListarDisciplinasAlunoRequest()
        ra = input("Digite o RA do aluno: ")
        ano = input("Digite o ano: ")
        semestre = input("Digite o semestre: ")
        if ra.isdigit() and ano.isdigit() and semestre.isdigit() and ra != '' and ano != '' and semestre != '':
            request.ra = int(ra)
            request.ano = int(ano)
            request.semestre = int(semestre)
            response = stub.ListarDisciplinasAluno(request)
            if response.mensagem != "":
                print(response.mensagem)
            else:
                for i in range(len(response.disciplinas)):
                        print("Ra: " + str(response.disciplinas[i].ra) + "\nCódigo da Disciplina: " + str(response.disciplinas[i].codigoDisciplina) + "\nNota: " + str(response.disciplinas[i].nota) + "\nFaltas: " + str(response.disciplinas[i].faltas))
                        print("------------------------------")
        else:
            print("Algum dos campos não foi preenchido corretamente")

    elif opcao == INSERIR_MATRICULA:
        request = gerenciamentoNotas_pb2.InserirMatriculaRequest()
        ra = input("Digite o código do aluno: ")
        codigoDisciplina = input("Digite o código da disciplina: ")
        ano = input("Digite o ano da disciplina: ")
        semestre = input("Digite o semestre da disciplina: ")
        if ra.isdigit() and ano.isdigit() and semestre.isdigit() and codigoDisciplina != "" and ano != "" and semestre != "" and ra != "":
            request.matricula.ra = int(ra)
            request.matricula.codigoDisciplina = codigoDisciplina
            request.matricula.ano = int(ano)
            request.matricula.semestre = int(semestre)
            response = stub.InserirMatricula(request)
            if response.mensagem != "":
                print(response.mensagem)
            else:
                print("Ra: " + str(response.matricula.ra) + "\nCodigo da Disciplina:" + str(response.matricula.codigoDisciplina) + "\nAno: " + str(response.matricula.ano) + "\nSemestre: " + str(response.matricula.semestre))
        else:
            print("Algum dos campos não foi preenchido corretamente")
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
            dadosRequisicao(opcao)
        else:
            print("Opção inválida")

main()
