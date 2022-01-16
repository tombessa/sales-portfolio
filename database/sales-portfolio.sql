-- Gerado por Oracle SQL Developer Data Modeler 21.2.0.183.1957
--   em:        2022-01-16 14:36:40 BRST
--   site:      Oracle Database 11g
--   tipo:      Oracle Database 11g



-- predefined type, no DDL - MDSYS.SDO_GEOMETRY

-- predefined type, no DDL - XMLTYPE

CREATE TABLE sales.address (
    id            INTEGER NOT NULL,
    created       TIMESTAMP,
    updated       TIMESTAMP,
    created_by    VARCHAR2(15),
    updated_by    VARCHAR2(15),
    address_line1 VARCHAR2(255),
    address_line2 VARCHAR2(255),
    address_line3 VARCHAR2(255),
    address_line4 VARCHAR2(255),
    city          VARCHAR2(255),
    state         VARCHAR2(255),
    zipcode       VARCHAR2(10),
    country       VARCHAR2(20),
    status        VARCHAR2(10) DEFAULT 'ACTIVE'
);

ALTER TABLE sales.address
    ADD CONSTRAINT address_ck_1 CHECK ( status IN ( 'PENDING', 'ACTIVE', 'INACTIVE' ) );

ALTER TABLE sales.address ADD CONSTRAINT address_pk PRIMARY KEY ( id );

CREATE TABLE sales.client (
    id          INTEGER NOT NULL,
    created     TIMESTAMP,
    updated     TIMESTAMP,
    created_by  VARCHAR2(15),
    updated_by  VARCHAR2(15),
    status      VARCHAR2(20) DEFAULT 'ACTIVE',
    people_id   INTEGER NOT NULL,
    name        VARCHAR2(255),
    observation CLOB
);

ALTER TABLE sales.client
    ADD CONSTRAINT client_ck_1 CHECK ( status IN ( 'PENDING', 'ACTIVE', 'INACTIVE' ) );

ALTER TABLE sales.client ADD CONSTRAINT client_pk PRIMARY KEY ( id );

CREATE TABLE sales.document (
    id          INTEGER NOT NULL,
    created     TIMESTAMP,
    updated     TIMESTAMP,
    created_by  VARCHAR2(15),
    updated_by  VARCHAR2(15),
    status      VARCHAR2(20) DEFAULT 'PENDING',
    type        VARCHAR2(10),
    serial      VARCHAR2(255),
    observation CLOB,
    image       BLOB
);

CREATE INDEX sales.document__idx ON
    sales.document (
        status
    ASC );

ALTER TABLE sales.document
    ADD CONSTRAINT document_ck_1 CHECK ( status IN ( 'PENDING', 'ACTIVE', 'REJECTED', 'INACTIVE' ) );

ALTER TABLE sales.document
    ADD CONSTRAINT document_ck_2 CHECK ( type IN ( 'BOND', 'CERTIFICATE', 'CHARTER', 'COMPACT', 'CONSTITUTION',
                                                   'CONTRACT', 'COVENANT', 'DIPLOMA', 'GUARANTEE',
                                                   'GUARANTY',
                                                   'LICENSE',
                                                   'PASSPORT',
                                                   'LICENSE',
                                                   'PLEDGE',
                                                   'SURETY',
                                                   'WARRANT' ) );

ALTER TABLE sales.document ADD CONSTRAINT document_pk PRIMARY KEY ( id );

CREATE TABLE sales.entity_address (
    id          INTEGER NOT NULL,
    created     TIMESTAMP,
    updated     TIMESTAMP,
    created_by  VARCHAR2(15),
    updated_by  VARCHAR2(15),
    status      VARCHAR2(20) DEFAULT 'ACTIVE',
    supplier_id INTEGER NOT NULL,
    address_id  INTEGER NOT NULL,
    people_id   INTEGER NOT NULL,
    client_id   INTEGER NOT NULL
);

ALTER TABLE sales.entity_address
    ADD CONSTRAINT entity_address_ck_1 CHECK ( status IN ( 'PENDING', 'ACTIVE', 'INACTIVE' ) );

ALTER TABLE sales.entity_address ADD CONSTRAINT entity_address_pk PRIMARY KEY ( id );

ALTER TABLE sales.entity_address
    ADD CONSTRAINT supplier_address__un UNIQUE ( id,
                                                 supplier_id,
                                                 address_id );

