<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>

<t:base type="jquery,easyui,tools,DatePicker,autocomplete"></t:base>

<link rel="stylesheet" href="highcharts/jquery.treeview.css" />
<script src="highcharts/lib/jquery.cookie.js" type="text/javascript">
                </script>
<script src="highcharts/jquery.treeview.js" type="text/javascript">
                </script>
<script type="text/javascript" src="highcharts/highcharts.js">
                </script>
<script type="text/javascript" src="highcharts/highcharts-class.js">
                </script>
<script type="text/javascript" src="highcharts/exporting.js">
                </script>
<script src="js/map.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<style type="text/css">

#table4 {
	position: absolute;
	z-index: 8;
}

#table5 {
	position: absolute;
	z-index: 8;
	display: none
}

panel-body {
	color: #F4FAFF;
}

table.altrowstable {
	width: 100%;
	height: 100%;
	font-family: "新宋体";
	font-weight: bold;
	font-size: 9px;
	border: 2px solid #b9d8f3;
	border-collapse: collapse;
	background-color: #F4FAFF;
}

table.altrowstable td {
	border-width: 1px;
	border: 2px solid #b9d8f3;
}

.table {
	width: 100%;
	height: 100%;
}

.anchorBL {
	display: none;
}



.run {
	padding: 2px;
	font-size: 12px;
	color: white;
	border-radius: 3px;
	background-color: #0A8021;
}

.off {
	padding: 2px;
	font-size: 12px;
	color: white;
	border-radius: 3px;
	background-color: #BEBEBE;
}

.warning {
	padding: 2px;
	font-size: 12px;
	color: white;
	border-radius: 3px;
	background-color: #CC6701;
}

#trs {
	text-align: center;
	COLOR: #0076C8;
	BACKGROUND-COLOR: #F4FAFF;
	font-weight: bold;
	height: 8px'
}

.wrap {
	width: 99.5%;
	height: 100%;
	border-radius: 8px;
	BORDER: #b7c5d9 1px solid;
	BACKGROUND: #F9FDFF
}
</style>

<script type="text/javascript"
	src="http://api.map.baidu.com/api?v=2.0&ak=${bdkey}"></script>
</head>
<body>
	<div class="easyui-panel" fit="true">
		<table class="table" border="0" cellpadding="0" bordercolor="#66FF33">
			<tr>
				<!--<td rowspan="2" class="td hover" id="topleft"
					style="width: 65%; height: 100%;" align="center">-->
				<!--	<div id="table4">
						<a href="javascript:void(0);" class="easyui-menubutton"
							menu="#layout_north_kzmbMenu" iconCls="icon-comturn"
							style="color: #242023; background-color: #E7E4E6;"> <t:mutiLang
								langKey="设置" />
						</a> &nbsp;&nbsp; <input type="BUTTON" name="FullScreen" value="全屏查看"
							onClick="fullScreen();">
						<input type="BUTTON" name="FullScreen" value="地图全屏"
							onClick="mainPageFull();">&nbsp;&nbsp; 
								  <input name="carNumber" type="text" id="carNumber" value="${carNumber}" placeholder="请输入车牌号/VIN/终端号"/>
					 <input id="Button1" type="button"  onClick="SelectGps();" value="查询"/>
					</div>
					<div id="table5">
						<a href="javascript:void(0);" class="easyui-menubutton"
							menu="#layout_north_kzmbMenu" iconCls="icon-comturn"
							style="color: #242023; background-color: #E7E4E6;"> <t:mutiLang
								langKey="设置" />
						</a> &nbsp;&nbsp; <input type="BUTTON" name="FullScreen" value="退出全屏"
							onClick="window.close()">&nbsp;&nbsp; 
								  <input name="carNumber" type="text" id="carNumber" placeholder="请输入车牌号/VIN/终端号"/>
					 <input id="Button1" type="button" onClick="SelectGps();" value="查询"/>
					</div>-->
					
				<!--	<div id="layout_north_kzmbMenu"
						style="width: 100px; display: none;">
						<div onclick="mquery();">
							<t:mutiLang langKey="过滤条件查询" />
						</div>-->
						
						<input type="hidden" value="${factory}" id="factory" /> 
						<input type="hidden" value="${carType}" id="carType" /> 
						<input type="hidden" value="${province}" id="province" />
						 <input type="hidden" value="${city}" id="city" />
						  <input type="hidden" value="${district}" id="district" />
						   <input type="hidden" value="${operatorName}" id="operatorName" /> 
						   <input type="hidden" value="${customerName}" id="customerName" /> 
						   <input type="hidden" value="${carStatus}" id="carStatus" /> 
						     <input type="hidden" value="${carNumber}" id="carNum" /> 
						<!--<div onclick="mre();">
							<t:mutiLang langKey="设置刷新频率" />
						</div>
						<input type="hidden" value="30000" id="mtime" />
					</div>-->
					<div id="allmap"
						style="width: 100%; height: 100%;margin:0; border-radius: 8px;"></div>
			<!--	</td>-->
				<!--<td id="topcenter" class="td hover" style="height: 75%;">
					<table width="100%" border="1" height="100%" align='center'
						class="altrowstable">
						<tr>
							<td style="width: 15%; height: 8%;" colspan="4"><div align="center">信息看板</div></td>

						</tr>
						<tr>
							<td class="td tdhover" style="width: 45%; height: 45%;">
								<DIV class="wrap" id="topmiddle"></DIV>
							</td>
							<td class="td tdhover" style="width: 45%; height: 45%;">
								<DIV class="wrap" id="topright"></DIV>
							</td>
						</tr>
						<tr>
							<td class="td tdhover" style="width: 25.5%; height: 20%;"><img
 								src='images/background_licheng.jpg'style="vertical-align:middle;" width='22%' height='40%' /><span id="trs"style="font-size:115%; line-height:600%;height:600%">总里程(万公里):</span>
								<div id="font_t"style="font-size:115%;color:#00008B;text-align:center;">0.00</div></td>
							<td class="td tdhover" style="width: 25.5%; height: 20%;"><img
								src='images/page02.png'style="vertical-align:middle;" width='22%' height='40%' /><span
								id="trs"style="font-size:115%;line-height:600%;height:600%">总碳减排量(吨):</span>
								<div style="font-size:115%;color:#00008B;text-align:center;" id="font_h">0.0</div></td>

						</tr>
						<tr>
							<td class="td tdhover" style="width: 25.5%; height: 20%;"><img
								src='images/page01.png' style="vertical-align:middle;"width='22%' height='40%' /><span
								id="trs"style="font-size:115%;line-height:600%;height:600%">总节油量(升):</span>
								<div style="font-size:115%;color:#00008B;text-align:center;" id="font_f">0.0</div></td>
							<td class="td tdhover" style="width: 25.5%; height: 20%;"><img
								src='images/page03.png' style="vertical-align:middle;"width='22%' height='40%' /><span
								id="trs"style="font-size:115%;line-height:600%;height:600%">总耗电量(度):</span> 
								<div style="font-size:115%;color:#00008B;text-align:center;"id="font_g">0.0</div></td>
						</tr>
					</table>
				</td>-->
			</tr>
			<tr>
			<!--	<td id="middlecenter" class="td hover" style="height: 35%;">
					<table width='100%' id="middlecenter" cellpadding="0px"
						cellspacing="0px" style="height: 100%;">
						<tr>
							<td style="height: 9%;"><div align="center"
									style="text-align: center; BACKGROUND-COLOR: #F4FAFF; font-weight: bold;">实时告警</div></td>
						</tr>
						<tr style="height: 7%;">
							<td>
								<table border='0' width='100%' align='center'
									style='background-color: #b9d8f3;'>
									<tr id="th"
										style='text-align: center; COLOR: #0076C8; BACKGROUND-COLOR: #F4FAFF; font-weight: bold; height: 8px'>
										<td width='30%'>
											<div align='center'>
												<span> 车牌号 </span>
											</div>
										</td>
										<td width='30%'>
											<div align='center'>
												<span> 采集时间 </span>
											</div>
										</td>
										<!--   <td width='20%'>
                                            <div align='center'>
                                                <span>
                                                    报警编号
                                                </span>
                                            </div>
                                        </td> 
										<td width='20%'>
											<div align='center'>
												<span> 级别 </span>
											</div>
										</td>
										<td width='20%'>
											<div align='center'>
												<span> 操作 </span>

												<div id="html"></div>
											</div>
										</td>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
									</tr>
								</table>
							<td>
						</tr>
						<tr>
							<td>
								<div id="affiche"
									style="overflow-y: auto; width: 100%; height: 100%; background-color: #F4FAFF;">
								</div>
							</td>
						</tr>
					</table>

				</td>-->
			</tr>
		</table>
		<input type="hidden" id="vin" value="${vin}" /> <input type="hidden"
			id="id" value="${id}" />
	</div>

