
# Documentação API

Nesse documento sera apresentado e detalhado todos os endpoints do Backend-Clubee e como utilizar cada um de forma correta. Endpoints são URLs onde uma API recebe solicitações e retorna respostas em formato Json, permitindo a comunicação entre diferentes partes de um sistema. Eles definem as operações que podem ser realizadas, como obter, criar, atualizar ou excluir dados. Abaixo, listamos as páginas para acessar a documentação dos endpoints de cada entidade no sistema Clubee.

- [Client](#client)

- [Company](#company)

- [User](#user)

<br>


## O que são Endpoints
Endpoints são como "endereços" que você pode acessar para pedir ou enviar informações a um sistema, como um site ou uma aplicação. Abaixo sera listada algumas informações que um endpoint geralmente tem e pode aparecer na documentação:

Entrada: é o endereço de onde quer acessar (ex: GET - localhost:8080/) é o tipo de ação que quer fazer (ex: GET, POST).

Header: é onde você coloca informações extras, como um código de segurança para provar que você está autorizado a fazer a solicitação.

Body: é o conteúdo que você envia para a API quando quer passar informações, como dados de um formulário.

Saída: é o que a API devolve para você após processar a sua solicitação, podendo ser uma mensagem ou os dados que você pediu.

Roles: definem as funções de cada usuario. Por exemplo, um endpoint pode ser acessível apenas por usuários com a função de admin, enquanto outro pode ser acessado por qualquer usuário autenticado.


## Autenticação

Na aplicação existem varios endpoints que precisam de autenticação. Para realizar a autenticação e necessário fazer o login e armazenar o "access-token" e o "refresh-token" localmente no dispositivo do usuario.

- Token de Acesso: são usados para acessar os endpoits sem que precise se autenticar novamente. Geralmente "access-tokens" tem vida util de poucos minutos (ou horas).
<br>

- Token de Atualização: servem para obtém novos tokens de acesso sem refazer a autenticação. Esses tokens têm uma validade mais longa e são usados para renovar o "access token" quando este expira.


<br>

### Exemplo de Autenticação:

O exemplo abaixo segue com 4 informações basicas que a maioria dos enpoints vai ter como *entrada, header, body e saída*. 


- *Entrada*: `GET` - localhost:8080/

- *Header*:

```json
{
    "Authorization":"Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9eyI...",
}
```

- *Body*:
```json
{
    "body-exemplo1":"value",
    "body-exemplo2":"value"
}
```




- *Saída*:

```text
saída realizada com sucesso
```      
      
<br>
<br>
<br>


## Client

### 1. Mostrar todos os Clientes.

Este endpoint retorna a lista de todos os clientes cadastrados. 

> role: admin

##### Ex:

- *Entrada*: `GET` - localhost:8080/api/users/client
- *Header*:
```json
{
    "Authorization":"Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9eyI...",
}
```

<br>

- *Saída*:

```json
[
    {
        "id": 1,
        "name": "joao",
        "cpf": "154.599.850-72",
        "phoneNumber": null,
        "termsOfUse": false,
        "dateTermsOfUse": null,
        "socialName":"Joao Paulo",
        "preferences":"alimentação,papelaria,petshop",
        "nascimento": "2024-04-29T12:30:45",
        "photo": "photo",
        "cep": "01001-000",
        "endereco": "Praça da Sé",
        "estado": "São Paulo",
        "cidade": "São Paulo",
        "user": {
            "id": 1,
            "email": "joao@gmail.com",
            "password": "123"
        }
    },
    {
        "id": 2,
        "name": "maria",
        "cpf": "154.599.850-72",
        "phoneNumber": null,
        "termsOfUse": false,
        "dateTermsOfUse": null,
        "socialName":"Maria Joaquina",
        "preferences":"alimentação,papelaria,petshop",
        "nascimento": "2024-04-29T12:30:45",
        "photo": "photo",
        "cep": "01001-000",
        "endereco": "Praça da Sé",
        "estado": "São Paulo",
        "cidade": "São Paulo",
        "user": {
            "id": 2,
            "email": "maria@gmail.com",
            "password": "123"
        }
    }
]
```

<br>

### 2. Cadastrar Cliente.

Este endpoint permite o cadastro de um novo cliente. Alguns dados são obrigatorios como nome, email, senha e cpf.

##### Ex:

- *Entrada*: `POST` - localhost:8080/api/users/client/register

- *Body*:

```json
{
    "name": "maria",
    "email": "maria@gmail.com",
    "password": "123",
    "termsOfUse": true,
    "dateTermsOfUse": "2024-04-29 12:30:45.123456",
    "cpf":"154.599.850-72",
    "phone_number":"61 9 9999-9999",
    "socialName":"Joao Paulo",
    "preferences":"alimentação,papelaria,petshop",
    "nascimento": "2024-04-29T12:30:45",
    "photo": "photo",
    "cep": "01001-000",
    "endereco": "Praça da Sé",
    "estado": "São Paulo",
    "cidade": "São Paulo",
}
```

<br>

- *Saída*:

```json
{
    "role":"CLIENT",
    "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c",
    "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
}
```

<br>

### 3. Editar Cliente.

Este endpoint permite a edição dos dados de um cliente existente. Alguns dados são obrigatorios como nome e cpf.

> role: client

##### Ex:

- *Entrada*: `PUT` - localhost:8080/api/users/client
- *Header*:
```json
{
    "Authorization":"Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9eyI...",
}
```

<br>

- *Body*:

```json
{
    "name": "joaopedro",
    "cpf":"154.599.850-72",
    "phoneNumber":"61 9 9999-9999",
    "socialName":"Joao Paulo",
    "preferences":"alimentação,papelaria,petshop",
    "nascimento": "2024-04-29T12:30:45",
    "photo": "photo",
    "cep": "01001-000",
    "endereco": "Praça da Sé",
    "estado": "São Paulo",
    "cidade": "São Paulo",
}
```

<br>

- *Saída*:

```text
Cliente editado com sucesso
```

<br>

### 4. Deleta Cliente.

Este endpoint permite a exclusão de um cliente.

> role: client

- *Entrada*: `DELETE` - localhost:8080/api/users/client

- *Header*:
```json
{
    "Authorization":"Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9eyI...",
}
```

<br>

- *Saída*:
```text

```    
  
<br>
<br>
<br>
   

## Company

### 1. Mostrar todas as Empresas.

Este endpoint retorna a lista de todas as empresas cadastradas. 

> role: admin

##### Ex:

- *Entrada*: `GET` - localhost:8080/api/users/company

- *Header*:
```json
{
    "Authorization":"Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9eyI...",
}
```

<br>

- *Saída*:

```json
[
    {
        "id": 1,
        "companyName": "Chiquinho Sorvetes",
        "cnpj": "39.496.353/0001-60",
        "cpf": "154.599.850-72",
        "type": "Sorveteria",
        "contactPhone": "61 9 9999-9999",
        "termsOfUse": false,
        "dateTermsOfUse": null,
        "user": {
            "id": 4,
            "email": "marcos@gmail.com",
            "password": "123"
        }
    },
    {
        "id": 2,
        "companyName": "Gonçalina Pães",
        "cnpj": "39.496.353/0001-60",
        "cpf": "154.599.850-72",
        "type": "Alimentação",
        "contactPhone": "61 9 9999-9999",
        "termsOfUse": false,
        "dateTermsOfUse": null,
        "user": {
            "id": 5,
            "email": "joao@gmail.com",
            "password": "123"
        }
    }
]
```

<br>

### 2. Cadastrar Empresa.

Este endpoint permite o cadastro de uma nova empresa. Alguns dados são obrigatorios como nome da empresa, email e senha.

##### Ex:

- *Entrada*:`POST` - localhost:8080/api/users/company/register

- *Body*:

```json
{
    "companyName": "Gonçalina Pães",
    "email": "joao@gmail.com",
    "password": "123",
    "termsOfUse": true,
    "dateTermsOfUse": "2024-04-29 12:30:45.123456",
    "cpf":"154.599.850-72",
    "cnpj":"39.496.353/0001-60",
    "type":"Alimentação",
    "contactPhone":"61 9 9999-9999"
}
```

<br>

- *Saída*:

```json
{
    "role":"COMPANY",
    "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c",
    "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
}
```

<br>

### 3. Editar Empresa.

Este endpoint permite a edição dos dados de uma empresa existente. Alguns dados são obrigatorios como nome da empresa.

> role: company

##### Ex:

- *Entrada*: `PUT` - localhost:8080/api/users/company

- *Header*:
```json
{
    "Authorization":"Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9eyI...",
}
```

<br>

- *Body*:

```json
{
    "companyName": "Chiquinho Sorvetes",
    "cpf":"154.599.850-72",
    "cnpj":"39.496.353/0001-60",
    "contactPhone":"61 8 9999-9999",
    "type":"Alimentação"
}
```

<br>

- *Saída*:

```text
Empresa editada com sucesso
```

<br>

### 4. Deleta Empresa.

Este endpoint permite a exclusão de uma empresa.

> role: company

##### Ex:

- *Entrada*: `DELETE` - localhost:8080/api/users/company

- *Header*:
```json
{
    "Authorization":"Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9eyI",
}
```


<br>

- *Saída*:

```text

```

<br>
<br>
<br>

## User

### 1. Logar Usuário.

Este endpoint permite que o usuario faça login na aplicação e receba um token de acesso. A saída desse endpoint tambem devolve um token de atualização (refresh-token) e a role do usuario. 

Obs: Em caso de duvidas leia o topico de [Autenticação](#autenticação) 

##### Ex:

- *Entrada*: `POST` - localhost:8080/api/users/login

- *Body*:

```json
{
    "email":"joao@gmail.com",
    "password":"123"
}
```

<br>

- *Saída*:

```json
{
    "role":"ADMIN",
    "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c",
    "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
}
```


<br>

### 2. Novo Token.

Este endpoint permite que o token de acesso seja atualizado, para que não seja necessario o usuario logar novamente. Para fazer isso e necessario pegar o token de acesso expirado e o token de atualização, e enviar no "body" da requisição.

Obs: Em caso de duvidas leia o topico de [Autenticação](#autenticação) 

##### Ex:

- *Entrada*: `POST` - localhost:8080/api/users/newtoken

- *Body*:

```json
{
    "expiredAccessToken":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c",
    "refreshToken":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
}
```

<br>

- *Saída*:

```json
{
    "newAccessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c",
    "newRefreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
}
```


<br>

### 3. Esqueci senha.

Este endpoint enviará instruções para o email do usuario que deseja trocar sua senha. O envio de email pode demorar alguns minutos.

##### Ex:

- *Entrada*: `POST` - localhost:8080/api/password/forgot

- *Body*:

```json
{
    "email":"joao@gmail.com"
}
```

<br>

- *Saída*:

```text
Solicitação de redefinição realizada. Voce recebera um email em alguns minutos
```

### 4. Resetar Senha.

Este endpoint permite que o usuario troque sua senha usando o token que ele recebeu por email. E necessario realizar o topico  "[Esqueci senha](#3-esqueci-senha)" para prossegir. 

##### Ex:

- *Entrada*: `POST` - localhost:8080/api/password/reset

- *Body*:

```json
{
    "token":"8bc418c7-218f-4e78-bf1b-eb7b30e0990c",
    "newPassword":"7458"
}
```

<br>

- *Saída*:

```text
Senha alterada com sucesso
```

  
  
  


