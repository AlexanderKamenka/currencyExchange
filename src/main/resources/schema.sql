-- schema.sql
CREATE TABLE IF NOT EXISTS CURRENCIES (
    id INT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(10) NOT NULL,
    name VARCHAR(255) NOT NULL,
    sign VARCHAR(10),
    CONSTRAINT unique_code UNIQUE (code),
    CONSTRAINT unique_name UNIQUE (name)
);
CREATE TABLE IF NOT EXISTS EXCHANGE_RATES (
     id INT AUTO_INCREMENT PRIMARY KEY,
    baseCurrencyId INT NOT NULL,
    targetCurrencyId INT NOT NULL,
    rate DECIMAL(6, 2) NOT NULL,
    CONSTRAINT fk_base_currency FOREIGN KEY (baseCurrencyId) REFERENCES CURRENCIES(id) ON DELETE CASCADE,
    CONSTRAINT fk_target_currency FOREIGN KEY (targetCurrencyId) REFERENCES CURRENCIES(id) ON DELETE CASCADE,
    CONSTRAINT unique_currency_pair UNIQUE (baseCurrencyId, targetCurrencyId)
);
