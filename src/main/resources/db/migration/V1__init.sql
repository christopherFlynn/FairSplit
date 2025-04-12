-- === USERS TABLE ===
CREATE TABLE users (
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password_hash TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- === GROUPS TABLE ===
CREATE TABLE groups (
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    invite_code VARCHAR(20) NOT NULL UNIQUE,
    created_by UUID NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (created_by) REFERENCES users(id)
);

-- === GROUP MEMBERSHIPS ===
CREATE TABLE group_memberships (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    group_id UUID NOT NULL,
    joined_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (group_id) REFERENCES groups(id),
    UNIQUE (user_id, group_id)
);

-- === EXPENSES ===
CREATE TABLE expenses (
    id UUID PRIMARY KEY,
    group_id UUID NOT NULL,
    title VARCHAR(200) NOT NULL,
    amount NUMERIC(10, 2) NOT NULL,
    paid_by UUID NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (group_id) REFERENCES groups(id),
    FOREIGN KEY (paid_by) REFERENCES users(id)
);

-- === EXPENSE SPLITS ===
CREATE TABLE expense_splits (
    id UUID PRIMARY KEY,
    expense_id UUID NOT NULL,
    user_id UUID NOT NULL, -- who owes
    amount NUMERIC(10, 2) NOT NULL,
    FOREIGN KEY (expense_id) REFERENCES expenses(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);
