$body = '{"email":"test@test.com","senha":"123456"}'
Invoke-WebRequest -Uri 'http://localhost:8081/api/auth/login' -Method POST -Body $body -ContentType 'application/json' -UseBasicParsing | Select-Object -ExpandProperty StatusCode
