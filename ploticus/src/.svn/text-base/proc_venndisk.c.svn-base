/* ======================================================= *
 * Copyright 1998-2005 Stephen C. Grubb                    *
 * http://ploticus.sourceforge.net                         *
 * Covered by GPL; see the file ./Copyright for details.   *
 * ======================================================= */

/* PROC VENN - render a venn diagram */

#include "pl.h"

#define TWOPI 6.2831854
#define HALFPI 1.5707963
#define PI 3.141592653589793238462643

extern double GL_rand();
extern int PLG_circle();
static int do_disk();

int
PLP_venndisk()
{
char attr[NAMEMAXLEN], val[256];
char *line, *lineval;
int first, nt, lvp;
double cenx, ceny, area, radius;
char color[COLORLEN];
char leglabel[128];
double densfact;
char outline[128];
double ofs;
int botflag;
double areascale;
int datadriven, xfld, areafld, clrfld;
double yloc;
int irow;
double y;
int solidfill;
int lblfld;
char lbldet[128];
double adjx, adjy;
int align;

TDH_errprog( "pl proc venn" );

cenx = ceny = area = -1.0;
densfact = 1.0;
strcpy( leglabel, "" );
strcpy( color, "red" );
strcpy( outline, "" );
botflag = 0;
areascale = 1.0;
ofs = 0.0;
datadriven = xfld = areafld = clrfld = 0;
yloc = 0.5;
solidfill = 0;
lblfld = 0;
strcpy( lbldet, "" );

/* get attributes.. */

first = 1;
while( 1 ) {
	line = getnextattr( first, attr, val, &lvp, &nt );
	if( line == NULL ) break;
	first = 0;
	lineval = &line[lvp];

	if( stricmp( attr, "location" )==0 ) getcoords( "location", lineval, &cenx, &ceny );
	else if( stricmp( attr, "bottomlocation" )==0 ) {
		getcoords( "bottomlocation", lineval, &cenx, &ceny ); /* given in scale units */
		botflag = 1;
		}
	else if( stricmp( attr, "area" )==0 ) area = atof( val );        /* square inches unless areascale given */
	else if( stricmp( attr, "areascale" )==0 ) areascale = atof( val );        
	else if( stricmp( attr, "color" )==0 ) strcpy( color, val );
	else if( stricmp( attr, "legendlabel" )==0 || stricmp( attr, "label" )==0 ) strcpy( leglabel, lineval );
	else if( stricmp( attr, "labelfield" )==0 ) lblfld = fref( val );
	else if( stricmp( attr, "labeldetails" )==0 ) strcpy( lbldet, lineval );
	else if( stricmp( attr, "density" )==0 ) densfact = atof( val );
	else if( stricmp( attr, "outline" )==0 ) strcpy( outline, lineval );
	else if( stricmp( attr, "dotsize" )==0 ) ofs = atof( val );

	else if( stricmp( attr, "areafld" )==0 ) { areafld = fref( val ); datadriven = 1; }
	else if( stricmp( attr, "xfld" )==0 ) xfld = fref( val );
	else if( stricmp( attr, "colorfld" )==0 ) clrfld = fref( val );
	else if( stricmp( attr, "yloc" )==0 ) yloc = atof( val );
 	else if( stricmp( attr, "solidfill" )==0 ) {
                if( strnicmp( val, YESANS, 1 )==0 ) solidfill = 1;
                else solidfill = 0;
                }


	else Eerr( 1, "attribute not recognized", attr );
	}


/* sanity checks.. */
if( !datadriven ) {
	if( cenx < 0.0 || ceny < 0.0 || area < 0.0 ) return( Eerr( 428, "The attributes x, y, and area must all be specified", "" ) );
	}

if( datadriven && !scalebeenset() ) return( Eerr( 51, "datadriven requires scaled units.. no scaled plotting area has been defined yet w/ proc areadef", "" ));

/* overrides.. */
if( solidfill ) densfact = 0.0;

if( datadriven ) {
	ceny = Ea( Y, yloc );
	for( irow = 0; irow < Nrecords; irow++ ) {
		area = atof( da( irow, areafld-1 ) );
		area *= areascale;
		if( area > 50.0 ) { Eerr( 72405, "skipping a very large disk", da( irow, areafld ) ); continue; }
		radius = sqrt( area / PI );

		if( xfld > 0 ) cenx = Ea( X, fda( irow, xfld-1, X ));
		else cenx = Ea( X, (double)(irow+1) );

		if( clrfld > 0 ) strcpy( color, da( irow, clrfld-1 ));
		y = ceny + radius;  /* so all disk bottoms are on the line.. */
		do_disk( cenx, y, radius, color, densfact, ofs, outline, solidfill );
		if( lblfld > 0 ) {
			double labx, laby;
			textdet( "labeldetails", lbldet, &align, &adjx, &adjy, -2, "R", 1.0 );
			if( align == '?' ) align = 'C';
			labx = cenx+adjx;
			laby = (Elimit( Y, 'l', 'a' )-(Ecurtextheight*2.0))+adjy;
			Emov( labx, laby );
			Edotext( da( irow, lblfld-1 ), align );
			}
		}
	}

else	{   /* individual disks */
	
	/* now do the plotting work.. */
	/* convert area to a radius in inches.. */
	area *= areascale;
	if( area > 50.0 ) return( Eerr( 72405, "disk area is too large.. reduce 'area' or reduce 'areascale'", "" ) );
	
	radius = sqrt( area / PI );
	if( PLS.usingcm ) { cenx /= 2.54; ceny /= 2.54; ofs /= 2.54; radius /= 2.54; }
	
	if( botflag ) ceny += radius;
	
	do_disk( cenx, ceny, radius, color, densfact, ofs, outline, solidfill );
	
	if( leglabel[0] != '\0' ) PL_add_legent( LEGEND_COLOR, leglabel, "", color, "", "" );
	}

return( 0 );
}


