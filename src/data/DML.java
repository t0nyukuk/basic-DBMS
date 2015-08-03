package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class DML {
	
	public final int PAGE_SIZE = 4096;
	
	syscat.Record type;
	URL url;
	Scanner in;
	
	public DML( syscat.Record type,Scanner in ){
		this.in = in;
		this.type=type;
		this.url = getClass().getResource("/data/"+type+".dat");
	}
	
	public boolean addRecord(){
		
		System.out.println("Create Record");
		
		int numberOfFields = type.header.numberOfFields;
		
		int fields[] = new int[ numberOfFields ];
		
		for(int i=0;i<numberOfFields;i++)
		{
			System.out.print( type.fieldNames[i]+": " );
			fields[i]=in.nextInt();
		}
		
		URL url = getClass().getResource("/files/"+type.typeName+".dat");
		
		int position=0;
		
		Page page;
		
		RandomAccessFile rac=null;
		
		try {
			rac = new RandomAccessFile(new File(url.getPath()), "rw");
			while(true){
				
				try{
					page = new Page(rac,position,numberOfFields);
				}catch(Exception e){
					page = new Page(numberOfFields);
					page.writePage(rac,position);
				}
				
				if( page.isEmpty() ){
					page=new Page(numberOfFields);
					page.writePage(rac,position);
					//rac.close();
				}
				if( !page.isFull() ){
					Record rec = new Record(fields, numberOfFields);
					page.insertRecord(rec);
					page.writePage(rac,position);
					rac.close();
					
					return true;
				}
				position += PAGE_SIZE;
			}
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return false;
		
		
	}
	
	public boolean deleteRecord(){
		
		System.out.println("Delete a Record");
		
		System.out.print("Please write key of record to delete : ");
		
		int key;
		
		key = in.nextInt();
		
		URL url = getClass().getResource("/files/"+type.typeName+".dat");
		
		int position=0;
		
		Page page;
		
		RandomAccessFile rac=null;
		
		try {
			rac = new RandomAccessFile(new File(url.getPath()), "rw");
			
			while(true){
				
				try{
				
					page = new Page(rac,position,type.header.numberOfFields);
				
				}catch(Exception e){
					
					break;
				
				}
				
				if( page.deleteRecord(key) ){
					
					page.writePage(rac, position);
					
					rac.close();
					
					
					return true;
					
				}
				position+=PAGE_SIZE;
				
			}
			rac.close();
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return false;
	}
	
	public Record retrieveRecord(){
		
		System.out.println("Retrieve a Record");
		
		System.out.print("Please write key of record to retrieve : ");
		
		int key;
		
		key = in.nextInt();
		
		URL url = getClass().getResource("/files/"+type.typeName+".dat");
		
		int position=0;
		
		Page page;
		
		RandomAccessFile rac=null;
		
		try {
			rac = new RandomAccessFile(new File(url.getPath()), "rw");
			
			while(true){
				
				try{
				
					page = new Page(rac,position,type.header.numberOfFields);
				
				}catch(Exception e){
					
					break;
				
				}
				
				Record rec = page.findRecord(key);
				
				if( rec!=null ){
					
					rac.close();

					return rec;
					
				}
				position+=PAGE_SIZE;
				
			}
			rac.close();
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public ArrayList<Record> retrieveAllRecords(){
		
		System.out.println("Retrieve All Records");
		
		URL url = getClass().getResource("/files/"+type.typeName+".dat");
		
		int position=0;
		
		Page page=null;
		
		RandomAccessFile rac=null;
		
		ArrayList<Record> result = new ArrayList<Record>();
		
		try {
			rac = new RandomAccessFile(new File(url.getPath()), "rw");
			while(true){
				
				try{
					page = new Page(rac,position,type.header.numberOfFields);
				}catch(Exception e){
					//e.printStackTrace();
					break;
				}
				
				if( !page.isEmpty() ){
					
					result.addAll( page.displayAllRecords() );
					
				}
				position += PAGE_SIZE;
			}
			rac.close();
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	
}
