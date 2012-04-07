/* ======================================================= *
 * Copyright 1998-2005 Stephen C. Grubb                    *
 * http://ploticus.sourceforge.net                         *
 * Covered by GPL; see the file ./Copyright for details.   *
 * ======================================================= */

/* PROC GETDATA - get some data.  Data may be specified literally, or
	gotten from a file or a command.  */

#include "pl.h"
#include "tdhkit.h"
#include <string.h>

#define COMMANDMAX 1024

static int do_filter();

static int specialmode = 0, showresults = 0;
static char pathname[MAXPATH] = "";
static int nscriptrows = 0, scriptstart;

/* =============================== */
int
PLP_getdata_initstatic()
{
specialmode = showresults = 0;
strcpy( pathname, "" );
nscriptrows = 0;
return( 0 );
}

/* =============================== */
int
PLP_getdata( )
{
int i, j;
char buf[ MAXRECORDLEN ]; /* 2/21/01 */
char attr[NAMEMAXLEN], val[256]; 
char *line, *lineval;
int nt, lvp;

int stat;

char datafile[256];
char command[COMMANDMAX];
char commentchar[12];
FILE *dfp, *popen();
char delim_method[30];
char tok[256];
char selectex[256];
int standardinput;
int rotaterec;
int fieldnameheader;
int first;
int cclen, buflen;
int literaldata;
int reqnfields;
char *pfnames;

int datastart, ndatarows, irow;
char datasource;
char *row;
int nrecords, nfields, totalitems, foo;
int sqlflag;
int doing_set;
int nfldnames;

TDH_errprog( "pl proc getdata" );



/* initialize */
strcpy( datafile, "" );
if( !specialmode ) strcpy( pathname, "" );
strcpy( command, "" );
strcpy( commentchar, "//" );
strcpy( delim_method, "space" );
if( !specialmode ) showresults = 0;
strcpy( selectex, "" );
standardinput = 0;
rotaterec = 0;
strcpy( PL_bigbuf, "" );
fieldnameheader = 0;
literaldata = 0;
nscriptrows = 0;
reqnfields = 0;
pfnames = NULL;
sqlflag = 0;
nfldnames = 0;


/* get attributes.. */
first = 1;
if( !specialmode ) while( 1 ) {
	line = getnextattr( first, attr, val, &lvp, &nt );
	if( line == NULL ) break;
	first = 0;
	lineval = &line[lvp];


	if( stricmp( attr, "file" )==0 ) {
		if( PLS.cgiargs != NULL ) strcpy( pathname, lineval );
		else	{
#ifdef WIN32
			strcpy( pathname, lineval ); /* to avoid invoking 'cat' .. */ /* changed to lineval  scg 10/16/03 */
#else
			strcpy( datafile, val );
#endif
			}
		}

	else if( stricmp( attr, "pathname" )==0 ) strcpy( pathname, lineval );

	else if( stricmp( attr, "command" )==0 ) strcpy( command, lineval );
	else if( stricmp( attr, "commandmr" )==0 ) getmultiline( "commandmr", lineval, COMMANDMAX, command );

	else if( stricmp( attr, "sql" )==0 ) { strcpy( command, lineval ); sqlflag = 1; }
	else if( stricmp( attr, "sqlmr" )==0 ) { getmultiline( "sqlmr", lineval, COMMANDMAX, command ); sqlflag = 1; }

	else if( strnicmp( attr, "delim", 5 )==0 ) strcpy( delim_method, val );
	else if( stricmp( attr, "commentchar" )==0 ) strcpy( commentchar, val );

	else if( stricmp( attr, "data" )==0 ) {
		if( nt == 2 ) {
			datastart = PLL.curline-1;
			strcpy( PLL.procline[ PLL.curline-1 ], lineval ); /* remove 'data:' */
			}
		else datastart = PLL.curline;
		getmultiline( "data", lineval, 0, NULL );
		ndatarows = (PLL.curline - datastart) - 1;
		literaldata = 1;
		PL_holdmem( 1 ); /* tell execline() not to free this storage when the proc is done executing.. */
		}
	else if( stricmp( attr, "filter" )==0 ) {
		if( nt == 2 ) {
			scriptstart = PLL.curline-1;
			strcpy( PLL.procline[ PLL.curline-1 ], lineval ); /* remove 'filter:' */
			}
		else scriptstart = PLL.curline;
		getmultiline( "filter", lineval, 0, NULL ); /* use this to skip over filter script */
		nscriptrows = (PLL.curline - scriptstart) - 1;

		/* in all script lines, convert double slashes (##) to single slash.. */
		for( i = scriptstart; i < scriptstart+nscriptrows; i++ ) {
			for( j = 0; PLL.procline[i][j] != '\0'; j++ ) {
				if( PLL.procline[i][j] == '#' && PLL.procline[i][j+1] == '#' ) {
					PLL.procline[i][j] = ' ';
					break;
					}
				else if( !isspace( (int) PLL.procline[i][j] )) break;  /* no need to search entire line.. scg 11/25/02 */
				}
			}
		}

	else if( stricmp( attr, "showresults" )==0 ) {
		if( strnicmp( val, YESANS, 1 )==0 ) showresults = 1;
		else showresults = 0;
		}
	else if( stricmp( attr, "standardinput" )==0 ) {
		if( strnicmp( val, YESANS, 1 )==0 ) standardinput = 1;
		else standardinput = 0;
		}
	else if( stricmp( attr, "rotate" )==0 ) {
		if( strnicmp( val, YESANS, 1 )==0 ) rotaterec = 1;
		else rotaterec = 0;
		}
	else if( stricmp( attr, "select" )==0 ) strcpy( selectex, lineval );
	else if( stricmp( attr, "nfields" )==0 ) reqnfields = atoi( val );
	else if( stricmp( attr, "fieldnames" )==0 ) nfldnames = definefieldnames( lineval ); 
	else if( stricmp( attr, "fieldnamerows" )==0 ) {
		getmultiline( "fieldnamerows", lineval, MAXBIGBUF, PL_bigbuf );  
		nfldnames = definefieldnames( PL_bigbuf );
		}
	else if( stricmp( attr, "pf_fieldnames" )==0 ) pfnames = lineval;

	else if( stricmp( attr, "fieldnameheader" )==0 ) {
		if( strnicmp( val, YESANS, 1 )==0 ) fieldnameheader = 1;
		else fieldnameheader = 0;
		}

	else Eerr( 1, "attribute not recognized", attr );
	}

if( specialmode ) strcpy( delim_method, "tab" );



/* now do the work.. */


PLP_processdata_initstatic();   /* reset proc_processdata "break" pointer.. scg 8/4/04 */


if( !GL_slmember( delim_method, "s* c* t* w*" )) {
	Eerr( 5839, "Warning, invalid delim specification, using 'space'", delim_method );
	strcpy( delim_method, "space" );
	}
	

/* determine source of data.. */
if( sqlflag ) datasource = 'q';

else if( command[0] != '\0' ) datasource = 'c';					/* shell command */

else if( literaldata ) {
	if( nscriptrows > 0 ) Eerr( 5792, "Warning, filter ignored (it cannot be used with in-script data)", "" );
	datasource = 'd';    							/* in-script data statement */
	}

else if( standardinput || strcmp( datafile, "-" ) ==0 || 			/* pathname added scg 5/24/05 */
	strcmp( pathname, "-" )==0 ) datasource = 's'; 				/* stdin */

else if( datafile[0] != '\0' ) { 						/* shell-expandable file name */
	sprintf( command, "cat \"%s\"", datafile ); 
	datasource = 'c'; 
	} 

else if( pathname[0] != '\0' ) datasource = 'p'; 				/* explicit full pathname */

else { PLS.skipout = 1; return( Eerr( 407, "No data, file, or command was specified", "" ) ); }


/* get ready to loop thru data.. */
if( datasource != 'd' ) { 
	datastart = 0; 
	ndatarows = 99999999; 
	}
if( datasource == 'p' ) {  /* 'pathname' given.. */
	dfp = fopen( pathname, "r" );
	if( dfp == NULL ) { PLS.skipout = 1; return( Eerr( 401, "Cannot open data file", pathname ) ); }
	}
else if( datasource == 's' ) dfp = stdin;
else if( datasource == 'c' ) {
	if( PLS.noshell ) return( Eerr( 401, "-noshell prohibits #proc getdata command", "" ) );
	dfp = popen( command, "r" );
	if( dfp == NULL ) { PLS.skipout = 1; return( Eerr( 401, "Cannot execute command", command ) ); }
	}



/* get the PLD data structure ready.. */

PL_checkds( 0 ); 
PLD.curds = 0;
if( PLS.debug ) fprintf( PLS.diagfp, "filling data set# 0\n" );
PLD.dsfirstdf[ 0 ] = PLD.curdf;
if( datasource == 'd' ) PLD.dsfirstrow[ 0 ] = -1;
else PLD.dsfirstrow[ 0 ] = PLD.currow;


/* handle embedded sql using db abstraction interface - see dbinterface.c */
if( sqlflag ) {
	char *fields[128];
	int blen, nf2;
	/* config file must have already been read.. */
	stat = TDH_sqlcommand( 0, command );
	if( stat != 0 ) return( Eerr( stat, "error on sql command", command ));
	/* get result field names and use these to set ploticus field names (what about joins?) */
	stat = TDH_sqlnames( 0, fields, &nfields );
	if( stat != 0 ) return( Eerr( stat, "error on sql result fieldnames", command ));
	for( i = 0, blen = 0; i < nfields; i++ ) {
		strcpy( &PL_bigbuf[blen], fields[i] ); blen += strlen( fields[i] );
		strcpy( &PL_bigbuf[blen], " " ); blen++;
		}
	PL_bigbuf[blen] = '\0';
	nfldnames = definefieldnames( PL_bigbuf );
	for( nrecords = 0; ; nrecords++ ) {
		if( PLD.curdf + nfields >= PLD.maxdf ) {
			Eerr( 406, "Data capture truncated (too many data fields; try raising -maxfields)", "" );
			break;
			}
		stat = TDH_sqlrow( 0, &PLD.df[ PLD.curdf ], &nf2 );
		if( stat > 1 ) return( Eerr( stat, "error on sql row retrieval", command ));
		if( stat != 0 ) break;
		if( nfields != nf2 ) return( Eerr( 461, "sql retrieval inconsistency", "" ));
		PLD.curdf += nf2;
		}
	goto READ_DONE; /* skip down to do the finish-up stuff.. */
	}


/* all other means of getting data use the following.. */

/* loop thru lines of data.. */
first = 1;
nrecords = 0;
cclen = strlen( commentchar );
for( irow = datastart; irow < datastart+ndatarows; irow++ ) {

	if( datasource != 'd' ) {
		if( fgets( buf, MAXRECORDLEN-1, dfp ) == NULL ) break;
		row = buf;
		}
	else row = PLL.procline[ irow ];


	/* skip empty lines */
	if( row[0] == '\n' || row[0] == '\0' ) continue;



	/* be careful w/ sscanf here.. with comma-delimited you can have very long lines with no whitespace */

	if( tolower( delim_method[0] ) == 'c' ) {		/* fixed 12-18-03 */
		/* skip comment lines */
		if( strncmp( row, commentchar, cclen )==0 ) continue;
		/* (lines containing nothing but whitespace are illegal with comma delim) */
		}
	else	{
		/* skip lines containing nothing but whitespace chars.. */
		/* and skip comment lines */
		nt = sscanf( row, "%s", tok );
		if( nt < 1 ) continue;	
		if( strncmp( tok, commentchar, cclen )==0 ) continue;
		}

	buflen = strlen( row );

	if( datasource != 'd' && row[ buflen-2 ] == 13 ) strcpy( &buf[ buflen-2 ], "\n" ); /* DOS LF */

	/* look for #set.. */
	doing_set = 0;
	for( j = 0; row[j] != '\0'; j++ ) if( strncmp( &row[j], "#set ", 5 )== 0 ) { doing_set = 1; break; }

	/* #set var = value .. this can be used in data files.. - added scg 11/13/00 */
	if( doing_set ) {
		int ix, oldix;
		char varname[40];
		ix = 0;
		GL_getchunk( tok, row, &ix, " 	" ); /* #set */
		GL_getchunk( varname, row, &ix, " 	=" ); /* varname */
		oldix = ix;
		GL_getchunk( tok, row, &ix, " 	" ); /* optional = */
		if( strcmp( tok, "=" ) != 0 ) ix = oldix;
		row[ buflen - 1 ] = '\0'; /* strip off trailing newline - scg 7/27/01 */
		buflen--;
		if( row[ix+1] == '"' ) TDH_setvar( varname, &row[ix+2] );
		else TDH_setvar( varname, &row[ix+1] );
		continue;
		}


	/* field name header.. */
	if( first && fieldnameheader ) {
		nfldnames = definefieldnames( row );  /* takes whitespace or comma delimited.. */
		first = 0;
		continue;
		}
	first = 0;

	
	/* if field names given and nfields not given, set expected # fields using # field names.. scg 3/15/06 */
	if( reqnfields <= 0 && nfldnames > 0 ) reqnfields = nfldnames; 


	/* optional select */
	if( selectex[0] != '\0' ) { 
		stat = do_filter( row, selectex, delim_method, 1 ); /*doesn't modify row*/
		if( ! stat ) continue;
		}

	/* optional filter data processing.. */
	if( datasource != 'd' && nscriptrows > 0  ) {
		do_filter( buf, "", delim_method, 0 ); /* modifies row */
		if( buf[0] == '\0' ) continue;  	/* nothing printed, skip row.. added scg 3/19/03 */
		buflen = strlen( buf ); 		/* because row has been modified above */
		if( buf[ buflen -1 ] != '\n' ) strcpy( &buf[ buflen-1 ], "\n" );
		}

	/* if we reach here, keep the line.. */

	if( datasource != 'd' ) {
		/* copy the row into malloced storage.. */
		if( PLD.currow >= PLD.maxrows ) { 
			Eerr( 429, "Data input truncated (too many data rows; try raising -maxrows)", "" );
			break;
			}
		row = (char *) malloc( buflen+1 );
		if( row == NULL ) return( err( 2480, "malloc error", "" ) );
		strcpy( row, buf );
		PLD.datarow[ PLD.currow++ ] = row;
		}

	/* parse the row into fields.. */
	if( reqnfields > 0 ) nfields = reqnfields;
	else if( nrecords == 0 ) nfields = 0;
	stat = PL_parsedata( row, delim_method, commentchar, &(PLD.df[ PLD.curdf ]), MAXITEMS, &foo, &nfields, &totalitems );
	if( stat != 0 ) { PLS.skipout = 1; return( Eerr( stat, "Parse error on input data.", "" )); }

	PLD.curdf += nfields;

	if( PLD.curdf + nfields >= PLD.maxdf ) {
		Eerr( 406, "Data input truncated (too many data fields; try raising -maxfields)", "" );
		break;
		}

	nrecords++;
	}
	

if( datasource == 's' ) ;
else if( datasource == 'p' ) fclose( dfp );
else if( datasource == 'c' ) pclose( dfp );

READ_DONE:

Nrecords = nrecords;		/* Nrecords is a macro - see pl.h */
setintvar( "NRECORDS", nrecords );

Nfields = nfields;		/* Nfields is a macro - see pl.h */
setintvar( "NFIELDS", nfields );

if( rotaterec ) {   /* swap nrecords and nfields */
	if( nrecords != 1 ) Eerr( 5798, "rotate cannot be done if more than 1 row of data", "" );
	else	{
		Nrecords = nfields;
		Nfields = nrecords;
		}
	}


/* if( nrecords == 0 ) fprintf( PLS.diagfp, "warning: no data fields found..\n" ); */ /* pulling this.. scg 3/25/04 */
if( PLS.debug ) fprintf( PLS.diagfp, "getdata has read %d records; there are %d fields per record.\n", Nrecords, Nfields );

if( showresults ) {
	if( specialmode ) fprintf( PLS.diagfp, "// proc processdata produced the following:\n" );
	else fprintf( PLS.diagfp, "// proc getdata has read & parsed these data:\n" );
 	for( i = 0; i < Nrecords; i++ ) {
 		for( j = 0; j < Nfields; j++ ) fprintf( PLS.diagfp, "[%s]", da(i,j) );
		fprintf( PLS.diagfp, "\n" );
 		}
	}
if( nscriptrows > 0 && pfnames != NULL ) definefieldnames( pfnames ); /* assign any post-filter field names.. */


return( 0 );
}
/* ======================================== */
/* DO_FILTER - implement 'select' and 'filter'.
   This needs to be reworked again sometime for efficiency.. 
 */

