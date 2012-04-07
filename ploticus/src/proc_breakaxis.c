/* ======================================================= *
 * Copyright 1998-2005 Stephen C. Grubb                    *
 * http://ploticus.sourceforge.net                         *
 * Covered by GPL; see the file ./Copyright for details.   *
 * ======================================================= */

/* PROC BREAKAXIS - break symbol for broken axis or broken bar */

#include "pl.h"

int
PLP_breakaxis()
{
char attr[NAMEMAXLEN], val[256];
char *line, *lineval;
int nt, lvp;
int first;

char axis;
char loc[80];
double len;
char breakpt[80];
char fillcolor[COLORLEN];
double gapsize;
char opax;
double bkpt;
char linedetails[256];
double x, y;
double ofs, hlen;
char style[80];

TDH_errprog( "breakaxis" );




/* initialize */
axis = 'y';
strcpy( loc, "axis" );
len = 0.3;
strcpy( breakpt, "" );
strcpy( fillcolor, Ecurbkcolor );
gapsize = 0.05;
strcpy( linedetails, "" );
strcpy( style, "slant" );

/* get attributes.. */
first = 1;
while( 1 ) {
	line = getnextattr( first, attr, val, &lvp, &nt );
	if( line == NULL ) break;
	first = 0;
	lineval = &line[lvp];

	if( stricmp( attr, "axis" )==0 ) axis = val[0];
	else if( stricmp( attr, "location" )==0 ) strcpy( loc, lineval );
	else if( stricmp( attr, "linelength" )==0 ) {
		len = atof( val );
		if( PLS.usingcm ) len /= 2.54;
		}
	else if( stricmp( attr, "breakpoint" )==0 ) strcpy( breakpt, val );
	else if( stricmp( attr, "fillcolor" )==0 ) strcpy( fillcolor, val );
	else if( stricmp( attr, "linedetails" )==0 ) strcpy( linedetails, lineval );
	else if( stricmp( attr, "style" )==0 ) strcpy( style, val );
	else if( stricmp( attr, "gapsize" )==0 ) {
		gapsize = atof( val );
		if( PLS.usingcm ) gapsize /= 2.54;
		}

	else Eerr( 1, "attribute not recognized", attr );
	}



/* overrides and degenerate cases */
if( !scalebeenset() )
         return( Eerr( 51, "No scaled plotting area has been defined yet w/ proc areadef", "" ) );
if( breakpt[0] == '\0' )
         return( Eerr( 9251, "The breakpoint attribute MUST be set", "" ) );


/* now do the plotting work.. */
/* -------------------------- */

bkpt = Econv( axis, breakpt );

if( axis == 'x' ) { opax = 'y'; Eflip = 1; }
else opax = 'x';


/* set line type */
linedet( "linedetails", linedetails, 0.5 );

hlen = len/2.0;
if( stricmp( loc, "axis" )==0 ) x = Elimit( opax, 'l', 'a' );   
else x = Ea( X, Econv( opax, loc ) );

y = Ea( Y, bkpt );

if( strnicmp( style, "sl", 2 )==0 ) { 	/* do a 'slanted' break symbol */
	ofs = gapsize * -1.0;
	Emov( x - hlen, y + ofs );
	Epath( x + hlen, y );
	Epath( x + hlen, y - ofs );
	Epath( x - hlen, y );
	Ecolorfill( fillcolor );
	if( strnicmp( linedetails, "no", 2 ) != 0 ) {
		Emov( x - hlen, y + ofs );
		Elin( x + hlen, y );
		Emov( x - hlen, y );
		Elin( x + hlen, y - ofs );
		}
	}

else	{	  /* do a straight break symbol */
	ofs = gapsize / 2.0;
	x = Ea( X, Econv( opax, loc ) );
	y = Ea( Y, bkpt );
	Ecblock( x - hlen, y - ofs, x + hlen, y + ofs, fillcolor, 0 );
	if( strnicmp( linedetails, "no", 2 ) != 0 ) {
		Emov( x - hlen, y - ofs );
		Elin( x + hlen, y - ofs );
		Emov( x - hlen, y + ofs );
		Elin( x + hlen, y + ofs );
		}
	}

if( axis == 'x' ) Eflip = 0;

return( 0 );
}

/* ======================================================= *
 * Copyright 1998-2005 Stephen C. Grubb                    *
 * http://ploticus.sourceforge.net                         *
 * Covered by GPL; see the file ./Copyright for details.   *
 * ======================================================= */
