/* Test Data */

-- REF_COUNTRIES
Insert into IFREND.REF_COUNTRIES (CO_COUNTRY_CODE_PK,CO_COUNTRY_DESC) values ('PH','Philippines');
Insert into IFREND.REF_COUNTRIES (CO_COUNTRY_CODE_PK,CO_COUNTRY_DESC) values ('AU','Australia');
Insert into IFREND.REF_COUNTRIES (CO_COUNTRY_CODE_PK,CO_COUNTRY_DESC) values ('SG','Singapore');
Insert into IFREND.REF_COUNTRIES (CO_COUNTRY_CODE_PK,CO_COUNTRY_DESC) values ('MY','Malaysia');
Insert into IFREND.REF_COUNTRIES (CO_COUNTRY_CODE_PK,CO_COUNTRY_DESC) values ('ID','Indonesia');
Insert into IFREND.REF_COUNTRIES (CO_COUNTRY_CODE_PK,CO_COUNTRY_DESC) values ('TH','Thailand');

-- REF_ELEMENTTYPES
Insert into IFREND.REF_ELEMENTTYPES (ET_ELEMENTTYPE_ID_PK,ET_ELEMENTTYPE_DESC) values (1,'Global');
Insert into IFREND.REF_ELEMENTTYPES (ET_ELEMENTTYPE_ID_PK,ET_ELEMENTTYPE_DESC) values (2,'Function');

-- REF_DATEFORMATS
Insert into IFREND.REF_DATEFORMATS (DF_DATEFORMAT_ID_PK,DF_DATEFORMAT_DESC) values (1,'MM/dd/yyyy');
Insert into IFREND.REF_DATEFORMATS (DF_DATEFORMAT_ID_PK,DF_DATEFORMAT_DESC) values (2,'dd/MM/yyyy');

-- REF_ELEMENTFORMATS
Insert into IFREND.REF_ELEMENTFORMATS (EF_ELEMENTFORMAT_ID_PK,EF_ELEMENTFORMAT_DESC) values (1,'Character');
Insert into IFREND.REF_ELEMENTFORMATS (EF_ELEMENTFORMAT_ID_PK,EF_ELEMENTFORMAT_DESC) values (2,'Alpha-Numeric');
Insert into IFREND.REF_ELEMENTFORMATS (EF_ELEMENTFORMAT_ID_PK,EF_ELEMENTFORMAT_DESC) values (3,'Numeric');
Insert into IFREND.REF_ELEMENTFORMATS (EF_ELEMENTFORMAT_ID_PK,EF_ELEMENTFORMAT_DESC) values (4,'Date');
Insert into IFREND.REF_ELEMENTFORMATS (EF_ELEMENTFORMAT_ID_PK,EF_ELEMENTFORMAT_DESC) values (5,'Amount');

-- REF_OPERATORS
Insert into IFREND.REF_OPERATORS (OP_OPERATOR_CODE_PK,OP_OPERATOR_DESC) values ('EQ','Equals');
Insert into IFREND.REF_OPERATORS (OP_OPERATOR_CODE_PK,OP_OPERATOR_DESC) values ('NE','Not Equal');
Insert into IFREND.REF_OPERATORS (OP_OPERATOR_CODE_PK,OP_OPERATOR_DESC) values ('GT','Greater Than');
Insert into IFREND.REF_OPERATORS (OP_OPERATOR_CODE_PK,OP_OPERATOR_DESC) values ('GE','Greater or Equal To');
Insert into IFREND.REF_OPERATORS (OP_OPERATOR_CODE_PK,OP_OPERATOR_DESC) values ('LT','Less Than');
Insert into IFREND.REF_OPERATORS (OP_OPERATOR_CODE_PK,OP_OPERATOR_DESC) values ('LE','Less or Equal To');

-- REF_ACTIONS
Insert into IFREND.REF_ACTIONS (AC_ACTION_ID_PK,AC_ACTION_DESC,AC_ACTION_CATEGORY) values (3,'Action 3',1);
Insert into IFREND.REF_ACTIONS (AC_ACTION_ID_PK,AC_ACTION_DESC,AC_ACTION_CATEGORY) values (4,'Action 4',0);
Insert into IFREND.REF_ACTIONS (AC_ACTION_ID_PK,AC_ACTION_DESC,AC_ACTION_CATEGORY) values (1,'Action 1',1);
Insert into IFREND.REF_ACTIONS (AC_ACTION_ID_PK,AC_ACTION_DESC,AC_ACTION_CATEGORY) values (2,'Action 2',0);

-- FUNCTIONMAX_ID
Insert into IFREND.FUNCTIONMAX_ID (FUNCTION_MAX_ID) values (1);

