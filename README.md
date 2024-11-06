É necessário ter o docker instalado e executando no compuytador.

# MANUAL DE EXECUÇÃO 
- Instalar e dar start no docker
- Clonar projeto: `git clone https://github.com/karinaerikads/mscadastrocliente.git`
- Ir até a raiz do projeto e abrir terminal no local
- Se certificar de estar na branch main: `git chechout main`
- Execute o seguinte comando  para construir a imagem a partir do Dockerfile: `docker build --tag mscadastroclientes .`
- Para garantir que a imagem foi criada com sucesso, use o comando: `docker images`. Isso exibirá uma lista de todas as imagens no sistema. Sua imagem recém-criada deve aparecer nesta lista
- Após finalizar o comando acima, execute o contêiner com o seguinte comando: `docker run --name teste_mscadastrocliente -p 8080:8080 mscadastroclientes`
- Agora o projeto está executando e pode ser acessada a documentação com swagger em: `http://localhost:8080/swagger-ui/index.html`