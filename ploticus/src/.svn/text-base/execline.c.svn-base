/* ======================================================= *
 * Copyright 1998-2005 Stephen C. Grubb                    *
 * http://ploticus.sourceforge.net                         *
 * Covered by GPL; see the file ./Copyright for details.   *
 * ======================================================= */

#include "pl.h"
#include "tdhkit.h"

#define NONDRAWINGPROCS "page print originaldata usedata tabulate getdata defineunits\
 rangebar catslide processdata settings endproc"


static int execline_init = 0;
static int lastbs; 	      /* indicates that previous line ended with a backslash, indicating continuation.. */
static char procname[NAMEMAXLEN];
static char saveas_name[NAMEMAXLEN];
static char last_proctok[20]; /* either #proc or #procdef */
static char clone_name[NAMEMAXLEN];
static int nlhold;
static char clonelist[200];
static int holdmemflag = 0;
static int prevlineblank = 0;	/* prevent -echo from spitting out lots of adjacent blank lines */

static int proc_call();

/* ================================================================= */
int
PL_execline_initstatic()
{
execline_init = 0;
holdmemflag = 0;
prevlineblank = 0;
return( 0 );
}

/* ================================================================= */
/* EXECLINE - execute one ploticus script line.
 * Returns 0 if ok, or a non-zero error code 
 */

