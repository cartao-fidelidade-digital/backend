
# Documentação API

Endpoints são URLs específicas onde uma API recebe solicitações e retorna respostas, permitindo a comunicação entre diferentes partes de um sistema. Eles definem as operações que podem ser realizadas, como obter, criar, atualizar ou excluir dados. Abaixo, listamos as páginas para acessar a documentação dos endpoints de cada entidade no sistema Clubee.

- [Client](#client)

- [Company](#company)

- [User](#user)


## Autenticação

Para realizar a autenticação e necessário fazer o login e armazenar o "access-token" e o "refresh-token" localmente no dispositivo do usuario.

- Token de Acesso: são usados para acessar os endpoits sem que precise se autenticar novamente. Geralmente "access-tokens" tem vida util de poucos minutos (ou horas).


- Token de Atualização: servem para obtém novos tokens de acesso sem refazer a autenticação. Esses tokens têm uma validade mais longa e são usados para renovar o "access token" quando este expira.


### Exemplo:


Entrada:

`GET` - localhost:8080/

*Header*:

```json
{
    "Authorization":"Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c",
}
```

*Body*:
```json
{
    "body-exemplo1":"value",
    "body-exemplo2":"value"
}
```




Saída:

```json
{
    "saida-exemplo":"value"
}
```

      
      



## Client

### 1. Mostrar todos os Clientes.

 Entrada:

`GET` - localhost:8080/api/users/client

 Saída:

```json
[
    {
        "id": 1,
        "name": "joao",
        "cpf": "154.599.850-72",
        "phoneNumber": null,
        "termsOfUse": false,
        "dateTermsOfUse": null,
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
        "user": {
            "id": 2,
            "email": "maria@gmail.com",
            "password": "123"
        }
    }
]
```

### 2. Cadastrar Cliente.

Entrada:

`POST` - localhost:8080/api/users/client/register

*Body*:

```json
{
    "name": "maria",
    "email": "maria@gmail.com",
    "password": "123",
    "termsOfUse": true,
    "dateTermsOfUse": "2024-04-29 12:30:45.123456",
    "cpf":"154.599.850-72",
    "phone_number":"61 9 9999-9999"
}
```

 Saída:

```
Usuario salvo com sucesso
```

### 3. Editar Cliente.

Entrada:

`PUT` - localhost:8080/api/users/1

*Body*:

```json
{
    "name": "joaopedro",
    "cpf":"154.599.850-72",
    "phoneNumber":"61 9 9999-9999"
}
```

 Saída:

```json
Cliente editado com sucesso
```

### 4. Deleta Cliente.

Entrada:

`DELETE` - localhost:8080/api/users/client/1

Saída:
```json

```    
  
      

## Company

### 1. Mostrar todas as Empresas.

 Entrada:

`GET` - localhost:8080/api/users/company

 Saída:

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

### 2. Cadastrar Empresa.

Entrada:

`POST` - localhost:8080/api/users/company/register

*Body*:

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

 Saída:

```
Empresa salva com sucesso
```

### 3. Editar Empresa.

Entrada:

`PUT` - localhost:8080/api/users/company/1

*Body*:

```json
{
    "companyName": "Chiquinho Sorvetes",
    "cpf":"154.599.850-72",
    "cnpj":"39.496.353/0001-60",
    "contactPhone":"61 8 9999-9999",
    "type":"Alimentação"
}
```

 Saída:

```json
Empresa editada com sucesso
```

### 4. Deleta Empresa.

Entrada:

`DELETE` - localhost:8080/api/users/company/1

Saída:

```json

```


## User

### 1. Logar Usuário.

Entrada:

`POST` - localhost:8080/api/users/login

*Body*:

```json
{
    "email":"joao@gmail.com",
    "password":"123"
}
```

 Saída:

```json
{
    "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c",
    "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
}
```


### 2. Novo Token.

Entrada:

`POST` - localhost:8080/api/users/newtoken

*Body*:

```json
{
    "expiredAccessToken":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c",
    "refreshToken":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
}
```

 Saída:

```json
{
    "newAccessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c",
    "newRefreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
}
```





### 3. Esqueci senha.

Entrada:

`POST` - localhost:8080/api/password/forgot

*Body*:

```json
{
    "email":"joao@gmail.com"
}
```

 Saída:

```json
Solicitação de redefinição realizada. Voce recebera um email em alguns minutos
```

### 4. Resetar Senha.

Entrada:

`POST` - localhost:8080/api/password/reset

*Body*:

```json
{
    "token":"8bc418c7-218f-4e78-bf1b-eb7b30e0990c",
    "newPassword":"7458"
}
```

 Saída:

```json

```

  
  
  


