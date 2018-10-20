# --- !Ups

CREATE TABLE feature_flip(
  id serial PRIMARY KEY,
  name text NOT NULL,
  activated boolean NOT NULL
);

INSERT INTO feature_flip(name, activated) values ('new-logo', true);
INSERT INTO feature_flip(name, activated) values ('show-settings', true);
INSERT INTO feature_flip(name, activated) values ('show-menu', false);
INSERT INTO feature_flip(name, activated) values ('new-policy', false);
INSERT INTO feature_flip(name, activated) values ('last-dynamic-update', false);

# --- !Downs
DROP TABLE feature_flip;
