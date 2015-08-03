package data;

import extra.Converter;

public class PageHeader {

	public static int INT_SIZE = 4;
	public static int PAGE_SIZE = 4096;
	
	int maxNumberOfRecords;
	int numberOfRecords;
	
	public PageHeader( byte data[],int position ){
		maxNumberOfRecords = Converter.byteToInt(data, position);
		position+=INT_SIZE;
		numberOfRecords = Converter.byteToInt(data, position);
		position+=INT_SIZE;
	}
	
	public PageHeader( int recordSize ){
		maxNumberOfRecords = (PAGE_SIZE-2*INT_SIZE)/recordSize;
		numberOfRecords = 0;
	}
	
	public byte[] headerInDbFormat(){
		
		byte res[]=new byte[INT_SIZE*2];
		
		byte tmp[]=Converter.intToByte(maxNumberOfRecords);
		int pos=0;
		for( int i=0;i<tmp.length;i++ )
			res[pos++]=tmp[i];
		tmp = Converter.intToByte(numberOfRecords);
		for( int i=0;i<tmp.length;i++ )
			res[pos++]=tmp[i];
		
		return res;
	}
	
	public static int getSize(){
		return 2*INT_SIZE;
	}
	
}
