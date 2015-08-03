package data;

import extra.Converter;

public class RecordHeader {

	public boolean notEmpty;
	
	public static int BOOL_SIZE = 1;
	
	public static int getSize(){
		return BOOL_SIZE;
	}
	
	public RecordHeader(){
		notEmpty=true;
	}
	
	public RecordHeader( byte data[],int position ){
		notEmpty= Converter.byteToBool(data, position);
		position+=BOOL_SIZE;
	}
	
	public byte[] headerInDbFormat(){
		
		byte res[]=new byte[BOOL_SIZE];
		byte tmp[];
		
		int pos=0;
		tmp = Converter.boolToByte(notEmpty);
		for( int i=0;i<tmp.length;i++ )
			res[pos++]=tmp[i];
		
		return res;
	}
	
}
