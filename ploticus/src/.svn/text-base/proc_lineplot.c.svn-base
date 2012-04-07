/* ======================================================= *
 * Copyright 1998-2005 Stephen C. Grubb                    *
 * http://ploticus.sourceforge.net                         *
 * Covered by GPL; see the file ./Copyright for details.   *
 * ======================================================= */

/* PROC LINEPLOT - draw a lineplot */

/* Jan 15 01 - went from dat2d to dat3d so that original data row is preserved */

#include "pl.h"
#define MAXALT 200
#define MOVE 0
#define LINE 1
#define PATH 2


static int dblcompare( const void *a, const void *b );
/* static int dblcompare( double *f, double *g ); */
static int placenum();

int
PLP_lineplot()
{
int i;
char attr[NAMEMAXLEN], val[256];
char *line, *lineval;
int nt, lvp;
int first;

char buf[256];
int stat;
int align;
double adjx, adjy;

int yfield;
int xfield;
char linedetails[256];
double linestart, linestop; 
int j, k;
int accum;
double x, y;
int stairstep;
double lastseglen;
char label[200];
char labeldetails[256];
double lastx, lasty;
char numstr[40];
char shownums[80];
char numstrfmt[20];
int donumbers;
char pointsym[256];
int dopoints;
char symcode[80];
double radius;
int ptlabelfield;
char ptlabeldetails[256];
double ptlblstart, ptlblstop;
double sob;
double linxstart;
char fillcolor[COLORLEN];
char legendlabel[256]; /* raised (can contain urls for clickmap) scg 4/22/04 */
int npoints;
double f, sum, cr;
int instancemode;
int groupmode;
char selectex[256];
int result;
char forcelastx[50];
char legsamptyp[20];
char altsym[120];
char altwhen[256];
int nalt;
int altlist[MAXALT+2];
int anyvalid;
int realrow;
int gapmissing, ingap;
int clipping;
int firstpt;
double firstx, firsty;
int sortopt;
int relax_xrange;
int fillmode;
/* char oldcolor[COLORLEN]; */
double typical_interval;

TDH_errprog( "pl proc lineplot" );

/* initialize */
yfield = -1;
xfield = -1;
linestart = EDXlo; linestop = EDXhi;
ptlblstart = EDXlo; ptlblstop = EDXhi;
accum = 0;
stairstep = 0;
lastseglen = 0.0;
strcpy( label, "" );
strcpy( labeldetails, "" );
strcpy( shownums, "" );
strcpy( numstrfmt, "%g" );
strcpy( pointsym, "" );
ptlabelfield = 0;
strcpy( ptlabeldetails, "" );
strcpy( linedetails, "" );
sob = 0.0;
linxstart = EDXlo;
fillmode = 0;
strcpy( fillcolor, "gray(0.8)" );
strcpy( legendlabel, "" );
instancemode = 0;
groupmode = 0; /* 1? */
strcpy( selectex, "" );
strcpy( forcelastx, "" );
strcpy( legsamptyp, "symbol" );
strcpy( altsym, "" );
strcpy( altwhen, "" );
gapmissing = 0;
clipping = 0;
firstpt = 0;
sortopt = 0;
relax_xrange = 0;
typical_interval = -99.0;


/* get attributes.. */
first = 1;
while( 1 ) {
	line = getnextattr( first, attr, val, &lvp, &nt );
	if( line == NULL ) break;
	first = 0;
	lineval = &line[lvp];

	if( stricmp( attr, "yfield" )==0 ) yfield = fref( val ) - 1;
	else if( stricmp( attr, "xfield" )==0 ) xfield = fref( val ) - 1;
	else if( stricmp( attr, "linedetails" )==0 ) {
		strcpy( linedetails, lineval );
		}
	else if( stricmp( attr, "label" )==0 ) {
		strcpy( label, lineval );
		convertnl( label );
		}
	else if( stricmp( attr, "labeldetails" )==0 ) strcpy( labeldetails, lineval );
	else if( stricmp( attr, "legendlabel" )==0 ) strcpy( legendlabel, lineval );
	else if( stricmp( attr, "linerange" )==0 ) {
		getrange( lineval, &linestart, &linestop, 'x', EDXlo, EDXhi );
		}
	else if( stricmp( attr, "xstart" )==0 ) {
		linxstart = Econv( X, val );
		if( Econv_error() ) linxstart = EDXlo;
		}
	else if( stricmp( attr, "firstpoint" )==0 ) {
		int ix;
		ix = 0;
		firstpt = 1;
		strcpy( buf, GL_getok( lineval, &ix ));
		firstx = Econv( X, buf );
		if( Econv_error() ) firstpt = 0;
		strcpy( buf, GL_getok( lineval, &ix ));
		firsty = Econv( X, buf );
		if( Econv_error() ) firstpt = 0;
		}
	else if( stricmp( attr, "accum" )==0 ) {
		if( strnicmp( val, YESANS, 1 )==0 ) accum = 1;
		else accum = 0;
		}
	else if( stricmp( attr, "stairstep" )==0 ) {
		if( strnicmp( val, YESANS, 1 )==0 ) stairstep = 1;
		else stairstep = 0;
		}
	else if( stricmp( attr, "instancemode" )==0 ) {
		if( strnicmp( val, YESANS, 1 )==0 ) instancemode = 1;
		else instancemode = 0;
		}
	else if( stricmp( attr, "groupmode" )==0 ) {
		if( strnicmp( val, YESANS, 1 )==0 ) groupmode = 1;
		else groupmode = 0;
		}
	else if( stricmp( attr, "gapmissing" )==0 ) {
		if( strnicmp( val, YESANS, 1 )==0 ) gapmissing = 1;
		else if( stricmp( val, "small" )==0 ) gapmissing = 2;
		else if( stricmp( val, "auto" )==0 ) gapmissing = 3;
		else if( stricmp( val, "autosmall" )==0 ) gapmissing = 4;
		else if( stricmp( val, "autozero" )==0 ) gapmissing = 5;
		else gapmissing = 0;
		}
	else if( stricmp( attr, "clip" )==0 ) {
		if( strnicmp( val, YESANS, 1 )==0 ) clipping = 1;
		else clipping = 0;
		}
	else if( stricmp( attr, "sort" )==0 ) {
		if( strnicmp( val, YESANS, 1 )==0 ) sortopt = 1;
		else sortopt = 0;
		}
	else if( stricmp( attr, "relax_xrange" ) == 0 ) {
		if( strnicmp( val, YESANS, 1 )==0 ) relax_xrange = 1;
		else relax_xrange = 0;
		}

	else if( stricmp( attr, "lastseglen" )==0 ) Elenex( val, X, &lastseglen );

	else if( stricmp( attr, "numbers" )==0 ) strcpy( shownums, lineval );
	else if( stricmp( attr, "numbersformat" )==0 ) strcpy( numstrfmt, val );
	else if( stricmp( attr, "select" )==0 ) strcpy( selectex, lineval );
	else if( stricmp( attr, "altsymbol" )==0 ) strcpy( altsym, lineval );
	else if( stricmp( attr, "altwhen" )==0 ) strcpy( altwhen, lineval );
	else if( stricmp( attr, "lastx" )==0 ) strcpy( forcelastx, val );
	else if( stricmp( attr, "pointsymbol" )==0 ) strcpy( pointsym, lineval );
	/* else if( stricmp( attr, "ptlabelfield" )==0 ) ptlabelfield = atoi( val ) - 1; */
	else if( stricmp( attr, "ptlabelfield" )==0 ) ptlabelfield = fref( val ) - 1; 
	else if( stricmp( attr, "ptlabeldetails" )==0 ) strcpy( ptlabeldetails, lineval );
	else if( stricmp( attr, "ptlabelrange" )==0 ) {
		getrange( lineval, &ptlblstart, &ptlblstop, 'x', EDXlo, EDXhi );
		}
	else if( stricmp( attr, "stairoverbars" )==0 ) {
		if( strnicmp( val, YESANS, 1 )==0 ) {
			sob = 0.5;
			stairstep = 1; /* implied */
			}
		else sob = 0.0;
		}
	else if( stricmp( attr, "fill" )==0 ) { 
		strcpy( fillcolor, val );
		if( fillcolor[0] != '\0' ) fillmode = 1;
		else fillmode = 0;
		}
	else if( stricmp( attr, "legendsampletype" )==0 ) strcpy( legsamptyp, val );

	else Eerr( 1, "lineplot attribute not recognized", attr );
	}


/* overrides and degenerate cases */
/* -------------------------- */
if( Nrecords < 1 ) return( Eerr( 17, "No data has been read yet w/ proc getdata", "" ) );
if( !scalebeenset() ) 
         return( Eerr( 51, "No scaled plotting area has been defined yet w/ proc areadef", "" ) );


if( (yfield < 0 || yfield >= Nfields ) && !instancemode ) return( Eerr( 601, "yfield out of range", "" ) );
if( xfield >= Nfields ) return( Eerr( 601, "xfield out of range", "" ) );
if( yfield >= 0 && instancemode ) {
	Eerr( 4729, "warning, turning instancemode off since yfield specified", "" );
	instancemode = 0;
	}
 
if( groupmode && ptlabelfield ) {
	Eerr( 4729, "warning, turning ptlabelfield off since groupmode specified", "" );
	ptlabelfield = 0;
	}
	
if( strnicmp( legendlabel, "#usefname", 9 )==0 ) {
	if( instancemode ) getfname( xfield+1, legendlabel );
	else getfname( yfield+1, legendlabel );
	}



/* now do the plotting work.. */
/* -------------------------- */

donumbers = 1;
if( GL_slmember( shownums, "no*" ) || shownums[0] == '\0' ) donumbers = 0;

dopoints = 1;
if( GL_slmember( pointsym, "no*" ) || pointsym[0] == '\0' ) dopoints = 0;


/* put all values into PLV vector, doing accumulation if required.. */
j = 0;
f = linxstart;
nalt = 0;

for( i = 0; i < Nrecords; i++ ) {

	if( selectex[0] != '\0' ) { /* process against selection condition if any.. */
                stat = do_select( selectex, i, &result );
                if( stat != 0 ) { Eerr( stat, "Select error", selectex ); continue; }
                if( result == 0 ) continue; /* reject */
                }
	if( altwhen[0] != '\0' ) { /* check altwhen condition.. */
                stat = do_select( altwhen, i, &result );
                if( stat != 0 ) { Eerr( stat, "Select error", altwhen ); continue; }
                if( result == 1 && nalt < MAXALT ) {
			/* altlist[nalt] = j/2; */
			altlist[nalt] = j/3;
			nalt++;
			}
                }
		


	/* X */
	if( xfield < 0 ) {
		PLV[j] = f + sob;
		f += 1.0;
		}
	else 	{
		PLV[j] = fda( i, xfield, X ) + sob;
		if( Econv_error() ) { 
			conv_msg( i, xfield, "xfield" ); 
			continue;
			}
		}

	j++; 

	/* Y */
	if( instancemode ) PLV[j] = 1.0;
	else 	{
		PLV[j] = fda( i, yfield, Y );
		if( Econv_error() ) { 
			conv_msg( i, yfield, "yfield" ); 
			PLV[j] = NEGHUGE;
			/* continue; removed scg 5/19/99 */
			}
		}
	j++;

	PLV[j] = (double)i;
	j++;

	if( j >= PLVsize-1 ) {
		Eerr( 3579, "Sorry, too many curve points, curve truncated (raise using -maxvector)", "" );
		break;
		}
	}

npoints = j / 3;


/* sort if required.. */  /* added 4/22/02 */
if( sortopt ) {
        if( PLS.debug ) fprintf( PLS.diagfp, "sorting points for line\n" );
        qsort( PLV, npoints, sizeof(double)*3, dblcompare );
        }

/* fprintf( stderr, "after sort\n" );
 * for( i = 0; i < npoints; i++ ) fprintf( stderr, "%g %g %g\n", dat3d(i,0), dat3d(i,1), dat3d(i,2 ) );
 */

/* process for groupmode.. */
if( groupmode && xfield >= 0 ) for( i = 0; i < npoints; i++ ) {

	for( k = i+1; k < npoints; k++ ) {
                		
		if( dat3d(i,0) == dat3d(k,0) ) {
			if( instancemode ) y = 1.0;
			else y = dat3d( k, 1 );
			dat3d( k, 1 ) = NEGHUGE; /* rub out the additional instance.. */
			if( y > NEGHUGE+1 ) (dat3d( i, 1 )) += (y);
			}
		else 	{
			i = k-1;     /* back off.. */
			break;
			}
		}
	}
/* fprintf( stderr, "after grouping\n" );
 * for( i = 0; i < npoints; i++ ) fprintf( stderr, "%g %g %g\n", dat3d(i,0), dat3d(i,1), dat3d(i,2 ) );
 */

/* process for accum.. */
if( accum ) {
	sum = 0.0;
	for( i = 0; i < npoints; i++ ) {

		if( dat3d( i, 1 ) > (NEGHUGE+1) ) {
			sum += (dat3d( i, 1 )); 
			(dat3d( i, 1 )) = sum;
			}
		}
   	}

/* fprintf( stderr, "after accum\n" );
 * for( i = 0; i < npoints; i++ ) fprintf( stderr, "%g %g %g\n", dat3d(i,0), dat3d(i,1), dat3d(i,2 ) );
 */


/* draw the curve.. */
/* ---------------- */

/* set line parameters */
linedet( "linedetails", linedetails, 1.0 );

if( fillmode ) {
	/* strcpy( oldcolor, Ecurcolor ); */
	Ecolor( fillcolor ); /* scg 6/18/04 */
	}
	

first = 1;
lasty = 0.0;
lastx = 0.0;
anyvalid = 0;
ingap = 0;
cr = Elimit( Y, 'l', 's' );
for( i = 0; i < npoints; i++ ) {
	if( !first && (y > (NEGHUGE+1) && x > (NEGHUGE+1) ) ) { lasty = y; lastx = x; }

	if( first && firstpt ) { 
		x = firstx; 
		y = firsty; 
		}
	else	{
		x = dat3d(i,0);
		y = dat3d(i,1);
		}
	
	if( x < (NEGHUGE+1) || y < (NEGHUGE+1) ) {
		if( gapmissing ) ingap = 1;
		continue; /* skip bad values */
		}
	
	if( x < linestart && !relax_xrange ) continue; /* X out of range - lo */ 
	if( x > linestop && !relax_xrange ) {          /* X out of range - hi */ 
		x = lastx; /* back up to last in-range point so last stairtstep is correct*/
		y = lasty;
		break; 
		}
	if( !first && ( gapmissing == 3 || gapmissing == 4 || gapmissing == 5 )) {
		if( typical_interval < 0.0 ) typical_interval = (x - lastx)*1.01;
		else if( x - lastx > typical_interval ) {
			if( gapmissing == 5 ) { Elinu( lastx+typical_interval, 0.0 ); Elinu( x-typical_interval, 0.0 ); }
			else ingap = 1;
			}
		}

	if( !anyvalid && !Ef_inr( Y, y ) ) continue; /* 1/9/03 scg - anyvalid should not become 1 if out of range in Y */

	anyvalid = 1;
	if( first ) {
		Emovu( x, y );
		setfloatvar( "XSTART", x );
		setfloatvar( "YSTART", y );
		first = 0;
		ingap = 0;
		continue;
		}
	if( !first && fillmode && !ingap ) {
		Emovu( x, cr ); Epathu( lastx, cr ); 
		/* if( stairstep ) Epathu( lastx, y ); 
		 * else Epathu( lastx, lasty ); 
		 */
		Epathu( lastx, lasty );
		if( stairstep ) Epathu( x, lasty );
		else Epathu( x, y );
		/* Ecolorfill( fillcolor ); */ /* using Efill .. scg 6/18/04 */
		Efill();
		}
	if( ( gapmissing == 2 || gapmissing ==4 ) && ingap ) {        /* do a quarter-length nib at previous location */
		double nib;
		Emovu( lastx, lasty );
		nib = (x-lastx) * 0.25;
		if( fillmode ) Ecblock( Eax(lastx), Eay(cr), Eax(lastx+nib), Eay(lasty), fillcolor, 0 ); 
		else Elinu( lastx+nib, lasty );
		}
	if( ! fillmode ) { 
		if( ingap ) Emovu( x, y ); 	
		if( stairstep && x > linestart && !ingap ) Elinu( x, lasty ); 
		if( stairstep && x == linestart ) Emovu( x, y ); 
		if( clipping && !ingap && !stairstep ) {
			double cx1, cy1, cx2, cy2;
			cx1 = lastx; cy1 = lasty; cx2 = x; cy2 = y;
			stat = Elineclip( &cx1, &cy1, &cx2, &cy2, EDXlo, EDYlo, EDXhi, EDYhi );
			if( !stat ) { Emovu( cx1, cy1 ); Elinu( cx2, cy2 ); }
			}
		else Elinu( x, y );
		}
	ingap = 0;
	}

if( !anyvalid ) { /* no plottable data points.. exit */  
	/* Ecolor( oldcolor ); */ /* don't do color chg - scg 5/10/05 */
	return( 0 ); 
	} 

/* if last point was invalid, back up to most recent valid point.. */
if( x < (NEGHUGE+1) || y < (NEGHUGE+1) ) { x = lastx; y = lasty; }


/* handle last segment of stairstep.. */
/* if( stairstep ) { */ /* } changed to allow lastseglen to be used anytime, scg 12/13/01 */
if( lastseglen > 0.0 ) {
	if( x < (NEGHUGE+1) || y < (NEGHUGE+1) ) { x = lastx; y = lasty; }
	lastx = Eax( x ) + lastseglen;
	if( fillmode ) {
		Emov( Eax(x), Eay(cr) ); Epath( lastx, Eay(cr) ); 
		Epath( lastx, Eay(y) ); Epath( Eax(x), Eay(y) );
		/* Ecolorfill( fillcolor ); */ /* using Efill .. scg 6/18/04 */
		Efill();
		}
	else 	Elin( lastx, Eay( y ) );
	}
else lastx = Eax(x);

if( forcelastx[0] != '\0' ) {
	lastx = Eax( Econv( X, forcelastx ) );
	if( !Econv_error() && anyvalid ) Elin( lastx, Eay( y ) );
	}


/* set YFINAL and Xfinal */
/* sprintf( numstr, numstrfmt, y ); */
Euprint( numstr, Y, y, numstrfmt );
setcharvar( "YFINAL", numstr );
Euprint( buf, X, Edx(lastx), "" );
setcharvar( "XFINAL", buf );
	





/* do points, labels, etc. */
/* ----------------------- */
for( i = 0; i < npoints; i++ ) {
	x = dat3d(i,0);
	y = dat3d(i,1);
	realrow = (int) dat3d(i,2);


	if( x < (NEGHUGE+1) || y < (NEGHUGE+1) ) continue; /* skip bad values */

	if( x < linestart && !relax_xrange ) continue; /* out of range - lo */
	if( x > linestop && !relax_xrange ) {          /* out of range - hi */
		break; 
		}

	if( clipping && !stairstep && !fillmode ) {
		/* if clipping, suppress points or labels that are outside the plotting area */
		if( x < EDXlo || x > EDXhi || y < EDYlo || y > EDYhi ) continue;
		}

	lasty = y;

	if( x >= ptlblstart && x <= ptlblstop ) {
		if( donumbers && stairstep ) {
			if( i == npoints-1 || GL_close_to( x, linestop, 0.01 ) ) {  
				double xls;
				xls = Edx(lastseglen) - Edx(0.0);
				placenum( shownums, x, x + xls, y, numstrfmt, linedetails );
				}
			else placenum( shownums, x, dat3d(i+1,0), y, numstrfmt, linedetails );
			}
		else if( donumbers && !stairstep ) 
			  placenum( shownums, x, x, y, numstrfmt, linedetails );
		if( donumbers && fillmode ) Ecolor( fillcolor ); /* scg 6/18/04 */
		}

	if( dopoints ) {
		int jj;
		/* see if this is one of the alternates.. if so use altsym rather
			than the regular symbol */
		for( jj = 0; jj < nalt; jj++ ) if( i == altlist[jj] ) break;
		if( jj != nalt ) symdet( "altsym", altsym, symcode, &radius );
		else symdet( "pointsymbol", pointsym, symcode, &radius );
		Emark( Eax(x), Eay(y), symcode, radius );
		}
	if( ptlabelfield && x >= ptlblstart && x <= ptlblstop ) {
		textdet( "ptlabeldetails", ptlabeldetails, &align, &adjx, &adjy, -4, "R", 1.0 );
		if( align == '?' ) align = 'C';
		Emov( Eax( x ) + adjx, Eay( y ) + adjy );
		Edotext( da( realrow, ptlabelfield ), align );
		}
	}


if( label[0] != '\0' ) {
        GL_varsub( label, "@YFINAL", numstr );
        textdet( "labeldetails", labeldetails, &align, &adjx, &adjy, -2, "R", 1.0 );
        if( align == '?' ) align = 'L';
        Emov( lastx+0.05+adjx, (Eay( lasty )-(Ecurtextheight*.35))+adjy );
        Edotext( label, align );
        }
 
if( legendlabel[0] != '\0' ) {
	if( fillmode ) PL_add_legent( LEGEND_COLOR, legendlabel, "", fillcolor, "", "" );
	else if( pointsym[0] != '\0' && stricmp( pointsym, "none" )!= 0 ) {
		if( tolower(legsamptyp[0]) == 's' && strlen( legsamptyp ) <= 6 )
			PL_add_legent( LEGEND_SYMBOL, legendlabel, "", pointsym, "", "" );
		else
			PL_add_legent( LEGEND_LINE+LEGEND_SYMBOL, legendlabel, "", linedetails, pointsym, "" );
		}
	else PL_add_legent( LEGEND_LINE, legendlabel, "", linedetails, "", "" );
	}

/* if( fillmode ) Ecolor( oldcolor ); */ /* restore */
return( 0 );
}

