package fr.urssaf.image.commons.maquette.template.parser.internal;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;
import fr.urssaf.image.commons.maquette.template.parser.exception.MissingHtmlElementInTemplateParserException;
import fr.urssaf.image.commons.maquette.template.parser.exception.MissingSourceParserException;

/**
 * @author CER6990172
 * @desc parse la balise body de toute cha�ne ou fichier de template pour en r�cup�rer les �l�ments de la balise <div id="content-application">
 */
public class LeftColParser extends AbstractParser
{
	private Element leftColTag ;
	
	/**
	 * @desc default constructor
	 */
	public LeftColParser() {
		
	}
	
	/**
	 * @desc ex�cute le doParse dans la foul�e
	 * @param sc
	 * @throws MissingHtmlElementInTemplateParserException 
	 * @throws MissingSourceParserException
	 */
	public LeftColParser( Source sc ) throws MissingHtmlElementInTemplateParserException, MissingSourceParserException {
		doParse(sc) ;
	}
	
	/**
	 * @return the leftColTag
	 */
	public Element getLeftColTag() {
		return leftColTag;
	}

	/**
	 * @desc lance le parsing des �l�ments de la balise body contenu dans l'attribut Source et r�cup�re
	 * 		 la div avec l'id leftcol
	 * @param sc
	 * @throws MissingHtmlElementInTemplateParserException 
	 * @throws MissingSourceParserException 
	 */
	protected void doParse( Source sc ) throws MissingHtmlElementInTemplateParserException, MissingSourceParserException
	{	
		if( sc != null )
		{
			leftColTag = doGetLeftColTag( sc );
		}
		else
			throw new MissingSourceParserException("LeftCol") ;
	}
	
	/**
	 * @desc	retourne la balise div leftcol
	 * @param sc
	 * @throws MissingHtmlElementInTemplateParserException 
	 */
	protected Element doGetLeftColTag( Source sc ) throws MissingHtmlElementInTemplateParserException {	
		return getElementById( sc, "leftcol" ) ;
	}	
}
