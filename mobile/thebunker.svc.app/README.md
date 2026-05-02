# svs form - App Flutter

App Flutter com login local, formulários dinâmicos baseados em JSON, seleção de formulário padrão, filtro por tipo de formulário, navegação entre formulários, relacionamentos pai-filho, persistência com Hive e captura de localização.

## Funcionalidades

- **Login Local**: Sistema de login simples com hash de senha
- **Picker de usuários salvos**: ícone no campo de e-mail mostra usuários previamente logados (sem armazenar senhas)
- **Formulários Dinâmicos**: Carregados de `assets/forms/*.json`
- **Seleção de Formulário Padrão**: botão hambúrguer no topo abre seletor; o FAB usa o formulário padrão diretamente
- **Filtro por Formulário**: ícone de filtro na Home para listar somente entradas de um formulário específico (inclui filhos relacionados)
- **Lista de Entradas com Hierarquia**: Visualização de entradas com relacionamentos pai-filho, colapsáveis e com indicação visual
- **Navegação entre Formulários**: Formulários podem chamar outros formulários através de botões ou navegação condicional automática
- **Mapeamento de Dados**: Dados podem ser passados entre formulários com configuração personalizada
- **Formulários Ocultos**: Formulários podem ser marcados como ocultos para não aparecerem em seletores e filtros
- **Detalhes da Entrada**: Mostra respostas com rótulos amigáveis conforme o schema, incluindo informações sobre formulários pai e filhos
- **Localização**: Captura automática da localização ao salvar formulários
- **Loadings**: 
  - Startup: "Carregando formulários..." enquanto o JSON é lido
  - Salvamento: "Adicionando dados da localização..." enquanto busca a localização
- **Persistência**: Dados salvos localmente com Hive (criptografado para boxes de usuários e entradas)

## Pré-requisitos

- Flutter SDK (>=3.0.0)
- Dart SDK

## Instalação

1. Instalar dependências:
```bash
flutter pub get
```

2. Gerar os adapters do Hive:
```bash
flutter pub run build_runner build --delete-conflicting-outputs
```

3. Verifique as permissões de localização:
   - Android: já configurado em `AndroidManifest.xml`
   - iOS: chaves `NSLocationWhenInUseUsageDescription` e `NSLocationAlwaysAndWhenInUseUsageDescription` em `Info.plist`

## Executar o App

```bash
flutter run
```

Se adicionar/remover JSONs em `assets/forms/`, faça um hot restart/rebuild para atualizar o `AssetManifest.json`.

## Estrutura do Projeto

- `lib/main.dart` - Ponto de entrada, inicialização do Hive
- `lib/app.dart` - Widget principal da aplicação (define título “svs form” e splash de carregamento)
- `lib/screens/` - Telas (Login, Home, Form)
- `lib/screens/entry_details_screen.dart` - Detalhes de uma entrada
- `lib/services/` - Serviços (Auth, Location, FormLoader)
- `lib/data/models/` - Modelos de dados (User, FormEntry, GeoPoint)
- `lib/widgets/dynamic_form/` - Widgets de formulário dinâmico
- `assets/forms/` - Arquivos JSON de definição de formulários

## Adicionar Novos Formulários

1. Crie um arquivo JSON em `assets/forms/`
2. Use a estrutura exemplo:

```json
{
  "key": "meu_formulario",
  "titulo": "Meu Formulário",
  "descricao": "Descrição do formulário",
  "campos": [
    {
      "id": "campo1",
      "tipo": "texto",
      "rotulo": "Campo 1",
      "obrigatorio": true
    }
  ]
}
```

3. O formulário será automaticamente carregado ao iniciar o app

Observação: Expressões regulares em JSON precisam escapar barras invertidas. Exemplo de e-mail válido:
```json
"regex": "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\\.[A-Za-z]{2,}$"
```

## Tipos de Campo Suportados

- `texto`: Campo de texto simples
- `multilinha`: Campo de texto multilinha
- `numero`: Campo numérico
- `data`: Seletor de data
- `hora`: Seletor de hora
- `selecao`: Dropdown com opções
- `checkbox`: Caixa de seleção
- `formulario`: Botão que navega para outro formulário (ver seção Navegação entre Formulários)
- `imagem`: Campo de imagem única (câmera ou galeria)
- `galeria`: Campo de múltiplas imagens (câmera ou galeria)

