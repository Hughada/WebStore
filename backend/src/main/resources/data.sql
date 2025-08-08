-- Insert admin user
INSERT INTO "user" (nom, prenom, email, password, telephone, role)
VALUES ('Admin', 'Super', 'admin@hughadatrips.com', '$2a$10$tP9p9F9Z6KZLx7Vvjd1ImmImBWt6hxE76tLnJuJSDrNzyc2yz48We', '0600000000', 'ADMIN'); 
-- password: admin123

-- Insert clients
INSERT INTO "user" (nom, prenom, email, password, telephone, role)
VALUES ('Client', 'Un', 'client1@hughadatrips.com', '$2a$10$tP9p9F9Z6KZLx7Vvjd1ImmImBWt6hxE76tLnJuJSDrNzyc2yz48We', '0611111111', 'CLIENT');
INSERT INTO "user" (nom, prenom, email, password, telephone, role)
VALUES ('Client', 'Deux', 'client2@hughadatrips.com', '$2a$10$tP9p9F9Z6KZLx7Vvjd1ImmImBWt6hxE76tLnJuJSDrNzyc2yz48We', '0622222222', 'CLIENT');

-- Insert trips
INSERT INTO trip (titre, description, date_depart, duree, prix, nb_places, images, statut)
VALUES ('Plongée à Hurghada', 'Explorez les récifs coralliens et la vie marine.', CURRENT_DATE + INTERVAL '10 day', 7, 1200.00, 15, 'https://example.com/image1.jpg,https://example.com/image2.jpg', 'DISPONIBLE');

INSERT INTO trip (titre, description, date_depart, duree, prix, nb_places, images, statut)
VALUES ('Safari Désert', 'Une aventure en 4x4 dans le désert égyptien.', CURRENT_DATE + INTERVAL '20 day', 3, 450.00, 10, 'https://example.com/safari1.jpg', 'DISPONIBLE');

INSERT INTO trip (titre, description, date_depart, duree, prix, nb_places, images, statut)
VALUES ('Visite du Caire', 'Découvrez les pyramides et le musée égyptien.', CURRENT_DATE + INTERVAL '30 day', 5, 800.00, 20, 'https://example.com/cairo1.jpg,https://example.com/cairo2.jpg', 'DISPONIBLE');

INSERT INTO trip (titre, description, date_depart, duree, prix, nb_places, images, statut)
VALUES ('Croisière sur le Nil', 'Naviguez sur le Nil en découvrant l\'Égypte ancienne.', CURRENT_DATE + INTERVAL '40 day', 10, 2200.00, 12, 'https://example.com/nile1.jpg', 'DISPONIBLE');

INSERT INTO trip (titre, description, date_depart, duree, prix, nb_places, images, statut)
VALUES ('Plage et détente', 'Profitez du soleil et des plages de la Mer Rouge.', CURRENT_DATE + INTERVAL '5 day', 6, 900.00, 25, 'https://example.com/beach1.jpg', 'DISPONIBLE');

-- Insert reservations
INSERT INTO reservation (user_id, trip_id, nb_places, date_reservation, statut)
VALUES (2, 1, 2, CURRENT_TIMESTAMP - INTERVAL '2 day', 'CONFIRMEE');

INSERT INTO reservation (user_id, trip_id, nb_places, date_reservation, statut)
VALUES (3, 2, 1, CURRENT_TIMESTAMP - INTERVAL '1 day', 'EN_ATTENTE');

INSERT INTO reservation (user_id, trip_id, nb_places, date_reservation, statut)
VALUES (2, 3, 3, CURRENT_TIMESTAMP - INTERVAL '3 day', 'ANNULEE');

INSERT INTO reservation (user_id, trip_id, nb_places, date_reservation, statut)
VALUES (3, 4, 1, CURRENT_TIMESTAMP - INTERVAL '4 day', 'CONFIRMEE');

INSERT INTO reservation (user_id, trip_id, nb_places, date_reservation, statut)
VALUES (2, 5, 5, CURRENT_TIMESTAMP - INTERVAL '5 day', 'CONFIRMEE');

-- Insert reviews
INSERT INTO review (note, commentaire, date, user_id, trip_id)
VALUES (5, 'Inoubliable aventure!', CURRENT_TIMESTAMP - INTERVAL '1 day', 2, 1);

INSERT INTO review (note, commentaire, date, user_id, trip_id)
VALUES (4, 'Superbe expérience.', CURRENT_TIMESTAMP - INTERVAL '2 day', 3, 2);

INSERT INTO review (note, commentaire, date, user_id, trip_id)
VALUES (3, 'Correct mais peut mieux faire.', CURRENT_TIMESTAMP - INTERVAL '3 day', 2, 3);

INSERT INTO review (note, commentaire, date, user_id, trip_id)
VALUES (5, 'Magnifique croisière.', CURRENT_TIMESTAMP - INTERVAL '1 day', 3, 4);

INSERT INTO review (note, commentaire, date, user_id, trip_id)
VALUES (4, 'Très relaxant.', CURRENT_TIMESTAMP - INTERVAL '5 day', 2, 5);

INSERT INTO review (note, commentaire, date, user_id, trip_id)
VALUES (2, 'Pas à la hauteur de mes attentes.', CURRENT_TIMESTAMP - INTERVAL '6 day', 3, 1);

INSERT INTO review (note, commentaire, date, user_id, trip_id)
VALUES (5, 'Guide très compétent.', CURRENT_TIMESTAMP - INTERVAL '10 day', 2, 2);

INSERT INTO review (note, commentaire, date, user_id, trip_id)
VALUES (4, 'Bon rapport qualité prix.', CURRENT_TIMESTAMP - INTERVAL '12 day', 3, 3);

INSERT INTO review (note, commentaire, date, user_id, trip_id)
VALUES (3, 'Expérience agréable.', CURRENT_TIMESTAMP - INTERVAL '8 day', 2, 4);

INSERT INTO review (note, commentaire, date, user_id, trip_id)
VALUES (5, 'Je recommande vivement!', CURRENT_TIMESTAMP - INTERVAL '2 day', 3, 5);

-- Insert contact messages
INSERT INTO contact_message (nom, email, sujet, contenu, date)
VALUES ('Alice Dupont', 'alice.dupont@example.com', 'Question sur une réservation', 'Bonjour, comment puis-je modifier ma réservation ?', CURRENT_TIMESTAMP - INTERVAL '1 day');

INSERT INTO contact_message (nom, email, sujet, contenu, date)
VALUES ('Bob Martin', 'bob.martin@example.com', 'Problème de paiement', 'Mon paiement a échoué, que faire ?', CURRENT_TIMESTAMP - INTERVAL '2 day');

INSERT INTO contact_message (nom, email, sujet, contenu, date)
VALUES ('Clara Durand', 'clara.durand@example.com', 'Suggestion', 'J\'aimerais voir plus d\'offres pour les familles.', CURRENT_TIMESTAMP - INTERVAL '3 day');

-- Insert payments (simulated)
INSERT INTO payment (reservation_id, montant, date, methode, statut)
VALUES (1, 2400.00, CURRENT_TIMESTAMP - INTERVAL '1 day', 'Stripe', 'SUCCESS');

INSERT INTO payment (reservation_id, montant, date, methode, statut)
VALUES (2, 450.00, CURRENT_TIMESTAMP - INTERVAL '1 day', 'Stripe', 'PENDING');

INSERT INTO payment (reservation_id, montant, date, methode, statut)
VALUES (4, 2200.00, CURRENT_TIMESTAMP - INTERVAL '1 day', 'Stripe', 'FAILED');
