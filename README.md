# MANUAL DE EXECUÇÃO 
- Instalar e dar start no docker
- Clonar projeto: `git clone https://github.com/karinaerikads/mscadastrocliente.git`
- Ir até a raiz do projeto e abrir terminal no local
- Se certificar de estar na branch main: `git chechout main`
- Execute o seguinte comando  para construir a imagem a partir do Dockerfile: `docker build --tag mscadastroclientes .`
- Para garantir que a imagem foi criada com sucesso, use o comando: `docker images`. Isso exibirá uma lista de todas as imagens no sistema. Sua imagem recém-criada deve aparecer nesta lista
- Após finalizar o comando acima, execute o contêiner com o seguinte comando: `docker run --name teste_mscadastrocliente -p 8080:8080 mscadastroclientes`
- Agora o projeto está executando e pode ser acessada a documentação com swagger em: `http://localhost:8080/swagger-ui/index.html`

# MANUAL DE USO
- Na pasta *collection* está um arquivo .json para ser usado como teste da api. Abrir esse arquivo em um programa como o postman
- A primeira coisa a ser feita é fazer o cadastro de login de usuário. Para fazer isso abra a request *Cadastro de login de usuário* e dê um *send*
- Agora é preciso fazer login com as mesmas credenciais cadastradas. Use a request *Login usuário*, dê um send e copie o token que retornou.
- Esse token deve estar no campo *bearer token* na authorization das requests restantes da collection, como por exemplo a de salvar cliente, listar cliente por ordem alfabética, deletar cliente e etc. 