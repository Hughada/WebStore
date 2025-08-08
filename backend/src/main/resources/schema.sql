CREATE SCHEMA public;

CREATE TABLE IF NOT EXISTS "user" (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    prenom VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    telephone VARCHAR(50) NOT NULL,
    role VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS trip (
    id SERIAL PRIMARY KEY,
    titre VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    date_depart DATE NOT NULL,
    duree INT NOT NULL CHECK (duree > 0),
    prix NUMERIC(10, 2) NOT NULL CHECK (prix >= 0),
    nb_places INT NOT NULL CHECK (nb_places >= 0),
    images TEXT,
    statut VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS reservation (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES "user"(id) ON DELETE CASCADE,
    trip_id INT NOT NULL REFERENCES trip(id) ON DELETE CASCADE,
    nb_places INT NOT NULL CHECK (nb_places > 0),
    date_reservation TIMESTAMP NOT NULL,
    statut VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS payment (
    id SERIAL PRIMARY KEY,
    reservation_id INT NOT NULL REFERENCES reservation(id) ON DELETE CASCADE,
    montant NUMERIC(10, 2) NOT NULL CHECK (montant >= 0),
    date TIMESTAMP NOT NULL,
    methode VARCHAR(50) NOT NULL,
    statut VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS review (
    id SERIAL PRIMARY KEY,
    note INT NOT NULL CHECK (note >= 1 AND note <= 5),
    commentaire TEXT,
    date TIMESTAMP NOT NULL,
    user_id INT NOT NULL REFERENCES "user"(id) ON DELETE CASCADE,
    trip_id INT NOT NULL REFERENCES trip(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS contact_message (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    sujet VARCHAR(255) NOT NULL,
    contenu TEXT NOT NULL,
    date TIMESTAMP NOT NULL
);

CREATE INDEX idx_user_email ON "user"(email);
CREATE INDEX idx_trip_statut ON trip(statut);
CREATE INDEX idx_reservation_user_id ON reservation(user_id);
CREATE INDEX idx_review_trip_id ON review(trip_id);
