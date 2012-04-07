/* ======================================================= *
 * Copyright 1998-2005 Stephen C. Grubb                    *
 * http://ploticus.sourceforge.net                         *
 * Covered by GPL; see the file ./Copyright for details.   *
 * ======================================================= */

/* PROC PIE  - render a pie graph */

/* 7/12/01 - scg - now renders each entire slice as a single polygon */

#include "pl.h"
#define TORAD 0.0174532
#define MAXSLICE 80
#define LEGEND 1
#define LABEL 2
#define LINELABEL 3

int
PLP_pie()
{
char attr[NAMEMAXLEN], val[256];
char *line, *lineval;
int nt, lvp;
int first;

char buf[256];
int j;
int align;
double adjx, adjy;
int df;
double cx, cy, radius;
double theta;
char color[MAXSLICE][40];
char color1[COLORLEN];
int lblfld;
double total;
double ux, uy, stop;
int ncolors;
double starttheta;
double sin(), cos();
double fval;
double x, y;
double oldx, oldy;
int nexpl;
double expl[MAXSLICE];
char outlinedetails[256];
char lablinedetails[256];
char labelmode[50];
int lbltextgiven;
int ibb;
char label[256];
char textdetails[256];
double stheta;
double lblfarout;
char pctstr[30];
char pctfmt[30];
int colorfield;
int nlines, maxlen;
double boxwid, boxhi;
char mapurl[256], expurl[256];
char maplabel[MAXTT], explabel[MAXTT];
double labx, laby;
int irow;
int ilabmode;
int clickmap_on;
double dval;
char labelback[COLORLEN];
int labelbackoutline;
int exactcolorfield;
char lblfmtstring[256];
int wraplen;

TDH_errprog( "pl proc pie" );




/* initialize */
theta = 0.0;
cx = cy = -1.0;
radius = -1.0;
lblfld = -1;
strcpy( labelmode, "legend" );
strcpy( PL_bigbuf, "" );
ncolors = 0;
starttheta = 90.0 * TORAD;
total = 0.0;
nexpl = 0;
strcpy( outlinedetails, "" );
strcpy( lablinedetails, "" );
lbltextgiven = 0;
strcpy( textdetails, "" );
lblfarout = 0.0;
strcpy( pctfmt, "%.1f" );
colorfield = -1;
exactcolorfield = -1;
strcpy( mapurl, "" );
strcpy( maplabel, "" );
clickmap_on = 0;
strcpy( labelback, "" );
labelbackoutline = 0;
strcpy( lblfmtstring, "" );
wraplen = 0;

/* get attributes.. */
first = 1;
while( 1 ) {
	line = getnextattr( first, attr, val, &lvp, &nt );
	if( line == NULL ) break;
	first = 0;
	lineval = &line[lvp];

	if( stricmp( attr, "datafield" )==0 ) df = fref( val ) -1 ;
	else if( stricmp( attr, "center" )==0 ) getcoords( "center", lineval, &cx, &cy );
	else if( stricmp( attr, "radius" )==0 ) Elenex( val, X, &radius ); /* use X units.. */
	else if( stricmp( attr, "firstslice" )==0 ) starttheta = ((360-atof( val )) * TORAD ) + 90.0 * TORAD;
	else if( stricmp( attr, "total" )==0 ) total = atof( val );
	else if( stricmp( attr, "clickmapurl" )==0 ) {
		if( PLS.clickmap ) { strcpy( mapurl, val ); clickmap_on = 1; }
		}
	else if( stricmp( attr, "clickmaplabel" )==0 ) {
		if( PLS.clickmap ) { strcpy( maplabel, lineval ); clickmap_on = 1; }
		}
        else if( stricmp( attr, "clickmaplabeltext" )==0 ) {
                if( PLS.clickmap ) { getmultiline( "clickmaplabeltext", lineval, MAXTT, maplabel ); clickmap_on = 1; }
                }
	else if( stricmp( attr, "colors" )==0 ) {
		int i, ix;
		for( i = 0, ix = 0; i < MAXSLICE; i++ ) {
			strcpy( color[i], GL_getok( lineval, &ix ) );
			if( color[i][0] == '\0' ) break;
			}
		ncolors = i;
		}
	else if( stricmp( attr, "labels" )==0 ) {
		getmultiline( "labels", lineval, MAXBIGBUF, PL_bigbuf );
		lbltextgiven = 1;
		}
	else if( stricmp( attr, "labelfield" )==0 ) lblfld = fref( val ) - 1;
	else if( stricmp( attr, "labelfmtstring" )==0 ) strcpy( lblfmtstring, lineval ); /* added scg 8/20/04 */
	else if( stricmp( attr, "colorfield" )==0 ) colorfield = fref( val ) - 1;
	else if( stricmp( attr, "exactcolorfield" )==0 ) exactcolorfield = fref( val ) - 1;
	else if( stricmp( attr, "outlinedetails" )==0 ) strcpy( outlinedetails, lineval );
	else if( stricmp( attr, "lablinedetails" )==0 ) strcpy( lablinedetails, lineval );
	else if( stricmp( attr, "textdetails" )==0 ) strcpy( textdetails, lineval );
	else if( stricmp( attr, "labelmode" )==0 ) strcpy( labelmode, val );
	else if( stricmp( attr, "labelfarout" )==0 ) lblfarout = atof( val );
	else if( stricmp( attr, "labelback" )==0 ) strcpy( labelback, val );
	else if( stricmp( attr, "labelbackoutline" )==0 ) {
		if( strnicmp( val, YESANS, 1 )==0 ) labelbackoutline = 1;
		else labelbackoutline = 0;
		}
	else if( stricmp( attr, "pctformat" )==0 ) strcpy( pctfmt, val );
	else if( stricmp( attr, "explode" )==0 ) {
		int i, ix;
		for( i = 0, ix = 0; i < MAXSLICE; i++ ) {
			strcpy( buf, GL_getok( lineval, &ix ));
			if( buf[0] == '\0' ) break;
			else expl[i] = atof( buf );
			}
		nexpl = i;
		}
	else if( stricmp( attr, "wraplen" )==0 ) wraplen = atoi( val );
	else Eerr( 1, "attribute not recognized", attr );
	}



/* overrides and degenerate cases */
/* -------------------------- */

if( Nrecords < 1 ) return( Eerr( 17, "No data has been read yet w/ proc getdata", "" ) );
if( df < 0 || df >= Nfields ) return( Eerr( 2840, "invalid datafield", "" ) );
if( cx < 0.0 || cy < 0.0 ) return( Eerr( 2841, "invalid center", "" ) );
if( radius < 0.0 || radius > 5.0 ) return( Eerr( 2842, "invalid radius", "" ) );


if( lblfarout < 0.001 && strnicmp( labelmode, "label", 5 )==0 ) lblfarout = 0.67;
if( lblfarout < 0.001 && strnicmp( labelmode, "line", 4 )==0 ) lblfarout = 1.3;

if( labelbackoutline && labelback[0] == '\0' ) strcpy( labelback, Ecurbkcolor );


/* now do the plotting work.. */
/* -------------------------- */

/* compute total.. */
if( total <= 0.0 ) {
	total = 0.0;
	for( irow = 0; irow < Nrecords; irow++ ) {
		total += atof( da( irow, df ) );
		}
	}

ibb = 0;

if( strnicmp( labelmode, "legend", 6 )==0 ) ilabmode = LEGEND;
else if( strnicmp( labelmode, "labelonly", 5 ) ==0 ) ilabmode = LABEL;
else if( strnicmp( labelmode, "line+label", 4 ) ==0 ) ilabmode = LINELABEL;
else ilabmode = 0;


for( j = 0; j < 2; j++ ) { /* first time - colors; 2nd time, lines */
	theta = starttheta + 6.28319;
	if( j == 1 ) {
		/* set line details for outline.. */
		linedet( "outlinedetails", outlinedetails, 0.5 );
		}
	for( irow = 0; irow < Nrecords; irow++ ) {
		/* take val as % of total and convert to rads */
		dval = atof( da( irow, df ) );
		if( dval == 0.0 ) continue; /* prevent slice attempt on degenerate case - scg 7/21/03 */
		fval = ( dval / total ) * 6.28319; 
		stop = theta - fval;

		/* find (ux,uy), the point at the wedge of the slice, normalized to 0,0 center.. */
		if( nexpl <= 0 ) { /* don't explode any slices */
			ux = 0.0; 
			uy = 0.0;
			}
		else if( irow >= nexpl ) { /* explode slice according to last explode value */
			ux = (expl[nexpl-1]*radius) * cos( theta+(fval/-2.0) );
			uy = (expl[nexpl-1]*radius) * sin( theta+(fval/-2.0) );
			}
		else if( irow < nexpl ) { /* explode slice according to explode value [i] */
			ux = (expl[irow]*radius) * cos( theta+(fval/-2.0) );
			uy = (expl[irow]*radius) * sin( theta+(fval/-2.0) );
			}
			
		first = 1;
		stheta = theta;
		if( j == 1 && strnicmp( outlinedetails, "no", 2 )==0 ) /* break; */ goto DOLAB;  /* changed again, scg 4/29/05 */
		for( ; theta > stop; theta -= 0.03 ) {
			if( theta - stop < 0.03 ) theta = stop;
			x = cx + (radius * cos( theta ));
			y = cy + (radius * sin( theta ));
			if( j == 0 ) {
				if( first ) { 
					first = 0; oldx = x; oldy = y; 
					Emov( cx+ux, cy+uy );
					Epath( x+ux, y+uy );
					continue; 
					}

				Epath( x+ux, y+uy ); 
				oldx = x; oldy = y;
				}
			else if( j == 1 ) {
				if( first ) { Emov( cx+ux, cy+uy ); Elin( x+ux, y+uy ); first = 0; }
				Elin( x+ux, y+uy );
				}
			}
		if( j == 1 ) Elin( cx+ux, cy+uy ); 

		strcpy( color1, "" );
		if( j == 0 ) {
			Epath( cx+ux, cy+uy );

			if( colorfield >=0 ) {  
                		strcpy( color1, "" );
                		PL_get_legent( da( irow, colorfield ), color1, NULL, NULL );
				Ecolorfill( color1 );
                		}

			else if( exactcolorfield >= 0 ) {
				strcpy( color1, da( irow, exactcolorfield ));
				Ecolorfill( color1 );
				}
			else if( stricmp( color[0], "auto" )==0 ) {
				Eicolor( irow, color1 );
				Ecolorfill( color1 );
				}
			else if( irow < ncolors ) Ecolorfill( color[irow] );
			else if( ncolors > 0 ) Ecolorfill( color[ncolors-1] );
			else Ecolorfill( "0.8" );
			}

		/* labeling */  /* if doing legend, handle this during j == 0 because color is available;
				   otherwise do labeling during j == 1 to avoid color fill obliterating labels.. */
		DOLAB:
		if( ( j == 0 && ilabmode == LEGEND ) || ( j == 1 && ilabmode != 0 ) ) {
			strcpy( label, "" );

			sprintf( pctstr, pctfmt, (atof( da( irow, df ) ) / total)*100.0 );
			if( PLS.bignumspacer ) rewritenums( pctstr ); /* added 4/5/03 */

			if( lblfld >= 0 ) strcpy( label, da( irow, lblfld ) );
			else if( lbltextgiven ) GL_getseg( label, PL_bigbuf, &ibb, "\n" );
			else if( lblfmtstring[0] != '\0' ) { /* added scg 8/20/04 */
				strcpy( buf, lblfmtstring );
				GL_varsub( buf, "@PCT", pctstr );
				do_subst( label, buf, irow, NORMAL );
				}


			GL_varsub( label, "@PCT", pctstr );
			convertnl( label );

			/* allow @field substitutions into url */
			if( clickmap_on ) {
				do_subst( expurl, mapurl, irow, URL_ENCODED );
				do_subst( explabel, maplabel, irow, NORMAL );
				}

			/* if( ilabmode == LEGEND )  */ /* changed 7/14/03 scg */
			if( j == 0 && ilabmode == LEGEND ) { 
				if( color1[0] != '\0' ) PL_add_legent( LEGEND_COLOR, label, "", color1, "", "" );
				else PL_add_legent( LEGEND_COLOR, label, "", color[irow], "", "" );
				}

			/* else if( ilabmode == LABEL )  */ /* changed 7/14/03 scg */
			else if( j == 1 && ilabmode == LABEL ) {
				double htheta;
				double x1, y1, x2, y2;
				int centerit = 0;
				htheta = stop + ((stheta - stop) / 2.0 );
				x = cx + ( (radius * lblfarout) * cos( htheta ) );
				y = cy + ( (radius * lblfarout) * sin( htheta ) );
				if( wraplen ) GL_wraptext( label, wraplen ); /* added scg 8/16/05 */
				measuretext( label, &nlines, &maxlen );
				labx = x+ux;
				laby = y+uy;
				boxhi = nlines * Ecurtextheight;
				boxwid = (maxlen * Ecurtextwidth);
				
				if( (htheta >= 7.7 && htheta <= 8.2 ) || (htheta >= 4.5 && htheta <= 5.0 )) centerit = 1;
				if( centerit ) { x1 = labx-(boxwid/2.0); x2 = labx+(boxwid/2.0); }
				else if( x < cx ) { x1 = labx - boxwid; x2 = labx; }
				else { x1 = labx; x2 = labx + boxwid; }
				y1 = laby-boxhi; y2 = laby;

				if( labelback[0] != '\0' ) 
					Ecblock( x1-0.1, y1+(Ecurtextheight*0.6), x2+0.1, y2+Ecurtextheight, labelback, labelbackoutline );

				textdet( "textdetails", textdetails, &align, &adjx, &adjy, -2,"R", 1.0 );
				Emov( labx, laby );
				if( !centerit && x < cx ) Erightjust( label );
				else if( !centerit && x >= cx ) Etext( label );
				else Ecentext( label );
				if( clickmap_on ) 
					clickmap_entry( 'r', expurl, 0, x1, y1+Ecurtextheight, x2, y2+Ecurtextheight, 1, 0, explabel );
				linedet( "linedetails", outlinedetails, 0.5 ); /* restore */
				}

			/* else if( ilabmode == LINELABEL )  */ /* changed 7/14/03 scg */
			else if( j == 1 && ilabmode == LINELABEL ) {
				double htheta, px, py, w, z;

				/* w = radius * lblfarout;
				 * if( w < (1.1 * radius) ) z = lblfarout;
				 */
				if( wraplen ) GL_wraptext( label, wraplen ); /* added scg 8/16/05 */
				measuretext( label, &nlines, &maxlen );
				boxwid = maxlen * Ecurtextwidth;
				boxhi = nlines * Ecurtextheight;

				w = radius * lblfarout;
				if( w < (1.1 * radius) ) z = lblfarout;
				else z = 1.1;

				htheta = stop + ((stheta - stop) / 2.0 );
				px = cx + ( (radius * 0.9 ) * cos( htheta ) );
				py = cy + ( (radius * 0.9 ) * sin( htheta ) );
		
				x = cx + ( (radius * z ) * cos( htheta ) );
				y = cy + ( (radius * z ) * sin( htheta ) );

				linedet( "lablinedetails", lablinedetails, 0.5 ); 
				Emov( px+ux, py+uy );
				Elin( x+ux, y+uy );
				if( x+ux < cx ) {
					Elin( (cx+ux)-w, y+uy );
					textdet( "textdetails", textdetails, &align, &adjx, &adjy, -2,"R", 1.0 );
					labx = ((cx+ux)-w)-0.1;
					laby = y+uy;
					if( labelback[0] != '\0' ) 
						Ecblock( labx-boxwid-0.1, laby-boxhi+(Ecurtextheight*0.6), 
							labx+0.1, laby+Ecurtextheight, labelback, labelbackoutline );

					Emov( labx, laby );
					Erightjust( label );
					if( clickmap_on ) clickmap_entry( 'r', expurl, 0, labx-boxwid, laby-boxhi+Ecurtextheight, 
						labx, laby+Ecurtextheight, 1, 0, explabel );
						
					}
				else 	{
					Elin( (cx+ux)+w, y+uy );
					textdet( "textdetails", textdetails, &align, &adjx, &adjy, -2,"R", 1.0 );
					labx = cx+ux+w+0.1;
					laby = y+uy;
					if( labelback[0] != '\0' ) 
						Ecblock( labx-0.1, laby-boxhi+(Ecurtextheight*0.6), 
							labx+boxwid+0.1, laby+Ecurtextheight, labelback, labelbackoutline );
					Emov( labx, laby );
					Etext( label );
					if( clickmap_on ) clickmap_entry( 'r', expurl, 0, labx, laby-boxhi+Ecurtextheight, 
						labx+boxwid, laby+Ecurtextheight, 1, 0, explabel );

					}
				linedet( "outlinedetails", outlinedetails, 0.5 ); /* restore */
				}
			}


		theta = stop;
		}
	}

return( 0 );
}

/* ======================================================= *
 * Copyright 1998-2005 Stephen C. Grubb                    *
 * http://ploticus.sourceforge.net                         *
 * Covered by GPL; see the file ./Copyright for details.   *
 * ======================================================= */