ALTER TABLE sales.entity_address
    ADD CONSTRAINT people_address__un UNIQUE ( id,
                                               address_id,
                                               people_id );

ALTER TABLE sales.entity_address
    ADD CONSTRAINT client_address__un UNIQUE ( id,
                                               address_id,
                                               client_id );

CREATE TABLE sales.entity_document (
    id          INTEGER NOT NULL,
    created     TIMESTAMP,
    updated     TIMESTAMP,
    created_by  VARCHAR2(15),
    updated_by  VARCHAR2(15),
    status      VARCHAR2(20) DEFAULT 'ACTIVE',
    supplier_id INTEGER NOT NULL,
    document_id INTEGER NOT NULL,
    people_id   INTEGER NOT NULL,
    client_id   INTEGER NOT NULL
);

ALTER TABLE sales.entity_document
    ADD CONSTRAINT supplier_document_ck_1 CHECK ( status IN ( 'PENDING', 'ACTIVE', 'REJECTED', 'INACTIVE' ) );

ALTER TABLE sales.entity_document ADD CONSTRAINT supplier_document_pk PRIMARY KEY ( id );

ALTER TABLE sales.entity_document
    ADD CONSTRAINT supplier_document__un UNIQUE ( id,
                                                  supplier_id,
                                                  document_id );

ALTER TABLE sales.entity_document
    ADD CONSTRAINT people_document__un UNIQUE ( id,
                                                document_id,
                                                people_id );

ALTER TABLE sales.entity_document
    ADD CONSTRAINT client_document__un UNIQUE ( id,
                                                document_id,
                                                client_id );

CREATE TABLE sales."ORDER" (
    id                  INTEGER NOT NULL,
    created             TIMESTAMP,
    updated             TIMESTAMP,
    created_by          VARCHAR2(15),
    updated_by          VARCHAR2(15),
    status              VARCHAR2(20) DEFAULT 'PENDING',
    client_id           INTEGER NOT NULL,
    delivery_address_id INTEGER NOT NULL,
    billing_address_id  INTEGER NOT NULL,
    amount              NUMBER(10, 2),
    amount_paid         NUMBER(10, 2),
    discount_rate       NUMBER(10, 2)
);

CREATE INDEX sales.order__idx ON
    sales."ORDER" (
        status
    ASC,
        client_id
    ASC );

CREATE INDEX sales.order__idxv1 ON
    sales."ORDER" (
        status
    ASC );

CREATE INDEX sales.order__idxv2 ON
    sales."ORDER" (
        status
    ASC,
        amount
    ASC );

CREATE INDEX sales.order__idxv3 ON
    sales."ORDER" (
        status
    ASC,
        discount_rate
    ASC );

ALTER TABLE sales."ORDER"
    ADD CONSTRAINT order_ck_1 CHECK ( status IN ( 'PENDING', 'ACTIVE', 'REJECTED', 'INACTIVE' ) );

ALTER TABLE sales."ORDER" ADD CONSTRAINT order_pk PRIMARY KEY ( id );

CREATE TABLE sales.order_item (
    id               INTEGER NOT NULL,
    created          TIMESTAMP,
    updated          TIMESTAMP,
    created_by       VARCHAR2(15),
    updated_by       VARCHAR2(15),
    status           VARCHAR2(20) DEFAULT 'PENDING',
    product_id       INTEGER NOT NULL,
    price_id         INTEGER NOT NULL,
    order_id         INTEGER NOT NULL,
    qtd              INTEGER,
    "UNITARY_VALUE " NUMBER(10, 2),
    "TOTAL_VALUE "   NUMBER(10, 2),
    discount_rate    NUMBER(10, 2)
);

CREATE INDEX sales.order_item__idx ON
    sales.order_item (
        status
    ASC,
        order_id
    ASC );

ALTER TABLE sales.order_item
    ADD CONSTRAINT order_item_ck_1 CHECK ( status IN ( 'PENDING', 'ACTIVE', 'REJECTED', 'INACTIVE' ) );

ALTER TABLE sales.order_item ADD CONSTRAINT order_item_pk PRIMARY KEY ( id );

CREATE TABLE sales.payment (
    id            INTEGER NOT NULL,
    created       TIMESTAMP,
    updated       TIMESTAMP,
    created_by    VARCHAR2(15),
    updated_by    VARCHAR2(15),
    status        VARCHAR2(20) DEFAULT 'PENDING',
    type          VARCHAR2(50),
    amount        NUMBER(10, 2),
    order_id      INTEGER NOT NULL,
    "RECEIVED BY" VARCHAR2(15)
);

