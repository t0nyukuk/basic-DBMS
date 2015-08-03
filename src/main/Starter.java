package main;

import java.util.ArrayList;
import java.util.Scanner;

import syscat.DDL;
import data.DML;

public class Starter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Starter st = new Starter();
		st.run();
		
	}
	
	public void run( ){
		
		Scanner in = new Scanner(System.in);
		
		while(true){
			System.out.println("===============================================");
			System.out.println("Main Menu");
			System.out.println("0 : Exit");
			System.out.println("1 : DDL Operations");
			System.out.println("2 : DML Operations");
			System.out.println("===============================================");
			int tmp = in.nextInt();
			if(tmp==0)
				break;
			if(tmp==1)
				runDDL();
			if(tmp==2)
				runDML();
		}
	}
	
	public void runDDL(){
		DDL ddl = new DDL();
		ddl.run();
	}
	
	public void runDML(){
		
		Scanner in;
		DML dml;
		DDL ddl = new DDL();
		
		in = new Scanner(System.in);
		
		while(true){
			
			System.out.println("-----------------------------------------------");
			System.out.println("DML Menu");
			System.out.println("0 : exit");
			System.out.println("1 : Add a new record to a file");
			System.out.println("2 : Retrieve a record from a file");
			System.out.println("3 : Delete a record from a file");
			System.out.println("4 : Retrieve all records from a file");
			System.out.println("-----------------------------------------------");
				
			int tmp = new Integer( in.next() ).intValue();
			
			if(tmp==0)
				break;
			System.out.print("Please write file(type) name:");
			String typeName = in.next();
			syscat.Record type = ddl.findType(typeName);
			
			dml = new DML(type,in);
			
			if(tmp==1)
			{
				boolean status = dml.addRecord();
				if(status)
					System.out.println("STATUS OK!");
				else
					System.out.println("STATUS NOT OK!");
			}
			if(tmp==2)
			{
				data.Record rec = dml.retrieveRecord();
				if(rec!=null)
					System.out.println("	" + rec.toString(type));
				else
					System.out.println("STATUS NOT OK!");
			}
			if(tmp==3)
			{
				boolean status = dml.deleteRecord();
				if(status)
					System.out.println("STATUS OK!");
				else
					System.out.println("STATUS NOT OK!");
			}
			if(tmp==4)
			{
				ArrayList<data.Record> res = dml.retrieveAllRecords();
				for( int i=0;i<res.size();i++ )
					System.out.println("	" + res.get(i).toString(type) );
			}
			
		}
		
	}
	
	

}
