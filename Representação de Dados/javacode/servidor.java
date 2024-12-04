/*
    Data de criação:25/04/2022
Ultima modificação:10/05/2022
Autores:
    @jeammiguel
    @morpheus6556
*/

import java.net.*;
import java.io.*;
import java.sql.*;

public class servidor {
    static final int INSERIR_MATRICULA = 1;
    static final int ALTERAR_NOTA = 2;
    static final int ALTERAR_FALTAS = 3;
    static final int LISTAR_ALUNOS = 4;
    static final int LISTAR_DISCIPLINAS_ALUNO = 5;
    static final int LISTAR_DISCIPLINAS_CURSO = 6;

    
    public static Connection connect() {
        Connection conn = null;

        try {
            String url = "jdbc:sqlite:../database_com_dados-contrib-Daniel-Farina.db";

            // faz conexão com o banco
            conn = DriverManager.getConnection(url);

            System.out.println("Conexão com o banco realizada com sucesso!!!");

        } catch (SQLException e) {
            // se der algum problema quando tentar fazer conexão com o banco
            System.out.println(e.getMessage());
        }

        return conn;
    }

    // exibe alunos de uma determinada disciplina
    public static GerenciamentoNotas.listarAlunosResponse listarAlunos(Connection connection, String codigoDisciplina, int ano, int semestre) {        
        GerenciamentoNotas.listarAlunosResponse.Builder response = GerenciamentoNotas.listarAlunosResponse.newBuilder();
        try{
            // statement para realizar a consulta
            Statement statement = connection.createStatement();

            // procura todos os alunos matriculados na matéria
            ResultSet rs = statement.executeQuery("SELECT a.* FROM matricula m INNER JOIN aluno a ON (a.ra = m.ra) WHERE '" + String.valueOf(codigoDisciplina) + "' = cod_disciplina AND " + ano + " = ano AND " + semestre + " = semestre;");
            // se não achar nenhum aluno exibe uma mensagem 
            if(!rs.isBeforeFirst()){
                response.setMensagem("Não existem alunos matriculados na disciplina!!!");
                return response.build();
            }
            // objeto responsável por lhe dar com a resposta
            while(rs.next()){
                int ra = rs.getInt("ra");
                String nome = rs.getString("nome");
                int periodo = rs.getInt("periodo");
                response.addAlunos(GerenciamentoNotas.listarAlunosResponse.Aluno.newBuilder().setNome(nome).setRa(ra).setPeriodo(periodo).build());
            }

        }
        catch(SQLException e){
            response.setMensagem(e.getMessage());
        }
        return response.build();
    }

    // alterar a nota do aluno
    public static GerenciamentoNotas.alterarNotaResponse alterarNota(Connection connection, int ra, String codigoDisciplina, int ano, int semestre , float nota) {        
        GerenciamentoNotas.alterarNotaResponse.Builder response = GerenciamentoNotas.alterarNotaResponse.newBuilder();
        // tenta realizar a execução
        try{
            //  statement para realizar a consulta
            Statement statement = connection.createStatement();
            // verificar se o aluno está matriculado na matéria
            ResultSet rs = statement.executeQuery("SELECT * FROM matricula WHERE ra = " + ra + " AND '" + String.valueOf(codigoDisciplina) + "' = cod_disciplina AND " + ano + " = ano AND " + semestre + " = semestre;");
            // se o aluno não está matriculado na materia
            if(!rs.isBeforeFirst()){
                response.setMensagem("O aluno não foi encontrado na disciplina!!");
                return response.build();
            }
            // atualizar a nota
            statement.execute("UPDATE matricula SET nota = " + nota + " WHERE ra =" + ra + " AND cod_disciplina = '" + String.valueOf(codigoDisciplina) + "' AND ano = " + ano + " AND semestre = " + semestre + ";");
            // fazer busca da nota para retornar o aluno com a nova nota
            rs = statement.executeQuery("SELECT * FROM matricula WHERE ra = " + ra + " AND '" + String.valueOf(codigoDisciplina) + "' = cod_disciplina AND " + ano + " = ano AND " + semestre + " = semestre;");
            // objeto para lhe dar com a resposta                        break;

            response.setRa(rs.getInt("ra"));
            response.setAno(rs.getInt("ano"));
            response.setSemestre(rs.getInt("semestre"));
            response.setCodigoDisciplina(rs.getString("cod_disciplina"));
            response.setNota(rs.getFloat("nota"));
        }

        catch(SQLException e){
            response.setMensagem(e.getMessage());
        }
        return response.build();
    }