/* ----------------------- */
/* place one line plot number  */
static int
placenum( shownums, x1, x2, y, numstrfmt, linedetails )
char *shownums;
double x1, x2, y;
char *numstrfmt, *linedetails;
{
char numstr[20];
int align;
double adjx, adjy;

/* change to text color, size, etc.. */
textdet( "numbers", shownums, &align, &adjx, &adjy, -4, "R", 1.0 );
if( align == '?' ) align = 'C';
Emov( Eax( (x1+x2)/2.0 ) + adjx, Eay(y)+0.02+adjy );
/* sprintf( numstr, numstrfmt, y ); */
Euprint( numstr, 'y', y, numstrfmt );
Edotext( numstr, align );
linedet( "linedetails", linedetails, 1.0 );
Emovu( x1, y ); /* restore old position for drawing the curve.. */
return( 0 );
}

/* ------------------------- */
static int
dblcompare( a, b )
const void *a, *b;

/* dblcompare( f, g )
 * double *f, *g;
 */  /* changed to eliminate gcc warnings  scg 5/18/06 */

{
double *f, *g;
f = (double *)a;
g = (double *)b;

if( *f > *g ) return( 1 );
if( *f < *g ) return( -1 );
return( 0 );
}


/* ======================================================= *
 * Copyright 1998-2005 Stephen C. Grubb                    *
 * http://ploticus.sourceforge.net                         *
 * Covered by GPL; see the file ./Copyright for details.   *
 * ======================================================= */
