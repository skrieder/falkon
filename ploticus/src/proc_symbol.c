/* ======================================================= *
 * Copyright 1998-2005 Stephen C. Grubb                    *
 * http://ploticus.sourceforge.net                         *
 * Covered by GPL; see the file ./Copyright for details.   *
 * ======================================================= */

/* PROC SYMBOL - render one symbol */
#include "pl.h"

int
PLP_symbol()
{
char attr[NAMEMAXLEN], val[256];
char *line, *lineval;
int nt, lvp;
int first;

char buf[256];
char symbol[256];
double x, y, radius;

TDH_errprog( "pl proc symbol" );

/* initialize */
strcpy( symbol, "" );
x = 3.0;
y = 3.0;
radius = 0.2;

/* get attributes.. */
first = 1;
while( 1 ) {
	line = getnextattr( first, attr, val, &lvp, &nt );
	if( line == NULL ) break;
	first = 0;
	lineval = &line[lvp];

	if( stricmp( attr, "symbol" )==0 ) strcpy( symbol, lineval );
	else if( stricmp( attr, "location" )==0 ) {
                if( lineval[0] != '\0' ) getcoords( "location", lineval, &x, &y );
                }
	else Eerr( 1, "attribute not recognized", attr );
	}

symdet( "symbol", symbol, buf, &radius );
Emark( x, y, buf, radius );

return( 0 );
}

/* ======================================================= *
 * Copyright 1998-2005 Stephen C. Grubb                    *
 * http://ploticus.sourceforge.net                         *
 * Covered by GPL; see the file ./Copyright for details.   *
 * ======================================================= */