    // alterar faltas
    public static GerenciamentoNotas.alterarFaltasResponse alterarFaltas(Connection connection, int ra, String codigoDisciplina, int ano, int semestre, int faltas) {        
        GerenciamentoNotas.alterarFaltasResponse.Builder response = GerenciamentoNotas.alterarFaltasResponse.newBuilder();
        try{
            // statement para realizar a consulta
            Statement statement = connection.createStatement();
            // verificar se o aluno está matriculado na matéria
            ResultSet rs = statement.executeQuery("SELECT * FROM matricula WHERE ra = " + ra + " AND '" + String.valueOf(codigoDisciplina) + "' = cod_disciplina AND " + ano + " = ano AND " + semestre + " = semestre;");
            // caso não o aluno não esteja matriculado na materia exibir uma mensagem de erro
            if(!rs.isBeforeFirst()){
                response.setMensagem("O aluno não foi encontrado nessa disciplina!!!!");
                return response.build();
            }
            // atualizar  faltas
            statement.execute("UPDATE matricula SET faltas = " + faltas + " WHERE ra =" + ra + " AND cod_disciplina = '" + String.valueOf(codigoDisciplina) + "' AND ano = " + ano + " AND semestre = " + semestre + ";");
            // buscr  a nota para retornar o aluno com as faltas atualizadas
            rs = statement.executeQuery("SELECT * FROM matricula WHERE ra = " + ra + " AND '" + String.valueOf(codigoDisciplina) + "' = cod_disciplina AND " + ano + " = ano AND " + semestre + " = semestre;");
            // criar objeto resposta
            response.setRa(rs.getInt("ra"));
            response.setAno(rs.getInt("ano"));
            response.setSemestre(rs.getInt("semestre"));
            response.setCodigoDisciplina(rs.getString("cod_disciplina"));
            response.setFaltas(rs.getInt("faltas"));
        }
        catch(SQLException e){
            response.setMensagem(e.getMessage());
        }
        return response.build();
    }

    // mostrar disciplinas de um curso
    public static GerenciamentoNotas.listarDisciplinasCursoResponse listarDisciplinasCurso(Connection connection, int codigoCurso) {        
        // criar um builder para a resposta
        GerenciamentoNotas.listarDisciplinasCursoResponse.Builder response = GerenciamentoNotas.listarDisciplinasCursoResponse.newBuilder();
        try{
            
            Statement statement = connection.createStatement();
            // procurar as  disciplinas de um curso
            ResultSet rs = statement.executeQuery("SELECT * FROM disciplina WHERE " + String.valueOf(codigoCurso) + " = cod_curso;");
            // verifica se foi encontrado alguma disciplina
            if(!rs.isBeforeFirst()){
                response.setMensagem("Não foi encontrada nenhuma disciplina para o curso desejado!!");
                return response.build();
            }
            // criar objeto para lhe dar com resposta
            while(rs.next()){
                String professor = rs.getString("professor");
                String nome = rs.getString("nome");
                String codigo = rs.getString("codigo");
                response.addDisciplinas(GerenciamentoNotas.listarDisciplinasCursoResponse.DisciplinaCurso.newBuilder().setNome(nome).setCodigoDisciplina(codigo).setProfessor(professor).build());
            }

        }
        catch(SQLException e){
            response.setMensagem(e.getMessage());
        }
        return response.build();
    }

    //exibir as disciplinas de um determinado aluno
    public static GerenciamentoNotas.listarDisciplinasAlunoResponse listarDisciplinasAluno(Connection connection, int ra, int ano, int semestre) {     
        // criar  builder para a resposta   
        GerenciamentoNotas.listarDisciplinasAlunoResponse.Builder response = GerenciamentoNotas.listarDisciplinasAlunoResponse.newBuilder();
        try{
            Statement statement = connection.createStatement();
            // procurar as disciplinas do aluno baseado  no ra,ano e semestre
            ResultSet rs = statement.executeQuery("SELECT * FROM matricula WHERE " + ra + " = ra AND " + ano + " = ano AND " + semestre + " = semestre;");
            if(!rs.isBeforeFirst()){
                response.setMensagem("O aluno informado não está cadastrado em nenhuma disciplina no ano e semetre informados");
                return response.build();
            }
            // criar  objeto para a resposta
            while(rs.next()){
                String codigoDisciplinaResult = rs.getString("cod_disciplina");
                float nota = rs.getFloat("nota");
                int faltas = rs.getInt("faltas");
                response.addDisciplinas(GerenciamentoNotas.listarDisciplinasAlunoResponse.DisciplinaAlunos.newBuilder().setRa(ra).setCodigoDisciplina(codigoDisciplinaResult).setNota(nota).setFaltas(faltas).build());
            }

        }
        catch(SQLException e){
            response.setMensagem(e.getMessage());
        }
        return response.build();
    }