## Guia Completo de Estrutura JSON de Formulários

### Estrutura Básica de um Formulário

Um formulário JSON deve conter as seguintes propriedades:

```json
{
  "key": "identificador_unico",        // Obrigatório: chave única do formulário
  "titulo": "Título do Formulário",    // Obrigatório: título exibido ao usuário
  "descricao": "Descrição opcional",   // Opcional: descrição exibida no topo
  "oculto": false,                      // Opcional: se true, não aparece em seletores (padrão: false)
  "campos": [...],                      // Obrigatório: array de campos
  "nextForm": {...}                     // Opcional: navegação condicional após salvar
}
```

**Exemplo básico:**
```json
{
  "key": "meu_formulario",
  "titulo": "Meu Formulário",
  "descricao": "Preencha os dados abaixo",
  "campos": [
    {
      "id": "nome",
      "tipo": "texto",
      "rotulo": "Nome Completo",
      "obrigatorio": true,
      "minLength": 3,
      "maxLength": 100
    },
    {
      "id": "email",
      "tipo": "texto",
      "rotulo": "E-mail",
      "obrigatorio": true,
      "regex": "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    }
  ]
}
```

### Campos de Imagem (Câmera/Galeria)

O sistema suporta dois tipos de campos de imagem que permitem ao usuário capturar fotos ou selecionar da galeria:

#### Tipo `imagem` - Imagem Única

```json
{
  "id": "foto",
  "tipo": "imagem",
  "rotulo": "Foto",
  "obrigatorio": false,
  "max": 1
}
```

**Propriedades:**
- `tipo`: Deve ser `"imagem"` para imagem única
- `max`: Sempre deve ser `1` para imagem única (padrão: `1`)
- `obrigatorio`: Se `true`, o usuário deve adicionar uma foto antes de salvar

**Comportamento:**
- Exibe botão "Adicionar Foto"
- Ao tocar, o usuário pode escolher entre:
  - **Escolher da Galeria**: Abre a galeria de fotos do dispositivo
  - **Tirar Foto**: Abre a câmera do dispositivo
- Após selecionar, mostra uma miniatura da imagem com opção de remover
- As imagens são automaticamente salvas em armazenamento persistente

#### Tipo `galeria` - Múltiplas Imagens

```json
{
  "id": "fotos_adicionais",
  "tipo": "galeria",
  "rotulo": "Fotos Adicionais",
  "obrigatorio": false,
  "max": 5
}
```

**Propriedades:**
- `tipo`: Deve ser `"galeria"` para múltiplas imagens
- `max`: Número máximo de imagens permitidas (padrão: `1`, mas para galeria use valores maiores)
- `obrigatorio`: Se `true`, o usuário deve adicionar pelo menos uma foto

**Comportamento:**
- Exibe botão "Adicionar Foto (0/5)" mostrando quantas imagens já foram adicionadas
- Permite selecionar múltiplas imagens da galeria de uma vez
- Cada imagem pode ser removida individualmente
- Mostra contador de imagens selecionadas

**Observações importantes:**
- As imagens são copiadas para armazenamento persistente automaticamente
- Os caminhos são salvos como caminhos relativos para evitar problemas com UUIDs do iOS
- Imagens são exibidas em uma galeria horizontal na tela de detalhes
- Toque em uma imagem para ver em tela cheia com zoom

### Formulários Aninhados (Formulários Dentro de Formulários)

Você pode criar formulários que navegam para outros formulários, criando uma hierarquia de formulários relacionados.

#### Campo tipo `formulario`

Este tipo de campo cria um botão que navega para outro formulário:

```json
{
  "id": "ir_para_feedback",
  "tipo": "formulario",
  "rotulo": "Continuar para Feedback",
  "formKey": "feedback",           // Obrigatório: chave do formulário destino
  "salvarAntes": false,            // Opcional: salvar antes de navegar
  "salvarJunto": false,             // Opcional: salvar todos juntos no final
  "mapearDados": {                 // Opcional: mapear dados entre formulários
    "nome": "nome",
    "data_visita": "data"
  }
}
```

**Propriedades:**
- `formKey`: **Obrigatório** - A chave (`key`) do formulário de destino
- `salvarAntes`: Se `true`, salva o formulário atual antes de navegar (padrão: `false`)
- `salvarJunto`: Se `true`, acumula dados e salva todos os formulários juntos no final (padrão: `false`)
- `mapearDados`: Mapeia dados do formulário atual para o destino (opcional)