/* ----------------------------------------------------------- */
static int
do_disk( cenx, ceny, radius, color_in, densfact, ofs, outline_in, solidfill )
double cenx, ceny, radius, densfact, ofs;
char *color_in, *outline_in;
int solidfill;
{
int i, j, ndots;
char val[100];
double area, dotx, doty, dx, dy, sqrt();
char outline[100], color[40];

area = PI * radius * radius;  /* standardize area into square inches.. we'll use it for dot density */
ndots = (int)(area * 2000 * densfact);
strcpy( outline, outline_in );
strcpy( color, color_in );
if( stricmp( color, "none" )==0 ) {
	densfact = 0.0;
	strcpy( color, "gray(0.7)" );
	}

if( densfact > 0.0 ) {
     sprintf( val, "color=%s width=0.3", color );
     linedet( "dots", val, 1.0 );

     if( Edev == 's' && ofs == 0.0 ) ofs = 0.008;
     for( i = 0; i < ndots; i++ ) {
	/* random location of candidate dot.. */
	dotx = cenx - radius + (GL_rand() * radius * 2);
	doty = ceny - radius + (GL_rand() * radius * 2);
	
	/* if dot is outside the disk skip it.. */
	dx = fabs( dotx - cenx );
        dy = fabs( doty - ceny );
	if( sqrt( (dx*dx) + (dy*dy) ) > (radius-ofs) ) continue;

	if( ofs == 0.0 ) { Emov( dotx, doty ); Elin( dotx, doty+ofs ); }
	else	{   /* randomized tiny line segment direction.. */
		j = (int) (GL_rand() * 3.0);
		if( j == 0 ) { Emov( dotx, doty ); Elin( dotx, doty+ofs ); }
		else if( j == 1 ) { Emov( dotx, doty ); Elin( dotx+ofs, doty+ofs ); }
		else if( j == 2 ) { Emov( dotx, doty ); Elin( dotx+ofs, doty-ofs ); }
		}
	}
    }

if( solidfill ) PLG_circle( cenx, ceny, radius, color, 0, 60 );

if( stricmp( outline, "no" ) != 0 ) {
	if( stricmp( outline, "yes" )==0 ) strcpy( outline, "" ); 
	if( ! GL_slmember( outline, "*color=*" )) { sprintf( val, " color=%s ", color ); strcat( outline, val ); } 
	linedet( "outline", outline, 0.3 );
	PLG_circle( cenx, ceny, radius, "", 1, 60 );
	}
return( 0 );
}



/* ======================================================= *
 * Copyright 1998-2005 Stephen C. Grubb                    *
 * http://ploticus.sourceforge.net                         *
 * Covered by GPL; see the file ./Copyright for details.   *
 * ======================================================= */