    // inserir nova matrícula
    public static GerenciamentoNotas.inserirMatriculaResponse inserirMatricula(Connection connection, int ra,String codigoDisciplina, int ano, int semestre) {
        GerenciamentoNotas.inserirMatriculaResponse.Builder response = GerenciamentoNotas.inserirMatriculaResponse.newBuilder();
        try{
            Statement statement = connection.createStatement();
            // verificar se a disciplina solicitada  existe
            ResultSet rs = statement.executeQuery("SELECT * FROM disciplina WHERE '" + String.valueOf(codigoDisciplina) + "' = codigo;");
            if(!rs.isBeforeFirst()){
                response.setMensagem("A disciplina informada não existe!!");
                return response.build();
            }
            rs = statement.executeQuery("SELECT * FROM aluno WHERE " + ra + " = ra;");
            if(!rs.isBeforeFirst()){
                response.setMensagem("O aluno informado não existe");
                return response.build();
            }

            // verificar se  aluno matriculado na disciplina
            rs = statement.executeQuery("SELECT * FROM matricula WHERE " + ra + " = ra AND '" + String.valueOf(codigoDisciplina) + "' = cod_disciplina AND " + ano + " = ano AND " + semestre + " = semestre;");
            // caso esteja matriculado, retorna mensagem de erro
            if(rs.isBeforeFirst()){
                response.setMensagem("O aluno já está matriculado na disciplina!!");
                return response.build();
            }

            // inserção na tabela de matricula
            statement.execute("INSERT INTO matricula (ra, cod_disciplina, ano, semestre, nota, faltas) VALUES (" + ra + ", '" + String.valueOf(codigoDisciplina) + "', " + ano + ", " + semestre + ", 0, 0);");
            // consulta para verificar se a matricula foi inserida
            rs = statement.executeQuery("SELECT * FROM matricula WHERE " + ra + " = ra AND '" + String.valueOf(codigoDisciplina) + "' = cod_disciplina AND " + ano + " = ano AND " + semestre + " = semestre;");
            if(!rs.isBeforeFirst()){
                // mensagem de erro
                response.setMensagem("Não foi possível realizar a matrícula");
                return response.build();
            }
            // objeto de resposta
            while(rs.next()){
                int raResult = rs.getInt("ra");
                int anoResult = rs.getInt("ano");
                int semestreResult = rs.getInt("semestre");
                String codigoDisciplinaResult = rs.getString("cod_disciplina");
                float notaResult = rs.getFloat("nota");
                int faltasResult = rs.getInt("faltas");
                response.setMatricula(GerenciamentoNotas.Matricula.newBuilder().setRa(raResult).setAno(anoResult).setSemestre(semestreResult).setCodigoDisciplina(codigoDisciplinaResult).setNota(notaResult).setFaltas(faltasResult).build());
            }

        }
        catch(SQLException e){
            response.setMensagem(e.getMessage());
        }
        return response.build();
    }

