/*Jean Carlos Martins Miguel
  Matheus Vinicius da Costa
Data de Criação: 12/05/2022
Ultima alteração: 18/05/2022

*/

import io.grpc.ServerBuilder;
import java.io.IOException;

public class Server {
    public static void main(String[] args) {
            io.grpc.Server server = ServerBuilder
            .forPort(6667)
            //.addService(new GerenciadorDeNotasImpl())
            .build();
                       
        try {
            server.start();
            System.out.println("Servidor inicializado com sucesso!!");
            server.awaitTermination();
        } catch (IOException | InterruptedException e) {
            System.err.println("Erro: " + e);
        }
        
    }
}
