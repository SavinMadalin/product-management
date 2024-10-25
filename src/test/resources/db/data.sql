CREATE TABLE PRODUCT (
    ID INTEGER PRIMARY KEY AUTO_INCREMENT,
    NAME VARCHAR(200) NOT NULL,
    PRICE VARCHAR(200) NOT NULL,
    DESCRIPTION VARCHAR(200),
    CREATED_BY VARCHAR(200),
    UPDATED_BY VARCHAR(200)
);

INSERT INTO PRODUCT(ID, NAME, PRICE)
values ('234', 'book', '123');

INSERT INTO PRODUCT(ID, NAME, PRICE)
values ('123', 'pen', '12');