    public static void main(String args[]) {
        // criar conexão com  banco
        Connection conn = connect();
        try {
            int serverPort = 6667;
            ServerSocket listenSocket = new ServerSocket(serverPort);
            // Váriveis para o receber as mensagens
            String valueStr;
            int sizeBuffer;
            byte[] buffer;

            // Váriaveis para o enviar mensagens
            byte[] msg;
            String responseSize;
            byte[] size;
            // recebe conexão com  socket nas portas definidas
            Socket clientSocket = listenSocket.accept();
            // criar conexão com o client para receber mensagens
            DataInputStream inClient = new DataInputStream(clientSocket.getInputStream());
            // criar conexão com o client para enviar mensagens
            DataOutputStream outClient = new DataOutputStream(clientSocket.getOutputStream());
            while (true) {
                // tamanho do buffer
                valueStr = inClient.readLine();
                // converter para int
                sizeBuffer = Integer.valueOf(valueStr);
                // criar buffer com  tamanho da mensagem
                buffer = new byte[sizeBuffer];
                // ler buffer
                inClient.read(buffer);

                // unmarshalling
                GerenciamentoNotas.requestType type = GerenciamentoNotas.requestType.parseFrom(buffer);

                valueStr = inClient.readLine();
                sizeBuffer = Integer.valueOf(valueStr);
                buffer = new byte[sizeBuffer];
                inClient.read(buffer);

                switch (type.getType()) {
                    case INSERIR_MATRICULA:
                        // unmarshalling
                        GerenciamentoNotas.inserirMatriculaRequest inserirMatriculaRequest = GerenciamentoNotas.inserirMatriculaRequest.parseFrom(buffer);
                        GerenciamentoNotas.inserirMatriculaResponse inserirMatriculaResponse = inserirMatricula(conn, inserirMatriculaRequest.getMatricula().getRa(), inserirMatriculaRequest.getMatricula().getCodigoDisciplina(), inserirMatriculaRequest.getMatricula().getAno(), inserirMatriculaRequest.getMatricula().getSemestre());
                        msg = inserirMatriculaResponse.toByteArray();
                        responseSize = String.valueOf(msg.length) + "\n";
                        outClient.write(responseSize.getBytes());
                        outClient.write(msg);
                        break;
                    case ALTERAR_NOTA:
                        //  unmarshalling
                        GerenciamentoNotas.alterarNotaRequest alterarNotaRequest = GerenciamentoNotas.alterarNotaRequest.parseFrom(buffer);
                        // chama a função que altera a nota de um aluno
                        GerenciamentoNotas.alterarNotaResponse alterarNotaResponse = alterarNota(conn, alterarNotaRequest.getRa(), alterarNotaRequest.getCodigoDisciplina(), alterarNotaRequest.getAno(), alterarNotaRequest.getSemestre(), alterarNotaRequest.getNota());
                        // converte o resultado da função para bytes
                        msg = alterarNotaResponse.toByteArray();
                        //converter resultado para string
                        responseSize = String.valueOf(msg.length) + "\n";
                        // envia o tamanho do resultado em bytes
                        outClient.write(responseSize.getBytes());
                        // envia o resultado em bytes
                        outClient.write(msg);
                        break;
                    case ALTERAR_FALTAS:
                        //  unmarshalling
                        GerenciamentoNotas.alterarFaltasRequest alterarFaltasRequest = GerenciamentoNotas.alterarFaltasRequest.parseFrom(buffer);
                        GerenciamentoNotas.alterarFaltasResponse alterarFaltasResponse = alterarFaltas(conn, alterarFaltasRequest.getRa(), alterarFaltasRequest.getCodigoDisciplina(), alterarFaltasRequest.getAno(), alterarFaltasRequest.getSemestre(), alterarFaltasRequest.getFaltas());
                        msg = alterarFaltasResponse.toByteArray();
                        responseSize = String.valueOf(msg.length) + "\n";
                        outClient.write(responseSize.getBytes());
                        outClient.write(msg);
                        break;
                    case LISTAR_ALUNOS:
                        //  unmarshalling
                        GerenciamentoNotas.listarAlunosRequest request = GerenciamentoNotas.listarAlunosRequest.parseFrom(buffer);
                        // função para listar os alunos
                        GerenciamentoNotas.listarAlunosResponse response = listarAlunos(conn, request.getCodigoDisciplina() , request.getAno(), request.getSemestre());
                        // converter o resultado da função para bytes
                        msg = response.toByteArray();
                        // obtem o tamanho do resultado e converte para string
                        responseSize = String.valueOf(msg.length) + "\n";
                        // envia o tamanho do resultado em bytes
                        outClient.write(responseSize.getBytes());
                        // envia o resultado em bytes
                        outClient.write(msg);
                        break;
                    case LISTAR_DISCIPLINAS_ALUNO:
                        //  unmarshalling
                        GerenciamentoNotas.listarDisciplinasAlunoRequest listarDisciplinasAlunoRequest = GerenciamentoNotas.listarDisciplinasAlunoRequest.parseFrom(buffer);
                        GerenciamentoNotas.listarDisciplinasAlunoResponse listarDisciplinasAlunoResponse = listarDisciplinasAluno(conn, listarDisciplinasAlunoRequest.getRa(), listarDisciplinasAlunoRequest.getAno(), listarDisciplinasAlunoRequest.getSemestre());
                        msg = listarDisciplinasAlunoResponse.toByteArray();
                        responseSize = String.valueOf(msg.length) + "\n";
                        outClient.write(responseSize.getBytes());
                        outClient.write(msg);
                        break;

                    case LISTAR_DISCIPLINAS_CURSO:
                        // unmarshalling
                        GerenciamentoNotas.listarDisciplinasCursoRequest listarDisciplinasCursoRequest = GerenciamentoNotas.listarDisciplinasCursoRequest.parseFrom(buffer);
                        GerenciamentoNotas.listarDisciplinasCursoResponse listarDisciplinasCursoResponse = listarDisciplinasCurso(conn, listarDisciplinasCursoRequest.getCodigoCurso());
                        msg = listarDisciplinasCursoResponse.toByteArray();
                        responseSize = String.valueOf(msg.length) + "\n";
                        outClient.write(responseSize.getBytes());
                        outClient.write(msg);
                         break;
                    default:
    
                        System.out.println("Nenhuma requisição");
                        break;
                }
            } 
        } catch (IOException e) {
        
            System.out.println("Listensocket:" + e.getMessage());
        } 
    }
}
