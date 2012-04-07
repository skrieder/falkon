/* simple example to load and execute a pl script, producing a png result */

/* compile and load w/ libploticus, libpng, libz, and -lm */

/* scg> cc api_examp.o libploticus.a -o api_examp /home/scg/lib/libpng.a /home/scg/lib/libz.a -lm */

#include <stdio.h>
#define RESULT_TYPE "png"

main( argc, argv )
int argc;
char **argv;
{
int stat;
char buf[256];

if( argc != 3 ) {
	fprintf( stderr, "usage: api_examp   plscriptfile   outputfile\n" );
	exit( 1 );
	}

/* show preliminary mem usage */
sprintf( buf, "ps -p %d -o vsz -o rss >&2", getpid() );
system( buf );

stat = ploticus_init( RESULT_TYPE, argv[2] );
if( stat ) { fprintf( stderr, "error %d on ploticus_init\n", stat ); exit(1); }

stat = ploticus_arg( "-debug", "" );
if( stat )  { fprintf( stderr, "error %d on ploticus_arg\n", stat ); exit(1); }

stat = ploticus_execscript( argv[1], 0 );
if( stat ) { fprintf( stderr, "error %d on ploticus_execscript\n", stat ); exit(1); }

stat = ploticus_end();
if( stat ) { fprintf( stderr, "error %d on ploticus_end\n", stat ); exit(1); }

/* show final mem usage */
sprintf( buf, "ps -p %d -o vsz -o rss >&2", getpid() );
system( buf );
}