int
PL_execline( line )
char *line;	/* line of script file.. */   /* if const, new trailing newline and no #ifspec! (these modify line) */
{
int i, ix, stat;
char buf2[256];
int buflen, endproc, procstat;


if( !execline_init ) {
	PLS.npages = 0;
	lastbs = 0;
	strcpy( saveas_name, "" );
	strcpy( last_proctok, "" );
	strcpy( procname, "" );
	strcpy( clonelist, "" );

	/* proc line initializations.. */
	PLL.nobj = 0;
	PLL.nlines = nlhold = 0;

	execline_init = 1;
	}

if( PLS.skipout ) return( 1 ); /* a major error has occured;  don't process any more lines.. just return */

buflen = strlen( line );
if( line[ buflen-1 ] == '\n' ) { line[ buflen-1 ] = '\0'; buflen--; } /* don't keep trailing newline.. */
if( line[ buflen-1 ] == 13 ) { line[ buflen-1] = '\0'; buflen--; } /* DOS LF */


ix = 0; 
strcpy( buf2, GL_getok( line, &ix ) );

if( PLS.echolines && stricmp( buf2, "#ifspec" )!= 0 ) {
	if( prevlineblank && buf2[0] == '\0' ); /* multiple blank lines.. don't output */
	else if( PLS.echolines == 1 ) printf( "%s\n", line );
        else if( PLS.echolines == 2 ) fprintf( PLS.diagfp, "%s\n", line );
	if( buf2[0] == '\0' ) prevlineblank = 1;
	else prevlineblank = 0;
	}



/* intercept #endproc.. */
endproc = 0;
if( stricmp( buf2, "#endproc" )==0 ) { 
	strcpy( buf2, "#proc" ); 
	endproc = 1; 

	/* and add an additional blank line to terminate any last multline item.. */
	/* nlines>0 was added .. this caused seg fault on degenerate api case of 0 script lines 5/29/03 */
	if( PLL.nlines > 0 && PLL.nlines < PLL.maxproclines-1 ) {
		PLL.procline[ PLL.nlines ] = (char *) malloc( 5 ); 
		strcpy( PLL.procline[ PLL.nlines ], "\n" );
        	(PLL.nlines)++;
		}
	}


/*** #proc(def): get ready to capture next proc, and execute the proc that has just been read... */
if( strnicmp( buf2, "#proc", 5 )==0 && !lastbs ) {  /* #proc or #procdef break */

	procstat = 0;

	/* if #saveas was used, record the name.. */
	if( saveas_name[0] != '\0' )  strcpy( PLL.objname[ PLL.nobj ], saveas_name );

	/* get line count.. */
	PLL.objlen[ PLL.nobj ] = PLL.nlines - nlhold;

	/* if not first time around, and #proc was used (as opposed to #procdef), execute proc.. */
	if( stricmp( last_proctok, "#proc" )==0 ) {  

		/* proc page can do the Einit, or we can do it here.. */
		if( stricmp( procname, "page" )==0 ) PLS.eready = 1;
		if( !PLS.eready && !GL_slmember( procname, NONDRAWINGPROCS )) {
			stat = Einit( PLS.device );
			if( stat ) { PLS.skipout = 1; return( stat ); }
			Epaper( PLS.landscape );
			PLS.eready = 1;

			/* we are ready to draw.. safe to say page 1.. scg 11/28/00 */
			if( stricmp( procname, "page" )!=0 ) PLS.npages = 1;
			if( PLS.bkcolorgiven ) {
				/* EPS color=transparent - best to do nothing.. scg 1/10/00*/
				if( PLS.device == 'e' && strcmp( Ecurbkcolor, "transparent" )==0 ) ;
				else Eclr(); 
				}
			}


		/* execute the appropriate plotting procedure... */
		procstat = proc_call( procname );

		if( PLS.eready ) Eflush();  /* get output onto screen.. */
		}

	if( endproc ) strcpy( procname, "endproc" );
	else if( sscanf( line, "%*s %s", procname ) < 1 ) {
		Eerr( 24, "#proc must be followed by a procedure name", "" );
		procstat = 24;
		}
		

	/* if we're not told to hold on to the memory (used by getdata), and if
	   there were no #saveas.., then free the proc lines now..*/
	if( !holdmemflag && saveas_name[0] == '\0' ) {
		for( i = nlhold; i < PLL.nlines; i++ ) free( PLL.procline[i] ); 
	 	PLL.nlines = nlhold; 
		}
	else 	{
		if( PLL.nobj >= MAXOBJ-1 ) {
			Eerr( 25, "too many active procs - see limitations page MAXOBJ", "" );
			procstat = 25;
			}
		else (PLL.nobj)++;
		}
	holdmemflag = 0;

	strcpy( last_proctok, buf2 );

	if( procname[ strlen( procname ) - 1 ] == ':' ) procname[ strlen( procname ) - 1 ] = '\0';


	/* initialize to capture next proc */
	strcpy( saveas_name, "" );
	strcpy( clonelist, "" );
	strcpy( PLL.objname[ PLL.nobj ], "" );
	PLL.objstart[ PLL.nobj ] = PLL.nlines;
	nlhold = PLL.nlines;

	return( procstat );
	}


/****** for all other lines, get them, and add them to proc line list, looking for
 ****** special cases such as #clone and #saveas..  */

else 	{
	if( procname[0] == '\0' ) return( 0 ); /* ? */

	else 	{
		/* add lines to proc line list.. */
		/* also look for exceptions such as "#clone" */

		if( !lastbs && strnicmp( buf2, "#clone", 6 )==0 ) {
			strcpy( clone_name, "" );
			sscanf( line, "%*s %s", clone_name );
			if( clone_name[0] == '\0' ) {
				Eerr( 27, "#clone object name is missing", procname );
				return( 1 );
				}
			strcat( clonelist, clone_name );
			strcat( clonelist, " " );
			}

		else if( !lastbs && strnicmp( buf2, "#saveas", 7 )==0 ) sscanf( line, "%*s %s", saveas_name );


		else 	{
			/* buflen = strlen( line ); 
			 * if( line[ buflen-1 ] == '\n' ) buflen--; // don't keep trailing newline.. //
			 *** moved to top scg 5/20/03...
			 */


			/* #ifspec   scg 10/16/03 */
			if( !lastbs && strnicmp( buf2, "#ifspec", 7 )==0 ) {  /* #ifspec varname [attrname] */
				int nt;
				char varname[50], attrname[50], val[DATAMAXLEN+1];
				nt = sscanf( line, "%*s %s %s", varname, attrname );
				if( nt == 1 ) strcpy( attrname, varname );
				stat = TDH_getvar( varname, val );
				if( stat == 0 && val[0] != '\0' ) {
					sprintf( line, " %s: %s", attrname, val );
					if( PLS.echolines == 1 ) printf( "%s\n", line );
        				else if( PLS.echolines == 2 ) fprintf( PLS.diagfp, "%s\n", line );
					}
				else strcpy( line, "" );
				buflen = strlen( line );
				}

			PLL.procline[ PLL.nlines ] = (char *) malloc(  buflen+1 );
			strncpy( PLL.procline[ PLL.nlines ], line, buflen );
			PLL.procline[ PLL.nlines ][ buflen ] = '\0';

			if( PLL.nlines >= PLL.maxproclines-1 ) {
				PLS.skipout = 1; /* this is severe enough to abort mission.. */
				return( err( 28, "Script file - too many lines in current proc plus saved procs; try raising -maxproclines", "" ));
				}
			(PLL.nlines)++;
			if( line[ buflen - 2 ] == '\\' ) lastbs = 1;
			else lastbs = 0;
			}
		}

	return( 0 );
	}

}

