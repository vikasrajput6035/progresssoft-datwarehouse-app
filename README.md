# progresssoft-datwarehouse-app

# Maven project Deployment steps:

Step 1 > Import project as Maven Project
Step 2 > Configure the Build Path of project -> Choose JDK 1.8 and Tomcat 8 Libraries
Step 3 > Right click on project -> Properties -> Deployment assembly -> Choose Add -> Select Build Entries -> Choose Maven Dependecies


# Prequisites for Project and Junit test cases:

1. Please go to the below google drive location:

https://drive.google.com/drive/folders/1K6maXR44X966q5B8tzoCFXiz5v5Ltd6r?usp=sharing


2.  Sample100kRecords.csv  -  Sample CSV File with 100K records
3.  For this project you'll need four tables. Out of four table you will need one table with its data inorder to run all Junit Test Cases otherwise test cases will fail.
	
	filereporttable.sql  (Sample data query is included)
	fxaccumulativecounttable.sql
	fxdealstable.sql
	fxinvaliddealstable.sql

4. 	sample folder is included for junit test cases. 
	Download this folder in your local machine and  inside TestUtils.java file change sampleFileFolders variable and give your folder location.



# Download the sample CSV file from the below url:

https://drive.google.com/file/d/1zXaoW_85duzhnzvDl3gRpqhxIU-SZ_yJ/view



# About Project:

Project UI is divided into three tabs.

# Tab 1 (Import Forex Deals) :- 

In this screen file browse option is given through one can upload .csv file. Following validations are given:

1. File format other than csv (Comma seperated values) is not allowed. It contains the following fields (Deal Unique Id, From Currency ISO Code "Ordering Currency", To Currency ISO Code, Deal timestamp, Deal Amount in ordering currency).
2. Validations are given on all the fields of .CSV files.
3. Duplicate files are not allowed.

User can see the validation message in red if any of the above validation fails. 
User can see the success message in green if file is uploaded successfully.

# Tab 2 (Summary Report) :-

In this screen user can see the summary report of every files uploaded by him. It shows following fields:

1. File name
2. No. Of Invalid deals
3. No. of Valid deals
4. Process Start timestamp
5. Process End timestamp
6. Process Duration in milliseconds

Also, option is given from which user can type in file name to see its report.

# Tab 3 (Accumulative report of Deals per currency) :-

Screen where user can see accumulative reports of all the deals against currency. It shows following fields:

1. Currency ISO Code
2. No. of Deals Imported of that currency


# Technical Specification:

1. Access to DB is done through JPA
2. For DB type, My SQL is choosen
3. Web interface provided for uploading files and inquire about results "using filename" by following web applications 3 tier architecture
4. In Tab-2 (Summary report screen)  system displays a summary report for each file. Summary contains fields process duration, number of imported deals, number of invalid records and file name
5. Backend is written in Spring MVC + JPA Integration
6. Frontend written in HTML, JQuery and Bootstrap
7. For Unittesting Junit and Mokito is used

# Deliverable includes :

1. Workable deployment including sample file
2. Deployment steps included with sample data of 100K records, contains all invalid/valid records that the system handles
3. Maven project for full source code
4. Provided Proper error/exception handling by using Special Controller for handling exception inorder to show user friendly error messages to end-user
5. Provided Proper Logging by using slf4j and logback
6. Follwed TDD and included unit testing of complete project by covering more than 70% of code
7. Github Repository