</body>

<script type="text/javascript">


	var a = ${id};
	
	if(a == '2'){
		document.getElementById('table4').style.display = "none";
		document.getElementById('table5').style.display = "block";
	}	 
	var map = new BMap.Map("allmap",{minZoom:5,maxZoom:17}); // 创建Map实例,{mapType: BMAP_HYBRID_MAP}
	map.centerAndZoom(new BMap.Point(116.4100451627, 39.916976551955), 5); // 初始化地图,设置中心点坐标和地图级别
	map.enableScrollWheelZoom(true);
	var mapzoomlevel = 5;//地图缩放等级     5-7省级  8-9市级   10-n车辆坐标
	var oldvid = null;
	var oldprovince = null;
	var oldcity = null;
	
	SelectGps();
	getWarnning();
	map.addEventListener("zoomend", function(e) {
		mapzoomlevel = this.getZoom();
		if (mapzoomlevel < 6) {
			oldvid = null;
			oldcity = null;
			/*  $("#province").val(""); */
			
		}
		if (mapzoomlevel > 5 && mapzoomlevel < 10) {
			oldvid = null;
			oldprovince = null;
		/* 	 $("#city").val(""); */
		}
		if (mapzoomlevel > 9) {
			oldprovince = null;
			oldcity = null;
		}
		SelectGps();
		getWarnning();
	});
		var markerArr = new Array();
		var warnrArr = new Array();	
		
		var mo = new Map();
		function rere(obj){
		   setInterval(function() {
				SelectGps();
				getWarnning();
			}, obj);
			
		}
		setInterval(function() {
			var display = $(".BMap_pop").css("display");
			if (display == "none") {
			}
		}, 500);	
		rere(30000);
		
		 
		var	warnlist=[];
		 function getWarnning() {
			 warnrArr=[];
			 var carType = $("#carType").val();
				var factory =  $("#factory").val();
				var province = $("#province").val();
				var city = $("#city").val();
				var district = $("#district").val();
				var operatorName = $("#operatorName").val();
				var customerName = $("#customerName").val();
				var carStatus = $("#carStatus").val();
			 var param="1=1&typeId="+carType+"&vendorId="+factory+"&province="+province+"&city="+city+"&district="+district
				+"&carrieroperator="+operatorName+"&customerId="+customerName+"&state="+carStatus;
			  $.ajax({
				  type : "POST",
					url : "mainPageController.do?getMapWarn&param="+param,
					dataType : "text",
					success : function(data) {
						warnlist=[];
						var d = $.parseJSON(data);
						var wwlist =d.attributes.warningList;
						if(wwlist!=null){
							warnlist=warningList(wwlist);
					        JsonSortWarning(warnlist); 
							
						}
	                  }
	          });
	       } 
		 
		 function topmiddle(dataTmp) {
             $('#topmiddle').highcharts({
            	   chart: {
                       plotBackgroundColor: null,
                       plotBorderWidth: null,
                       plotShadow: false,
                       borderRadius: 8,
                       borderWidth:0
                   },
                   title: {
                       text: '车辆类型统计'
                   },
                   plotOptions: {
                       pie: {
                           allowPointSelect: true,
                           cursor: 'pointer',
                           dataLabels: {
                               enabled: true,
                               color: '#000',    
                               formatter: function(index) {      
                                   return  '<span style="color:#00008B;font-weight:bold">' + this.point.name + '</span>';  
                              }  ,
                               distance: 8,
                               connectorPadding:0,
                           }
                       }
                   },
                   credits: {
                       enabled: false
                   },
                   exporting: {
                       enabled: false
                   },
                   series: [{
                       type: 'pie',
                       name: '数量',
                       data: eval("[" + dataTmp + "]")
                   }]
               });
           }
		  
		 function topright(dataTmp) {
			 $('#topright').highcharts({
                 chart: {
                     plotBackgroundColor: null,
                     plotBorderWidth: null,
                     plotShadow: false,
                     borderRadius: 8,
                     borderWidth:0
                 },
                 title: {
                     text: '注册/在线数统计'
                 },
                 xAxis: {
                     categories: ['注册数', '在线数']
                 },
                 yAxis: {
                	 lineWidth : 0,
                	 tickInterval:1, // 刻度值  
                	 },
                 plotOptions: {
                     pie: {
                         allowPointSelect: true,
                         cursor: 'pointer',
                         dataLabels: {
                             enabled: false,
                             color: '#000000',
                             connectorColor: '#000000',
                             format: '<b>{point.name}'
                         }
                     }
                 },
                 credits: {
                     enabled: false
                 },
                 exporting: {
                     enabled: false
                 },
                 series: [{
                     type: 'column',
                     name: '数量',
                     data: eval("[" + dataTmp + "]")
                 }]
             });
           
           }
	
		var derc=null;
		var dercs=null;

		var mapType=null;
		var onlineMap =null;
		var countReg =0;
		var mileage = 0;
		var online=0;
		var data_info = new Array();
			 
		function SelectGps() {
			//getCarCountByCarType();
			map.clearOverlays();
			 var carNumber=$("#carNumber").val();
			 if(carNumber==null ||carNumber=="" || carNumber==undefined){
				 loadMainPage();
			 }else{
				 onLoadCar();
			 }
		}
	  function loadMainPage() {
		 
		    var carType = $("#carType").val();
			 var factory =  $("#factory").val();
			 var province = $("#province").val();
			 var city = $("#city").val();
			 var district = $("#district").val();
			 var operatorName = $("#operatorName").val();
			 var customerName = $("#customerName").val();
			 var carStatus = $("#carStatus").val();
			 var param="1=1&typeId="+carType+"&vendorId="+factory+"&province="+province+"&city="+city+"&district="+district
					+"&carrieroperator="+operatorName+"&customerId="+customerName+"&state="+carStatus;
			 $.ajax({
						type : "POST",
						url : "mainPageController.do?selectGps&param="+param,
						dataType : "text",
						success : function(data) {
							var d = $.parseJSON(data);
							var list =d.attributes.rspclst;
						
							
							online=0;
							markerArr= new Array();
							data_info = new Array();
							for (var i = 0, keys = mo.keySet(), len = keys.length; i < len; i++) {
								mo.put(keys[i],"0");

							} 
							countReg =d.attributes.total;
							onlineMap =d.attributes.onlineMap;
							mileage = d.attributes.mileage;
							mapType=d.attributes.mapType;
							for(var key in mapType){
								if(Number(key)!=0 ){
									mo.put(key, mapType[key]);
								}
							}
							 var dataTmp='';
							 var temp='';
							 for (var i = 0, keys = mo.keySet(), len = keys.length; i < len; i++) {
								 if(Number(mo.get(keys[i]))!=0){
									 dataTmp += "['" +keys[i] + "'," + mo.get(keys[i]) + "]" + ",";
								 }
							 }
							for(var key in onlineMap){
									if(Number(key)==1  ){
										online= onlineMap[key];
									}
								}
							 temp = "['注册数'," + countReg + "],['在线数'," +online+ "]";
							 topright(temp); 
							 topmiddle(dataTmp); 
							 
								
							 $("#font_t").html((mileage / 10000).toFixed(2)); //总里程
							 $("#font_f").html((mileage/100*15).toFixed(1));//总节油量
							 $("#font_h").html(((mileage/100*15) / 300.28).toFixed(1));//总碳减排量
							 $("#font_g").html((mileage / 10000 * 2000).toFixed(1)); //总耗电量 */
							if (mapzoomlevel < 6) {//5-7省级
							   if(list!=null){
									
								for (var i = 0; i < list.length; i++) {
									if (list[i].plong != null
											&& list[i].plat != null
											&& list[i].plong != 0
											&& list[i].plat != 0) {
										var lng = list[i].plong;
										var lat = list[i].plat;
										var provinceCount=list[i].count;
									    var content="<span style='font-size:12px'><b>车辆总数</b>："
										+ provinceCount
										+ "<br/>"
										+ "</span><span style='float: right;font-size:12px'><a href='javascript:UpdateZoom(8)'>展开</a></span>"
										var arrpoin = new Array();
										arrpoin.push(lng);
										arrpoin.push(lat);
										arrpoin.push(content);
										arrpoin.push(list[i].province);
										arrpoin.push(provinceCount);
										data_info.push(arrpoin);
									}
								}
								for (var i = 0; i < data_info.length; i++) {
									var pt = new BMap.Point(data_info[i][0],
											data_info[i][1]);
									
										var myIcon = new BMap.Icon(
												"images/province02.png",
												new BMap.Size(68, 68),{
													anchor : new BMap.Size(28, 30),
													
													imageOffset: new BMap.Size(0, 0)
												});
										var marker = new BMap.Marker(pt, {
											icon : myIcon
										}); // 创建标注
										map.addOverlay(marker);
										var content = data_info[i][2];
										var title = data_info[i][3];
										var provinceCount = data_info[i][4];
										var label=null;
										if(Number(provinceCount)<10){
											label = new BMap.Label(provinceCount, {
												offset : new BMap.Size(27, 24)
											});
										}else if(Number(provinceCount)>9 && Number(provinceCount)<100){
											 label = new BMap.Label(provinceCount, {
												offset : new BMap.Size(24, 24)
											});
										}else if(Number(provinceCount)>99 && Number(provinceCount)<1000){
											 label = new BMap.Label(provinceCount, {
												offset : new BMap.Size(22, 24)
											});
										} 
										 label.setStyle({
											
											"border" : "0",
											"backgroundColor" : "transparent",
											"textAlign":"center",  
										    "font-size" : "12px",
											"font-weight" : "bold"
										}); 
										marker.setLabel(label);
									
										addProvinceClickHandler(title, content,
												marker,pt);
								}
							   }
							}
							
							if (mapzoomlevel > 5 && mapzoomlevel < 10) {//6-8市级 
							  if(list!=null){
								for (var i = 0; i < list.length; i++) {
									var clst = list[i].clst;
										for (var c = 0; c < clst.length; c++) {
										
											var lng = clst[c].clong;
											var lat = clst[c].clat;
											var cityCount = clst[c].count;
											var pt = new BMap.Point(lng,lat);
											var myIcon = new BMap.Icon(
													"images/city02.png", new BMap.Size(
															50, 45), {
														anchor : new BMap.Size(0, 30)
													});
											var marker = new BMap.Marker(pt, {
												icon : myIcon
											}); // 创建标注
											map.addOverlay(marker);
											var content = "<span style='font-size:12px'><b>车辆总数</b>："
												+ cityCount
												+ "<br/>"
												+ "</span><span style='float: right;font-size:12px'><a href='javascript:UpdateZoom(10)'>展开</a></span>";
											var title = clst[c].city;
											var label=null;
											if(Number(cityCount)<10){
												label = new BMap.Label(cityCount, {
													offset : new BMap.Size(26, 24)
												});
											}else if(Number(cityCount)>9 && Number(cityCount)<100){
												 label = new BMap.Label(cityCount, {
													offset : new BMap.Size(24, 24)
												});
											}else if(Number(cityCount)>99 && Number(cityCount)<1000){
												 label = new BMap.Label(cityCount, {
													offset : new BMap.Size(22, 24)
												});
											} 
											label.setStyle({
												"border" : "0",
												"background" : "transparent",
												"textAlign":"center",  
												"font-size" : "12px",
												"font-weight" : "bold"
											});
											marker.setLabel(label);
											addCityClickHandler(title, content, marker,pt);
										
										}
									}
							      }
								}
							
							if (mapzoomlevel > 9) {//12-n车辆坐标
								var gcProvince = null;
								var gcCity = null;
								var gc = new BMap.Geocoder(); 
								var center=map.getBounds().getCenter();
								 gc.getLocation(center, function(rs){  
									 var addComp = rs.addressComponents; 
									 gcProvince = addComp.province;
									 gcCity = addComp.city;
							         param="1=1&typeId="+carType+"&vendorId="+factory+"&province="+gcProvince+"&city="+gcCity+"&district="+district
											+"&carrieroperator="+operatorName+"&customerId="+customerName+"&state="+carStatus;
									
								 $.ajax({
									  type : "POST",
										url : "mainPageController.do?queryVechicleGps&param="+param,
										dataType : "text",
										success : function(data) {
											 var d = $.parseJSON(data);
									         var varray =d.attributes.list;
											 if(varray!=null){
												for (var v = 0; v < varray.length; v++) {
													var vlst=varray[v];
													    loadCar(vlst);
												}
											  }
										}
						          });
							 }); 
							}
						
							
					
						}
					});
		
		}
		
		function onLoadCar(){
			 var carType = $("#carType").val();
			var factory =  $("#factory").val();
			var province = $("#province").val();
			var city = $("#city").val();
			var district = $("#district").val();
			var operatorName = $("#operatorName").val();
			var customerName = $("#customerName").val();
			var carStatus = $("#carStatus").val();
			var carNumber=$("#carNumber").val();
			var vin="";
			var number="";
			var terminatorNo="";
			if(isCarNumber(carNumber.trim())){
				number=carNumber;
				// alert("number="+number); 
			}else if(isVin(carNumber.trim())){
				vin=carNumber;
				// alert("vin="+vin); 
			}else{
				terminatorNo=carNumber;
				// alert("terminatorNo="+terminatorNo); 
			}
		/* 	for (var i = 0, keys = mo.keySet(), len = keys.length; i < len; i++) {
				mo.put(keys[i],"0");

			} */
			countReg=0;
			online=0;
			mileage=0;
			var mileag=0;
			var totalReg=0;
			var totalLine=0;
			map.clearOverlays(); 
			 var param="1=1&typeId="+carType+"&vendorId="+factory+"&province="+province+"&city="+city+"&district="+district
				+"&carrieroperator="+operatorName+"&customerId="+customerName+"&state="+carStatus+"&carNumber="+number.trim()
				+"&vin="+vin.trim()+"&terminatorNo="+terminatorNo.trim();
			 $.ajax({
		  
				 type : "POST",
			     url : "mainPageController.do?searchCar&param="+param,
			     dataType : "text",
			     success : function(data) {
				 	var d = $.parseJSON(data);
				 	 var dataTmp='';
					 var temp='';
				 	if(d.success){
				 		
			         	var varray =d.attributes.rspclst;
			         	if(varray!=null){
			         		for (var v = 0; v < varray.length; v++) {
			         			var vlst=varray[v];
			         		    loadCar(vlst);
							    totalReg++;
							    mileag= vlst.accumuMileage;
							    if(Number(vlst.name)!=0){
								  	mo.put(vlst.name, 1);
							    }
								if (vlst.state == 1) {
									totalLine++;
								}
						     }
			            }
			    		
						
						 for (var i = 0, keys = mo.keySet(), len = keys.length; i < len; i++) {
							 if(Number(mo.get(keys[i]))!=0){
								 dataTmp += "['" +keys[i] + "'," + mo.get(keys[i]) + "]" + ",";
							 }
						 }
				 	} 
						 temp = "['注册数'," + totalReg + "],['在线数'," + online + "]";
						 topright(temp); 
						
						 $("#font_t").html((mileag / 10000).toFixed(2)); //总里程
						 $("#font_f").html((mileag/100*15).toFixed(1));//总节油量
						 $("#font_h").html(((mileag/100*15) / 300.28).toFixed(1));//总碳减排量
						 $("#font_g").html((mileag / 10000 * 2000).toFixed(1)); //总耗电量
						 topmiddle(dataTmp); 
				}
	 }); 
			
		}
		
		function loadCar(vlst){
			if (vlst.lon != null && vlst.lat != null
					&& vlst.lon != 0 && vlst.lat != 0) {
			var vlng = vlst.lon;
			var vlat = vlst.lat;
			var icontype = "green";
			var speed = vlst.speed;
			var state = "<span class=\"run\">在线</span>";
			if (vlst.state == 2) {
					icontype = "grey";
					state = "<span class=\"off\">离线</span>";
					speed="--";
			}
			if (vlst.state == 3) {
				icontype = "redgreen";
				state = "<span class=\"run\">故障</span>";
			}
			if (vlst.state == 4) {
				icontype = "redgrey";
				state = "<span class=\"off\">故障</span>";
			}
			derc = vlst.derc;
			var imgname = null;
			if(derc>"337.5" && derc<="359"){
				dercs="正北";
				imgname = "0";
			}else if(derc>="0"&&derc<="22.5"){
				dercs="正北";
				imgname = "0";
			}else if(derc>"67.5"&&derc<="112.5"){
				dercs="正东";
				imgname = "2";
			}else if(derc>"157.5"&&derc<="202.5"){
				dercs="正南";
				imgname = "4";
			}else if(derc>"247.5"&&derc<="292.5"){
				dercs="正西";
				imgname = "6";
			}else if(derc>"22.5"&&derc<="67.5"){
				dercs="东北";
				imgname = "1";
			}else if(derc>"112.5"&&derc<="157.5"){
				dercs="东南";
				imgname = "3";
			}else if(derc>"202.5"&&derc<="247.5"){
				dercs="西南";
				imgname = "5";
			}else if(derc>"292.5"&&derc<="337.5"){
				dercs="西北";
				imgname = "7";
			}
			
			var content ="<div style='line-height:1.8em;font-size:12px;'>"
					+"<b>车牌号:</b>"+ vlst.carNumber+"</br>"
								+"<b>采集时间:</b>"+ vlst.collectTime+"</br>"
								+"<b>运行状态:</b>"
								+ state
								+ "<br>"
								+ "<b>速度(km/h):</b>"
								+ speed
								+ "<br/><b>海拔(m):</b>"
								+ vlst.height
								+ "<br/><b>方向(°):</b>"
								+ dercs
								+ "<br/><b>地址:</b>"
								+ vlst.addr
								+"<a style='text-decoration:none;color:#2679BA;float:right' href=\"javascript:define("
								+ "'"+vlst.vin+"','"+vlst.collectTime
								+ "')\">车辆信息>></a></div>";
		
			var pt = new BMap.Point(vlng,
					vlat); 
		var myIcon = new BMap.Icon(
				"images/mach_marker_" + icontype +"_"+[parseInt((vlst.derc+1)/45)]
						+ ".png", new BMap.Size(40, 40));
			var marker = new BMap.Marker(pt, {
				icon : myIcon
			}); // 创建标注 
		    map.addOverlay(marker); 
			var title = vlst.carNumber; 
			var vid = vlst.vin; 
		 	addClickHandler(vid, title, content, marker); 
			if (vid == oldvid) {
				openInfoOld(vid, title, content,vlng,vlat);
			} 
			}
		}
		
		 function isCarNumber(str){
			 if(str.length!=0){    
			        var reg = /^[\u4e00-\u9fa5|WJ]{1}[A-Z0-9]{6}$/;     
			        var r = str.match(reg); 
			        if(r==null) {
			        	 return false;
			        }else{
			        	return true;
			        }
			        return true;
			    }    
		} 
		 function isVin(str){
			 if(str.length!=0){    
			        var reg = /^[A-Z0-9]{17}$/;     
			        var r = str.match(reg); 
			        if(r==null) {
			        	 return false;
			        }else{
			        	return true;
			        }
			        return true;
			    }    
		} 
	    function openInfo(content, e,opts) {
	        var p = e.target;
	        var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
	        var infoWindow = new BMap.InfoWindow(content, opts);  // 创建信息窗口对象 
	        map.openInfoWindow(infoWindow, point); //开启信息窗口
	    }
		
		function query(jsonArray, obj){
			var jarray = [];
			out:for (var i = 0; i < jsonArray.length; i++){
				for (var key in obj){
					if (!obj[key]){
						continue;
					}
					if (!jsonArray[i][key] || jsonArray[i][key] != obj[key]){
						continue out;
					}
				}
				jarray.push(jsonArray[i]);
			}
			return jarray;
		}
