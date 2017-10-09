package yang.pat;

public class MyObject {
	boolean one;
	Boolean two;
	boolean isDUT = false;
	
	public void test() {
		if( this.isDUT ) {
			System.out.println( "True" );
		}
		else {
			System.out.println( "False" );
		}
	}
}
