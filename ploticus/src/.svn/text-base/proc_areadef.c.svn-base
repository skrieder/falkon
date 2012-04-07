/* ======================================================= *
 * Copyright 1998-2005 Stephen C. Grubb                    *
 * http://ploticus.sourceforge.net                         *
 * Covered by GPL; see the file ./Copyright for details.   *
 * ======================================================= */

/* PROC AREADEF - Set up a graphics area with scaling */

#include "pl.h"

/* constants */
static int nareas = 17;
static char *areas[17] = {
"standard     1.2 2.5 7.4 7.0   1.5 1.5 9.0 6.2",
"standardp    1.2 3.5 7.4 8.0   1.5 1.5 9.0 6.2",
"square       1.2 2.0 7.2 8.0   2.2 0.8 8.2 6.8",
"whole        1.2 1.0 7.4 9.0   1.5 1.2 9.0 7.0",
"2hi          1.0 5.5 7.6 9.0   1.0 4.5 10.0 7.0",
"2lo          1.0 1.0 7.6 4.5   1.0 1.0 10.0 3.5",
"2left        1.0 1.0 4.0 9.5   1.0 1.0 5.25 6.5",
"2right       5.0 1.0 8.0 9.5   6.25 1.0 10.5 6.5",
"3hi          1.0 7.0 7.6 9.0   1.0 5.5 10.0 7.5",
"3mid         1.0 4.0 7.6 6.0   1.0 3.0 10.0 5.0",
"3lo          1.0 1.0 7.6 3.0   1.0 0.5 10.0 2.5",
"4nw          1.0 6.0 4.0 9.0   1.0 4.0 5.25 7.0",
"4ne          4.5 6.0 7.5 9.0   6.25 4.0 10.5 7.0",
"4sw          1.0 1.5 4.0 4.5   1.0 0.5 5.25 3.5",
"4se          4.5 1.5 7.5 4.5   6.25 0.5 10.5 3.5",
"slide        1 0.7 5.85 3.2    1 0.7 5.85 3.2",
"lifetab      1.0 1.0 7.0 4.5   1.5 1.0 7.5 4.5",
};

