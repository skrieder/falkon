/* ======================================================= *
 * Copyright 1998-2005 Stephen C. Grubb                    *
 * http://ploticus.sourceforge.net                         *
 * Covered by GPL; see the file ./Copyright for details.   *
 * ======================================================= */

/* PROC SCATTERPLOT - render a scatterplot */

#include "pl.h"

/* constants */
#define NOFS
static double xofst[38] = { 0, 0, 4, 0, -4, 4, -4, -4, 4,
        0, -8, 0, 8, 4, -8, 4, 8, -4, -8, -4, 8,
        0, 0, 12, -12, 4, 4, 12, -12, -4, -4, 12, -12,
        8, -8, -8, 8 };

static double yofst[38] = { 0, 4, 0, -4, 0, 4, -4, 4, -4,
        -8, 0, 8, 0, -8, 4, 8, 4, -8, -4, 8, -4,
        12, -12, 0, 0, 12, -12, 4, 4, 12, -12, -4, -4,
        8, -8, 8, -8 };


#ifdef NONANSI
static int ptcompare();
#else
static int ptcompare(const void *a, const void *b);
/* static int ptcompare(double *f, double *g); */
#endif

int
PLP_scatterplot()
{
int i;
char attr[NAMEMAXLEN], val[256];
char *line, *lineval;
int nt, lvp;
int first;

int stat;
int align;
double adjx, adjy;

int xfield, yfield;
char symbol[256];
double linelen;
char linedetails[256];
double xloc, yloc;
int cluster;
double radius;
char symcode[80];
double x, y;
char text[256];
double cx, cy;
double hlinelen;
char textdetails[256];
int lblfield;
char selex[256];
int result;
double sizescale;
int sizefield;
double ox[38], oy[38];
double clusterfact;
double oldx, oldy;
int dupcount;
int subdupcount;
int clustevery;
int verttext;
int nrow;
char legendlabel[256]; /* raised (can contain urls for clickmap) scg 4/22/04 */
char xrange[80], yrange[80];
double xlo, xhi, ylo, yhi;
char rhi[40], rlo[40];
double clusterdiff;
int realrow;
int clustermeth;
int symfield;
int symfield_userange;
int dupsleg;
char mapurl[MAXPATH], expurl[MAXPATH];
char maplabel[MAXTT], explabel[MAXTT];
int irow;
double ptx, pty;
double hw, txhi;
char linedir, reqlinedir;
double rectw, recth;
int dorect, rectoutline;
int clickmap_on;
int flop2;
int maxdups;
char labelword[80], labeltxt[80];
double vennden;

TDH_errprog( "pl proc scatterplot" );


/* initialize */
xfield = -1; yfield = -1;
strcpy( symbol, "" );
linelen = -1.0;
strcpy( linedetails, "" );
xloc = 0.0;
yloc = 0.0;
/* cluster = 1; */
cluster = 0;   /* changed and added to "breakers" in docs, scg 5/29/06 */
strcpy( text, "" );
strcpy( textdetails, "" );
lblfield = -1;
strcpy( selex, "" );
sizefield = -1;
sizescale = 0.5/72.0; /* correspond roughly with pt size */
clusterfact = 0.01;
verttext = 0;
strcpy( legendlabel, "" );
strcpy( xrange, "" );
strcpy( yrange, "" );
clustevery = 0;
clusterdiff = 0.001;
clustermeth = 0;
symfield = -1;
dupsleg = 0;
symfield_userange = 0;
strcpy( mapurl, "" ); strcpy( expurl, "" );
strcpy( maplabel, "" ); strcpy( explabel, "" );
dorect = 0;
rectoutline = 0;
linedir = reqlinedir = '\0'; /* scg 3/4/03 */
clickmap_on = 0;
strcpy( labelword, "@VAL" );
vennden = 0.0;

/* get attributes.. */
first = 1;
while( 1 ) {
	line = getnextattr( first, attr, val, &lvp, &nt );
	if( line == NULL ) break;
	first = 0;
	lineval = &line[lvp];

	if( stricmp( attr, "xfield" )==0 ) xfield = fref( val ) -1;
		
	else if( stricmp( attr, "yfield" )==0 ) yfield = fref( val ) -1;
	else if( stricmp( attr, "labelfield" )==0 ) lblfield = fref( val ) -1;
		
	else if( stricmp( attr, "symbol" )==0 ) strcpy( symbol, lineval );
	else if( stricmp( attr, "text" )==0 ) strcpy( text, val );
	else if( stricmp( attr, "textdetails" )==0 ) strcpy( textdetails, lineval );

	else if( stricmp( attr, "sizefield" )==0 ) sizefield = fref( val ) -1;
	else if( stricmp( attr, "sizescale" )==0 ) sizescale = atof( val ) * 0.5/72.0;
	else if( stricmp( attr, "xrange" )==0 ) strcpy( xrange, lineval );
	else if( stricmp( attr, "yrange" )==0 ) strcpy( yrange, lineval );
	else if( stricmp( attr, "clickmapurl" )==0 ) {
		if( PLS.clickmap ) { strcpy( mapurl, val ); clickmap_on = 1; }
		}
	else if( stricmp( attr, "clickmaplabel" )==0 ) {
		if( PLS.clickmap ) { strcpy( maplabel, lineval ); clickmap_on = 1; }
		}
        else if( stricmp( attr, "clickmaplabeltext" )==0 ) {
                if( PLS.clickmap ) { getmultiline( "clickmaplabeltext", lineval, MAXTT, maplabel ); clickmap_on = 1; }
                }

	else if( stricmp( attr, "linelen" )==0 ) {
		if( val[0] == '\0' ) linelen = -1.0;
		else	{
			linelen = atof( val );
			if( PLS.usingcm ) linelen /= 2.54;
			}
		}
	else if( stricmp( attr, "linedir" )==0 ) reqlinedir = tolower( val[0] );
	else if( stricmp( attr, "linedetails" )==0 ) strcpy( linedetails, lineval );
		
	else if( stricmp( attr, "xlocation" )==0 ) {
		Eposex( lineval, X, &xloc ); /* val -> lineval scg 5/3/99 */
		if( Econv_error() ) Eerr( 2394, "invalid xlocation", val );
		}
		
	else if( stricmp( attr, "ylocation" )==0 ) {
		Eposex( lineval, Y, &yloc ); /* val -> lineval 5/3/99 */
		if( Econv_error() ) Eerr( 2395, "invalid ylocation", val );
		}
		
	else if( stricmp( attr, "select" )==0 ) strcpy( selex, lineval );
	else if( stricmp( attr, "legendlabel" )==0 ) strcpy( legendlabel, lineval );

	else if( stricmp( attr, "cluster" )==0 ) {
		if( strnicmp( val, YESANS, 1 )==0 ) cluster = 1;
		else cluster = 0;
		}
	else if( stricmp( attr, "clusterdiff" )==0 ) {
		cluster = 1;
		clusterdiff = atof( val );
		}
	else if( stricmp( attr, "clustermethod" )==0 ) {
		cluster = 1;
		clustermeth = tolower( val[0] );  /* h, v, 2, u, r, ..  */
		}
	else if( stricmp( attr, "clusterfact" )==0 ) {
		cluster = 1;
		clusterfact = atof( val ) * .01;
		}
	else if( stricmp( attr, "clustevery" )==0 ) {
		cluster = 1;
		clustevery = atoi( val );
		if( clustevery < 1 ) clustevery = 1;
		}
	else if( stricmp( attr, "dupsleg" )==0 ) {
		if( strnicmp( val, YESANS, 1 )==0 ) { 
			dupsleg = 1; 
			cluster = 1; 
			clustermeth = 'l';
			strcpy( symbol, "sym6a" ); /* just to get us into symbol mode */
			}
		else dupsleg = 0;
		}
	else if( stricmp( attr, "symfield" )==0 ) {
		strcpy( symbol, "sym6a" ); /* just to get us into symbol mode */
		symfield = fref( val ) -1;
		symfield_userange = 0;
		}
	else if( stricmp( attr, "symrangefield" )==0 ) {
		strcpy( symbol, "sym6a" ); /* just to get us into symbol mode */
		symfield = fref( val ) -1;
		symfield_userange = 1;
		}
	else if( stricmp( attr, "verticaltext" )==0 ) {
		if( strnicmp( val, YESANS, 1 )==0 ) verttext = 1;
		else verttext = 0;
		}
	else if( stricmp( attr, "rectangle" )==0 ) {
		nt = sscanf( lineval, "%lf %lf %s", &rectw, &recth, val );
		if( nt == 3 ) rectoutline = 1;
		rectw *= 0.5;
		recth *= 0.5;
		rectw = Eax( rectw ) - Eax( 0.0 );
		recth = Eay( recth ) - Eay( 0.0 );
		dorect = 1;
		}

	else if( stricmp( attr, "labelword" ) == 0 ) strcpy( labelword, lineval );
	else if( stricmp( attr, "vennden" ) == 0 ) vennden = atof( val );

	else Eerr( 1, "attribute not recognized", attr );
	}


/* overrides and degenerate cases */
/* -------------------------- */
if( Nrecords < 1 ) return( Eerr( 17, "No data has been read yet w/ proc getdata", "" ) );
if( !scalebeenset() )
         return( Eerr( 51, "No scaled plotting area has been defined yet w/ proc areadef", "" ) );

if( xfield < 0 && yfield < 0 ) return( Eerr( 2205, "Niether xfield nor yfield defined", "" ));

if( lblfield >= 0 ) cluster = 0;  /* added scg 12/21/00 */

if( stricmp( legendlabel, "#usexname" )==0 ) getfname( xfield+1, legendlabel );
if( stricmp( legendlabel, "#useyname" )==0 ) getfname( yfield+1, legendlabel );

if( dorect ) strcpy( symbol, "" );


/* now do the plotting work.. */
/* -------------------------- */

if( cluster ) {
	/* make offsets */
	for( i = 0; i < 38; i++ ) {
		ox[i] = xofst[i] * clusterfact;
		oy[i] = yofst[i] * clusterfact;
		}

	/* determine cluster method */
	if( clustermeth == 0 ) {
		if( yfield < 0 ) clustermeth = 'v'; 	 /* 1-d horizontal - cluster vertically (was 'h'-scg 4/21/05) */
		else if( xfield < 0 ) clustermeth = 'h'; /* 1-d vertical - cluster horizontally (was 'v'-scg 4/21/05) */
		else clustermeth = '2'; 		 /* 2-d cluster */
		}
	}

/* ranges */
xlo = EDXlo;
xhi = EDXhi;
ylo = EDYlo;
yhi = EDYhi;
if( xrange[0] != '\0' ) {
	nt = sscanf( xrange, "%s %s", rlo, rhi );
	xlo = Econv( X, rlo );
	if( Econv_error() ) { Eerr( 3958, "xrange bad format", rlo ); xlo = EDXlo; }
	if( nt == 2 ) xhi = Econv( X, rhi );
	if( Econv_error() ) { Eerr( 3958, "xrange bad format", rhi ); xhi = EDXhi; }
	}
if( yrange[0] != '\0' ) {
	nt = sscanf( yrange, "%s %s", rlo, rhi );
	ylo = Econv( Y, rlo );
	if( Econv_error() ) { Eerr( 3958, "yrange bad format", rlo ); ylo = EDYlo; }
	if( nt == 2 ) yhi = Econv( Y, rhi );
	if( Econv_error() ) { Eerr( 3958, "yrange bad format", rhi ); yhi = EDYhi; }
	}






nrow = 0;
for( i = 0; i < Nrecords; i++ ) {

	if( selex[0] != '\0' ) { /* process against selection condition if any.. */
                stat = do_select( selex, i, &result );
                if( stat != 0 ) { Eerr( stat, "Select error", selex ); continue; }
                if( result == 0 ) continue; /* reject */
                }

	/* get x value.. */
	if( xfield >= 0 ) {
		x = fda( i, xfield, 'x' );
        	if( Econv_error() ) { conv_msg( i, xfield, "xfield" ); continue; }
		if( x < xlo || x > xhi ) continue;
		}

	/* get y value.. */
	if( yfield >= 0 ) {
		y = fda( i, yfield, 'y' );
        	if( Econv_error() ) { conv_msg( i, yfield, "yfield" ); continue; }
		if( y < ylo || y > yhi ) continue;
		}

	/* go to absolute units.. */
	if( xfield < 0 ) x = xloc;
	else x = Eax(x);
	if( yfield < 0 ) y = yloc;
	else y = Eay(y);


	/* put (x,y) into PLV array so points can be sorted.. */
	if( nrow >= PLVthirdsize ) {
		fprintf( PLS.errfp, "point capacity exceeded, skipping data point (raise using -maxvector)\n" );
		continue;
		}
	dat3d( nrow, 0 ) = x;
	dat3d( nrow, 1 ) = y;
	dat3d( nrow, 2 ) = (double)i;  /* added scg 12/21/00 - went from dat2d to dat3d */
				       /* need to keep track of actual location in data array for labels, sizefield, etc.. */
	nrow++;
	}


/* if clustering and not using a label field, sort PLV array */
if( cluster && lblfield < 0 && sizefield < 0 ) {
	if( PLS.debug ) fprintf( PLS.diagfp, "sorting points for scatterplot\n" );
	qsort( PLV, nrow, sizeof(double)*3, ptcompare );
	}



if( verttext ) Etextdir( 90 );

/* these are used in clustering.. */
oldx = NEGHUGE;
oldy = NEGHUGE;
dupcount = 0;
subdupcount = 0;
maxdups = 0;

strcpy( symcode, "sym6a" );
radius = 0.04;


/* in the following, text must come before symbol.. */
if( text[0] != '\0' || lblfield >= 0 ) {
	textdet( "textdetails", textdetails, &align, &adjx, &adjy, -3, "R", 1.0 );
	}
if( symbol[0] != '\0' ) {
	symdet( "symbol", symbol, symcode, &radius );
	}
if( linelen > 0.0 || rectoutline ) {
	linedet( "linedetails", linedetails, 0.5 );
	}
cx = Ecurtextwidth * 0.3;
cy = Ecurtextheight * 0.3;
hlinelen = linelen * 0.5;
txhi = cy + cy;
if( text[0] != '\0' ) hw = strlen( text ) * Ecurtextwidth * 0.5;

/* now display points.. */
for( irow = 0; irow < nrow; irow++ ) {
	x = dat3d( irow, 0 );
	y = dat3d( irow, 1 );
	realrow = (int)dat3d( irow, 2 ); /* added scg 12/21/00 */

	/* in this loop, you MUST USE REALROW, NOT IROW for accessing ancillary data fields!! */

	if( cluster ) {
		if( GL_close_to( x, oldx, clusterdiff ) && GL_close_to( y, oldy, clusterdiff ) ) {
			subdupcount++;
			if( subdupcount >= clustevery ) {
				dupcount++;
				subdupcount = 0;
				}

			if( dupcount % 2 == 0 ) flop2 = 1;
			else flop2 = -1;

			if( clustermeth == '2' && dupcount > 37 ) {
				maxdups = 37;
				dupcount = 0; /* mod */
				}

			if( clustermeth == 'h' ) x += ((dupcount+1)/2) * clusterfact * 2.0 * flop2;
			else if( clustermeth == 'v' ) y += ((dupcount+1)/2) * clusterfact * 2.0 * flop2;
			else if( clustermeth == 'u' ) y += dupcount * clusterfact * 2.0; /* 1D upward */
			else if( clustermeth == 'r' ) x += dupcount * clusterfact * 2.0; /* 1D rightward */
			else if( clustermeth == 'l' ) ; /* legend lookup, no offset */
			else if( clustermeth == '2' ) {  x += ox[dupcount%38]; y += oy[dupcount%38]; } /* 2-D */

			if( clustermeth == 'l' ) { /* if more duplicate points coming, skip.. */
				if( irow < nrow-1 ) {
					double nextx, nexty;
					nextx = dat3d( irow+1, 0 );
					nexty = dat3d( irow+1, 1 );
					if( GL_close_to( x, nextx, clusterdiff ) && 
					    GL_close_to( y, nexty, clusterdiff ) ) continue;
					}
				}
			}
		else {
			if( dupcount > maxdups ) maxdups = dupcount;
			oldx = x;
			oldy = y;
			dupcount = 0;
			subdupcount = 0;
			}
		}

	/* allow @field substitutions into url */
        if( clickmap_on ) {
		do_subst( expurl, mapurl, realrow, URL_ENCODED );
		do_subst( explabel, maplabel, realrow, NORMAL );
		}



	/* render text, mark or line.. */
	/* text can be combined with mark if text and symbol both specified */

	/* symbol or rectangle.. */
	if( symbol[0] != '\0' || dorect || ( text[0] == '\0' && linelen <= 0.0 && lblfield < 0 ) ) {
		if( symfield >= 0 ) {  /* look it up in legend list.. */
			if( symfield_userange ) {
                		stat = PL_get_legent_rg( atof( da( realrow, symfield ) ), symbol, NULL, NULL );
				}
                	else stat = PL_get_legent( da( realrow, symfield ), symbol, NULL, NULL );
			if( stat ) Eerr( 7429, "warning: symfield: no matching legend entry tag found", da( realrow, symfield ) );
			if( !dorect ) symdet( "symfield", symbol, symcode, &radius );
			}
		if( dupsleg ) {  /* look it up in legend list.. */
                	stat = PL_get_legent_rg( (double)dupcount+1, symbol, NULL, NULL );
			if( stat ) Eerr( 7692, "warning: dupsleg: no appropriate legend entry tag\n", "" );
			if( !dorect ) symdet( "symfield", symbol, symcode, &radius );
			/* note: currently all marks will be rendered; the last one will be on "top"  */
			}
		if( sizefield >= 0 ) 
			radius = sqrt((atof( da( realrow, sizefield ) ) * sizescale)/3.1415927);
			         /* sizefield scales up the AREA of symbol, not the diameter */
		if( dorect ) {
			char color[ COLORLEN ];
			strcpy( color, "" ); /* added scg 9/1/05 - heatmap bug */
			if( symfield >=0 || dupsleg ) sscanf( symbol, "%s", color ); /* strip off any trailing space */
			Ecblock( x-rectw, y-recth, x+rectw, y+recth, color, rectoutline );
			symbol[0] = '\0';
			}
		else if( vennden > 0.0 ) {
			double urad;
			for( urad = 0.01; urad < radius; urad += vennden ) Emark( x, y, symcode, urad );
			} 
		else Emark( x, y, symcode, radius );

		if( clickmap_on ) {
			if( dorect ) clickmap_entry( 'r', expurl, 0, x-rectw, y-recth, x+rectw, y+recth, 0, 0, explabel );
			else clickmap_entry( 'r', expurl, 0, x-radius, y-radius, x+radius, y+radius, 0, 0, explabel );
			}
			
		}

	/* text */
	if( text[0] != '\0' ) {
		if( symbol[0] != '\0' )  /* set text color etc... */
			textdet( "textdetails", textdetails, &align, &adjx, &adjy, -3, "R", 1.0 );
		if( sizefield >= 0 ) Etextsize( (int) (atof( da( realrow, sizefield ) ) * sizescale) );
		if( verttext ) { ptx = (x+cy)+adjx; pty = y; } /* cy puts midheight of character on point */
		else { ptx = x+adjx; pty = (y-cy)+adjy; }

		convertnl( text );
		Emov( ptx, pty );
		if( align == '?' ) Edotext( text, 'C' );
		else Edotext( text, align );
		if( symbol[0] != '\0'  )  /* restore symbol color etc... */
			symdet( "symbol", symbol, symcode, &radius );

		if( clickmap_on ) clickmap_entry( 'r', expurl, 0, ptx-hw, pty, x+hw, y+txhi, 0, 0, explabel );
		}

	/* label from data */
	else if( lblfield  >= 0 ) {
		if( sizefield >= 0 ) Etextsize( (int) (atof( da( realrow, sizefield ) ) * sizescale) );
		if( verttext) { ptx = (x+cy)+adjx; pty = y+adjy; } /* cy puts midheight of character on point */
		else { ptx = x+adjx; pty = (y-cy)+adjy; } 

		strcpy( labeltxt, labelword );
		GL_varsub( labeltxt, "@VAL", da( realrow, lblfield ) );

		Emov( ptx, pty );
		if( align == '?' ) Edotext( labeltxt, 'C' );
		else Edotext( labeltxt, align );

		if( clickmap_on ) {
			hw = strlen( labeltxt ) * Ecurtextwidth * 0.5;
			if( GL_member( align, "C?" ))clickmap_entry( 'r', expurl, 0, ptx-hw, pty, x+hw, y+txhi, 0, 0, explabel );
			else if( align == 'L' ) clickmap_entry( 'r', expurl, 0, ptx, pty, x+(hw*2.0), y+txhi, 0, 0, explabel );
			else if( align == 'R' ) clickmap_entry( 'r', expurl, 0, ptx-(hw*2.0), pty, x, y+txhi, 0, 0, explabel );
			}
		}

	/* line */         /* (no clickmap support) */   /* no legend support either (?) */
	else if( linelen > 0.0 ) {
		if( sizefield >= 0 ) hlinelen = linelen * 0.5 * atof( da( realrow, sizefield ) ); 
					/* sizefield acts as a scale factor to linelen */

		if( reqlinedir != '\0' ) linedir = reqlinedir;
		else if( xfield >= 0 && yfield >= 0 ) linedir = 'h'; 	/* arbitrary .. scg 5/16/03 */
		else if( xfield >= 0 ) linedir = 'v';
		else linedir = 'h';			/* scg 3/5/03 */

		if( linedir == 'v' ) { Emov( x, y-hlinelen ); Elin( x, y+hlinelen ); }
		else if( linedir == 'u' ) { Emov( x, y ); Elin( x, y+(hlinelen*2.0) ); }
		else if( linedir == 'r' ) { Emov( x, y ); Elin( x+(hlinelen*2.0), y ); }
		else { Emov( x-hlinelen, y ); Elin( x+hlinelen, y ); }
		}

	}
if( verttext ) Etextdir( 0 );

if( legendlabel[0] != '\0' ) {
	char s[40];
	sprintf( s, "%d", nrow );
	GL_varsub( legendlabel, "@NVALUES", s );
	if( linelen <= 0.0 && lblfield < 0 && text[0] == '\0' )
		PL_add_legent( LEGEND_SYMBOL, legendlabel, "", symbol, "", "" );
	else if( symbol[0] != '\0' && text[0] != '\0' )
		PL_add_legent( LEGEND_SYMBOL+LEGEND_TEXT, legendlabel, "", text, textdetails, symbol );
	else if( linelen > 0.0 ) {
		char dirstr[8];
		sprintf( dirstr, "%c", linedir );
		PL_add_legent( LEGEND_LINEMARK, legendlabel, "", linedetails, dirstr, "" );
		}
	}

setintvar( "NVALUES", nrow );
maxdups++;
setintvar( "MAXDUPS", maxdups );

return( 0 );
}
/* ======================= */

static int
ptcompare( a, b )
const void *a, *b;

/* static int ptcompare( f, g )
 * double *f, *g;
 */  /* changed to eliminate gcc warnings  scg 5/18/06 */

{
double *f, *g;
double *f2, *g2;

f = (double *)a;
g = (double *)b;

if( *f > *g ) return( 1 );
else if( *f < *g ) return( -1 );
else	{
	/* advance to Y component */
	f2 = f+1;
	g2 = g+1;
	if( *f2 > *g2 ) return( 1 );
	else if( *f2 < *g2 ) return( -1 );
	else return( 0 ); /* same */
	}
}

/* ======================================================= *
 * Copyright 1998-2005 Stephen C. Grubb                    *
 * http://ploticus.sourceforge.net                         *
 * Covered by GPL; see the file ./Copyright for details.   *
 * ======================================================= */
