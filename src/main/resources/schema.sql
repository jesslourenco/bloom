CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE IF NOT EXISTS customers (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  first_name VARCHAR(100) NOT NULL,
  last_name VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL UNIQUE,
  phone VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS category (
  id SERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL UNIQUE,
  parent_category_id INTEGER REFERENCES category(id) ON DELETE SET NULL
);

CREATE INDEX idx_category_name ON category(name);

CREATE TABLE IF NOT EXISTS catalog (
    id SERIAL PRIMARY KEY,
    product_name VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
    category INTEGER REFERENCES category(id) ON DELETE SET NULL,
    brand VARCHAR(50),
    img_url TEXT,
    price NUMERIC,
    cost NUMERIC,
    stock_qty INTEGER,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE INDEX idx_product_name ON catalog(product_name);

