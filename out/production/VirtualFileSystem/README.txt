// Структура таблиц необходима следующая

CREATE TABLE Folders
(
id    INTEGER     AUTO_INCREMENT,
parent_id INTEGER  NULL,
name  VARCHAR(255)     NOT NULL,
PRIMARY KEY (id),
FOREIGN KEY (parent_id)  REFERENCES Folders(id) ON DELETE CASCADE,
UNIQUE    (name, parent_id)
);


CREATE TABLE Files
(
id    INTEGER     AUTO_INCREMENT,
name  VARCHAR(255)      NOT NULL,
folder_id INTEGER  NOT NULL ,
PRIMARY KEY (id),
FOREIGN KEY (folder_id)   REFERENCES Folders(id) ON DELETE CASCADE,
UNIQUE    (name, folder_id)
);
