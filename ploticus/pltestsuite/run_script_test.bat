REM MSDOS batch file to run pl examples..
REM
REM 
echo off
set PL=pl

REM which %PL

REM available modes are generally: gif, svg, eps
set MODE=gif

echo Testing %PL% .. output format will be %MODE%
echo Be sure you have pl.exe in your PATH, or else copied into this dir..
pause


set OPTS=

echo "--------- stock2..." 
%PL% -%MODE% %OPTS% stock2.htm 
echo "--------- timely..." 
%PL% -%MODE% %OPTS% timely.htm 
echo "Note: some numbers should have appeared above..  " 
echo "--------- clickmap_mouse... (should produce a server-side image map file..)" 
%PL% -%MODE% %OPTS% -map clickmap_mouse.htm 
echo "--------- kmslide..." 
%PL% -%MODE% %OPTS% kmslide.htm 
echo "--------- propbars1..." 
%PL% -%MODE% %OPTS% propbars1.htm 
echo "--------- td..." 
%PL% -%MODE% %OPTS% td.htm 
echo "5 plots done"
echo "--------- distrib..." 
%PL% -%MODE% %OPTS% distrib.htm 
echo "--------- errbar5..." 
%PL% -%MODE% %OPTS% errbar5.htm 
echo "Note: some warnings are ok in errbar5..  " 
echo "--------- scatterplot10..." 
%PL% -%MODE% %OPTS% scatterplot10.htm 
echo "--------- errbar1..." 
%PL% -%MODE% %OPTS% errbar1.htm 
echo "--------- devol..." 
%PL% -%MODE% %OPTS% devol.htm 
echo "10 plots done"
echo "--------- lineplot4..."
%PL% -%MODE% %OPTS% lineplot4.htm 
echo "--------- lineplot5..." 
%PL% -%MODE% %OPTS% lineplot5.htm 
echo "--------- pie1..." 
%PL% -%MODE% %OPTS% pie1.htm 
echo "--------- bars3..." 
%PL% -%MODE% %OPTS% bars3.htm 
echo "--------- quarters..." 
%PL% -%MODE% %OPTS% quarters.htm 
echo "15 plots done"
echo "--------- timeline2..." 
%PL% -%MODE% %OPTS% timeline2.htm 
echo "--------- scatterplot4..." 
%PL% -%MODE% %OPTS% scatterplot4.htm 
echo "--------- annot2..." 
%PL% -%MODE% %OPTS% annot2.htm 
echo "--------- drawcom..." 
%PL% -%MODE% %OPTS% drawcom.htm 
echo "--------- hitcount3..." 
%PL% -%MODE% %OPTS% hitcount3.htm 
echo "Note: there should be 8 'warning: time is outside of window range' msgs above..  " 
echo "20 plots done.."
echo "--------- lineplot20..." 
%PL% -%MODE% %OPTS%  lineplot20.htm 
echo "--------- colorgrid..." 
%PL% -%MODE% %OPTS%  colorgrid.htm 
echo "--------- heatmap3..." 
%PL% -%MODE% %OPTS%  heatmap3.htm 
echo "--------- vector1..." 
%PL% -%MODE% %OPTS%  vector1.htm 
echo "--------- windbarbs..." 
%PL% -%MODE% %OPTS%  windbarbs.htm 


echo "Finished."
