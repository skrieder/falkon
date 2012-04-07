/* ======================================================= *
 * Copyright 1998-2005 Stephen C. Grubb                    *
 * http://ploticus.sourceforge.net                         *
 * Covered by GPL; see the file ./Copyright for details.   *
 * ======================================================= */

/* PROC ANNOTATE - arbitrary placement of text, arrow, etc. */

#include "pl.h"

/* these statics are here to share values with calc_arrow() */
static double ahx, ahy, atx, aty, ahsize;
static double ah2x, ah2y, at2x, at2y;
static double boxw, boxh, ulx, uly;
static int arrowh, arrowt, arrow2h, arrow2t;
static int do_arrows(), calc_arrow();
static char arrowdet[256];

int
PLP_annotate()
{
char attr[NAMEMAXLEN], val[256];
char *line, *lineval;
int nt, lvp;
int first;

int align;
double adjx, adjy;
double x, y;
char textdetails[256];
char fromfile[256];
int fromfilemode;
int nlines, maxlen;
char box[256];
char backcolor[COLORLEN];
int verttext;
double bm;
char mapurl[MAXPATH];
double bevelsize, shadowsize;
char lowbevelcolor[COLORLEN], hibevelcolor[COLORLEN], shadowcolor[COLORLEN];
int ioutline;
char maplabel[MAXTT];
int clickmap_on;
int do_ellipse;
double bd_1, bd_2, bd_3, bd_4;
int backdim;
double cx, cy, px, py;
int clip;


TDH_errprog( "pl proc annotate" );

/* initialize */
strcpy( PL_bigbuf, "" );
strcpy( textdetails, "" );
strcpy( fromfile, "" );
fromfilemode = 0;
x = 3.0;
y = 3.0;
arrowh = arrowt = 0;
arrow2h = arrow2t = 0;
strcpy( arrowdet, "" );
ahsize = 0.1;
strcpy( box, "" );
strcpy( backcolor, "" );
verttext = 0;
bm = 0.0;
strcpy( mapurl, "" );
bevelsize = 0.0;
shadowsize = 0.0;
strcpy( lowbevelcolor, "0.6" );
strcpy( hibevelcolor, "0.8" );
strcpy( shadowcolor, "black" );
strcpy( maplabel, "" );
clickmap_on = 0;
do_ellipse = 0;
backdim = 0;
clip = 0;




/* get attributes.. */
first = 1;
while( 1 ) {
	line = getnextattr( first, attr, val, &lvp, &nt );
	if( line == NULL ) break;
	first = 0;
	lineval = &line[lvp];


	if( stricmp( attr, "location" )==0 ) {
		getcoords( "location", lineval, &x, &y );
		}
	else if( stricmp( attr, "text" )==0 ) 
		getmultiline( "text", lineval, MAXBIGBUF, PL_bigbuf );

	else if( stricmp( attr, "textdetails" )==0 ) strcpy( textdetails, lineval );
	else if( stricmp( attr, "fromfile" )==0 ) {
		strcpy( fromfile, lineval );
		fromfilemode = 1;
		}
	else if( stricmp( attr, "fromcommand" )==0 ) {
		strcpy( fromfile, lineval );
		fromfilemode = 2;
		}

	else if( stricmp( attr, "arrowhead" )==0 ) {
		getcoords( "arrowhead", lineval, &ahx, &ahy );
		arrowh = 1;
		}
	else if( stricmp( attr, "arrowtail" )==0 ) {
		getcoords( "arrowtail", lineval, &atx, &aty );
		arrowt = 1;
		}
	else if( stricmp( attr, "arrowhead2" )==0 ) {
		getcoords( "arrowhead2", lineval, &ah2x, &ah2y );
		arrow2h = 1;
		}
	else if( stricmp( attr, "arrowtail2" )==0 ) {
		getcoords( "arrowtail2", lineval, &at2x, &at2y );
		arrow2t = 1;
		}
	else if( stricmp( attr, "arrowdetails" )==0 ) strcpy( arrowdet, lineval );
	else if( stricmp( attr, "arrowheadsize" )==0 ) {
		ahsize = atof( val );
		if( ahsize <= 0.0 ) ahsize = 0.0; /* no arrow */
		if( PLS.usingcm ) ahsize /= 2.54;
		}
	else if( stricmp( attr, "box" )==0 || stricmp( attr, "outline" )==0 ) strcpy( box, lineval );
	else if( stricmp( attr, "ellipse" )==0 ) {
		if( strnicmp( val, YESANS, 1 )==0 ) do_ellipse = 1;
		else do_ellipse = 0;
		}
	else if( stricmp( attr, "clickmapurl" )==0 ) {
		if( PLS.clickmap ) { strcpy( mapurl, val ); clickmap_on = 1; }
		}
	else if( stricmp( attr, "clickmaplabel" )==0 ) {
		if( PLS.clickmap ) { strcpy( maplabel, lineval ); clickmap_on = 1; }
		}
        else if( stricmp( attr, "clickmaplabeltext" )==0 ) {
                if( PLS.clickmap ) { getmultiline( "clickmaplabeltext", lineval, MAXTT, maplabel ); clickmap_on = 1; }
                }

	else if( stricmp( attr, "boxmargin" )==0 ) {
		bm = atof( val );
		if( PLS.usingcm ) bm /= 2.54;
		}
	else if( stricmp( attr, "verttext" )==0 ) {
		if( strnicmp( val, YESANS, 1 )==0 ) verttext = 1;
		else verttext = 0;
		}
	else if( stricmp( attr, "backcolor" )==0 ) strcpy( backcolor, val );
	else if( stricmp( attr, "bevelsize" )==0 ) bevelsize = atof( val );
        else if( stricmp( attr, "shadowsize" )==0 ) shadowsize = atof( val );
        else if( stricmp( attr, "lowbevelcolor" )==0 ) strcpy( lowbevelcolor, val );
        else if( stricmp( attr, "hibevelcolor" )==0 ) strcpy( hibevelcolor, val );
        else if( stricmp( attr, "shadowcolor" )==0 ) strcpy( shadowcolor, val );
	else if( stricmp( attr, "backdim" )==0 ) {
		PL_getbox( "backdim", lineval, &bd_1, &bd_2, &bd_3, &bd_4 );
		backdim = 1;
		}
        else if( stricmp( attr, "clip" )==0 ) {
		if( strnicmp( val, YESANS, 1 )==0 ) clip = 1;
		else clip = 0;
		}
	else Eerr( 1, "attribute not recognized", attr );
	}


if( fromfilemode > 0 ) file_to_buf( fromfile, fromfilemode, PL_bigbuf, MAXBIGBUF );

textdet( "textdetails", textdetails, &align, &adjx, &adjy, 0, "R", 1.0 );
if( align == '?' ) align = 'C';

px = x + adjx;
py = y + adjy;

/* figure backing box */
measuretext( PL_bigbuf, &nlines, &maxlen );
boxw = (maxlen+2) * Ecurtextwidth;
boxh = (nlines*1.2) * Ecurtextheight;
uly = py + Ecurtextheight;
if( align == 'C' ) ulx = px - (boxw/2.0); 
else if( align == 'L' ) ulx = px;
else if( align == 'R' ) ulx = px - boxw;


if( bm != 0.0 ) {
	ulx -= bm;
	uly += bm;
	boxw += (bm*2);
	boxh += (bm*2);
	}

if( clip ) PLG_pcodeboundingbox( 0 ); /* clip the annotation to the cropped size (by turning off bb) */

if( backcolor[0] != '\0' || ( box[0] != '\0' && strnicmp( box, "no", 2 )!= 0 ) ) {
	if( box[0] != '\0' && strnicmp( box, "no", 2 )!= 0 ) {
		ioutline = 1;
		linedet( "box", box, 0.5 );
		}
	else ioutline = 0;
	if( do_ellipse ) {
		cx = ulx+(boxw/2.0);
		cy = uly-(boxh/2.0);
		if( backdim ) { cx = bd_1; cy = bd_2; boxw = bd_3; boxh = bd_4; }
		do_arrows(); /* do this before ellipse to get abutting edge */
		PLG_ellipse( cx, cy, ((boxw/2.0)*1.3), ((boxh/2.0)*1.3), backcolor, ioutline );
		}
	else	{
		if( backdim ) { ulx = bd_1; uly = bd_2; boxw = bd_3; boxh = bd_4; }
		do_arrows(); /* do this before fill to get abutting edge */
		Ecblock( ulx, (uly-boxh), ulx+boxw, uly, backcolor, ioutline );
		if( bevelsize > 0.0 || shadowsize > 0.0 ) 
			Ecblockdress( ulx, (uly-boxh), ulx+boxw, uly, bevelsize, lowbevelcolor, hibevelcolor, shadowsize, shadowcolor);
		}
	}
else do_arrows();

if( clickmap_on ) {
	if( backdim && do_ellipse ) {
		/* need to solve back to ulx,uly in this case.. */
		ulx = (((boxw/2.0)-cx)*-1.0); 
		uly = (((boxw/2.0)-cy)*-1.0) + boxh; 
		}
	clickmap_entry( 'r', mapurl, 0, ulx, (uly-boxh), ulx+boxw, uly, 1, 0, maplabel );
	}

/* now render the text.. */
textdet( "textdetails", textdetails, &align, &adjx, &adjy, 0, "R", 1.0 ); /* need to do again */
if( align == '?' ) align = 'C';
Emov( x + adjx, y + adjy );
if( verttext ) Etextdir( 90 );
Edotext( PL_bigbuf, align );
if( verttext ) Etextdir( 0 );

if( clip ) PLG_pcodeboundingbox( 1 ); /* restore */

return( 0 );
}