/* ========================= */
/* HOLDMEM - allow other modules to tell execline() to not free the lines 
   for the current proc.. Used by getdata.
 */
int
PL_holdmem( stat )
int stat;
{
holdmemflag = stat;
return( 0 );
}

/* ========================= */
/* PROC_CALL - call the appropriate proc routine */
static int proc_call( procname )
char *procname;
{
int stat;
int n;
char attr[NAMEMAXLEN], val[256];
char *line;
int nt, lvp;
int first;

stat = 0;

if( PLS.debug ) { 
	if( strcmp( procname, "endproc" )==0 ) { fprintf( PLS.diagfp, "(endproc)\n" ); fflush( PLS.diagfp ); }
	else { fprintf( PLS.diagfp, "Executing %s\n", procname ); fflush( PLS.diagfp ); }
	}
first = 1;
while( 1 ) {
	line = getnextattr( first, attr, val, &lvp, &nt );
	if( line == NULL ) break;
	first = 0;
	}

if( stricmp( procname, "areadef" )==0 ) {
	stat = PLP_areadef();
	if( stat != 0 ) {
		PLS.skipout = 1;
		return( Eerr( 10, "cannot set up plotting area .. likely culprits: bad xrange or yrange, or bad area rectangle", "" ));
		}
	}
else if( stricmp( procname, "page" )==0 ) {
	stat = PLP_page();
	if( stat ) { PLS.skipout = 1; return( stat ); }
	}
else if( stricmp( procname, "xaxis" )==0 ) stat = PLP_axis( 'x', 0 );
else if( stricmp( procname, "yaxis" )==0 ) stat = PLP_axis( 'y', 0 );
else if( stricmp( procname, "getdata" )==0 ) stat = PLP_getdata();
else if( stricmp( procname, "lineplot" )==0 ) stat = PLP_lineplot();
else if( stricmp( procname, "bars" )==0 ) stat = PLP_bars();
else if( stricmp( procname, "rangebar" )==0 ) stat = PLP_rangebar();
else if( stricmp( procname, "scatterplot" )==0 ) stat = PLP_scatterplot();
else if( stricmp( procname, "vector" )==0 ) stat = PLP_vector();
else if( stricmp( procname, "venndisk" )==0 ) stat = PLP_venndisk();
else if( stricmp( procname, "annotate" )==0 ) stat = PLP_annotate();
else if( stricmp( procname, "legend" )==0 ) stat = PLP_legend();
else if( stricmp( procname, "curvefit" )==0 ) stat = PLP_curvefit();
else if( stricmp( procname, "rangesweep" )==0 ) stat = PLP_rangesweep();
else if( stricmp( procname, "pie" )==0 ) stat = PLP_pie();
else if( stricmp( procname, "drawcommands" )==0 ) stat = PLP_drawcommands();
else if( stricmp( procname, "line" )==0 ) stat = PLP_line();
else if( stricmp( procname, "rect" )==0 || stricmp( procname, "bevelrect" )==0 ) stat = PLP_rect();
else if( stricmp( procname, "tabulate" )==0 ) stat = PLP_tabulate();
else if( stricmp( procname, "transform" )==0 || stricmp( procname, "processdata" )==0 ) stat = PLP_processdata();
else if( stricmp( procname, "print" )==0 ) stat = PLP_print(); /* deprecated */
else if( stricmp( procname, "legendentry" )==0 ) stat = PLP_legendentry();
else if( stricmp( procname, "defineunits" )==0 ) stat = PLP_defineunits();
else if( stricmp( procname, "categories" )==0 ) stat = PLP_categories();
else if( stricmp( procname, "catslide" )==0 ) stat = PLP_categories(); /* PLP_catslide(); */
else if( stricmp( procname, "settings" )==0 || stricmp( procname, "datesettings" )==0 ) stat = PLP_settings();
else if( stricmp( procname, "originaldata" )==0 || stricmp( procname, "usedata" )==0 ) stat = PLP_usedata();
else if( stricmp( procname, "symbol" )==0 ) stat = PLP_symbol();
else if( stricmp( procname, "breakaxis" )==0 ) stat = PLP_breakaxis();
else if( stricmp( procname, "import" )==0 ) stat = PLP_import();
else if( stricmp( procname, "trailer" )==0 ) ; /* do nothing */
else if( stricmp( procname, "endproc" )==0 ) ; /* do nothing */
else return( Eerr( 101, "procedure name unrecognized", procname ) );

TDH_errprog( "pl" );

if( PLS.eready ) Eflush();
n = report_convmsgcount();
if( PLS.debug && n > 0 ) {
	fprintf( PLS.diagfp, "note: pl proc %s encountered %d unplottable data values\n", procname, n );
	zero_convmsgcount();
	}
return( stat );
}

