CREATE TABLE users (
  username VARCHAR(50) NOT NULL,
  password VARCHAR(100) NOT NULL,
  enabled SMALLINT NOT NULL DEFAULT 1,
  PRIMARY KEY (username)
);

CREATE TABLE authorities (
  username VARCHAR(50) NOT NULL,
  authority VARCHAR(50) NOT NULL,
  FOREIGN KEY (username) REFERENCES users(username)
);

CREATE UNIQUE INDEX ix_auth_username
  on authorities (username,authority);

INSERT INTO users (username, password, enabled)
  values ('admin',
    '$2a$10$mievjAFubpUyOAccOiN9ceHKi.3v4IaAs9YsnGnfAaLXxmraigVEG',
    1);

INSERT INTO authorities (username, authority)
  values ('admin', 'ROLE_STANDARD_ROLE'); -- Needs manual Prefix Role

