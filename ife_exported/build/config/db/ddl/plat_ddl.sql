/*
 * PLATFORM Data Definition Language scripts - CREATE TABLE
 */

--------------------------------------------------------
--  REF_COUNTRIES
--------------------------------------------------------
CREATE TABLE REF_COUNTRIES
(
    CO_COUNTRY_CODE_PK VARCHAR2(2 BYTE) 	NOT NULL,
    CO_COUNTRY_DESC    VARCHAR2(40 BYTE) 	NOT NULL,
    CONSTRAINT COUNTRIES_PK PRIMARY KEY (CO_COUNTRY_CODE_PK)
);

--------------------------------------------------------
--  REF_ELEMENTTYPES
--------------------------------------------------------
CREATE TABLE REF_ELEMENTTYPES
(
    ET_ELEMENTTYPE_ID_PK NUMBER(3,0) 		NOT NULL,
    ET_ELEMENTTYPE_DESC  VARCHAR2(25 BYTE) 	NOT NULL,
    CONSTRAINT REF_ELEMENTTYPES_PK PRIMARY KEY (ET_ELEMENTTYPE_ID_PK)
);

--------------------------------------------------------
--  REF_DATEFORMATS
--------------------------------------------------------
CREATE TABLE REF_DATEFORMATS
(
    DF_DATEFORMAT_ID_PK NUMBER(9,0) 		NOT NULL,
    DF_DATEFORMAT_DESC  VARCHAR2(14 BYTE) 	NOT NULL,
    CONSTRAINT REF_DATEFORMATS_PK PRIMARY KEY (DF_DATEFORMAT_ID_PK)
);
  
--------------------------------------------------------
--  REF_ELEMENTFORMATS
--------------------------------------------------------  
CREATE TABLE REF_ELEMENTFORMATS
(
    EF_ELEMENTFORMAT_ID_PK NUMBER(3,0) 			NOT NULL,
    EF_ELEMENTFORMAT_DESC  VARCHAR2(15 BYTE) 	NOT NULL,
    CONSTRAINT REF_ELEMENTFORMATS_PK PRIMARY KEY (EF_ELEMENTFORMAT_ID_PK)
);

--------------------------------------------------------
--  REF_OPERATORS
--------------------------------------------------------
CREATE TABLE REF_OPERATORS
  (
    OP_OPERATOR_CODE_PK VARCHAR2(2 BYTE) 	NOT NULL,
    OP_OPERATOR_DESC    VARCHAR2(25 BYTE) 	NOT NULL,
    CONSTRAINT REF_OPERATORS_PK PRIMARY KEY (OP_OPERATOR_CODE_PK)
);

--------------------------------------------------------
--  REF_ACTIONS
--------------------------------------------------------
CREATE TABLE REF_ACTIONS
(
    AC_ACTION_ID_PK    NUMBER 				NOT NULL,
    AC_ACTION_DESC     VARCHAR2(25 BYTE) 	NOT NULL,
    AC_ACTION_CATEGORY NUMBER(1,0),
    CONSTRAINT REF_ACTIONS_PK PRIMARY KEY (AC_ACTION_ID_PK)
);

--------------------------------------------------------
--  REF_FUNCTIONS
--------------------------------------------------------
CREATE TABLE REF_FUNCTIONS
(
    FU_FUNCTION_CODE VARCHAR2(8 BYTE) 	NOT NULL,
    FU_FUNCTION_DESC VARCHAR2(60 BYTE) 	NOT NULL,
    CONSTRAINT REF_FUNCTIONS_PK PRIMARY KEY (FU_FUNCTION_CODE)
);

--------------------------------------------------------
--  REF_LANGUAGECODES
--------------------------------------------------------
CREATE TABLE REF_LANGUAGECODES
(
    LC_LANGUAGECODE_PK   VARCHAR2(2 BYTE) 	NOT NULL,
    LC_LANGUAGECODE_DESC VARCHAR2(25 BYTE) 	NOT NULL,
    CONSTRAINT REF_LANGUAGECODES_PK PRIMARY KEY (LC_LANGUAGECODE_PK)
);
 
--------------------------------------------------------
--  REF_PRIORITIES
--------------------------------------------------------
CREATE TABLE REF_PRIORITIES
(
    PR_PRIORITY_ID_PK NUMBER(1,0) 			NOT NULL,
    PR_PRIORITY_DESC  VARCHAR2(25 BYTE) 	NOT NULL,
    CONSTRAINT REF_PRIORITIES_PK PRIMARY KEY (PR_PRIORITY_ID_PK)
);

--------------------------------------------------------
--  REF_USERFIELDS
--------------------------------------------------------
CREATE TABLE REF_USERFIELDS
(
    UF_USERFIELD_ID_PK NUMBER(4,0) 			NOT NULL,
    UF_USERFIELD_DESC  VARCHAR2(60 BYTE) 	NOT NULL,
    CONSTRAINT REF_USERFIELDS_PK PRIMARY KEY (UF_USERFIELD_ID_PK)
);

--------------------------------------------------------
--  REF_ENTITIES
--------------------------------------------------------
CREATE TABLE REF_ENTITIES
(
    EN_ENTITY_ID_PK NUMBER(10,0)		NOT NULL,
    EN_ENTITY_DESC  VARCHAR2(30 BYTE)	NOT NULL,
    CONSTRAINT REF_ENTITIES_PK PRIMARY KEY (EN_ENTITY_ID_PK)
);


--------------------------------------------------------
--  FUNCTIONMAX_ID
--------------------------------------------------------
CREATE TABLE FUNCTIONMAX_ID
(
    FUNCTION_MAX_ID NUMBER(4,0) NOT NULL
);
  
--------------------------------------------------------
--  GLOBALMAX_ID
--------------------------------------------------------
CREATE TABLE GLOBALMAX_ID
(
    GLOBAL_MAX_ID NUMBER(4,0) NOT NULL
);

--------------------------------------------------------
--  CRITERIAMAXID
--------------------------------------------------------
CREATE TABLE CRITERIAMAXID 
(	
  CRITERIA_MAX_ID NUMBER(4,0) NOT NULL
);

