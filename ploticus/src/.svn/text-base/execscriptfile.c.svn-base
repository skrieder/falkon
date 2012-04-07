/* ======================================================= *
 * Copyright 1998-2005 Stephen C. Grubb                    *
 * http://ploticus.sourceforge.net                         *
 * Covered by GPL; see the file ./Copyright for details.   *
 * ======================================================= */

#include "pl.h"
#include "tdhkit.h"

/* =================================================== */
/* EXEC_SCRIPTFILE - execute a script file using midriff script interpreter.
 * Returns 0 if ok, or a non-zero error code 
 */

int
PL_exec_scriptfile( scriptfile )
char *scriptfile;
{
int ix, stat;
struct sinterpstate ss; 
char buf[ SCRIPTLINELEN ];
char buf2[ 256 ];
char tok[256];
long loc;
int seekstate;

/* open spec file.. */
stat = TDH_sinterp_open( scriptfile, &ss );
if( stat != 0 ) return( Eerr( 22, "Cannot open scriptfile; perhaps an error in command line args", scriptfile ) );
	
if( PLS.debug ) { fprintf( PLS.diagfp, "Script file successfully opened\n" ); fflush( PLS.diagfp ); }

/* read through script file using sinterp script interpreter.. */
while( 1 ) {
	stat = TDH_sinterp( buf, &ss, "", NULL );
	if( stat == 1226 ) {
        	err( stat, "required variable(s) missing", buf );
        	break;
        	}

        if( stat > 255 ) return( Eerr( 23, "Fatal script processing error.", "" ) );
	else if( PLS.skipout ) { /* error that is fatal for this script */
		PLS.skipout = 0;
		return( 1 );
		}
        else if( stat != SINTERP_MORE ) break;

	ix = 0; 
	strcpy( buf2, GL_getok( buf, &ix ) ); /* check first token on line.. */

	/* check for #proc trailer.. if encountered we're done.. */
	if( stricmp( buf2, "#proc" )==0 && stricmp( GL_getok( buf, &ix ), "trailer" )==0 ) break; 

	/* handle midriff "secondary" ops such as #shell, #sql, #write... */
	if( ss.doingshellresult == 0 && ss.doingsqlresult == 0 && 
	    buf2[0] == '#' && buf2[1] != '#' && !GL_slmember( buf2, "#proc* #endproc #clone* #ifspec* #saveas* #intrailer" ) ) {
            	while( 1 ) {
			stat = TDH_secondaryops( buf, &ss, "", NULL ); /* may call sinterp() to get lines*/
			if( stat > 255 ) return( Eerr( 24, "Fatal script processing error.", "" ) );
        		else if( stat != SINTERP_MORE ) break;
			if( ss.writefp != NULL ) fprintf( ss.writefp, "%s", buf );
			}
		if( stat == SINTERP_END_BUT_PRINT ) {
			if( ss.writefp != NULL ) fprintf( ss.writefp, "%s", buf );
			}
		continue;
		}
	if( ss.writefp != NULL ) {
		fprintf( ss.writefp, "%s", buf ); 
		continue;
		}

	if( strnicmp( buf2, "#intrailer", 10 )==0 ) {
		/* remember current location in control file, then scan forward until we reach 
		   #proc trailer.  Then get the lines there.  Then seek back to where we left off. 
		   This cannot be used from within an #include.  */
		loc = ftell( ss.sfp[0] );
		seekstate = 0;
		while( fgets( buf, SCRIPTLINELEN-1, ss.sfp[0] ) != NULL ) {
			sscanf( buf, "%s %s", buf2, tok );
			if( stricmp( buf2, "#proc" ) ==0 && stricmp( tok, "trailer" )==0 ) break;
			}
		while( fgets( buf, SCRIPTLINELEN-1, ss.sfp[0] ) != NULL ) {
			sscanf( buf, "%s", buf2 );
			if( strcmp( buf2, "//" )==0 ) continue;
			PL_execline( buf );
			/* no use checking return status.. */
			}

		fseek( ss.sfp[0], loc, 0 /*SEEK_SET BSD*/ );  /* now go back.. */
		continue;
		}

	PL_execline( buf );
	/* no use checking return status.. */

	if( PLS.skipout ) break;
	}

PL_execline( "#endproc" ); /* this causes last-encountered proc to be executed.. */
/* no use checking return status.. */

if( ss.sfp[0] != NULL ) fclose( ss.sfp[0] );
return( 0 );
}


/* ======================================================= *
 * Copyright 1998-2005 Stephen C. Grubb                    *
 * http://ploticus.sourceforge.net                         *
 * Covered by GPL; see the file ./Copyright for details.   *
 * ======================================================= */
