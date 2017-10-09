package yang.pat;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
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

import static yang.pat.Type.*;

public class TypeAnalyzer {
	
	public static void main( String[] args ) {
//		Type test1 = translate( boolean.class );
//		Type test2 = translate( Boolean.class );
//		Boolean hey = new Boolean(false);
//		Type test3 = translate( hey.getClass() );
//		
//		System.out.println( test1 );
//		System.out.println( test2 );
//		System.out.println( test3 );
		
//		Collection<Boolean> values = TypeAnalyzer.getPossibleValues( Boolean.class );
		
	}
	
//	static Type translate( Class<?> type ) {
//		if( type.isAssignableFrom( Boolean.class ) || type.isAssignableFrom( boolean.class ) ) {
//			return BOOLEAN;
//		}
//		else if( type.isAssignableFrom( Integer.class ) || type.isAssignableFrom( int.class ) ) {
//			return INTEGER;
//		}
//		else if( type.isAssignableFrom( Double.class ) || type.isAssignableFrom( double.class ) ) {
//			return DOUBLE;
//		}
//		else if( type.isAssignableFrom( Character.class ) || type.isAssignableFrom( char.class ) ) {
//			return CHARACTER;
//		}
//		else if( type.isAssignableFrom( Long.class ) || type.isAssignableFrom( long.class ) ) {
//			return LONG;
//		}
//		else if( type.isAssignableFrom( Float.class ) || type.isAssignableFrom( float.class ) ) {
//			return FLOAT;
//		}
//		else if( type.isAssignableFrom( Byte.class ) || type.isAssignableFrom( byte.class ) ) {
//			return BYTE;
//		}
//		else if( type.isAssignableFrom( Short.class ) || type.isAssignableFrom( short.class ) ) {
//			return SHORT;
//		}
//		else {
//			return UNKNOWN;
//		}
//	}
	
	static <T> List<List<T>> getPossibleValues( Class<?>[] paramTypes ) throws Exception {
		List<List<T>> possibleParamValues = new ArrayList<List<T>>( paramTypes.length );
		
		for( Class<?> paramType : paramTypes ) {
			List<T> values = (List<T>) getPossibleValues( paramType );
			possibleParamValues.add( values );
		}
		
		return possibleParamValues;
	}
	
	static <T> List<T> getPossibleValues( Class<T> type ) throws Exception {

		List<T> result = new LinkedList<T>();
		
		if( boolean.class.isAssignableFrom( type ) || Boolean.class.isAssignableFrom( type )) {
			result = new LinkedList<T>( 
					Arrays.asList( (T[])new Boolean[] { false, true } ) );
		}
		else {
			List<Field> fields = ClassAnalyzer.getNonPrivateFields( type );
			Class[] internalTypes = new Class[ fields.size() ];
			int i = 0;
			for( Field field : fields ) {
				internalTypes[ i ] = field.getType();
				
				field.setAccessible(true);
				Field modifiersField = Field.class.getDeclaredField("modifiers");
				modifiersField.setAccessible(true);
				modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

				i++;
			}
			
			List<List<Boolean>> possibleValues = TypeAnalyzer.getPossibleValues( internalTypes );
			
			int numPossibleResults = 1;
			
			for( List<Boolean> row : possibleValues ) {
				numPossibleResults *= row.size();
			}
			
			List<List<Boolean>> product = Util.getCrossProduct( possibleValues, numPossibleResults, internalTypes.length );
			try {
				for( List<Boolean> combination : product ) {
//					type.getDeclaredConstructors();
					T obj = ClassAnalyzer.instantiateClass( type );
					
					int j = 0;
					for( Field field : fields ) {
						field.set( obj, combination.get( j ) );
						j++;
					}
					result.add( obj );
				}
			
				if( product.isEmpty() ) {
					result.add( type.getConstructor().newInstance() );
				}
			} catch ( Exception e ) {
				e.printStackTrace();
			}
		}
		
		if( !type.isPrimitive() ) {
			result.add( null );
		}
		
		return result;
//		switch( type ) {
//		case BOOLEAN :
//			values.add( (T) new Boolean( false ) );
//			values.add( (T) new Boolean( true ) );
			
//		case INTEGER :
//			values = new HashSet<Integer>( 
//					Arrays.asList( new Integer[] { Integer.MIN_VALUE, -1, 0, 1, Integer.MAX_VALUE } )
//					);
//			return;
//		case DOUBLE :
//			values = new HashSet<Double>( 
//					Arrays.asList( new Double[] { -Double.MAX_VALUE, -Double.MIN_VALUE, 0.0, Double.MIN_VALUE, Double.MAX_VALUE } )
//					);
//			return;
//		default :
//			values = new HashSet();
//			return;
//		}
	}
//	static Collection<Byte> getPossibleValues( Class<Byte> type ) {
//		
//	}
	
}
