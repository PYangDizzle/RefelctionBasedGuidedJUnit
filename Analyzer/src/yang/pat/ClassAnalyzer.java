package yang.pat;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

public class ClassAnalyzer {
	
	public static void main( String[] args ) throws Exception {
//		Constructor<?>[] constructors = Boolean.class.getDeclaredConstructors();
//		
//		Class<?>[] paramTypes = constructors[0].getParameterTypes();
//		
//		Object[] params = new Object[ paramTypes.length ];
//		
//		for( int i = 0; i < paramTypes.length; i++ ) {
//			Class<?> paramType = paramTypes[ i ];
//			Object param = instantiateClass( paramType );
//			params[ i ] = param;
//		}
//		
//		Boolean test = (Boolean) constructors[0].newInstance( params );
//		System.out.println( test );
		
		DUT outer = new DUT();
		DUT.Inner inner = outer.new Inner();
		DUT.Inner.DoubleInner dInner = inner.new DoubleInner();
		List<Field> fields = ClassAnalyzer.getNonPrivateFields( DUT.Inner.DoubleInner.class );
		System.out.println( fields );
		
	}
	
	static List<Method> getNonPrivateMethods( Class<?> type ) {
		Method[] methods = type.getDeclaredMethods();
		Predicate<Method> isNotPrivate = new Predicate<Method>() {
			@Override
			public boolean test(Method t) {
				return !Modifier.isPrivate( t.getModifiers() ) && !t.getName().equals( "main" );
			}
		};
		
		return filterWithPredicate( methods, isNotPrivate );
	}
	
	static List<Field> getNonPrivateFields( Class<?> type ) {
		Field[] fields = type.getDeclaredFields();
		Predicate<Field> isNotPrivate = new Predicate<Field>() {
			@Override
			public boolean test(Field t) {
				return !Modifier.isPrivate( t.getModifiers() ); // && !t.getName().contains( "this" );
			}
		};
		
		return filterWithPredicate( fields, isNotPrivate );
	}
	
	static <T> T instantiateClass( Class<T> type ) throws Exception {
		if( type.isPrimitive() ) {
			return instantiatePrimitiveClass( type );
		}
		
		Constructor<?>[] constructor = type.getDeclaredConstructors();
		
		Class<?>[] paramTypes = constructor[0].getParameterTypes();
		
		Object[] params = new Object[ paramTypes.length ];
		
		for( int i = 0; i < paramTypes.length; i++ ) {
			Class<?> paramType = paramTypes[ i ];
			Object param = instantiateClass( paramType );
			params[ i ] = param;
		}
		
		return (T) constructor[0].newInstance( params );
	}
	
	private static <T> T instantiatePrimitiveClass( Class<T> type ) throws Exception {
		if( !type.isPrimitive() ) {
			return null;
		}
		
//		if( boolean.class.isAssignableFrom( type ) ) {
			return (T) new Boolean( false );
//		}
	}
	
	
	private static <T> List<T> filterWithPredicate( T[] objs, Predicate<T> pred ) {
		List<T> result = new LinkedList<T>();
		for( T obj : objs ) {
			if( pred.test( obj ) ) {
				result.add( obj );
			}
		}
		return result;
	}
}