/* ================================================================= */
/* GETNEXTATTR - serve up the next proc line, or NULL if no more */

char *
PL_getnextattr( flag, attr, val, linevalpos, nt )
int flag; /* 1 = first call for proc;  2 = getting multiline (don't get toks); any other value = nothing */
char *attr, *val;
int *linevalpos, *nt;
{
static int cloneix, stop, state;
static char *line;
int i, j, ix, alen;
char clone_name[NAMEMAXLEN];

/* states:  0 = init   1 = getting clone  2 = getting proc  3 = done */

if( flag == 1 ) {
	state = 0;
	cloneix = 0;
	}

if( state == 3 ) {
	line = NULL;
	return( line );
	}

if( state == 0 ) {  
	RETRY:
	strcpy( clone_name, GL_getok( clonelist, &cloneix ));
	if( clone_name[0] != '\0' ) {
		/* look up obj in list, starting with latest entry and working backward.. */
		for( j = (PLL.nobj)-1; j >= 0; j-- ) if( strcmp( PLL.objname[j], clone_name )==0 ) break;
		if( j < 0 ) {
			Eerr( 2506, "#clone object not found", clone_name );
			goto RETRY;
			}
		PLL.curline = PLL.objstart[j];
		stop = PLL.objstart[j] + PLL.objlen[j];
		state = 1;
		}
	else 	{
		PLL.curline = PLL.objstart[ PLL.nobj ];
		stop = PLL.nlines;
		state = 2;
		}
	}

if( state == 1 || state == 2 ) {
	RETRY2:
	if( PLL.curline >= PLL.nlines ) return( NULL );
	if( flag == 2 ) { /* multirow */
		for( i = 0; PLL.procline[ PLL.curline ][ i ] != '\0'; i++ ) 
			if( !isspace( (int) PLL.procline[ PLL.curline ][ i ] ) ) break;
		line = &(PLL.procline[ PLL.curline ][ i ]);
		}
	else	{  /* single row attr: val */
		line = PLL.procline[ PLL.curline ];
		ix = 0;
		strncpy( attr, GL_getok( line, &ix ), 38 ); /* it is possible for 1st token in line to be longer than
								38 chars (eg comma-delimited data) scg 6/26/02 */
		attr[38] = '\0';
		if( attr[0] == '\0' ) {   /* blank line.. skip */
			(PLL.curline)++;
			if( PLL.curline >= stop && state == 1 ) { 
				state = 0; 
				goto RETRY; 
				}
			else if( PLL.curline >= stop && state == 2 ) {
				state = 3; 
				return( NULL ); 
				}
			else goto RETRY2;
			}
		alen = strlen( attr );
		if( attr[ alen-1 ] == ':' ) attr[ alen-1 ] = '\0';
		if( attr[0] != '\0' ) while( line[ix] == ' ' || line[ix] == '\t' ) ix++; /* skip over ws */
		*linevalpos = ix;
		strcpy( val, GL_getok( line, &ix ) );
		if( val[0] != '\0' ) while( line[ix] == ' ' || line[ix] == '\t' ) ix++; /* skip over ws */
		if( attr[0] == '\0' ) *nt = 0;
		else if( val[0] == '\0' ) *nt = 1;
		else *nt = 2;
		}

	PLL.curline++;
	if( PLL.curline >= stop ) {
		if( state == 1 ) state = 0;
		else state = 3;
		}
	return( line );
	}
return( NULL );
}

