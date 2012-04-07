/* ======================================================= *
 * Copyright 1998-2005 Stephen C. Grubb                    *
 * http://ploticus.sourceforge.net                         *
 * Covered by GPL; see the file ./Copyright for details.   *
 * ======================================================= */

#include "pl.h"
#define MAXFIELDS 50

int
PLP_autorange( axis, specline, minval, maxval )
char axis;
char *specline; /* spec line from proc areadef.. */
char *minval;   /* determined plot minima.. */
char *maxval;   /* determined plot maxima.. */
{
int i, j;
int df[MAXFIELDS];
int ndf;
char nearest[80];
char buf[256];
char dfield[256];
double min, max, fmod();
char smin[80], smax[80];
double fval;
int stat;
double margin;
int ix;
char lowfix[80], hifix[80];
char unittyp[80];
char floatformat[20];
/* char datepart[40], timepart[40]; */
double incmult;
char tok[80];
char selex[256]; /* added */
int selresult; /* added */
int combomode, first;
double hiaccum, loaccum;
int goodfound;
double submin, submax;
double fabs(), floor();
int mininit, maxinit;
int ffgiven;



/* see what scaletype is being used.. */
Egetunits( axis, unittyp );
if( strcmp( unittyp, "linear" )==0 ) strcpy( nearest, "auto" );
else if ( GL_smemberi( unittyp, "date time datetime" )) strcpy( nearest, "datematic" );
else strcpy( nearest, "exact" ); /* categories? */

margin = 0.0;
strcpy( dfield, "" );
ix = 0;
strcpy( lowfix, "" );
strcpy( hifix, "" );
strcpy( floatformat, "%g" );  /* changed scg 10/1/03 .. scientific formats (e+ and e-) should be ok now */
ffgiven = 0;
incmult = 1.0;
strcpy( selex, "" ); /* added */
combomode = 0;
mininit = maxinit = 0;

while( 1 ) {
        strcpy( buf, GL_getok( specline, &ix ) );
        if( buf[0] == '\0' ) break;
        if( strnicmp( buf, "datafields", 10 )==0 ) strcpy( dfield, &buf[11] );
        else if( strnicmp( buf, "datafield", 9 )==0 ) strcpy( dfield, &buf[10] ); 
        else if( strnicmp( buf, "incmult", 7 )==0 ) incmult = atof( &buf[8] );
        else if( strnicmp( buf, "nearest", 7 )==0 ) strcpy( nearest, &buf[8] );
        else if( strnicmp( buf, "margin", 6 )==0 ) margin = atof( &buf[7] );
        else if( strnicmp( buf, "lowfix", 6 )==0 ) strcpy( lowfix, &buf[7] );
        else if( strnicmp( buf, "hifix", 5 )==0 ) strcpy( hifix, &buf[6] );
        else if( strnicmp( buf, "mininit", 7 )==0 ) { strcpy( lowfix, &buf[8] ); mininit = 1; }
        else if( strnicmp( buf, "maxinit", 7 )==0 ) { strcpy( hifix, &buf[8] ); maxinit = 1; }
        else if( strnicmp( buf, "numformat", 9 )==0 ) { strcpy( floatformat, &buf[10] ); ffgiven = 1; }
        else if( strnicmp( buf, "combomode", 9 )==0 ) {
		if( stricmp( &buf[10], "stack" )==0 ) combomode = 1;
		else if( stricmp( &buf[10], "hilo" )==0 ) combomode = 2;
		else combomode = 0;
		}
        else if( strnicmp( buf, "selectrows", 10 )==0 ) {
                strcpy( selex, &buf[11] );
                strcat( selex, &specline[ix] );
                break;
                }
	else Eerr( 5702, "unrecognized autorange subattribute", buf );
	}

/* fill array of df from comma-delimited list - contributed by Paul Totten <pwtotten@nortelnetworks.com> */
ndf = 0; ix = 0; 
while( 1 ) {
	GL_getseg( tok, dfield, &ix, "," );
	if( tok[0] == '\0' ) break;
	if( ndf >= (MAXFIELDS-1) ) break;
	df[ndf] = fref( tok ) - 1;
	if( df[ndf] < 0 || df[ndf] >= Nfields ) continue;
	ndf++;
	}

/* df = fref( dfield ) - 1; */

if( Nrecords < 1 ) return( Eerr( 17, "autorange: no data set has been read/specified w/ proc getdata", "" ) );
if( axis == '\0' ) return( Eerr( 7194, "autorange: axis attribute must be specified", "" ) );
if( ndf == 0 ) return( Eerr( 7194, "autorange: datafield omitted or invalid ", dfield ) );


/* ------------------ */
/* now do the work.. */
/* ----------------- */

/* override.. */
/* if( stricmp( nearest, "day" )==0 && stricmp( unittyp, "date" )==0 ) strcpy( nearest, "exact" ); */ /* removed scg 4/19/05 */

/* find data min and max.. */


/* initialize.. */
if( mininit ) min = Econv( axis, lowfix );
else min = PLHUGE;
if( maxinit ) max = Econv( axis, hifix );
else max = NEGHUGE;

for( i = 0; i < Nrecords; i++ ) {

        if( selex[0] != '\0' ) { /* added scg 8/1/01 */
                stat = do_select( selex, i, &selresult );
                if( selresult == 0 ) continue;
                }


	hiaccum = 0.0; loaccum = 0.0; first = 1; goodfound = 0; submin = PLHUGE; submax = NEGHUGE;

	for( j = 0; j < ndf; j++ ) {  /* for all datafields to be examined.. */
		fval = fda( i, df[j], axis );
		if( Econv_error() ) continue;
		goodfound = 1;
		if( !combomode ) {
			if( fval < submin ) submin = fval;
			if( fval > submax ) submax = fval;
			}
		else 	{
			hiaccum += fval;
			if( combomode == 2 ) { /* hilo */
				if( first ) { loaccum = fval; first = 0; }
				else loaccum -= fval;
				}
			else	{ /* stack */
				if( first ) { loaccum = fval; first = 0; }
				}
			}
		}
	if( !goodfound ) continue;

	if( combomode ) {
		if( loaccum < min ) min = loaccum;
		if( hiaccum > max ) max = hiaccum;
		}
	else	{
		if( submin < min ) min = submin;
		if( submax > max ) max = submax;
		}
	}


/* If user didn't specify "numformat", try to be "smart" about whether to use %g or %f for building result..         *
 * %g is usually best but in certain cases (high magnitude low variance data) %f should be used.  Added scg 8/10/05. */
if( !ffgiven && fabs( min ) > 100000 && fabs( max ) > 100000 && ( max - min < 1000 )) strcpy( floatformat, "%f" );


/* now convert min and max to current units and then to nearest interval.. */
if( strcmp( unittyp, "linear" )==0 ) { /* avoid using Euprint()- trouble w/ v. big or v. small #s */
	sprintf( smin, floatformat, min );
	sprintf( smax, floatformat, max );
	}
else	{
	Euprint( smin, axis, min, "" );
	Euprint( smax, axis, max, "" );
	}

/* save data min/max.. */
if( axis == 'x' ) {
	setcharvar( "DATAXMIN", smin );
	setcharvar( "DATAXMAX", smax );
	}
else 	{
	setcharvar( "DATAYMIN", smin );
	setcharvar( "DATAYMAX", smax );
	}


/* now adjust for margin.. */
min -= margin;
max += margin;


/* degenerate case.. all data the same (bad if it happens to lie on inc boundary, eg: 0) - added scg 9/21/01 */
if( min == max ) {
	/* min = min - 1.0; max = max + 1.0; */
	min -= fabs(min*0.2); max += fabs(max*0.2);  /* changed to work better w/ small magnitude values - scg 3/3/05 */
					             /* now uses fabs().. to handle degen case of a single neg. data value- scg 7/31/05 */
	if( min == max ) { min = min - 1.0; max = max + 1.0; } /* this kicks in if min=0 and max=0 - scg 7/6/05 */
	}


/* and do the conversion with margin.. */
if( strcmp( unittyp, "linear" )==0 ) { /* avoid using Euprint()- trouble w/ v. big or v. small #s */
	sprintf( smin, floatformat, min );
	sprintf( smax, floatformat, max );
	}
else	{
	Euprint( smin, axis, min, "" );
	Euprint( smax, axis, max, "" );
	}


/******* handle nearest=  ***********/

if( GL_slmember( nearest, "dat*matic" ) ) {
	char foo1[40], foo2[40], foo3[40], foo4[40], foo5[40], foo6[40];
	double dfoo1, dfoo2;
	/* get an automatic reasonable "nearest" value.. */
	DT_reasonable( unittyp, min, max, &dfoo1, foo1, foo2, foo3, foo4, foo5, &dfoo2, foo6, nearest );
	}


if( stricmp( nearest, "exact" )==0 ) {  /* exact */
	sprintf( minval, "%s", smin ); 
	sprintf( maxval, "%s", smax ); 
	}

else if( PLP_findnearest( smin, smax, axis, nearest, minval, maxval ) );


#ifdef CUT

else if( strnicmp( nearest, "month", 5 )== 0 || strnicmp( nearest, "quarter", 7 )==0 || strnicmp( nearest, "3month", 6 )==0 ) { 
	/* nearest month boundary / quarter-year boundary.. */
	int mon, day, yr, newmon;
	long l;
	if( !GL_smember( unittyp, "date datetime" )) 
		Eerr( 2892, "autorange 'nearest=month' or 'nearest=quarter' only valid with date or datetime scaletype", unittyp );
	/* min */
	stat = DT_jdate( smin, &l );
        DT_getmdy( &mon, &day, &yr );
	if( tolower( nearest[0] ) == 'q' || nearest[0] == '3' ) {
		if( mon >= 10 ) mon = 10;
		else if( mon >= 7 ) mon = 7;
		else if( mon >= 4 ) mon = 4;
		else if( mon >= 1 ) mon = 1;
		}
	DT_makedate( yr, mon, 1, "", datepart );
	if( strcmp( unittyp, "datetime" )==0 ) {
		DT_maketime( 0, 0, 0.0, timepart );
		DT_build_dt( datepart, timepart, minval );
		}
	else strcpy( minval, datepart );

	/* max */
	stat = DT_jdate( smax, &l );
        DT_getmdy( &mon, &day, &yr );
	if( tolower( nearest[0] ) == 'q' || nearest[0] == '3' ) {
		if( mon <= 3 ) mon = 4;
		else if( mon <= 6 ) mon = 7;
		else if( mon <= 9 ) mon = 10;
		else if( mon <= 12 ) mon = 13;
		}
	else mon ++;

        /* wrap around year.. */
        newmon = ((mon-1) % 12 ) +1;
        yr += ((mon-1) / 12);
        mon = newmon;
	DT_makedate( yr, mon, 1, "", datepart );
	if( strcmp( unittyp, "datetime" )==0 ) {
		DT_maketime( 0, 0, 0.0, timepart );
		DT_build_dt( datepart, timepart, maxval );
		}
	else strcpy( maxval, datepart );
	}


else if( strnicmp( nearest, "year", 4 )== 0 || strnicmp( nearest, "2year", 5 )==0 || 
	strnicmp( nearest, "5year", 5 )==0 || strnicmp( nearest, "10year", 6 )==0 ) { 
	int mon, day, yr;
	long l;
	int yearsblock; /* 0 5 or 10 */

	if( !GL_smember( unittyp, "date datetime" )) 
		Eerr( 2892, "autorange 'nearest=year' only valid with date or datetime scaletype", unittyp );

	if( tolower( (int) nearest[0] ) != 'y' ) {			/* this section scg 1/28/05 */
		yearsblock = nearest[0] - '0';
		if( yearsblock == 1 ) yearsblock = 10;
		}
	else yearsblock = 0;

	/* min */
	stat = DT_jdate( smin, &l );
        DT_getmdy( &mon, &day, &yr );
	if( yearsblock ) yr = (yr / yearsblock) * yearsblock; 		/* scg 1/28/05 */
	DT_makedate( yr, 1, 1, "", datepart );
	if( strcmp( unittyp, "datetime" )==0 ) {
		DT_maketime( 0, 0, 0.0, timepart );
		DT_build_dt( datepart, timepart, minval );
		}
	else strcpy( minval, datepart );
		
	/* max */
	stat = DT_jdate( smax, &l );
        DT_getmdy( &mon, &day, &yr );
	if( yearsblock ) yr = ((yr / yearsblock)+1) * yearsblock; 		/* scg 1/28/05 */
	else yr++;
	DT_makedate( yr, 1, 1, "", datepart );
	if( strcmp( unittyp, "datetime" )==0 ) {
		DT_maketime( 0, 0, 0.0, timepart );
		DT_build_dt( datepart, timepart, maxval );
		}
	else strcpy( maxval, datepart );
	}


else if( strnicmp( nearest, "day", 3 )== 0 || stricmp( nearest, "monday" )==0 || stricmp( nearest, "sunday" )==0 ) { 
	int mon, day, yr;
	double days, mins;

	if( !GL_smember( unittyp, "date datetime" )) 
		Eerr( 2892, "autorange 'nearest=day' only valid with date or datetime scaletype", unittyp );

	/* min */
	if( strcmp( unittyp, "datetime" )==0 ) DT_getdtparts( smin, datepart, timepart ); 
	else strcpy( datepart, smin ); /* if and else added scg 8/10/05 */

	if( tolower( nearest[0] ) == 'm' || tolower( nearest[0] ) == 's' ) {  /* adjust datepart back to a monday or sunday */
		int iwk;  char rbuf[40];
		DT_weekday( datepart, rbuf, &iwk ); /* rbuf not used */
		if( tolower( nearest[0] ) == 'm' ) { if( iwk == 1 ) iwk = 8; DT_dateadd( datepart, 2 - iwk, rbuf ); }
		else if( tolower( nearest[0] ) == 's' ) DT_dateadd( datepart, 1 - iwk, rbuf );
		strcpy( datepart, rbuf );
		}

	/* this is just a way to get the dt parts (?) ...
	 * DT_datetime2days( smin, &days ); 
         * DT_getmdy( &mon, &day, &yr );
	 * DT_makedate( yr, mon, day, "", datepart );
	 */

	if( strcmp( unittyp, "date" )==0 ) 
	/* check for biz day window.. scg 7/21/04 */
	mins = 0.0;
	DT_frame_mins( &mins ); /* adjust to any biz day window.. */
	DT_frommin( mins, timepart ); 
	if( strcmp( unittyp, "date" )==0 ) strcpy( minval, datepart );
	else DT_build_dt( datepart, timepart, minval );

	/* max */
	if( strcmp( unittyp, "datetime" )==0 ) DT_getdtparts( smax, datepart, timepart );
	else strcpy( datepart, smax );  /* if and else added scg 8/10/05 */

	if( tolower( nearest[0] ) == 'm' || tolower( nearest[0] ) == 's' ) {  /* adjust datepart to next monday or sunday */
		int iwk;  char rbuf[40];
		DT_weekday( datepart, rbuf, &iwk ); /* rbuf not used */
		if( tolower( nearest[0] ) == 'm' ) { if( iwk == 1 ) iwk = 8; DT_dateadd( datepart, 9 - iwk, rbuf ); }
		else if( tolower( nearest[0] ) == 's' ) DT_dateadd( datepart, 8 - iwk, rbuf );
		DT_build_dt( rbuf, timepart, smax );
		}

	DT_datetime2days( smax, &days );
	if( fabs( days - floor( days )) < 0.0001 ) ; /* avoid spurious extra day when data max is on date boundary added scg 7/21/04 */
	else days++;
	DT_days2datetime( days, smax ); 
	DT_datetime2days( smax, &days ); /* set next day's date for getmdy below */
        DT_getmdy( &mon, &day, &yr );
	DT_makedate( yr, mon, day, "", datepart );
	DT_maketime( 0, 0, 0.0, timepart );
	if( strcmp( unittyp, "date" )==0 ) strcpy( maxval, datepart );
	else DT_build_dt( datepart, timepart, maxval );
	}

else if( strnicmp( nearest, "hour", 4 )== 0 || strnicmp( nearest, "3hour", 5 )==0 ||
	 strnicmp( nearest, "6hour", 5 )==0 || strnicmp( nearest, "12hour", 6 )==0 ) {
	int hour, minute;
	double sec;
	int hoursblock; /* 0, 3, 6, or 12 */
	if( !GL_smember( unittyp, "time datetime" )) 
		Eerr( 2892, "autorange 'nearest=hour' is incompatible with scaletype", unittyp );

	if( tolower( (int) nearest[0] ) != 'h' ) {				/* this section scg 1/28/05 */
		hoursblock = nearest[0] - '0';
		if( hoursblock == 1 ) hoursblock = 12;
		}
	else hoursblock = 0;

	if( strcmp( unittyp, "time" )==0 ) {
		/* min */
		DT_tomin( smin, &sec ); /* sec not used */
		DT_gethms( &hour, &minute, &sec );
		if( hoursblock ) hour = (hour / hoursblock) * hoursblock; 		/* scg 1/28/05 */
		DT_maketime( hour, 0, 0.0, minval );
		/* max */
		DT_tomin( smax, &sec ); /* sec not used */
		DT_gethms( &hour, &minute, &sec );
		if( hoursblock ) hour = ((hour / hoursblock)+1) * hoursblock; 		/* scg 1/28/05 */
		if( minute != 0 || sec != 0.0 ) hour++; 				/* bug, scg 12/13/05 */
		DT_maketime( hour, 0, 0.0, maxval ); 
		}
	else if( strcmp( unittyp, "datetime" )==0 ) {
		double days;
		int mon, day, yr;

		/* min */
		DT_datetime2days( smin, &days );
		/* time part */
		DT_gethms( &hour, &minute, &sec );
		if( hoursblock ) hour = (hour / hoursblock) * hoursblock; 		/* scg 1/28/05 */
		DT_maketime( hour, 0, 0.0, timepart );
		/* date part */
        	DT_getmdy( &mon, &day, &yr );
		DT_makedate( yr, mon, day, "", datepart );
		DT_build_dt( datepart, timepart, minval );

		/* max */
		DT_datetime2days( smax, &days );
		/* time part */
		DT_gethms( &hour, &minute, &sec );
		if( hour == 23 ) {
			DT_days2datetime( days+1.0, smax ); /* set next day's date for getmdy below*/
			DT_datetime2days( smax, &days ); /* set next day's date for getmdy below*/
			DT_maketime( 0, 0, 0.0, timepart );					/* ok for any hoursblock */
			}
		else 	{
			if( hoursblock ) hour = ((hour / hoursblock)+1) * hoursblock; 		/* scg 1/28/05 */
			else hour++;
			DT_maketime( hour, 0, 0.0, timepart );
			}
		/* date part */
        	DT_getmdy( &mon, &day, &yr );
		DT_makedate( yr, mon, day, "", datepart );
		DT_build_dt( datepart, timepart, maxval );
		}
	}

else if( stricmp( nearest, "minute" )==0 || stricmp( nearest, "10minute" )==0 || 
	stricmp( nearest, "20minute" )==0 || stricmp( nearest, "30minute" )==0 ) {		/* this section scg 1/28/05 */
	int hour, minute, minblock;
	double sec;

	if( strcmp( unittyp, "time" )!= 0 ) Eerr( 2892, "autorange 'nearest=minute' is incompatible with scaletype", unittyp );

	if( tolower( (int) nearest[0] ) != 'm' ) minblock = (nearest[0] - '0') * 10;
	else minblock = 0;

	/* min */
	DT_tomin( smin, &sec ); /* sec not used */
	DT_gethms( &hour, &minute, &sec );
	if( minblock ) minute = (minute / minblock) * minblock; 		
	DT_maketime( hour, minute, 0.0, minval );

	/* max */
	DT_tomin( smax, &sec ); /* sec not used */
	DT_gethms( &hour, &minute, &sec );
	if( minblock ) minute = ((minute / minblock)+1) * minblock; 	
	else minute++;
	if( minute >= 60 ) { minute = minute % 60; hour++; }
	DT_maketime( hour, minute, 0.0, maxval );
	}


#endif
else 	{      /* this section added scg 7/5/00 */
	double inc, h, fmod(), a, b;

	if( strcmp( nearest, "auto" )==0 ) PL_defaultinc( min, max, &inc );
	else inc = atof( nearest );


	h = fmod( min, inc );

	a = (min - h) - inc;
	b = a - inc;    /* include one extra inc on low end */
	if( a >= 0.0 && b < 0.0 ) b = a;  /* but don't dip below 0 */

	if( min < 0.0 ) sprintf( minval, floatformat, (min - h) - (inc * incmult) ); /* include extra inc on low end - scg 11/29/00 */
	else 	{
		a = min - h;
		b = a - (inc*(incmult-1.0)); /* include extra inc on low end - 11/29 */
		if( a >= 0.0 && b < 0.0 ) b = a;  /* but don't dip below 0  - 11/29 */
		sprintf( minval, floatformat, b );
		}

	h = fmod( max, inc );
	if( max < 0.0 ) sprintf( maxval, floatformat, (max - h) + (inc*(incmult-1.0)) ); /* include extra inc on high end - 11/29 */
	else sprintf( maxval, floatformat, (max - h) + (inc * incmult) ); /* extra inc - 11/29 */
	}



/* lowfix and hifix overrides.. */
if( lowfix[0] != '\0' && !mininit ) strcpy( minval, lowfix );
if( hifix[0] != '\0' && !maxinit ) strcpy( maxval, hifix );

/* be sure result makes sense.. for instance, if lowfix=0 specified but data are all negative,
	min will be 0 but max will be eg. -3.... patch that up here to avoid areadef crash&burn 
	(automated situations) - scg 3/25/04 */
if( GL_slmember( unittyp, "linear log*" ) && atof( maxval ) <= atof( minval ) ) {
	Eerr( 5709, "autorange: all data out of range", "" );
	strcpy( minval, "0" );
	strcpy( maxval, "1" );
	}

if( PLS.debug )
  fprintf( PLS.diagfp, "Autorange on %c: min=%s to max=%s\n", axis, minval, maxval);

DT_suppress_twin_warn( 1 ); /* suppress complaints about datetime outside of window 
				until after areadef */
return( 0 );
}

/* ======================================================= *
 * Copyright 1998-2005 Stephen C. Grubb                    *
 * http://ploticus.sourceforge.net                         *
 * Covered by GPL; see the file ./Copyright for details.   *
 * ======================================================= */
