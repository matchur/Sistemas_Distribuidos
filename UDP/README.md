<h1 align="center">Programação com sockets UDP</h1>
<p href="#descricao" align="center">Exercício UDP.</p>

<div align="center">
  <img src="https://img.shields.io/badge/python-%23007ACC.svg?&style=for-the-badge&logo=python&logoColor=white">
</div>

[comment]: <> (<h4 align="center"> )


[comment]: <> (</h4>)

Tabela de conteúdos
=================
<!--ts-->
   * [Pre Requisitos](#pre-requisitos)
   * [Executar o projeto](#executando-o-projeto)
   * [Bibliotecas Utilizadas](#bibliotecas-utilizadas)
   * [Exemplos de Uso](#exemplos-de-uso)
   * [Autores](#autores)
<!--te-->

Pre-requisitos
==============

Antes de começar, vai precisar ter instalado na sua máquina as seguintes ferramentas:
- [Python](https://www.python.org/);

Executar o projeto
====================

## Exercício1
###  Executar o Chat P2P
```bash
# Entrar na pasta UDP do Exercicio1 e execute a aplicação no udp_chat.py
#    colocando seu apelido, endereço IP e Porta 
$ python3 udp_chat.py
```

## Exercicio2
### Executar o Servidor

```bash
# Vá para a pasta do  Exercicio2 e entre em  /server e execute a aplicação udp_server.py
$ python3 udp_server.py
```

### Executar Cliente

```bash
# Vá para a pasta do  Exercicio2 e entre em  /client e execute a aplicação udp_client.py
$ python3 udp_client.py
```

Bibliotecas Utilizadas
==============

As seguintes bibliotecas foram utilizadas:
- [hashlib](https://docs.python.org/3/library/hashlib.html)
- [socket](https://docs.python.org/3/library/socket.html)
- [_thread](https://docs.python.org/3/library/_thread.html)
- [emoji](https://pypi.org/project/emoji/)


Exemplos de Uso
==============

Após o início da aplicação, ver o passo [Executar o projeto](#executando-o-projeto), o ```Exercicio1``` faz a  
comunicação entre duas pessoas via chat P2P e o ```Exercicio2``` faz transferência de arquivos entre o 
***cliente*** e o ***servidor***. 

# Exercício1
```bash
# Colocar o nome / ip  e Porta
$ Apelido: Jean 
$ ip: 127.0.0.1
$ porta:6666

# As mensagens são tratadas e catalogadas em: Mensagem normal, emoji e url
# Mensagem normal (texto)
$ Jean: hello world

# Para utilizar emojis, utilize a seguinte sintaxe :nome_do_emoji:
# Exemplos:
#thumbs_up
#grinning_face
#kissing_face
#disguised_face
#heart_on_fire
$ Jean: :thumbs_up:

#A aplicação consegue identificar links e eles são visualizados da seguinte maneira:
$ Jean: ~URL~ https://www.google.com.br

# Exercicio2
# No cliente do Exercicio2:
$ SENDFILE naruto.jpg
```

Autores
=======
Jean Carlos Martins Miguel
Matheus Vinicius da Costa