ALTER TABLE sales.payment
    ADD CONSTRAINT payment_ck_1 CHECK ( status IN ( 'PENDING', 'ACTIVE', 'REJECTED', 'INACTIVE' ) );

ALTER TABLE sales.payment
    ADD CONSTRAINT payment_ck_2 CHECK ( type IN ( 'CREDIT', 'TRANSFER ACCOUNT', 'IN CASH', 'DEBIT', 'OTHER' ) );

ALTER TABLE sales.payment ADD CONSTRAINT payment_pk PRIMARY KEY ( id );

CREATE TABLE sales.people (
    id         INTEGER NOT NULL,
    created    TIMESTAMP,
    updated    TIMESTAMP,
    created_by VARCHAR2(15),
    updated_by VARCHAR2(15),
    status     VARCHAR2(20) DEFAULT 'PENDING',
    address_id INTEGER NOT NULL,
    name       VARCHAR2(255),
    cellphone  VARCHAR2(20),
    otherphone VARCHAR2(20),
    email      VARCHAR2(25),
    birthdate  TIMESTAMP
);

ALTER TABLE sales.people
    ADD CONSTRAINT people_ck_1 CHECK ( status IN ( 'PENDING', 'ACTIVE', 'INACTIVE' ) );

ALTER TABLE sales.people ADD CONSTRAINT people_pk PRIMARY KEY ( id );

CREATE TABLE sales.price (
    id            INTEGER NOT NULL,
    created       TIMESTAMP,
    updated       TIMESTAMP,
    created_by    VARCHAR2(15),
    updated_by    VARCHAR2(15),
    status        VARCHAR2(10) DEFAULT 'ACTIVE',
    value         NUMBER(10, 2),
    profit_margin NUMBER(10, 2),
    product_id    INTEGER NOT NULL
);

CREATE INDEX sales.price__idx ON
    sales.price (
        status
    ASC,
        profit_margin
    ASC );

CREATE INDEX sales.price__idxv1 ON
    sales.price (
        status
    ASC,
        product_id
    ASC );

ALTER TABLE sales.price
    ADD CONSTRAINT price_ck_1 CHECK ( status IN ( 'PENDING', 'ACTIVE', 'INACTIVE' ) );

ALTER TABLE sales.price ADD CONSTRAINT price_pk PRIMARY KEY ( id );

CREATE TABLE sales.product (
    id          INTEGER NOT NULL,
    created     TIMESTAMP,
    updated     TIMESTAMP,
    created_by  VARCHAR2(15),
    updated_by  VARCHAR2(15),
    name        VARCHAR2(50),
    description CLOB,
    picture     BLOB,
    status      VARCHAR2(10) DEFAULT 'ACTIVE',
    supplier_id INTEGER NOT NULL
);

ALTER TABLE sales.product
    ADD CONSTRAINT product_ck_1 CHECK ( status IN ( 'PENDING', 'ACTIVE', 'INACTIVE' ) );

ALTER TABLE sales.product ADD CONSTRAINT product_pk PRIMARY KEY ( id );

CREATE TABLE sales.role (
    id         INTEGER NOT NULL,
    created    TIMESTAMP,
    updated    TIMESTAMP,
    created_by VARCHAR2(15),
    updated_by VARCHAR2(15),
    name       VARCHAR2(50),
    status     VARCHAR2(10) DEFAULT 'ACTIVE'
);

ALTER TABLE sales.role
    ADD CONSTRAINT role_ck_1 CHECK ( status IN ( 'PENDING', 'ACTIVE', 'INACTIVE' ) );

ALTER TABLE sales.role ADD CONSTRAINT role_pk PRIMARY KEY ( id );

CREATE TABLE sales.supplier (
    id          INTEGER NOT NULL,
    created     TIMESTAMP,
    updated     TIMESTAMP,
    created_by  VARCHAR2(15),
    updated_by  VARCHAR2(15),
    status      VARCHAR2(20) DEFAULT 'ACTIVE',
    name        VARCHAR2(255),
    people_id   INTEGER NOT NULL,
    observation CLOB
);

CREATE INDEX sales.supplier__idx ON
    sales.supplier (
        name
    ASC,
        status
    ASC );

CREATE INDEX sales.supplier__idxv1 ON
    sales.supplier (
        status
    ASC );