int
PLP_areadef()
{
int i;
char attr[NAMEMAXLEN], val[256];
char *line, *lineval;
int nt, lvp;
int first;

int stat;
double xlo, xhi, ylo, yhi;
char areaname[NAMEMAXLEN];
char xscaletype[50], yscaletype[50];
char title[256];
char title2[256];
char titledet[256];
char title2det[256];
char frame[256];
int nlines, maxlen;
int doframe;
int namedarea;
int align;
double adjx, adjy;
int gotarea, gotxrange, gotyrange;
int doxaxis, doyaxis;
char areacolor[COLORLEN];
char xminstr[256], xmaxstr[120];  /* was xminstr[120]  - needs bigger if big autorange datafield list given - scg 10/25/04 */
char yminstr[256], ymaxstr[120];  /* was yminstr[120]  -  '' - scg 10/25/04 */
double height, width;
int locspec;
char linebottom[256];
char lineside[256];
double autowf, autowmin, autowmax;
double autohf, autohmin, autohmax;
char mapurl[MAXPATH];
double cmadj;
int docats;
char maplabel[MAXTT];
int clickmap_on;

TDH_errprog( "pl proc areadef" );
 

/* initialize */
strcpy( xscaletype, "linear" );
strcpy( yscaletype, "linear" );
strcpy( areaname, "standard" );
gotxrange = gotyrange = 0;
if( PLS.device == 'c' || PLS.device == 'p' ) strcpy( areaname, "standardp" ); /* for sheet of paper */
strcpy( title, "" );
strcpy( title2, "" );
doframe = 0;
strcpy( frame, "" );
strcpy( titledet, "" );
strcpy( title2det, "" );
namedarea = 1;
/* doxaxis = doyaxis = 1; */
doxaxis = doyaxis = 0;
strcpy( areacolor, "" );
strcpy( PL_bigbuf, "" );
height = 4.0;
width = 6.0;
xlo = 1.5;
ylo = 5.0;
locspec = 0; 
strcpy( xminstr, "" );
strcpy( xmaxstr, "" );
strcpy( yminstr, "" );
strcpy( ymaxstr, "" );
strcpy( linebottom, "" );
strcpy( lineside, "" );
gotarea = 1;
autowf = 0.0; autowmin = 0.0; autowmax = 20.0;
autohf = 0.0; autohmin = 0.0; autohmax = 20.0;
strcpy( mapurl, "" );
strcpy( maplabel, "" );
docats = 0;
clickmap_on = 0;



/* get attributes.. */
first = 1;
while( 1 ) {
	line = getnextattr( first, attr, val, &lvp, &nt );
	if( line == NULL ) break;
	first = 0;
	lineval = &line[lvp];


	if( stricmp( attr, "areaname" )==0 ) {
		strcpy( areaname, val );
		gotarea = 1;
		}
	else if( stricmp( attr, "rectangle" )==0 ) {
		stat = getbox( "rectangle", lineval, &xlo, &ylo, &xhi, &yhi );
		if( stat ) { PLS.skipout = 1; return( 1 ); }
		namedarea = 0;
		gotarea = 1;
		}
	else if( stricmp( attr, "box" )==0 ) {
		sscanf( lineval, "%lf %lf", &width, &height );
		if( PLS.usingcm ) { width /= 2.54; height /= 2.54; }
		namedarea = 0;
		gotarea = 1;
		}	

	else if( stricmp( attr, "location" )==0 ) {
		sscanf( lineval, "%lf %lf", &xlo, &ylo );
		if( PLS.usingcm ) { xlo /= 2.54; ylo /= 2.54; }
		locspec = 1;
		}	

	else if( stricmp( attr, "autowidth" )==0 ) {
		nt = sscanf( lineval, "%lf %lf %lf", &autowf, &autowmin, &autowmax );
		}
		
	else if( stricmp( attr, "autoheight" )==0 ) {
		nt = sscanf( lineval, "%lf %lf %lf", &autohf, &autohmin, &autohmax );
		}

	else if( stricmp( attr, "xrange" )==0 || stricmp( attr, "xautorange" )==0 ) {
		if( strnicmp( val, "datafield", 9 )==0 ) strcpy( xminstr, lineval );
		else if( stricmp( val, "categories" )==0 ) { strcpy( xscaletype, val ); continue; }
		else	{
			nt = sscanf( lineval, "%s %s", xminstr, xmaxstr );
			if( nt != 2 ) {
				PLS.skipout = 1;
				return( Eerr( 105, "both min and max expected", "xrange" ));
				}
			}
		gotxrange = 1;
		}
	else if( stricmp( attr, "yrange" )==0 || stricmp( attr, "yautorange" )==0 ) {
		if( strnicmp( val, "datafield", 9 )==0 ) strcpy( yminstr, lineval );
		else if( stricmp( val, "categories" )==0 ) { strcpy( yscaletype, val ); continue; }
		else	{
			nt = sscanf( lineval, "%s %s", yminstr, ymaxstr );
			if( nt != 2 ) {
				PLS.skipout = 1;
				return( Eerr( 105, "both min and max expected", "yrange" ));
				}
			}
		gotyrange = 1;
		}

	else if( stricmp( attr, "xscaletype" )==0 ) strcpy( xscaletype, lineval );
	else if( stricmp( attr, "yscaletype" )==0 ) strcpy( yscaletype, lineval );

	else if( stricmp( attr, "frame" )==0 ) {
		if( strnicmp( val, "no", 2 )==0 ) doframe = 0;
		else { strcpy( frame, lineval ); doframe = 1; }
		}
	else if( stricmp( attr, "title" )==0 ) {
		strcpy( title, lineval );
		convertnl( title );
		}
	else if( stricmp( attr, "title2" )==0 ) {
		strcpy( title2, lineval );
		convertnl( title2 );
		}
	else if( stricmp( attr, "titledetails" )==0 ) strcpy( titledet, lineval );
	else if( stricmp( attr, "title2details" )==0 ) strcpy( title2det, lineval );
	else if( stricmp( attr, "areacolor" )==0 ) strcpy( areacolor, val );
	else if( stricmp( attr, "linebottom" )==0 ) {
		if( strnicmp( val, "no", 2 )!=0 ) strcpy( linebottom, lineval );
		}
	else if( stricmp( attr, "lineside" )==0 ) {
		if( strnicmp( val, "no", 2 )!= 0 ) strcpy( lineside, lineval );
		}

	else if( stricmp( attr, "clickmapurl" )==0 ) {
		if( PLS.clickmap ) { strcpy( mapurl, val ); clickmap_on = 1 ; }
		}
	else if( stricmp( attr, "clickmaplabel" )==0 ) {
		if( PLS.clickmap ) { strcpy( maplabel, lineval ); clickmap_on = 1 ; }
		}
        else if( stricmp( attr, "clickmaplabeltext" )==0 ) {
                if( PLS.clickmap ) { getmultiline( "clickmaplabeltext", lineval, MAXTT, maplabel ); clickmap_on = 1; }
                }

	else if( stricmp( attr, "axes" )==0 ) {
		if( stricmp( val, "none" )==0 ) { doxaxis = 0; doyaxis = 0; }
		else if( stricmp( val, "x" )==0 ) doxaxis = 1;
		else if( stricmp( val, "y" )==0 ) doyaxis = 1;
		else if( stricmp( val, "both" )==0 ) { doxaxis = 1; doyaxis = 1; }
		}
	else if( GL_slmember( attr, "?axis.stubs ?axis.selflocatingstubs" )) {
		if( strnicmp( attr, "xaxis.", 6 )==0 ) doxaxis = 1; 
		if( strnicmp( attr, "yaxis.", 6 )==0 ) doyaxis = 1; 
		if( GL_slmember( val, "none inc* dat*matic file datafield* list categories usecategories" )) ;
		else getmultiline( "stubtext", lineval, MAXBIGBUF-5, PL_bigbuf ); /* just to skip past it..*/
		}
	else if( strnicmp( attr, "xaxis.", 6 )==0 ) { doxaxis = 1; continue; }
	else if( strnicmp( attr, "yaxis.", 6 )==0 ) { doyaxis = 1; continue; }
	else if( GL_slmember( attr, "axisline tic* minortic*" )) continue;

	else if( GL_slmember( attr, "?categories ?extracategory catcompmethod" )) {
		docats = 1;
		if( GL_slmember( attr, "?categories" ) && strnicmp( val, "datafield", 9 )!=0 ) 
				getmultiline( "categories", lineval, MAXBIGBUF-5, PL_bigbuf ); /* skip over it.. */
		if( attr[0] == 'x' ) strcpy( xscaletype, "categories" );
		if( attr[0] == 'y' ) strcpy( yscaletype, "categories" );
		}

	else Eerr( 1, "areadef attribute not recognized", attr );
	}




/* now do the plotting work.. */

PL_resetstacklist();  /* (obscure) - reset the bar graph list that tries to
			keep track of fields for automatic stacking */

/* go set up category sets if needed.. */
if( docats ) {
	PLP_categories(); 
	TDH_errprog( "pl proc areadef" );
	}

if( stricmp( xscaletype, "categories" )==0 ) {
	if( PL_ncats('x') < 1 ) { 
		PLS.skipout = 1; 
		return( Eerr( 5972, "no x categories exist", "" ) ); 
		}
	gotxrange = 1;  /* note- categories could have been filled from a previous data set */
	strcpy( xminstr, "0" );
	sprintf( xmaxstr, "%d", PL_ncats('x')+1 );
	}

if( stricmp( yscaletype, "categories" )==0 ) {
	if( PL_ncats('y') < 1 ) { 
		PLS.skipout = 1; 
		return( Eerr( 5973, "no y categories exist", "" ) ); 
		}
	gotyrange = 1; /* note- categories could have been filled from a previous data set */
	strcpy( yminstr, "0" );
	sprintf( ymaxstr, "%d", PL_ncats('y')+1 );
	}

if( locspec ) { /* location and box specified, calculate xhi and yhi.. */
	xhi = xlo + width;
	yhi = ylo + height;
	}

/* determine area.. */
if( namedarea ) {
	RETRY:
	strcat( areaname, " " );
	for( i = 0; i < nareas; i++ ) {
		if( strnicmp( areas[i], areaname, strlen( areaname ) )==0 ) {
			if( Ecurpaper == 0 ) sscanf( areas[i], "%*s %lf %lf %lf %lf", &xlo, &ylo, &xhi, &yhi );
			else if( Ecurpaper == 1 ) sscanf( areas[i], "%*s %*s %*s %*s %*s %lf %lf %lf %lf", &xlo, &ylo, &xhi, &yhi );
			break;
			}
		}
	if( i == nareas ) {
		areaname[ strlen( areaname ) -1 ] = '\0';
		Eerr( 110, "warning, areaname not recognized, using 'standard'..", areaname );
		strcpy( areaname, "standard" );
		goto RETRY;
		}
	}
		
if( !gotarea && !locspec ) {  /* && !locspec added scg 11/21/00 */
	PLS.skipout = 1;
	return( Eerr( 130, "No plotting area has been specified", "" ) );
	}
if( !gotxrange ) {
	PLS.skipout = 1;
	return( Eerr( 130, "No xrange has been specified", "" ) );
	}
if( !gotyrange ) {
	PLS.skipout = 1;
	return( Eerr( 130, "No yrange has been specified", "" ) );
	}

/* set scaling type and special units if any.. */
stat = Escaletype( xscaletype, 'x' );
if( stat != 0 ) Escaletype( "linear", 'x' );
if( strnicmp( xscaletype, "log", 3 )==0 ) Esetunits( 'x', "linear" );
else Esetunits( 'x', xscaletype );

stat = Escaletype( yscaletype, 'y' );
if( stat != 0 ) Escaletype( "linear", 'y' );
if( strnicmp( yscaletype, "log", 3 )==0 ) Esetunits( 'y', "linear" );
else Esetunits( 'y', yscaletype );


/* if autoranging is specified, do it now.. */
if( strnicmp( xminstr, "datafield", 9 ) == 0 ) PLP_autorange( 'x', xminstr, xminstr, xmaxstr );
if( strnicmp( yminstr, "datafield", 9 ) == 0 ) PLP_autorange( 'y', yminstr, yminstr, ymaxstr );

/* if scaletype is log but there are data values = 0.0, go to log+1 scaling.. */
if( stricmp( xscaletype, "log" )==0 && atof( xminstr ) <= 0.0 ) Escaletype( "log+1", 'x' );
if( stricmp( yscaletype, "log" )==0 && atof( yminstr ) <= 0.0 ) Escaletype( "log+1", 'y' );

/* if using autowidth or autoheight, revise plotting area now.. */
if( autowf != 0.0 ) {
	double xmin, xmax;
	if( stricmp( xscaletype, "categories" )== 0 ) { 
		xmin = atof( xminstr );
		xmax = atof( xmaxstr );
		}
	else	{
		xmin = Econv( 'x', xminstr );
		xmax = Econv( 'x', xmaxstr );
		}
	xhi = xlo + ((xmax-xmin) * autowf);
	if( xhi - xlo < autowmin ) xhi = xlo + autowmin;
	else if( xhi - xlo > autowmax ) xhi = xlo + autowmax;
	}
if( autohf != 0.0 ) {
	double ymin, ymax;
	if( stricmp( yscaletype, "categories" )== 0 ) {
		ymin = atof( yminstr );
		ymax = atof( ymaxstr );
		}
	else	{
		ymin = Econv( 'y', yminstr );
		ymax = Econv( 'y', ymaxstr );
		}
	yhi = ylo + ((ymax-ymin) * autohf);
	if( yhi - ylo < autohmin ) yhi = ylo + autohmin;
	else if( yhi - ylo > autohmax ) yhi = ylo + autohmax;
	}

	


/* set the scaling for the plot area.. */
stat = Esetscale( 'x', xlo, xhi, xminstr, xmaxstr );
stat += Esetscale( 'y', ylo, yhi, yminstr, ymaxstr );

if( PLS.debug ) fprintf( PLS.diagfp, "areadef: lowerleft: %g,%g  upperright: %g,%g\n",
		xlo, ylo, xhi, yhi );
if( PLS.debug ) fprintf( PLS.diagfp, "areadef:   xrange is %s to %s.   yrange is %s to %s.\n",
		xminstr, xmaxstr, yminstr, ymaxstr );

if( stat != 0 ) return( stat );
	
DT_suppress_twin_warn( 0 );


/* set variables to hold plot area bounds and scale min/max.. */
if( PLS.usingcm ) cmadj = 2.54;
else cmadj = 1.0;
setfloatvar( "AREATOP", EYhi * cmadj );
setfloatvar( "AREABOTTOM", EYlo * cmadj );
setfloatvar( "AREALEFT", EXlo * cmadj );
setfloatvar( "AREARIGHT", EXhi * cmadj );
setcharvar( "XMIN", xminstr );
setcharvar( "XMAX", xmaxstr );
setcharvar( "YMIN", yminstr );
setcharvar( "YMAX", ymaxstr );


if( areacolor[0] != '\0' ) Ecblock( EXlo, EYlo, EXhi, EYhi, areacolor, 0 );
if( clickmap_on ) {
	if( GL_slmember( mapurl, "*@XVAL*" ) || GL_slmember( mapurl, "*@YVAL*" )) clickmap_seturlt( mapurl );
	else clickmap_entry( 'r', mapurl, 0, EXlo, EYlo, EXhi, EYhi, 0, 0, maplabel );
	}

/* draw a frame of the graphics area */
if( doframe ) {
	if( stricmp( frame, "bevel" )==0 ) {
		Ecblockdress( EXlo, EYlo, EXhi, EYhi, 0.1, "0.6", "0.8", 0.0, "" );
		}
	else	{
		linedet( "frame", frame, 1.0 );
		Ecblock( EXlo, EYlo, EXhi, EYhi, "", 1 );
		}
	}



/* draw bottom line */
if( linebottom[0] != '\0' ) {
	linedet( "linebottom", linebottom, 1.0 );
	Emov( EXlo, EYlo );
	Elin( EXhi, EYlo );
	}

/* draw side line */
if( lineside[0] != '\0' ) {
	linedet( "lineside", lineside, 1.0 );
	Emov( EXlo, EYlo );
	Elin( EXlo, EYhi );
	}

/* title */
if( title[0] != '\0' ) {
	textdet( "titledetails", titledet, &align, &adjx, &adjy, 0, "R", 1.0 );
	measuretext( title, &nlines, &maxlen );
	if( align == '?' ) align = 'L';
	if( align == 'L' ) Emov( EXlo + adjx, 
				EYhi + ((nlines-1) * Ecurtextheight ) + adjy + 0.02 );
	else if( align == 'C' ) 
		Emov( EXlo+((EXhi-EXlo) / 2.0 ) + adjx, 
			EYhi + ((nlines-1) * Ecurtextheight ) +adjy + 0.02 );
	else if( align == 'R' ) 
		Emov( EXhi + adjx, EYhi + ((nlines-1) * Ecurtextheight ) +adjy + 0.02);
	Edotext( title, align );
	}
/* title 2 */
if( title2[0] != '\0' ) {
	textdet( "title2details", title2det, &align, &adjx, &adjy, 0, "R", 1.0 );
	measuretext( title2, &nlines, &maxlen );
	if( align == '?' ) align = 'L';
	if( align == 'L' ) Emov( EXlo + adjx, 
		EYhi + ((nlines-1) * Ecurtextheight ) + adjy + 0.02 );
	else if( align == 'C' ) 
		Emov( EXlo+((EXhi-EXlo) / 2.0 ) + adjx, 
			EYhi + ((nlines-1) * Ecurtextheight ) +adjy + 0.02 );
	else if( align == 'R' ) Emov( EXhi + adjx, 
		EYhi + ((nlines-1) * Ecurtextheight ) +adjy + 0.02 );
	Edotext( title2, align );
	}


if( doxaxis ) PLP_axis( 'x', 6 );

if( doyaxis ) PLP_axis( 'y', 6 );

return( 0 );
}

/* ======================================================= *
 * Copyright 1998-2005 Stephen C. Grubb                    *
 * http://ploticus.sourceforge.net                         *
 * Covered by GPL; see the file ./Copyright for details.   *
 * ======================================================= */
