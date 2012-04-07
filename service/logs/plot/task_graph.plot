#proc page
  //color: white
  backgroundcolor: white
  linewidth: 2
  //pagesize: 11 8.5
  //for ps
  pagesize: 8.5 11
  #if @DEVICE in gif,png   
    scale: 1
  #endif

#proc getdata
	file: GenericPortalWS_taskPerf.txt
	//file: task_10000.txt
#proc categories
  axis: y
  #comparemethod: exact
	listsize: 1000000
#proc areadef
	#xrange: 0.0 2000
        #xautorange: datafields=4,7
	#yautorange: datafields=1
	#ycategories: datafields=1
	frame: width=0.5 color=gray(0.3)
	#rectangle: 0 0 13 10
	title: Falkon Information per Task
  titledetails: align=C size=14 adjust=0,0.3
  //rectangle: 1 1 10 8
  //for ps
  rectangle: 1 1 8 10
  areaname: whole
     xautorange: datafields=4,7
     yautorange: datafields=1
     #ycategories: datafield 1
        


#proc yaxis
  #ticincrement: 100
  labeldetails: adjust=-0.25
  stubs: inc
  #stubs: categories
  stubcull: yes
  #grid: color=gray(0.5) style=2
  label: Tasks
  tics: yes
  #minorticinc: 10
#proc xaxis
  #ticincrement: 600
  #grid: color=gray(0.5) style=2
  label: Time (sec)
  tics: yes
  stubs: inc
  stubmult: 0.001
  #minorticinc: 100
#proc bars
	horizontalbars: yes
	segmentfields: 4 5
	#barwidth: 0.005
        #thinbarline: color=red      
	color: red
	#tails: 0.03
	outline: no
#proc legendentry
  sampletype: color
  label: Wait Queue
  details: red
#proc bars
	horizontalbars: yes
	segmentfields: 5 6
	#barwidth: 0.005
        #thinbarline: color=green      
	color: green
	#tails: 0.03
	outline: no
#proc legendentry
  sampletype: color
  label: Execute
  details: green
#proc bars
	horizontalbars: yes
	segmentfields: 6 7
	#barwidth: 0.005
        #thinbarline: color=black      
	#tails: 0.03
	color: black
	outline: no
#proc legendentry
  sampletype: color
  label: Deliver
  details: black
#proc legend
location: max-0.6 max-0.2
colortext: yes
