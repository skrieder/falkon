// set page-wide attributes appropriate for slides (proc page)
#proc page
  //color: white
  backgroundcolor: white
  linewidth: 2
  pagesize: 11 8
  #if @DEVICE in gif,png   
    scale: 1
  #endif

// specify data (proc getdata)
#proc getdata
  file: ../service/logs/GenericPortalWS_perf_per_sec.log

// set up plotting area (proc areadef)
#proc areadef
  title: Falkon Summary 
  titledetails: align=C size=14 adjust=0,0.3
  rectangle: 1 1 10 7
  areaname: slide
     xautorange: datafields=1
     yautorange: datafields=5,8,9,13


// do filled pink curve (proc lineplot)
#proc lineplot
  yfield: 5
  fill: red
  legendlabel: Idle Executors

// do filled blue curve (proc lineplot)
#proc lineplot
  yfield: 8
  fill: green
  legendlabel: Active Executors

// do curve w/ diamond points (proc lineplot)
#proc lineplot
  yfield: 9
  //pointsymbol: shape=pixdiamond style=filled fillcolor=yellow 
  linedetails: width=0.5 color=blue
  legendlabel: Wait Queue Length

// do curve w/ square points (proc lineplot)
#proc lineplot
  yfield: 13
  //pointsymbol: shape=pixsquare style=filled fillcolor=orange
  linedetails: width=0.5 color=black
  legendlabel: Delivered Tasks


#proc yaxis
     stubs: inc
#  stubs: inc 10
  //grid: color=powderblue
  label: Queue Length / Number of Executors

#proc xaxis
  stubs: inc
  tics: yes
#  stubformat: mm:ss
  //grid: color=orange style=2
  label: Time (sec)


// do axes (proc xaxis, proc yaxis) last so they are not obliterated
#proc xaxis
//  stubrange: 0 6
//  stubs: text
//	March\n1996
//	April\n1996
//	May\n1996
//	June\n1996
//	July\n1996

#proc yaxis
//  stubs: inc 10


// render legend using labels specified above (proc legend)
#proc legend
  location: max-1 max
