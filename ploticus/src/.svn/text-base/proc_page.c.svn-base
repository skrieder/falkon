/* ======================================================= *
 * Copyright 1998-2005 Stephen C. Grubb                    *
 * http://ploticus.sourceforge.net                         *
 * Covered by GPL; see the file ./Copyright for details.   *
 * ======================================================= */

/* PROC PAGE - set page-wide attributes, and do a "page" break for pp 2 and up */

#include "pl.h"
#ifndef NOSVG
  extern int PLGS_setparms();
#endif


int
PLP_page( )
{
char buf[256];
char attr[NAMEMAXLEN], val[256];
char *line, *lineval;
int nt, lvp, first;

int stat;
int align;
double adjx, adjy;
int nlines, maxlen;

int landscapemode;
char titledet[80];
int dobackground;
int dopagebox;
char outfilename[ MAXPATH ];
char mapfilename[ MAXPATH ];
int pagesizegiven;
char devval[20];
double scalex, scaley;
double sx, sy;
int clickmap_enabled_here;

TDH_errprog( "pl proc page" );

/* initialize */
landscapemode = PLS.landscape; /* from command line */
strcpy( titledet, "normal" );
strcpy( outfilename, "" );
strcpy( mapfilename, "" );
strcpy( PL_bigbuf, "" );
dobackground = 1;
dopagebox = 1;
if( GL_member( PLS.device, "gesf" )) dopagebox = 0; /* bounding box shouldn't include entire page for gif , eps */
if( PLS.device == 'e' ) dobackground = 0; 
pagesizegiven = 0;
strcpy( devval, "" );
scalex = scaley = 1.0;
clickmap_enabled_here = 0;

/* get attributes.. */
first = 1;
while( 1 ) {
	line = getnextattr( first, attr, val, &lvp, &nt );
	if( line == NULL ) break;
	first = 0;
	lineval = &line[lvp];


	/* if an attribute is given on command line, it overrides anything here.. */
	if( GL_slmember( attr, PLS.cmdlineparms )) continue;
	if( stricmp( attr, "landscape" )==0 && GL_slmember( "portrait", PLS.cmdlineparms )) continue;
	if( stricmp( attr, "outfilename" )==0 && GL_slmember( "o", PLS.cmdlineparms )) continue;

	if( stricmp( attr, "landscape" )==0 ) {
		if( strnicmp( val, YESANS, 1 )==0 ) landscapemode = 1;
		else landscapemode = 0;
		}
	else if( stricmp( attr, "title" )==0 ) getmultiline( "title", lineval, MAXBIGBUF, PL_bigbuf );
	else if( stricmp( attr, "titledetails" )==0 ) strcpy( titledet, lineval );
	else if( stricmp( attr, "outlabel" )==0 ) Esetoutlabel( lineval );
	else if( stricmp( attr, "color" )==0 ) strcpy( Estandard_color, val );
	else if( stricmp( attr, "scale" )==0 ) {
		nt = sscanf( val, "%lf %lf", &scalex, &scaley );
		if( nt == 1 ) scaley = scalex;
		}
	else if( stricmp( attr, "backgroundcolor" )==0 ) {
		strcpy( Estandard_bkcolor, val );
		Ebackcolor( val );
		dobackground = 1; /* added scg 9/27/99 */
		}
	else if( stricmp( attr, "linewidth" )==0 ) Estandard_lwscale = atof( val );
	else if( stricmp( attr, "textsize" )==0 ) {
		Estandard_textsize = atoi( val );
		}
	else if( stricmp( attr, "font" )==0 ) strcpy( Estandard_font, lineval );
	else if( stricmp( attr, "dobackground" )==0 ) {
		if( strnicmp( val, YESANS, 1 )==0 ) dobackground = 1;
		else dobackground = 0;
		}
	else if( stricmp( attr, "dopagebox" )==0 ) {
		if( strnicmp( val, YESANS, 1 )==0 ) dopagebox = 1;
		else dopagebox = 0;
		}
	else if( stricmp( attr, "tightcrop" )==0 ) {
		if( strnicmp( val, YESANS, 1 )==0 ) Etightbb( 1 );
		else Etightbb( 0 );
		}
	else if( strnicmp( attr, "crop", 4 )==0 ) {
		double cropx1, cropy1, cropx2, cropy2;
		nt = sscanf( lineval, "%lf %lf %lf %lf", &cropx1, &cropy1, &cropx2, &cropy2 );
		if( nt != 4 ) Eerr( 2707, "usage: crop x1 y1 x2 y2 OR croprel left bottom right top", "" );
		else {
			if( PLS.usingcm ) { cropx1 /= 2.54; cropy1 /= 2.54; cropx2 /= 2.54; cropy2 /= 2.54; }
			if( strcmp( attr, "croprel" )==0 ) Especifycrop( 2, cropx1, cropy1, cropx2, cropy2 ); /* relative to tight */
			else Especifycrop( 1, cropx1, cropy1, cropx2, cropy2 ); /* absolute */
			}
		}

	else if( stricmp( attr, "pagesize" )==0 ) {
		getcoords( "pagesize", lineval, &(PLS.winw), &(PLS.winh) );
		pagesizegiven = 1;
		}
	else if( stricmp( attr, "outfilename" )==0 ) strcpy( outfilename, val );

	else if( stricmp( attr, "clickmapdefault" )==0 ) clickmap_setdefaulturl( val );

	else if( strcmp( attr, "map" )==0 ) {	/* added 2/3/05 - scg */
		if( strnicmp( val, YESANS, 1 )==0 ) { PLS.clickmap = 1; clickmap_enabled_here = 1; }
		else PLS.clickmap = 0; 
		}
	else if( strcmp( attr, "csmap" )==0 ) {	/* added 2/3/05 - scg */
		if( strnicmp( val, YESANS, 1 )==0 ) { PLS.clickmap = 2; clickmap_enabled_here = 1; }
		else PLS.clickmap = 0;
		}
	else if( strnicmp( attr, "mapfile", 7 )==0 ) strcpy( mapfilename, val );

	else Eerr( 1, "page attribute not recognized", attr );
	}




/* -------------------------- */
/* Page break logic.. */
/* -------------------------- */
if( PLS.npages == 0 ) {

	/* following 3 lines moved here from above - also replicated below.  scg 10/31/00 */
	if( scalex != 1.0 || scaley != 1.0 ) Esetglobalscale( scalex, scaley );
	Egetglobalscale( &sx, &sy );
	if( pagesizegiven ) Esetsize( PLS.winw * sx, PLS.winh * sy, PLS.winx, PLS.winy );
	else if( landscapemode && !PLS.winsizegiven ) Esetsize( 11.0, 8.5, PLS.winx, PLS.winy ); /* landscape */

	/* clickmap (must come before init for eg. svg - scg 2/7/05) */
	if( clickmap_enabled_here ) {
		if( mapfilename[0] == '\0' ) {
        		if( PLS.clickmap == 2 ) strcpy( PLS.mapfile, "stdout" );  /* csmap defaults to stdout..  scg 8/26/04  */
        		else if( PLS.outfile[0] != '\0' ) makeoutfilename( PLS.outfile, PLS.mapfile, 'm', 1);
        		else strcpy( PLS.mapfile, "unnamed.map" );
        		}
		PL_clickmap_init();
#ifndef NOSVG
		/* must update this now too.. scg 2/7/05  */
		if( PLS.device == 's' ) PLGS_setparms( PLS.debug, PLS.tmpname, PLS.clickmap );
#endif
		}
	else if( mapfilename[0] != '\0' ) strcpy( PLS.mapfile, mapfilename );

	/* initialize and give specified output file name .. */
	if( outfilename[0] != '\0' ) Esetoutfilename( outfilename );
	stat = Einit( PLS.device );
	if( stat ) return( stat );

	/* set paper orientation */
	if( landscapemode ) Epaper( 1 );

	}


else if( PLS.npages > 0 ) {

	if( GL_member( PLS.device, "gesf" )) {

		/* finish up current page before moving on to next one.. */
		Eshow();
		stat = Eendoffile();
		if( stat ) return( stat );

		/* now set file name for next page.. */
		if( outfilename[0] != '\0' ) Esetoutfilename( outfilename );
		else	{
			makeoutfilename( PLS.outfile, buf, PLS.device, (PLS.npages)+1 );
			if( PLS.debug ) fprintf( PLS.diagfp, "Setting output file name to %s\n", PLS.outfile );
			Esetoutfilename( buf );
			}

		if( PLS.clickmap ) {
			/* initialize a new click map file.. */
			if( mapfilename[0] != '\0' ) strcpy( PLS.mapfile, mapfilename );
			else makeoutfilename( PLS.outfile, PLS.mapfile, 'm', (PLS.npages)+1 );
			PL_clickmap_init();

			}


		/* perhaps set global scaling and/or page size for next page.. */
		/* following 3 lines copied here from above - scg 10/31/00 */
		if( scalex != 1.0 || scaley != 1.0 ) Esetglobalscale( scalex, scaley );
		Egetglobalscale( &sx, &sy ); 
		if( pagesizegiven ) Esetsize( PLS.winw * sx, PLS.winh * sy, PLS.winx, PLS.winy );
		else if( landscapemode && !PLS.winsizegiven ) Esetsize( 11.0, 8.5, PLS.winx, PLS.winy ); /* landscape */

		/* initialize next page.. */
		stat = Einit( PLS.device );
		if( stat ) return( stat );
		}

	else if ( PLS.device == 'x' ) PL_do_x_button( "More.." );

	else if ( GL_member( PLS.device, "pc" ) ) {
		Eprint();
		if( landscapemode ) Epaper( 1 ); /* added scg 2/29/00 */
		Elinetype( 0, 0.6, 1.0 );   /* added scg 9/20/99 */
		}
	}
(PLS.npages)++;


/* -------------------------- */
/* now do other work.. */
/* -------------------------- */

/* do background.. */
/* if( dopagebox ) Ecblock( 0.0, 0.0, EWinx, EWiny, Ecurbkcolor, 0 ); */ /* does update bb */
if( dopagebox ) Ecblock( 0.0, 0.0, PLS.winw, PLS.winh, Ecurbkcolor, 0 ); /* does update bb */
else if( dobackground ) {
	/* EPS color=transparent - best to do nothing.. */
        if( PLS.device == 'e' && strcmp( Ecurbkcolor, "transparent" )==0 ) ;

        else Eclr(); /* doesn't update bb */
	}

if( PL_bigbuf[0] != '\0' ) {
	textdet( "titledetails", titledet, &align, &adjx, &adjy, 3, "B", 1.0 );
	if( align == '?' ) align = 'C';
	measuretext( PL_bigbuf, &nlines, &maxlen );
	if( align == 'L' ) Emov( 1.0 + adjx, (PLS.winh-0.8) + adjy );
	else if ( align == 'C' ) Emov( (PLS.winw / 2.0 ) + adjx, (PLS.winh-0.8) + adjy );
	else if( align == 'R' ) Emov( (PLS.winw-1.0) + adjx, (PLS.winh-0.8) + adjy );
	Edotext( PL_bigbuf, align );
	}

return( 0 );
}

/* ======================================================= *
 * Copyright 1998-2005 Stephen C. Grubb                    *
 * http://ploticus.sourceforge.net                         *
 * Covered by GPL; see the file ./Copyright for details.   *
 * ======================================================= */
