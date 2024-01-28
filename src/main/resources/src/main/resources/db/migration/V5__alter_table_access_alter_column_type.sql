ALTER TABLE access_table
    ALTER COLUMN access_type TYPE smallint
    USING CASE WHEN access_type = 'ENTRY' THEN 1
    WHEN access_type = 'EXIT' THEN 2
    ELSE 0 END;