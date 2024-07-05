
### 1. Instale o Docker:

Primeiro, você precisa instalar o Docker em sua máquina. Você pode encontrar instruções detalhadas de instalação para o seu sistema operacional no site oficial do [***Docker***](https://docs.docker.com/get-docker/).

Obs: Caso o seu sistema seja *Windows* clique [***aqui***](https://docs.docker.com/desktop/install/windows-install/).

### 2. Baixe as Imagens do Projeto:

Imagem do Backend:

Após instalar o Docker, você pode baixar as imagens do backend e do banco de dados da Clubee a partir do Docker Hub usando os seguintes comandos no terminal:

```docker
docker pull clubee/clubee.backend:latest
```

Imagem do Banco de dados:

```docker
docker pull clubee/clubee.database:latest
```

Caso queira ver o repositório da [***Clubee no Docker Hub***](https://hub.docker.com/repositories/clubee). 

### 3. Crie o Arquivo `docker-compose.yml`:

Agora, crie um arquivo chamado **`docker-compose.yml`** em seu diretório de trabalho e adicione o seguinte conteúdo:

```docker
version: "3.8"
services:
  db:
    image: clubee/clubee.database:latest
    container_name: postgres-container
    restart: always
    mem_limit: 1000m 
    ports:
      - "5432:5432"
    networks:
      - rede-clubee
    

  api:
    image: clubee/clubee.backend:latest
    container_name: springboot-container
    restart: always
    mem_limit: 2000m
    ports:
      - "8080:8080"
    depends_on:
      - db
    networks:
      - rede-clubee

networks:
  rede-clubee:
    driver: bridge
```

Este arquivo **`docker-compose.yml`** define dois serviços: um para o banco de dados PostgreSQL e outro para o backend da API. Ele também configura uma rede interna para que os contêineres possam se comunicar entre si.


### 4. Execute o Docker Compose:

Por fim, execute o seguinte comando no terminal, dentro do diretório onde está 

o arquivo **`docker-compose.yml`**:

```docker
docker-compose up -d
```

Agora, você pode acessar a API do Clubee em **`http://localhost:8080`**. O banco de dados PostgreSQL estará disponível em **`localhost:5432`**.

Obs: Dentro do terminal navegue até a pasta onde está localizada o seu **`docker-compose.yml` e depois execute o comando descrito.**

## Quais são os Endpoints do Backend?

Endpoints são URLs específicas onde uma API recebe solicitações e retorna respostas, permitindo a comunicação entre diferentes partes de um sistema. Eles definem as operações que podem ser realizadas, como obter, criar, atualizar ou excluir dados. Abaixo, listamos as páginas para acessar a documentação dos endpoints de cada entidade no sistema Clubee.

