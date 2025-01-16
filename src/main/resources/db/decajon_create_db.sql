/* Database: decajondb
 * Script: decajon_create_db.sql
 * Description: creates the database
 **/

-- Creation of DeCajon database
CREATE DATABASE decajondb
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Spanish_Mexico.1252'
    LC_CTYPE = 'Spanish_Mexico.1252'
    LOCALE_PROVIDER = 'libc'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

-- Comment for DeCajon database
COMMENT ON DATABASE decajondb
    IS 'Database for DeCajón app';
