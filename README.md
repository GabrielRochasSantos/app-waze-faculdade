# 🚗 Sistema de Localização da Italo (Waze Clone)

## Como Rodar o App (Completo)

### 1. Backend (API Java)
```
cd backend
mvn clean spring-boot:run
```
- Roda em http://localhost:8081
- Test H2 DB: http://localhost:8081/h2-console (JDBC URL: `jdbc:h2:mem:waze` user:sa pass:empty)
- Swagger APIs: http://localhost:8081/swagger-ui.html

### 2. Frontend (HTML/JS)
```
# Opção 1: Browser direto
# Abra frontend/login.html

# Opção 2: VSCode Live Server (melhor)
# Instale extensão Live Server, right-click index.html em frontend/ > Open with Live Server
```

### 3. Test Login/Register
1. **Cadastro novo usuário:**
   - frontend/cadastro.html
   - Nome: Italo, Email: italo@test.com, Senha: 123456
   - Clique Criar Conta → Alert sucesso → Vai para login.html

2. **Login:**
   - Email: italo@test.com, Senha: 123456
   - Entrar → Dashboard com mapa GPS + sidebar

### 4. Funcionalidades
- **Mapa Leaflet + GPS Live** (marker verde sua posição)
- **Report Acidente** (click mapa ou sidebar → modal → marker laranja)
- **Dashboard**: Perfil foto upload, reports list, logout
- **DB H2** auto-reset cada restart (test sempre novo user)

## 🐛 Debug Problemas
**Login carregando infinito?**
1. Backend rodando? `netstat -an | findstr :8081`
2. F12 Browser > Network tab → Veja status da request `/api/auth/login` (200 ok? 401? 500?)
3. Console erros JS? 

**Erros comuns:**
- Backend não restart após edits (mvn spring-boot:run novo terminal)
- File case Windows: use `login.html` lowercase
- Firewall block port 8081

## Tech Stack
- Backend: Spring Boot 3, H2, JWT, BCrypt
- Frontend: HTML5, Leaflet Maps, localStorage auth

App pronto! 🚀 Teste cadastro/login agora.
