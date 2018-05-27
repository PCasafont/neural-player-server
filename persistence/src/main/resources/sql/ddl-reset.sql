DROP SCHEMA IF EXISTS neural_player;
CREATE SCHEMA neural_player;
USE neural_player;

CREATE TABLE user
(
  id           BIGINT      AUTO_INCREMENT PRIMARY KEY,
  username     VARCHAR(16) NOT NULL,
  email        VARCHAR(32) NOT NULL,
  password     VARCHAR(64) NOT NULL,
  display_name VARCHAR(32) NOT NULL,
  roles        LONGTEXT    NOT NULL,
  created_at   DATETIME    NOT NULL,
  CONSTRAINT user_name_unique UNIQUE (username),
  CONSTRAINT user_email_unique UNIQUE (email)
);

CREATE TABLE track
(
  id             BIGINT       AUTO_INCREMENT PRIMARY KEY,
  title          VARCHAR(128) NOT NULL,
  artist         VARCHAR(64)  NULL,
  album          VARCHAR(64)  NULL,
  file           LONGBLOB     NOT NULL,
  file_extension VARCHAR(8)   NOT NULL,
  created_at     DATETIME     NOT NULL
);

CREATE TABLE user_track
(
  user_id          BIGINT,
  track_id         BIGINT,
  preference_score DOUBLE NOT NULL,
  PRIMARY KEY (user_id, track_id),
  CONSTRAINT user_track_user_id_fk FOREIGN KEY (user_id) REFERENCES user (id)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  CONSTRAINT user_track_track_id_fk FOREIGN KEY (track_id) REFERENCES track (id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

CREATE TABLE playlist
(
  id           BIGINT       AUTO_INCREMENT PRIMARY KEY,
  name         VARCHAR(64)  NOT NULL,
  creator_id   BIGINT       NOT NULL,
  CONSTRAINT playlist_creator_id_fk FOREIGN KEY (creator_id) REFERENCES user (id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

CREATE TABLE playlist_track
(
  playlist_id BIGINT,
  track_id    BIGINT,
  PRIMARY KEY (playlist_id, track_id),
  CONSTRAINT playlist_track_playlist_id_fk FOREIGN KEY (playlist_id) REFERENCES playlist (id)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  CONSTRAINT playlist_track_track_id_fk FOREIGN KEY (track_id) REFERENCES track (id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);
