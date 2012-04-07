/* test libploticus - execute a bunch of scripts in the pltestsuite directory.. */

/* execute this in the ./pltestsuite directory! */

#include <stdio.h>

#define NSCRIPTS 34

char *scriptfiles[NSCRIPTS+1] = {
"annot2.htm",
"bars3.htm",
"drawcom.htm",
"errbar1.htm",
"errbar5.htm",
"heatmap3.htm",
"hitcount3.htm",
"kmslide.htm",
"lineplot20.htm",
"lineplot4.htm",
"lineplot5.htm",
"pie1.htm",
"clickmap_mouse.htm",  /* repeat this one to check clickmap mem usage.. */
"clickmap_mouse.htm",
"clickmap_mouse.htm",
"clickmap_mouse.htm",
"clickmap_mouse.htm",
"devol.htm",
"distrib.htm",
"propbars1.htm",
"quarters.htm",
"scatterplot10.htm",
"scatterplot4.htm",
"stock2.htm",
"td.htm",
"colorgrid.htm",  /* repeat this one to check category clickmap mem usage.. */
"colorgrid.htm",
"colorgrid.htm",
"colorgrid.htm",
"colorgrid.htm",
"timeline2.htm",
"timely.htm",
"vector1.htm",
"windbarbs.htm" };

main()
{
int i, stat;
char outfilename[128], oname[128], buf[512];

mkdir( "api_test_output", 00755 );

sprintf( buf, "ps -p %d -o vsz -o rss >&2", getpid() ); 
system( buf ); 

for( i = 0; i < NSCRIPTS; i++ ) {

	fprintf( stderr, "%s ...\n", scriptfiles[i] );

	strcpy( oname, scriptfiles[i] );
	oname[ strlen( oname )-4 ] = '\0';
	sprintf( outfilename, "api_test_output/%s.swf", oname );

	stat = ploticus_init( "swf", outfilename );
	if( stat ) { fprintf( stderr, "error %d on ploticus_init\n", stat ); exit(1); }

	/* for the clickmap example, specify -map .. */
	if( strncmp( scriptfiles[i], "clickmap", 8 )==0 ) {
		stat = ploticus_arg( "-map", "" );
		if( stat )  { fprintf( stderr, "error %d on ploticus_arg\n", stat ); exit(1); }
		}

	/* for one of the examples, specify -scale 0.9.. */
	if( strcmp( scriptfiles[i], "lineplot20.htm" )==0 ) {
		stat = ploticus_arg( "-scale", "0.9" );
		if( stat )  { fprintf( stderr, "error %d on ploticus_arg\n", stat ); exit(1); }
		}

	stat = ploticus_execscript( scriptfiles[i], 0 );
	if( stat ) { fprintf( stderr, "error %d on ploticus_execscript\n", stat ); exit(1); }

	stat = ploticus_end();
	if( stat ) { fprintf( stderr, "error %d on ploticus_end\n", stat ); exit(1); }

	sprintf( buf, "ps -p %d -o vsz -o rss >&2", getpid() ); 
	system( buf ); 
	}

/* embedded script lines.. */
fprintf( stderr, "embedded script lines ...\n" );

stat = ploticus_init( "png", "api_test_output/embedded.png" );
if( stat ) { fprintf( stderr, "error %d on ploticus_init\n", stat ); exit(1); }

stat = 0;
stat += ploticus_execline( "#proc annotate" );
stat += ploticus_execline( "  location: 2 2" );
stat += ploticus_execline( "  text: embedded script lines" );
stat += ploticus_execline( "  test!" );
stat += ploticus_execline( "" );
if( stat ) { fprintf( stderr, "error %d on ploticus_execline\n", stat ); exit(1); }

stat = ploticus_end();
if( stat ) { fprintf( stderr, "error %d on ploticus_end\n", stat ); exit(1); }

sprintf( buf, "ps -p %d -o vsz -o rss >&2", getpid() ); 
system( buf ); 

exit( 0 );
}
