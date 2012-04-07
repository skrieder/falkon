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
	file: ../falkon_task_perf.txt
	//file: exec_10000.txt
#proc categories
        axis: y
	listsize: 1000000
#proc areadef
	frame: width=0.5 color=gray(0.3)
	title: Falkon Information per Task
  titledetails: align=C size=14 adjust=0,0.3
  //rectangle: 3 1 10 8
  //for ps
  rectangle: 3 1 8 10
  areaname: whole
     xautorange: datafields=5,6
     ycategories: datafield 3
        


#proc yaxis
  stubs: categories
  stubcull: yes
  label: Executors
  tics: yes
  labeldetails: adjust=-2.3
#proc xaxis
  label: Time (sec)
  tics: yes
  stubs: inc
  stubmult: 0.001
#proc bars
	horizontalbars: yes
        locfield: 3
	segmentfields: 5 6
	barwidth: 0.0003
        #thinbarline: color=green      
	tails: 0.001
	color: green
	outline: no
#proc legendentry
  sampletype: color
  label: Execute
  details: green
#proc legend
location: max-0.05 max-0.05
colortext: yes