-- GLOBALMAX_ID
Insert into IFREND.GLOBALMAX_ID (GLOBAL_MAX_ID) values (1);

-- REF_FUNCTIONS
Insert into IFREND.REF_FUNCTIONS (FU_FUNCTION_CODE,FU_FUNCTION_DESC) values ('LGIN','LOGIN');
Insert into IFREND.REF_FUNCTIONS (FU_FUNCTION_CODE,FU_FUNCTION_DESC) values ('SRCH','SEARCH');
Insert into IFREND.REF_FUNCTIONS (FU_FUNCTION_CODE,FU_FUNCTION_DESC) values ('IDEN','IDENTIFICATION');
Insert into IFREND.REF_FUNCTIONS (FU_FUNCTION_CODE,FU_FUNCTION_DESC) values ('ACCI','ACCOUNT INQUIRY');
Insert into IFREND.REF_FUNCTIONS (FU_FUNCTION_CODE,FU_FUNCTION_DESC) values ('RELI','RELATIONSHIP INQUIRY');
Insert into IFREND.REF_FUNCTIONS (FU_FUNCTION_CODE,FU_FUNCTION_DESC) values ('NOTE','NOTES');
Insert into IFREND.REF_FUNCTIONS (FU_FUNCTION_CODE,FU_FUNCTION_DESC) values ('ADET','ADDRESS DETAILS');
Insert into IFREND.REF_FUNCTIONS (FU_FUNCTION_CODE,FU_FUNCTION_DESC) values ('CDET','CONTACT DETAILS');
Insert into IFREND.REF_FUNCTIONS (FU_FUNCTION_CODE,FU_FUNCTION_DESC) values ('EMNC','EMBOSSER NAME CHANGE');
Insert into IFREND.REF_FUNCTIONS (FU_FUNCTION_CODE,FU_FUNCTION_DESC) values ('ICLI','INSTANT CREDIT LIMIT INCREASE');
Insert into IFREND.REF_FUNCTIONS (FU_FUNCTION_CODE,FU_FUNCTION_DESC) values ('TCLI','TEMPORARY CREDIT LIMIT INCREASE');
Insert into IFREND.REF_FUNCTIONS (FU_FUNCTION_CODE,FU_FUNCTION_DESC) values ('PCLI','PERMANENT CREDIT LIMIT INCREASE');
Insert into IFREND.REF_FUNCTIONS (FU_FUNCTION_CODE,FU_FUNCTION_DESC) values ('LOSR','LOST/STOLEN REPORTING');
Insert into IFREND.REF_FUNCTIONS (FU_FUNCTION_CODE,FU_FUNCTION_DESC) values ('ECRL','EARLY CARD RENEWAL');
Insert into IFREND.REF_FUNCTIONS (FU_FUNCTION_CODE,FU_FUNCTION_DESC) values ('REPL','REPLACE PLASTIC');
Insert into IFREND.REF_FUNCTIONS (FU_FUNCTION_CODE,FU_FUNCTION_DESC) values ('REIN','REINSTATE CARD');
Insert into IFREND.REF_FUNCTIONS (FU_FUNCTION_CODE,FU_FUNCTION_DESC) values ('MADJ','MANUAL ADJUSTMENTS');
Insert into IFREND.REF_FUNCTIONS (FU_FUNCTION_CODE,FU_FUNCTION_DESC) values ('CACC','CLOSE ACCOUNTS');
Insert into IFREND.REF_FUNCTIONS (FU_FUNCTION_CODE,FU_FUNCTION_DESC) values ('NORC','NON RECEIVED CARD');
Insert into IFREND.REF_FUNCTIONS (FU_FUNCTION_CODE,FU_FUNCTION_DESC) values ('MEMS','MEMO SUMMARY');
Insert into IFREND.REF_FUNCTIONS (FU_FUNCTION_CODE,FU_FUNCTION_DESC) values ('LETR','LETTER REQUEST');
Insert into IFREND.REF_FUNCTIONS (FU_FUNCTION_CODE,FU_FUNCTION_DESC) values ('AUTH','AUTHORIZATIONS');
Insert into IFREND.REF_FUNCTIONS (FU_FUNCTION_CODE,FU_FUNCTION_DESC) values ('DIDB','DIRECT DEBIT');
Insert into IFREND.REF_FUNCTIONS (FU_FUNCTION_CODE,FU_FUNCTION_DESC) values ('SDET','STATEMENT DETAILS');
Insert into IFREND.REF_FUNCTIONS (FU_FUNCTION_CODE,FU_FUNCTION_DESC) values ('MAIN','MAINTENANCE INQUIRY');
Insert into IFREND.REF_FUNCTIONS (FU_FUNCTION_CODE,FU_FUNCTION_DESC) values ('PAYD','PAYMENT DETAILS');
Insert into IFREND.REF_FUNCTIONS (FU_FUNCTION_CODE,FU_FUNCTION_DESC) values ('UBTR','UNBILLED TRANSACTION DETAILS');
Insert into IFREND.REF_FUNCTIONS (FU_FUNCTION_CODE,FU_FUNCTION_DESC) values ('ACMA','ACCOUNT MAINTENANCE');
Insert into IFREND.REF_FUNCTIONS (FU_FUNCTION_CODE,FU_FUNCTION_DESC) values ('CACT','CARD ACTIVATION');
Insert into IFREND.REF_FUNCTIONS (FU_FUNCTION_CODE,FU_FUNCTION_DESC) values ('CHMA','CARD HOLDER MAINTENANCE');
Insert into IFREND.REF_FUNCTIONS (FU_FUNCTION_CODE,FU_FUNCTION_DESC) values ('CUMA','CUSTOMER MAINTENANCE');
Insert into IFREND.REF_FUNCTIONS (FU_FUNCTION_CODE,FU_FUNCTION_DESC) values ('PIMA','PIN MAINTENANCE');
Insert into IFREND.REF_FUNCTIONS (FU_FUNCTION_CODE,FU_FUNCTION_DESC) values ('PAYF','PAYOUT FIGURE');
Insert into IFREND.REF_FUNCTIONS (FU_FUNCTION_CODE,FU_FUNCTION_DESC) values ('ASUP','ADDON SUPPLEMENTARY');
Insert into IFREND.REF_FUNCTIONS (FU_FUNCTION_CODE,FU_FUNCTION_DESC) values ('ENDC','END CALL');
Insert into IFREND.REF_FUNCTIONS (FU_FUNCTION_CODE,FU_FUNCTION_DESC) values ('SAVC','SAVE CALL');
Insert into IFREND.REF_FUNCTIONS (FU_FUNCTION_CODE,FU_FUNCTION_DESC) values ('CALS','CALL LOG SUMMARY');
Insert into IFREND.REF_FUNCTIONS (FU_FUNCTION_CODE,FU_FUNCTION_DESC) values ('CALR','CALL LOG REPORTS');
Insert into IFREND.REF_FUNCTIONS (FU_FUNCTION_CODE,FU_FUNCTION_DESC) values ('RETC','RETRIEVE CALL');
Insert into IFREND.REF_FUNCTIONS (FU_FUNCTION_CODE,FU_FUNCTION_DESC) values ('RERE','REWARDS & REDEMPTIONS');
Insert into IFREND.REF_FUNCTIONS (FU_FUNCTION_CODE,FU_FUNCTION_DESC) values ('APIN','APPLICATION INQUIRY');

