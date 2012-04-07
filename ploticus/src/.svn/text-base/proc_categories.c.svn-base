/* ======================================================= *
 * Copyright 1998-2005 Stephen C. Grubb                    *
 * http://ploticus.sourceforge.net                         *
 * Covered by GPL; see the file ./Copyright for details.   *
 * ======================================================= */

/* PROC CATEGORIES - define or set attribute(s) for a set of categories */

#include "pl.h"

int
PLP_categories()
{
char attr[NAMEMAXLEN], val[256];
char *line, *lineval;
int nt, lvp;
int first;

char axis;
double slideamount;
char dfield[NAMEMAXLEN];
char selex[255];

TDH_errprog( "pl proc categories" );

slideamount = -99999.9;
axis = '\0';
strcpy( dfield, "" );
strcpy( selex, "" );


/* note: old AREADEF and CATSLIDE category-related attributes supported here as 'overlays'..
 *	hence, beware of attribute name collision with those procs */

/* note: proc categories is unusual in that attribute order does matter..  
 *	- 'axis' and 'listsize' (if used) must specified before other attributes.  
 *	- extracategory adds categories pre or post depending on position relative to category set fill
 *	- select must be given before datafield
 *	- #clone is not supported for this proc 
 */

/*  get attributes.. */
first = 1;
while( 1 ) {
	line = getnextattr( first, attr, val, &lvp, &nt );
	if( line == NULL ) break;
	first = 0;
	lineval = &line[lvp];

	if( stricmp( attr, "axis" )==0 ) {
		axis = tolower( val[0] );
		PL_setcatparms( axis, "compmethod", -1 ); /* default to exact.. */
		}

	else if( GL_slmember( attr, "?categories categories" )) {
		/* put category names into array slots now.. */
		if( strnicmp( val, "datafield", 9 )==0 ) strcpy( PL_bigbuf, lineval );
		else getmultiline( "categories", lineval, MAXBIGBUF, PL_bigbuf );
		if( attr[0] == 'x' || attr[0] == 'y' ) PL_setcats( attr[0], PL_bigbuf );
		else if( axis == '\0' ) return( Eerr( 2795, "'axis' expected as first attribute", "" ));
		else PL_setcats( axis, PL_bigbuf );
		}

	else if( GL_slmember( attr, "?extracategory extracategory" )) {
		char ax;
		if( attr[0] == 'x' || attr[0] == 'y' ) ax = attr[0];
		else ax = axis;
		if( ax == '\0' ) return( Eerr( 2795, "'axis' expected as first attribute", "" ));
		if( PL_ncats( ax ) < 1 ) PL_addcat( ax, "pre", lineval );
		else PL_addcat( ax, "post", lineval );
		}

	else if( GL_slmember( attr, "comparemethod catcompmethod" )) {
		/* old 'catcompmethod' affects both axes */
		int p;
		if( stricmp( val, "exact" )==0 ) p = -1;
		else if( strnicmp( val, "length=", 7 )==0 ) p = atoi( val );
		else p = 0;
		if( axis == 'x' || axis == '\0' ) PL_setcatparms( 'x', "compmethod", p );
		if( axis == 'y' || axis == '\0' ) PL_setcatparms( 'y', "compmethod", p );
		}

	else if( stricmp( attr, "slideamount" )==0 || stricmp( attr, "amount" )==0 ) slideamount = atof( val );

	else if( stricmp( attr, "datafield" )==0 ) {
		if( axis == '\0' ) return( Eerr( 2795, "'axis' expected as first attribute", "" ));
		sprintf( PL_bigbuf, "datafield=%s %s%s", val, (selex[0]=='\0')?"":"selectrows=", selex );
		PL_setcats( axis, PL_bigbuf );
		}

	else if( stricmp( attr, "select" )==0 ) strcpy( selex, lineval );

	else if( stricmp( attr, "listsize" )==0 ) {
		if( axis == '\0' ) return( Eerr( 2795, "'axis' expected as first attribute", "" ));
		PL_setcatparms( axis, "listsize", atoi( val ) );
		}

	else if( stricmp( attr, "checkuniq" )==0 ) {
		if( axis == '\0' ) return( Eerr( 2795, "'axis' expected as first attribute", "" ));
		if( tolower( val[0] ) == 'y' ) PL_setcatparms( axis, "checkuniq", 1 );
		else PL_setcatparms( axis, "checkuniq", 0 );
		}

	else if( stricmp( attr, "roundrobin" )==0 ) {
		if( axis == '\0' ) return( Eerr( 2795, "'axis' expected as first attribute", "" ));
		if( tolower( val[0] ) == 'y' ) PL_setcatparms( axis, "roundrobin", 1 );
		else PL_setcatparms( axis, "roundrobin", 0 );
		}

	/* else Eerr( 1, "attribute not recognized", attr ); */ /* can't do this because we could be processing an areadef block */
	}

if( slideamount > -100.0 && slideamount < 100.0 ) {
	if( axis == '\0' ) return( Eerr( 2795, "'axis' expected as first attribute", "" ));
	Esetcatslide( axis, slideamount );
	}

return( 0 );
}

/* ======================================================= *
 * Copyright 1998-2005 Stephen C. Grubb                    *
 * http://ploticus.sourceforge.net                         *
 * Covered by GPL; see the file ./Copyright for details.   *
 * ======================================================= */
