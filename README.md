# 🚗 Sistema de Localização da Italo (Waze Clone)

## Conta de teste (já criada automaticamente)

| Email | Senha |
| --- | --- |
| `italo@test.com` | `123456` |

> A conta é criada sozinha na primeira execução do backend (e recriada se o banco for apagado).
> Na tela de login há um botão **Preencher** que coloca esses dados com um clique.

## Estrutura do projeto

```
app-waze-faculdade/
├─ backend/    API Spring Boot
├─ frontend/   páginas HTML, CSS e JS (config.js centraliza backend e mapa)
├─ scripts/    start-backend.ps1 e test-api.ps1
├─ README.md
└─ TODO.md
```

## Como Rodar o App (Completo)

### 1. Backend (API Java)

Requer **JDK 21** (recomendado) e Maven.

```
cd backend
mvn spring-boot:run
```

Ou, na raiz do projeto:

```
powershell -ExecutionPolicy Bypass -File scripts\start-backend.ps1
```

- Roda em http://localhost:8081
- Banco H2 persistente em `backend/data/waze.mv.db` (não perde os usuários ao reiniciar)
- H2 Console: http://localhost:8081/h2-console (JDBC URL: `jdbc:h2:file:./data/waze`, user `sa`, senha vazia)
- Swagger: http://localhost:8081/swagger-ui.html

### 2. Frontend (HTML/JS)

```
# Opção 1: abrir direto no navegador
# Abra frontend/Login.html

# Opção 2: VSCode Live Server (melhor)
# Right-click frontend/Login.html > Open with Live Server
```

### 3. Configuração (backend e API de mapa)

- O endereço do backend e a chave da API de mapa ficam em **`frontend/config.js`**.
- Para usar sua API de mapa, preencha `map.tileUrl` (e `map.apiKey` se o provedor exigir).
  Exemplos de URL já estão comentados no arquivo.
- Sem o backend ligado, o login entra em **modo demonstração** e o dashboard abre mesmo assim.

### 4. Testar Login/Registro

1. **Login rápido:** abra `frontend/Login.html`, clique em **Preencher** e depois em **Entrar**.
2. **Criar conta:** `frontend/cadastro.html` → preencha nome, email e senha (mín. 6 caracteres).
3. Após entrar, o dashboard abre com o mapa, GPS e busca de rotas.

## Funcionalidades

- **Mapa Leaflet + GPS ao vivo** (com fallback automático entre provedores de tiles)
- **Rotas reais** via OSRM/OpenStreetMap (carro, a pé e bicicleta)
- **Reportar acidentes** (clique no mapa ou pela barra lateral)
- **Perfil**: foto, troca de senha e logout
- **Acessibilidade**: foco visível, leitores de tela (`aria-live`), atalhos de teclado e suporte a *reduzir movimento*
- **Voz**: leitura das instruções de navegação (Web Speech API)

## 🐛 Debug

**Login não entra?**
1. Backend está rodando? `netstat -an | findstr :8081`
2. F12 → Network → veja a resposta de `POST /api/auth/login` (200/400/500)
3. Confirme a conta: `italo@test.com` / `123456` (ou crie uma nova em `cadastro.html`)

**Mapa em branco?**
1. Precisa de internet para carregar os tiles (CartoDB/OpenStreetMap).
2. O sistema troca de provedor sozinho após 3 erros e mostra um aviso no topo do mapa.
3. Teste: abra `https://tile.openstreetmap.org/14/6051/9286.png` no navegador.

## Tech Stack

- Backend: Spring Boot 3, H2, JWT, BCrypt
- Frontend: HTML5, CSS3, Leaflet, OSRM, Web Speech API
