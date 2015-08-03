package data;

import extra.Converter;

public class Record {
	
	public RecordHeader header;
	
	public int fields[];
	
	public static int INT_SIZE = 4;
	
	public Record( byte data[],int position,int numberOfFields ){
		
		this.fields=new int[10];
		
		for( int i=0;i<10;i++ )
			this.fields[i]=0;
		
		header = new RecordHeader(data, position);
		position+=RecordHeader.getSize();
		
		for( int i=0;i<numberOfFields;i++ )
		{
			fields[i] = Converter.byteToInt(data, position);
			position+=INT_SIZE;
		}
		
	}
	
	public Record( int fields[],int numberOfFields ){
		
		this.fields=new int[10];
		
		for( int i=0;i<10;i++ )
			this.fields[i]=0;
		
		this.header=new RecordHeader();
		
		for( int i=0;i<numberOfFields;i++ )
			this.fields[i]=fields[i];
	}
	
	public void writeRecord( byte data[],int position ){
		byte tmp[];
		tmp=header.headerInDbFormat();
		for( int i=0;i<tmp.length;i++ )
			data[position++]=tmp[i];
		for( int i=0;i<fields.length;i++ )
		{
			tmp=Converter.intToByte(fields[i]);
			for(int j=0;j<tmp.length;j++)
				data[position++]=tmp[j];
		}
	}

	public String toString( syscat.Record type ){
		
		String res = "";
		
		for(int i=0;i<type.header.numberOfFields;i++)
		{
			res+=type.fieldNames[i]+":"+ fields[i];
			if( i!=type.header.numberOfFields-1 )
				res += ",  ";
		}
		
		return res;
		
	}
	
	public static int getSize(){
		return RecordHeader.getSize() + 4*10;
	}
	
}
