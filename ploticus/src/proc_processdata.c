/* ======================================================= *
 * Copyright 1998-2005 Stephen C. Grubb                    *
 * http://ploticus.sourceforge.net                         *
 * Covered by GPL; see the file ./Copyright for details.   *
 * ======================================================= */

/* PROC PROCESSDATA - perform various processing to the data set */

#include "tdhkit.h" /* for MAXITEMS */
#include "pl.h"

extern int unlink();

#define MAXFLD MAXITEMS
#define MAXBREAKFLDS 5

static int rejfld[MAXFLD];
static int nrejfld;
static int kpfld[MAXFLD];
static int nkpfld;

static int stackmode = 1;
static FILE *outfp;
static int breakcurrow = 0;
static int eofcount = 0;

static int bor(), out(), eor(), dofld();
static int jadvance();

/* ================================= */
int
PLP_processdata_initstatic()
{
stackmode = 1;
breakcurrow = 0;
eofcount = 0;
return(0);
}


/* ================================= */

int
PLP_processdata( )
{
int i, j, k;
char attr[NAMEMAXLEN], val[256];
char *line, *lineval;
int nt, lvp;
int first;

char buf[256];
int stat;
char tmpfile[256];
char action[NAMEMAXLEN];
int showresults;
int nfld;
int fld[MAXFLD];
double accum[MAXFLD];
char rformat[40];
int keepall;
/* char outfmt[40]; */
char outbuf[256];
char selectex[256];
char tok[256];
int dispformatnum;
int resetbns;
char breakbuf[ MAXBREAKFLDS ][52];
double atof();
int outfile;
char curcon[256], startval[256];
double count;
int istart;
int select_result, select_error;
int nocloseoutfp;
char select1[256], select2[256];
char nacode[20];
int tagfld;

TDH_errprog( "pl proc processdata" );



/* initialize */
showresults = 0;
nfld = 0;
strcpy( rformat, "%g" );
keepall = 0;
nrejfld = 0;
nkpfld = 0;
strcpy( action, "" );
stackmode = 1;
strcpy( selectex, "" );
strcpy( select1, "" );
strcpy( select2, "" );
dispformatnum = 0;
strcpy( tmpfile, "" );
outfile = 0;
select_error = 0;
nocloseoutfp = 0;
strcpy( nacode, "=" );
tagfld = -1;


/* get attributes.. */
first = 1;
while( 1 ) {
	line = getnextattr( first, attr, val, &lvp, &nt );
	if( line == NULL ) break;
	first = 0;
	lineval = &line[lvp];

	if( stricmp( attr, "action" )==0 ) strcpy( action, val );
	else if( stricmp( attr, "fieldnames" )==0 ) definefieldnames( lineval );
	else if( strnicmp( attr, "field", 5 )==0 ) {
		int ix; ix = 0; i = 0; 
		while( 1 ) {
			strcpy( tok, GL_getok( lineval, &ix ) );
			if( tok[0] == '\0' ) break;
			fld[i] = fref( tok ) -1;
			i++;
			if( i >= MAXFLD ) break;
			}
		nfld = i;
		}
	else if( stricmp( attr, "keepfields" )==0 ) {
		int ix; ix = 0; i = 0; 
		while( 1 ) {
			strcpy( tok, GL_getok( lineval, &ix ) );
			if( tok[0] == '\0' ) break;
			kpfld[i] = fref( tok ) -1;
			i++;
			if( i >= MAXFLD ) break;
			}
		nkpfld = i;
		}
	else if( stricmp( attr, "rejectfields" )==0 ) {
		int ix; ix = 0; i = 0; 
		while( 1 ) {
			strcpy( tok, GL_getok( lineval, &ix ) );
			if( tok[0] == '\0' ) break;
			rejfld[i] = fref( tok ) -1;
			i++;
			if( i >= MAXFLD ) break;
			}
		nrejfld = i;
		}
	else if( stricmp( attr, "tagfield" )==0 ) tagfld = fref( val )-1;

	else if( stricmp( attr, "resultformat" )==0 ) strcpy( rformat, lineval );
	else if( stricmp( attr, "select" )==0 ) strcpy( selectex, lineval );
	else if( stricmp( attr, "leftselect" )==0 ) strcpy( select1, lineval );
	else if( stricmp( attr, "rightselect" )==0 ) strcpy( select2, lineval );
	else if( stricmp( attr, "missingdatacode" )==0 ) strcpy( nacode, val );
	else if( stricmp( attr, "showresults" )==0 ) {
		if( strnicmp( val, YESANS, 1 )==0 ) showresults = 1;
		else showresults = 0;
		}
        else if( stricmp( attr, "keepall" )==0 ) {
                if( strnicmp( val, YESANS, 1 ) ==0 ) keepall = 1;
                else keepall = 0;
                }
        else if( stricmp( attr, "stack" )==0 ) {
                if( strnicmp( val, YESANS, 1 ) ==0 ) stackmode = 1;
                else stackmode = 0;
                }
        else if( stricmp( attr, "outfile" )==0 ) {
                strcpy( tmpfile, val );
                outfile = 1;
                }

        else Eerr( 1, "attribute not recognized", attr );

	}

if( strcmp( action, "breakreset" )!= 0 ) {
	if( Nrecords < 1 ) return( Eerr( 17, "Current data set is empty, nothing to process", "" ) );
	}
	
if( GL_slmember( action, "per* tot* acc*") && nfld < 1 ) 
	return( Eerr( 2870, "one or more 'fields' must be specified with percents, totals, or accumulate", "" ) );

if( strcmp( action, "count" )==0 && ( nfld < 1 || nfld > 2 ) )
	return( Eerr( 2874, "'count' action requires one or two fields", "" ));

if( strcmp( action, "segment" )==0 && ( nfld < 1 || nfld > 2 ) )
	return( Eerr( 2874, "'segment' action requires one or two fields", "" ));

if( strcmp( action, "select" )== 0 && selectex[0] == '\0' ) 
	return( Eerr( 3879, "if action is 'select' a selection expression must be given", "" ));

/* if( strcmp( action, "select" )!= 0 && selectex[0] != '\0' )
 *	return( Eerr( 3880, "a selection expression may not be given unless action is select", "" ));
 */

/* tmp file specified, implies stack:no */
if( tmpfile[0] != '\0' ) stackmode = 0;


/* now do the work.. */
/* -------------------------- */

if( rformat[0] == 'n' ) {  /* if resultformat begins with 'n', user wants rewritenum to be applied.. */
	dispformatnum = 1; 
	strcpy( rformat, &rformat[1] );
	}
/* sprintf( outfmt, "\"%s\" ", rformat ); */  /* pulled scg 5/18/06, unsure why quoting was used.. */  
for( i = 0; i < MAXFLD; i++ ) accum[i] = 0.0;

if( stackmode ) {
	stat = PL_newdataset();
	if( stat != 0 ) return( stat );
	}
else	{
	if( !outfile ) sprintf( tmpfile, "%s_S", PLS.tmpname );
	if( stricmp( tmpfile, "stdout" )==0 ) {
		outfp = stdout;
		nocloseoutfp = 1;
		outfile = 1;
		}
	else if( stricmp( tmpfile, "stderr" )==0 ) {
		outfp = stderr;
		nocloseoutfp = 1;
		outfile = 1;
		}
	else outfp = fopen( tmpfile, "w" ); /* temp file, unlinked below */
	if( outfp == NULL ) return( Eerr( 2987, "Cannot open tmp file", tmpfile ) );
	}



/* break processing - calling script can detect when end is reached by looking at NRECORDS or BREAKFIELD1 */
if( strcmp( action, "breaks" )==0 ) { 
	int breakfound;
	char breakvarname[20];

	/* start at current row */
	if( breakcurrow >= Nrecords ) {
		eofcount++;
		if( eofcount > 10 ) {
			PLS.skipout = 1;
			return( Eerr( 4729, "unterminated loop (processdata action=breaks)", "" ) );
			}
		goto SKIPBREAK;
		}
	i = breakcurrow;

	/* save initial contents of break fields.. */
	/* also set vars BREAKFIELD1 .. N */
	for( j = 0; j < nfld; j++ ) {
		strncpy( breakbuf[j], da( i, fld[j] ), 50 );
		breakbuf[j][50] = '\0';
		sprintf( breakvarname, "BREAKFIELD%d", j+1 );
		setcharvar( breakvarname, breakbuf[j] );
		}
	for( ; i < Nrecords; i++ ) {

		if( selectex[0] != '\0' ) { /* process against selection condition if any.. */
               		stat = do_select( selectex, i, &select_result );
			if( stat ) select_error += stat;
                	if( select_result == 0 || stat ) continue; /* reject */
                	}

		/* compare against contents of break fields.. when any differences found, break.. */
		breakfound = 0;
		for( j = 0; j < nfld; j++ ) {
			if( strncmp( breakbuf[j], da( i, fld[j] ), 50 ) != 0 ) {
				breakfound = 1;
				break;
				}
			}

		if( breakfound ) break; 

		else 	{
			bor();
			for( j = 0; j < Nfields; j++ ) if( dofld( j )) out( da(i,j) );
			eor();
			}
		}
	breakcurrow = i;
	SKIPBREAK: ;
	}

/* breakreset */
else if( strcmp( action, "breakreset" )==0 ) {
	breakcurrow = 0;
	eofcount = 0;
	return( 0 );
	}


/* reverse */
else if( strncmp( action, "rev", 3 )==0 ) { 
	for( i = Nrecords-1; i >= 0; i-- ) {
		
		if( selectex[0] != '\0' ) { /* process against selection condition if any.. */
               		stat = do_select( selectex, i, &select_result );
			if( stat ) select_error += stat;
                	if( select_result == 0 || stat ) continue; /* reject */
                	}

		bor();
		for( j = 0; j < Nfields; j++ ) if( dofld( j )) out( da(i,j) );
		eor();
		}
	}


/* rotate */
else if( strncmp( action, "rot", 3 )==0 ) {     
	for( j = 0; j < Nfields; j++ ) {
		if( dofld( j )) {
			bor();
			for( i = 0; i < Nrecords; i++ ) {
				if( selectex[0] != '\0' ) { /* process against selection condition if any.. */
		               		stat = do_select( selectex, i, &select_result );
					if( stat ) select_error += stat;
		                	if( select_result == 0 || stat ) continue; /* reject */
		                	}
				out( da( i,j) );
				}
			eor();
			}
		}
	}


/* percents */
else if( strncmp( action, "per", 3 )==0 ) {    
	/* find all totals.. */
	for( i = 0; i < nfld; i++ ) {
		for( j = 0; j < Nrecords; j++ ) {
			
			if( selectex[0] != '\0' ) { /* process against selection condition if any.. */
	               		stat = do_select( selectex, i, &select_result );
				if( stat ) select_error += stat;
	                	if( select_result == 0 || stat ) continue; /* reject */
	                	}

			accum[i] += atof( da( j, fld[i] ) );
			}
		}

	for( i = 0; i < Nrecords; i++ ) {

		if( selectex[0] != '\0' ) { /* process against selection condition if any.. */
	              	stat = do_select( selectex, i, &select_result );
			if( stat ) select_error += stat;
	               	if( select_result == 0 || stat ) continue; /* reject */
	               	}

		bor();
		for( j = 0; j < Nfields; j++ ) {
			if( dofld( j )) {
				/* see if j is a 'hot' field */
				for( k = 0; k < nfld; k++ ) if( j == fld[k] ) break;
				if( k != nfld ) {
				    if( keepall ) out( da( i, j ) );
				    /* sprintf( outbuf, outfmt, (atof(da( i, j )) / accum[k]) * 100.0 ); */
				    sprintf( outbuf, rformat, (atof(da( i, j )) / accum[k]) * 100.0 ); /* changed scg 5/18/06 - quoted
														values not plottable */
				    out( outbuf );
				    }
				else out( da( i, j ) );
				}
			}
		eor();
		}
	}


/* accumulate */
else if( strncmp( action, "acc", 3 )==0 ) {     

	for( i = 0; i < Nrecords; i++ ) {

		if( selectex[0] != '\0' ) { /* process against selection condition if any.. */
	              	stat = do_select( selectex, i, &select_result );
			if( stat ) select_error += stat;
	               	if( select_result == 0 || stat ) continue; /* reject */
	               	}

		bor();
		for( j = 0; j < Nfields; j++ ) {
			/* see if j is a 'hot' field */
			if( dofld( j )) {
				for( k = 0; k < nfld; k++ ) if( j == fld[k] ) break;
				if( k != nfld ) {
				    accum[k] += atof( da( i, j ) );
				    if( keepall ) out( da( i, j ) );
				    /* out( da( i, j ) ); */
				    sprintf( tok, rformat, accum[k] ); /* fixed scg 10/1/03 */
				    out( tok ); /* fixed scg 10/1/03 */
				    }
				else out( da( i, j ) );
				}
			}
		eor();
		}
	}

/* count - may be used with one or two fields.
 * If one field, result has these fields: 1) field contents 2) count
 * If two fields, result has these fields: 1) field1 contents 2) sum of field 2 */

/* segment - may be used with one or two fields.
 * If one field, result has these fields: 1) field contents 2) beginning record# 3) ending record#
 * If two fields, result has these fields: 1) field1 contents 2) beginning record field2 value 3) ending record field2 value
 *
 * segmentb - same as segment, but segments butt up against each other (end point coincides with beginning point of next seg)
 */
else if( strncmp( action, "cou", 3 )==0 || strnicmp( action, "seg", 3 ) ==0 ) {    
	count = 0.0;
	strcpy( curcon, "" );
	for( i = 0; i < Nrecords; i++ ) {

		if( selectex[0] != '\0' ) { /* process against selection condition if any.. */
	              	stat = do_select( selectex, i, &select_result );
			if( stat ) select_error += stat;
	               	if( select_result == 0 || stat ) continue; /* reject */
	               	}

		if( strcmp( da( i, fld[0] ), curcon )!=0 ) {
			if( i == 0 ) { 
				strcpy( curcon, da( i, fld[0] ) ); 
				}
			else	{
				bor();
				out( curcon );
				if( action[0] == 's' ) {
					if( nfld == 2 ) {
						out( startval );
						if( action[7] == 'b' ) out( da( i, fld[1] ));
						else out( da( i-1, fld[1] ));
						}
					else	{
						sprintf( buf, "%d", istart ); 
						out( buf );
						if( action[7] == 'b' ) sprintf( buf, "%d", i+1 );
						else sprintf( buf, "%d", i ); 
						out( buf );
						}
					}
				else 	{ sprintf( buf, "%g", count ); out( buf ); }
				eor();
				strcpy( curcon, da( i, fld[0] ) );
				count = 0.0;
				}

			if( action[0] == 's' ) {
				if( nfld == 2 ) strcpy( startval, da( i, fld[1] )); 
				else istart = i+1;
				}
			}
		/* bug - counts off by one - moved from above the if .. scg 1/25/02 */
		if( action[0] == 'c' ) {
			if( nfld == 1 ) count = count + 1.0;
			else if( nfld == 2 && action[0] == 'c' ) count = count + atof( da( i, fld[1] ) );
			}
		}
	/* last round.. */
	bor();
	out( curcon );
	if( action[0] == 's' ) {
		if( nfld == 2 ) {
			out( startval );
			out( da( i-1, fld[1] ));
			}
		else	{
			sprintf( buf, "%d", istart ); out( buf );
			sprintf( buf, "%d", i ); out( buf );
			}
		}
	else 	{ sprintf( buf, "%g", count ); out( buf ); }
	eor();

	/* bor();
	 * out( curcon );
	 * sprintf( buf, "%g", count );
	 * out( buf );
	 * eor();
	 */
	}



/* total */
else if( strncmp( action, "tot", 3 )==0 )  {
	for( i = 0; i < nfld; i++ ) {
		for( j = 0; j < Nrecords; j++ ) {
			
			if( selectex[0] != '\0' ) { /* process against selection condition if any.. */
		              	stat = do_select( selectex, i, &select_result );
				if( stat ) select_error += stat;
		               	if( select_result == 0 || stat ) continue; /* reject */
		               	}

			accum[i] += atof( da( j, fld[i] ) );
			}
		}
	}


/* join */
else if( strcmp( action, "join" )==0 || strcmp( action, "leftjoin" )==0 || strcmp( action, "rightjoin" )==0 ) {
	int irec1, irec2, inprogress, diff, prec;
	char *f1, *f2;
	inprogress= 1;
	irec1 = irec2 = -1;
	jadvance( select1, &irec1, &inprogress );   /* advance LHS to first matching record */  
	if( inprogress ) jadvance( select2, &irec2, &inprogress );   /* advance RHS to first matching record */

	while( inprogress ) {
	
		/* compare all join fields.. when first difference encountered break; save strcmp diff */
		for( diff = 0, i = 0; i < nfld; i++ ) {
			f1 = da( irec1, fld[i] );
			f2 = da( irec2, fld[i] );
			/* if both are integers, do a numeric comparison.. */
			if( GL_goodnum( f1, &prec ) && GL_goodnum( f2, &prec )) diff = atoi( f1 ) - atoi( f2 );
			/* otherwise do a strcmp */
			else diff = strcmp( da( irec1, fld[i]),   da( irec2, fld[i] ));
			if( diff != 0 ) break;
			}

		if( diff == 0 ) {
			/* if diff == 0 then join left record with right record and output */
			bor();
			for( i = 0; i < Nfields; i++ ) if( dofld( i )) out( da( irec1, i ));
			for( i = 0; i < Nfields; i++ ) if( dofld( i )) out( da( irec2, i ));
			eor();

			jadvance( select1, &irec1, &inprogress );   /* advance LHS to next matching record */  
			if( inprogress ) jadvance( select2, &irec2, &inprogress );   /* advance RHS to next matching record */
			if( !inprogress ) break;
			}

		else if( diff < 0 ) {
			if( action[0] == 'l' ) { /* leftjoin... output missing RHS fields.. */
				bor();
				for( i = 0; i < Nfields; i++ ) if( dofld( i )) out( da( irec1, i ));
				for( i = 0; i < Nfields; i++ ) if( dofld( i )) out( nacode );
				eor();
				}
			jadvance( select1, &irec1, &inprogress );
			}

		else if( diff > 0 ) {
			if( action[0] == 'r' ) { /* rightjoin... output missing LHS fields.. */
				bor();
				for( i = 0; i < Nfields; i++ ) if( dofld( i )) out( nacode );
				for( i = 0; i < Nfields; i++ ) if( dofld( i )) out( da( irec2, i ));
				eor();
				}
			jadvance( select2, &irec2, &inprogress );
			}
		}
	}


/* stats */

else if( strcmp( action, "stats" )==0 )  {
	double val, min, max, sqrt();
	int n, prec, nbad;

	n = 0; 
	nbad = 0;
	max = NEGHUGE;
	min = PLHUGE;
	
	/* accum[0] holds sum, accum[1] holds sumsq.. */
	for( j = 0; j < Nrecords; j++ ) {
			
		if( selectex[0] != '\0' ) { /* process against selection condition if any.. */
	              	stat = do_select( selectex, j, &select_result );
			if( stat ) select_error += stat;
	               	if( select_result == 0 || stat ) continue; /* reject */
	               	}
		for( k = 0; k < nfld; k++ ) {
			strcpy( tok, da( j, fld[k] ) );
			if( !GL_goodnum( tok, &prec )) { nbad++; continue; }
			n++;
			val = atof( tok );
			accum[0] += val;  /* sum */
			accum[1] += (val * val); /* sum squared */
			if( val > max ) { 
				max = val; 
				if( tagfld >= 0 ) strcpy( breakbuf[0], da( j, tagfld )); 
				}
			if( val < min ) { 
				min = val; 
				if( tagfld >= 0) strcpy( breakbuf[1], da( j, tagfld )); 
				}
			}
		}
	setintvar( "N", n );
	setfloatvar( "TOTAL", accum[0] );
	setintvar( "NMISSING", nbad );
	if( n == 0 ) { setcharvar( "MEAN", "n/a" ); }
	else	{
		setfloatvar( "MEAN", accum[0]/(double)n );
		if( n > 1 ) setfloatvar( "SD", sqrt( ( accum[1] - (accum[0]*accum[0]/(double)n )) / ((double)n-1.0) ) );
		else setcharvar( "SD", "n/a" ); 
		setfloatvar( "MAX", max );
		if( tagfld >= 0 ) setcharvar( "MAX_ID", breakbuf[0] ); /* fixed scg 3/8/05 */
		setfloatvar( "MIN", min );
		if( tagfld >= 0 ) setcharvar( "MIN_ID", breakbuf[1] ); /* fixed scg 3/8/05 */
		}
	return( 0 );
	}


else	{
	int do_numrows, foundrows;
	char numstr[20];

	do_numrows = 0;
	if( strcmp( action, "numberrows" )==0 ) do_numrows = 1;

	/* just write out fields */
	for( i = 0, foundrows = 0; i < Nrecords; i++ ) {
		if( selectex[0] != '\0' ) { /* process against selection condition if any.. */
               		stat = do_select( selectex, i, &select_result );
			if( stat ) select_error += stat;
                	if( select_result == 0 || stat ) continue; /* reject */
                	}
		foundrows++;
		bor();
		if( do_numrows ) { sprintf( numstr, "%d", foundrows ); out( numstr ); }
		for( j = 0; j < Nfields; j++ ) if( dofld( j ) ) out( da( i, j ) );
		eor();
		}
	}

if( !stackmode && !nocloseoutfp ) fclose( outfp );

if( select_error ) Eerr( 472, "warning, an error occurred during 'select'", "" );

if( GL_slmember( action, "per* acc* tot*" )) {
	/* make a comma-delimited list of totals for TOTALS */
	strcpy( buf, "" );
	for( i = 0; i < nfld; i++ ) {
		char out[40];
		sprintf( out, rformat, accum[i] );
		if( dispformatnum ) {  /* rewrite using numbernotation */
			resetbns = 0;
			if( PLS.bignumspacer == '\0' ) {
				PLS.bignumspacer = ',';
				resetbns = 1;
				}
			rewritenums( out ); /* rewrite w/spacing, decimal pt options*/
			if( resetbns ) PLS.bignumspacer = '\0';
			}
		strcat( buf, out );
		strcat( buf, "," );
		}
	buf[ strlen( buf ) -1 ] = '\0'; /* last comma */
	setcharvar( "TOTALS", buf );

	/* if doing totals, exit here */
	if( strncmp( action, "tot", 3 )==0 ) return( 0 );
	}

if( stackmode ) {
	PLD.curds++;  /* now start referencing the result data set */
	if( PLS.debug ) fprintf( PLS.diagfp, "filling data set# %d (this will now be the current data set)\n", PLD.curds );
	setintvar( "NRECORDS", Nrecords );
	setintvar( "NFIELDS", Nfields );
	if( showresults ) {
		fprintf( PLS.diagfp, "// proc processdata results (action = %s)\n", action );
		for( i = 0; i < Nrecords; i++ ) {
			for( j = 0; j < Nfields; j++ ) fprintf( PLS.diagfp, "[%s]", da( i, j ) );
			fprintf( PLS.diagfp, "\n" );
			}
		}
	}

if( !stackmode && !outfile ) {
	if( PLS.debug ) fprintf( PLS.diagfp, "processdata (action = %s) writing to tmp file and invoking proc getdata..\n", 
				action );
	PL_getdata_specialmode( 1, tmpfile, showresults );
	PLP_getdata();
	PL_getdata_specialmode( 0, "", 0 );
	unlink( tmpfile );
	}

return( 0 );
}

