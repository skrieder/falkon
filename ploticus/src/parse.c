/* ======================================================= *
 * Copyright 1998-2005 Stephen C. Grubb                    *
 * http://ploticus.sourceforge.net                         *
 * Covered by GPL; see the file ./Copyright for details.   *
 * ======================================================= */

/* Parse data into fields by assigning char * pointers to beginning
   of each field, and inserting nulls into the data buffer. 
*/
#include "pl.h"
/* #define Eerr(a,b,c)  TDH_err(a,b,c) */

#define FILLCHAR 3
#define SPACECHAR 4
#define SPACE 's'
#define WHITESPACE 'w'
#define TAB 't'
#define COMMA 'c'


/* Notes:
   spacequoted allows "" to represent null field, and "John Adams" as a field
   with comma delimitation (.csv), embedded (") are represented as ("").  This routine
	converts these to ''
   nr parameter may be passed as > 0 to dictate number of fields per record
 */

/* ============================================ */
int
PL_parsedata( data, delimmethod, comsym, field, maxd, nr, nf, nd )
unsigned char *data; 	/* for LOCALE scg 3/15/00 */
/* char *data;  	// for LOCALE scg 3/15/00 //   this avoids gcc4 warning */
char *delimmethod;	/* one of: space (whitespace w/ quotes), whitespace (no quotes), tab, comma */
char *comsym;		/* user symbol signifying beginning of comment */
char *field[]; 		/* array of pointers to fields */
int maxd; 		/* max # of elements in above array */
int *nr; 		/* number of newline-delimited records - returned */
int *nf; 		/* number of fields per record - returned - but if this is passed as > 0, 
						it also dictates number of fields per record */
int *nd; 		/* total number of fields */
{

int i, j, ip, state, start, quotes, qon, firstline, reqnf, nfields, nrows, cslen, nt, lastbreak;
char delim, sepchar, tok[255];
int datalen; /* added scg 9/30/03 */


*nr = 0;
ip = 0;
if( *nf > 0 ) reqnf = *nf;
else reqnf = 0;

delim = tolower( delimmethod[0] );
if( ! GL_member( delim, "tcw")) delim = SPACE;

quotes = 0;
if( delim == SPACE ) { sepchar = ' '; quotes = 1; }
else if( delim == WHITESPACE ) { sepchar = ' '; delim = SPACE; }
else if( delim == TAB ) sepchar = '\t';
else if( delim == COMMA ) { sepchar = ','; quotes = 1; }


cslen = strlen( comsym );

datalen = strlen( data ); /* scg 9/30/03 */

/* do quote conversion if necessary .. */
if( quotes ) for( i = 0, qon = 0; i < datalen; i++ ) {
	if( data[i] == '\n' ) qon = 0; /* BOL - clean slate */
	if( data[i] == '"' ) {
		if( !qon ) qon = 1; 
		else qon = 0; 
		if( i > 0 && data[i-1] == FILLCHAR ) { 
			if( delim == SPACE ) data[i] = SPACECHAR;	  /* "" in whitespace is null field */
			else { data[i-1] = '\''; data[i] = '\''; }        /* convert "" -> '' */
			}
		else data[i] = FILLCHAR;
		}
	else if( qon ) {    /* mask separator characters found within a quoted string */
		if( delim == SPACE && isspace( data[i] ) ) data[i] = SPACECHAR;
		else if( delim == COMMA && data[i] == ',' ) data[i] = SPACECHAR;
		}
	}


/* now go thru data buffer one line at a time..  */
firstline = 1;  nrows = 0; lastbreak = -1;

/* condition of datalen+1 needed because the 'data' null terminator must be processed - scg 9/30/03 */
for( i = 0, start = 0; i < (datalen+1); i++ ) {

	if( data[i] == '\n' || data[i] == '\0' ) {

		if( i - lastbreak <= 1 ) break; /* don't do anything where we have newline followed immed. by null .. */
		lastbreak = i;


		/* process a line.. current line is from data[start] to null terminator.. */

		data[i] = '\0';

		/* skip blank and commented lines.. */
		if( delim == SPACE ) {
			nt = sscanf( &data[start], "%s", tok );
			if( nt < 1 || strncmp( tok, comsym, cslen )==0 ) { start = i+1; continue; }
			}
		else if( strncmp( &data[start], comsym, cslen )==0 ) { start = i+1; continue; }

		nrows++;
		state = 0;
		nfields = 0;

		if( delim == SPACE ) for( j = start; data[j] != '\0'; j++ ) {
			if( state == 0 ) {
				if( isspace( data[j] ) || data[j] == FILLCHAR ) continue; /* eat leading space */
				field[ip++] = &data[j];		   /* set pointer to field */
				nfields++;
				state = 1; 			   /* 1 = get field */
				}
			else if( state == 1 && ( isspace( data[j] ) || data[j] == FILLCHAR ) ) { /* terminate item */
				data[j] = '\0';
				if( reqnf > 0 && nfields >= reqnf ) break;
				state = 0;
				}
			else if( data[j] == SPACECHAR ) data[j] = sepchar;
			}

		else if( delim == TAB || delim == COMMA ) for( j = start; data[j] != '\0'; j++ ) {
			if( state == 0 ) {
				if( data[j] == FILLCHAR ) continue;
				field[ip++] = &data[j];
				nfields++;
				state = 1;
				}
			if( data[j] == sepchar ) { /* terminate item */
				data[j] = '\0';
				if( reqnf > 0 && nfields >= reqnf ) break;
				state = 0;
				}
			if( data[j] == FILLCHAR ) data[j] = '\0';
			if( state == 1 && data[j] == SPACECHAR ) data[j] = sepchar;
			}
		

		if( firstline && reqnf == 0 ) reqnf = nfields;
		else if( nfields != reqnf ) {
			if( nfields == 0 ) nrows--;  /* empty line -- reject */
			else for( j = nfields; j < reqnf; j++ ) field[ip++] = "";  /* fill in "" fields.. */
			}
		firstline = 0;

		/* finish up for current line.. */
		start = i+1;
		}
	}

*nf = reqnf;
*nr = nrows;
*nd = ip;

return( 0 );
}

/* ======================================================= *
 * Copyright 1998-2005 Stephen C. Grubb                    *
 * http://ploticus.sourceforge.net                         *
 * Covered by GPL; see the file ./Copyright for details.   *
 * ======================================================= */
