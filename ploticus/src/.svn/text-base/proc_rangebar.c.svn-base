/* ======================================================= *
 * Copyright 1998-2005 Stephen C. Grubb                    *
 * http://ploticus.sourceforge.net                         *
 * Covered by GPL; see the file ./Copyright for details.   *
 * ======================================================= */

/* PROC RANGEBAR - render boxplots */

/* 2/23/00 scg corrected 1.5xIRQ algorithm for tails.
 *		As explained by BMM, lower tail should extend from 25th
 *		downward to the nearest data point on or above (25th - (1.5 x IRQ)).
 *		Similar for upper tail.
 */

#include "pl.h"

static int calculate_stats();
#ifdef NONANSI
static int dblcompare();
#else
static int dblcompare(const void *a, const void *b);
/* static int dblcompare(double *f, double *g); */
#endif

static double Iqr, Lotail, Hitail;
static int Skipmed = 0;
static int Logmean = 0;

/* ========================= */
int
PLP_rangebar_initstatic()
{
Skipmed = 0;
Logmean = 0;
return( 0 );
}


/* ========================= */
int
PLP_rangebar()
{
int i;
char attr[NAMEMAXLEN], val[256];
char *line, *lineval;
int nt, lvp;
int first;

char buf[256];
int j;
int stat;
int align;
double adjx, adjy;

char baseax, axis;
int datafield;
int showstats;
int showbriefstats;
char showstatsfile[256];
double stats[20];
char values[256];
char tailmode[80];
char nlocation[40];
double nloc;
int printn;
char missword[NAMEMAXLEN+1];
char nword[NAMEMAXLEN+1];
char mlocation[40];
double mloc;
int printm;
double barloc;
double barwidth, hb;
double h[5];
char taildet[256];
char outlinedet[256];
char barcolor[COLORLEN];
int baroutline;
char ntextdet[256];
char mtextdet[256]; /* 3/6/01 */
char msym[256];
double r;
double ticlen;
char selectex[256];
int nplotfields;
int pf[6];
int plotrecord;
int fnftics;
int quitaftershowingstats;
char sbarloc[20];
int meanmode;
int nstddevs;
int trunc;
double fval;
int showoutliers;
double nfcutoff, outnfcutoff;
char outnearsym[256], outfarsym[256];
int outlblfld;
char outlbldet[256];
char meansym[100];
int printoutliers;
char buf2[256];
double outlinelen;
char outlierlinedet[256];
int result;
FILE *statfp;
char datafieldname[NAMEMAXLEN+1];
char briefstatstag[256];
double exp();
int mwhenexists; /* 3/6/01 */
double radius;
char symcode[50];


TDH_errprog( "pl proc rangebar" );

/* initialize */
datafield = -1;
axis = 'y';
strcpy( values, "" );
strcpy( tailmode, "5/95" );
strcpy( nlocation, "" );
printn = 1;
strcpy( mlocation, "" );
printm = 0;
/* barloc = -1.0; */
barwidth = 0.2;
baroutline = 1;
strcpy( barcolor, "gray(0.8)" );
strcpy( nword, "N=@N" );
strcpy( missword, "M=@M" );
strcpy( ntextdet, "" );
strcpy( mtextdet, "" );
strcpy( msym, "line" );
strcpy( taildet, "" );
strcpy( outlinedet, "" );
ticlen = -1;
strcpy( selectex, "" );
nplotfields = 0;
plotrecord = 0; /* do first record by default */
fnftics = 1;
quitaftershowingstats = 0;
strcpy( sbarloc, "" );
meanmode = 0;
nstddevs = 1;
trunc = 1;
showoutliers = 0;
strcpy( outfarsym, "shape=circle style=spokes radius=0.05" );
strcpy( outnearsym, "shape=circle style=outline radius=0.05" );
outlblfld = 0;
strcpy( outlbldet, "" );
outnfcutoff = 3.0;
strcpy( meansym, "" );
printoutliers = 0;
outlinelen = 0.0;
strcpy( outlierlinedet, "" );
showstats = 0;
showbriefstats = 0;
strcpy( showstatsfile, "" );
strcpy( briefstatstag, "" );
statfp = NULL; /* scg 10/12/00 */
mwhenexists = 0;


/* get attributes.. */
first = 1;
while( 1 ) {
	line = getnextattr( first, attr, val, &lvp, &nt );
	if( line == NULL ) break;
	first = 0;
	lineval = &line[lvp];

	if( stricmp( attr, "datafield" )==0 ) {
		datafield = fref( val ) - 1;
		strcpy( datafieldname, val ); /* for the stats */
		}
	else if( stricmp( attr, "axis" )==0 ) axis = tolower(val[0]);
	else if( stricmp( attr, "barloc" )==0 ) strcpy( sbarloc, lineval ); /* val ->lineval scg */
	else if( stricmp( attr, "barwidth" )==0 ) {
		stat = num( val, &barwidth );
		if( stat != 0 ) { Eerr( 237, "barwidth invalid", val ); barwidth = 0.2; }
		if( PLS.usingcm ) barwidth /= 2.54;
		}
	else if( stricmp( attr, "showstats" )==0 ) {
		if( strnicmp( val, YESANS, 1 )==0 ) showstats = 1;
		else if( stricmp( val, "only" )==0 ) {showstats = 1; quitaftershowingstats = 1; }
		else showstats = 0;
		}
	else if( stricmp( attr, "statsonly" )== 0 ) {
		if( strnicmp( val, YESANS, 1 )==0 ) quitaftershowingstats = 1;
		else quitaftershowingstats = 0;
		}

	else if( stricmp( attr, "showbriefstats" )==0 ) {
		if( strnicmp( val, YESANS, 1 )==0 ) showbriefstats = 1;
		else if( stricmp( val, "only" )==0 ) {showbriefstats = 1; quitaftershowingstats = 1; }
		else showbriefstats = 0;
		}
	else if( stricmp( attr, "briefstatstag" )==0 ) sprintf( briefstatstag, "%s	", lineval ); /* should end w/ tab */
		
	else if( stricmp( attr, "showstatsfile" )==0 ) strcpy( showstatsfile, val );

	else if( stricmp( attr, "values" )==0 ) strcpy( values, lineval ); /* plot values given
							here for one bar
							(calculations already done exteranally) */

	else if( stricmp( attr, "plotfields" )==0 ) { /* read plot values from data array
							(calculations already done exteranally) */
		/* nplotfields = sscanf( lineval, "%d %d %d %d %d %d", 
			&pf[0], &pf[1], &pf[2], &pf[3], &pf[4], &pf[5], &pf[6] ); */
		char fld[NAMEMAXLEN+1], *GL_getok();
		int ix;
		ix = 0;
		for( i = 0; ; i++ ) {
			strcpy( fld, GL_getok( lineval, &ix ));
			if( fld[0] == '\0' ) break;
			pf[i] = fref( fld ) - 1;
			}
		nplotfields = i;
		
		}
	else if( stricmp( attr, "plotrecord" )==0 ) plotrecord = atoi( val ) -1; /* select which
										data record
										to plot */

	else if( stricmp( attr, "tailmode" )==0 ) strcpy( tailmode, val );
	else if( stricmp( attr, "95tics" )==0 ) {
		if( strnicmp( val, YESANS, 1 )==0 ) fnftics = 1;
		else fnftics = 0;
		}

	else if( stricmp( attr, "taildetails" )==0 ) strcpy( taildet, lineval );
	else if( stricmp( attr, "outlinedetails" )==0 ) {
		strcpy( outlinedet, lineval );
		baroutline = 1;
		}
	else if( stricmp( attr, "color" )==0 ) strcpy( barcolor, val );
	else if( stricmp( attr, "outline" )==0 ) {
		if( strnicmp( val, YESANS, 1 )==0 ) baroutline = 1;
		else baroutline = 0;
		}

	else if( stricmp( attr, "printn" )==0 ) {
		if( strnicmp( val, YESANS, 1 )==0 ) printn = 1;
		else printn = 0;
		}
	else if( stricmp( attr, "nlocation" )==0 ) strcpy( nlocation, val );

	else if( stricmp( attr, "printmissing" )==0 ) {
		if( strnicmp( val, YESANS, 1 )==0 ) printm = 1;
		else printm = 0;
		}
	else if( stricmp( attr, "mlocation" )==0 ) strcpy( mlocation, val );
	else if( stricmp( attr, "mediansym" )==0 ) strcpy( msym, lineval );
	else if( stricmp( attr, "mword" )==0 ) strcpy( missword, lineval );
	else if( stricmp( attr, "nword" )==0 ) strcpy( nword, lineval );
	else if( stricmp( attr, "textdetails" )==0 ||
		stricmp( attr, "ntextdetails" )==0 ) strcpy( ntextdet, lineval );
	else if( stricmp( attr, "mtextdetails" )==0 ) strcpy( mtextdet, lineval );
	else if( stricmp( attr, "mwhenexists" )==0 ) {
		if( strnicmp( val, YESANS, 1 )==0 ) mwhenexists = 1;
		else mwhenexists = 0;
		}
	else if( stricmp( attr, "truncate" )==0 ) {
		if( strnicmp( val, YESANS, 1 )==0 ) trunc = 1;
		else trunc = 0;
		}

	else if( stricmp( attr, "ticlen" )==0 ) {
		stat = num( val, &ticlen );
		if( stat != 0 ) ticlen = -1.0;
		if( PLS.usingcm ) barwidth /= 2.54;
		}
	else if( stricmp( attr, "select" )==0 ) strcpy( selectex, lineval );
	else if( stricmp( attr, "meanmode" )==0 ) {
		if( strnicmp( val, YESANS, 1 )==0 ) meanmode = 1;
		else meanmode = 0;
		}
	else if( stricmp( attr, "nstddevs" )==0 ) nstddevs = atoi( val );
	else if( stricmp( attr, "showoutliers" )==0 ) {
		if( strnicmp( val, YESANS, 1 )==0 ) showoutliers = 1;
		else showoutliers = 0;
		}
	else if( stricmp( attr, "outliernearfarcutoff" )==0 ) outnfcutoff = atof( val );
	else if( stricmp( attr, "outliernearsym" )==0 ) strcpy( outnearsym, lineval );
	else if( stricmp( attr, "outlierfarsym" )==0 ) strcpy( outfarsym, lineval );
	else if( stricmp( attr, "outlierlabelfield" )==0 ) outlblfld = fref( val );
	else if( stricmp( attr, "outlierlabeldetails" )==0 ) strcpy( outlbldet, lineval );
	else if( stricmp( attr, "outlierprint" )==0 ) {
		if( strnicmp( val, YESANS, 1 )==0 ) printoutliers = 1;
		else printoutliers = 0;
		}
	else if( stricmp( attr, "outlierlinelen" )==0 ) {
		outlinelen = atof( val );
		if( PLS.usingcm ) outlinelen /= 2.54;
		}
	else if( stricmp( attr, "outlierlinedetails" )==0 ) strcpy( outlierlinedet, lineval );
	else if( stricmp( attr, "meansym" )==0 ) strcpy( meansym, lineval );
	else if( stricmp( attr, "skipmed" )==0 ) {
		if( strnicmp( val, YESANS, 1 )==0 ) Skipmed = 1;
		else Skipmed = 0;
		}
	else if( stricmp( attr, "logmean" )==0 ) {
		if( strnicmp( val, YESANS, 1 )==0 ) { 
			Logmean = 1;
			meanmode = 1;
			}
		else Logmean = 0;
		}
	else Eerr( 1, "attribute not recognized", attr );
	}


/* overrides and degenerate cases */
/* -------------------------- */
if( ( datafield < 0 || datafield >= Nfields ) && values[0] == '\0'  && nplotfields < 1 ) 
	return( Eerr( 601, "datafield not specified or out of range", "" ) );
if( axis == 'x' ) baseax = 'y';
else baseax = 'x';

if( !meanmode && (nplotfields > 0 && nplotfields != 5 && nplotfields != 6 ) )
			return( Eerr( 729, 
		"plotfields: 5 or 6 datafields expected: 5thpercentile 25th median 75th 95th [N]", "" ) );

if( meanmode && (nplotfields > 0 && nplotfields != 2 && nplotfields != 3 ) )
			return( Eerr( 730, 
		"plotfields: 2 or 3 datafields expected in meanmode: mean stddev [N]", "" ) );

if( values[0] != '\0' && meanmode )
	return( Eerr( 2470, "values may not be used with meanmode", "" ) );

if( !quitaftershowingstats ) {
	if( Nrecords < 1 && values[0] == '\0' ) 
		return( Eerr( 17, "No data has been read yet w/ proc getdata", "" ) );
	if( !scalebeenset() )
         return( Eerr( 51, "No scaled plotting area has been defined yet w/ proc areadef", "" ) );
	}

if( !meanmode ) Skipmed = 0;
if( nlocation[0] == '\0' ) nloc = Elimit( axis, 'l', 'a' ) + 0.3;
else Eposex( nlocation, axis, &nloc );

if( mlocation[0] == '\0' ) mloc = Elimit( axis, 'l', 'a' ) + 0.15;
else Eposex( mlocation, axis, &mloc );

if( ticlen < 0.0 ) ticlen = barwidth * 0.7;

if( stricmp( msym, "dot" )==0 || stricmp( msym, "yes" )==0 ) 
	strcpy( msym, "shape=circle style=filled fillcolor=black radius=0.05" );
 

	


/* Do the computation work.. */
/* --------------------------- */

/* if we are doing logmean we need to see whether to do log or log+1.. */
if( Logmean ) {
	if( axis == 'x' &&  Escaletype_x == E_LOGPLUS1 ) Logmean = 2;
	if( axis == 'y' &&  Escaletype_y == E_LOGPLUS1 ) Logmean = 2;
	}

/* first, make sure that bar location makes sense.. if not, don't bother with the rest.. */	
/* code moved from oldbarloccheck   scg 8/14/00 */
if( sbarloc[0] == '\0' ) strcpy( sbarloc, "1" );
if( !quitaftershowingstats ) {
	barloc = Econv( baseax, sbarloc );
	if( Econv_error() ) { 
		fprintf( PLS.errfp, "ignoring barloc %s\n", sbarloc ); 
		return( 0 );
		}
	}


if( values[0] != '\0' ) {
	nt = sscanf( values, "%lf %lf %lf %lf %lf %lf", 
		&stats[4], &stats[5], &stats[6], &stats[7], &stats[8], &stats[0] );
	if( nt != 6 && nt != 5 ) return( Eerr( 205, 
		"values: 5 or 6 numbers expected: 5thpercentile 25th median 75th 95th [N]", "" ) );
	if( nt == 5 ) { printn = 0; stats[0] = PLHUGE; } /* no N given */
	strcpy( tailmode, "5/95" );
	printm = 0;
	}
else if( nplotfields > 0  ) {
	if( !meanmode ) {
		stats[4] = fda( plotrecord, pf[0], axis );
		stats[5] = fda( plotrecord, pf[1], axis );
		stats[6] = fda( plotrecord, pf[2], axis );
		if( Econv_error() ) {
			fprintf( PLS.errfp, "plotfields: skipping rangebar due to non-numeric median value.\n" );
			return( 1 );
			}
		stats[7] = fda( plotrecord, pf[3], axis );
		stats[8] = fda( plotrecord, pf[4], axis );
		if( nplotfields == 6 ) stats[0] = fda( plotrecord, pf[5], axis );
		else { printn = 0; stats[0] = PLHUGE; }
		}
	else if( meanmode ) {
		stats[1] = fda( plotrecord, pf[0], axis );
		if( Econv_error() ) {
			fprintf( PLS.errfp, "plotfields: skipping rangebar due to non-numeric mean value.\n" );
			return( 1 );
			}
		stats[2] = fda( plotrecord, pf[1], axis );
		if( nplotfields == 3 ) stats[0] = fda( plotrecord, pf[2], axis );
		else { printn = 0; stats[0] = PLHUGE; }
		}
	printm = 0;
	}
else	{
	calculate_stats( datafield, axis, selectex, stats );
	if( showstats || showbriefstats ) {
		statfp = PLS.diagfp;
		if( showstatsfile[0] != '\0' ) {
			statfp = fopen( showstatsfile, "a" ); /* diagnostics */
			if( statfp == NULL ) {
				Eerr( 4279, "cannot open showstatsfile for append", showstatsfile );
				return( 1 );
				}
			}
		}

	if( showstats ) {
	    fprintf( statfp, "\n// Rangebar statistics computed on data field %d ", datafield+1 );
	    if( selectex[0] != '\0'  ) fprintf( statfp, "(where %s)", selectex );
	    Euprint( buf, axis, stats[1], "" );
	    fprintf( statfp, "\n// N=%g,  mean=%s,  stddev=%g,  #missing=%g,  sum(E)=%g\n", 
		stats[0], buf, stats[2], stats[10], stats[11] );
	    Euprint( buf, axis, stats[3], "" );
	    Euprint( buf2, axis, stats[9], "" );
	    fprintf( statfp, "// min=%s,  max=%s,  ", buf, buf2 );
	    if( Skipmed ) strcpy( buf2, "n/a" );
	    else Euprint( buf2, axis, stats[6], "" );
	    fprintf( statfp, "median=%s,  ", buf2 );
	    if( Skipmed ) strcpy( buf, "n/a" );
	    else Euprint( buf, axis, stats[5], "" );
	    if( Skipmed ) strcpy( buf2, "n/a" );
	    else Euprint( buf2, axis, stats[7], "" );
	    fprintf( statfp, "25th_pctile=%s,  75th_pctile=%s,  IQR=%g\n", buf, buf2, Iqr );
	    }
	if( showbriefstats && stats[0] != 0.0 ) {
		char *GL_autoroundf();
		fprintf( statfp, "%s%s	%g	", briefstatstag, datafieldname, stats[0] );  /* N */
	        Euprint( buf, axis, stats[1], "" );
		GL_autoround( buf, 0 );
		fprintf( statfp, "%s	%s	", buf, GL_autoroundf( stats[2], 0 ) ); /* mean,  stddev */
		if( Skipmed ) strcpy( buf2, "n/a" );
	        else Euprint( buf2, axis, stats[6], "" );
	        fprintf( statfp, "%s	", buf2 );  /* median */
	        Euprint( buf, axis, stats[3], "" );
	        Euprint( buf2, axis, stats[9], "" );
	        fprintf( statfp, "%s	%s	%g\n", buf, buf2, stats[10] ); /* min, max, #missing */ /* select rm scg 8/11/00 */
		}
	}

/* set NVALUES to zero in case plot craps out */
setfloatvar( "NVALUES", 0.0 );



if( !GL_slmember( tailmode, "5* m* 1*" )) {
	Eerr( 582, "tailmode must be either 5/95, min/max, or 1.5iqr", tailmode );
	strcpy( tailmode, "5/95" ); 
	}
/* -------------------------- */
/* now do the plotting work.. */
/* -------------------------- */

/* stats are in STATS array as follows:  
 *	0    1    2      3    4    5    6      7    8    9
 *     ---  ---  ---    ---  ---  ---  ---    ---  ---  ---
 *      N  mean  stddev min  5th  25th median 75th 95th max
 *
 * rangebar drawing array (H) is:
 *
 *      0       1      2      3      4
 *    ----   -----  -----   -----  -----
 *    lotail  lobox  midbox  hibox  hitail    
 */

/* oldbarloccheck */
 
if( baseax == 'y' ) Eflip = 1;

hb = barwidth / 2.0;
if( meanmode ) {
	if( Logmean == 1 ) {
		h[0] = exp(stats[1] - (stats[2] * nstddevs));
		h[2] = exp(stats[1]);
		h[4] = exp(stats[1] + (stats[2] * nstddevs));
		}
	else if( Logmean == 2 ) {  /* log+1 */
		h[0] = exp(stats[1] - (stats[2] * nstddevs)) - 1.0;
		h[2] = exp(stats[1]) - 1.0;
		h[4] = exp(stats[1] + (stats[2] * nstddevs)) - 1.0;
		}
	else	{
		h[0] = stats[1] - (stats[2] * nstddevs);
		h[2] = stats[1];
		h[4] = stats[1] + (stats[2] * nstddevs);
		}
	}
else 	{
	if( tailmode[0] == '5' ) {
		h[0] = stats[4];
		h[4] = stats[8];
		}
	else if( tailmode[0] == '1' ) {  	/* 1.5 X IRQ */
		h[0] = Lotail; 
		h[4] = Hitail;
		}
	else	{
		h[0] = stats[3];
		h[4] = stats[9];
		}
	h[1] = stats[5];
	h[2] = stats[6];
	h[3] = stats[7];
	}
if( showstats && !Skipmed ) {
	Euprint( buf, axis, h[0], "" );
	Euprint( buf2, axis, h[4], "" );
	fprintf( statfp, "// Tails (mode=%s): low=%s,  high=%s\n", tailmode, buf, buf2 );
	}

/* these moved up here from below.. scg 11/22/00 */
setfloatvar( "RANGEBARMIN", h[0] );
if( !meanmode ) setfloatvar( "RANGEBARIQRMIN", h[1] );
if( meanmode ) setfloatvar( "RANGEBARMEAN", h[2] );
else setfloatvar( "RANGEBARMEDIAN", h[2] );
if( !meanmode ) setfloatvar( "RANGEBARIQRMAX", h[3] );
setfloatvar( "RANGEBARMAX", h[4] );

if( ( showstats || showbriefstats ) && showstatsfile[0] != '\0' ) fclose( statfp );
if( quitaftershowingstats ) return( 0 ); /* moved scg 11/22/00 */

/* check to see if bar location is in range.. */
if( !Ef_inr( baseax, barloc ) ) {
	fprintf( PLS.errfp, "warning, rangebar location out of %c plotting area\n", baseax );
	goto SKIPOUT;
	}

/* now convert barloc to absolute.. */
barloc = Ea(X,barloc);

ticlen *= 0.5;


/* if N=0, skip out */
if( stats[0] <= 0.0 ) goto SKIPOUT;

/* if entire bar is out of plotting area, skip out.. */
if( h[0] < Elimit( axis, 'l', 's' ) && h[4] < Elimit( axis, 'l', 's' ) ) {
	fprintf( PLS.errfp, "warning, entire rangebar out of %c plotting area (under)\n", axis );
	goto SKIPOUT;
	}
if( h[0] > Elimit( axis, 'h', 's' ) && h[4] > Elimit( axis, 'h', 's' ) ) {
	fprintf( PLS.errfp, "warning, entire rangebar out of %c plotting area (over)\n", axis );
	goto SKIPOUT;
	}

linedet( "taildetails", taildet, 1.0 );

if( !trunc || Ef_inr( axis, h[0] ) ) { /* bottom tic */
	if( meanmode || h[0] < h[1] ) { /* only do if below bottom of box - added scg 5/23/00 */
		Emov( barloc-ticlen, Ea( Y, h[0] ) ); 
		Elin( barloc+ticlen, Ea( Y, h[0] ) ); 
		}
	}

if( !trunc || Ef_inr( axis, h[4] ) ) { /* top tic */
	if( meanmode || h[4] > h[3] ) { /* only do if above top of box - added scg 5/23/00 */
		Emov( barloc-ticlen, Ea( Y, h[4] ) ); 
		Elin( barloc+ticlen, Ea( Y, h[4] ) ); 
		}
	}

/* truncate values to be within range.. */
if( trunc && !Ef_inr( axis, h[0] )) h[0] = Elimit( axis, 'l', 's' );
if( trunc && !Ef_inr( axis, h[1] )) h[1] = Elimit( axis, 'l', 's' );
if( trunc && !Ef_inr( axis, h[3] )) h[3] = Elimit( axis, 'h', 's' );
if( trunc && !Ef_inr( axis, h[4] )) h[4] = Elimit( axis, 'h', 's' );

if( meanmode ) {
	Emov( barloc, Ea( Y, h[0] ) ); 
	Elin( barloc, Ea( Y, h[4] ) ); 
	}

else	{
	Emov( barloc, Ea( Y, h[0] ) ); 
	Elin( barloc, Ea( Y, h[1] ) ); 
	Emov( barloc, Ea( Y, h[3] ) ); 
	Elin( barloc, Ea( Y, h[4] ) );
	}



if( !meanmode ) {
	linedet( "outlinedetails", outlinedet, 0.5 );
	Ecblock( barloc-hb, Ea(Y,h[1]), barloc+hb, Ea(Y,h[3]), barcolor, baroutline );
	}


/* do median */
if( strcmp( msym, "line" ) ==0 && (!trunc || Ef_inr( axis, h[2] ))) {
	Emov( barloc-hb, Ea(Y,h[2]) );
	Elin( barloc+hb, Ea(Y,h[2]) );
	}
else if( msym[0] != '\0' && (!trunc || Ef_inr( axis, h[2] ))) {
	symdet( "mediansym", msym, buf, &r );
	Emark( barloc, Ea(Y,h[2]), buf, r );
	}

/* other setfloatvars used to be here  - scg 11/22/00 */
setfloatvar( "NVALUES", stats[0] );

if( tailmode[0] != '5' && tailmode[0] != '1' && fnftics && !meanmode ) { /* add 5/95 ticks to min/max tails */
	if( !trunc || Ef_inr( axis, stats[4] )) {
		Emov( barloc-(ticlen*0.8), Ea( Y, stats[4] ) );
		Elin( barloc+(ticlen*0.8), Ea( Y, stats[4] ) );
		}
	if( !trunc || Ef_inr( axis, stats[8] )) {
		Emov( barloc-(ticlen*0.8), Ea( Y, stats[8] ) );
		Elin( barloc+(ticlen*0.8), Ea( Y, stats[8] ) );
		}
	}


/* display outliers.. */
if( showoutliers ) {
	nfcutoff = (h[3] - h[1]) * outnfcutoff;
	if( Eflip ) Etextdir( 90 );
	if( outlierlinedet[0] != '\0' ) linedet( "outlierlinedetails", outlierlinedet, 0.5 );
	for( i = 0; i < Nrecords; i++ ) {

		if( selectex[0] != '\0' ) { /* process against selection condition if any.. */
						/* added 10/29/99 */
			stat = do_select( selectex, i, &result );
			if( stat != 0 ) { Eerr( stat, "Select error", selectex ); continue; }
			if( result == 0 ) continue; /* reject */
			}

		fval = fda( i, datafield, axis );
		if( ( fval < h[0] || fval > h[4] ) && !Econv_error() ) {  /* its an outlier.. */
			if( printoutliers ) {
				fprintf( PLS.diagfp, "// data field %d outlier: %s (", 
					datafield+1, da(i, datafield)  );
				for( j = 0; j < Nfields; j++ ) 
					fprintf( PLS.diagfp, "%s ", da( i, j ) );
				fprintf( PLS.diagfp, ")\n" );
				}
					
			if( !Ef_inr( axis, fval )) {
				fprintf( PLS.errfp, "warning, outlier %s out of range, not displayed\n", 
					da(i,datafield) );
				continue;
				}
			if( outlinelen > 0.0 ) { /* render as lines, whether near of far */
						 /* use linedetails in effect for tails */
				Emov( barloc-(outlinelen/2.0), Ea(Y,fval) );
				Elin( barloc+(outlinelen/2.0), Ea(Y,fval) );
				}
			else if( fval < (h[1] - nfcutoff) || fval > h[3] + nfcutoff ) {    /* far */
				if( stricmp( outfarsym, "none" ) != 0 ) {
					symdet( "outlierfarsym", outfarsym, symcode, &radius );
					Emark( barloc, Ea(Y,fval), symcode, radius );
					}
				}
			else 	{	/* near */
				if( stricmp( outnearsym, "none" ) != 0 ) {
					symdet( "outliernearsym", outnearsym, symcode, &radius );
					Emark( barloc, Ea(Y,fval), symcode, radius );
					}
				}
			if( outlblfld > 0 ) {
				textdet( "outlierlabeldetails", outlbldet, &align, &adjx, &adjy, -3, "R", 1.0 );
				if( align == '?' ) align = 'L';
				if( Eflip ) Emov( barloc+0.1+adjx, Ea( Y, fval )+adjy );
				else Emov( barloc+0.1+adjx, Ea( Y, fval )-((Ecurtextheight*0.35)+adjy) );
				Edotext( da( i, outlblfld-1 ), align );
				}
			}
		}
	if( Eflip ) Etextdir( 0 );
	}
		

/* display mean symbol on a median-based rangebar.. */
if( meansym[0] != '\0' ) {
	double radius; char symcode[50];
	if( stricmp( meansym, "yes" )==0 || stricmp( meansym, "dot" )==0 ) 
		strcpy( meansym, "shape=circle style=filled fillcolor=black radius=0.02" );
	symdet( "meansym", meansym, symcode, &radius );
	Emark( barloc, Ea(Y,stats[1]), symcode, radius );
	}



/* display N= and maybe M= .. */
SKIPOUT:

if( printn ) {
	textdet( "ntextdetails", ntextdet, &align, &adjx, &adjy, -2, "R", 1.0 );
	sprintf( buf, "%g", stats[0] );
	GL_varsub( nword, "@N", buf );
	if( baseax == 'y' ) Emov( (barloc-(Ecurtextheight*0.3)) + adjx, nloc + adjy );
	else Emov( barloc + adjx, nloc + adjy );
	Ecentext( nword );
	}
if( printm && (stats[10] != 0.0 || !mwhenexists) ) {  /* modified 3/6/01 per H. Jaffee request */
	textdet( "mtextdetails", mtextdet, &align, &adjx, &adjy, -2, "R", 1.0 );
	sprintf( buf, "%g", stats[10] );
	GL_varsub( missword, "@M", buf );
	/* Emov( barloc, mloc ); */
	if( baseax == 'y' ) Emov( (barloc-(Ecurtextheight*0.3)) + adjx, mloc + adjy );
	else Emov( barloc + adjx, mloc + adjy );
	Ecentext( missword );
	}

if( baseax == 'y' ) Eflip = 0;
return( 0 );
}


