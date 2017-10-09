package yang.pat;

import java.util.ArrayList;
import java.util.List;

public class Util {
	static <T> List<List<T>> getCrossProduct( List<List<T>> src, int expectedLength, int expectedWidth ) {
		
		List<List<T>> dest = new ArrayList<List<T>>( expectedLength );
		
		if( src.isEmpty() ) {
			return dest;
		}
		
		for( T value : src.get( 0 ) ) {
			List<T> newRow = new ArrayList<T>( expectedWidth );
			newRow.add( value );
			dest.add( newRow );
		}
		
		for( int i = 1; i < src.size(); i++ ) {
			List<T> list = src.get( i );

			int currSize = dest.size();
			for( int k = 0; k < currSize; k++ ) {
				
				for( int j = 1; j < list.size(); j++ ) {
					
					ArrayList<T> newRow = new ArrayList<T>( expectedWidth ); 
					newRow.addAll( dest.get( k ) );
					newRow.add( list.get( j ) );
					dest.add( newRow );
				}
				dest.get( k ).add( list.get( 0 ) );
			}
		}
		
		return dest;
	}
}
