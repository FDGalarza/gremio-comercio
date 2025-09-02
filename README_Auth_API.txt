
API de Autenticación
====================

Este módulo expone los endpoints de autenticación para el sistema.
Base URL:
/api/auth

Endpoints disponibles
---------------------

1. Login
--------
Descripción:
Permite autenticar a un usuario con correo y contraseña.
Si las credenciales son correctas, devuelve un token JWT que debe usarse para las siguientes peticiones autenticadas.

Endpoint:
POST /api/auth/login

Headers requeridos:
| Key            | Value                |
|----------------|----------------------|
| Content-Type   | application/json     |

Request Body (JSON)
-------------------
{
  "correoElectronico": "usuario@ejemplo.com",
  "contrasena": "MiContraseñaSegura123"
}

Response Exitosa (200 OK)
-------------------------
{
  "token": "eyJhbGciOiJIUzI1NiIsInR..."
}

Response Error (401 UNAUTHORIZED)
---------------------------------
Cuando las credenciales no son válidas:
{
  "error": "Credenciales inválidas"
}

Ejemplo con cURL
----------------
curl -X POST http://localhost:8080/api/auth/login   -H "Content-Type: application/json"   -d '{
    "correoElectronico": "usuario@ejemplo.com",
    "contrasena": "MiContraseñaSegura123"
  }'

Notas importantes
-----------------
- El token JWT recibido debe incluirse en el header Authorization en los demás endpoints protegidos:
  Authorization: Bearer <token>
- El tiempo de expiración del token y su renovación depende de la configuración del servicio AuthService.
