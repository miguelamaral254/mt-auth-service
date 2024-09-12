# mt-auth-service


Lembrar de alterar application.propeties -> Ccriar banco em sua máquina(mysql) -> Passsar informações no arquivo.




ROTAS ->{
POST ... /auth/login -> Loga com email e password -> gera token
POST ... /auth/register -> Cria user
GET ... /users Resgata usuários criados(precisa estar autenticado)



Exemplos pro postman/insomnia -> {


http://localhost:8080/auth/login
{
  "email":"miguel254@teste",
  "password": "32412294"
}

http://localhost:8080/auth/register
{
  
  "email": "testemiguel@example.com",
  "name": "migueeeel",
  "password": "32412294"
}

}

}
