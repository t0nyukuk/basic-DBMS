package syscat;

import extra.Converter;

public class Record {
	
	public RecordHeader header;
	public String typeName;
	public String fieldNames[];
	
	public Record( byte data[],int position ){
		
		this.fieldNames=new String[10];
		
		for( int i=0;i<10;i++ )
			this.fieldNames[i]="";
		
		header = new RecordHeader(data, position);
		position+=RecordHeader.getSize();
		
		typeName = Converter.byteToString(data, position, 20);
		position+=20;
		
		for( int i=0;i<header.numberOfFields;i++ )
		{
			fieldNames[i] = Converter.byteToString(data, position, 10);
			position+=10;
		}
		
	}
	
	public Record( String typeName,int numberOfFields,String fieldNames[] ){
		
		this.fieldNames=new String[10];
		
		for( int i=0;i<10;i++ )
			this.fieldNames[i]="";
		
		this.header=new RecordHeader(numberOfFields);
		this.typeName=typeName;
		for( int i=0;i<numberOfFields;i++ )
			this.fieldNames[i]=fieldNames[i];
	}
	
	public void writeRecord( byte data[],int position ){
		byte tmp[];
		tmp=header.headerInDbFormat();
		for( int i=0;i<tmp.length;i++ )
			data[position++]=tmp[i];
		tmp=Converter.stringToByte(typeName, 20);
		for( int i=0;i<tmp.length;i++ )
			data[position++]=tmp[i];
		for( int i=0;i<fieldNames.length;i++ )
		{
			tmp=Converter.stringToByte(fieldNames[i], 10);
			for(int j=0;j<tmp.length;j++)
				data[position++]=tmp[j];
		}
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return typeName;
	}
	
	public static int getSize(){
		return RecordHeader.getSize() + 20 + 10*10;
	}
	
}