ALTER TABLE sales.supplier
    ADD CONSTRAINT supplier_ck_1 CHECK ( status IN ( 'PENDING', 'ACTIVE', 'INACTIVE' ) );

ALTER TABLE sales.supplier ADD CONSTRAINT supplier_pk PRIMARY KEY ( id );

CREATE TABLE sales.user_access (
    id         INTEGER
        CONSTRAINT nnc_user_access_id NOT NULL,
    created    TIMESTAMP,
    updated    TIMESTAMP,
    created_by VARCHAR2(15),
    updated_by VARCHAR2(15),
    status     VARCHAR2(10) DEFAULT 'PENDING',
    login      VARCHAR2(15),
    password   CLOB,
    people_id  INTEGER NOT NULL,
    role_id    INTEGER NOT NULL
);

CREATE INDEX sales.user_access__idx ON
    sales.user_access (
        login
    ASC );

CREATE INDEX sales.user_access__idxv1 ON
    sales.user_access (
        role_id
    ASC );

ALTER TABLE sales.user_access
    ADD CONSTRAINT user_access_ck_1 CHECK ( status IN ( 'PENDING', 'ACTIVE', 'INACTIVE' ) );

ALTER TABLE sales.user_access ADD CONSTRAINT user_access_pk PRIMARY KEY ( id );

ALTER TABLE sales.user_access ADD CONSTRAINT user_access__un UNIQUE ( login );

ALTER TABLE sales.client
    ADD CONSTRAINT client_people_fk FOREIGN KEY ( people_id )
        REFERENCES sales.people ( id );

ALTER TABLE sales.entity_address
    ADD CONSTRAINT entity_address_address_fk FOREIGN KEY ( address_id )
        REFERENCES sales.address ( id );

ALTER TABLE sales.entity_address
    ADD CONSTRAINT entity_address_client_fk FOREIGN KEY ( client_id )
        REFERENCES sales.client ( id );

ALTER TABLE sales.entity_address
    ADD CONSTRAINT entity_address_people_fk FOREIGN KEY ( people_id )
        REFERENCES sales.people ( id );

ALTER TABLE sales.entity_address
    ADD CONSTRAINT entity_address_supplier_fk FOREIGN KEY ( supplier_id )
        REFERENCES sales.supplier ( id );

ALTER TABLE sales.entity_document
    ADD CONSTRAINT entity_document_client_fk FOREIGN KEY ( client_id )
        REFERENCES sales.client ( id );

ALTER TABLE sales.entity_document
    ADD CONSTRAINT entity_document_people_fk FOREIGN KEY ( people_id )
        REFERENCES sales.people ( id );

ALTER TABLE sales."ORDER"
    ADD CONSTRAINT order_address_fk FOREIGN KEY ( delivery_address_id )
        REFERENCES sales.address ( id );

ALTER TABLE sales."ORDER"
    ADD CONSTRAINT order_address_fkv1 FOREIGN KEY ( billing_address_id )
        REFERENCES sales.address ( id );

ALTER TABLE sales."ORDER"
    ADD CONSTRAINT order_client_fk FOREIGN KEY ( client_id )
        REFERENCES sales.client ( id );

ALTER TABLE sales.order_item
    ADD CONSTRAINT order_item_order_fk FOREIGN KEY ( order_id )
        REFERENCES sales."ORDER" ( id );

ALTER TABLE sales.order_item
    ADD CONSTRAINT order_item_price_fk FOREIGN KEY ( price_id )
        REFERENCES sales.price ( id );

ALTER TABLE sales.order_item
    ADD CONSTRAINT order_item_product_fk FOREIGN KEY ( product_id )
        REFERENCES sales.product ( id );

ALTER TABLE sales.payment
    ADD CONSTRAINT payment_order_fk FOREIGN KEY ( order_id )
        REFERENCES sales."ORDER" ( id );

ALTER TABLE sales.price
    ADD CONSTRAINT price_product_fk FOREIGN KEY ( product_id )
        REFERENCES sales.product ( id );

ALTER TABLE sales.product
    ADD CONSTRAINT product_supplier_fk FOREIGN KEY ( supplier_id )
        REFERENCES sales.supplier ( id );

ALTER TABLE sales.entity_document
    ADD CONSTRAINT supplier_document_document_fk FOREIGN KEY ( document_id )
        REFERENCES sales.document ( id );

ALTER TABLE sales.entity_document
    ADD CONSTRAINT supplier_document_supplier_fk FOREIGN KEY ( supplier_id )
        REFERENCES sales.supplier ( id );

