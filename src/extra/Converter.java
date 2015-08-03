package extra;

public class Converter {
	
	public static int byteToInt( byte ar[],int pos ){
		int res=0;
		for( int i=0;i<4;i++ )
		{
			res<<=8;
			res+=(int)ar[pos+i];
		}
		return res;
	}
	
	public static byte[] intToByte( int k ){
		int m=1<<8;
		byte res[]=new byte[4];
		for( int i=3;i>=0;i-- ){
			res[i]=(byte)(k%m);
			k>>=8;
		}
		return res;
	}
	
	public static String byteToString( byte ar[],int pos,int len ){
		String res="";
		for( int i=0;i<len;i++ )
			if( ar[pos+i]!=0 )
				res+=(char)ar[pos+i];
		return res;
	}
	
	public static byte[] stringToByte( String str,int len ){
		byte res[]=new byte[len];
		for(int i=0;i<len;i++)
			res[i]=0;
		for(int i=0;i<str.length();i++)
			res[i]=(byte)str.charAt(i);
		return res;
	}
	
	public static boolean byteToBool( byte ar[],int pos ){
		if( ar[pos]==(byte)0 )
			return false;
		return true;
	}
	
	public static byte[] boolToByte( boolean f ){
		byte res[]=new byte[1];
		if(!f)
			res[0]=(byte)0;
		else
			res[0]=(byte)1;
		return res;
	}
	
}
