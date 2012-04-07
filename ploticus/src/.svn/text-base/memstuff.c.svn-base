/* ======================================================= *
 * Copyright 1998-2005 Stephen C. Grubb                    *
 * http://ploticus.sourceforge.net                         *
 * Covered by GPL; see the file ./Copyright for details.   *
 * ======================================================= */

/* misc routines related to pl data structures */

/* warning: the *dataset routines will alter PL_bigbuf  */

#include "pl.h"
#include <string.h>

static int buflen;
static int nfields, prevnfields, newds;
static int highwater = -1;

extern int TDH_midriff_flag;
extern int PLGG_initstatic(), PLGP_initstatic(), PLGS_initstatic(), PLGF_initstatic();

/* ================================== */
/* INIT_MEM - initialize pl data structures */
int
PL_init_mem()
{
PLD.datarow = (char **) malloc( PLD.maxrows * sizeof( char * ) );
PLD.df = (char **) malloc( PLD.maxdf * sizeof( char * ) );
PLL.procline = (char **) malloc( PLL.maxproclines * sizeof( char * ) );
PLV = (double *) malloc( PLVsize * sizeof( double ));
PLVhalfsize = PLVsize / 2;
PLVthirdsize = PLVsize / 3;
highwater = -1;
return( 0 );
}


/* ================================== */
/* INIT_STATICS - initialize static variables */
int
PL_init_statics()
{
PLG_cblock_initstatic();
PLG_init_initstatic();
PLG_mark_initstatic();
PLG_pcode_initstatic();
PLG_stub_initstatic();
PL_execline_initstatic();
PL_fieldnames_initstatic();
PL_units_initstatic();
PL_lib_initstatic();
PLP_bars_initstatic();
PLP_getdata_initstatic();
PLP_legend_initstatic();
PLP_processdata_initstatic();
PLP_rangebar_initstatic();
#ifndef NOGD
PLGG_initstatic() ;
#endif
#ifndef NOPS
PLGP_initstatic();
#endif
#ifndef NOSVG
PLGS_initstatic();
#endif
#ifndef NOSWF
PLGF_initstatic();
#endif
/* no initstatic for X11 .. doesn't seem necessary now */

/* the following static initializations shouldn't be done if ploticus is being invoked
   from environments (eg midriff) where the TDH stuff is already in action.. */
if ( ! TDH_midriff_flag ) {
  GL_initstatic();
  TDH_condex_initstatics();
  TDH_err_initstatic();
  TDH_functioncall_initstatic();
  TDH_valuesubst_initstatic();
  TDH_setvar_initstatic();
  TDH_shell_initstatic();
  DT_initstatic();
  DT_time_initstatic();
  DT_datetime_initstatic();

  TDH_readconfig_initstatic();  /* some doubt on this one */
}


return( 0 );
}


/* ================================== */
/* FREE - free all mallocated memory.  */
int
PL_free( )
{
int i;

PL_clickmap_free();
PL_catfree();

free( PLD.df );

for( i = 0; i < PLD.currow; i++ ) free( PLD.datarow[ i ] );
free( PLD.datarow );

for( i = 0; i < PLL.nlines; i++ ) free( PLL.procline[ i ] );
free( PLL.procline );

free( PLV ); /* scg 5/16/03 */

return( 0 );
}

/* ================================= */
/* CHECKDS - indicate that the ds will be used, and check if ds has been used previously.
 *	If ds has been used previously, free datarow memory and set currow and curdf back.
 *	We don't attempt to free procline memory for embedded data sets.
 */
int
PL_checkds( ds )
int ds;
{
int i;
if( ds <= highwater ) {
	if( PLS.debug ) fprintf( PLS.diagfp, "obliterating data sets %d thru %d\n", ds, highwater );
	if( PLD.dsfirstrow[ ds ] >= 0 ) {
		for( i = PLD.dsfirstrow[ ds ]; i < PLD.currow; i++ ) free( PLD.datarow[i] ); /* free rows */
		PLD.currow = PLD.dsfirstrow[ ds ];
		}
	PLD.curdf = PLD.dsfirstdf[ ds ];
	}
if( ds > highwater ) highwater = ds;

return( 0 );
}


/* ================================ */
/* NEWDATASET - when building a new data set directly (eg. proc processdata), to initialize.
 *
 * Note, this doesn't advance PLD.curds.  This must be done after the
 * new data set has been completely built.
 */
int
PL_newdataset( )
{
newds = PLD.curds + 1;
if( newds >= MAXDS ) { PLS.skipout = 1; return( Eerr( 2358, "max number of data sets exceeded", "" )); }
PL_checkds( newds );  
PLD.nrecords[ newds ] = 0;
PLD.dsfirstdf[ newds ] = PLD.curdf;
PLD.dsfirstrow[ newds ] = PLD.currow;
return( 0 );
}


/* ================================ */
/* STARTDATAROW - when building a new data set directly, (eg. proc processdata) 
   this is used to indicate the start of a new data row.
 */

int
PL_startdatarow()
{
buflen = 0;
nfields = 0;
prevnfields = 0;
return( 0 );
}

/* ================================ */
/* CATITEM - when building a new data set directly, (eg. proc processdata) 
   this is used to append a new piece of data to the current row.
 */

int
PL_catitem( item )
char *item;
{
int len;
if( PLS.skipout ) return( 1 );
len = strlen( item );
strcpy( &PL_bigbuf[ buflen ], item );
buflen += len;
strcpy( &PL_bigbuf[ buflen ], "\t" );
buflen++;
nfields++;
return( 0 );
}

/* ================================ */
/* ENDDATAROW - when building a new data set directly, (eg. proc processdata) 
   this is used to terminate a data row.  The row is actually
   added to the pl data structures at this point.. 
 */

int
PL_enddatarow()
{
int i, state;
char *r;

if( PLS.skipout ) return( 1 );

r = (char *) malloc( buflen+1 );
if( r == NULL ) { PLS.skipout = 1; return( Eerr( 2378, "malloc error", "" )); }

if( PLD.currow >= (PLD.maxrows-1)) { PLS.skipout = 1; return( Eerr( 2380, "new data set truncated, too many rows", "" )); }

PLD.datarow[ PLD.currow++ ] = r;

strcpy( r, PL_bigbuf );

/* parse fields and assign data field pointers.. */
for( i = 0, state = 0; i < buflen; i++ ) {
	if( state == 0 ) { 
		if( PLD.curdf >= PLD.maxdf ) { 
			PLS.skipout = 1; 
			return( Eerr( 2381, "new data set truncated, too many fields overall", "" ));
			}
		PLD.df[ PLD.curdf++ ] = &r[i];
		state = 1;
		}
	if( r[i] == '\t' ) {
		r[i] = '\0';
		state = 0;
		}
	}

/* update nrecords and nfields */
(PLD.nrecords[ newds ])++;
if( prevnfields > 0 && nfields != prevnfields ) {
	PLS.skipout = 1;
	return( Eerr( 2379, "build data row: inconsistent nfields across rows", "" ));
	}
prevnfields = nfields;
PLD.nfields[ newds ] = nfields;

return( 0 );
}

/* ======================================================= *
 * Copyright 1998-2005 Stephen C. Grubb                    *
 * http://ploticus.sourceforge.net                         *
 * Covered by GPL; see the file ./Copyright for details.   *
 * ======================================================= */
