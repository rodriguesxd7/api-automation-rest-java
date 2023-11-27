# api-automation-rest-java
This repository was create just to practice my skills in automation tests. This repository will be updated after each feature is create

Projeto de Automação de API com Java e RestAssured
Este projeto consiste em testes de automação de API utilizando Java como linguagem de programação, o framework RestAssured para realizar chamadas e as bibliotecas JUnit e TestNG para execução e organização dos testes.

Pré-requisitos
Java JDK 17.0.9 instalado
Gerenciador de dependências Maven
IDE de desenvolvimento como IntelliJ IDEA
Configuração do Projeto
Faça o clone deste repositório.
Abra o projeto na sua IDE.
Certifique-se de ter as dependências corretas configuradas no arquivo pom.xml para o Maven ou no arquivo de configuração equivalente para o Gradle.
Estrutura do Projeto
src/test/java: Classe TestsRequests contém os testes de automação.
src/main/java: Contém as classes Resquests e Hooks
Requests: Contém as requests necessárias para chamadas das APIs, leitura dos responses e leitura de arquivos.
Hooks: Contém a configuração do report das execuções e o métodos com tags After e Before da execução dos métodos
Executando os Testes
Para executar os testes, basta dar um play na classe TestsRequest ou rodar o o job no menu Actions do github

Cenários de testes inseridos no projeto
Cenários método GET: 
Realizar requisição GET em todos os usuários: endpoint: http://dummyjson.com/users
Realziar requisição GET em usuário por ID: endpoint: http://dummyjson.com/users/id
Realziar requisição GET em usuário por ID inválido: endpoint: http://dummyjson.com/users/id
Realziar requisição GET em todos os produtos por ID: endpoint: http://dummyjson.com/auth/products
Realziar requisição GET em todos os produtos sem token: endpoint: http://dummyjson.com/auth/products
Realziar requisição GET em todos os produtos com token inválido: endpoint: http://dummyjson.com/auth/products
Realziar requisição GET em produtos por id: endpoint: http://dummyjson.com/auth/products/id
Realziar requisição GET em produtos por id inválido: endpoint: http://dummyjson.com/auth/products/id
Realziar requisição GET em produtos por id sem o auth: endpoint: http://dummyjson.com/products

Cenários método POST:
Adicionar produto válido: endpoint: https://dummyjson.com/products/add
Realizar login com credenciais válidas: endpoint: https://dummyjson.com/auth/login
Realizar login com credenciais inválidas: endpoint: https://dummyjson.com/auth/login

Possíveis bugs encontrados: 
1 - É possível realizar um get no endpoint de produtos sem um token e com um token, se for sem o token, basta tirar o "auth" do CURL
2 - No método de adicionar novos produtos, é possível passar um objeto vazio, um body sem nenhum json ou um objeto com campos faltando

Detalhes Técnicos
O projeto utiliza o framework RestAssured para realizar chamadas e asserções em APIs.
As asserções são feitas usando as funcionalidades do JUnit e/ou TestNG.
A estrutura do projeto segue as melhores práticas para organização de testes de automação de API.
