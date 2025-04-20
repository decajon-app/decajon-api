/* Database: decajondb
 * Script: decajon_create_db.sql
 * Description: creates the database
 **/

-- Creation of DeCajon database
CREATE DATABASE decajondb
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'es_MX.UTF-8'
    LC_CTYPE = 'es_MX.UTF-8'
    LOCALE_PROVIDER = 'libc'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    TEMPLATE = template0;

-- Comment for DeCajon database
COMMENT ON DATABASE decajondb
    IS 'Database for DeCajón app';
