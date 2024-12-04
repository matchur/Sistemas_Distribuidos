/*Autores:
 Jean Carlos Martins Miguel
  Matheus Vinicius da Costa

Data de Criação: 23/04/2022
Ultima alteração: 04/05/2022
*/
import java.sql.*;
import java.net.*;
import java.io.*;



public class ServerSD {
    static Connection db_connection;
   
    public static void main (String args[]) {
        try {
            // Conectar com o bd
            db_connection = SQLiteJDBCDriverConnection.connect();

            int PortaServidor = 6667; 
            
            // porta em listening
            ServerSocket listenSocket = new ServerSocket(PortaServidor);
            while (true) {
                // receber socket
                Socket clientSocket = listenSocket.accept();
                
                /* thread responsável pela conexão corrente*/
                ClientMessages t = new ClientMessages(clientSocket, db_connection);

                /* dar start na thread */
                t.start();
            }
        } catch (IOException e) {
            System.out.println("Listen socket:" + e.getMessage());
        } 
    }
}

class ClientMessages extends Thread {
   
    Socket clientSocket;
    Connection db_connection;


    //DataInputStream isCliente;
    BufferedReader isCliente;
    Reader isClienteReader;
    DataOutputStream osCliente;
    public ClientMessages(Socket clientSocket, Connection db_connection) {
        try {
            this.clientSocket = clientSocket;
            this.db_connection = db_connection;
            
            //isCliente = new DataInputStream(clientSocket.getInputStream());
            isCliente = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            isClienteReader = isCliente;
            osCliente = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException ioe) {
            System.out.println("Conexão:" + ioe.getMessage());
        }
    }
    
