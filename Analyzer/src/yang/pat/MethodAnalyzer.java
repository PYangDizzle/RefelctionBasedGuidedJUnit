package yang.pat;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static yang.pat.Type.*;

public abstract class MethodAnalyzer {
	
//	public static void main( String[] args ) {
//		List<Method> itrMethod = ClassAnalyzer.getNonPrivateMethods( DUT.class );
//		
//		while( itrMethod.hasNext() ) {
//			Method method = itrMethod.next();
//			String name = method.getName();
//			Collection<Collection<Boolean>> possibleParams = new LinkedList<Collection<Boolean>>();
//			
//			Iterator<Class<?>> itrParamType = getParamTypesIterator( method );
//			while( itrParamType.hasNext() ) {
//				Class<?> paramType = itrParamType.next();
//				Type type = TypeAnalyzer.translate( paramType );
//				Collection<Boolean> values = TypeAnalyzer.getPossibleValues( paramType );
//				possibleParams.add( values );
//			}
//			
//			System.out.println(name);
//		}
//	}
	
	static Iterator<Class<?>> getParamTypesIterator( Method met ) {
		return Arrays.stream( met.getParameterTypes() )
				.iterator();
	}
	
//	static Object[][] getPossibleParameters( Method met ) {
	static List<List<Boolean>> getPossibleParameters( Method met ) throws Exception {
		
		Class<?>[] paramTypes = met.getParameterTypes();
		if( paramTypes.length <= 0 ) {
			return new ArrayList<List<Boolean>>();
		}
		List<List<Boolean>> possibleParamValues = TypeAnalyzer.getPossibleValues( paramTypes );
//		int numPossibleParams = 1;
//		for( Class<?> paramType : paramTypes ) {
//			List<Boolean> values = TypeAnalyzer.getPossibleValues( paramType );
//			possibleParamValues.add( values );
//			numPossibleParams *= values.size();
//		}
		
		int numPossibleParams = 1;
		
		for( List<Boolean> row : possibleParamValues ) {
			numPossibleParams *= row.size();
		}
		
		return Util.getCrossProduct( possibleParamValues, numPossibleParams, paramTypes.length );
		
//		Object[][] possibleParams = new Object[numPossibleParams][ paramTypes.length ];
//		
//		List<List<Boolean>> result = new ArrayList<List<Boolean>>( numPossibleParams );
//		
//		for( Boolean value : possibleParamValues.get( 0 ) ) {
//			ArrayList<Boolean> newRow = new ArrayList<Boolean>( paramTypes.length );
//			newRow.add( value );
//			result.add( newRow );
//		}
//		
//		for( int i = 1; i < possibleParamValues.size(); i++ ) {
//			List<Boolean> list = possibleParamValues.get( i );
//
//			int currSize = result.size();
//			for( int k = 0; k < currSize; k++ ) {
//				
//				for( int j = 1; j < list.size(); j++ ) {
//					
//					ArrayList<Boolean> newRow = new ArrayList<Boolean>( paramTypes.length ); 
//					newRow.addAll( result.get( k ) );
//					newRow.add( list.get( j ) );
//					result.add( newRow );
//				}
//				result.get( k ).add( list.get( 0 ) );
//			}
//		}
//		
//		return result;
	}
}