//告警列表
		function  warningList(clst){
			for(var c=0;c< clst.length;c++){
				var point = new Array();
				var iconlevel=null;
			    var color=null;
				var level=clst[c].warningLevel;
				if(level==1){
					iconlevel="red";
					var html='<audio autoplay width="0" heigth="0" id="audio_player">';
					html+='<source src="<%=request.getContextPath()%>/ee.wav" >'; 
					html+='</audio>';
					$("#html").html(html);
					color="#FF8E8E"; 
				}
				else if(level==2){
					iconlevel="yellow";
					color="#FFFA7C ";
				}else if(level==3){
					iconlevel="orange";
					color="#FFDB8E";
				}else if(level==4){
					iconlevel="blue";
					color="#C1FAE3";
				}else if(level==5){
					iconlevel="brown";
					color="#D1B498";
				} 
			
		    	var v1 = (clst[c].warningCode == ''
					|| clst[c].warningCode == undefined || clst[c].warningCode == null);
			    var v2 = (clst[c].warningLevel == ''
					|| clst[c].warningLevel == undefined || clst[c].warningLevel == null);
			    if (v1 || v2) {
				   continue;
			     } else {
					var warningCode = convertWarningCode(clst[c].warningCode);
					var warningLevel = convertWarningLevel(clst[c].warningLevel);
					point.push(clst[c].vin);
					point.push(change2Date(clst[c].collectionDate));
					point.push(warningCode);
					point.push(warningLevel);
					point.push(clst[c].carNumber);
					point.push(0);
					point.push(clst[c].wid);
					point.push(iconlevel); 
					point.push(color); 
				    point.push(clst[c].warningState);
				    point.push(clst[c].collection);

					warnrArr.push(point);
			}
		 } 
			return warnrArr;
		}

		function change2Date(str) {
			var t=str.split(/[- :]/);
			return new Date(t[0], t[1]-1, t[2], t[3], t[4], t[5]);

		}

		var formatDateTime = function(date) {
			var y = date.getFullYear();
			var m = date.getMonth() + 1;
			m = m < 10 ? ('0' + m) : m;
			var d = date.getDate();
			d = d < 10 ? ('0' + d) : d;
			var h = date.getHours();
			var minute = date.getMinutes();
			minute = minute < 10 ? ('0' + minute) : minute;
			var s = date.getSeconds();
			s = s < 10 ? ('0' + s) : s;
			return y + '-' + m + '-' + d + ' ' + h + ':' + minute + ':' + s;
		};

		
