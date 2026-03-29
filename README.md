2 - Objetivo 

Estrutura completa do sistema
1️⃣ Telas / Frontend
1. Tela de Registro (Signup)
Objetivo: permitir que novos usuários se registrem.
Campos:
Nome
Email
Senha
Botão: Registrar
Mensagem: “Usuário registrado com sucesso” ou erros.
Ação: envia dados para o endpoint /api/signup do backend.
Após registro bem-sucedido: redireciona para tela de login.
------------------------------------------------------------------------------------------------

2. Tela de Login (Signin)
Objetivo: permitir acesso de usuários existentes.
Campos:
Email
Senha
Botão: Entrar
Mensagem: “Login realizado” ou “Usuário/senha incorretos”.
Ação: envia dados para o endpoint /api/login.
Após login bem-sucedido: redireciona para tela do mapa.
-------------------------------------------------------------------------------------------------------















git add .
git commit -m "Atualiza README.md e outras mudanças"
git push origin main