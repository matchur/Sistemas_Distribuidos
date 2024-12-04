<h1 align="center">Programação com sockets TCP</h1>
<p href="#descricao" align="center">Exercícios da disciplina  de Sistemas Distribuidos utilizando sockets TCP.</p>

<div align="center">
  <img src="https://img.shields.io/badge/python-%23007ACC.svg?&style=for-the-badge&logo=python&logoColor=white">
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

Executando o projeto
====================

###  Executar o Servidor

```bash
# Vá para a pasta server do Exercicio 1 ou do Exercicio 1
$ cd [Exercicio1]

# Execute a aplicação
$ python3 server.py

# O servidor inciará na porta: 66667
```

###  Executar o Cliente

```bash
# Vá para a pasta server do Exercíco 1 ou do Exercício 1
$ cd [Exercicio 2]

# Execute a aplicação
$ python3 client.py

# O servidor se conecta incialmente na porta: 66667
```

Bibliotecas Utilizadas no projeto 
==============

As seguintes bibliotecas foram usadas na construção do projeto:

- [socket](https://docs.python.org/3/library/socket.html)
- [_thread](https://docs.python.org/3/library/_thread.html)
- [copy](https://docs.python.org/pt-br/3/library/copy.html)

Exemplos de Uso
==============

Após o início da aplicação, ver o passo [Executando o projeto](#executando-o-projeto), o cliente consegue de executar alguns 
comandos para que o servidor possa processar, então é só  informá-los no ```bash```. Os comandos que cada cliente e servidor 
processam variam de acordo com o exercício para ver quais são atendidos basta olhar no *pdf* da atividade 

**Atividade 01- TCP - v2.pdf**.

```bash
# No cliente do Exercicio1 execute:
$ PWD

# No cliente do Exercício 2 execute:
$ GETFILESLIST
```

* No *Exercício 2* o **servidor** guarda um log de todas as requisições feitas em: ```log.log```.