**Importante:** `salvarAntes` e `salvarJunto` são mutuamente exclusivos. Se ambos forem `true`, `salvarAntes` terá prioridade.

### Estratégias de Salvamento em Formulários Aninhados

O sistema oferece duas estratégias diferentes para salvar formulários relacionados:

#### 1. Salvar Antes (`salvarAntes: true`) - Salvamento por Partes

**Quando usar:** Quando você quer que cada formulário seja salvo independentemente à medida que o usuário progride.

```json
{
  "id": "ir_para_feedback",
  "tipo": "formulario",
  "rotulo": "Continuar para Feedback",
  "formKey": "feedback",
  "salvarAntes": true,
  "mapearDados": {
    "nome": "nome",
    "data_visita": "data"
  }
}
```

**Comportamento:**
1. Usuário preenche o formulário pai
2. Usuário toca no botão de formulário
3. Sistema valida o formulário pai
4. Sistema **salva o formulário pai** no banco de dados
5. Sistema navega para o formulário filho
6. Usuário preenche o formulário filho
7. Ao salvar o formulário filho, ele é salvo separadamente

**Resultado:**
- Formulário pai é salvo imediatamente
- Formulário filho é salvo separadamente quando completado
- Criam-se duas entradas independentes com relacionamento pai-filho

**Casos de uso:**
- Quando cada formulário precisa ser salvo independentemente
- Quando você quer garantir que dados parciais sejam preservados
- Para rastreabilidade de progresso por etapa

#### 2. Salvar Junto (`salvarJunto: true`) - Salvamento Agrupado

**Quando usar:** Quando você quer que os formulários sejam salvos todos juntos apenas após completar todos eles.

```json
{
  "id": "ir_para_feedback",
  "tipo": "formulario",
  "rotulo": "Continuar para Feedback",
  "formKey": "feedback",
  "salvarJunto": true,
  "mapearDados": {
    "nome": "nome",
    "data_visita": "data"
  }
}
```

**Comportamento:**
1. Usuário preenche o formulário pai
2. Usuário toca no botão de formulário
3. Sistema **não salva ainda**, apenas acumula os dados do formulário pai
4. Sistema navega para o formulário filho
5. Usuário preenche o formulário filho
6. Ao salvar o formulário filho:
   - Sistema valida **ambos** os formulários (pai e filho)
   - Sistema **salva ambos os formulários juntos** em uma única operação
   - Formulários são salvos em sequência, ligados pelo relacionamento pai-filho

**Resultado:**
- Nenhum formulário é salvo até que o último seja completado
- Todos os formulários são validados juntos
- Todos os formulários são salvos em uma única transação
- Se houver erro em qualquer validação, nenhum formulário é salvo

**Casos de uso:**
- Quando você precisa garantir integridade dos dados (tudo ou nada)
- Para formulários que só fazem sentido quando completos
- Para evitar entradas parciais no banco de dados

**Exemplo completo com `salvarJunto`:**

```json
{
  "key": "checkin",
  "titulo": "Check-in",
  "campos": [
    {
      "id": "nome",
      "tipo": "texto",
      "rotulo": "Nome",
      "obrigatorio": true
    },
    {
      "id": "foto",
      "tipo": "imagem",
      "rotulo": "Foto",
      "max": 1
    },
    {
      "id": "ir_para_feedback",
      "tipo": "formulario",
      "rotulo": "Continuar para Feedback",
      "formKey": "feedback",
      "salvarJunto": true,
      "mapearDados": {
        "nome": "nome"
      }
    }
  ]
}
```

### Mapeamento de Dados entre Formulários

O `mapearDados` permite passar dados do formulário atual para o formulário de destino:

**Sintaxe:**
```json
"mapearDados": {
  "campo_destino": "campo_origem",    // Copia valor do campo origem para campo destino
  "campo_destino": "valor_fixo"        // Define valor literal no campo destino
}
```

**Exemplos:**

```json
{
  "id": "ir_para_feedback",
  "tipo": "formulario",
  "formKey": "feedback",
  "mapearDados": {
    "nome": "nome",                    // Campo "nome" do destino recebe valor de "nome" da origem
    "data_visita": "data",             // Campo "data_visita" do destino recebe valor de "data" da origem
    "status": "Pendente",               // Campo "status" do destino recebe o valor literal "Pendente"
    "tipo_registro": "Check-in"        // Campo "tipo_registro" do destino recebe valor literal
  }
}
```

