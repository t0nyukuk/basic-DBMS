package syscat;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class Page {
	
	public final int PAGE_SIZE = 4096;
	
	PageHeader header;
	byte data[] = new byte[PAGE_SIZE];
	
	public Page(){
		for( int i=0;i<data.length;i++ )
			data[i]=(byte)0;
		header = new PageHeader( Record.getSize() );
	}
	
	public Page( RandomAccessFile rac,int position ) throws IOException{
		
		rac.seek(position);
		rac.readFully(data, 0, PAGE_SIZE);	
		header = new PageHeader( data,0 );
		
	}
	
	public boolean isEmpty(){
		if( header.maxNumberOfRecords==0 )
			return true;
		return false;
	}
	
	public boolean isFull(){
		if( header.maxNumberOfRecords>0 && header.numberOfRecords==header.maxNumberOfRecords )
			return true;
		return false;
	}
	
	public Record findRecord( String typeName ){
		
		int position = PageHeader.getSize();
		
		Record rec;
		
		while( PAGE_SIZE - position >= Record.getSize() ){
			
			rec = new Record(data, position);
			
			if( rec.header.notEmpty && rec.typeName.equals(typeName)){
				return rec;
			}
			
			position+=Record.getSize();
			
		}
		
		return null;
		
	}
	
	public boolean deleteRecord( String typeName ){
		
		int position = PageHeader.getSize();
		
		Record rec;
		
		while( PAGE_SIZE - position >= Record.getSize() ){
			
			rec = new Record(data, position);
			
			if( rec.header.notEmpty && rec.typeName.equals(typeName)){
				
				rec.header.notEmpty=false;
				header.numberOfRecords--;
				rec.writeRecord(data, position);
				return true;
			}
			
			position+=Record.getSize();
			
		}
		
		return false;
		
	}
	
	public void insertRecord( Record record ){
		
		int position = PageHeader.getSize();
		
		Record rec;
		
		while( PAGE_SIZE - position >= Record.getSize() ){
			
			rec = new Record(data, position);
			
			if( rec.header.notEmpty==false ){
				record.writeRecord(data, position);
				header.numberOfRecords++;
				break;
			}
			
			position+=Record.getSize();
			
		}
		
	}
	
	public ArrayList<Record> displayAllRecords(){
		
		ArrayList<Record> res = new ArrayList<Record>();
		
		int position = PageHeader.getSize();
		
		Record rec;
		
		while( PAGE_SIZE - position >= Record.getSize() ){
			
			rec = new Record(data, position);
			
			if( rec.header.notEmpty ){
				res.add( rec );
			}
			
			position+=Record.getSize();
			
		}
		
		return res;
	}
	
	public void writePage( RandomAccessFile rac,int position ){
		
		byte tmp[] = header.headerInDbFormat();
		
		for(int i=0;i<tmp.length;i++)
			data[i]=tmp[i];
		
		try {
			rac.seek(position);
			rac.write(data, 0, PAGE_SIZE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		
	}
	
}
