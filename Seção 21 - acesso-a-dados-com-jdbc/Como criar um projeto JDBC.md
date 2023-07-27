# Como criar um Projeto Java com JDBC
Neste passo a passo, vamos criar um projeto Java utilizando JDBC (Java Database Connectivity) para conectar e interagir com um banco de dados relacional. Vamos supor que você já tenha o JDK (Java Development Kit) instalado e configurado corretamente em seu sistema.

## Passo 1: Configurar o Ambiente
Certifique-se de ter o JDK instalado e configurado corretamente em seu sistema. Você também precisará de um banco de dados relacional instalado e configurado. Neste exemplo, utilizaremos o MySQL como banco de dados, mas você pode escolher outro banco de dados compatível com o JDBC.

## Passo 2: Criar o Projeto Java
Abra um editor de código de sua preferência (por exemplo, Visual Studio Code, IntelliJ, Eclipse, etc.).
Crie uma nova pasta para o projeto em um local de sua escolha.
Dentro da pasta do projeto, crie um novo arquivo Java com o nome desejado (por exemplo, Main.java).
## Passo 3: Adicionar a Biblioteca JDBC ao Projeto
Baixe o driver JDBC correspondente ao banco de dados que você está usando (por exemplo, MySQL Connector/J).
Coloque o arquivo .jar do driver JDBC baixado na pasta do projeto ou em um diretório específico para bibliotecas externas.
## Passo 4: Escrever o Código Java
Aqui, criaremos uma classe Java simples para conectar ao banco de dados, realizar uma consulta e exibir os resultados.
``` java 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        // Configurações de conexão com o banco de dados
        String url = "jdbc:mysql://localhost:3306/nome_do_banco";
        String user = "seu_usuario";
        String password = "sua_senha";
        try {
            // Registrar o driver JDBC
            Class.forName("com.mysql.jdbc.Driver");

            // Estabelecer a conexão com o banco de dados
            Connection connection = DriverManager.getConnection(url, user, password);

            // Criar uma declaração SQL
            Statement statement = connection.createStatement();

            // Executar uma consulta SQL
            String query = "SELECT * FROM tabela_exemplo";
            ResultSet resultSet = statement.executeQuery(query);

            // Processar os resultados da consulta
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                System.out.println("ID: " + id + ", Nome: " + nome);
            }

            // Fechar as conexões
            resultSet.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
} 
```
## Passo 5: Executar o Projeto
Abra um terminal ou prompt de comando e navegue até a pasta do projeto.
Compile o código Java:
``` bash
javac Main.java
```
Execute o programa:

``` bash
java Main
```
Se tudo estiver configurado corretamente, o programa se conectará ao banco de dados, executará a consulta e exibirá os resultados na saída.

Parabéns! Você criou com sucesso um projeto Java utilizando JDBC para conectar e interagir com um banco de dados relacional. Agora você pode expandir este projeto para realizar outras operações de banco de dados ou implementar outras funcionalidades em seu sistema.