**Regras:**
- Chave = ID do campo no formulário de destino
- Valor pode ser:
  - ID de um campo no formulário atual (copia o valor)
  - Uma string literal (define valor fixo)

### Exemplos Completos

#### Exemplo 1: Formulário Simples com Imagens

```json
{
  "key": "registro_visita",
  "titulo": "Registro de Visita",
  "descricao": "Registre sua visita com fotos",
  "campos": [
    {
      "id": "nome_visitante",
      "tipo": "texto",
      "rotulo": "Nome do Visitante",
      "obrigatorio": true
    },
    {
      "id": "foto_principal",
      "tipo": "imagem",
      "rotulo": "Foto Principal",
      "obrigatorio": true,
      "max": 1
    },
    {
      "id": "fotos_adicionais",
      "tipo": "galeria",
      "rotulo": "Fotos Adicionais",
      "obrigatorio": false,
      "max": 10
    },
    {
      "id": "observacoes",
      "tipo": "multilinha",
      "rotulo": "Observações",
      "obrigatorio": false,
      "maxLength": 500
    }
  ]
}
```

#### Exemplo 2: Formulário Pai com Salvamento por Partes

```json
{
  "key": "checkin",
  "titulo": "Check-in",
  "campos": [
    {
      "id": "nome",
      "tipo": "texto",
      "rotulo": "Nome",
      "obrigatorio": true
    },
    {
      "id": "foto",
      "tipo": "imagem",
      "rotulo": "Foto",
      "max": 1
    },
    {
      "id": "ir_para_feedback",
      "tipo": "formulario",
      "rotulo": "Continuar para Feedback",
      "formKey": "feedback",
      "salvarAntes": true,
      "mapearDados": {
        "nome": "nome"
      }
    }
  ]
}
```

**Comportamento:**
- Formulário "checkin" é salvo antes de navegar para "feedback"
- Formulário "feedback" é salvo separadamente quando completado

#### Exemplo 3: Formulário Pai com Salvamento Agrupado

```json
{
  "key": "inspecao_inicial",
  "titulo": "Inspeção Inicial",
  "campos": [
    {
      "id": "inspetor",
      "tipo": "texto",
      "rotulo": "Nome do Inspetor",
      "obrigatorio": true
    },
    {
      "id": "fotos_inspecao",
      "tipo": "galeria",
      "rotulo": "Fotos da Inspeção",
      "max": 5,
      "obrigatorio": true
    },
    {
      "id": "continuar_para_relatorio",
      "tipo": "formulario",
      "rotulo": "Continuar para Relatório",
      "formKey": "relatorio_detalhado",
      "salvarJunto": true,
      "mapearDados": {
        "inspetor": "inspetor",
        "data_inspecao": "data"
      }
    }
  ]
}
```

**Comportamento:**
- Dados do formulário "inspecao_inicial" são acumulados (não salvos)
- Ao completar "relatorio_detalhado", ambos são validados e salvos juntos
- Se qualquer validação falhar, nenhum formulário é salvo

#### Exemplo 4: Formulário Complexo Completo

```json
{
  "key": "processo_completo",
  "titulo": "Processo Completo",
  "descricao": "Formulário completo com todos os recursos",
  "campos": [
    {
      "id": "dados_basicos",
      "tipo": "texto",
      "rotulo": "Dados Básicos",
      "obrigatorio": true
    },
    {
      "id": "foto_principal",
      "tipo": "imagem",
      "rotulo": "Foto Principal",
      "obrigatorio": true,
      "max": 1
    },
    {
      "id": "documentos",
      "tipo": "galeria",
      "rotulo": "Documentos (máx 10)",
      "obrigatorio": false,
      "max": 10
    },
    {
      "id": "data_registro",
      "tipo": "data",
      "rotulo": "Data do Registro",
      "obrigatorio": true
    },
    {
      "id": "ir_para_aprovacao",
      "tipo": "formulario",
      "rotulo": "Solicitar Aprovação",
      "formKey": "formulario_aprovacao",
      "salvarJunto": true,
      "mapearDados": {
        "processo_id": "dados_basicos",
        "data": "data_registro",
        "status": "Pendente"
      }
    }
  ],
  "nextForm": {
    "formKey": "formulario_finalizacao",
    "condicao": {
      "campo": "dados_basicos",
      "valor": "Completo"
    },
    "mapearDados": {
      "origem": "processo_completo"
    }
  }
}
```

