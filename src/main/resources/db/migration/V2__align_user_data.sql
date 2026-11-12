-- Align the original schema with the API model. Identifiers are strings so
-- leading zeroes are preserved and INN values may contain 10 or 12 digits.
UPDATE users
SET status = 'ACTIVE'
WHERE status IS NULL;

ALTER TABLE users
    ALTER COLUMN status SET DEFAULT 'ACTIVE'::user_status,
    ALTER COLUMN status SET NOT NULL,
    ALTER COLUMN inn TYPE VARCHAR(12) USING inn::text;

ALTER TABLE users_addresses
    ALTER COLUMN index TYPE VARCHAR(6) USING index::text;

ALTER TABLE users_documents
    ALTER COLUMN passport_series TYPE VARCHAR(4) USING passport_series::text,
    ALTER COLUMN passport_number TYPE VARCHAR(6) USING passport_number::text;

ALTER TABLE users_documents
    DROP COLUMN inn;