ALTER TABLE sales.supplier
    ADD CONSTRAINT supplier_people_fk FOREIGN KEY ( people_id )
        REFERENCES sales.people ( id );

ALTER TABLE sales.user_access
    ADD CONSTRAINT user_access_people_fk FOREIGN KEY ( people_id )
        REFERENCES sales.people ( id );

ALTER TABLE sales.user_access
    ADD CONSTRAINT user_access_role_fk FOREIGN KEY ( role_id )
        REFERENCES sales.role ( id );

CREATE SEQUENCE sales.sq_address START WITH 1 MINVALUE 1 MAXVALUE 9999999999999999 NOCACHE ORDER;

CREATE SEQUENCE sales.sq_client START WITH 1 MINVALUE 1 MAXVALUE 9999999999999999 NOCACHE ORDER;

CREATE SEQUENCE sales.sq_document START WITH 1 MINVALUE 1 MAXVALUE 9999999999999999 NOCACHE ORDER;

CREATE SEQUENCE sales.sq_entity_address START WITH 1 MINVALUE 1 MAXVALUE 9999999999999999 NOCACHE ORDER;

CREATE SEQUENCE sales.sq_entity_document START WITH 1 MINVALUE 1 MAXVALUE 9999999999999999 NOCACHE ORDER;

CREATE SEQUENCE sales.sq_order START WITH 1 MINVALUE 1 MAXVALUE 9999999999999999 NOCACHE ORDER;

CREATE SEQUENCE sales.sq_order_item START WITH 1 MINVALUE 1 MAXVALUE 9999999999999999 NOCACHE ORDER;

CREATE SEQUENCE sales.sq_payment START WITH 1 MINVALUE 1 MAXVALUE 9999999999999999 NOCACHE ORDER;

CREATE SEQUENCE sales.sq_people START WITH 1 MINVALUE 1 MAXVALUE 9999999999999999 NOCACHE ORDER;

CREATE SEQUENCE sales.sq_price START WITH 1 MINVALUE 1 MAXVALUE 9999999999999999 NOCACHE ORDER;

CREATE SEQUENCE sales.sq_product START WITH 1 MINVALUE 1 MAXVALUE 9999999999999999 NOCACHE ORDER;

CREATE SEQUENCE sales.sq_role START WITH 1 MINVALUE 1 MAXVALUE 9999999999999999 NOCACHE ORDER;

CREATE SEQUENCE sales.sq_supplier START WITH 1 MINVALUE 1 MAXVALUE 9999999999999999 NOCACHE ORDER;

CREATE SEQUENCE sales.sq_user_access START WITH 1 MINVALUE 1 MAXVALUE 9999999999999999 NOCACHE ORDER;



-- Relatório do Resumo do Oracle SQL Developer Data Modeler: 
-- 
-- CREATE TABLE                            14
-- CREATE INDEX                            12
-- ALTER TABLE                             58
-- CREATE VIEW                              0
-- ALTER VIEW                               0
-- CREATE PACKAGE                           0
-- CREATE PACKAGE BODY                      0
-- CREATE PROCEDURE                         0
-- CREATE FUNCTION                          0
-- CREATE TRIGGER                           0
-- ALTER TRIGGER                            0
-- CREATE COLLECTION TYPE                   0
-- CREATE STRUCTURED TYPE                   0
-- CREATE STRUCTURED TYPE BODY              0
-- CREATE CLUSTER                           0
-- CREATE CONTEXT                           0
-- CREATE DATABASE                          0
-- CREATE DIMENSION                         0
-- CREATE DIRECTORY                         0
-- CREATE DISK GROUP                        0
-- CREATE ROLE                              0
-- CREATE ROLLBACK SEGMENT                  0
-- CREATE SEQUENCE                         14
-- CREATE MATERIALIZED VIEW                 0
-- CREATE MATERIALIZED VIEW LOG             0
-- CREATE SYNONYM                           0
-- CREATE TABLESPACE                        0
-- CREATE USER                              0
-- 
-- DROP TABLESPACE                          0
-- DROP DATABASE                            0
-- 
-- REDACTION POLICY                         0
-- 
-- ORDS DROP SCHEMA                         0
-- ORDS ENABLE SCHEMA                       0
-- ORDS ENABLE OBJECT                       0
-- 
-- ERRORS                                   0
-- WARNINGS                                 0
