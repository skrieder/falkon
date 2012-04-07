/* ======================================================= *
 * Copyright 1998-2005 Stephen C. Grubb                    *
 * http://ploticus.sourceforge.net                         *
 * Covered by GPL; see the file ./Copyright for details.   *
 * ======================================================= */

/* PROC SETTINGS - date, unit, notation settings */

#include "pl.h"
extern int PLGP_settings(), PLGS_setxmlparms();
extern int setuid(), setgid();

int
PLP_settings()
{
char attr[NAMEMAXLEN], val[256];
char *line, *lineval;
int nt, lvp;
int first;

int stat;


TDH_errprog( "pl proc settings" );

/* get attributes.. */
first = 1;
while( 1 ) {
	line = getnextattr( first, attr, val, &lvp, &nt );
	if( line == NULL ) break;
	first = 0;
	lineval = &line[lvp];

	/* settings that aren't shared with config file go here: */

	/* do this here since "format" is an old attr name still supported.. */
	if( stricmp( attr, "format" )==0 || stricmp( attr, "dateformat" )==0 ) DT_setdatefmt( val );

	else if( GL_slmember( attr, "pivotyear months* weekdays omitweekends lazydates" )) {
                stat = DT_setdateparms( attr, lineval );
		}

	/* the rest are shared settings.. */
	else 	{
		stat = PL_sharedsettings( attr, val, lineval );
		if( stat ) Eerr( 1, "attribute not recognized", attr );
		}
	}

return( 0 );
}


/* ========================================= */
/* common code for setting attributes from proc settings or config file */
int
PL_sharedsettings( attr, val, lineval )
char *attr, *val, *lineval;
{


if( stricmp( attr, "units" )==0 ) {
	if( tolower( val[0] ) == 'c' ) { PLS.usingcm = 1; setintvar( "CM_UNITS", 1 ); }
	else { PLS.usingcm = 0; setintvar( "CM_UNITS", 0 ); }
	}

#ifndef WIN32
else if( stricmp( attr, "uid" )==0 ) setuid( atoi( val ) );
        else if( stricmp( attr, "gid" )==0 ) setgid( atoi( val ) );
#endif
#ifndef NORLIMIT
else if( stricmp( attr, "cpulimit" )==0 ) TDH_reslimits( "cpu", atoi( val ) );
#endif

else if( stricmp( attr, "numbernotation" )==0 ) {
	if( stricmp( val, "us" )==0 ) PLS.bignumspacer = ',';
	else if( stricmp( val, "euro" )==0 ) PLS.bignumspacer = '.';
	else PLS.bignumspacer = '\0';
	}
else if( stricmp( attr, "numberspacerthreshold" )==0 ) PLS.bignumthres = atoi( val ); /* scg 2/28/02 */

else if( stricmp( attr, "font" )==0 ) strcpy( Estandard_font, lineval ); 

else if( stricmp( attr, "encodenames" )==0 ) {					      /* added scg 8/4/04 */
        if( strnicmp( val, YESANS, 1 )==0 ) PL_encode_fnames( 1 ); 
        else PL_encode_fnames( 0 );
	}

#ifndef NOPS
else if( stricmp( attr, "ps_latin1_encoding" )==0 ) {
        if( strnicmp( val, YESANS, 1 )==0 ) PLGP_settings( "ps_latin1_encoding", "1" ); /* added 7/28/04 */
        else PLGP_settings( "ps_latin1_encoding", "0" ); /* added 7/28/04 */
	}
#endif

#ifndef NOSVG
else if( stricmp( attr, "xml_encoding" )==0 ) PLGS_setxmlparms( "encoding", val );
else if( stricmp( attr, "xml_declaration" )==0 ) {
	if( strnicmp( val, YESANS, 1 )==0 ) PLGS_setxmlparms( "xmldecl", "1" );
	else PLGS_setxmlparms( "xmldecl", "0" );
	}
else if( stricmp( attr, "svg_tagparms" )==0 ) PLGS_setxmlparms( "svgparms", lineval );
else if( stricmp( attr, "svg_linkparms" )==0 ) PLGS_setxmlparms( "linkparms", lineval );
else if( stricmp( attr, "svg_mouseover_js" )==0 ) PLGS_setxmlparms( "mouseover_js", val );
#endif

else if( stricmp( attr, "dtsep" )==0 ) DT_setdtsep( val[0] );
else if( stricmp( attr, "errmsgpre" )==0 ) TDH_errprogsticky( lineval ); /* added 3/25/04 scg */
else if( stricmp( attr, "enable_suscripts" )==0 ) {
	if( strnicmp( val, YESANS, 1 )==0 ) PLG_textsupmode( 1 );
	else PLG_textsupmode( 0 );
	}
#ifdef HOLD
else if( stricmp( attr, "sanezone" )==0 ) {
	double sanex, saney;
	sscanf( lineval, "%lf %lf", &sanex, &saney );
	PLG_sanezone( 0.0, 0.0, sanex, saney );
	}
#endif

else return( 1 ); /* not found */

return( 0 ); /* ok */
}

/* ======================================================= *
 * Copyright 1998-2005 Stephen C. Grubb                    *
 * http://ploticus.sourceforge.net                         *
 * Covered by GPL; see the file ./Copyright for details.   *
 * ======================================================= */