**Explicação:**
- Contém campos de texto, imagem única, galeria de imagens e data
- Tem um botão que navega para outro formulário com `salvarJunto: true`
- Tem navegação condicional automática após salvar

## Validações

- `obrigatorio`: Campo obrigatório (true/false)
- `min`: Valor mínimo (para números)
- `max`: Valor máximo (para números) ou número máximo de imagens (para `imagem`/`galeria`)
- `minLength`: Comprimento mínimo (para texto)
- `maxLength`: Comprimento máximo (para texto)
- `regex`: Expressão regular para validação (para texto)

**Validações específicas por tipo:**

- **Texto/Multilinha:**
  - `minLength`: Comprimento mínimo em caracteres
  - `maxLength`: Comprimento máximo em caracteres
  - `regex`: Expressão regular (lembre-se de escapar `\` em JSON)

- **Número:**
  - `min`: Valor mínimo permitido
  - `max`: Valor máximo permitido

- **Imagem/Galeria:**
  - `max`: Número máximo de imagens (1 para `imagem`, N para `galeria`)
  - `obrigatorio`: Se `true`, requer pelo menos uma imagem

## Navegação entre Formulários

O sistema suporta múltiplas formas de navegação entre formulários. Para detalhes completos sobre formulários aninhados, veja a seção [Guia Completo de Estrutura JSON de Formulários](#guia-completo-de-estrutura-json-de-formulários).

### 1. Botão de Formulário (Campo tipo `formulario`)

Adicione um botão dentro do formulário que navega para outro:

```json
{
  "id": "ir_para_feedback",
  "tipo": "formulario",
  "rotulo": "Continuar para Feedback",
  "formKey": "feedback",
  "salvarAntes": true,
  "mapearDados": {
    "nome": "nome",
    "data_visita": "data"
  }
}
```

**Propriedades:**
- `formKey`: Chave do formulário destino (obrigatório)
- `salvarAntes`: Se `true`, salva o formulário atual antes de navegar (padrão: `false`)
  - **Comportamento:** Formulário pai é salvo imediatamente, formulário filho é salvo separadamente
  - **Use quando:** Quiser salvar cada formulário independentemente durante o processo
- `salvarJunto`: Se `true`, acumula dados e salva todos os formulários juntos no final (padrão: `false`)
  - **Comportamento:** Nenhum formulário é salvo até que todos sejam completados, então todos são salvos juntos
  - **Use quando:** Quiser garantir integridade dos dados (tudo ou nada)
  - **Nota:** Se ambos `salvarAntes` e `salvarJunto` forem `true`, `salvarAntes` terá prioridade
- `mapearDados`: Mapeia dados do formulário atual para o destino
  - Chave = campo destino
  - Valor = campo origem (ou valor literal)

**Escolhendo entre `salvarAntes` e `salvarJunto`:**
- **Use `salvarAntes: true`** quando:
  - Você quer que cada etapa seja salva independentemente
  - Precisar preservar dados parciais
  - Quiser rastrear progresso por etapa
  
- **Use `salvarJunto: true`** quando:
  - Você precisa garantir que todos os formulários sejam salvos juntos ou nenhum
  - Quiser evitar entradas parciais no banco de dados
  - Os formulários só fazem sentido quando completos em conjunto

### 2. Navegação Condicional Automática (nextForm)

Após salvar, navega automaticamente para outro formulário baseado em condições:

```json
{
  "key": "checkin",
  "titulo": "Check-in",
  "nextForm": {
    "formKey": "feedback",
    "condicao": {
      "campo": "categoria",
      "valor": "A"
    },
    "mapearDados": {
      "nome": "nome",
      "data_visita": "data"
    }
  }
}
```

**Propriedades:**
- `formKey`: Chave do formulário destino
- `condicao`: Condição opcional - se não especificada, sempre navega
  - `campo`: ID do campo a verificar
  - `valor`: Valor que o campo deve ter para acionar a navegação
- `mapearDados`: Mapeamento de dados (mesmo formato do botão)

### Mapeamento de Dados

O `mapearDados` permite:
- **Campo para campo**: `{"campo_destino": "campo_origem"}`
- **Valor literal**: `{"campo_destino": "valor_fixo"}`

Exemplo:
```json
"mapearDados": {
  "nome": "nome",              // Campo "nome" do destino recebe valor do campo "nome" da origem
  "data_visita": "data",       // Campo "data_visita" do destino recebe valor do campo "data" da origem
  "status": "Pendente"          // Campo "status" do destino recebe o valor literal "Pendente"
}
```

## Formulários Ocultos

Formulários podem ser marcados como ocultos para não aparecerem em seletores e filtros:

```json
{
  "key": "feedback",
  "titulo": "Feedback",
  "oculto": true,
  "campos": [...]
}
```

**Comportamento:**
- Não aparece no seletor de formulário padrão
- Não aparece no seletor ao clicar no FAB
- Não aparece no filtro de formulários
- Ainda pode ser acessado via navegação entre formulários
- Útil para formulários que só devem ser acessados após completar outros

## Relacionamentos Pai-Filho

Quando um formulário é criado a partir de outro (via navegação), um relacionamento pai-filho é estabelecido:

**Visualização na Home:**
- Entradas filhas aparecem aninhadas sob suas entradas pai
- Indicadores visuais: borda azul à esquerda, ícone de subdiretório, chip "Filho"
- Entradas com filhos mostram um chip com o número de filhos
- Entradas pai são colapsáveis - toque na entrada para expandir/recolher filhos
- Botão de detalhes (chevron) sempre disponível para ver informações completas

**Filtros:**
- Ao filtrar por um formulário, também mostra seus filhos relacionados
- Exemplo: filtrar por "checkin" mostra entradas de checkin e suas entradas de feedback relacionadas

**Detalhes da Entrada:**
- Mostra informações do formulário pai (se existir)
- Mostra lista de formulários filhos relacionados (se existirem)
- Permite navegação entre relacionados

## Permissões

O app requer permissão de localização para salvar a posição junto com os formulários. As permissões já estão configuradas para iOS e Android.

## Dicas de Uso

- Para definir o formulário padrão, use o botão de menu (hambúrguer) na Home.
- O FAB abre diretamente o formulário padrão. Se nenhum estiver definido, será exibido o seletor.
- Use o ícone de filtro para ver entradas de um único formulário (inclui filhos relacionados); clique em "Limpar" para remover o filtro.
- Toque em uma entrada para ver os detalhes completos; toque no botão chevron para sempre ver detalhes.
- Entradas pai com filhos podem ser colapsadas/expandidas tocando na área principal da entrada.
- Formulários ocultos não aparecem em seletores mas ainda podem ser acessados via navegação entre formulários.
- No login, use o ícone ao lado do e-mail para selecionar rapidamente usuários já logados (somente e-mails são armazenados; senhas nunca são salvas).

## Git e Build Artifacts

Evite versionar caches e builds locais. Certifique-se de ignorar:
```
android/.gradle/
android/**/build/
**/.gradle/
**/build/
```

## CI/CD - Pipeline de Deploy

O projeto inclui um pipeline automatizado usando GitHub Actions para construir artefatos de release (APK, AAB e IPA) para Android e iOS.

### Configuração do Pipeline

O pipeline é acionado automaticamente em:
- Push para a branch `main`
- Pull requests para a branch `main`

Os artefatos são construídos apenas em pushes (não em PRs) e ficam disponíveis para download nas GitHub Actions por 30 dias.

### Configuração de Signing

#### Android - Keystore

1. **Gerar o keystore** (execute apenas uma vez):

```bash
keytool -genkey -v -keystore android/app/upload-keystore.jks -keyalg RSA -keysize 2048 -validity 10000 -alias upload
```

Você será solicitado a fornecer:
- Senha do keystore
- Informações pessoais (nome, organização, etc.)
- Senha da chave (pode ser a mesma do keystore)

**Importante**: Guarde essas informações, você precisará delas!

2. **Codificar o keystore em Base64** (para GitHub Secrets):

```bash
# macOS/Linux
base64 -i android/app/upload-keystore.jks | pbcopy  # macOS
base64 android/app/upload-keystore.jks | xclip -selection clipboard  # Linux

# Windows (PowerShell)
[Convert]::ToBase64String([IO.File]::ReadAllBytes("android/app/upload-keystore.jks"))
```

3. **Configurar GitHub Secrets**:

No GitHub, vá em **Settings → Secrets and variables → Actions** e adicione:

- `ANDROID_KEYSTORE_BASE64`: O keystore codificado em Base64 (cole o resultado do passo 2)
- `ANDROID_KEYSTORE_PASSWORD`: A senha do keystore
- `ANDROID_KEY_ALIAS`: O alias usado (geralmente `upload`)
- `ANDROID_KEY_PASSWORD`: A senha da chave (pode ser igual à senha do keystore)

4. **Template de configuração local**:

Para builds locais, copie o template e preencha:

```bash
cp android/key.properties.template android/key.properties
```

Edite `android/key.properties` com suas informações:
```properties
storePassword=sua_senha_do_keystore
keyPassword=sua_senha_da_chave
keyAlias=upload
storeFile=app/upload-keystore.jks
```

#### iOS - App Store Connect API Key

1. **Criar a API Key no Apple Developer Portal**:

   - Acesse [App Store Connect](https://appstoreconnect.apple.com)
   - Vá em **Users and Access → Keys → App Store Connect API**
   - Clique em **Generate API Key** ou use uma existente
   - Baixe o arquivo `.p8` (você só poderá baixar uma vez!)
   - Anote o **Key ID** e o **Issuer ID**

2. **Codificar o certificado em Base64**:

```bash
# macOS/Linux
base64 -i AuthKey_XXXXXXXXXX.p8 | pbcopy  # macOS
base64 AuthKey_XXXXXXXXXX.p8 | xclip -selection clipboard  # Linux

# Windows (PowerShell)
[Convert]::ToBase64String([IO.File]::ReadAllBytes("AuthKey_XXXXXXXXXX.p8"))
```

3. **Obter o Team ID**:

   - No [Apple Developer Portal](https://developer.apple.com/account), vá em **Membership**
   - Copie o **Team ID** (formato: `XXXXXXXXXX`)

4. **Configurar GitHub Secrets**:

No GitHub, adicione os seguintes secrets:

- `IOS_CERTIFICATE_BASE64`: O arquivo `.p8` codificado em Base64
- `IOS_CERTIFICATE_KEY_ID`: O Key ID da API Key (ex: `XXXXXXXXXX`)
- `IOS_TEAM_ID`: O Team ID do Apple Developer (ex: `XXXXXXXXXX`)
- `IOS_APPLE_ID`: Seu Apple ID (email)
- `IOS_APP_ID`: Bundle identifier do app (ex: `com.example.the_bunker`)

**Nota**: `IOS_APP_SPECIFIC_PASSWORD` é opcional e só necessário se você quiser fazer upload automático para a App Store (não implementado atualmente).

### Download dos Artefatos

Após um build bem-sucedido:

1. Acesse a aba **Actions** no repositório GitHub
2. Clique na execução do workflow (ex: "Build Release Artifacts")
3. Role até a seção **Artifacts**
4. Baixe os artefatos desejados:
   - `app-release-apk`: APK para instalação direta
   - `app-release-aab`: App Bundle para upload no Google Play Store
   - `app-release-ipa`: IPA para upload no App Store Connect

### Estrutura do Pipeline

O pipeline está em `.github/workflows/build.yml` e inclui:

- **Job Android** (Ubuntu): Build APK e AAB
- **Job iOS** (macOS): Build IPA
- Cache de dependências para builds mais rápidos
- Upload automático de artefatos

### Troubleshooting

**Android builds falhando:**
- Verifique se o keystore foi codificado corretamente
- Certifique-se de que todas as secrets estão configuradas
- O alias padrão é `upload` - ajuste se necessário

**iOS builds falhando:**
- Verifique se o Team ID está correto
- Certifique-se de que o certificado `.p8` foi baixado (só pode ser baixado uma vez)
- O bundle identifier deve corresponder ao configurado no Xcode
- Verifique se o certificado não expirou (renovar anualmente)

**Builds não aparecem:**
- Artefatos são gerados apenas em pushes para `main` (não em PRs)
- Verifique os logs da Action para erros

### Manutenção

- **Keystore Android**: Guarde-o com segurança - não pode ser recuperado se perdido
- **Certificado iOS**: Renove anualmente no Apple Developer Portal
- **Atualização de dependências**: Execute `flutter pub get` localmente antes de fazer push

## Observações

- O login é local e não possui recuperação de senha
- Senhas são hasheadas usando SHA-256
- Dados são armazenados localmente no dispositivo
- Formulários são carregados automaticamente dos arquivos JSON em `assets/forms/`

