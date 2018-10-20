# --- !Ups

CREATE TABLE feature_flip(
  id serial PRIMARY KEY,
  name text NOT NULL,
  activated boolean NOT NULL
);

INSERT INTO feature_flip(id, name, activated) values (1, 'new-logo', true);
INSERT INTO feature_flip(id, name, activated) values (2, 'show-settings', true);
INSERT INTO feature_flip(id, name, activated) values (3, 'show-menu', false);
INSERT INTO feature_flip(id, name, activated) values (4, 'new-policy', false);
INSERT INTO feature_flip(id, name, activated) values (5, 'last-dynamic-update', false);

# --- !Downs
DROP TABLE feature_flip;
