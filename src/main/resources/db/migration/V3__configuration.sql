CREATE TABLE configuration (
  config_key VARCHAR(50) NOT NULL,
  config_value VARCHAR(100) NOT NULL,
  PRIMARY KEY (config_key)
);

INSERT INTO configuration (config_key, config_value) values('passphrase', 'Y2RLQm85NXhjVFZWSDNkaA==');
