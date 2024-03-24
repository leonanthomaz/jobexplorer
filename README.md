# JobExplorer

JobExplorer é uma plataforma para explorar e descobrir oportunidades de emprego em diversos setores e regiões. O projeto utiliza Java com Spring Boot para o backend e React com Material-UI para o frontend.

## Funcionalidades

- Pesquisa de vagas por cargo, localização e preferências.
- Coleta e organização de informações de múltiplos portais de emprego.
- Interface intuitiva e amigável para facilitar a navegação.
- Atualizações regulares sobre novas oportunidades de emprego.

## Tecnologias Utilizadas

- Java
- Spring Boot
- React
- Material-UI
- Jsoup
- WebClient
- Maven
- Docker
- H2 (para ambiente de teste)
- MySQL (para ambiente de produção)

## Configuração do Backend

1. Clone este repositório.
2. Navegue até o diretório do backend (`backend/`).
3. Execute o seguinte comando para iniciar o servidor Spring Boot com H2 (para ambiente de teste):

```bash
mvn spring-boot:run
```

4. Para executar o backend com MySQL (para ambiente de produção), crie um banco de dados MySQL e configure as propriedades de conexão no arquivo `application.properties`.

## Configuração do Frontend

1. Navegue até o diretório do frontend (`frontend/`).
2. Execute o seguinte comando para instalar as dependências do projeto:

```bash
npm install
```

3. Execute o seguinte comando para iniciar o aplicativo React:

```bash
npm start
```

Isso iniciará o servidor de desenvolvimento do React.

## Configuração do Docker

1. Certifique-se de ter o Docker instalado em sua máquina.
2. Navegue até a raiz do projeto onde está localizado o arquivo `docker-compose.yml`.
3. Execute o seguinte comando para iniciar os contêineres definidos no arquivo `docker-compose.yml`:

```bash
docker-compose up
```

Isso iniciará os contêineres Docker conforme definido no arquivo `docker-compose.yml`.

## Contribuindo

Contribuições são bem-vindas! Sinta-se à vontade para abrir um problema para relatar bugs, sugestões ou melhorias. Se deseja contribuir com código, por favor, abra uma solicitação de pull com suas alterações.

## Licença

Este projeto está licenciado sob a [Licença MIT](https://opensource.org/licenses/MIT).
