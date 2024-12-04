/*
Jean Carlos Martins Miguel
Matheus Vinicius da Costa
Data de Criação: 12/05/2022
Ultima alteração: 23/05/2022
*/

import io.grpc.stub.StreamObserver;
import java.sql.*;

//
public class GerenciadorDeNotasImpl extends GerenciadorDeNotasGrpc.GerenciadorDeNotasImplBase  { 
    // conecta com o banco de dados
    static final Connection connection = connect();
    
    // cria conexão com o banco de dados
    public static Connection connect() {
        Connection conn = null;

        try {
            
            String url = "jdbc:sqlite:../database_com_dados-contrib-Daniel-Farina.db";   
            conn = DriverManager.getConnection(url);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }

    @Override
    public void listarAlunos(ListarAlunosRequest request, StreamObserver<ListarAlunosResponse> responseObserver) {
        ListarAlunosResponse.Builder response = ListarAlunosResponse.newBuilder();
        try{
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT a.* FROM matricula m INNER JOIN aluno a ON (a.ra = m.ra) WHERE '" + String.valueOf(request.getCodigoDisciplina()) + "' = cod_disciplina AND " + request.getAno() + " = ano AND " + request.getSemestre() + " = semestre;");
    
            if(!rs.isBeforeFirst()){
          
                throw new SQLException("Não há alunos matriculados nessa disciplina");
            }
    
            while(rs.next()){
                int ra = rs.getInt("ra");
                String nome = rs.getString("nome");
                int periodo = rs.getInt("periodo");
                response.addAlunos(ListarAlunosResponse.Aluno.newBuilder().setNome(nome).setRa(ra).setPeriodo(periodo).build());
            }

        }

        catch(SQLException e){
            response.setMensagem(e.getMessage());
        }
      

    @Override
    public void alterarNota(AlterarNotaRequest request, StreamObserver<AlterarNotaResponse> responseObserver) {
        AlterarNotaResponse.Builder response = AlterarNotaResponse.newBuilder();
        try{
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM matricula WHERE ra = " + request.getRa() + " AND '" + String.valueOf(request.getCodigoDisciplina()) + "' = cod_disciplina AND " + request.getAno() + " = ano AND " + request.getSemestre() + " = semestre;");
            if(!rs.isBeforeFirst()){
                throw new SQLException("O aluno não foi encontrado na disciplina informada");
            }
            statement.execute("UPDATE matricula SET nota = " + request.getNota() + " WHERE ra =" + request.getRa() + " AND cod_disciplina = '" + String.valueOf(request.getCodigoDisciplina()) + "' AND ano = " + request.getAno() + " AND semestre = " + request.getSemestre() + ";");
            rs = statement.executeQuery("SELECT * FROM matricula WHERE ra = " + request.getRa() + " AND '" + String.valueOf(request.getCodigoDisciplina()) + "' = cod_disciplina AND " + request.getAno() + " = ano AND " + request.getSemestre() + " = semestre;");
            response.setRa(rs.getInt("ra"));
            response.setAno(rs.getInt("ano"));
            response.setSemestre(rs.getInt("semestre"));
            response.setCodigoDisciplina(rs.getString("cod_disciplina"));
            response.setNota(rs.getFloat("nota"));
        }
        catch(SQLException e){
            response.setMensagem(e.getMessage());
        }
        AlterarNotaResponse res = response.build();
        responseObserver.onNext(res);
        responseObserver.onCompleted();
    }

    @Override
    public void alterarFaltas(AlterarFaltasRequest request, StreamObserver<AlterarFaltasResponse> responseObserver) {
        AlterarFaltasResponse.Builder response = AlterarFaltasResponse.newBuilder();
    
        try{
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM matricula WHERE ra = " + request.getRa() + " AND '" + String.valueOf(request.getCodigoDisciplina()) + "' = cod_disciplina AND " + request.getAno() + " = ano AND " + request.getSemestre() + " = semestre;");
            if(!rs.isBeforeFirst()){
                throw new SQLException("O aluno não foi encontrado na disciplina informada");
            }
            statement.execute("UPDATE matricula SET faltas = " + request.getFaltas() + " WHERE ra =" + request.getRa() + " AND cod_disciplina = '" + String.valueOf(request.getCodigoDisciplina()) + "' AND ano = " + request.getAno() + " AND semestre = " + request.getSemestre() + ";");
            rs = statement.executeQuery("SELECT * FROM matricula WHERE ra = " + request.getRa() + " AND '" + String.valueOf(request.getCodigoDisciplina()) + "' = cod_disciplina AND " + request.getAno() + " = ano AND " + request.getSemestre() + " = semestre;");
            response.setRa(rs.getInt("ra"));
            response.setAno(rs.getInt("ano"));
            response.setSemestre(rs.getInt("semestre"));
            response.setCodigoDisciplina(rs.getString("cod_disciplina"));
            response.setFaltas(rs.getInt("faltas"));
        }
        catch(SQLException e){
            response.setMensagem(e.getMessage());
        }
        AlterarFaltasResponse res = response.build();
        responseObserver.onNext(res);
        responseObserver.onCompleted();
    }
    
    @Override
    public void listarDisciplinasCurso(ListarDisciplinasCursoRequest request, StreamObserver<ListarDisciplinasCursoResponse> responseObserver) {
        ListarDisciplinasCursoResponse.Builder response = ListarDisciplinasCursoResponse.newBuilder();
        try{
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM disciplina WHERE " + String.valueOf(request.getCodigoCurso()) + " = cod_curso;");
            if(!rs.isBeforeFirst()){
                throw new SQLException("Não foi encontrada nenhuma disciplina para o curso informado");
            }
            while(rs.next()){
                String professor = rs.getString("professor");
                String nome = rs.getString("nome");
                String codigo = rs.getString("codigo");
                response.addDisciplinas(ListarDisciplinasCursoResponse.DisciplinaCurso.newBuilder().setNome(nome).setCodigoDisciplina(codigo).setProfessor(professor).build());
            }

        }
        catch(SQLException e){
            response.setMensagem(e.getMessage());
        }
        ListarDisciplinasCursoResponse res = response.build();
        responseObserver.onNext(res);
        responseObserver.onCompleted();
    }

    @Override
    public void listarDisciplinasAluno(ListarDisciplinasAlunoRequest request, StreamObserver<ListarDisciplinasAlunoResponse> responseObserver) {
       //responseObserver usado pelo stub cliente e serviço q envia/recebe mensagem
               ListarDisciplinasAlunoResponse.Builder response = ListarDisciplinasAlunoResponse.newBuilder();
    
        try{
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM matricula WHERE " + request.getRa() + " = ra AND " + request.getAno() + " = ano AND " + request.getSemestre() + " = semestre;");
            if(!rs.isBeforeFirst()){
                throw new SQLException("O aluno informado não está cadastrado em nenhuma disciplina no ano e semetre informados");
            }
            while(rs.next()){
                String codigoDisciplinaResult = rs.getString("cod_disciplina");
                float nota = rs.getFloat("nota");
                int faltas = rs.getInt("faltas");
                response.addDisciplinas(ListarDisciplinasAlunoResponse.DisciplinaAlunos.newBuilder().setRa(request.getRa()).setCodigoDisciplina(codigoDisciplinaResult).setNota(nota).setFaltas(faltas).build());
            }

        }
        catch(SQLException e){
            response.setMensagem(e.getMessage());
        }
        ListarDisciplinasAlunoResponse res = response.build();
        responseObserver.onNext(res);
        responseObserver.onCompleted();
    }

    @Override
    public void inserirMatricula(InserirMatriculaRequest request, StreamObserver<InserirMatriculaResponse> responseObserver) {
        InserirMatriculaResponse.Builder response = InserirMatriculaResponse.newBuilder();
        try{
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM disciplina WHERE '" + String.valueOf(request.getMatricula().getCodigoDisciplina()) + "' = codigo;");
            if(!rs.isBeforeFirst()){
                throw new SQLException("A disciplina informada não existe");
            }

            rs = statement.executeQuery("SELECT * FROM aluno WHERE " + request.getMatricula().getRa() + " = ra;");
            if(!rs.isBeforeFirst()){
                throw new SQLException(" aluno  não existe");
            }

            rs = statement.executeQuery("SELECT * FROM matricula WHERE " + request.getMatricula().getRa() + " = ra AND '" + String.valueOf(request.getMatricula().getCodigoDisciplina()) + "' = cod_disciplina AND " + request.getMatricula().getAno() + " = ano AND " + request.getMatricula().getSemestre() + " = semestre;");
            if(rs.isBeforeFirst()){
                throw new SQLException("O aluno já está matriculado nessa disciplina");
            }

            statement.execute("INSERT INTO matricula (ra, cod_disciplina, ano, semestre, nota, faltas) VALUES (" + request.getMatricula().getRa() + ", '" + String.valueOf(request.getMatricula().getCodigoDisciplina()) + "', " + request.getMatricula().getAno() + ", " + request.getMatricula().getSemestre() + ", 0, 0);");
            rs = statement.executeQuery("SELECT * FROM matricula WHERE " + request.getMatricula().getRa() + " = ra AND '" + String.valueOf(request.getMatricula().getCodigoDisciplina()) + "' = cod_disciplina AND " + request.getMatricula().getAno() + " = ano AND " + request.getMatricula().getSemestre() + " = semestre;");
            if(!rs.isBeforeFirst()){
                throw new SQLException("Não foi possível realizar a matrícula");
            }
            while(rs.next()){
                int raResult = rs.getInt("ra");
                int anoResult = rs.getInt("ano");
                int semestreResult = rs.getInt("semestre");
                String codigoDisciplinaResult = rs.getString("cod_disciplina");
                float notaResult = rs.getFloat("nota");
                int faltasResult = rs.getInt("faltas");
                response.setMatricula(Matricula.newBuilder().setRa(raResult).setAno(anoResult).setSemestre(semestreResult).setCodigoDisciplina(codigoDisciplinaResult).setNota(notaResult).setFaltas(faltasResult).build());
            }

        }
        
        catch(SQLException e){
            response.setMensagem(e.getMessage());
        }
       
        InserirMatriculaResponse res = response.build();
        responseObserver.onNext(res);
        responseObserver.onCompleted();
    }
}
}