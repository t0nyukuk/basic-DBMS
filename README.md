# basic-DBMS
1 Introduction

1.1 Definition

This project is about the implementation of a simple storage manager for a DBMS. In this project this topic is divided into four categories and all of them are prepared separately. These are:

- Introduction
	
- Data structures

- Conclusion and Evaluation
	

1.2 Abstract

The brief summary of the storage manager will be given here. The operations which must be included by the storage manager are listed below.
	
- File Operations
	- Create a file with specified record type
	- Delete a record and its file
	- Display all record types or file types
	
- Record Operations
	- Insert a record to a file
	- Retrieve a record according to its key field.
	- Delete a record according to its key field.
	- Display all the records in a file
	
	The database is representing with one folder and in root folder there are a folder and a file. This file represents System Catalog and it is named as sys.cat. On the other hand folder has named as 'files' contains filename.dat files for all files. The sys.cat file contains general information about files in database. The filename.dat files are includes all necessary information.
	
Assumptions
- The files are stored in binary format.
- All file and field names can be at most 20 characters.
- There can be at most 10 fields in a record.
- All fields of records are integer type.
- The page size is assumed to be 4096 bytes
- There must be only one key and a type cannot be created without a key. 
- First field of each type is also key field.
- Key field cannot be duplicate value. 
- Cannot be created empty type in other words every type must have key field.
- Type names must be unique. 
- We assume that unhandled actions will not be tried.
- For example 
	- disallowed type names or field names.
	- remove record or file which is not available

2 Data Structures

Our DBMS is specialized in two different part:

System catalog : This part stores information about current state of DBMS.

Data : This part stores the information about specified type.

2.1 System Catalog

System catalog file consists of pages and each page has a page header and records. Every record represents a type.

i) Page

The figure of page is given below. First row represents Page Header all other rows represents a record.
- maxNumberOfRecords : It is calculated at the beginning and it represents maximum number of records that can fit into this page. It cannot be changed after page is created unless it is removed.
- numberOfRecords : It stores current number of records storing in this page.

ii) Record

The figure of record is given below. First two column represents record header and all others represents body.
numberOfFields : It stores the number of fields for this record type.
- notEmpty : It stores a boolean and this boolean show that if record is notEmpty(true) or empty(false).
- typeName : I represents name of type.
- fieldNames : They represents name of each field for this record.

2.2 Data

In data files each file consist of pages and each page of them consist of records.

i) Page

The page type is complete same with page structure in system catalog. The figure of page is given below. First row represents Page Header all other rows represents a record.
- maxNumberOfRecords : It is calculated at the beginning and it represents maximum number of records that can fit into this page. It cannot be changed after page is created unless it is removed.
- numberOfRecords : It stores current number of records storing in this page.

ii) Record

The figure of record is given below. First two column represents record header and all others represents body.
- notEmpty : It stores a boolean and this boolean show that if record is notEmpty(true) or empty(false).
- fields : They represents value of each field for this record and all of them are integer variable.

3 Conclusion and Evaluation

To sum up, this project helped the understanding to logic of database management system and disk usage. While preparing project many problems are occurred and while solving this problems logic of database management system has been understood. 

Although the project has done and java codes are implemented, there may be mistakes in logical aspects. In whole project limits and error handlings can cause some problems because they aren’t handled. They should also be fixed.

On the other hand, we create a simple storage manager but it didn’t developed considering maximizing performance. Therefore this issue is also a big topic then it should be examined.

In all operations and structures, I have used simple array data structure it is easy to think and decide. However, array data structure is very stable so it sometimes requires moving data from somewhere to another. These movings can cause time consuming and RAM usage problems. Therefore a more effective data structure can be used for this purpose.

