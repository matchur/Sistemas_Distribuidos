<h1 align="center">Representação externa de dados</h1>
<p href="#descricao" align="center">Exercícios da disciplina  de Sistemas Distribuidos.</p>

<div align="center">
  <img src="https://img.shields.io/badge/python-%23007ACC.svg?&style=for-the-badge&logo=python&logoColor=white"> 
  <img src="https://img.shields.io/badge/java-%23007ACC.svg?&style=for-the-badge&logo=java&logoColor=white">

	
</div>

[comment]: <> (<h4 align="center"> )


[comment]: <> (</h4>)

Conteúdos do trabalho
=================
<!--ts-->
   * [Pre Requisitos](#pre-requisitos)
   * [Executando o projeto](#executando-o-projeto)
   * [Bibliotecas](#bibliotecas-utilizadas)
   * [Exemplos de Uso](#exemplos-de-uso)
  
<!--te-->

Pre-requisitos
==============

Precisa-se ter instalado na sua máquina as seguintes ferramentas antes de executar o código:
- [Python](https://www.python.org/);
- [Java](https://www.java.com/pt-BR/);

Executando o projeto
====================

###  Executar o Servidor

```bash
# Vá para a pasta servidor
$ cd Servidor

# Execute a aplicação
$ javac --class-path .:protobuf-java-3.19.1.jar *.java
$ java --class-path ".:protobuf-java-3.19.1.jar: .:sqlite-jdbc-3.27.2.1.jar" servidor


# O servidor inciará na porta: 66667
```

###  Executar o Cliente

```bash
# Vá para a pasta cliente
python3 cliente.py

# O servidor se conecta incialmente na porta: 66667
```

Bibliotecas Utilizadas no projeto 
==============

  - socket: interface de socket BSD.	
  - java.io: Funções para a realização de entrada e saída
  - java.sql: Funções para a realização de conexões com o banco de dados
  - java.net: Funções para a realização de conexões TCP


Exemplo de Uso
==============
-Inserir uma matrícula
- Executar o programa e informar no cliente:
- Digite 1 para INSERIR MATRICULA 
- Em seguida digite a matricula ex: 1987542
- Depois informe o código da disciplina: ex BCC32A
- Depois informe o ano da disciplina: ex 2022
- Depois informar o semestre da disciplina: ex 2


```

Autores
=======
Jean Carlos Martins Miguel
Matheus Vinicius da Costa
