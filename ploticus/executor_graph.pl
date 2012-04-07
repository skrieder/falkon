#proc page
#if @DEVICE in gif,png
  scale: 1.0
#endif

#proc getdata
#  delim: comma
  file: ../service/logs/GenericPortalWS_taskPerf.log

#proc areadef
  title: Falkon Information per Executor
  #titledetails: align=C style=B size=18
#  areaname: whole
#  xscaletype: time
  xautorange: datafields=11,12
#  xrange: 0 11300
#  yrange: 0 200
#  yautorange: datafield=1
   ycategories: datafield 2
#  frame: bevel

#proc yaxis
  stubs: categories
#  stubs: inc 10
  grid: color=powderblue
  label: Executors

#proc xaxis
  stubs: inc 1000000
  tics: yes
#  stubformat: mm:ss
  grid: color=orange style=2
  label: Time (ms)

#proc bars
  axis: x
  outline: color=blue
  locfield: 1
  segmentfields: 11 12
  barwidth: 0.001
  tails: 0.02

