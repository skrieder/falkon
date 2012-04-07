/* ======================================================= *
 * Copyright 1998-2005 Stephen C. Grubb                    *
 * http://ploticus.sourceforge.net                         *
 * Covered by GPL; see the file ./Copyright for details.   *
 * ======================================================= */

/* PROC BEVELRECT - bevelled rectange, e.g. for button */

#include "pl.h"

int
PLP_rect()
{
char attr[NAMEMAXLEN], val[256];
char *line, *lineval;
int nt, lvp;
int first;

double xlo, ylo, xhi, yhi;
char color[COLORLEN];
double bevelsize;
char lowbevelcolor[COLORLEN], hibevelcolor[COLORLEN], shadowcolor[COLORLEN];
double shadowsize;
int gotrect;
char outline[128];
int ioutline;
char mapurl[MAXPATH];
char maplabel[MAXTT];
int clickmap_on;

TDH_errprog( "pl proc rect" );


/* initialize */
strcpy( color, "dullyellow" );
/* bevelsize = 0.1; */
bevelsize = 0.0;
strcpy( lowbevelcolor, "0.6" );
strcpy( hibevelcolor, "0.8" );
strcpy( shadowcolor, "black" );
shadowsize = 0.0;
strcpy( outline, "" );
ioutline = 0;
gotrect = 0;
clickmap_on = 0;
strcpy( mapurl, "" );
strcpy( maplabel, "" );


/* get attributes.. */
first = 1;
while( 1 ) {
	line = getnextattr( first, attr, val, &lvp, &nt );
	if( line == NULL ) break;
	first = 0;
	lineval = &line[lvp];

	if( stricmp( attr, "rectangle" )==0 ) {
                getbox( "box", lineval, &xlo, &ylo, &xhi, &yhi );
		gotrect = 1;
		}
	else if( stricmp( attr, "color" )==0 ) strcpy( color, val );
	else if( stricmp( attr, "bevelsize" )==0 ) bevelsize = atof( val );
	else if( stricmp( attr, "shadowsize" )==0 ) shadowsize = atof( val );
	else if( stricmp( attr, "lowbevelcolor" )==0 ) strcpy( lowbevelcolor, val );
	else if( stricmp( attr, "hibevelcolor" )==0 ) strcpy( hibevelcolor, val );
	else if( stricmp( attr, "shadowcolor" )==0 ) strcpy( shadowcolor, val );
	else if( stricmp( attr, "clickmapurl" )==0 ) {
		if( PLS.clickmap ) { strcpy( mapurl, val ); clickmap_on = 1; }
		}
	else if( stricmp( attr, "clickmaplabel" )==0 ) {
		if( PLS.clickmap ) { strcpy( maplabel, lineval ); clickmap_on = 1; }
		}
        else if( stricmp( attr, "clickmaplabeltext" )==0 ) {
                if( PLS.clickmap ) { getmultiline( "clickmaplabeltext", lineval, MAXTT, maplabel ); clickmap_on = 1; }
                }

	else if( stricmp( attr, "outline" )==0 ) {
		strcpy( outline, lineval );
		if( GL_smember( val, "no none" )==0 ) ioutline = 1;
		}

	else Eerr( 1, "attribute not recognized", attr );
	}



/* now do the plotting work.. */

if( !gotrect ) return( Eerr( 695, "No rectangle specified", "" ));
if( stricmp( color, "none" )==0 ) strcpy( color, "" );/* "none" added scg 1/21/05 */

linedet( "outline", outline, 0.5 );
Ecblock( xlo, ylo, xhi, yhi, color, ioutline );

if( bevelsize > 0.0 || shadowsize > 0.0 ) 
	Ecblockdress( xlo, ylo, xhi, yhi,
       		bevelsize, lowbevelcolor, hibevelcolor, shadowsize, shadowcolor);

if( clickmap_on ) clickmap_entry( 'r', mapurl, 0, xlo, ylo, xhi, yhi, 0, 0, maplabel );

return( 0 );
}

/* ======================================================= *
 * Copyright 1998-2005 Stephen C. Grubb                    *
 * http://ploticus.sourceforge.net                         *
 * Covered by GPL; see the file ./Copyright for details.   *
 * ======================================================= */
