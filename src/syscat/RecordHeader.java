package syscat;

import java.text.spi.NumberFormatProvider;

import extra.Converter;

public class RecordHeader {
	public int numberOfFields;
	public boolean notEmpty;
	
	public static int INT_SIZE = 4;
	public static int BOOL_SIZE = 1;
	
	public static int getSize(){
		return INT_SIZE+BOOL_SIZE;
	}
	
	public RecordHeader( int numberOfFields ){
		this.numberOfFields = numberOfFields;
		notEmpty=true;
	}
	
	public RecordHeader( byte data[],int position ){
		numberOfFields = Converter.byteToInt(data, position);
		position+=INT_SIZE;
		notEmpty= Converter.byteToBool(data, position);
		position+=BOOL_SIZE;
	}
	
	public byte[] headerInDbFormat(){
		
		byte res[]=new byte[INT_SIZE+BOOL_SIZE];
		
		byte tmp[]=Converter.intToByte(numberOfFields);
		int pos=0;
		for( int i=0;i<tmp.length;i++ )
			res[pos++]=tmp[i];
		tmp = Converter.boolToByte(notEmpty);
		for( int i=0;i<tmp.length;i++ )
			res[pos++]=tmp[i];
		
		return res;
	}
	
}