/* ==================== */
/* do arrows */
static int do_arrows()
{

/* if tail location not given, try to be smart about arrow placement.. */

if( arrowh ) {
	linedet( "arrowdetails", arrowdet, 0.7 );
	if( !arrowt ) calc_arrow( ulx, uly, boxw, boxh, ahx, ahy, &atx, &aty );
	Earrow( atx, aty, ahx, ahy, ahsize, 0.4, "" );
	}

/* and render 2nd arrow.. */
if( arrow2h ) {
	if( !arrow2t ) calc_arrow( ulx, uly, boxw, boxh, ah2x, ah2y, &at2x, &at2y );
	Earrow( at2x, at2y, ah2x, ah2y, ahsize, 0.4, "" );
	}
return( 0 );
}

/* ================== */
/* figure where tail of arrow should be */
static int
calc_arrow( ulx, uly, boxw, boxh, ahx, ahy, tailx, taily )
double ulx, uly, boxw, boxh, ahx, ahy, *tailx, *taily;
{
double atx, aty;

/* ah directly above or below textbox.. straight arrow.. */
if( ahx >= ulx && ahx <= ulx+boxw ) {
	atx = ahx;
	if( ahy > uly ) aty = uly;
	else if( ahy < uly-boxh ) aty = uly-boxh;
	}

/* ah directly beside textbox.. straight arrow.. */
else if( ahy >= uly-boxh && ahy <= uly ) {
	aty = ahy;
	if( ahx < ulx ) atx = ulx;
	else if( ahx > ulx+boxw ) atx = ulx+boxw;
	} 

/* ah somewhere to left of textbox.. elbow arrow from mid-box.. */
else if( ahx < ulx ) {
	atx = ahx + ((ulx - ahx)/2.0);
	aty = uly - (boxh/2.0);
	Emov( ulx, aty ); 
	Elin( atx, aty );
	}

/* ah somewhere to right of textbox.. elbow arrow from mid-box.. */
else if( ahx > ulx+boxw ) {
	atx = ulx+boxw + ((ahx-(ulx+boxw))/2.0);
	aty = uly - (boxh/2.0);
	Emov( ulx+boxw, aty ); 
	Elin( atx, aty );
	}

else { atx = ahx; aty = ahy; } /* ? */

*tailx = atx;
*taily = aty;
return( 0 );
}

/* ======================================================= *
 * Copyright 1998-2005 Stephen C. Grubb                    *
 * http://ploticus.sourceforge.net                         *
 * Covered by GPL; see the file ./Copyright for details.   *
 * ======================================================= */
