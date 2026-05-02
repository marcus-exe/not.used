# FIAP Challenge Back-end

### 1- Esse projeto usa Java + SpringBoot + H2 (db)
### 2- Para rodar o projeto, basta rodar o main em ChallengeApplication.java
### 3- Em caso de erros, checar se o maven importou as bibliotecas corretamente
- no caso do IntelliJ, basta clicar no ícone "M" na lateral direita do projeto e procurar por "reload all maven projects"
### 4- Para verificar o estado do banco de dados em H2, utilize `http://localhost:8080/h2-console`
username: sa <br>
password: password
### 5- Caso queira testar o código, foi fornecido um arquivo.json no qual foram feitos os testes no Postman (basta importar o arquivo)
- lembrando que para testar códigos relacionados ao gemini, o back-end em python também deve estar rodando
- mais informações sobre como rodar o código em python, favor checar o README do projeto (python)
### 6- Futuro do projeto (em caso de aprovação para NEXT)
**Integração com o Front-end**
- Devido a outras demandas, o time de front ainda não realizou a integração com os serviços do back-end"
- Mas tendo em vista a simplicidade do MVP, isso não será muito difícil de implementar

**Leitura de Arquivos**
- pretendemos abrir uma outra instância de um back-end python (flask) somente para a parte de leitura de arquivos (.json, .csv, .pdf)

**Containers**
- a nível de entrega pretendemos separar o código em containers e usar docker compose para orquestar essa mudança
- mudar o storage de H2 para postgresql

**Deploy**
- gostaria de testar uma instância usando o serviço da CloudFlare
- mas se não for viável, pretendo utilizar AWS (provavelmente lightsail)

**Integração com ServiceNow**
- essa parte vai depender inteiramente da disponibilidade do time conforme o projeto avança