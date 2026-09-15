# To-Do List Android

Aplicativo Android de lista de tarefas desenvolvido como atividade individual da disciplina de Android Development (FIAP). O objetivo do projeto é evoluir uma base de código já existente, implementando a camada de apresentação (UI), a navegação entre telas e a integração com a arquitetura já estruturada no projeto.

Com o app é possível **listar, criar, editar, concluir e excluir tarefas**, com persistência local em banco de dados e navegação entre a tela de listagem e o formulário de cadastro/edição.

## 🛠️ Tecnologias utilizadas

- **Kotlin** — linguagem principal do projeto
- **Jetpack Compose** — construção declarativa da interface
- **Room** — persistência local em banco de dados SQLite
- **Coroutines / Flow** — operações assíncronas e observação reativa de dados
- **ViewModel** (Android Architecture Components) — gerenciamento de estado da UI
- **Navigation Compose** — navegação entre as telas do aplicativo

---

## 🏛️ Arquitetura e Responsabilidades

O projeto segue o padrão **MVVM (Model-View-ViewModel)**, com uma camada de repositório entre o ViewModel e o banco de dados, garantindo separação de responsabilidades:
UI (Compose) → ViewModel → Repository → DAO (Room) → Banco de dados 

### 📦 Camada de Dados

- **`TarefaRepository`**: atua como a única fonte da verdade (*single source of truth*) para os dados de tarefas. Recebe uma instância de `TarefaDao` e expõe `tarefas: Flow<List<Tarefa>>`, além das funções `suspend` `inserir`, `atualizar` e `deletar`, que apenas repassam a operação ao DAO. Sua função é isolar o restante do app dos detalhes de acesso ao Room.

### 🧠 Camada de Apresentação (ViewModel)

- **`TarefaViewModel`**: conecta o `TarefaRepository` à interface. Expõe `tarefas` como um `StateFlow<List<Tarefa>>`, convertendo o `Flow` do repositório com `.stateIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed(5_000), initialValue = emptyList())` — o que mantém o fluxo ativo por 5 segundos após a UI parar de observá-lo, evitando recriações desnecessárias em rotações de tela. As funções `inserir`, `atualizar` e `deletar` rodam dentro de `viewModelScope.launch { }`. Um `companion object` com uma função `factory(context)` cria o ViewModel já injetando o `TarefaRepository`, obtendo o DAO via `TarefaDatabase.getDatabase(context)`.

### 📱 Camada de UI (Jetpack Compose)

- **`ListaTarefasScreen`**: observa o estado com `viewModel.tarefas.collectAsStateWithLifecycle()`, recompondo a tela automaticamente quando a lista muda. As ações do usuário (marcar checkbox, excluir, clicar para editar) disparam callbacks (`onCheckedChange`, `onDeletar`, `onEditarTarefa`) que chamam diretamente as funções do ViewModel. Um `FloatingActionButton` aciona a navegação para o formulário em modo de cadastro. A tela foi separada em `ListaTarefasScreen` (conectada ao ViewModel) e `ListaTarefasContent` (sem dependência do ViewModel), permitindo `@Preview`s com dados fake — inclusive da lista vazia.

- **`FormularioTarefaScreen`**: diferencia o modo **Cadastro** do modo **Edição** verificando o `tarefaId` recebido pela navegação. Se `tarefaId == 0`, a tela entende que é um cadastro e os campos começam vazios. Se `tarefaId != 0`, ela busca a tarefa correspondente na lista observada do ViewModel (`tarefas.find { it.id == tarefaId }`) e pré-preenche título e descrição. A flag `isEdicao` também controla o título exibido na `TopAppBar` ("Nova Tarefa" ou "Editar Tarefa").

### 🧭 Navegação e Inicialização

- **`AppNavigation`**: configura o `NavHost` com duas rotas: `"lista"` (rota inicial) e `"formulario/{tarefaId}"`. A passagem do ID é feita via argumento de rota — para uma nova tarefa, navega-se para `"formulario/0"`; para editar, para `"formulario/$id"`. O ID é extraído de `backStackEntry.arguments` e convertido para `Int` antes de ser repassado à tela.

- **`MainActivity`**: ponto de entrada do app. Cria o `TarefaViewModel` usando `viewModel(factory = TarefaViewModel.factory(applicationContext))` — a factory manual definida no próprio ViewModel, sem uso de frameworks de injeção de dependência. Em seguida, inicia a navegação chamando `AppNavigation(viewModel = viewModel)`, substituindo o conteúdo de exemplo gerado pelo template padrão do Android Studio.

---

## 🚀 Como Executar o Projeto

1. Certifique-se de ter o **Android Studio** instalado.
2. Clone este repositório:
git clone https://github.com/lu42s/to-do-list.git 
3. Abra o projeto no Android Studio (`File > Open` e selecione a pasta raiz).
4. Aguarde o **Gradle Sync** terminar de baixar as dependências.
5. Conecte um dispositivo físico ou inicie um emulador.
6. Clique em **Run ▶** (ou `Shift + F10`) para compilar e executar o app.

---

## ✅ Funcionalidades

- Listar tarefas cadastradas
- Cadastrar nova tarefa
- Editar tarefa existente
- Marcar/desmarcar tarefa como concluída
- Excluir tarefa
- Navegação entre lista e formulário sem encerrar o app
- Persistência local dos dados (Room)

---

## 📸 Evidências da Atividade
Tela inicial:

<img width="240" height="480" alt="image" src="https://github.com/user-attachments/assets/5d1bd5d8-faef-4777-bf73-453c53b153c6" />


Criação de tarefa:

<img width="440" height="480" alt="image" src="https://github.com/user-attachments/assets/9e8eddd6-bcd8-41f9-b447-a5b1db54c414" />

  
Tarefa criada:

<img width="240" height="480" alt="image" src="https://github.com/user-attachments/assets/72e10bbb-3a3a-4ce0-a571-e6aa12f4abee" />


Tarefa marcada como realizada:

<img width="240" height="480" alt="image" src="https://github.com/user-attachments/assets/65829493-480d-4af1-b7a9-99cdb98187f2" />

 
Tarefa sendo editada:

<img width="240" height="480" alt="image" src="https://github.com/user-attachments/assets/94bce560-eb38-44f6-bdaa-a0008add62bc" />

Build:

<img width="900" height="400" alt="image" src="https://github.com/user-attachments/assets/fa8edc6e-8554-495b-a638-f9c25bbd4247" />


---

## 👤 Autor

Desenvolvido por [@lu42s](https://github.com/lu42s) como atividade individual da disciplina de Android Development — FIAP.