//告警列表排序
		function JsonSortWarning(markerArr) {
			var warningHtml = "";
			if(markerArr.length>0){
				markerArr.sort(function(a, b) {
					return b[1] - a[1];
	
				});
			
				warningHtml += "<table  border='0' width='100%'  style='background-color: #b9d8f3;'>";
					
				for (var i = 0; i <markerArr.length; i++) {
					warningHtml += "<tr id='tr"+i+"' style='background-color:"+markerArr[i][8]+"'>";
					if(a == '2'){
						warningHtml += "<td width='30%'height='25'><div align='center'><span>"
							        + "<a href=\"javascript:define('"+markerArr[i][0]+"','"+formatDateTime(markerArr[i][1])+"','0')\" >"
						         	+ markerArr[i][4] + "</a>";
							if(markerArr[i][9]==2){
								warningHtml +=	"<img src='images/"+markerArr[i][7]+"_"+markerArr[i][9]+".gif ' width='25%' height='20' />";
								
							}else{
								warningHtml +="<img src='images/"+markerArr[i][7]+".gif ' width='25%' height='20' />";
							}
							warningHtml +="</span></div></td>";
					}else{
						
						warningHtml += "<td width='30%'height='25'><div align='center'><span>"
							+ "<a href=\"javascript:define('"+markerArr[i][0]+"','"+formatDateTime(markerArr[i][1])+"','0')\" >"
							+ markerArr[i][4] + "</a>";
							if(markerArr[i][9]==2){
							
								warningHtml +=	"<img src='images/"+markerArr[i][7]+"_"+markerArr[i][9]+".gif ' width='25%' height='20' />";
								
							}else{
								warningHtml +="<img src='images/"+markerArr[i][7]+".gif ' width='25%' height='20' />";
							}
							warningHtml +="</span></div></td>";
					}
	
					warningHtml += "<td width='30%'height='25'><div align='center'><span>"
							+ formatDateTime(markerArr[i][1])
							+ "</span></div></td>";
					
					warningHtml += "<td width='20%'height='25'><div align='center'><span>"
							+markerArr[i][3]
						    +"</span></div></td>";
	           
					warningHtml += "<td width='20%'height='25'><div align='center'><span><a href=\"javascript:igg('"+markerArr[i][6]+"','"+markerArr[i][0]+"','"+markerArr[i][10]+"')\" >详情</a></span></div></td></tr>";
							continue;
				 }
				    warningHtml += "</table>";
			}else{
				warningHtml += "<tr >";
				warningHtml += "<td width='10%'height='25' ><div align='center' ><span style='font-size:120%;font-weight: bold;';>无数据</span></div></td></tr>";
				   warningHtml += "</table>";
			}
			$("#affiche").html(null);
			$("#affiche").html(warningHtml);
		}
		
		function define(vin, startTime, content) {
			 var ah = screen.availHeight-60;  
		     var aw = screen.availWidth-10;  
			window.open ('carConditionController.do?carWorkInfo&vin='+vin+"&reftime="+startTime+"&mainpage=mainpage", "newwindow", "height="+ah+", width="+aw+", top=0,left=0 toolbar =no, menubar=no, scrollbars=no, resizable=no, location=no, status=no"); 
		}
		
		 var fulls = "left=0,screenX=0,top=0,screenY=0,scrollbars=1";    //定义弹出窗口的参数  
		 if (window.screen) {  
		     var ah = screen.availHeight-60;  
		     var aw = screen.availWidth-10;  
		     fulls += ",height=" + ah;
		     fulls += ",innerHeight=" + ah;  
		     fulls += ",width=" + aw;
		     fulls += ",innerWidth=" + aw;
		     fulls += ",resizable"; 
		 } else {  
		     fulls += ",resizable"; // 对于不支持screen属性的浏览器，可以手工进行最大化。 manually  
		 }  
		 function openNewWindow(url,name){  
		 	window.open(url,name,fulls);  
		 }
		 
		function igg(id,vin,collectionTime){
			var iHeight = 500;
			var iWidth = 900;
			var iTop = (window.screen.availHeight - 30 - iHeight) / 2; //获得窗口的垂直位置;
			var iLeft = (window.screen.availWidth - 10 - iWidth) / 2; //获得窗口的水平位置;
			window.open("warnActualController.do?carWorkInfo&vin="+vin+"&id="+id+"&index=1"+"&collection="+collectionTime,
							"告警详情", "height=" + iHeight + ", width=" + iWidth
							+ ", top=" + iTop + ", left=" + iLeft
							+ ", toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no");
		}

		
		
		function convertWarningCode(warningCode) {
			var val = "";
			if (warningCode == 1) {
				val = "电机控制器温度 ";
				return val;

			} else if (warningCode == 2) {
				val = "驱动电机温度";
				return val;
			} else if (warningCode == 3) {
				val = "电机驱动系统故障 ";
				return val;

			} else if (warningCode == 4) {
				val = "DCDC温度 ";
				return val;
			} else if (warningCode == 5) {
				val = "DCDC状态 ";
				return val;
			} else if (warningCode == 6) {
				val = "电池总电压 ";
				return val;
			} else if (warningCode == 7) {
				val = "电池单体最高温度";
				return val;
			} else if (warningCode == 8) {
				val = "电池单体最低温度 ";
				return val;
			} else if (warningCode == 9) {
				val = "电池单体最高电压";
				return val;
			} else if (warningCode == 10) {
				val = "电池单体最低电压";
				return val;
			} else if (warningCode == 11) {
				val = "高压互锁状态";
				return val;
			} else if (warningCode == 12) {
				val = "绝缘电阻值";
				return val;
			} else if (warningCode == 13) {
				val = "碰撞信号状态 ";
				return val;
			} else if (warningCode == 14) {
				val = "储能系统故障指示";
				return val;
			} else if (warningCode == 15) {
				val = "制动系统故障 ";
				return val;
			}

		}

		function convertWarningValue(warningValue) {
			if (warningValue == 0) {
				return "无报警";
			} else if (warningValue == 1) {
				return "有报警";
			}

		}

		function convertWarningLevel(warningLevel) {
			if (warningLevel == 1) {
				return "一级";
			} else if (warningLevel == 2) {
				return "二级";
			} else if (warningLevel == 3) {
				return "三级";
			} else if (warningLevel == 4) {
				return "四级";
			} else if (warningLevel == 5) {
				return "五级";
			}

		}
		
	
		
		//过滤条件查询
		function mquery(){
			var carType = $("#carType").val();
			var factory =  $("#factory").val();
			var province = $("#province").val();
			var city = $("#city").val();
			var district = $("#district").val();
			var operatorName = $("#operatorName").val();
			var customerName = $("#customerName").val();
			var carStatus = $("#carStatus").val();
			var param="1=1&carType="+carType+"&factory="+factory+"&province="+province+"&city="+city+"&district="+district
					+"&operatorName="+operatorName+"&customerName="+customerName+"&carStatus="+carStatus;
			createwindow2('<t:mutiLang langKey="过滤条件查询"/>','mainPageController.do?query&param='+param);
		}
		
		//设置刷新频率
		function mre(){
			
			createwindow('<t:mutiLang langKey="设置刷新频率"/>','mainPageController.do?refresh');
		}
		
		function createwindow2(title, addurl,width,height) {
			
			width = width?width:500;
			height = height?height:220;
			if(width=="100%" || height=="100%"){
				width = window.top.document.body.offsetWidth;
				height =window.top.document.body.offsetHeight-100;
			}
			if(typeof(windowapi) == 'undefined'){
				$.dialog({
					content: 'url:'+addurl,
					lock : true,
					width:width,
					height:height,
					title:title,
					opacity : 0.3,
					cache:false,
				    ok: function(){
				    	iframe = this.iframe.window || this.iframe.contentWindow;
				    	var carType= iframe.document.getElementById("carType");
				    	var factory= iframe.document.getElementById("factory");
				    	var province= iframe.document.getElementById("Select1");
				    	var city= iframe.document.getElementById("Select2");
				    	var district= iframe.document.getElementById("Select3");
				    	var customerName= iframe.document.getElementById("customerName");
				    	var operatorName= iframe.document.getElementById("operatorName");
				    	var carStatus= iframe.document.getElementById("carStatus");
						var v1=($(province).find("option:selected").val());
						var v2=($(city).find("option:selected").val());
						var v3=($(district).find("option:selected").val());
						if(v1 == '|'){
							v1=null;
						}
						if(v2 == '|'){
							v2=null;
						}
						if(v3 == '|'){
							v3=null;
						}
					
						$("#province").val(v1);
				    	$("#city").val(v2);
				    	$("#district").val(v3);
				    	$("#carType").val($(carType).find("option:selected").val());
				    	$("#carStatus").val($(carStatus).find("option:selected").val());
				    	$("#factory").val($(factory).find("option:selected").val());
				    	$("#customerName").val($(customerName).find("option:selected").val());
				    	$("#operatorName").val($(operatorName).find("option:selected").val());
				    	SelectGps();
				    	saveObj1();
						return false;
				    },
				    cancelVal: '关闭',
				    cancel: true
				}).zindex();
			}else{
				W.$.dialog({
					content: 'url:'+addurl,
					lock : true,
					width:width,
					height:height,
					parent:windowapi,
					title:title,
					opacity : 0.3,
					cache:false,
				    ok: function(){
				    	iframe = this.iframe.window || this.iframe.contentWindow;
				    	var carType= iframe.document.getElementById("carType");
				    	var factory= iframe.document.getElementById("factory");
				    	var province= iframe.document.getElementById("Select1");
				    	var city= iframe.document.getElementById("Select2");
				    	var district= iframe.document.getElementById("Select3");
				    	
				    	var customerName= iframe.document.getElementById("customerName");
				    	var operatorName= iframe.document.getElementById("operatorName");
				    	var carStatus= iframe.document.getElementById("carStatus");
						$("#province").val(($(province).find("option:selected").val()));
				    	$("#city").val(($(city).find("option:selected").val()));
				    	$("#district").val(($(district).find("option:selected").val()));
				    	$("#carType").val($(carType).find("option:selected").val());
				    	$("#factory").val($(factory).find("option:selected").val());
				    	$("#customerName").val($(customerName).find("option:selected").val());
				    	$("#operatorName").val($(operatorName).find("option:selected").val());
				     	$("#carStatus").val($(carStatus).find("option:selected").val());
				    	saveObj1();
						return false;
				    },
				    cancelVal: '关闭',
				    cancel: true
				}).zindex();
			}
		}
		
		function createwindow(title, addurl,width,height) {
			width = width?width:600;
			height = height?height:150;
			if(width=="50%" || height=="50%"){
				width = window.top.document.body.offsetWidth;
				height =window.top.document.body.offsetHeight-100;
			}
			if(typeof(windowapi) == 'undefined'){
				$.dialog({
					content: 'url:'+addurl,
					lock : true,
					width:width,
					height:height,
					title:title,
					opacity : 0.3,
					cache:false,
				    ok: function(){
				    	iframe = this.iframe.window || this.iframe.contentWindow;
				    	var f = iframe.document.getElementById("seconds");
				    	$("#mtime").val(Number($(f).val())*1000);
				    	rere($(f).val()*1000);
				    	saveObj1();
						return false;
				    },
				    cancelVal: '关闭',
				    cancel: true
				}).zindex();
			}else{
				W.$.dialog({
					content: 'url:'+addurl,
					lock : true,
					width:width,
					height:height,
					parent:windowapi,
					title:title,
					opacity : 0.3,
					cache:false,
				    ok: function(){
				    	iframe = this.iframe.window || this.iframe.contentWindow;
				    	var f = iframe.document.getElementById("seconds");
				    	$("#mtime").val(Number($(f).val())*1000);
				    	rere($(f).val()*1000);
				    	saveObj1();
						return false;
				    },
				    cancelVal: '关闭',
				    cancel: true
				}).zindex();
			}
		}
		function saveObj1() {
			$('#btn_sub', iframe.document).click();
		}
		
		function findTrackHistory(vin,id) {
			addOneTab("历史轨迹", "carTrackHistoryController.do?findTrackHistory&vin="
					+ vin+ "&id=" + id);
		}
		
		function carMainPage(vin, id) {
			addOneTab("定位", "carOneMonitorController.do?carMainPage&vin=" + vin
					+ "&id=" + id);

		}
		
		function fullScreen(){
			var carType = $("#carType").val();
			var factory =  $("#factory").val();
			var province = $("#province").val();
			var city = $("#city").val();
			var district = $("#district").val();
			var operatorName = $("#operatorName").val();
			var customerName = $("#customerName").val();
			var carStatus = $("#carStatus").val();
			var carNumber=$("#carNumber").val();
			var vin="";
			var number="";
			var terminatorNo="";
			if(isCarNumber(carNumber.trim())){
				number=carNumber;
				// alert("number="+number); 
			}else if(isVin(carNumber.trim())){
				vin=carNumber;
				// alert("vin="+vin); 
			}else{
				terminatorNo=carNumber;
				// alert("terminatorNo="+terminatorNo); 
			}
			var param="id=2&carType="+carType+"&factory="+factory+"&province="+province+"&city="+city+"&district="+district
					+"&operatorName="+operatorName+"&customerName="+customerName+"&carStatus="+carStatus+"&carNumber="+number.trim()
					+"&vin="+vin.trim()+"&terminatorNo="+terminatorNo.trim();
			openNewWindow('mainPageController.do?mainPage&param='+param, 'big', 'fullscreen=yes,scrollbars=yes')
		}
		function mainPageFull(){
			var carType = $("#carType").val();
			var factory =  $("#factory").val();
			var province = $("#province").val();
			var city = $("#city").val();
			var district = $("#district").val();
			var operatorName = $("#operatorName").val();
			var customerName = $("#customerName").val();
			var carStatus = $("#carStatus").val();
			var carNumber=$("#carNumber").val();
			var vin="";
			var number="";
			var terminatorNo="";
			if(isCarNumber(carNumber.trim())){
				number=carNumber;
				// alert("number="+number); 
			}else if(isVin(carNumber.trim())){
				vin=carNumber;
				// alert("vin="+vin); 
			}else{
				terminatorNo=carNumber;
				// alert("terminatorNo="+terminatorNo); 
			}
			var param="1=1&carType="+carType+"&factory="+factory+"&province="+province+"&city="+city+"&district="+district
					+"&operatorName="+operatorName+"&customerName="+customerName+"&carStatus="+carStatus+"&carNumber="+number.trim()
					+"&vin="+vin.trim()+"&terminatorNo="+terminatorNo.trim();
			openNewWindow('mainPageController.do?mainPageFull&param='+param, 'big', 'fullscreen=yes,scrollbars=yes')
		}
		function addClickHandler(vid, title, content, marker) {
			marker.addEventListener("onmouseover", function(e) {
				openInfo(vid, title, content, e);
				/* SelectGps(); */
			});
		}
		
		function addProvinceClickHandler(title, content, marker,point) {
			/* marker.addEventListener("click", function(e) {
				
				openProvinceInfo(title, content, e);
				SelectGps();
			}); */
			
			  var strictBounds = new BMap.Bounds(new BMap.Point(point.lng-0.1, point.lat-0.1),new BMap.Point(point.lng+0.1, point.lat+0.1));
				marker.addEventListener("click", function(e) {
					 var c=map.getCenter();
			          x=c.lng,
			          y=c.lat,
			          maxX=strictBounds.getNorthEast().lng,
			          maxY=strictBounds.getNorthEast().lat,
			          minX=strictBounds.getSouthWest().lng,
			          minY=strictBounds.getSouthWest().lat;
			         if (x < minX) x = minX;
			         if (x > maxX) x = maxX;
			         if (y < minY) y = minY;
			         if (y > maxY) y = maxY;
			         map.centerAndZoom(new BMap.Point(x,y),8);});
		}
		
		
		function addCityClickHandler(title, content, marker,point) {
		/* 	marker.addEventListener("click", function(e) {
				openCityInfo(title, content, e);
				SelectGps();
			}); */
			
			  var strictBounds = new BMap.Bounds(new BMap.Point(point.lng-0.1, point.lat-0.1),new BMap.Point(point.lng+0.1, point.lat+0.1));
			marker.addEventListener("click", function(e) {
				
				 var c=map.getCenter();
		          x=c.lng,
		          y=c.lat,
		          maxX=strictBounds.getNorthEast().lng,
		          maxY=strictBounds.getNorthEast().lat,
		          minX=strictBounds.getSouthWest().lng,
		          minY=strictBounds.getSouthWest().lat;
		         if (x < minX) x = minX;
		         if (x > maxX) x = maxX;
		         if (y < minY) y = minY;
		         if (y > maxY) y = maxY;
		         map.centerAndZoom(new BMap.Point(x,y),10);
				/* UpZoom(8,point);
 */			});
		}
		
		
	
		function openCityInfo(title, content, e) {
			oldcity = title;
			var p = e.target;
			var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
			var opts = {
				width : 90, // 信息窗口宽度
				height : 0, // 信息窗口高度
				title : "<b>" + title + "</b>", // 信息窗口标题
				enableMessage : false,//设置允许信息窗发送短息
				offset : new BMap.Size(34, -20)
			};
			var infoWindow = new BMap.InfoWindow(content, opts); // 创建信息窗口对象 
			map.openInfoWindow(infoWindow, point); //开启信息窗口
		}
		function openProvinceInfo(title, content, e) {
			oldprovince = title;
		 
			var p = e.target;
			var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
			
			var opts = {
				width : 90, // 信息窗口宽度
				height : 0, // 信息窗口高度
				title : "<b>" + title + "</b>", // 信息窗口标题
				enableMessage : false,//设置允许信息窗发送短息
				offset : new BMap.Size(34, -20)
			};
			var infoWindow = new BMap.InfoWindow(content, opts); // 创建信息窗口对象 
			map.openInfoWindow(infoWindow, point); //开启信息窗口
		}
		function openInfo(vid, title, content, e) {
			oldvid = vid;
			var p = e.target;
			var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
			var opts = {
				width : 250, // 信息窗口宽度
				height : 0, // 信息窗口高度
				title : "<b>" + title + "</b>", // 信息窗口标题
				enableMessage : false
			//设置允许信息窗发送短息
			};
			var infoWindow = new BMap.InfoWindow(content, opts); // 创建信息窗口对象 
			map.openInfoWindow(infoWindow, point); //开启信息窗口
		}
		function openInfoOld(vid, title, content, lng, lat) {
			oldvid = vid;
			var point = new BMap.Point(lng, lat);
			var opts = {
				width : 250, // 信息窗口宽度
				height : 0, // 信息窗口高度
				title : "<b>" + title + "</b>", // 信息窗口标题
				enableMessage : false
			//设置允许信息窗发送短息
			};
			var infoWindow = new BMap.InfoWindow(content, opts); // 创建信息窗口对象 
			map.openInfoWindow(infoWindow, point); //开启信息窗口
		}
		function openInfoProvinceOld(title, content, lng, lat) {
			oldprovince = title;
			var point = new BMap.Point(lng, lat);
			var opts = {
				width : 90, // 信息窗口宽度
				height : 0, // 信息窗口高度
				title : "<b>" + title + "</b>", // 信息窗口标题
				enableMessage : false,//设置允许信息窗发送短息
				offset : new BMap.Size(34, -20)
			};
			var infoWindow = new BMap.InfoWindow(content, opts); // 创建信息窗口对象 
			map.openInfoWindow(infoWindow, point); //开启信息窗口
		}
		function openInfoCityOld(title, content, lng, lat) {
			oldcity = title;
			var point = new BMap.Point(lng, lat);
			var opts = {
				width : 90, // 信息窗口宽度
				height : 0, // 信息窗口高度
				title : "<b>" + title + "</b>", // 信息窗口标题
				enableMessage : false,//设置允许信息窗发送短息
				offset : new BMap.Size(34, -20)
			};
			var infoWindow = new BMap.InfoWindow(content, opts); // 创建信息窗口对象 
			map.openInfoWindow(infoWindow, point); //开启信息窗口
		}
		function openInfoDisOld(title, content, lng, lat) {
			oldDis = title;
			var point = new BMap.Point(lng, lat);
			var opts = {
				width : 90, // 信息窗口宽度
				height : 0, // 信息窗口高度
				title : "<b>" + title + "</b>", // 信息窗口标题
				enableMessage : false,//设置允许信息窗发送短息
				offset : new BMap.Size(34, -20)
			};
			var infoWindow = new BMap.InfoWindow(content, opts); // 创建信息窗口对象 
			map.openInfoWindow(infoWindow, point); //开启信息窗口
		}
		function UpdateZoom(level) {
			
			map.setZoom(level);
		}
		
	</script>
</html>

