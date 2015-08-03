package syscat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class DDL {
	
	public final int PAGE_SIZE = 4096;
	
	public void run(){
		
		Scanner in = new Scanner(System.in);
		
		while(true){
			
			System.out.println("-----------------------------------------------");
			System.out.println("DDL menu:");
			System.out.println("0 : Exit");
			System.out.println("1 : Add a new file type");
			System.out.println("2 : Delete a file type");
			System.out.println("3 : Display all file types");
			System.out.println("-----------------------------------------------");
			int r = in.nextInt();
			
			if(r==0)
				break;
			if(r==1){
				System.out.println(this.createType());
			}
			if(r==2)
			{
				System.out.println(this.deleteType());
			}
			if(r==3)
			{
				System.out.println(this.displayAllTypes());
			}
			
		}
	}
	
	public boolean createType(){
		
		System.out.println("Create Type");
		
		Scanner in = new Scanner(System.in);
		
		System.out.print("Please write type name: ");
		String typeName;
		typeName = in.next();
		
		System.out.print("Please write number of fields : ");
		int numberOfFields;
		numberOfFields = in.nextInt();
		
		String fieldNames[] = new String[numberOfFields];
		for(int i=0;i<numberOfFields;i++)
		{
			System.out.print("write name of field " + i + " : ");
			fieldNames[i] = in.next();
		}
		
		URL url = getClass().getResource("/sys.cat");
		
		createFile( typeName );
		
		byte ar[] = new byte[PAGE_SIZE];
		
		int position=0;
		
		Page page;
		
		RandomAccessFile rac=null;
		
		try {
			rac = new RandomAccessFile(new File(url.getPath()), "rw");
			while(true){
				
				try{
					page = new Page(rac,position);
				}catch(Exception e){
					page = new Page();
					page.writePage(rac,position);
				}
				
				if( page.isEmpty() ){
					page=new Page();
					page.writePage(rac,position);
				}
				if( !page.isFull() ){
					Record rec = new Record(typeName, numberOfFields, fieldNames);
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
	
	public ArrayList<Record> displayAllTypes(){
		
		System.out.println("Display All Types");
		
		URL url = getClass().getResource("/sys.cat");
		
		byte ar[] = new byte[PAGE_SIZE];
		
		int position=0;
		
		Page page=null;
		
		RandomAccessFile rac=null;
		
		ArrayList<Record> result = new ArrayList<Record>();
		
		try {
			rac = new RandomAccessFile(new File(url.getPath()), "rw");
			while(true){
				
				try{
					page = new Page(rac,position);
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
	
	public boolean deleteType(){
		
		System.out.println("Delete a Type");
		
		Scanner in = new Scanner(System.in);
		
		System.out.print("Please write type name to delete : ");
		String typeName;
		typeName = in.next();
		
		URL url = getClass().getResource("/sys.cat");
		
		byte ar[] = new byte[PAGE_SIZE];
		
		int position=0;
		
		Page page;
		
		RandomAccessFile rac=null;
		
		deleteFile(typeName);
		
		try {
			rac = new RandomAccessFile(new File(url.getPath()), "rw");
			
			while(true){
				
				try{
				
					page = new Page(rac,position);
				
				}catch(Exception e){
					
					break;
				
				}
				
				if( page.deleteRecord(typeName) ){
					
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
	
	public Record findType( String typeName ){
		
		URL url = getClass().getResource("/sys.cat");
		
		byte ar[] = new byte[PAGE_SIZE];
		
		int position=0;
		
		Page page;
		
		RandomAccessFile rac=null;
		
		try {
			rac = new RandomAccessFile(new File(url.getPath()), "rw");
			
			while(true){
				
				try{
				
					page = new Page(rac,position);
				
				}catch(Exception e){
					
					break;
				
				}
				
				Record rec = page.findRecord(typeName);
				
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
	
	private boolean createFile( String str ){
		
		URL url=null;
		try {
			url = new URL(getClass().getResource("/files") + "/" + str + ".dat" );
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(url==null)
			return false;
		
		File file = new File(url.getPath());
		
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return false;
		
				
	}
	
	private boolean deleteFile( String str ){
		
		URL url = getClass().getResource("/files/"+str+".dat");
		
		if(url==null)
			return false;
		
		File file = new File(url.getPath());
		
		file.delete();
		
		return true;
				
	}
	
}
