#!/bin/bash

LIMIT=50

for ((a=46; a <= LIMIT ; a++))  # Double parentheses, and "LIMIT" with no "$".
do
   java  -Xms1536M -Xmx1536M KDTree/KDTreeMain ../AstroPortal/SDSS/index-SDSS-DR5.txt ../AstroPortal/SDSS/quazar_search_155Kx1_ra_dec.txt ${a} 1000000 > ../AstroPortal/SDSS/quazar_search_155Kx1_ra_dec_loc${a}.txt
done                           # A construct borrowed from 'ksh93'.

           

