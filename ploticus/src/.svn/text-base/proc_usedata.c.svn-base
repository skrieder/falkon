/* ======================================================= *
 * Copyright 1998-2005 Stephen C. Grubb                    *
 * http://ploticus.sourceforge.net                         *
 * Covered by GPL; see the file ./Copyright for details.   *
 * ======================================================= */

/* PROC USEDATA */

/* Note: if no attributes given, this does the same as the old 'proc originaldata' */

#include "pl.h"

int
PLP_usedata( )
{
char attr[NAMEMAXLEN], val[256];
char *line, *lineval;
int nt, lvp;
int first;


TDH_errprog( "pl proc usedata" );

if( PLD.curds < 0 ) return( Eerr( 2478, "no data have been read yet", "" ));

/* get attributes.. */
first = 1;
while( 1 ) {
	line = getnextattr( first, attr, val, &lvp, &nt );
	if( line == NULL ) break;
	first = 0;
	lineval = &line[lvp];

	if( stricmp( attr, "element" )== 0 ) {
		if( atoi( val ) <= PLD.curds ) PLD.curds = atoi( val );
		}
	else if( stricmp( attr, "pop" )== 0 ) PLD.curds -= atoi( val );
	else if( strnicmp( attr, "original", 8 )== 0 ) PLD.curds = 0;
	else if( stricmp( attr, "fieldnames" )==0 ) definefieldnames( lineval );
	else Eerr( 1, "attribute not recognized", attr );
	}

if( first == 1 ) PLD.curds = 0;   /* no attributes specified - (originaldata) */

if( PLD.curds < 0 ) PLD.curds = 0;

if( PLS.debug ) fprintf( PLS.diagfp, "using data set %d\n", PLD.curds );
setintvar( "NRECORDS", Nrecords );
setintvar( "NFIELDS", Nfields );

return( 0 );
}

/* ======================================================= *
 * Copyright 1998-2005 Stephen C. Grubb                    *
 * http://ploticus.sourceforge.net                         *
 * Covered by GPL; see the file ./Copyright for details.   *
 * ======================================================= */