-- REF_LANGUAGECODES
Insert into IFREND.REF_LANGUAGECODES (LC_LANGUAGECODE_PK,LC_LANGUAGECODE_DESC) values ('EN','ENGLISH');
Insert into IFREND.REF_LANGUAGECODES (LC_LANGUAGECODE_PK,LC_LANGUAGECODE_DESC) values ('DE','GERMAN');
Insert into IFREND.REF_LANGUAGECODES (LC_LANGUAGECODE_PK,LC_LANGUAGECODE_DESC) values ('FR','FRENCH');
Insert into IFREND.REF_LANGUAGECODES (LC_LANGUAGECODE_PK,LC_LANGUAGECODE_DESC) values ('JA','JAPANESE');
Insert into IFREND.REF_LANGUAGECODES (LC_LANGUAGECODE_PK,LC_LANGUAGECODE_DESC) values ('KO','KOREAN');
Insert into IFREND.REF_LANGUAGECODES (LC_LANGUAGECODE_PK,LC_LANGUAGECODE_DESC) values ('ZH','CHINESE');
Insert into IFREND.REF_LANGUAGECODES (LC_LANGUAGECODE_PK,LC_LANGUAGECODE_DESC) values ('PH','FILIPINO');

-- TODO Update 
-- REF_PRIORITIES
Insert into IFREND.REF_PRIORITIES (PR_PRIORITY_ID_PK,PR_PRIORITY_DESC) values (1,'Priority 1');
Insert into IFREND.REF_PRIORITIES (PR_PRIORITY_ID_PK,PR_PRIORITY_DESC) values (2,'Priority 2');
Insert into IFREND.REF_PRIORITIES (PR_PRIORITY_ID_PK,PR_PRIORITY_DESC) values (3,'Priority 3');
Insert into IFREND.REF_PRIORITIES (PR_PRIORITY_ID_PK,PR_PRIORITY_DESC) values (4,'Priority 4');
Insert into IFREND.REF_PRIORITIES (PR_PRIORITY_ID_PK,PR_PRIORITY_DESC) values (5,'Priority 5');

