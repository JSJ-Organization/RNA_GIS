DO
$$
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM pg_tables WHERE schemaname = 'public' AND tablename = 'vworld_log') THEN
            CREATE TABLE vworld_log
            (
                id           bigint       not null primary key,
                created_at   timestamp(6) not null,
                error_code   varchar(255),
                error_text   varchar(255),
                search_query varchar(255),
                status       varchar(255)
            );
        END IF;

        IF NOT EXISTS (SELECT 1
                       FROM pg_sequences
                       WHERE schemaname = 'public'
                         AND sequencename = 'vworld_log_seq') THEN
            CREATE SEQUENCE vworld_log_seq
                INCREMENT BY 50;
        END IF;

        ALTER TABLE vworld_log
            OWNER TO admin;
        ALTER SEQUENCE vworld_log_seq OWNER TO admin;

    END
$$;