ROTAS!!!
---

## API Endpoints

### Autenticação

- **POST `/auth/login`**
  - Permite que os usuários façam login sem a necessidade de autenticação prévia.

### Usuários

- **POST `/user/register`**
  - Permite o registro de novos usuários no sistema (sem autenticação prévia).
  
- **POST `/user/register/student`**
  - Permite o registro de novos estudantes no sistema (sem autenticação prévia).

- **POST `/user/register/parent`**
  - Permite o registro de novos pais no sistema (sem autenticação prévia).

- **POST `/user/register/professor`**
  - Permite o registro de novos professores no sistema (sem autenticação prévia).

- **POST `/user/register/coordination`**
  - Permite o registro de novos coordenadores no sistema (sem autenticação prévia).

- **GET `/user/all`**
  - Retorna uma lista de todos os usuários registrados no sistema (sem autenticação prévia).

### Turmas

- **POST `/schoolclasses`**
  - Permite a criação de novas turmas no sistema (sem autenticação prévia).

- **GET `/schoolclasses`**
  - Retorna uma lista de todas as turmas cadastradas no sistema (sem autenticação prévia).

- **POST `/schoolclasses/addstudent`**
  - Permite adicionar um estudante a uma turma específica (sem autenticação prévia).

### Aulas

- **POST `/lessons`**
  - Permite a criação de novas aulas no sistema (sem autenticação prévia).

- **GET `/lessons`**
  - Retorna uma lista de todas as aulas cadastradas no sistema (sem autenticação prévia).

### Disciplinas

- **POST `/disciplines`**
  - Permite a criação de novas disciplinas no sistema (sem autenticação prévia).

- **GET `/disciplines`**
  - Retorna uma lista de todas as disciplinas cadastradas no sistema (sem autenticação prévia).

