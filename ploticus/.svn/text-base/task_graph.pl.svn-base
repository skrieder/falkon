#proc page
#if @DEVICE in gif,png
  scale: 1.0
#endif

#proc getdata
#  delim: comma
  file: ../service/logs/GenericPortalWS_taskPerf.log

// set up plotting area using proc areadef
// Y scale will be categories, using the labels in field 1 of the data.. 
#proc areadef
  title: Falkon Information per Task
#  rectangle: 2 2 5.5 4
  xautorange: datafields=5,9
  yscaletype: categories
  ycategories: datafields 1


#proc yaxis
  stubs: categories
#     stubs: usecategories
#  stubs: inc 10
  grid: color=powdergreen
  label: Tasks

#proc xaxis
  stubs: inc 1000000
  tics: yes
#  stubformat: mm:ss
  grid: color=orange style=2
  label: Time (ms)

// do red bars using proc bars
#proc bars
  horizontalbars: yes
  lenfield: 2
  color: white
  legendlabel: Start Time

// do purple bars using proc bars
// (they are "stacked" above data field 2)
#proc bars
  horizontalbars: yes
  lenfield: 3
  color: red
  stackfields: 2
  legendlabel: Wait Queue Time

// do yellow bars using proc bars
// (they are "stacked" above data fields 2 and 3)
#proc bars
  horizontalbars: yes
  lenfield: 4
  color: green
  stackfields: 2 3
  legendlabel: Execution Time

// do green bars using proc bars
// (they are "stacked" above data fields 2, 3, and 4)
#proc bars
  horizontalbars: yes
  lenfield: 5
  color: black
  stackfields: 2 3 4
  legendlabel: Delivery Time

// render legend using labels supplied above (proc legend)
#proc legend
  location: min-0.6 min-0.5
  textdetails: size=5
  format: singleline
  sep: 0.4
