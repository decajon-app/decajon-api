/* Database: decajondb
 * Script: decajon_create_tables.sql
 * Description: craetes all necessary tables for decajon database
 **/

-- Create Users table
CREATE TABLE users(
	id SERIAL NOT NULL,
	email VARCHAR(255) UNIQUE NOT NULL,
	password VARCHAR(255) NOT NULL,
	first_name VARCHAR(50) NOT NULL,
	last_name VARCHAR(50) NOT NULL,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

-- Create Groups Table
CREATE TABLE groups(
	id SERIAL NOT NULL,
	name VARCHAR(255) NOT NULL,
	password VARCHAR(255) NOT NULL,
	description TEXT,
	owner_id INT NOT NULL REFERENCES users(id) ON DELETE SET NULL,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

-- Create Genres Table
CREATE TABLE genres(
	id SERIAL NOT NULL,
	group_id INT NOT NULL,
	genre VARCHAR(25) NOT NULL,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uq_group_genre UNIQUE (group_id, genre),
    FOREIGN KEY (group_id) REFERENCES groups(id) ON DELETE CASCADE,
    PRIMARY KEY (id)
);

-- Create Artists Table
CREATE TABLE artists(
	id SERIAL NOT NULL,
	group_id INT NOT NULL,
	artist VARCHAR(255) NOT NULL,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uq_group_artist UNIQUE (group_id, artist),
    FOREIGN KEY (group_id) REFERENCES groups(id) ON DELETE CASCADE,
    PRIMARY KEY (id)
);

-- Create Songs Table
CREATE TABLE songs(
    id SERIAL NOT NULL,
	group_id INT NOT NULL,
	title VARCHAR(100) NOT NULL,
	duration INT DEFAULT NULL,
	genre_id INT DEFAULT NULL,
	artist_id INT DEFAULT NULL,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uq_group_title_artist UNIQUE (group_id, title, artist_id),
    FOREIGN KEY (genre_id) REFERENCES genres(id) ON DELETE SET NULL,
    FOREIGN KEY (artist_id) REFERENCES artists(id) ON DELETE SET NULL,
    FOREIGN KEY (group_id) REFERENCES groups(id) ON DELETE CASCADE,
    PRIMARY KEY (id)
);

-- Create UsersGroups Table
CREATE TABLE users_groups(
	user_id INT NOT NULL,
	group_id INT NOT NULL,
	role VARCHAR(10) DEFAULT 'MEMBER',
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (group_id) REFERENCES groups(id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, group_id)
);

-- Create UsersSongs Table
CREATE TABLE users_songs(
	id SERIAL NOT NULL,
	user_id INT NOT NULL,
	group_id INT NOT NULL,
	song_id INT NOT NULL,
	performance INT NOT NULL DEFAULT 0,
	practiced_at TIMESTAMP DEFAULT NULL,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uq_user_group_song UNIQUE (user_id, group_id, song_id),
    CONSTRAINT chk_user_performance_range CHECK (performance BETWEEN 0 AND 10),
    CONSTRAINT chk_user_practiced_not_future CHECK (practiced_at <= CURRENT_TIMESTAMP),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (group_id) REFERENCES groups(id) ON DELETE CASCADE,
    FOREIGN KEY (song_id) REFERENCES songs(id) ON DELETE CASCADE,
    PRIMARY KEY (id)
);

-- Create (user_id, group_id) index for queries with this two columns
CREATE INDEX idx_user_group ON users_songs(user_id, group_id);

-- Create Repertoires Table
CREATE TABLE repertoires(
    id SERIAL NOT NULL,
    song_id INT NOT NULL,
    tone VARCHAR(3) DEFAULT NULL,
    comment VARCHAR(255) DEFAULT NULL,
    performance INT NOT NULL DEFAULT 0,
    practiced_at TIMESTAMP DEFAULT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_group_performance_range CHECK (performance BETWEEN 0 AND 10),
    CONSTRAINT chk_group_practiced_not_future CHECK (practiced_at <= CURRENT_TIMESTAMP),
    FOREIGN KEY (song_id) REFERENCES songs(id) ON DELETE CASCADE,
    PRIMARY KEY (id)
);