    public void run() {
        while (true) {
            try {
                String typeOperacao = isCliente.readLine();
                String decoded = new String(typeOperacao.getBytes("UTF-8"));
                Integer operacao = Integer.parseInt(decoded);
                
                if (operacao == 1) {
                    String valueStr = isCliente.readLine();
                    int tamanhoBuffer = Integer.valueOf(valueStr);
                    byte[] buffer = new byte[tamanhoBuffer];
                    isClienteReader.read(buffer);

                    Database.Matricula dbServer = Database.Matricula.parseFrom(buffer);
                    System.out.println("--\n" +  dbServer + "--\n");
                    
                    String flagErro = ""; //trata os erros

                    if(dbServer.getCodDisciplina() == ""){
                        System.out.println("Codigo da disciplina é invalido\n");
                        flagErro += "Codigo da Disciplina é invalido\n";
                    }
                    else if(dbServer.getRa() < 1){
                        System.out.println("RA invalido\n");
                        flagErro += "RA invalido\n";
                    }
                    else if(dbServer.getAno() < 0){
                        System.out.println("Ano invalido\n");
                        flagErro += "Ano invalido\n";
                    }
                    else if(dbServer.getSemestre() < 1){
                        System.out.println("Semestre invalido\n");
                        flagErro += "Semestre invalido\n";
                    }

                    if(flagErro != ""){
                        osCliente.write(flagErro.getBytes());
                        continue;
                    }

                    Integer ra = dbServer.getRa();
                    String cod_disciplina = dbServer.getCodDisciplina();
                    Integer ano = dbServer.getAno();
                    Integer semestre = dbServer.getSemestre();

                    try {
                        Statement statement = db_connection.createStatement();
                        ResultSet resultSet = statement.executeQuery("SELECT ra FROM aluno WHERE ra="+ra);
                        if(resultSet.isBeforeFirst()){
                            resultSet = statement.executeQuery("SELECT codigo FROM disciplina WHERE codigo='"+cod_disciplina+"'");
                            if(resultSet.isBeforeFirst()){
                                statement.executeUpdate("INSERT INTO matricula( ra, cod_disciplina, ano, semestre, nota, faltas) VALUES ( "+ra+", '"+cod_disciplina+"', "+ano+", "+semestre+", 0.0, 0)");
                                byte[] bytes = ("Matricula inserida:\nRA:"+ra+"\nCodigo da disciplina:"+cod_disciplina+"\nAno:"+ano+"\nSemestre:"+semestre+"\nNota:0.0\nFaltas:0").getBytes();
                                osCliente.write(bytes);
                            }else{
                                System.out.println("Disciplina nao existe");
                                osCliente.write(("Disciplina nao existe").getBytes());
                            }
                        }else{
                            System.out.println("Aluno nao existe");
                            osCliente.write(("Aluno nao existe").getBytes());
                        }

                    } catch (SQLException e) {
                        System.out.println(e);
                    }
                } 
                else if (operacao == 2) {
                    String valueStr = isCliente.readLine();
                    int tamanhoBuffer = Integer.valueOf(valueStr);
                    byte[] buffer = new byte[tamanhoBuffer];
                    isCliente.read(buffer);

                    Database.Matricula  dbServer = Database.Matricula.parseFrom(buffer);
                    System.out.println("--\n" +  dbServer + "--\n");
                    
                    String flagErro = "";
                    if( dbServer.getCodDisciplina() == ""){
                        System.out.println("Codigo de Disciplina invalido\n");
                        flagErro += "Codigo de Disciplina invalido\n";
                    }
                    else if(dbServer.getRa() < 1){
                        System.out.println("RA invalido\n");
                        flagErro += "RA invalido\n";
                    }
                    else if( dbServer.getAno() < 0){
                        System.out.println("Ano invalido\n");
                        flagErro += "Ano invalido\n";
                    }
                    else if(dbServer.getSemestre() < 1){
                        System.out.println("Semestre invalido\n");
                        flagErro += "Semestre invalido\n";
                    }
                    else if(dbServer.getNota() < 0){
                        System.out.println("Nota invalida\n");
                        flagErro += "Nota invalida\n";
                    }

                    if(flagErro != ""){
                        osCliente.write(flagErro.getBytes());
                        continue;
                    }

                    Integer ra =  dbServer.getRa();
                    String cod_disciplina =  dbServer.getCodDisciplina();
                    Integer ano =  dbServer.getAno();
                    Integer semestre =  dbServer.getSemestre();
                    Float nota =  dbServer.getNota();

                    try {
                        Statement statement = db_connection.createStatement();
                        ResultSet resultSet = statement.executeQuery("SELECT ra FROM aluno WHERE ra="+ra);
                        
                        if(resultSet.isBeforeFirst()){
                            resultSet = statement.executeQuery("SELECT codigo FROM disciplina WHERE codigo='"+cod_disciplina+"'");
                            if(resultSet.isBeforeFirst()){
                                statement.execute("UPDATE matricula SET nota="+nota+" WHERE cod_disciplina='"+cod_disciplina+"' and ra="+ra+" and ano="+ano+" and semestre="+semestre);
                                resultSet = statement.executeQuery("SELECT * FROM matricula WHERE cod_disciplina='"+cod_disciplina+"' and ra="+ra+" and ano="+ano+" and semestre="+semestre);
                                byte[] bytes = ("Nota atualizada:\nRA:"+resultSet.getInt("ra")+"\nCodigo da disciplina:"+resultSet.getString("cod_disciplina")+"\nAno:"+resultSet.getInt("ano")+"\nSemestre:"+resultSet.getInt("semestre")+"\nNota:"+resultSet.getFloat("nota")+"\nFaltas:"+resultSet.getInt("faltas")).getBytes();
                                osCliente.write(bytes);
                            }else{
                                System.out.println("Disciplina nao existe");
                                osCliente.write(("Disciplina nao existe").getBytes());
                            }
                        }else{
                            System.out.println("Aluno nao existe");
                            osCliente.write(("Aluno nao existe").getBytes());
                        }

                    } 
                    catch (SQLException e) {
                        System.out.println(e);
                    } 
                } 
                else if (operacao == 3) {
                    String valueStr = isCliente.readLine();
                    int tamanhoBuffer = Integer.valueOf(valueStr);
                    byte[] buffer = new byte[tamanhoBuffer];
                    isCliente.read(buffer);

                    Database.Matricula  dbServer = Database.Matricula.parseFrom(buffer);
                    System.out.println("--\n" +  dbServer + "--\n");
                    
                    String flagErro = "";
                    if( dbServer.getRa() < 1){
                        System.out.println("RA invalido\n");
                        flagErro += "RA invalido\n";
                    }
                    else if(dbServer.getCodDisciplina() == ""){
                        System.out.println("Codigo de Disciplina invalido\n");
                        flagErro += "Codigo de Disciplina invalido\n";
                    } 
                    else if(dbServer.getAno() < 0){
                        System.out.println("Ano invalido\n");
                        flagErro += "Ano invalido\n";
                    }
                    else if(dbServer.getSemestre() < 1){
                        System.out.println("Semestre invalido\n");
                        flagErro += "Semestre invalido\n";
                    }
                    else if(dbServer.getFaltas() < 0){
                        System.out.println("Faltas invalidas\n");
                        flagErro += "Faltas invalidas\n";
                    }

                    if(flagErro != ""){
                        osCliente.write(flagErro.getBytes());
                        continue;
                    }

                    Integer ra = dbServer.getRa();
                    String cod_disciplina =  dbServer.getCodDisciplina();
                    Integer ano =  dbServer.getAno();
                    Integer semestre =  dbServer.getSemestre();
                    Integer faltas =  dbServer.getFaltas();

                    try {
                        Statement statement = db_connection.createStatement();
                        ResultSet resultSet = statement.executeQuery("SELECT ra FROM aluno WHERE ra="+ra);
                        
                        if(resultSet.isBeforeFirst()){
                            resultSet = statement.executeQuery("SELECT codigo FROM disciplina WHERE codigo='"+cod_disciplina+"'");
                            if(resultSet.isBeforeFirst()){
                                statement.execute("UPDATE matricula SET faltas="+faltas+" WHERE cod_disciplina='"+cod_disciplina+"' and ra="+ra+" and ano="+ano+" and semestre="+semestre);
                                resultSet = statement.executeQuery("SELECT * FROM matricula WHERE cod_disciplina='"+cod_disciplina+"' and ra="+ra+" and ano="+ano+" and semestre="+semestre);
                                byte[] bytes = ("Nota atualizada:\nRA:"+resultSet.getInt("ra")+"\nCodigo da disciplina:"+resultSet.getString("cod_disciplina")+"\nAno:"+resultSet.getInt("ano")+"\nSemestre:"+resultSet.getInt("semestre")+"\nNota:"+resultSet.getFloat("nota")+"\nFaltas:"+resultSet.getInt("faltas")).getBytes();
                                osCliente.write(bytes);
                            }else{
                                System.out.println("Disciplina nao existe");
                                osCliente.write(("Disciplina nao existe").getBytes());
                            }
                        }else{
                            System.out.println("Aluno nao existe");
                            osCliente.write(("Aluno nao existe").getBytes());
                        }

                    } 
                    catch (SQLException e) {
                        System.out.println(e);
                    } 
                } 
                else if (operacao == 4) {
                    String valueStr = isCliente.readLine();
                    int tamanhoBuffer = Integer.valueOf(valueStr);
                    byte[] buffer = new byte[tamanhoBuffer];
                    isCliente.read(buffer);

                    Database.Matricula  dbServer = Database.Matricula.parseFrom(buffer);
                    System.out.println("--\n" +  dbServer + "--\n");
                    
                    String flagErro = "";
                    if(dbServer.getCodDisciplina() == ""){
                        System.out.println("Codigo de Disciplina invalido\n");
                        flagErro += "Codigo de Disciplina invalido\n";
                    }
                    else if(dbServer.getAno() < 0){
                        System.out.println("Ano invalido\n");
                        flagErro += "Ano invalido\n";
                    }
                    else if(dbServer.getSemestre() < 1){
                        System.out.println("Semestre invalido\n");
                        flagErro += "Semestre invalido\n";
                    }

                    if(flagErro != ""){
                        osCliente.write(flagErro.getBytes());
                        continue;
                    }

                    String cod_disciplina = dbServer.getCodDisciplina();
                    Integer ano =  dbServer.getAno();
                    Integer semestre =  dbServer.getSemestre();

                    try {
                        Statement statement = db_connection.createStatement();
                        ResultSet resultSet = statement.executeQuery("SELECT codigo FROM disciplina WHERE codigo='"+cod_disciplina+"'");
                        
                        if(resultSet.isBeforeFirst()){
                            resultSet = statement.executeQuery("SELECT a.ra, a.nome, a.periodo FROM matricula as  dbServer, aluno as a WHERE  dbServer.cod_disciplina='"+cod_disciplina+"' and  dbServer.ra=a.ra");
                            String bigArray = "RA\tNOME\tPERIODO\n";
                            while (resultSet.next()) {
                                Integer ra = resultSet.getInt("ra");
                                String nome = resultSet.getString("nome");
                                Integer periodo = resultSet.getInt("periodo");
                                
                                bigArray += ""+ra+"\t"+nome+"\t"+periodo+"\n";
                            }
                            osCliente.write(bigArray.getBytes());
                        }else{
                            System.out.println("Disciplina nao existe");
                            osCliente.write(("Disciplina nao existe").getBytes());
                        }

                    } 
                    catch (SQLException e) {
                        System.out.println(e);
                    } 
                } 
                else if (operacao == 5) {
                    String valueStr = isCliente.readLine();
                    int tamanhoBuffer = Integer.valueOf(valueStr);
                    byte[] buffer = new byte[tamanhoBuffer];
                    isCliente.read(buffer);

                    Database.Matricula  dbServer = Database.Matricula.parseFrom(buffer);
                    System.out.println("--\n" +  dbServer + "--\n");
                    
                    String flagErro = "";
                    if( dbServer.getRa() < 1){
                        System.out.println("RA invalido\n");
                        flagErro += "RA invalido\n";
                    }
                    else if(dbServer.getAno() < 0){
                        System.out.println("Ano invalido\n");
                        flagErro += "Ano invalido\n";
                    }
                    else if(dbServer.getSemestre() < 1){
                        System.out.println("Semestre invalido\n");
                        flagErro += "Semestre invalido\n";
                    }

                    if(flagErro != ""){
                        osCliente.write(flagErro.getBytes());
                        continue;
                    }

                    Integer ra =  dbServer.getRa();
                    Integer ano =  dbServer.getAno();
                    Integer semestre =  dbServer.getSemestre();

                    try {
                        Statement statement = db_connection.createStatement();
                        ResultSet resultSet = statement.executeQuery("SELECT ra FROM aluno WHERE ra="+ra);
                        if(resultSet.isBeforeFirst()){
                            ResultSet rs = statement.executeQuery("SELECT a.ra, a.nome,  dbServer.cod_disciplina,  dbServer.nota,  dbServer.faltas FROM matricula as  dbServer, aluno as a WHERE  dbServer.ra=a.ra and  dbServer.ra="+ra+" and  dbServer.ano="+ano+" and  dbServer.semestre="+semestre);
                            String bigArray = "RA\tNOME\tCOD_DISCIPLINA\tNOTA\tFALTAS\n";
                            while (rs.next()) {
                                Integer ra_res = resultSet.getInt("ra");
                                String nome = resultSet.getString("nome");
                                String cod_disciplina = resultSet.getString("cod_disciplina");
                                Float nota = resultSet.getFloat("nota");
                                Integer faltas = resultSet.getInt("faltas");
                                
                                bigArray += ""+ra_res+"\t"+nome+"\t"+cod_disciplina+"\t\t"+nota+"\t"+faltas+"\n";
                            }
                            System.out.println(bigArray);
                            osCliente.write(bigArray.getBytes());
                        }else{
                            System.out.println("Disciplina nao existe");
                            osCliente.write(("Disciplina nao existe").getBytes());
                        }

                    } 
                    catch (SQLException e) {
                        System.out.println(e);
                    } 
                } 
                else if (operacao == 6) {
                    break;
                } 
                else {
                    osCliente.write(("Informe uma operação válida").getBytes());
                }
            }
            catch (IOException e) {

            }
        }
    }
}