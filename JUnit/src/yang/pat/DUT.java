package yang.pat;

import java.lang.reflect.Field;
import java.util.List;

public class DUT {
	
	boolean hi;
	
	class Inner {
		
		boolean hi;
		class DoubleInner{
			boolean hi;
			
			public void test() {
				this.hi = false;
				Inner.this.hi = true;
			}
		}
	}
	
	public static void main( String[] args ) {
		
	}
	
//	public void first() {
//	}
//
//	protected void second( boolean test ) {
//	}
//	
//	boolean third( boolean one, boolean two, boolean three ) {
//		return true;
//	}
//	
//	protected void forth( Boolean test ) {
//	}
//	
//	boolean fifth( Boolean one, boolean two, Boolean three ) {
//		return true;
//	}
	
	void objectTest( MyObject test ) {
	}
}
