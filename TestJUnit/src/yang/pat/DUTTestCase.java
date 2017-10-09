package yang.pat;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

public class DUTTestCase {
	@Test
	public void testDUT() throws Exception {
		
//		ClassLoader classloader = this.getClass().getClassLoader();
		
		DUT dut = new DUT();
		
		Class<?> classType = dut.getClass();
		
		List<Method> methods = ClassAnalyzer.getNonPrivateMethods(classType);
		
		for( Method method : methods ) {
//		while( itrMethod.hasNext() ) {
//			Method method = itrMethod.next();
			String name = method.getName();
//			Collection<Collection<Boolean>> possibleParams = new LinkedList<Collection<Boolean>>();
//			
//			Iterator<Class<?>> itrParamType = MethodAnalyzer.getParamTypesIterator( method );
//			while( itrParamType.hasNext() ) {
//				Class<?> paramType = itrParamType.next();
////				Type type = TypeAnalyzer.translate( paramType );
//				Collection<Boolean> values = TypeAnalyzer.getPossibleValues();
//				possibleParams.add( values );
//			}
			
			List<List<Boolean>> paramsList = MethodAnalyzer.getPossibleParameters( method );
			
			System.out.println( paramsList );

			for( List<Boolean> params : paramsList ) {
				Object[] args = params.toArray();
				Boolean result = (Boolean)method.invoke( dut, args );
				if( result != null ) {
					assertTrue( result ); // decision making
				}
			}
			
//			if( name.equals( "third" ) ) {
//				Object[] args = new Boolean[] { false, true, false };
//				Boolean result = (Boolean)method.invoke( dut, args );
//				
//				if( method.getReturnType().isAssignableFrom( boolean.class ) ) {
//					assertTrue( result ); // decision making
//				}
//			}
		}
	}
}
