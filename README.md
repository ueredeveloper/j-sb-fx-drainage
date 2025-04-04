# Projeto de Software com Maven, JavaFX, PostgreSQL (Supabase), Georreferenciamento e Google Maps

## Introdu��o
Este projeto tem como objetivo criar um software Java que utiliza Maven para gerenciamento de depend�ncias e compila��o, JavaFX para a interface gr�fica, PostgreSQL via Supabase para armazenamento de dados, georreferenciamento para manipula��o de informa��es de localiza��o e a integra��o do Google Maps para visualiza��o de mapas. Al�m disso, o software � capaz de gerar documentos a partir dos dados do banco de dados.

## Pr�-requisitos
Antes de come�ar, certifique-se de ter os seguintes pr�-requisitos instalados e configurados:
- JDK (Java Development Kit)
- Maven
- IDE Java (Eclipse, IntelliJ, ou NetBeans)
- Conta no Supabase
- Chave de API do Google Maps
- Conhecimento b�sico em Java e SQL
- Utilizar Scene Builder 8.5
- Use Eclipse 2022-3

## Passos

### 1. Configura��o do Projeto Maven
- Crie um novo projeto Maven em sua IDE.
- Adicione as depend�ncias necess�rias no arquivo `pom.xml`.

### 2. Configura��o da Conex�o com o Banco de Dados
- Utilize o driver JDBC do PostgreSQL para estabelecer uma conex�o com o banco de dados Supabase.
- Forne�a as credenciais de conex�o em uma classe de configura��o.

### 3. Desenvolvimento da Interface Gr�fica com JavaFX
- Crie a interface gr�fica do aplicativo usando JavaFX.
- Projete telas para inser��o, visualiza��o e edi��o de dados.

### 4. Implementa��o do Georreferenciamento
- Utilize bibliotecas Java para georreferenciamento, como JTS ou GeoTools.
- Manipule dados geoespaciais no aplicativo.

### 5. Integra��o com o Google Maps
- Utilize a API do Google Maps para exibir mapas na interface.
- Configure a chave de API do Google Maps.

### 6. Acesso aos Dados no Banco de Dados Supabase
- Execute consultas SQL para recuperar dados do banco de dados Supabase.
- Utilize bibliotecas JDBC para executar consultas e receber resultados.

### 7. Gera��o de Documentos a Partir dos Dados
- Utilize bibliotecas de gera��o de documentos, como Apache PDFBox ou Apache POI.
- Crie documentos a partir dos dados do banco.

### 8. Teste e Depura��o
- Teste o aplicativo de forma abrangente.
- Depure quaisquer erros ou problemas que surgirem durante o teste.

### 9. Empacotamento e Distribui��o
- Empacote o aplicativo em um formato apropriado para distribui��o, como um arquivo JAR.
- Forne�a instru��es para a configura��o do banco de dados e outras configura��es necess�rias.

### 10. Documenta��o e Suporte
- Crie documenta��o para o software, incluindo um guia de usu�rio.
- Esteja dispon�vel para oferecer suporte aos usu�rios e solucionar problemas.

## Conclus�o
Desenvolver um software t�o abrangente requer dedica��o e conhecimento em v�rias tecnologias. Siga os passos acima e consulte a documenta��o relevante para cada tecnologia. Boa sorte com o seu projeto!


## Versão atual

Versão: 2.0.1  
Melhorias no CSS e novo template HTML adicionado.

Para mais detalhes, veja o [CHANGELOG](./CHANGELOG.md).
