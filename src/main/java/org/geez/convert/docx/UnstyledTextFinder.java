package org.geez.convert.docx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.docx4j.XmlUtils;
import org.docx4j.TraversalUtil.CallbackImpl;
import org.docx4j.wml.P;
import org.docx4j.wml.PPr;
import org.docx4j.wml.ParaRPr;
import org.docx4j.wml.R;
import org.docx4j.wml.RFonts;
import org.docx4j.wml.RPr;
import org.docx4j.wml.Text;


public class  UnstyledTextFinder extends CallbackImpl {
    
    public Map<Text,String> results = new HashMap<Text,String>();
    public List<Text> resultsOrdered = new ArrayList<Text>();
    private String fontOut = null;
    List<String> targetTypefaces= null;
    
    public UnstyledTextFinder( List<String> targetTypefaces, String fontOut ) {
    	super();
    	this.targetTypefaces = targetTypefaces;
    	this.fontOut = fontOut;
    }
    
    
	public String checkTargetFont( RFonts rfonts ) {
		
		if( rfonts == null ) {
			return null;
		}
	
		// We assume one of these fields will be set, and not more then one legacy typeface is set per RFonts element.
		boolean isSet = false;
		String fontIn = null;
		if( targetTypefaces.contains( rfonts.getAscii() ) ) {
			fontIn = rfonts.getAscii();
			rfonts.setAscii( fontOut );
			isSet = true;
		}
		if( targetTypefaces.contains( rfonts.getHAnsi() ) ) {
			if(! isSet ) {
				fontIn = rfonts.getHAnsi();
				isSet = true;
			}
			rfonts.setHAnsi( fontOut );
		}
		if( targetTypefaces.contains( rfonts.getCs() ) ) {
			if(! isSet ) {
				fontIn = rfonts.getCs();
				isSet = true;
			}
			rfonts.setCs( fontOut );
		}
		if( targetTypefaces.contains( rfonts.getEastAsia() ) ) {
			if(! isSet ) {
				fontIn = rfonts.getEastAsia();
				isSet = true;
			}
			rfonts.setEastAsia( fontOut );
		}

		return fontIn ;
	}
    
    /* update this class like StyledTextFinder where the constructor receives a list
     * of target fonts, and retrieves any w:text nodes for runs that apply the font
     */
    @Override
    public List<Object> apply(Object o) {
		
    	if (o instanceof org.docx4j.wml.R) {
    		R r = (org.docx4j.wml.R)o;
			RPr rpr = r.getRPr();
			if ( (rpr == null) || (rpr.getRFonts() == null) ) return null;

			String fontIn = checkTargetFont( rpr.getRFonts() );
			if ( fontIn == null ) {
				return null;
			}
			List<Object> rObjects = r.getContent();
			for(Object robj: rObjects) {
				Object tobj = XmlUtils.unwrap(robj);
				if ( tobj instanceof org.docx4j.wml.Text ) {
					results.put( (org.docx4j.wml.Text)tobj, fontIn );
					resultsOrdered.add( (org.docx4j.wml.Text)tobj );
				}
			}
		}
		else if  (o instanceof org.docx4j.wml.P) {
			P p = (org.docx4j.wml.P)o;
			PPr ppr = p.getPPr();
			if (ppr == null ) return null;
			ParaRPr rpr = ppr.getRPr();
			if ( (rpr == null) || (rpr.getRFonts() == null) ) return null;

			String fontIn = checkTargetFont( rpr.getRFonts() );
			if ( fontIn == null ) {
				return null;
			}
			
			List<Object> pObjects = p.getContent();
			for(Object pobj: pObjects) {
				if( pobj instanceof org.docx4j.wml.R ) {
					R r = (org.docx4j.wml.R)pobj;
					List<Object> rObjects = r.getContent();
					for(Object robj: rObjects) {
						Object tobj = XmlUtils.unwrap(robj);
						if ( tobj instanceof org.docx4j.wml.Text ) {
							results.put( (org.docx4j.wml.Text)tobj, fontIn );
							resultsOrdered.add( (org.docx4j.wml.Text)tobj );
						}
					}
				}
				else {
					// w:t node is a direct child of the w:p node:
					Object tobj = XmlUtils.unwrap(pobj);
					if( tobj instanceof org.docx4j.wml.Text ) {
						results.put( (org.docx4j.wml.Text)tobj, fontIn );
						resultsOrdered.add( (org.docx4j.wml.Text)tobj );
					}
				}
			}
		}
    	
        return null;
    }
}  
