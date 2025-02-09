-- data.sql
MERGE INTO currencies (fullName, code, sign)
    KEY (code)
    VALUES ('US Dollar', 'USD', '$');

MERGE INTO currencies (fullName, code, sign)
    KEY (code)
    VALUES ('Euro', 'EUR', '€');

MERGE INTO currencies (fullName, code, sign)
    KEY (code)
    VALUES ('British Pound', 'GBP', '£');

MERGE INTO currencies (fullName, code, sign)
    KEY (code)
    VALUES ('Japanese Yen', 'JPY', '¥');

MERGE INTO currencies (fullName, code, sign)
    KEY (code)
    VALUES ('Swiss Franc', 'CHF', '₣');
