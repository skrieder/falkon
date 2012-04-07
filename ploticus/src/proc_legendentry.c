/* ======================================================= *
 * Copyright 1998-2005 Stephen C. Grubb                    *
 * http://ploticus.sourceforge.net                         *
 * Covered by GPL; see the file ./Copyright for details.   *
 * ======================================================= */

/* PROC LEGENDENTRY - proc to define one legend entry */

#include "pl.h"

int
PLP_legendentry( )
{
char attr[NAMEMAXLEN], val[256];
char *line, *lineval;
int nt, lvp;
int first;

char label[120];
char sampletype[80];
char spec1[120];
char spec2[120];
char spec3[120];
char tag[80];
int  samptyp;

TDH_errprog( "pl proc legendentry" );


/* initialize */
strcpy( label, "" );
strcpy( sampletype, "none" );
strcpy( spec1, "" );
strcpy( spec2, "" );
strcpy( spec3, "" );

/* get attributes.. */
first = 1;
while( 1 ) {
	line = getnextattr( first, attr, val, &lvp, &nt );
	if( line == NULL ) break;
	first = 0;
	lineval = &line[lvp];

	if( stricmp( attr, "label" )==0 ) strcpy( label, lineval );
	else if( stricmp( attr, "tag" )==0 ) strcpy( tag, val ); /* was lineval, but docs say single token.  scg 11/21/03 */
	else if( stricmp( attr, "sampletype" )==0 ) strcpy( sampletype, val );
	else if( stricmp( attr, "details" )==0 ) strcpy( spec1, lineval );
	else if( stricmp( attr, "details2" )==0 ) strcpy( spec2, lineval );
	else if( stricmp( attr, "details3" )==0 ) strcpy( spec3, lineval );

	else Eerr( 1, "attribute not recognized", attr );
	}


if( stricmp( sampletype, "line" )==0 ) samptyp = LEGEND_LINE;
else if( stricmp( sampletype, "color" )==0 ) samptyp = LEGEND_COLOR;
else if( stricmp( sampletype, "symbol" )==0 ) samptyp = LEGEND_SYMBOL;
else if( stricmp( sampletype, "text" )==0 ) samptyp = LEGEND_TEXT;
else if( stricmp( sampletype, "line+symbol" )==0 ) samptyp = LEGEND_LINE + LEGEND_SYMBOL;
else if( stricmp( sampletype, "text+symbol" )==0 ) samptyp = LEGEND_TEXT + LEGEND_SYMBOL;
else if( stricmp( sampletype, "none" )==0 ) samptyp = 0;

PL_add_legent( samptyp, label, tag, spec1, spec2, spec3 );

return( 0 );
}

/* ======================================================= *
 * Copyright 1998-2005 Stephen C. Grubb                    *
 * http://ploticus.sourceforge.net                         *
 * Covered by GPL; see the file ./Copyright for details.   *
 * ======================================================= */