/* ================ */
/* DOFLD - return 1 or 0 depending on whether field has been listed
   in keepfields or rejectfields */
static int
dofld( fld )
int fld;
{
int i;
if( nrejfld > 0 ) {
	for( i = 0; i < nrejfld; i++ ) if( fld == rejfld[i] ) break;
	if( i != nrejfld ) return( 0 ); /* no */
	}
if( nkpfld > 0 ) {
	for( i = 0; i < nkpfld; i++ ) if( fld == kpfld[i] ) break;
	if( i != nkpfld ) return( 1 ); /* yes */
	else return( 0 ); /* no */
	}
return( 1 );
}

/* ================= */
/* OUTPUT mgmt routines */

static int 
bor( )
{
if( stackmode ) return( PL_startdatarow() );
return( 0 );
}

static int
out( s )
char *s;
{
if( !stackmode ) fprintf( outfp, "%s	", s );
else PL_catitem( s );
return( 0 );
}

static int
eor()
{
if( !stackmode ) fprintf( outfp, "\n" );
else PL_enddatarow(); 
return( 0 );
}

/* ============================= */
/* routines for JOIN */

static int
jadvance( select, irow, flag )
char *select;
int *irow, *flag;
{
int sresult;
(*irow)++;
if( *flag ) {
	for( ; *irow < Nrecords; (*irow)++ ) {
		do_select( select, *irow, &sresult );
		if( sresult ) break;
		}
	if( *irow >= Nrecords ) *flag = 0;
	}
return( 0 );
}

/* ======================================================= *
 * Copyright 1998-2005 Stephen C. Grubb                    *
 * http://ploticus.sourceforge.net                         *
 * Covered by GPL; see the file ./Copyright for details.   *
 * ======================================================= */