-- TODO Update 
-- REF_USERFIELDS 
Insert into IFREND.REF_USERFIELDS (UF_USERFIELD_ID_PK,UF_USERFIELD_DESC) values (1,'User Field 1');
Insert into IFREND.REF_USERFIELDS (UF_USERFIELD_ID_PK,UF_USERFIELD_DESC) values (2,'User Field 2');
Insert into IFREND.REF_USERFIELDS (UF_USERFIELD_ID_PK,UF_USERFIELD_DESC) values (3,'User Field 3');
Insert into IFREND.REF_USERFIELDS (UF_USERFIELD_ID_PK,UF_USERFIELD_DESC) values (4,'User Field 4');
Insert into IFREND.REF_USERFIELDS (UF_USERFIELD_ID_PK,UF_USERFIELD_DESC) values (5,'User Field 5');

-- REF_ENTITIES
Insert into IFREND.REF_ENTITIES (EN_ENTITY_ID_PK,EN_ENTITY_DESC) values (1,'Elements');
Insert into IFREND.REF_ENTITIES (EN_ENTITY_ID_PK,EN_ENTITY_DESC) values (2,'Criteria');
Insert into IFREND.REF_ENTITIES (EN_ENTITY_ID_PK,EN_ENTITY_DESC) values (3,'MLI');
Insert into IFREND.REF_ENTITIES (EN_ENTITY_ID_PK,EN_ENTITY_DESC) values (4,'Scripts');
Insert into IFREND.REF_ENTITIES (EN_ENTITY_ID_PK,EN_ENTITY_DESC) values (5,'Code Table');
Insert into IFREND.REF_ENTITIES (EN_ENTITY_ID_PK,EN_ENTITY_DESC) values (6,'Labels');

Insert into IFREND.MST_CODETYPES (CT_ID_PK,CT_DESC,CT_STATUS_ID,CT_ACTION_ID,CT_CREATOR_USER_ID_FK,
CT_DATE_CREATED,CT_LASTMODIFIER_USER_ID_FK,
CT_DATE_LAST_MODIFIED,CT_APPROVER_USER_ID_FK,CT_DATE_APPROVED) 
values (1,'MLI Response Codes',1,1,'LAURENTES',to_date('08/08/2012','MM/DD/RRRR'),
'LAURENTES',to_date('08/08/2012','MM/DD/RRRR'),'TOBIASK',to_date('08/08/2012','MM/DD/RRRR'));
Insert into IFREND.MST_CODES (CO_ID_PK,CO_CODETYPE_ID_FK,CO_VALUE,CO_DESC) values (1,1,'708','CARD NUMBER NOT FOUND ');
Insert into IFREND.MST_CODES (CO_ID_PK,CO_CODETYPE_ID_FK,CO_VALUE,CO_DESC) values (2,1,'709','ACCT NUMBER NOT FOUND ');
Insert into IFREND.MST_CODES (CO_ID_PK,CO_CODETYPE_ID_FK,CO_VALUE,CO_DESC) values (3,1,'901','INVALID CARD NUMBER ');
Insert into IFREND.MST_CODES (CO_ID_PK,CO_CODETYPE_ID_FK,CO_VALUE,CO_DESC) values (4,1,'902','ORGANIZATION ENTRY REQUIRED ');
Insert into IFREND.MST_CODES (CO_ID_PK,CO_CODETYPE_ID_FK,CO_VALUE,CO_DESC) values (5,1,'903','INVALID ORGANIZATION ');
Insert into IFREND.MST_CODES (CO_ID_PK,CO_CODETYPE_ID_FK,CO_VALUE,CO_DESC) values (6,1,'904','NO ORGANIZATION RECORD ON FILE ');
Insert into IFREND.MST_CODES (CO_ID_PK,CO_CODETYPE_ID_FK,CO_VALUE,CO_DESC) values (7,1,'905','ACCOUNT NUMBER REQUIRED          ');
Insert into IFREND.MST_CODES (CO_ID_PK,CO_CODETYPE_ID_FK,CO_VALUE,CO_DESC) values (8,1,'906','ACCOUNT NUMBER NOT FOUND ');
Insert into IFREND.MST_CODES (CO_ID_PK,CO_CODETYPE_ID_FK,CO_VALUE,CO_DESC) values (9,1,'907','AUTHORIZATIONS NOT IN FILE');
Insert into IFREND.MST_CODES (CO_ID_PK,CO_CODETYPE_ID_FK,CO_VALUE,CO_DESC) values (10,1,'908','NO AUTHORIZATION RECORDS ');
Insert into IFREND.MST_CODES (CO_ID_PK,CO_CODETYPE_ID_FK,CO_VALUE,CO_DESC) values (11,1,'1001','CARD NUMBER IS A SPACE OR NON-NUMERIC');

