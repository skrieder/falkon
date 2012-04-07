/* ======================================================= *
 * Copyright 1998-2005 Stephen C. Grubb                    *
 * http://ploticus.sourceforge.net                         *
 * Covered by GPL; see the file ./Copyright for details.   *
 * ======================================================= */

/* PROC AXIS - Draw an X or Y axis.  astart parameter allows areadef xaxis.* or yaxis.* parameters */

/* Coded for Y axis.. axes are logically flipped when drawing an X axis */

#include "pl.h"

#define INCREMENTAL 1
#define HERE 2
#define FROMFILE 3
#define FROMDATA 4
#define FROMCATS 5
#define MONTHS 100

int
PLP_axis( xory, astart )
char xory; /* either 'x' or 'y' */
int astart;
{
int i;
char attr[NAMEMAXLEN], val[256];
char *line, *lineval;
int nt, lvp;
int first;

int stat;
int align;
double adjx, adjy;

char buf[256];
int j;
char opax;
double y;
double f;
double pos; /* distance of axis in absolute units from scale-0 */
double stubstart, stubstop; /* start and stop of stubs and tics in data units */
double inc;
char tics[256]; /* details of tic lines */
double ticin, ticout; /* length of tic into plot area and outward */
char mtics[256]; /* details of minor tic lines */
double minorticinc;	/* inc for minor tics */
double mticin, mticout; /* length of minor tic into plot area and outward */
char axisline[256]; /* axis line details */
char stubdetails[256];    /* stub text details and on/off */
char txt[256]; /* general */
char stubformat[80];	
int isrc;
FILE *stubfp;
double axlinestart, axlinestop;
double max, min;
char axislabel[300];
char axislabeldet[256];
double axislabelofs;
int stubreverse;
char grid[256];
int vertstub;
int irow;
double ofsx, ofsy;
char stubomit[256];
int mon, day, yr;

int stubdf1, stubdf2, nstubdf;
int selfloc;
char filename[256];
double incamount, ticincamount;
char incunits[50], ticincunits[50], minorticunits[50];
int ibb;
double overrun;
int bigbuflen;
int doingtics;
int stubrangegiven;
int stubreverse_given;
int specialunits;
int doinggrid;
double stubslide;
char scaleunits[30];
char gridskip[20];
char scalesubtype[20];
char autoyears[20];
int firsttime;
double ticslide;
int revsign;
char glemins[40], glemaxs[40];
double glemin, glemax;
char gbcolor1[COLORLEN], gbcolor2[COLORLEN];
double gbylast;
int gbstate;
double stubcull, prevstub;
int stubevery;
int forlen;
char clickmap;
double cmylast, cmemin, cmemax;
char cmemins[40], cmemaxs[40];
char cmvalfmt[80], cmtxt[100];
int logx, logy, stubexp;
int stublen;
char autodays[40], automonths[40];
char labelurl[256], labelinfo[MAXTT];
double stubmult;
double stubmininc;
char firststub[80], laststub[80];
char stubsubpat[80], stubsubnew[80];
int sanecount, stubhide;
int curyr, curday, curmon;
int circuit_breaker_disable;
int axis_arrow;
double arrowheadsize;
char nearest[30];
char stubround[30];
int prec;

TDH_errprog( "pl proc axis" );

if( xory == 'x' ) opax = 'y';
else if( xory == 'y' ) opax = 'x';
else { Eerr( 301, "axis: bad xory", "" ); xory = 'x'; opax = 'y'; }



/* initialize */
min = Elimit( xory, 'l', 's' );
max = Elimit( xory, 'h', 's' );
pos = Elimit( opax, 'l', 'a' ); /* location will be at minima of other axis */
stubstart = axlinestart = min; 
stubstop = axlinestop = max; 
ticin = 0;
ticout = 0.07;
strcpy( axisline, "" );
strcpy( tics, "" );
strcpy( mtics, "none" );
minorticinc = 0.0;
mticin = 0;
mticout = 0.03;
strcpy( stubformat, "" );
strcpy( PL_bigbuf, "" );
strcpy( stubdetails, "" );
strcpy( axislabel, "" );
strcpy( axislabeldet, "" );
axislabelofs = 0.4;
stubreverse = 0;
strcpy( grid, "none" );
/* isrc = INCREMENTAL; */
isrc = 0;
incamount = 0.0;
ticincamount = 0.0;
vertstub = 0;
nstubdf = 0;
strcpy( stubomit, "" );
doingtics = 1;
selfloc = 0;
stubrangegiven = 0;
stubreverse_given = 0;
doinggrid = 0;
stubslide = 0.0;
ticslide = 0.0;
strcpy( gridskip, "" );
strcpy( autoyears, "" );
revsign = 0;
strcpy( glemins, "min" ); strcpy( glemaxs, "max" );
strcpy( gbcolor1, "" );
strcpy( gbcolor2, "white" );
stubcull = 0.0;
stubevery = 1;
clickmap = 0;
strcpy( cmemins, "min" ); strcpy( cmemaxs, "max" );
strcpy( cmvalfmt, "" );
stubexp = 0; stublen = 0;
strcpy( autodays, "" ); strcpy( automonths, "" );
strcpy( labelurl, "" ); strcpy( labelinfo, "" );
stubmult = 1.0; stubmininc = 0.0;
strcpy( firststub, "" ); strcpy( laststub, "" );
strcpy( stubsubpat, "" ); strcpy( stubsubnew, "" );
stubhide = 0;
curyr = curmon = curday = 0;
circuit_breaker_disable = 0;
axis_arrow = 0;
arrowheadsize = 0.15;
strcpy( nearest, "" );
strcpy( stubround, "" );

Egetunits( xory, scaleunits );  /* moved from below - scg 1/27/05 */

/* get attributes.. */
first = 1;
while( 1 ) {
	line = getnextattr( first, attr, val, &lvp, &nt );
	if( line == NULL ) break;
	first = 0;
	lineval = &line[lvp];

	 /* screen out areadef attributes for the other axis.. */
	if( GL_slmember( attr, "axisline tic* minortic*" )) ;
	else if( astart > 0 && strnicmp( &attr[1], "axis.", 5 )!=0 ) continue;
	else if( astart > 0 && tolower(attr[0]) != xory ) continue; 

	if( stricmp( &attr[astart], "label" )==0 ) {
		strcpy( axislabel, lineval );
		convertnl( axislabel );
		}
	else if( stricmp( &attr[astart], "labelurl" )==0 ) strcpy( labelurl, val );
	else if( stricmp( &attr[astart], "labelinfo" )==0 ) strcpy( labelinfo, lineval );
        else if( stricmp( attr, "labelinfotext" )==0 ) {
                if( PLS.clickmap ) { getmultiline( "labelinfotext", lineval, MAXTT, labelinfo ); }
                }


	else if( stricmp( &attr[astart], "stubs" )==0 || stricmp( &attr[astart], "selflocatingstubs" )==0 ) {

		if( stricmp( &attr[astart], "selflocatingstubs" )==0 ) selfloc = 1;	
		else selfloc = 0;

		if( strnicmp( val, "inc", 3 )==0 || GL_slmember( val, "dat*matic" )) {
			isrc = INCREMENTAL;
			strcpy( incunits, "" );
			nt = sscanf( lineval, "%*s %lf %s", &incamount, incunits );
			if( nt < 1 || incamount == 0.0 ) {
				if( strncmp( scaleunits, "date", 4 )== 0 || strcmp( scaleunits, "time" )==0 ) {
					DT_reasonable( scaleunits, min, max, &incamount, incunits, stubformat, automonths, 
						autoyears, autodays, &minorticinc, minorticunits, nearest );
					}
				else incamount = 0.0;
				}
			}
		else if( stricmp( val, "minmaxonly" )==0 ) {
			isrc = INCREMENTAL;
			incamount = max - min;
			}
		else if( stricmp( val, "minonly" )==0 ) {
			isrc = INCREMENTAL;
			incamount = (max - min) * 2.0;
			}
		else if( stricmp( val, "maxonly" )==0 ) {
			isrc = INCREMENTAL;
			stubstart = max;
			incamount = (max - min) * 2.0;
			}
		else if( stricmp( val, "file" )==0 ) {
			isrc = FROMFILE;
			strcpy( filename, "" );
			nt = sscanf( lineval, "%*s %s", filename );
			if( nt < 1 ) {
				Eerr( 2796, "usage:   stub: file filename", "" );
				isrc = INCREMENTAL;
				}
			}
		else if( strnicmp( val, "datafield", 9 ) ==0 )  {
			char fnames[2][50];
			isrc = FROMDATA;
			for( j = 0, forlen = strlen( lineval ); j < forlen; j++ ) /* allow = and , */
				if( GL_member( lineval[j], "=," )) lineval[j] = ' ';
			nstubdf = sscanf( lineval, "%*s %s %s", fnames[0], fnames[1] );
			if( nstubdf < 1 ) { 
				Eerr( 2795, "usage:   stub: datafields=a,[b] where a and b are dfields", "" );
				isrc = INCREMENTAL;
				}
			if( nstubdf > 0 ) stubdf1 = fref( fnames[0] );
			if( nstubdf == 2 ) stubdf2 = fref( fnames[1] );
			}

		else if( stricmp( val, "categories" )==0 || stricmp( val, "usecategories" )==0 ) isrc = FROMCATS;
			
		else if( stricmp( val, "list" )==0 ) {
			isrc = HERE;
			i = 0;
			GL_getchunk( buf, lineval, &i, " \t" );
			while( GL_member( lineval[i], " \t" ) ) i++;
			strcpy( PL_bigbuf, &lineval[i] );
			convertnl( PL_bigbuf );
			}
		else if( stricmp( val, "none" )==0 ) isrc = 0;
		else 	{
			isrc = HERE;
			if( stricmp( val, "text" ) == 0 ) {
				getmultiline( "stubs", "", MAXBIGBUF, PL_bigbuf );
				}
			else 	{
				fprintf( PLS.errfp, 
				  "warning: proc axis assuming multiline stub text even though 'text' keyword not given\n" );
				getmultiline( "stubs", lineval, MAXBIGBUF, PL_bigbuf );
				}
			}
		}
			

	else if( stricmp( &attr[astart], "stubformat" )==0 ) strcpy( stubformat, lineval );

	else if( stricmp( &attr[astart], "stubdetails" )==0 ) strcpy( stubdetails, lineval );

	else if( stricmp( &attr[astart], "stubrange" )==0 ) {
		getrange( lineval, &stubstart, &stubstop, xory, min, max );
		stubrangegiven = 1;
		}
	else if( stricmp( &attr[astart], "stubround" )==0 ) strcpy( stubround, val );  /* added 5/29/06 scg */

	else if( stricmp( &attr[astart], "stubreverse" )==0 ) {
		if( strnicmp( val, YESANS, 1 )==0 )stubreverse = 1;
		else stubreverse = 0;
		stubreverse_given = 1;
		}

	else if( stricmp( &attr[astart], "stubvert" )==0 ) {
		if( strnicmp( val, YESANS, 1 )==0 ) vertstub = 1;
		else vertstub = 0;
		}

	else if( stricmp( &attr[astart], "stubomit" )==0 ) strcpy( stubomit, lineval );

	else if( stricmp( &attr[astart], "stubslide" )==0 ) Elenex( val, xory, &stubslide );

	else if( stricmp( &attr[astart], "stubexp" )==0 ) {
		if( strnicmp( val, YESANS, 1 )==0 ) stubexp = 1;
		else if( stricmp( val, "exp-1" )==0 ) stubexp = 2;
                else stubexp = 0;
		}

	else if( stricmp( &attr[astart], "stublen" )==0 ) stublen = atoi( val );

	else if( stricmp( &attr[astart], "stubmult" )==0 ) stubmult = atof( val );

	else if( stricmp( &attr[astart], "stubmininc" )==0 ) stubmininc = atof( val );

	else if( stricmp( &attr[astart], "stubhide" )==0 ) {
		if( strnicmp( val, YESANS, 1 )==0 ) stubhide = 1;
		else stubhide = 0;
		}

	else if( stricmp( &attr[astart], "ticslide" )==0 )  Elenex( val, xory, &ticslide );

	else if( stricmp( &attr[astart], "labeldetails" )==0 ) strcpy( axislabeldet, lineval );

	else if( stricmp( &attr[astart], "labeldistance" )==0 ) {
		axislabelofs = atof( val );
		if( PLS.usingcm ) axislabelofs /= 2.54;
		}

	else if( stricmp( &attr[astart], "location" )==0 ) {
		Eposex( val, opax, &f );
		pos = f;
		}


	else if( stricmp( &attr[astart], "axisline" )==0  ) {
		if( strnicmp( val, "no", 2 )==0 ) strcpy( axisline, "none" );
		else strcpy( axisline, lineval );
		}

	else if( stricmp( &attr[astart], "axislinerange" )==0 ) {
		getrange( lineval, &axlinestart, &axlinestop, xory, min, max );
		}

	else if( stricmp( &attr[astart], "tics" )==0  ) {
		strcpy( tics, lineval );
		if( strnicmp( tics, "no", 2 ) != 0 ) doingtics = 1;
		else doingtics = 0;
		}

	else if( stricmp( &attr[astart], "ticincrement" )==0  ) {
		strcpy( ticincunits, "" );
		sscanf( lineval, "%lf %s", &ticincamount, ticincunits );
		}

	else if( stricmp( &attr[astart], "ticlen" )==0  ) {
		sscanf( lineval, "%lf %lf", &ticout, &ticin );
		if( PLS.usingcm ) { ticout /= 2.54; ticin /= 2.54; }
		}

	else if( stricmp( &attr[astart], "minortics" )==0 ) {
		if( strnicmp( val, "no", 2 )==0 ) { strcpy( mtics, "none" ); minorticinc = 0.0; }   /* set ticinc = 0 scg 1/28/05 */
		else strcpy( mtics, lineval );
		}
	else if( stricmp( &attr[astart], "minorticinc" )==0 ) {
		strcpy( minorticunits, "" );
		nt = sscanf( lineval, "%lf %s", &minorticinc, minorticunits );
		}

	else if( stricmp( &attr[astart], "minorticlen" )==0 ||
		stricmp( attr, "minorticlen" )==0 ) {
		sscanf( lineval, "%lf %lf", &mticout, &mticin );
		if( PLS.usingcm ) { mticout /= 2.54; mticin /= 2.54; }
		}

	else if( stricmp( &attr[astart], "grid" )==0 ) {
		strcpy( grid, lineval );
		if( strnicmp( grid, "no", 2 )!= 0 ) doinggrid = 1;
		}
	else if( stricmp( &attr[astart], "gridskip" )==0 ) strcpy( gridskip, val );
	else if( stricmp( &attr[astart], "gridlineextent" )==0 ) {
		nt = sscanf( lineval, "%s %s", glemins, glemaxs );
		}
	else if( stricmp( &attr[astart], "gridblocks" )==0 ) {
		nt = sscanf( lineval, "%s %s", gbcolor1, gbcolor2 );
		if( stricmp( gbcolor1, "no" )==0 || stricmp( gbcolor1, "none" )==0 ) strcpy( gbcolor1, "" );
		else doinggrid = 1;
		}

	else if( stricmp( &attr[astart], "stubcull" )==0 ) {
		if( strnicmp( val, YESANS, 1 )==0 ) stubcull = 0.1;
		else if( atof( val ) > 0.0 ) stubcull = atof( val );
                else stubcull = 0.0;
		}

	else if( stricmp( &attr[astart], "autoyears" )==0 ) {
		strcpy( autoyears, val );
		if( stricmp( autoyears, "yes" )==0 || stricmp( autoyears, "y" )==0 ) strcpy( autoyears, "'yy" );
		else if( stricmp( autoyears, "no" )==0 ) strcpy( autoyears, "" );
		}

	else if( stricmp( &attr[astart], "autodays" )==0 ) {
		strcpy( autodays, val );
		if( stricmp( autodays, "yes" )==0 || stricmp( autodays, "y" )==0 ) strcpy( autodays, "Mmmdd" );
		else if( stricmp( autodays, "no" )==0 ) strcpy( autodays, "" );
		}

	else if( stricmp( &attr[astart], "automonths" )==0 ) {
		strcpy( automonths, val );
		if( stricmp( automonths, "yes" )==0 || stricmp( automonths, "y" )==0 ) strcpy( automonths, "Mmm" );
		else if( stricmp( automonths, "no" )==0 ) strcpy( automonths, "" );
		}


	else if( stricmp( &attr[astart], "signreverse" )==0 ) {
		if( strnicmp( val, YESANS, 1 )==0 )revsign = 1;
                else revsign = 0;
		}

	else if( stricmp( &attr[astart], "stubevery" )==0 ) stubevery = atoi( val );

	else if( stricmp( &attr[astart], "firststub" )==0 ) {
		strcpy( firststub, lineval );
		if( strcmp( firststub, "\"\"" )==0 ) strcpy( firststub, " " );
		}
	else if( stricmp( &attr[astart], "laststub" )==0 ) {
		strcpy( laststub, lineval );
		if( strcmp( laststub, "\"\"" )==0 ) strcpy( laststub, " " );
		}
	else if( stricmp( &attr[astart], "stubsubpat" )==0 ) strcpy( stubsubpat, lineval );
	else if( stricmp( &attr[astart], "stubsubnew" )==0 ) {
		strcpy( stubsubnew, lineval );
		if( strcmp( stubsubnew, "\"\"" )==0 ) strcpy( stubsubnew, " " );
		}

	else if( stricmp( &attr[astart], "clickmap" )==0 ) {
		if( strnicmp( val, "xy", 2 )==0 ) {
			if( xory == 'x' ) clickmap = 3;
			else clickmap = 4;
			}
		else 	{
			if( xory == 'x' ) clickmap = 1;
			else clickmap = 2;
			}
		}
	else if( stricmp( &attr[astart], "clickmapextent" )==0 ) nt = sscanf( lineval, "%s %s", cmemins, cmemaxs );
	else if( stricmp( &attr[astart], "clickmapvalformat" )==0 ) strcpy( cmvalfmt, lineval );
	else if( stricmp( &attr[astart], "nolimit" )==0 ) {
		if( strnicmp( val, YESANS, 1 )==0 ) circuit_breaker_disable = 1;
		}
	else if( stricmp( &attr[astart], "arrow" )==0 ) {
		if( strnicmp( val, YESANS, 1 )==0 ) axis_arrow = 1;
		}
	else if( stricmp( &attr[astart], "arrowheadsize" )==0 ) {
		arrowheadsize = atof( val );
		if( PLS.usingcm ) arrowheadsize /= 2.54;
		}
	else if( astart == 0 || strnicmp( attr, "xaxis.", 6 )==0 || 
	       strnicmp( attr, "yaxis.", 6 )==0 )
			Eerr( 301, "axis attribute not recognized", attr );
	}



/* check for degenerate cases and control overrides.. */
if( !scalebeenset() )
         return( Eerr( 51, "No scaled plotting area has been defined yet w/ proc areadef", "" ) );

if( stubstart < min || stubstart > max ) stubstart = min;
if( stubstop > max || stubstop < min ) stubstop = max;
if( axlinestart < min || axlinestart > max ) axlinestart = min;
if( axlinestop > max || axlinestop < min ) axlinestop = max;
if( stubevery == 0 ) stubevery = 1;




/* -------------------------- */
/* now do the plotting work.. */
/* -------------------------- */

/* Egetunits( xory, scaleunits ); */ /* moved up - scg 1/27/05 */


/* do the label.. easier if we do it before the Eflip below.. */
if( axislabel[0] != '\0' ) {
	double xpos, ypos, txtsize;
	textdet( "labeldetails", axislabeldet, &align, &adjx, &adjy, 0, "R", 1.0 );
	txtsize = (double)(strlen( axislabel )) * Ecurtextwidth;

	if( xory == 'x' ) {
		xpos =  ( EXlo+ (( EXhi - EXlo ) / 2.0 )) + adjx;
		ypos = (pos - axislabelofs) + adjy;
		Emov( xpos, ypos );
		Ecentext( axislabel );
		if( PLS.clickmap && ( labelurl[0] != '\0' || labelinfo[0] != '\0' ) )
			clickmap_entry( 'r', labelurl, 0, xpos - (txtsize/2.0), ypos, 
				xpos + (txtsize/2.0), ypos+Ecurtextheight, 1, 0, labelinfo );
		}
	else if( xory == 'y' ) {
		xpos = (pos - axislabelofs) + adjx;
		ypos = (EYlo + (( EYhi-EYlo ) / 2.0 )) + adjy;
		Emov( xpos, ypos );
		Etextdir( 90 );
		Ecentext( axislabel );
		Etextdir( 0 );
		if( PLS.clickmap && ( labelurl[0] != '\0' || labelinfo[0] != '\0' ) )
			clickmap_entry( 'r', labelurl, 0, xpos-Ecurtextheight, ypos - (txtsize*0.6), 
				xpos, ypos + (txtsize*0.6), 0, 0, labelinfo );
		}
	}


if( xory == 'x' ) Eflip = 1; /* reverse sense of x and y for draw operations */



/* avoid "circuit breaker" message when just doing label and/or line..  added scg 5/24/06 */
if( isrc == 0 && !doingtics && !doinggrid ) goto SKIPLOOP;   


/* --------------------- */
/* tics and axis preliminaries.. */
/* --------------------- */

inc = 1.0;
overrun = inc / 10.0; 
specialunits = 0;



/* ----------------- */
/* helpful overrides */
/* ----------------- */


/* if user didn't specify stub range, and 
   if isrc indicates text stubs, and not doing stubreverse, start at 1.0 */
if( !stubrangegiven && !stubreverse && !selfloc &&
	( isrc == HERE || isrc == FROMFILE || isrc == FROMDATA ) ) stubstart = 1.0;


/* if doing Y axis with text stubs, and user didn't specify stubreverse: no, 
	reverse the stubs */
if( xory == 'y' && !stubreverse_given && stubreverse == 0 &&
	( isrc == HERE || isrc == FROMFILE || isrc == FROMDATA ) && !selfloc ) {  /* && !selfloc added scg 3/19/03 */
	stubreverse = 1;
	if( !stubrangegiven ) stubstop = max - 1.0;
	}

if( stubformat[0] != '\0' && isrc == 0 )
	Eerr( 2749, "warning, stubformat but no stubs specification", "" );

/* if( strncmp( scaleunits, "date", 4 )==0 && stubformat[0] == '\0' ) 
 *	Eerr( 319, "warning, no stubformat specified.. using current notation.. for better results try 'stubs: datematic'", "" );
 */
	

if( minorticinc > 0.0 && strnicmp( mtics, "no", 2 ) == 0 ) strcpy( mtics, "yes" );


/* compute abs grid line extent - added scg 11/22/00 */
if( doinggrid ) { 
	Eposex( glemins, opax, &glemin );
	Eposex( glemaxs, opax, &glemax );
	/* Eposex( "min", Y, &gbylast ); changed to below 5/17/01 */
	Eposex( "min", xory, &gbylast );
	gbstate = 0;
 	} 

if( PLS.clickmap && clickmap ) {
	Eposex( cmemins, opax, &cmemin ); 
	Eposex( cmemaxs, opax, &cmemax );
	Eposex( "min", xory, &cmylast );
	}


/* stubround stuff..   added scg 5/29/06 */
if( stubround[0] != '\0' ) {    /* stubround is useful when user requires min and max to be at exact oddball locations, 
				 * but want the stubs to fall on round locations.  Shouldn't come into play with 'datematic' 
				 * since dm sets min and max to round (nearest) locations. */
	char minval[40], maxval[40];
	double ninc;
	if( GL_smember( scaleunits, "date datetime time" )) {
		Euprint( buf, xory, stubstart, "" );
		PLP_findnearest( buf, buf, xory, stubround, minval, maxval );  
		stubstart = Econv( xory, maxval );
		}
	else if( stricmp( stubround, "useinc" )==0 ) {   /* base it on whatever the axis increment value is/will be.. */
		if( incamount > 0.0 ) stubstart = GL_numgroup( stubstart, incamount, "high" );
		else	{
			PL_defaultinc( min, max, &ninc );
			stubstart = GL_numgroup( stubstart, ninc, "high" );
			}
		}
	else if( GL_goodnum( stubround, &prec )) stubstart = GL_numgroup( stubstart, atof( stubround ), "high" );
	}

	

/* render minor tics.. */
if( isrc == INCREMENTAL && strnicmp( mtics, "no", 2 )!= 0 ) {
	/* unit conversions.. */
	if( stricmp( scaleunits, "time" )==0 && strnicmp( minorticunits, "hour", 4 ) ==0 ) {
		strcpy( minorticunits, "" );
		minorticinc = minorticinc * 60;
		}
	else if( stricmp( scaleunits, "datetime" )==0 && 
		(strnicmp( minorticunits, "hour", 4 ) ==0 || strnicmp( minorticunits, "minute", 3 )==0 )) {
		double winsize, mm; /* window size in hours */
		DT_getwin( &winsize );
		mm = ((24.0/winsize) / 24.0);
		if( strnicmp( minorticunits, "minute", 3 )==0 ) mm /= 60.0;
		minorticinc *= mm;
		/* minorticinc = minorticinc / winsize;
		 * if( strnicmp( minorticunits, "minute", 3 )==0 ) minorticinc = minorticinc / 60.0;
		 * strcpy( minorticunits, "" );
		 */
		}
	else if( stricmp( scaleunits, "time" )==0 && strnicmp( minorticunits, "second", 3 ) ==0 ) {
		strcpy( minorticunits, "" );
		minorticinc = minorticinc / 60.0;
		}
	else if( stricmp( scaleunits, "date" )==0 && strnicmp( minorticunits, "year", 4 ) ==0 ) {
		strcpy( minorticunits, "month" );
		minorticinc = minorticinc * 12;
		}
	else if( stricmp( scaleunits, "date" )==0 && strnicmp( minorticunits, "month", 5 ) ==0 ) {
		strcpy( minorticunits, "month" );
		minorticinc = minorticinc * (365.25/12.0);
		}

	linedet( "minortics", mtics, 0.5 );
	if( minorticinc <= 0.0 ) {
		Eerr( 2340, "warning, minorticinc must be specified if doing minor tics", "" );
		minorticinc = 1.0;
		}
	y = stubstart;
	while( 1 ) {
		Emov( pos-mticout, Ea( Y, y ) ); 
		Elin( pos+mticin, Ea( Y, y ) );
		y += minorticinc;
		if( y >= stubstop ) break;
		}
	} 




/* --------------------------------- */
/* preliminaries based on stubs type */
/* --------------------------------- */

if( isrc == HERE ) bigbuflen = strlen( PL_bigbuf );

if( isrc == FROMFILE ) {  /* if taking from file, read the file into PL_bigbuf */
	stubfp = fopen( filename, "r" );
	if( stubfp == NULL ) {
		Eerr( 303, "warning, cannot open specified stub file.. using incremental stubs.", filename );
		isrc = INCREMENTAL; /* fallback */
		}
	else	{
		i = 0;
		while( fgets( buf, 128, stubfp ) != NULL ) {
			strcpy( &PL_bigbuf[i], buf );
			i += strlen( buf );
			}
		fclose( stubfp );
		bigbuflen = i;
		}
	}

if( isrc == FROMDATA ) {
	if( nstubdf >= 1 ) stubdf1--;  /* off-by-one */

	if( nstubdf >= 2 ) { 
		stubdf2--;
		nstubdf = 2;
		}
	}

if( isrc == FROMCATS ) selfloc = 0; 	/* for rendering purposes don't treat category stubs as self locating */


if( isrc == 0 && (doingtics || doinggrid) ) {   /* no stubs but doing tics or doing grid */
	strcpy( incunits, ticincunits );
	incamount = ticincamount;
	} 

/* incunit conversions.. */
if( stricmp( scaleunits, "time" )==0 && strnicmp( incunits, "hour", 4 ) ==0 ) {
	strcpy( incunits, "" );
	incamount = incamount * 60.0;
	}
else if( stricmp( scaleunits, "datetime" )==0 && 
	( strnicmp( incunits, "hour", 4 ) ==0 || strnicmp( incunits, "minute", 3 )==0 )) {
	double winsize; /* window size in hours */
	/* must be relative to window size.. */
	DT_getwin( &winsize );
	incamount = incamount / winsize;
	if( strnicmp( incunits, "minute", 3 )==0 ) incamount = incamount / 60.0;
	strcpy( incunits, "" );
	/* incamount = incamount / 24.0; */
	}
else if( stricmp( scaleunits, "datetime" )==0 && strnicmp( incunits, "minute", 3 ) ==0 ) {
	strcpy( incunits, "" );
	incamount = (incamount / 24.0) / 60.0;
	}
else if( stricmp( scaleunits, "time" )==0 && strnicmp( incunits, "second", 3 ) ==0 ) {
	strcpy( incunits, "" );
	incamount = incamount / 60.0;
	}
else if( stricmp( scaleunits, "date" )==0 && strnicmp( incunits, "year", 4 ) ==0 ) {
	strcpy( incunits, "month" );
	incamount = incamount * 12;
	}
else if( strnicmp( incunits, "month", 5 )!=0 && 
	atof( incunits ) == 0.0 ) strcpy( incunits, "" ); /* prevent racecon */


Egetunitsubtype( xory, scalesubtype );

/* yymm (etc) implies inc unit of "months" (yy implies years) */
if( scalesubtype[0] != '\0' ) {
	if( GL_slmember( scalesubtype, "yymm yy?mm yyyy?mm mm?yy mm?yyyy" ) && incunits[0] == '\0'  ) 
			strcpy( incunits, "month" );
	if( stricmp( scalesubtype, "yy" )==0 && incunits[0] == '\0' ) 
			strcpy( incunits, "year" );
	}


if( isrc == INCREMENTAL || ( isrc == 0 && (doingtics || doinggrid )) ) {

	/* for special units, initialize */
	if( strnicmp( incunits, "month", 5 )==0 ) {
		long l;
		if( ! GL_smember( scaleunits, "date datetime" ))  /* changed to include datetime as well as date - scg 8/11/05 */
			return( Eerr( 2476, "month increment only valid with date or datetime scale type", "" ) );
		specialunits = MONTHS;
		selfloc = 0; /* for rendering purposes don't treat month stubs as self locating.. */
		/* do the following to get starting m, d, y.. */
		Euprint( buf, xory, stubstart, "" );
		stat = DT_jdate( buf, &l );
		DT_getmdy( &mon, &day, &yr ); 
		if( incamount > 0.0 ) inc = incamount;
		else inc = 1; 	/* added scg 3/29 */
		}

	else if( stricmp( scaleunits, "datetime" )==0 && 
			strnicmp( incunits, "hour", 4 ) ==0 ) {
		inc = incamount / 24.0;
		}

		
		
	else	{
		if( incamount > 0.0 ) {
			if( incunits[0] == '\0' ) inc = incamount;
			else {
				double ftest;
				sscanf( incunits, "%lf", &ftest );
				inc = incamount * ftest;
				/* inc = incamount * atof( incunits ); repl scg 5/23/00 */
				}
			}
		else	{
			/* try to be smart about choosing default inc */
			PL_defaultinc( min, max, &inc );
			if( stubevery > 1 ) inc = inc * (double)stubevery;
			if( inc < stubmininc ) inc = stubmininc;
			}
		overrun = inc / 10.0;
		}
	}

if( xory == 'x' ) setfloatvar( "XINC", inc );
else if( xory == 'y' ) setfloatvar( "YINC", inc );

/* log mode */
logx = logy = 0;
if( Escaletype_x == E_LOG || Escaletype_x == E_LOGPLUS1 ) logx = 1;
if( Escaletype_y == E_LOG || Escaletype_y == E_LOGPLUS1 ) logy = 1;

/* sanity check.. added scg 8/7/00 */
if( inc <= 0.0 ) return( Eerr( 2705, "axis increment is zero or negative", "" ));





/* ---------------------------------------------- */
/* render the stubs and/or tics and/or grid lines.. */
/* ---------------------------------------------- */


if( stubreverse ) y = stubstop;
else y = stubstart;
ibb = 0;
irow = 0;
if( vertstub ) Etextdir(90);
prevstub = NEGHUGE;


firsttime = 1;


    
for( sanecount = 0; sanecount < 20000; sanecount++ ) {

	if( circuit_breaker_disable ) sanecount = 0;

	strcpy( txt, "" );

	/* if( isrc == 0 ) goto DOTIC;  */ /* no stubs */

	if( isrc == INCREMENTAL ) {

 		/* exception for log 0.0 */
                if( y <= 0.0 && ((xory == 'x' && logx ) || (xory == 'y' && logy ) ) ) goto NEXTSTUB;
                 
		}

	if( isrc == INCREMENTAL ) {
		double yy, fabs(), ftest, exp();
		if( revsign && fabs(y) > 0.0001 ) yy = y * -1;
		else yy = y;
		if( stubexp == 1 ) yy = exp( yy );
		else if( stubexp == 2 ) yy = exp( yy ) - 1;

		/* when generating incremental axes moving from negative to positive, for zero sprintf sometimes
           	gives -0.00 or very tiny values like -5.5579e-17.  The following is a workaround.. scg 7/5/01 */
		/* if( stubstart < 0.0 && stubstop > 0.0 && yy < 0.0000000000001 && yy > -0.0000000000001 ) yy = 0.0;  */
		/* 5/18/06 scg - problem still occurring.. adjusting to: */
		if( stubstart < 0.00001 && stubstop >= 0.0 && yy < 0.000000001 && yy > -0.000000001 ) yy = 0.0; 

		if( stubmult != 1.0 ) yy = yy * stubmult; /* added scg 10/2/03 */

		nt = sscanf( incunits, "%lf", &ftest );
		if( nt > 0 ) Euprint( txt, xory, yy/ftest, stubformat );
		else Euprint( txt, xory, yy, stubformat );
		if( PLS.clickmap && clickmap ) Euprint( cmtxt, xory, yy, cmvalfmt );
		}

	if( isrc == HERE || isrc == FROMFILE ) {
		if( ibb >= bigbuflen ) break;
		GL_getseg( txt, PL_bigbuf, &ibb, "\n" );
		if( PLS.clickmap && clickmap ) strcpy( cmtxt, txt );
		}
		
	if( isrc == FROMDATA ) {
		if( irow >= Nrecords ) break;
		if( ( irow % stubevery ) != 0 ) { irow++; goto NEXTSTUB; }
		if( nstubdf == 1 )
			sprintf( txt, "%s", da( irow, stubdf1 ) );
		else if( nstubdf == 2 ) 
			sprintf( txt, "%s %s", da( irow, stubdf1 ), da( irow, stubdf2 ) );
		if( PLS.clickmap && clickmap ) strcpy( cmtxt, txt );
		irow++;
		}

	if( isrc == FROMCATS ) {
		stat = PL_getcat( xory, irow, txt, 255 );
		if( stat ) break;  /* reached end of cat list */
		if( ( irow % stubevery ) != 0 ) { irow++; goto NEXTSTUB; }
		y = Econv( xory, txt ); /* error checking not needed */
		if( PLS.clickmap && clickmap ) strcpy( cmtxt, txt );
		irow++;
		}

	/* special units.. */
	if( specialunits == MONTHS ) {
		/* put a stub at current mon/year */
		DT_makedate( yr, mon, day, "", buf );
		y = Econv( xory, buf );
		if( Econv_error() ) { 
			Eerr( 9675, "warning, error on date conversion", buf );
			break; /* goto NEXTSTUB; changed to avoid inf loop - scg 2/12/03 */
			}
		if( day != 1 ) goto NEXTSTUB; /* added scg 9/12/01 */
		if( ( y - stubstop ) > overrun ) break;
		DT_formatdate( buf, stubformat, txt ); /* buf holds date string made above */
		if( PLS.clickmap && clickmap ) DT_formatdate( buf, cmvalfmt, cmtxt ); /* buf holds date string made above */
		}

	/* autoyears */
	if( autoyears[0] != '\0' ) {
		int doit;
		doit = 0;
		if( specialunits == MONTHS ) {
			if( (firsttime && mon < 11) || yr != curyr ) doit = 1; /* mon<11 added scg 9/12/01 */
			}
		else 	{
			DT_getmdy( &mon, &day, &yr );
			if( (firsttime && (mon < 11 || day < 18 )) || yr != curyr ) doit = 1;
			}
		curyr = yr;
		if( doit ) { 
			if( strlen( autoyears ) == 2 ) sprintf( buf, "%s\n%02d", txt, yr % 100 );
			else if( strlen( autoyears ) == 3 ) sprintf( buf, "%s\n'%02d", txt, yr % 100 );
			else	{
				if( yr >= 100 ) sprintf( buf, "%s\n%d", txt, yr );
				else if( yr >= PIVOTYEAR ) sprintf( buf, "%s\n%d", txt, 1900+yr );
				else if( yr < PIVOTYEAR ) sprintf( buf, "%s\n%d", txt, 2000+yr );
				}
			strcpy( txt, buf );
			}
		}

	/* autodays */
	if( autodays[0] != '\0' ) {
		if( stricmp( scaleunits, "datetime" )!=0 ) Eerr( 9677, "warning, autodays is only valid with datetime scaling", "" );
		else	{
			double datepart, timepart;
			char dt[40];
			datepart = floor( y );
			timepart = y - datepart;
			if( firsttime || datepart != curday ) { /* render date  */
				DT_fromjul( (long)datepart, dt );
				DT_formatdate( dt, autodays, buf );
				strcat( txt, "\n" );
				strcat( txt, buf );
				}
			curday = datepart;
			}
		}

	/* automonths - can be used w/ date or datetime.. */
	if( automonths[0] != '\0' ) {
		char dt[40];
		long foo;
		int imon, iday, iyr;
		DT_fromjul( (long) y, dt );
		DT_jdate( dt, &foo ); /* to get m d y */
		DT_getmdy( &imon, &iday, &iyr );
		if( firsttime || imon != curmon ) {
			DT_formatdate( dt, automonths, buf );
			strcat( txt, "\n" );
			strcat( txt, buf );
			}
		curmon = imon;
		}
	

	/* last minute stub content overrides.. */
	if( stubsubpat[0] != '\0' && GL_wildcmp( txt, stubsubpat, strlen(stubsubpat), 0 )==0 ) strcpy( txt, stubsubnew );
	if( firststub[0] != '\0' && firsttime ) strcpy( txt, firststub );
	if( laststub[0] != '\0' && y+inc > stubstop ) strcpy( txt, laststub );

	firsttime = 0;
		




	/* by this point stub text (including any selfloc field) 
	   should be in txt and location in y.. */


	/* if selflocating, get embedded location */
	if( selfloc ) {
		i = 0;
		GL_getchunk( buf, txt, &i, " \t" );
		if( buf[0] == '\0' ) goto NEXTSTUB;
		y = Econv( xory, buf );
		if( Econv_error() ) { 
			Eerr( 9676, "warning, error on value conversion", buf );
			goto NEXTSTUB;
			}
		if( y < stubstart || y > stubstop ) continue;
		while( GL_member( txt[i], " \t" ) ) i++;
		strcpy( txt, &txt[i] ); /* now obliterate the location field */
		}


	/* out of plotting area.. */
	if( min - y > overrun ) goto NEXTSTUB;
	if( y - max > overrun ) goto NEXTSTUB;

	/* too close to previous stub.. supress.. */
	if( stubcull > 0.0 ) {
		double fabs();
		if( fabs( Ea( Y, y ) - Ea( Y, prevstub )) < stubcull && prevstub != NEGHUGE ) goto NEXTSTUB; 
		/* fixed bug.. in log space, NEGHUGE becomes 1, sometimes causing 1st stub to be culled ... scg 9/21/04 */
		else prevstub = y;
		}


	/* render grid line or block - done first so other content can be "on top" of it.. */
	/* moved here from below, grid line extent and gridblocks added - scg 11/22/00 */
	if( doinggrid ) {
		if( gridskip[0] != '\0' && y <= min && GL_smember( gridskip, "min minmax both" ) );
		else if( gridskip[0] != '\0' && y >= max && 
			GL_smember( gridskip, "max minmax both" ) );
		else if( gbcolor1[0] != '\0' ) {   /* grid blocks */
			if( gbstate == 0 ) {
				Ecblock( glemin, gbylast, glemax, Ea( Y, y ), gbcolor1, 0 );
				gbstate = 1;
				}
			else 	{
				Ecblock( glemin, gbylast, glemax, Ea( Y, y ), gbcolor2, 0 );
				gbstate = 0;
				}
			gbylast = Ea( Y, y );
			}
		else	{
			linedet( "grid", grid, 0.5 );
			Emov( glemin, Ea( Y, y ) );
			Elin( glemax, Ea( Y, y ) );
			}
		}

	if( PLS.clickmap && clickmap ) {    /* save region.. */
		double halfdown, halfup;
		if( specialunits == MONTHS ) { halfdown = halfup = ( Ea( Y, y ) - cmylast ) / 2.0; }
		else 	{
			/* handle linear & log.. (doesn't work quite right for months).. */
			halfup =  ( Ea( Y, y+inc ) - Ea( Y, y ) ) / 2.0;  
			halfdown =  ( Ea( Y, y ) - Ea( Y, y-inc ) ) / 2.0;
			/* halfdist = ( Ea( Y, y+inc ) - Ea( Y, y ) ) / 2.0; */
			}

		/* urlencode the value now.. scg 5/29/06 */
		GL_urlencode( cmtxt, buf );

		if( Eflip ) {
			clickmap_entry( 'r', buf, clickmap, (Ea(Y,y)-halfdown)+stubslide, cmemin, 
				Ea(Y,y)+halfup+stubslide, cmemax, 0, 1, "" );
			}
		else 	{
			clickmap_entry( 'r', buf, clickmap, cmemin, (Ea(Y,y)-halfdown)+stubslide, 
				cmemax, Ea(Y,y)+halfup+stubslide, 0, 2, "" );
			}
		cmylast = Ea( Y, y );
		}

	/* convert any embedded "\n" to newline.. */
	convertnl( txt );

	/* determine exact position and render stub text.. */ 
	if( isrc > 0 && !stubhide && ! GL_slmember( txt, stubomit ) ) {   /* stub can't be in omit list.. */ 
		textdet( "stubdetails", stubdetails, &align, &adjx, &adjy, -2, "R", 1.0 );
		if( vertstub && xory == 'x' ) { 
			if( align == '?' ) align = 'R';
			ofsx = adjx + (ticout * -2.0) ;
			ofsy = (adjy + 0.04) + stubslide;
			}
		else if( xory == 'y' ) {
			if( align == '?' ) align = 'R';
			ofsx = (adjx + -0.15) /* + stubslide*/;
			/* ofsy = adjy + ( Ecurtextheight * -0.4 ); */
			ofsy = adjy + ( Ecurtextheight * -0.3 ) + stubslide; 
			}
		else if( xory == 'x' ) {
			if( align == '?' ) align = 'C';
			ofsx = adjx + (Ecurtextheight + ticout)*-1.0 ;
			ofsy = adjy + stubslide;
			}
		
		/* render text..   but don't do last stub if it is past range.. */
		if( ( (Ea(Y, y)+ofsy)  - Ea(Y, stubstop ) <= overrun ) || stubreverse ) {  
			Emov( pos+ofsx, Ea( Y, y)+ofsy ); 
			if( stublen && txt[stublen] != '\0' ) { txt[stublen] = '.'; txt[stublen+1] = '.'; txt[stublen+2] = '\0'; }
			Edotext( txt, align );
			}
		}



	/* render major tic mark  */
	if( doingtics ) {
		linedet( "tics", tics, 0.5 );
		Emov( pos-ticout, Ea( Y, y) + ticslide );  
		Elin( pos+ticin, Ea( Y, y ) + ticslide );
		}

	/* render grid line - was here */


	NEXTSTUB:

	/* increment stub location.. */

	if( specialunits == MONTHS ) {    /* increment to next */
		int newmon;

		mon += inc;

		/* wrap around year.. */
		newmon = ((mon-1) % 12 ) +1;
		yr += ((mon-1) / 12);
		mon = newmon;
		
		/* fprintf( stderr, "[mon=%d yr=%d]", mon, yr ); */

		day = 1;
		}

	else if( ! selfloc ) {
		if( stubreverse ) {
			y -= inc;
			if( stubstart - y > overrun ) break;
			}
		else	{
			y += inc;
			if( ( y - stubstop ) > overrun ) break;
			}
		}
	}

if( vertstub ) Etextdir(0);


SKIPLOOP:

/* make the line..  do it last of all for appearance sake.. */
if( stricmp( axisline, "none" )!= 0 ) {
	linedet( "axisline", axisline, 0.5 );
	Emov( pos, Ea( Y, axlinestart ) ); Elin( pos, Ea( Y, axlinestop ) );
	if( axis_arrow ) PLG_arrow( pos, Ea( Y, axlinestop), pos, Ea( Y, axlinestop)+(arrowheadsize*2.0), arrowheadsize, 0.3, Ecurcolor );
	}


Eflip = 0;

if( sanecount >= 20000 ) return( Eerr( 5729, "warning, too many stubs/major tics, circuit breaker tripped", "" ));

return( 0 );
}


/* ======================================================= *
 * Copyright 1998-2005 Stephen C. Grubb                    *
 * http://ploticus.sourceforge.net                         *
 * Covered by GPL; see the file ./Copyright for details.   *
 * ======================================================= */
