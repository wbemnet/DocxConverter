package org.geez.convert.docx;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;



public class ConvertDocxSamawerfa extends ConvertDocxDiacriticalSystem {

	public static final Set<String> supportedFonts = new HashSet<String> ( Arrays.asList( "Samawerfa" ) );
	
	{
		IDs = new String[] { "Samawerfa" } ;
	}
	
	public ConvertDocxSamawerfa() {
		super();
		init();
	}
	
	private void init() {
		this.initialize( "monodirectional/Samawerfa.txt", "Addis98" );
		
		huletNeteb = '\u003a';

		translit2 = null;
		
		fontToTransliteratorMap.put( "Blknwt98", translit1 );

		diacritics.addAll (
			Arrays.asList( "\u00fd", "\u00e7", "\u00e6", "\u00e5", "\u00e8", "\u00e9", "\u00ea", "\u00eb", "\u00ec", "\u00ed", "\u00ee", "\u00ef", "\u00f0", "\u00f1", "\u00f2", "\u00f9", "\u00f8", "\u00f7", "\u00f6" ,"\u00f5", "\u00f4", "\u00f3", "\u00fb", "\u00fc", "\u00fa" )
		);
		
		buildRE();
		
	}

}
