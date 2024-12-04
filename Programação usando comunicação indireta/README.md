<h1 align="center">Programação com Comunicação Indireta</h1>
<p href="#descricao" align="center"> Exercícios avalativos de Sistemas Distribuidos utilizando comunicação indireta.
</p>

<div align="center">
  <img alt="Python" src="https://img.shields.io/badge/python-%23007ACC.svg?&style=for-the-badge&logo=python&logoColor=white">
</div>

[comment]: <> (<h4 align="center"> )

[comment]: <> (</h4>)

Conteúdos
=================
<!--ts-->
   * [Pre Requisitos](#pre-requisitos)
   * [Executando o projeto](#executando-o-projeto)
   * [Bibliotecas Utilizadas](#bibliotecas-utilizadas)
   * [Exemplos de Uso](#exemplos-de-uso)
   * [Autores](#autores)
<!--te-->

Pre-requisitos
==============
 ter instalado na sua máquina as seguintes ferramentas:
- [Python](https://www.python.org/)
- [RabbitMQ](https://www.rabbitmq.com/)

Executando o projeto
====================

##  Cliente
```bash
# Na pasta do src/ execute:
$ python3 client.py
```
##  Collector
```bash
# Na pasta do src/ execute:
$ python3 collector.py
```
## Classificador
```bash
# Na pasta do src/ execute:
$ python3 classifier.py
```



Bibliotecas Utilizadas
==============

As seguintes bibliotecas foram usadas na construção do projeto:
#### Python
- [Pika]()
- [Datatime]()
- [Pandas]()
- [Json]()

Exemplos de Uso
==============

Nesta versão o cliente seleciona o tópico de interesse que deseja receber notificações. 

Assim toda mensagem encontrada relacionada ao assunto de interesse do cliente será enviado a notificação.

*Obs:* É importante que o cliente seja iniciado préviamente. 

Autores
=======

- Jean Carlos Martins Miguel
-Matheus Vinicius Costa

