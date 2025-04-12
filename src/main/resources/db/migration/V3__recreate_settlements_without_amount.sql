-- Drop the existing settlements table if it exists
DROP TABLE IF EXISTS settlements;

-- Recreate the settlements table without the amount column
CREATE TABLE settlements (
    id UUID PRIMARY KEY,
    group_id UUID NOT NULL,
    from_user_id UUID NOT NULL,
    to_user_id UUID NOT NULL,
    is_paid BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (group_id) REFERENCES groups(id),
    FOREIGN KEY (from_user_id) REFERENCES users(id),
    FOREIGN KEY (to_user_id) REFERENCES users(id)
);