Insert into IFREND.MST_CODETYPES (CT_ID_PK,CT_DESC,CT_STATUS_ID,CT_ACTION_ID,CT_CREATOR_USER_ID_FK,
CT_DATE_CREATED,CT_LASTMODIFIER_USER_ID_FK,
CT_DATE_LAST_MODIFIED,CT_APPROVER_USER_ID_FK,CT_DATE_APPROVED) 
values (2,'Letter Codes',1,1,'LAURENTES',to_date('08/08/2012','MM/DD/RRRR'),
'LAURENTES',to_date('08/08/2012','MM/DD/RRRR'),'TOBIASK',to_date('08/08/2012','MM/DD/RRRR'));
Insert into IFREND.MST_CODES (CO_ID_PK,CO_CODETYPE_ID_FK,CO_VALUE,CO_DESC) values (12,2,'LC1','Letter Code 1');
Insert into IFREND.MST_CODES (CO_ID_PK,CO_CODETYPE_ID_FK,CO_VALUE,CO_DESC) values (13,2,'LC2','Letter Code 2');
Insert into IFREND.MST_CODES (CO_ID_PK,CO_CODETYPE_ID_FK,CO_VALUE,CO_DESC) values (14,2,'LC3','Letter Code 3');

-- TODO Remove 
-- REF_LETTERCODES
Insert into IFREND.REF_LETTERCODES (LC_LETTERCODE_ID_PK,LC_LETTERCODE_DESC) values (1,'Letter Code 1');
Insert into IFREND.REF_LETTERCODES (LC_LETTERCODE_ID_PK,LC_LETTERCODE_DESC) values (2,'Letter Code 2');

-- TODO Remove 
-- REF_MLI_RESPONSECODES
Insert into IFREND.REF_MLI_RESPONSECODES (ML_RESPONSECODE_ID_PK,ML_RESPONSECODE_DESC) values (708,'CARD NUMBER NOT FOUND ');
Insert into IFREND.REF_MLI_RESPONSECODES (ML_RESPONSECODE_ID_PK,ML_RESPONSECODE_DESC) values (709,'ACCT NUMBER NOT FOUND ');
Insert into IFREND.REF_MLI_RESPONSECODES (ML_RESPONSECODE_ID_PK,ML_RESPONSECODE_DESC) values (901,'INVALID CARD NUMBER ');
Insert into IFREND.REF_MLI_RESPONSECODES (ML_RESPONSECODE_ID_PK,ML_RESPONSECODE_DESC) values (902,'ORGANIZATION ENTRY REQUIRED ');
Insert into IFREND.REF_MLI_RESPONSECODES (ML_RESPONSECODE_ID_PK,ML_RESPONSECODE_DESC) values (903,'INVALID ORGANIZATION ');
Insert into IFREND.REF_MLI_RESPONSECODES (ML_RESPONSECODE_ID_PK,ML_RESPONSECODE_DESC) values (904,'NO ORGANIZATION RECORD ON FILE ');
Insert into IFREND.REF_MLI_RESPONSECODES (ML_RESPONSECODE_ID_PK,ML_RESPONSECODE_DESC) values (905,'ACCOUNT NUMBER REQUIRED          ');
Insert into IFREND.REF_MLI_RESPONSECODES (ML_RESPONSECODE_ID_PK,ML_RESPONSECODE_DESC) values (906,'ACCOUNT NUMBER NOT FOUND ');
Insert into IFREND.REF_MLI_RESPONSECODES (ML_RESPONSECODE_ID_PK,ML_RESPONSECODE_DESC) values (907,'AUTHORIZATIONS NOT IN FILE');
Insert into IFREND.REF_MLI_RESPONSECODES (ML_RESPONSECODE_ID_PK,ML_RESPONSECODE_DESC) values (908,'NO AUTHORIZATION RECORDS ');
Insert into IFREND.REF_MLI_RESPONSECODES (ML_RESPONSECODE_ID_PK,ML_RESPONSECODE_DESC) values (1001,'CARD NUMBER IS A SPACE OR NON-NUMERIC');
