
# TERMO - Jogo de Adivinhação de Palavras

## 1. Descrição

Este projeto consiste na implementação completa do jogo **TERMO**, desenvolvido em **Java** com interface gráfica utilizando **Swing**.

O sistema foi estruturado seguindo o padrão arquitetural **MVC**

---

## 2. Objetivo do Jogo

O objetivo é descobrir uma palavra secreta de **5 letras** em até **6 tentativas**.

A cada tentativa, o jogador recebe dicas visuais que indicam:

- Letra correta na posição correta
- Letra existente na palavra, mas em posição incorreta
- Letra inexistente na palavra secreta

---

## 3. Regras do Jogo

- Uma palavra secreta de 5 letras é escolhida aleatoriamente a partir de um arquivo externo.
- O jogador possui no máximo 6 tentativas.
- Cada tentativa deve conter exatamente 5 letras.
- Após cada tentativa, o sistema avalia letra por letra e fornece o feedback visual correspondente.
- O jogo termina quando:
  - O jogador descobre a palavra secreta, ou
  - As 6 tentativas são utilizadas.

---

## 4. Funcionalidades Implementadas

- Seleção aleatória de palavras a partir de arquivo externo
- Interface gráfica completa com teclado virtual
- Sistema de estatísticas (vitórias, derrotas, taxa de acerto)
- Persistência de estatísticas em arquivo
- Atualização dinâmica de cores conforme regras do jogo
- Reinício de partida
- Diálogo de ajuda
- Diálogo de estatísticas
- Estrutura organizada em pacotes

---

## 5. Arquitetura do Sistema

O projeto segue o padrão **MVC**, organizado da seguinte forma:

```

src/
├── Main.java
│
├── controller/
│   ├── ControladorJogo.java
│   └── GerenciadorUsuarios.java
│
├── model/
│   ├── BancoPalavras.java
│   ├── Estatisticas.java
│   ├── Jogador.java
│   ├── Letra.java
│   ├── Palavra.java
│   └── Partida.java
│
├── view/
│   ├── DialogoAjuda.java
│   ├── DialogoEstatisticas.java
│   ├── DialogoLogin.java
│   ├── DialogoPerfil.java
│   ├── DialogoSobre.java
│   ├── JanelaPrincipal.java
│   ├── MenuPrincipal.java
│   ├── PainelEstatisticas.java
│   ├── PainelGrade.java
│   ├── PainelLetra.java
│   └── TecladoVirtual.java
│
└── util/
    ├── ConstantesVisuais.java
    └── GerenciadorArquivos.java


````

### Model

Contém as classes responsáveis pela lógica do jogo e pelo funcionamento principal do sistema:

- Representação de letras e seus estados
- Controle da partida
- Gerenciamento de estatísticas
- Banco de palavras

### Controller

Responsável por intermediar a comunicação entre interface e regras de negócio.

### View

Interface gráfica desenvolvida com Swing.

### Util

Classes auxiliares para persistência e constantes visuais.

---

## 6. Requisitos do Sistema

- Java 8 ou superior
- Sistema operacional Windows, macOS ou Linux

---

## 7. Compilação

No terminal, execute:

```bash
javac -d bin -sourcepath src src/Main.java
````

---

## 8. Execução

```bash
java -cp bin Main resources/palavras.txt
```

O parâmetro passado corresponde ao arquivo contendo as palavras válidas para o jogo.

---

## 9. Controles

* Teclas A–Z: inserir letras
* ENTER: confirmar tentativa
* BACKSPACE: apagar letra
* Ctrl + N: iniciar nova partida
* Ctrl + E: abrir estatísticas

---

## 10. Persistência

As estatísticas do jogador são armazenadas em arquivo, permitindo que os dados sejam mantidos entre execuções do programa.

---

## 11. Autor

Matheus Cardoso de Araújo 24.1.4033
Pedro Souza Goularte 24.2.4084
Pedro Henrique Boseja Carolino Barbosa 24.2.4092
David Cristian de Paula Rodrigues 24.2.4094

BCC 221 – Programação Orientada a Objetos
Universidade Federal de Ouro Preto – 2025/2

