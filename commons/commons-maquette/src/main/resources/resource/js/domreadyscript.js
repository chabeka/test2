window.addEvent('domready', ( function(){

	/* debug for IE and Firefox */
	_d = new DebugToolboxClass() ;
	   
	// init du menu, variable globale
	menuObject = new MenuClass( $$("div#header.frUrssafImage > div#menu")[0], 'menuObject' ) ;
   

}) );