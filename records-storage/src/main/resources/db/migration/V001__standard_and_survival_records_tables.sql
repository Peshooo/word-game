CREATE TABLE IF NOT EXISTS standard_records (
    game_id char(36) NOT NULL PRIMARY KEY,
    nickname varchar(36) NOT NULL,
    score bigint NOT NULL,
    created_at TIMESTAMP NOT NULL
);

CREATE INDEX standard_records_score_index ON standard_records (score);
CREATE INDEX standard_records_created_at ON standard_records (created_at);

CREATE TABLE IF NOT EXISTS survival_records (
    game_id char(36) NOT NULL PRIMARY KEY,
    nickname varchar(36) NOT NULL,
    score bigint NOT NULL,
    created_at TIMESTAMP NOT NULL
);

CREATE INDEX survival_records_score_index ON survival_records (score);
CREATE INDEX survival_records_created_at ON survival_records (created_at);