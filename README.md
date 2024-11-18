# Clubee BackEnd

O backend da Clubee é a espinha dorsal do sistema, responsável por gerenciar e fornecer acesso aos dados e funcionalidades para toda a aplicação. Desenvolvido utilizando tecnologias modernas e robustas, o backend é construído com foco na eficiência, segurança e escalabilidade.

**Tecnologias Utilizadas:**

- **Java:** A linguagem de programação principal do Backend, conhecida por sua robustez e portabilidade, permite o desenvolvimento de aplicações escaláveis e de alto desempenho
- **Spring Framework:** Utilizamos o Spring Framework para criar aplicativos Java empresariais de forma rápida e com menos código, aproveitando seus recursos como injeção de dependência, controle transacional e segurança
- **Banco de Dados PostgreSQL:** O PostgreSQL é utilizado como o sistema de gerenciamento de banco de dados relacional. Ele oferece confiabilidade, desempenho e uma ampla gama de recursos para garantir a integridade e eficiência dos dados

<br>
<br>

## Como executar o Backend localmente?

Para executar o backend da Clubee localmente, você pode usar o Docker, uma plataforma de contêineres que simplifica o processo de criação, implantação e execução de aplicativos em ambientes isolados. Siga os passos abaixo para configurar e executar o backend via Docker:

Para instruções de instalação, veja [instruções de instalação](./markdown/installation.md).

<br>
<br>

## Quais são os Endpoints do Backend?

Endpoints são URLs específicas onde uma API recebe solicitações e retorna respostas, permitindo a comunicação entre diferentes partes de um sistema. Eles definem as operações que podem ser realizadas, como obter, criar, atualizar ou excluir dados. Abaixo, listamos as páginas para acessar a documentação dos endpoints de cada entidade no sistema Clubee.

Para mais detalhes sobre a API, veja a [documentação de API](./markdown/api-documentation.md) ou acesse "http://localhost:8080/api-docs" ao iniciar a aplicação localmente.

<br>
<br>

## Como funciona a Segurança do Backend?

Algumas requisições exigem um nível maior de proteção. Abaixo, explicamos como a aplicação garante segurança e funcionalidade durante o uso pelo usuário.

No backend, utilizamos dois tokens para autenticação: token de acesso e token de atualização.

- O **token de acesso** é gerado no momento do login do usuário e utilizado para validar requisições. Ele funciona como um cracha, para que o usuario não precise se autenticar de novo.

- O **token de atualização** é utilizado para renovar o token de acesso quando ele expira, garantindo que o usuário não precise fazer login novamente durante um determinado período.

<br>

Ambos os tokens são gerados no backend usando criptografia assimétrica, que funciona da seguinte forma:

- O sistema criptografa os tokens com uma chave privada.
- Os tokens são descriptografados utilizando uma chave pública.

Essa abordagem aumenta a segurança dos dados e protege a integridade das operações realizadas pela aplicação.

**Tecnologias Utilizadas:**

Para garantir o funcionamento seguro do sistema, foi utilizado o Spring Security para identificar e gerenciar todas as requisições, além do JWT (JSON Web Token) para autenticar, assinar e validar cada requisição.

Além disso, um filtro de Cross-Origin Resource Sharing (CORS) foi implementado para permitir requisições originadas de fontes específicas e confiáveis, assegurando a comunicação entre o frontend e o backend.
