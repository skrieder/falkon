/* ======================================================= *
 * Copyright 1998-2005 Stephen C. Grubb                    *
 * http://ploticus.sourceforge.net                         *
 * Covered by GPL; see the file ./Copyright for details.   *
 * ======================================================= */

/* PROC LINE - draw arbitrary line(s) */

#include "pl.h"
#define ABSOLUTE 'a'
#define LOCVAL 'l'
#define SCALED 's'

int
PLP_line()
{
char attr[NAMEMAXLEN], val[256];
char *line, *lineval;
int nt, lvp;
int first;

char buf[256];
double x, y, ancx, ancy;
char linedetails[256];
char notation;
char a[40], b[40], c[40], d[40];
int ix;
int buflen;
int ancgiven;


TDH_errprog( "pl proc line" );

/* initialize */
strcpy( PL_bigbuf, "" );
strcpy( linedetails, "" );
notation = LOCVAL;
x = 0.0; y = 0.0;
ancx = 0.0; ancy = 0.0;
ancgiven = 0;

/* get attributes.. */
first = 1;
while( 1 ) {
	line = getnextattr( first, attr, val, &lvp, &nt );
	if( line == NULL ) break;
	first = 0;
	lineval = &line[lvp];

	if( stricmp( attr, "points" )==0 ) 
		getmultiline( "points", lineval, MAXBIGBUF, PL_bigbuf );
		

	else if( stricmp( attr, "linedetails" )==0 ) strcpy( linedetails, lineval );
	else if( stricmp( attr, "notation" )==0 ) notation = tolower( (int) val[0] );
	else if( stricmp( attr, "anchor" )==0 ) {
		getcoords( "anchor", lineval, &ancx, &ancy );
		ancgiven = 1;
		}

	else Eerr( 1, "attribute not recognized", attr );
	}


/* overrides & sanity checks.. */
if( notation != ABSOLUTE && notation != SCALED && notation != LOCVAL ) {
	notation = LOCVAL;
	Eerr( 479, "warning: invalid 'notation'.. using locval", "" );
	}

if( ancgiven && notation == SCALED ) { 
	Eerr( 478, "warning, 'anchor' can't be used with notation=scaled .. ignored", "" ); 
	ancx = ancy = 0.0; 
	} 


/* now do the plotting work.. */
linedet( "linedetails", linedetails, 1.0 );

ix = 0;
first = 1;
buflen = strlen( PL_bigbuf );
while( 1 ) {
	GL_getchunk( buf, PL_bigbuf, &ix, "\n" );
	nt = sscanf( buf, "%s %s %s %s", a, b, c, d );

	if( nt == 4 || first ) { 
		if( notation == ABSOLUTE ) { 
			Emov( atof( a )+ancx, atof( b )+ancy ); 
			Elin( atof( c )+ancx, atof( d )+ancy ); 
			}
		else if( notation == SCALED ) { 
			Emov( PL_u( X, a ), PL_u( Y, b ) ); 
			if( Econv_error() ) Eerr( 2945, "unplottable value(s) ", buf );
			if( nt == 4 ) { 
				Elin( PL_u( X, c ), PL_u( Y, d ) ); 
				if( Econv_error() ) Eerr( 2946, "unplottable value(s) ", buf );
				} 
			}
		else if( notation == LOCVAL ) { 
			Eposex( a, X, &x ); Eposex( b, Y, &y ); Emov( x+ancx, y+ancy );
			if( Econv_error() ) Eerr( 2947, "unplottable value(s) ", buf );
			Eposex( c, X, &x ); Eposex( d, Y, &y ); Elin( x+ancx, y+ancy );
			if( Econv_error() ) Eerr( 2948, "unplottable value(s) ", buf );
			}
		}

	else if( nt == 2 ) { 
		if( notation == ABSOLUTE ) Elin( atof( a )+ancx, atof( b )+ancy ); 
		else if( notation == SCALED ) Elin( PL_u( X, a ), PL_u( Y, b ) ); 
		else if( notation == LOCVAL ) { Eposex( a, X, &x ); Eposex( b, Y, &y ); Elin( x+ancx, y+ancy ); }
		}
	else if( nt <= 0 ) ;
	else Eerr( 2959, "warning: points must have either 4 or 2 values per line", "" );

	first = 0;

	if( ix >= buflen ) break;
	}
return( 0 );
}

/* ======================================================= *
 * Copyright 1998-2005 Stephen C. Grubb                    *
 * http://ploticus.sourceforge.net                         *
 * Covered by GPL; see the file ./Copyright for details.   *
 * ======================================================= */
