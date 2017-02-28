CREATE TABLE Users (
       id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
       login VARCHAR(32) UNIQUE,
       mdp VARCHAR(40),
       nom VARCHAR(255),
       prenom VARCHAR(255),
       mail VARCHAR(255)
);

CREATE TABLE Followers (
       id_from INTEGER,
       id_to INTEGER,
       timestamp TIMESTAMP,
       PRIMARY KEY(id_from, id_to),
       FOREIGN KEY(id_from) REFERENCES Users(id),
       FOREIGN KEY(id_to) REFERENCES Users(id)
);

CREATE TABLE Sessions (
       session_key VARCHAR(36) UNIQUE,
       id INTEGER,
       timestamp TIMESTAMP,
       root BOOLEAN,
       PRIMARY KEY(session_key),
       INDEX(session_key, id, timestamp)
);