/* ================================================================= */
/* GETMULTILINE - get a multi-line text item from script file.
   End is marked by an empty line.  Leading white space is removed from all lines.
   Normally, the multi-line text item is copied into 'result', which should be a buffer 
   of adequate size.  The result buffer size should be passed as 'maxlen'.  

   However, if 'maxlen' is passed as 0, this routine simply advances to the end of the
   item without doing any copying, (this is done in proc_getdata.c).

   Example of a multiline item:
	title:  Number of days difference 
	 	\
	        Subgroup: 1B

	frame: yes
*/
   
int
PL_getmultiline( parmname, firstline, maxlen, result )
char *parmname, *firstline;
int maxlen;
char *result;
{
char *line, buf[256];
int nt, i, len, foo, rlen, lonebs;

rlen = 0;

/* strip leading white space from first line.. */
if( maxlen > 0 ) {
	for( i = 0, len = strlen( firstline ); i < len; i++ )
		if( firstline[i] != ' ' && firstline[i] != '\t' ) break;

	if( i < len ) {
		sprintf( result, "%s\n", &firstline[i] );
		rlen = ( len - i ) + 1;
		}
	}


/* now get remaining lines.. */
while( 1 ) {
	line = getnextattr( 2, NULL, NULL, &foo, &foo );

	if( line == NULL ) break;
	if( line[0] == '\0' ) break;  /* first blank line terminates */

	if( maxlen == 0 ) continue; /* when just advancing.. */

	len = strlen( line );
	if( rlen + len > maxlen ) return( Eerr( 60, "Warning, multiline text truncated", parmname ) );
	
	/* detect lines having just a backslash.. */
	lonebs = 0;
	if( line[0] == '\\' ) {
		nt = sscanf( &line[1], "%s", buf );
		if( nt < 1 ) lonebs = 1;
		}

	if( lonebs ) {
		strcpy( &result[ rlen ], "\n" ); rlen += 1;
		}
	else	{
		/* allow backslash to represent start of text, so that leading blanks can be displayed  4/19/02 */
		for( i = 0; i < len; i++ ) {
			if( line[i] == '\\' && isspace( (int) line[i+1] ) ) continue; /* skip the backslash */
			else result[ rlen++ ] = line[i];
			}
		result[ rlen++ ] = '\n';
		result[ rlen ] = '\0';
		}
	}
return( 0 );
}


/* ======================================================= *
 * Copyright 1998-2005 Stephen C. Grubb                    *
 * http://ploticus.sourceforge.net                         *
 * Covered by GPL; see the file ./Copyright for details.   *
 * ======================================================= */
