package org.geez.convert.docx;

/*
 * The non-maven way to build the jar file:
 *
 * javac -Xlint:deprecation -cp docx4j-6.0.1.jar:dependencies/commons-io-2.5.jar:../icu4j-63_1.jar:dependencies/slf4j-api-1.7.25.jar:slf4j-1.7.25 *.java
 * jar -cvf convert.jar org/geez/convert/docx/*.class org/geez/convert/tables/
 * java -cp convert.jar:docx4j-6.0.1.jar:dependencies/*:../icu4j-63_1.jar:slf4j-1.7.25/slf4j-nop-1.7.25.jar org.geez.convert.docx.ConvertDocx geeznewab myFile-In.docx myFile-Out.docx
 *
 */

import java.util.Arrays;
import java.util.regex.Pattern;


public class ConvertDocxVisualGeez extends ConvertDocxDiacriticalSystem {

	public ConvertDocxVisualGeez() {
		
		this.initialize( "VisualGeez.txt", "VisualGeezNumbers.txt", "VG2 Main", "VG Geez Numbers" );
		
		huletNeteb = '\u003a';
		
		font1Typefaces.add( "VG2 Main" );
		font1Typefaces.add( "VG2 Agazian" );
		font1Typefaces.add( "VG2 Title" );
		font1Typefaces.add( "VG2 Main regular" );
		font1Typefaces.add( "VG2 Agazian regular" );
		font1Typefaces.add( "VG2 Title regular" );
		
		targetTypefaces.add( "VG2 Agazian" );
		targetTypefaces.add( "VG2 Title" );
		targetTypefaces.add( "VG2 Main regular" );
		targetTypefaces.add( "VG2 Agazian regular" );
		targetTypefaces.add( "VG2 Title regular" );
		
		for(String key: font1Typefaces) {
			fontToTransliteratorMap.put( key, translit1 );			
		}
		
		fontToTransliteratorMap.put( "VG Geez Numbers" , translit2 );


		diacritics.addAll (
				Arrays.asList( "\u0021", "\u0023", "\u0024", "\u0026", "\u002a", "\u0040", "\u0045", "\u00a4", "\u00ba", "\u00d6" )
		);
				
		
		StringBuilder sb = new StringBuilder();
		for (String s : diacritics) {
			sb.append(s);
		}
		
		diacriticsRE = Pattern.compile(
				"([" + sb + "])([" + sb + "])"
		);
		
	}

}
