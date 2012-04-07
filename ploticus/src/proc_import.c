/* ======================================================= *
 * Copyright 1998-2005 Stephen C. Grubb                    *
 * http://ploticus.sourceforge.net                         *
 * Covered by GPL; see the file ./Copyright for details.   *
 * ======================================================= */

/* PROC IMPORT an image file */

#include "pl.h"

int
PLP_import()
{
char attr[NAMEMAXLEN], val[256];
char *line, *lineval;
int nt, lvp;
int first;

int stat;

char format[20];
char filename[256];
double x, y;
char imalign[30];
double scalex, scaley;

TDH_errprog( "pl proc import" );




/* initialize */
strcpy( format, "png" );
strcpy( filename, "" );
x = 3.0;
y = 3.0;
strcpy( imalign, "centered" );
scalex = 1.0;
scaley = 1.0;


/* get attributes.. */
first = 1;
while( 1 ) {
	line = getnextattr( first, attr, val, &lvp, &nt );
	if( line == NULL ) break;
	first = 0;
	lineval = &line[lvp];

	if( stricmp( attr, "format" )==0 ) strcpy( format, val );
	else if( strnicmp( attr, "file", 4 )==0 ) strcpy( filename, val );
	else if( stricmp( attr, "align" )==0 ) strcpy( imalign, val );
	else if( stricmp( attr, "scale" )==0 ) {
		nt = sscanf( lineval, "%lf %lf", &scalex, &scaley );
		if( nt == 1 ) scaley = scalex;
		}
	else if( stricmp( attr, "location" )==0 ) {
		if( lineval[0] != '\0' ) getcoords( "location", lineval, &x, &y );
		}
               

	else Eerr( 1, "attribute not recognized", attr );
	}


/* now do the plotting work.. */
if( !devavail( format ) )
	  return( Eerr( 4891, "image format not supported in this build", format ) );

stat = Eimload( filename, scalex, scaley );
if( stat != 0 ) return( Eerr( 5893, "Error on image load", filename ) );

stat = Eimplace( x, y, imalign, 1.0, 1.0 );

return( 0 );
}

/* ======================================================= *
 * Copyright 1998-2005 Stephen C. Grubb                    *
 * http://ploticus.sourceforge.net                         *
 * Covered by GPL; see the file ./Copyright for details.   *
 * ======================================================= */