static int
do_filter( buf, scriptname, delim, mode )
char *buf;
char *scriptname;
char *delim;
int mode; /* 0 = filter    1 = select */
{
int stat;
char recordid[80]; 
char data[MAXITEMS][DATAMAXLEN+1];
char *df[MAXITEMS];
char str[MAXRECORDLEN], str2[MAXRECORDLEN]; /* size increased from 255  scg 6/27/01 */
int nfields, nrecords, nd;
int i;
char commentchar[12];
struct sinterpstate ss;

strcpy( recordid, "" ); /* not used */
strcpy( commentchar, "//" ); /* not used? */

/* split up buf into fields.. */
strcpy( str, buf );
nfields = 0;
PL_parsedata( str, delim, commentchar, df, MAXITEMS, &nrecords, &nfields, &nd ); 

if( mode == 1 ) { /* condex processing.. */
	strcpy( str2, str );
	stat = PL_value_subst( str2, scriptname, df, FOR_CONDEX );
	if( stat > 1 ) Eerr( 2208, "value_subst error", scriptname );
	stat = TDH_condex( str2, 0 );
	return( stat );
	}

/* for sinterp we need to copy data into array.. */
for( i = 0; i < MAXITEMS; i++ ) strcpy( data[i], "" ); /* null out data array.. added scg 11/15/00 */
for( i = 0; i < nfields; i++ ) strcpy( data[i], df[i] );



stat = TDH_sinterp_openmem( &(PLL.procline[ scriptstart ]), nscriptrows, &ss ); /* scriptstart & nscriptlines set above.. */
if( stat != 0 ) return( Eerr( stat, "filter script error", "" ) );

/* do filter processing.. */
strcpy( buf, "" );
while( 1 ) {
	stat = TDH_sinterp( str, &ss, recordid, data );
        if( stat > 255 ) return( Eerr( 169, "filter script error.. quitting..", "" ) );
        else if( stat != SINTERP_MORE ) break;
	strcat( buf, str ); /* strcat ok */
	}
return( 0 ); /* return results in buf */
}


/* ===================================== */
/* GETDATA_SPECIALMODE - allow getdata to read in a data file without accessing 
	attribute list.  Used by proc processdata.  TAB delimitation is always
	used when operating in special mode */
int
PL_getdata_specialmode( mode, dfn, showr )
int mode;
char *dfn;
int showr;
{
specialmode = mode;
strcpy( pathname, dfn );
showresults = showr;
return( 0 );
}

/* ======================================================= *
 * Copyright 1998-2005 Stephen C. Grubb                    *
 * http://ploticus.sourceforge.net                         *
 * Covered by GPL; see the file ./Copyright for details.   *
 * ======================================================= */
