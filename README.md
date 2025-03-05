# Ejecutar los scripts para la creación de la base de datos
1. Tener instalado PostgreSQL
2. Abrir el shell de PostgreSQL(psql)
3. Conectar a la base de datos postgres
4. Ejecutar los scripts en orden
   5. Conectado a la bd <postgres>: "\i /ruta/completa/al/script/decajon_create_db.sql"
   6. Ejecutar "\c decajondb" para conectar a la base de datos recien creada
   7. Conectado a la bd <decajondb>: "\i /ruta/completa/al/script/decajon_create_tables.sql"

Si es windows la ruta debe estar de la siguiente manera:

C:/Users/Edgar/Documents/decajon/src/main/resources/db/decajon_create_db.sql
C:/Users/Edgar/Documents/decajon/src/main/resources/db/decajon_create_tables.sql

Estamos utilizando JWT Auth para la autenticación y tokens

También estamos utilizando BCrypt que viene incluido con
Spring Security, para la encriptación de las contraseñas de los usuarios.

La otra opcion de encriptacion era Argon2, más seguro que la anterior, pero también más lenta.