/* ========================= */
/* ========================= */
/* ========================= */
static int
calculate_stats( ifld, axis, selectex, stats )
int ifld;
char axis;
char *selectex;
double stats[];
{
double total, val, mean, stddev, totsq, min, max;
int i, n;
int nbad;
double sqrt(), log();
int result;
int stat;
double loend, hiend;

total = 0; n = 0, totsq=0; min = PLHUGE; max = NEGHUGE; nbad = 0;
Iqr = 0.0; /* fallback */

/* read lines of data */
for( i = 0; i < Nrecords; i++ ) {
	
	if( selectex[0] != '\0' ) { /* process against selection condition if any.. */
		stat = do_select( selectex, i, &result );
		if( stat != 0 ) { Eerr( stat, "Select error", selectex ); continue; }
		if( result == 0 ) continue; /* reject */
		}
	val = fda( i, ifld, axis );
	if( Econv_error() ) {  /* will be true on bad data item */
		conv_msg( i, ifld, "datafield" );
		nbad++; 
		continue; 
		}
	if( val > max ) max = val;
	if( val < min ) min = val;
	if( Logmean == 1 ) {
		total += log(val);
		totsq += log(val) * log(val);
		}
	else if( Logmean == 2 ) {
		total += log(val+1.0);
		totsq += log(val+1.0) * log(val+1.0);
		}
	else	{
		total += val;
		totsq += val * val;
		}
	n++;
	if( !Skipmed ) {
		if( n <= PLVsize-1 ) PLV[n] = val;
		else if( n > PLVsize-1 ) 
			Eerr( 248, "Cannot compute median - capacity exceeded (raise using -maxvector)\n", "" );
		}
	}

mean = total / (double)n;
if( n > 1 ) stddev = sqrt( (totsq-( total*total /(double)n )) / (double)(n - 1)) ;
else stddev = 0.0;


/* PLV[0] will not be used.  PLV[n] holds last value. */

/* sort */
if( !Skipmed ) qsort( &PLV[1], n, sizeof(double), dblcompare);

stats[0] = (double) n;
stats[1] = mean;
stats[2] = stddev;
stats[3] = min;
if( !Skipmed ) {
  stats[4] = (n % 20 ) ? PLV[(n/20) + 1] :  (PLV[n/20] + PLV[(n/20) + 1] ) /2.0 ;  /* 5th */
  stats[5] = ( n % 4 ) ?  PLV[(n/4) + 1]  :  (PLV[n/4] + PLV[(n/4) + 1])/2.0 ;      /* 25 */
  stats[6] = ( n % 2 ) ?  PLV[(n+1) / 2]  :  (PLV[n/2] + PLV[(n/2)+1])/2.0 ;         /* 50 */
  stats[7] = ( n % 4 )  ? PLV[n - (n/4)]  :  (PLV[(n+1) - (n/4)] + PLV[n-(n/4)])/2.0 ;   /* 75 */
  stats[8] = ( n % 20 ) ? PLV[n - (n/20)] : (PLV[(n+1) - (n/20)] + PLV[n - (n/20)]) / 2.0 ;  /* 95 */
  }
stats[9] = max;
stats[10] = (double) nbad;
stats[11] = total; 

if( Skipmed ) return( 0 );

/* find nearest inside values for 1.5XIRQ tails.. */
Iqr = stats[7] - stats[5];
loend = stats[5] - (Iqr*1.5);
hiend = stats[7] + (Iqr*1.5);



/* NOTE: PLV values are in cell 1 THROUGH n */

/* find low tail.. */
for( i = 1; i < n; i++ ) {
	if( PLV[i] >= loend && PLV[i] <= stats[5] ) {  /* (2nd part of exp handles degen case) */
		Lotail = PLV[i];
		break;
		}
	}
if( i == n ) Lotail = stats[5]; /* handle degenerate case */

/* find hi tail.. */
for( i = n; i >= 1; i-- ) { 
	if( PLV[i] <= hiend && PLV[i] >= stats[7] ) {  /* (2nd part of exp handles degen case) */
		Hitail = PLV[i];
		break;
		}
	}
if( i == 0 ) Hitail = stats[7]; /* handle degenerate case */
	

return( 0 );
}


/* ============================= */

static int
dblcompare( a, b )
const void *a, *b;

/* static int dblcompare( f, g )
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
