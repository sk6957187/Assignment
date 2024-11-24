i) 	    show data
ii)	    Upload data
iii) 	if required then update the data
iv)	    delete the data

CREATE TABLE daily_report(
  SNO            NUMBER(5),
  START_DATE     VARCHAR2(15),
  USERID         VARCHAR2(50),
  SUB            VARCHAR2(50),
  TOPIC          VARCHAR2(50),
  TOPIC_DETAILS  CLOB,
  COMPLETED      CHAR(3),
  ADDED_DATE     DATE DEFAULT SYSDATE,
  UPDATE_TIME    TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);