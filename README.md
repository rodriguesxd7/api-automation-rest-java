# api-automation-rest-java
This repository was create just to practice my skills in automation tests. This repository will be updated after each feature is create

Projeto de Automação de API com Java e RestAssured
Este projeto consiste em testes de automação de API utilizando Java como linguagem de programação, o framework RestAssured para realizar chamadas e as bibliotecas JUnit e TestNG para execução e organização dos testes.

**Pré-requisitos**
Java JDK 17.0.9 instalado
Gerenciador de dependências Maven
IDE de desenvolvimento como IntelliJ IDEA


**Configuração do Projeto**
Faça o clone deste repositório.
Abra o projeto na sua IDE.
Certifique-se de ter as dependências corretas configuradas no arquivo pom.xml para o Maven ou no arquivo de configuração equivalente para o Gradle.
Estrutura do Projeto
src/test/java: Classe TestsRequests contém os testes de automação.
src/main/java: Contém os pacotes
Requests: Contém as requests necessárias para chamadas das APIs, leitura dos responses e leitura de arquivos.
Hooks: Contém a configuração do report das execuções e o métodos com tags After e Before da execução dos métodos

**Executando os Testes**
Para executar os testes, basta dar um play na classe TestsRequest para rodar todos os testes de uma única vez ou rodar individualmente

**Evidencias**
Para visualizar o report das evidencias, basta abrir o arquivo "report.html" no path "report/report.html" (abrir no browser de preferencia

**Detalhes Técnicos**
O projeto utiliza o framework RestAssured para realizar chamadas e asserções em APIs.
As asserções são feitas usando as funcionalidades do JUnit e/ou TestNG.
A estrutura do projeto segue as melhores práticas para organização de testes de automação de API.
