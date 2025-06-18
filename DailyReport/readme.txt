i) 	    Display data
ii)	    Upload data
iii) 	if required then update the data
iv)	    delete the data

Summary:-
The code implements a "DailyReportApplication " using Dropwizard for the backend and React for the frontend. The backend
 consists of multiple components: the `DailyReportApplication` class, which initializes the Dropwizard application,
 configures necessary bundles, and registers filters and REST resources; the `DailyReportConfiguration` class, which manages configuration settings for Oracle SQL and the UI; and the `DailyReportController`, which handles CRUD operations through various API endpoints. The `DailyReportService` layer contains the business logic and interacts with the `TableDataRepository` to manage database operations, including fetching, adding, updating, and deleting records. On the frontend, React components like `Home`, `DailyReport`, and `AddForm` are used to display data in a table, manage form inputs, and handle user interactions. React Router manages navigation between pages for different operations. The system facilitates the management of daily report data, with clear separation between the backend and frontend, allowing for easy updates and maintenance. However, improvements could be made in error handling, user feedback, and scalability.


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
  deleted         Varchar(3) Default "NO"
);


