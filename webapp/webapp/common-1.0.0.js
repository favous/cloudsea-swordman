

	
	var $$ = {
			loadGrid : 					loadConfigTable,
			createPopupDiv:				createPopupDiv,
			confirm : 					confirmDialog,
			warn : 						warnDialog,
			popupRowAsForm : 			popupRowAsForm,
			popupAsForm : 				popupAsForm,
			ajaxGetRequest:				ajaxGetRequest,
			ajaxPostRequest:			ajaxPostRequest,
			getTagElementsByClassName : getObjElementsByClassNameTagName,
			getElementsByClassName : 	getObjElementsByClassName,
			createSelectOption : 		createSelectOption,
			createSelectOp : 			createSelectOp,
			formateWithJson : 			formateWithJson,
			getValueByFeildName : 		getValueByNameFromObj,
			getPathRelativeJsFile:	getPathRelativeJsFile,
			isIE :						isIEExplorer,
			diffTime : 					diffTime
	};
	
	/**
	 * typeof 可以用来检测给定变量的数据类型，可能的返回值：
	1. 'undefined' --- 这个值未定义；
	2. 'boolean'    --- 这个值是布尔值；
	3. 'string'        --- 这个值是字符串；
	4. 'number'     --- 这个值是数值；
	5. 'object'       --- 这个值是对象或null；
	6. 'function'    --- 这个值是函数。
	 * @param arg
	 * @returns
	 */
	function $$$(arg){
		if (typeof arg == 'string'){
			if (arg.startWith('#')){
				var id = arg.substring(1, arg.length);
				return [document.getElementById(id)]; 
			}
			
			else if (arg.startWith('.')){
				var className = arg.substring(1, arg.length);
				return $$.getElementsByClassName(document, className);
				
			} else{
				return document.getElementsByTagName("arg");
			}
			
		}
		else if (typeof arg == 'object'){
			
		}
		
	}
	
	function loadConfigTable(config){
		var loadTable = new LoadTable(config);
		loadTable.loadTable();
	}
	
	function ajaxGetRequest(url, sync, parmaEntrys){
		var ajaxReq = new AjaxGetRequest();
		if (parmaEntrys && parmaEntrys != '')
			url = url + '?' + parmaEntrys;
		ajaxReq.ajaxRequest(url, sync);
		if(ajaxReq.readyState == 4 && ajaxReq.status == 200)
			return ajaxReq.result;
		else
			throw new Error('请求异常，readyState：' + ajaxReq.status);
	}
	
	function ajaxPostRequest(url, sync, postBody) {
		var ajaxReq = new AjaxPostRequest();
		ajaxReq.ajaxRequest(url, sync, postBody);
		if(ajaxReq.readyState == 4 && ajaxReq.status == 200)
			return ajaxReq.result;
		else
			throw new Error('请求异常，readyState：' + ajaxReq.status);
	}
	
	function isIEExplorer(){
		if (navigator.appVersion.indexOf("MSIE") > -1)
			return true;
		else
			return false;
	}
		
	function getObjElementsByClassNameTagName(obj, className, tagName) {
		var ele = [];
		var all = obj.getElementsByTagName(tagName || "*");
		for ( var i = 0; i < all.length; i++) {
			if (all[i].className.match(new RegExp('(\\s|^)' + className
					+ '(\\s|$)'))) {
				ele[ele.length] = all[i];
			}
		}
		return ele;
	}
	
	function getObjElementsByClassName(obj, className) {
		var eleArray = [];
		var all = obj.getElementsByTagName("*");
		for ( var i = 0; i < all.length; i++) {
			if (all[i].className.match(new RegExp('(\\s|^)' + className
					+ '(\\s|$)'))) {
				eleArray[eleArray.length] = all[i];
			}
		}
		return eleArray;
	}
	
	/**
	async:是否异步请求
	**/	
	function AjaxGetRequest() {
		var obj = new Object;
		obj.result = null;
		obj.readyState = null;
		obj.status = null;
		obj.ajaxRequest = function (url, async) {
			var XMLHttpReq;
			try {
				XMLHttpReq = new ActiveXObject("Msxml2.XMLHTTP");//IE高版本创建XMLHTTP  
			} catch (E) {
				try {
					XMLHttpReq = new ActiveXObject("Microsoft.XMLHTTP");//IE低版本创建XMLHTTP  
				} catch (E) {
					XMLHttpReq = new XMLHttpRequest();//兼容非IE浏览器，直接创建XMLHTTP对象  
				}
			}
			
			XMLHttpReq.open("get", url, async);
			XMLHttpReq.onreadystatechange = function() {
				if (XMLHttpReq.readyState == 4) {
					if (XMLHttpReq.status == 200) {
						obj.result = XMLHttpReq.responseText;
					}
				}
			}; //指定响应函数  
			XMLHttpReq.send(null);
			obj.readyState = XMLHttpReq.readyState;
			obj.status = XMLHttpReq.status;
		};
		return obj;
	}
	
	function AjaxPostRequest() {
		var obj = new Object;
		obj.result = null;
		obj.readyState = null;
		obj.status = null;
		obj.ajaxRequest = function (url, async, postBody) {
			var XMLHttpReq;
			try {
				XMLHttpReq = new ActiveXObject("Msxml2.XMLHTTP");//IE高版本创建XMLHTTP  
			} catch (E) {
				try {
					XMLHttpReq = new ActiveXObject("Microsoft.XMLHTTP");//IE低版本创建XMLHTTP  
				} catch (E) {
					XMLHttpReq = new XMLHttpRequest();//兼容非IE浏览器，直接创建XMLHTTP对象  
				}
			}
			
			XMLHttpReq.open("post", url, async);
			XMLHttpReq.onreadystatechange = function() {
				if (XMLHttpReq.readyState == 4) {
					if (XMLHttpReq.status == 200) {
						obj.result = XMLHttpReq.responseText;
					}
				}
			}; //指定响应函数  
			XMLHttpReq.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');  
			XMLHttpReq.send(postBody);
			obj.readyState = XMLHttpReq.readyState;
			obj.status = XMLHttpReq.status;
		};
		return obj;
	}

	function createSelectOption(selectid, jsonData, valueField, textField, allSel) {   
	    var select = document.getElementById(selectid); 
	    createSelectOp(select, jsonData, valueField, textField, allSel);
	}

	/**
	以JSON数组为数据源，往selectid对应select标签加载选项
	select:	select标签对象，
	jsonData:	JSON数据源，
	valueField:	选项值对应属性名，
	textField:	select选项显示对应属性名
	allSel:		是否另外加载‘所有’的选项
	**/
	function createSelectOp(select, jsonData, valueField, textField, allSel) {   
	    if (allSel){
	        var opt = document.createElement("option");    
	        opt.value = "";    
	        opt.innerHTML = "所有"; 
	        opt.selected = 'true';
	        select.appendChild(opt);  
	    }
	    for(var i = 0; i < jsonData.length; i++) {    
	        var opt = document.createElement("option"); 
	        for (var key in jsonData[i]) {
				if (key == valueField){
					opt.value = jsonData[i][key];
				}
				if (key == textField){
					opt.innerHTML = jsonData[i][key];
				}
			}
	        opt.value = jsonData[i].value;    
	        opt.innerHTML = jsonData[i].name;    
	        if(jsonData[i].selected == 'true')   {    
	            opt.selected = 'true';    
	        }     
	        select.appendChild(opt);  
	   }  
	}

	/**
	遍历JSON数组
	返回新属性名对应的值
	jsonData:JSON数据源，oldval:原来的值，oldField:旧属性名，newoldField:新属性名
	**/
	function formateWithJson(jsonData, oldval, oldField, newField){
		for (var i = 0; i < jsonData.length; i++){
			var recycle = false;
			for (var key in jsonData[i]) {
				if (key == oldField && jsonData[i][key] == oldval){
					recycle = true;
					break;
				}
			}
			if (recycle == true){
				for (var key in jsonData[i]) {
					if (key == newField){
						return jsonData[i][key];
					}
				}
			}
		}
	}
	
	function findParentElementByName(node, nodeName){
		if (node.nodeName == nodeName)
			return node;
		else
			return findParentElementByName(node.parentElement, nodeName);
	}

	function warnDialog(msg){
		msgDialog(msg, [['确定', null]]);
	}

	function confirmDialog(msg, fn){
		msgDialog(msg, [['是', fn], ['否', null]]);
	}
	
	/**
	 * 
	 * @param msg		提示信息
	 * @param buttonCfg	按钮配置数组，元素是子数组，子数组0元素是按钮名，子数组是响应方法
	 */
	function msgDialog(msg, buttonCfg){
		config = {lockScreen:false, deleteButton:true, title:'提示框', width:'365px', height:'183px'};
		var popupDiv = createPopupDiv(config);
		var contentDiv = popupDiv.contentDiv;
		
		var contentTable = document.createElement('table');
		contentDiv.appendChild(contentTable);
		contentTable.style.width = popupDiv.style.width;
		contentTable.style.height = '100px';
		contentTable.style.border = "0px solid #768";
		var contentTR = contentTable.insertRow(-1);
		
		var iconTd = contentTR.insertCell(0);
		iconTd.style.width = '80px';
		iconTd.setAttribute("align", "center");
		
		var iconImg = document.createElement('img');
		iconImg.src = getPathRelativeJsFile("common-1.0.0.js", "images/alert.png");
		iconImg.style.width = '50px';
		iconImg.style.height = '55px';
		iconTd.appendChild(iconImg);
		
		var msgTd = contentTR.insertCell(1);
		msgTd.style.paddingRight = '20px';
		msgTd.setAttribute("align", "center");
		
		var msgSpan = document.createElement('span');
		msgSpan.style.width = '200px';
		msgSpan.style.fontSize = '11pt';
		msgSpan.style.fontWeight = '800';
		msgSpan.innerHTML = msg;
		msgTd.appendChild(msgSpan);
				
		var buttonDiv = document.createElement('div');
		contentDiv.appendChild(buttonDiv);
		var buttonLength = 0;
		
		for (var i = 0; i < buttonCfg.length; i++){
			var buttonSpan = document.createElement('span');
			buttonSpan.style.border = "1px solid #768";	//点状dotted 实线solid 双线double 虚线dashed
			buttonSpan.style.paddingLeft = "10px"; 
			buttonSpan.style.paddingRight = "10px"; 
			buttonSpan.style.marginRight = "15px";
			var a_link = document.createElement('a');	
			a_link.innerHTML = buttonCfg[i][0];
			a_link.href = "#";
			a_link.style.textDecoration = 'none';// 无下划线
			a_link.style.fontWeight = 'bold';
			a_link.style.fontSize = '11pt';
			a_link.style.color = '#333';
			a_link['buttonCfg'] = buttonCfg;
			a_link['btmIndex'] = i;
			a_link['popupDiv'] = popupDiv;
			buttonSpan.appendChild(a_link);
			buttonDiv.appendChild(buttonSpan);
			
			a_link.onclick = function (event){
				this.popupDiv.remove();
				if (this.popupDiv.backDiv)
					this.popupDiv.backDiv.remove();
				if (this.buttonCfg && this.buttonCfg[this.btmIndex] && this.buttonCfg[this.btmIndex][1])
					this.buttonCfg[this.btmIndex][1]();
			};
			buttonLength = buttonLength + buttonSpan.offsetWidth + 15;
		}
		buttonDiv.style.paddingLeft = (popupDiv.offsetWidth - buttonLength)/2 + "px"; 
	}
	
	
	function popupAsForm(config){
		popupRowAsForm(null, config);
	}
	
	
	function createPopupDiv(config){
		var popupDiv = document.createElement('div');
		if (config.lockScreen && config.lockScreen == true){			
			var dw = document.documentElement.clientWidth;
			var dh = document.documentElement.clientHeight;
			
			var backDiv = document.createElement('div');
			backDiv.style.width = dw + 'px';
			backDiv.style.height = dh + 'px';
			backDiv.style.position = 'absolute';
			backDiv.style.left = 0;
			backDiv.style.top = 0;
			backDiv.style.zIndex = 99;
			backDiv.style.filter = 'alpha(opacity:10)';
			backDiv.style.opacity = '0.1';
			backDiv.style.backgroundColor = "#000";
			document.body.appendChild(backDiv);
			popupDiv['backDiv'] = backDiv;
		}
		
		document.body.appendChild(popupDiv);
		popupDiv.id = config.id ? config.id : "popupDiv_" + new Date().getTime();
		popupDiv.style.display = "block";
		popupDiv.style.position = 'absolute';
		popupDiv.style.width = config.width;
		popupDiv.style.height = config.height;
		popupDiv.style.backgroundColor = "#EDF";
		popupDiv.style.border = "1px solid #98A";	//点状dotted 实线solid 双线double 虚线dashed
		
		//弹出界面居中
    	popupDiv.marginCenter(document.body);
    	var cut = document.documentElement.clientHeight - popupDiv.offsetHeight;
    	popupDiv.style.top = cut/2 + "px";
    	
    	//显示标题
		var titleDiv = document.createElement('div');
		titleDiv.style.backgroundColor = "#AAC";
		titleDiv.style.width = popupDiv.style.width;
		titleDiv.style.height = "23px";
		titleDiv.style.borderBottom = "1px solid";	//点状dotted 实线solid 双线double 虚线dashed
		titleDiv.style.borderBottomColor = "#98A";
		
		titleDiv.innerHTML = '<table><tr><td></td><td></td></tr></table>';
		var tds = titleDiv.getElementsByTagName('td');
		tds[0].style.width = parseInt(titleDiv.style.width.replace("px", "")) - 30 + 'px';
		tds[1].style.width = '30px';
		
		var font = document.createElement('font');
		font.style.fontSize = '11pt';
		font.style.fontWeight = 'bold';
		font.style.color = '#234';
		font.innerHTML = config.title;
		tds[0].appendChild(font);
		popupDiv.appendChild(titleDiv);
		
		if (config.deleteButton && config.deleteButton == true){
	        var img = document.createElement('img');
	        img.src = getJsPathByName("common-1.0.0.js").replace("common-1.0.0.js", "images/delete.gif");
	        var a_del = document.createElement('a');	
	        a_del.href = "#";
	        a_del.style.textDecoration = 'none';// 无下划线					
	        a_del.style.color = "#999";
	        a_del.appendChild(img);
	        a_del['popupDiv'] = popupDiv;
	        titleDiv.getElementsByTagName('td')[1].appendChild(a_del);
	       
	        a_del.onclick = function (event){				
				this.popupDiv.remove();
				if (this.popupDiv.backDiv)
					this.popupDiv.backDiv.remove();
			};
		}

		//内容DIV
		var contentDiv = document.createElement('div');		
		contentDiv.style.overflow = 'auto';
		contentDiv.style.height = parseInt(config.height.replace("px", "")) - 30 + 'px';
		contentDiv.style.width = config.width;
		popupDiv.appendChild(contentDiv);
		contentDiv.marginCenter(popupDiv);
		popupDiv['contentDiv'] = contentDiv;
		
		if (config.content){
			contentDiv.innerHTML = config.content;
		}
		
		return popupDiv;
	}
	
	/**
	 * @param rowData	表格数据源的一条记录
	 * @param config	弹出表单的配置
	 * @returns {___formDiv24}	返回表格div对象，内有table属性
	 */
	function createFormByRowData(rowData, config){
		var formDiv = document.createElement('div');
		var table = document.createElement('table');
		var columnSize = !isExist(config.rowsize) ? 1 : config.rowsize;
		var count = 0;
		
		//隐藏表单
		var hidden_tr = table.insertRow(-1);
		var hidden_td = hidden_tr.insertCell(0);
		hidden_td.setAttribute("colspan", columnSize);
		
		outer:
		for (var i = 1 ; i > 0; i++){
			var tr = table.insertRow(-1);
			for (var j = 0; j < columnSize; j++){
				count++;
				if (count > config.cells.length)
					break outer;
				
				var cellCfg = config.cells[count-1];
				var cell = null;
				
				if (!cellCfg.display || cellCfg.display != 'none'){
					cell = tr.insertCell();
					cell.style.paddingLeft = "15px";
					cell.style.paddingRight = "15px";
				} else{
					cell = hidden_td;
					j--;
				}

				//加载标签
				var label = document.createElement('span');
				if (cellCfg.label)
					label.innerHTML = cellCfg.label;
				if (cellCfg.width)
					label.style.width = cellCfg.width;
				label.style.display = !cellCfg.display ? 'block' : cellCfg.display;
				cell.appendChild(label);
				
				//加载表单field
				var type = 'text';
				if (cellCfg.inputType)
					type = cellCfg.inputType;
				else if (config.defaultType)	//input配置默认类型
					type = config.defaultType;
				var field = null;
				if ('select' == type)
					field = document.createElement('select');
				else{
					field = document.createElement('input');
					field.type = type;
				}
				var val = !rowData ? '' : rowData[cellCfg.name];
				field.name = cellCfg.name;
				field.value = val ? val : '';
				if (cellCfg.width)
					field.style.width = cellCfg.width;
				field.disabled = cellCfg.editable ? false : true;
				field.style.display = !cellCfg.display ? 'block' : cellCfg.display;
				cell.appendChild(field);
				
				//调用配置的加载函数
				if (cellCfg.loadFn)
					cellCfg.loadFn.apply(null, [field]);
								
				//添加事件
				if (cellCfg.event && cellCfg.eventName){
					field['eventFn'] = cellCfg.event;
					field[cellCfg.eventName] = function (event){
						if (this.eventFn)
							this.eventFn.apply(null, [this]);
					};
				}
								
				//select选中   
				if ('select' == type){
					var optionArray = field.options;
					for (var k = 0; k < optionArray.length; k++){	
						if(val && val == optionArray[k].value){  
							optionArray[k].selected = true; 
							break;
			            }  
			        } 				
				}				
			}
		}
		
		formDiv.appendChild(table);	
		formDiv['table'] = table;
		return formDiv;
	}


	/**
	 * 
	 * @param row
	 * @param config
	 */
	function popupRowAsForm(row, config){
		config.deleteButton = true;
		var popupDiv = createPopupDiv(config); 				         
    	
		var form = document.createElement('form');
		form.action = config.form.url;
		form.method = config.form.method;
		popupDiv.contentDiv.appendChild(form);
		
		var contentDiv = document.createElement('div');
		form.appendChild(contentDiv);
		
		var formDiv = createFormByRowData(!row ? null : row.rowData, config);
		contentDiv.appendChild(formDiv);
		formDiv.table.marginCenter(popupDiv);
		formDiv.table.marginTop = "15px"; 
		
		//按钮DIV
		var buttondiv = document.createElement("div");
		buttondiv.style.marginTop = "25px"; 
		buttondiv.style.paddingLeft = "10px"; 
		contentDiv.appendChild(buttondiv);
		var buttonLength = 0;
		
		config["actionMap"] = [];

		//加载按钮
		for (var i = 0; i < config.buttons.length; i++){
			var link_span = document.createElement("span");
			link_span.style.border = "1px solid #98A";	//点状dotted 实线solid 双线double 虚线dashed
			link_span.style.paddingLeft = "5px"; 
			link_span.style.paddingRight = "5px"; 
			link_span.style.marginRight = "5px"; 
			
			var buttonCfg = config.buttons[i];
			var a_link = document.createElement('a');	
			a_link.href = "#";
			a_link.style.textDecoration = 'none';// 无下划线					
			a_link.style.color = "#000";
			
			var font = document.createElement('font');
			var fontId = isBlank(buttonCfg.id) ? popupDiv.id + "_button" + i : buttonCfg.id;
			font.id = fontId;
			font.style.fontSize = '15pt';
			font.innerHTML = buttonCfg.text;
			a_link.appendChild(font);	
			
			config.actionMap.push([fontId, buttonCfg.action]);
			a_link.onclick = function (event){
				event = event || window.event;
				var element = event.srcElement ? event.srcElement : event.target;				
				var id = element.id;
				for (var index in config.actionMap){
					if (config.actionMap[index][0] == id){
						var action = config.actionMap[index][1];
						action.apply(null, [popupDiv, contentDiv, config]);
						break;
					}
				}
			};
			
			link_span.appendChild(a_link);	
			buttondiv.appendChild(link_span);				
			buttonLength = buttonLength + link_span.offsetWidth + 10;
		}
		buttondiv.style.paddingLeft = (popupDiv.offsetWidth - buttonLength)/2 + "px";
		buttondiv.style.paddingRight = (popupDiv.offsetWidth - buttonLength)/2 + "px";
	}  
	
	/**
	 * 
	 * @param form	form标签对象
	 * @returns		键值对拼接的字符串
	 */
	function getFormParams(form) {  
		var elements = [];  
	    var paramEntryArray = [];  
	    
	    var tagElements = form.getElementsByTagName('input');  
	    var tagElements2 = form.getElementsByTagName('select');  
	    for (var j = 0; j < tagElements.length; j++)  
	        elements.push(tagElements[j]);  
	    for (var j = 0; j < tagElements2.length; j++)  
	        elements.push(tagElements2[j]);  
	    
	    for (var i = 0; i < elements.length; i++) {  
	        var paramEntry = getParamEntry(elements[i]);  
	        if (paramEntry && paramEntry[0] && paramEntry[1]) {  
	        	var key = encodeURIComponent(paramEntry[0]); 
	          	var val = encodeURIComponent(paramEntry[1]);
	          	paramEntryArray.push(key + '=' + val);  
	        } 
	    }
	    
	    return paramEntryArray.join('&');
	}

	/**
	 * 
	 * @param element	form表单元素对象
	 * @returns			键值对
	 */
	function getParamEntry(element) {  
		
		if (element.tagName.toLowerCase() == 'select')
			return [element.name, element.value];
		
	    switch (element.type.toLowerCase()) {  
	      	case 'submit':  
	    	    return false;  
	      	case 'hidden':  
	      		return [element.name, element.value];  
	      	case 'password':  
	      		return [element.name, element.value];  
	      	case 'text': 
	      		return [element.name, element.value];   
	      	case 'checkbox': 
	      		if (element.checked)  
	    	     	return [element.name, element.value];   
	      	case 'radio':  
	      		if (element.checked)  
	    	     	return [element.name, element.value];   
	    }  
	    return null;  
	}
	
		
	function LoadTable(config) {
		var loaded = config.loaded;
		if (loaded == "undefined" || loaded == null || loaded == false){
			config["loaded"] = true;
			
			/**初始化选中行**/
			config["selectedRow"] = [];
			
			/**初始化分页配置属性**/
			config.page["currentPage"] = 1;
			config.page["pageList"] = [];
			config.page["totalRows"] = 0;
			config.page["maxPage"] = 0;
			config.page["prePage"] = false;
			config.page["nextPage"] = true;
			
			/**初始 加载默认排序配置**/
			if (isExist(config.orderby.defField)){
				config["orderField"] = config.orderby.defField;
				if (!isExist(config.orderby.defType)){
					config["orderType"] = config.orderby.defType;
				} else{
					config["orderType"] = "desc";
				}
			} 
			
			/**初始化存储请求条件entry数组**/
			config.requestEntry = [];
			
			/**初始化过滤row状态 	余数 0:隐藏，1:显示，2:过滤后	**/
			config.filterRowStatus = 0;
			
//			/**指定图片的加载方法*/
			var loadPicFn = function(event){
				event = event || window.event;
				var element = event.srcElement ? event.srcElement : event.target;
	        	var dw = document.documentElement.clientWidth;
	        	var dh = document.documentElement.clientHeight;
	        	var pw = element.width ? element.width : 0;
	        	var ph = element.height ? element.height : 0;
	        	element.src = getJsPathByName("common-1.0.0.js").replace("common-1.0.0.js", "images/waiting.gif");
	        	element.style.left = (dw-pw)/2 + "px";
	        	element.style.top = (dh-ph)/2 + "px";
	        };
			
			/**创建图片对象，并给图片添加onload事件。注：微软与别家的添加事件方法不一样**/
	        var loadingPic = document.createElement('img');
			if (navigator.appVersion.indexOf("MSIE") > -1) {
				loadingPic.attachEvent('onload', loadPicFn);
			} else {
				loadingPic.addEventListener('load', loadPicFn);
			}
			
			/**图片对象指定路径，并把图片对象放到body内*/
		    loadingPic.src = getJsPathByName("common-1.0.0.js").replace("common-1.0.0.js", "images/waiting.gif");
	        loadingPic.alt = config.loadingMsg;
	        document.body.appendChild(loadingPic);
	        loadingPic.style.display = "block";
	        loadingPic.style.position = 'absolute';
	        loadingPic.style.zIndex = 1000;
	        loadingPic.width = 30;
	        loadingPic.height = 30;
        	var dw = document.documentElement.clientWidth;
        	var dh = document.documentElement.clientHeight;
        	loadingPic.style.left = (dw-30)/2 + "px";
        	loadingPic.style.top = (dh-30)/2 + "px";
	        config["loadingPic"] = loadingPic;
		}
		config.loadingPic.style.display = "block";
		
		var obj = new Object;
		obj.config = config;		
		
		obj.loadTable = function (){
			
			//var caller = LoadTable.caller;
			var config = this.config;
			var data = config.dataSource.data;
			var url = config.dataSource.url;
			var url_size = config.page.totalPageUrl;
			var columnInfo = config.column;
			
			/*************************获取表格ata数据源总记录数**************************/
			if (config.requestEntry.length > 0){
				for (var i in config.requestEntry){
					url_size = updateOrInsertUrl(url_size, config.requestEntry[i].key, config.requestEntry[i].val);
				}
			}
			var totalRows = $$.ajaxGetRequest(url_size, false);
			if (!isBlank(totalRows)){
				config.page.totalRows = parseInt(totalRows);
				config.page.maxPage = Math.ceil(config.page.totalRows/config.page.pageSize);				
			}
			
			
			
			/*************************获取表格ata数据源，优先使用data数据源配置*************************/
			if (!isEmpty()){
				/**按分页规则取data中的子集**/
				
			
			} 
			/**加载表格，data中没找到，再找url数据源配置**/
			else if (!isBlank(url)){

				/**加载每页行数**/
				var pageSize = config.page.pageSize;
				var pageSizeReqName = config.page.pageSizeReqName;
				url = updateOrInsertUrl(url, pageSizeReqName, pageSize);
				
				/**加载当前页数**/
				var currPage = config.page.currentPage;
				var currPageqReqName = config.page.currPageqReqName;
				if (isExist(currPage)){	
					url = updateOrInsertUrl(url, currPageqReqName, currPage);
				} 
				
				/**加载config.requestEntry中保存的请求entry**/
				if (isExist(config.requestEntry) && config.requestEntry.length > 0){
					for (var i in config.requestEntry)
						url = updateOrInsertUrl(url, config.requestEntry[i].key, config.requestEntry[i].val);
				}
				
				/**加载排序请求**/
				if (isExist(config.orderField)){
					url = url + "&" + config.orderby.fieldReqName + "=" + config.orderField;
					url = url + "&" + config.orderby.typeReqName + "=" + config.orderType;
				}
				
				/**ajax请求，获取服务端传回的data**/
				data = $$.ajaxGetRequest(url, false);
				if (isBlank(data))
					data = [];
				else
					eval('data=' + data);
			}
			
			else{
				data = [];
			}
			
			
			   
			/***************************创建表格对象***************************/
			var tableDiv = document.getElementById(config.id);
			tableDiv.style.width = config.width;
			tableDiv.style.height = config.height ? config.height : null;
			tableDiv.removeChildren();
			tableDiv.style.border = "1px solid #98A";	//点状dotted 实线solid 双线double 虚线dashed
			var stl = tableDiv.style.cssText;
			if (!stl.endWith(';'))
				stl = stl + ';';
			tableDiv.style.cssText = stl + 'overflow:auto;';
			
			var scrollTable = document.createElement('table'); 
			tableDiv.appendChild(scrollTable);
			var scrollTR = scrollTable.insertRow(-1);
			var scrollTD = scrollTR.insertCell(0);
			
			var table = document.createElement('table');
//			table.style.width = config.width;
			table.style.tableLayout = 'fixed';			//列宽由表格宽度和列宽度设定
			table.style.borderCollapse = 'collapse';	//合并连线
			table.style.fontSize = isExist(config.fontSize) ? config.fontSize : '13px';
			table.style.border = isExist(config.border) ? config.border : '1px';
			table.style.borderColor = isExist(config.borderColor) ? config.borderColor : "#333";
			scrollTD.appendChild(table);
			
			/*************************加载表格工具栏**************************************************/
			if (isExist(config.toolBar)) {
				var barInfo = config.toolBar; 
				var tr = table.insertRow(-1);
				var td = tr.insertCell(0);
				var colsize = columnInfo.length;
				
				for (var i in columnInfo)
					if (columnInfo[i].display == 'none')
						colsize--;
				if (!isExist(config.showRownum) || config.showRownum)
					colsize++;
				
				td.removeChildren();
				td.style.border = "1px solid #98A";	//点状dotted 实线solid 双线double 虚线dashed
				td.style.width = tr.style.width;
				td.setAttribute("colspan", colsize);
				td.setAttribute("align", "left");
				td.style.backgroundColor = "#ABD";
				
				var bar_div = document.createElement("div");
				bar_div.style.paddingLeft = "5px"; 
				bar_div.style.paddingRight = "5px"; 
				bar_div.style.paddingTop = "3px"; 
				bar_div.style.paddingBottom = "3px"; 
				td.appendChild(bar_div);
				
				for (var key in barInfo){
					var link_div = document.createElement("span");
					bar_div.appendChild(link_div);
					
					var link_a = document.createElement("a");
					link_a.style.textDecoration = 'none';// 无下划线					
					link_a.style.color = "#000";
//					var img_link = document.createElement('img');
					link_a.innerHTML = barInfo[key].textName;
					link_a.href = '#';
					link_a.id = config.id + "_toolBar";
					link_a.className = key;
					link_div.appendChild(link_a);
					
					var divWidth = link_div.offsetWidth;
					var divHeight = "25";
					if (isExist(barInfo[key].width))
						divWidth = barInfo[key].width.replace("px", "").trim();//获取配置宽度
					if (isExist(barInfo[key].height))
						divHeight = barInfo[key].height.replace("px", "").trim();//获取配置高度
					var aWidth = link_a.offsetWidth;
					var aHeight = link_a.offsetHeight;
					
					//居中
					var marginleft = (divWidth - aWidth)/2;  
					var margintop = (divHeight - aHeight)/2;  
					link_a.style.marginLeft = marginleft + "px";  
					link_div.style.paddingTop = margintop + "px"; 				         
					
					link_a.onclick = function() { 
						var event = event || window.event;
						for (var k in config.toolBar){
							if (k == this.className && config.toolBar[k].event != null){								
								config.toolBar[k].event.apply(null, [config.selectedRow]);
								break;
							}
						}
				    }; 
				}
			}
			
			
			
			/*************************加载表格标题行*************************/
			var title_tr = table.insertRow(-1);
			title_tr.id = config.id + "_title_tr";
			title_tr.style.backgroundColor = "#BCD";
			
			/**先加载序号列，无配置默认加载**/
			var showRownum = false;
		    if (!isExist(config.showRownum) || config.showRownum){
				var showRownum = true;
		    	var td = title_tr.insertCell(0);
				td.style.width = "40px";
				td.innerHTML = "序号";
				td.style.borderBottom = "1px solid #98A";	//点状dotted 实线solid 双线double 虚线dashed
				if (isExist(config.columnExpand) && !config.columnExpand)
					td.style.whiteSpace = 'nowrap';
		    }
			
		    config["orderFieldMap"] = [];
		    
			for (var i in columnInfo){
				/**创建td对象，并添加配置的各种属性**/
				var td = title_tr.insertCell(showRownum ? parseInt(i) + 1 : i);
				td.id = title_tr.id + '_td' + i;
				td.style.borderBottom = "1px solid #98A";	//点状dotted 实线solid 双线double 虚线dashed
				td.style.borderLeft = "1px solid #98A";	//点状dotted 实线solid 双线double 虚线dashed
				td.innerHTML = columnInfo[i].titleName;
				if (!isBlank(columnInfo[i].width))
					td.style.width = columnInfo[i].width;//td.setAttribute("width", columnInfo[j].width);
				if (!isBlank(columnInfo[i].display))
					td.style.display = columnInfo[i].display;
				if (!isBlank(columnInfo[i].align))
					td.style.textAlign = columnInfo[i].align;
				if (isExist(config.columnExpand) && !config.columnExpand)
					td.style.whiteSpace = 'nowrap';
				
				/**如果config.orderby有排序配置，加载排序功能**/
				if (arrayContain(config.orderby.feildList, columnInfo[i].name)){	
//					td.className = td.className + " _order" + columnInfo[i].name;			//⇂ ⇃ ↾ ↿ ↑ ↓⇡ ⇣ ⇞⇟ ↨
					
					var titleSpan = document.createElement('span');
//					titleSpan.className = 'click_order _order' + columnInfo[i].name;
					titleSpan.innerHTML = columnInfo[i].titleName;
					
					var orderSpan = document.createElement('span');
					orderSpan.style.backgroundColor = "#9AC";
					orderSpan.style.width = '3px';
					orderSpan.style.border = "1px solid #768";	//点状dotted 实线solid 双线double 虚线dashed

					var orderA = document.createElement('a');
					orderA.id = td.id + '_order';
					orderA.href = "#";
					orderA.style.textDecoration = 'none';// 无下划线					
					orderA.style.color = "#000";
					orderA.innerHTML = '⇡⇣';				//⇂ ⇃ ↾ ↿ ↑ ↓⇡ ⇣ ⇞⇟ ↨
					config.orderFieldMap.push([orderA.id, columnInfo[i].name]);
					
					if (config.orderField && config.orderField == columnInfo[i].name){
						if (config.orderType == 'desc')
							orderA.innerHTML = '⇣';
						else if (config.orderType == 'asc')
							orderA.innerHTML = '⇡';
					}

					td.innerHTML = '';
					var orderTable = document.createElement('table');
					td.appendChild(orderTable);
					orderTable.style.width = td.style.width;
					orderTable.style.height = td.style.height;
					orderTable.style.border = "0px solid #768";
					var orderTR = orderTable.insertRow(-1);
					var titleTd = orderTR.insertCell(0);
					var orderTd = orderTR.insertCell(1);
					orderTd.style.width = '3px';
					titleTd.appendChild(titleSpan);
					orderTd.appendChild(orderSpan);
					orderSpan.appendChild(orderA);
					
					/**排序的标题添加事件，点击后重新用对应排序字段加载表格**/
					orderA.onclick = function(event) {
						event = event || window.event;
						var element = event.srcElement ? event.srcElement : event.target;
						var orderField = null;
						for (var s = 0; s < config.orderFieldMap.length; s ++){
							if (element.id && element.id == config.orderFieldMap[s][0]){
								orderField = config.orderFieldMap[s][1];								
								break;
							}
						}
						
						/**如果找到orderField，把orderField做为排序字段值，存放到config.orderField，以便表格加载时调用**/
						if (orderField != null){
							if (!config.orderType)
								config["orderType"] = "desc";
							else if (config.orderType.toLowerCase() == "asc" || config.orderField != orderField)
								config.orderType = "desc";
							else
								config.orderType = "asc";
							
							if (isExist(config.orderField))
								config.orderField = orderField;
							else
								config["orderField"] = orderField;
							
							/**重新加载表格**/
							var load = new LoadTable(obj.config);
							load.loadTable();
						}
					};
				}
			}
						
			
			
			/*************************用data数组加载表格数据*************************/
			for (var i = 0; i < data.length; i++){
				/**创建tr行对象**/
				var rowObj = data[i];
				var tr = table.insertRow(-1);
				tr.id = config.id + "_tr" + i;
				if (config.rowHeight)
					tr.style.height = config.rowHeight;
				if (i%2==0)
					tr.style.backgroundColor = "#BEF";
				else
					tr.style.backgroundColor = "#CDF";
				
				/**添加行点击选中事件**/
				tr.onclick = function(event) {
					/**如果点击到按钮链接，行不被选中**/
					event = event || window.event;
					var element = event.srcElement ? event.srcElement : event.target;
					var nodeName = element.nodeName.toUpperCase();
					if (nodeName == "A" || nodeName == "INPUT" || nodeName == "BUTTON") 
						return;
					
					/**如果是单选，无单多选配置，先取消选中，颜色还原，记录从config.selectedRow数组中取出**/
					if (!isExist(config.singleSelect) || config.singleSelect){							
						var seled = config.selectedRow;
						for (var i in seled){
							var ele = document.getElementById(seled[i].nodeId);
							ele.style.backgroundColor = seled[i].color;
						}
					}
					
					/**找到点击的tr行，判断是添加选中，还是去掉选中，true表示添加选中，false表示去掉选中**/
					var trNode = findParentElementByName(element, "TR");
					var rowIndex = parseInt(trNode.id.substring(trNode.id.length - 1));
					var addSelect = true;
					var seledRowIndex = -1;
					for (var k in config.selectedRow){
						if (trNode.id == config.selectedRow[k].nodeId){
							addSelect = false;
							seledRowIndex = k;
							break;
						}
					}
					
					/**config.selectedRow数组中保存行的颜色和id，然后改成选中颜色中**/
					if (addSelect){							
						var bgcolorObj = {nodeId:trNode.id, color:trNode.style.backgroundColor, rowData:data[rowIndex]};
						if (!isExist(config.singleSelect) || config.singleSelect)						
							config.selectedRow = [bgcolorObj];
						else
							config.selectedRow.push(bgcolorObj);
						trNode.style.backgroundColor = "#4876FF";	//选中颜色
						
						/**config.selectedRow还原选中颜色，然后数组中删除选中记录**/
					} else{
						trNode.style.backgroundColor = config.selectedRow[seledRowIndex].color;
						config.selectedRow.splice(seledRowIndex, 1);		
					}
				};
				
				/**先加载序号列，无配置默认加载**/
			    if (!isExist(config.showRownum) || config.showRownum){
					var showRownum = true;
			    	var td = document.createElement("td");
					td.style.width = "40px";
					td.innerHTML = i + 1;
					tr.appendChild(td);
					
					if (i < data.length - 1){
						td.style.borderBottom = "1px dashed #98A";	//点状dotted 实线solid 双线double 虚线dashed
					}
			    }
				
				/**再加载config.column配置列**/
				for (var j = 0; j < columnInfo.length; j++){
					
					/**创建td对象，并添加配置的各种属性**/
					var td = tr.insertCell(showRownum ? j + 1 : j);
					td.id = tr.id + '_td' + j;
					if (!isBlank(columnInfo[j].width))
						td.style.width = columnInfo[j].width;//td.setAttribute("width", columnInfo[j].width);
					if (!isBlank(columnInfo[j].display))
						td.style.display = columnInfo[j].display;
					if (!isBlank(columnInfo[j].align))
						td.style.textAlign = columnInfo[j].align;
					//如果未设置td宽度，则nowrap属性起作用的，如果设置了td宽度，则nowrap属性不起作用
					if (isExist(config.columnExpand) && !config.columnExpand && td.style.display != 'hidden'){
						var st = td.style.cssText;
						if (!st.endWith(';'))
							st = st + ';';
						td.style.cssText = st + 'text-overflow:ellipsis; overflow:hidden; white-space: nowrap; padding:2px;';
					}
						
					/**根据column中name字段配置，从data数据源中找到对应值**/
					var fieldValue = "";
					var origVal = getValueByNameFromObj(rowObj, columnInfo[j].name);
					
					/**如果无转换函数，直接取用**/
					if (!isExist(columnInfo[j].formateFn))
						fieldValue = origVal;
					/**如果配置了转换函数，用函数转换**/
					else {
						//slice(0)方法提取出数组子集，从0开始等于复制，
						//splice(i, j, arg1,arg12...)修改原数组，删除部分，添加部分，i起始index, j删除长度，arg添加元素集
						/**用户指定的函数，可以用两个参数：单元值和行数据源，为不使数据源被改，这里行数据源params是复制的**/
						var params = [origVal, rowObj].slice(0);
						fieldValue = columnInfo[j].formateFn.apply(null, params);
					}
					
					/**单元值加到td中，td加到tr中**/
					td.innerHTML = fieldValue;
					
					td.style.borderLeft = "1px dashed #98A";	//点状dotted 实线solid 双线double 虚线dashed
					if (i < data.length - 1){
						td.style.borderBottom = "1px dashed #98A";	//点状dotted 实线solid 双线double 虚线dashed
					}
				}
			}
			
			
			
			/*************************如果过滤有配置，加载表格过滤功能*************************/
			if (isExist(config.fiter)){
				var fiter = config.fiter;
				var row = table.insertRow(2);
				row.setAttribute("height", table.children[0].style.height);
				
				if (config.filterRowStatus % 3 == 0)
					row.style.display = "none";
				else if (config.filterRowStatus % 3 == 1 || config.filterRowStatus % 3 == 2)
					row.style.display = "";
				
				var showRownum = !isExist(config.showRownum) || config.showRownum ? true : false;
				var f = 0;
				
				if (showRownum){
					f++;
					var td = row.insertCell(s);
					td.style.borderBottom = "1px dashed #98A";	//点状dotted 实线solid 双线double 虚线dashed
				}
				
				for (var i in columnInfo)
					if (columnInfo[i].display == 'none')
						f--;
				
				for (var i in columnInfo){
					
					var s = showRownum ? parseInt(i) + f : i;
					
					var flag = false;
					for (var j in fiter){
						
						/**如果过滤fiter配置，和column配置中name的是相等的，就加载此列过滤**/
						if (fiter[j].name == columnInfo[i].name){
							/**创建td，如果td不是过滤td，td内为空，如果要过滤，把表单node放入td**/
							var td = row.insertCell(s);
							td.style.borderLeft = "1px dashed #98A";	//点状dotted 实线solid 双线double 虚线dashed
							td.style.borderBottom = "1px dashed #98A";	//点状dotted 实线solid 双线double 虚线dashed
							
							var node = null;
							var tdwidth = columnInfo[i].width;
							if (isBlank(tdwidth))
								tdwidth = table.children[0].children[i].style.width;
							
							/**按类型把node加载成表单，下拉框用配置的函数加载数据，__做前缀class存索引**/
							if (fiter[j].type == "text"){
								node = document.createElement("input");
								node.type = "text";
								node.className = "fiter_form __" + j;
								node.style.width = tdwidth;
								node.name= fiter[j].name;
								td.appendChild(node);
							} else if(fiter[j].type == "password"){
								td.innerHTML = "<input type='password' class='fiter_form __" + j + "'  style='width:" + tdwidth + ";' />";
							} else if(fiter[j].type == "select" || fiter[j].type == "radio"){
								node = document.createElement("select");  
								node.className = "fiter_form __" + j;
								node.style.width = tdwidth;
								node.name= fiter[j].name;
								td.appendChild(node);
								createSelectOp(node, fiter[j].dataSource, fiter[j].valueField, fiter[j].textField, true);
							}	
							
							/**如果配置了事件，就给过滤表单添加事件**/
							if (isExist(node) && isExist(fiter[j].onclick)){
								node.onclick = function(event){
									event = event || window.event;
									var element = event.srcElement ? event.srcElement : event.target;
									var classNames = element.className.split(" ");
									/**索引存放在clsaa中，用__做前缀，查出来**/
									var index = -1;
									for (var j in classNames){
										if (classNames[j].startWith("__")){
											var valStr = classNames[j].replace("__", "");
											index = parseInt(valStr);
											break;								
										}
									}
									fiter[index].onclick.apply(null, [event]);
								};
							}
							flag = true;
							break;
						}
					}
					if (!flag){
						var td = row.insertCell(s);
						td.style.borderLeft = "1px dashed #98A";	//点状dotted 实线solid 双线double 虚线dashed
						td.style.borderBottom = "1px dashed #98A";	//点状dotted 实线solid 双线double 虚线dashed
					}
				}
				
				/**说明：添加键盘事件，如果回车就在 过滤行显示或隐藏 
				 * 注意：此处IE用document.onkeypress失灵 **/
				obj.onkeypress = document.onkeydown = function (e) {
					var currKey = 0;
					var e = e || event;
					currKey = e.keyCode || e.which || e.charCode;
					if (currKey == 13){
						config.filterRowStatus = config.filterRowStatus + 1;
						
						if (config.filterRowStatus % 3 == 0)
							row.style.display = "none";
						else if (config.filterRowStatus % 3 == 1 || config.filterRowStatus % 3 == 2)
							row.style.display = "";
						if (config.filterRowStatus % 3 == 2){
							var elements = getObjElementsByClassName(table, "fiter_form");
							if (!isExist(config.requestEntry))
								config[requestEntry] = [];
							/**把表格中所有fiter_form的表单name和value做为请求数据，存入config.requestEntry，表格重新加载时使用**/
							config.requestEntry = [];
							for (var ele in elements){
								var entry = {};
								entry["key"] = elements[ele].name;
								entry["val"] = elements[ele].value;
								config.requestEntry.push(entry);
							}
							/**重新加载表格**/
							var load = new LoadTable(obj.config);
							load.loadTable();
						}
					}
				};
			}

			/**按config.page存放的信息，config.page里生成分页信息**/
			loadPage(config.page);
			
			
			
			/*************************如果表格有记录行，加载分页*************************/
			if (data.length > 1){
				
				var colsize = columnInfo.length;
				for (var i in columnInfo)
					if (columnInfo[i].display == 'none')
						colsize--;
				if (!isExist(config.showRownum) || config.showRownum)
					colsize++;
				
				var pageInfo = config.page;
				var tr = document.createElement("tr");
				tr.style.backgroundColor = "#BCD";
				var td = document.createElement("td");
				td.id = config.id + "_page_td";
				td.setAttribute("colspan", colsize);
				td.setAttribute("align", "right");
				
				td.style.borderTop = "1px solid #98A";	//点状dotted 实线solid 双线double 虚线dashed
				
				/**分页行的显示值，添加链接**/
				var linkNums = "";
				
				for (var i = 0; i < pageInfo.pageList.length; i++){
					if (pageInfo.pageList[i] == pageInfo.currentPage)
						linkNums = linkNums + " " + pageInfo.pageList[i];
					else
						linkNums = linkNums + " <a href='#' class='pageNumLink __" + i + "' onclick='' >" + pageInfo.pageList[i] + "</a>";
				}
				
				if (isExist(pageInfo.prePage) && pageInfo.prePage){
					linkNums = linkNums + " <a href='#' id='" + config.id + "_prePageLink_id' class='prePageLink' onclick='' >上一页></a>";
				}
				
				if (isExist(pageInfo.nextPage) && pageInfo.nextPage){
					linkNums = linkNums + " <a href='#' id='" + config.id + "_nextPageLink_id' class='nextPageLink' onclick='' >下一页></a>";
				}
				
				linkNums = linkNums + " <a href='#' id='" + config.id + "_lastPageLink_id' class='lastPageLink' onclick='' >尾页</a>";
				
				if (!isBlank(pageInfo.totalRows))
					linkNums = linkNums +  " 记录总数是:" + pageInfo.totalRows;
				
				linkNums = linkNums + "  跳到第";
				linkNums = linkNums + "<input type='text' id='" + config.id + "_pageToNum_id' name='pageToNum' style='width:30px;' >";
				linkNums = linkNums + "页   ";
				linkNums = linkNums + "<a href='#' id='" + config.id + "_pageToLink_id' >确定</a>";
					
				linkNums = linkNums + "  每页";
				linkNums = linkNums + "<input type='text' id='" + config.id + "_setPageNum_id' name='setPageNum' style='width:30px;' >";
				linkNums = linkNums + "行   ";
				linkNums = linkNums + "<a href='#' id='" + config.id + "_setPageLink_id' >确定</a>";
					
				td.innerHTML = linkNums;		
				tr.appendChild(td);
				table.appendChild(tr);
				
				var pageToLink = document.getElementById(config.id + "_pageToLink_id");
				if (pageToLink != null ){					
					pageToLink.onclick = function(event) { 
						event = event || window.event;
						var element = event.srcElement ? event.srcElement : event.target;
						var inputId = element.id.replace('_pageToLink_id', '_pageToNum_id');
						var inputStr = document.getElementById(inputId).value;
						var inputNum = parseInt(inputStr.trim());
						if (!inputNum || isNaN(inputNum)){
							warnDialog('请输入数字');
							return;
						}
						pageInfo.currentPage = parseInt(inputNum);
						/**重新加载表格**/
						var load = new LoadTable(obj.config);
						load.loadTable(); 
				    }; 
				}
	
				var setPageLink = document.getElementById(config.id + "_setPageLink_id");
				if (setPageLink != null ){					
					setPageLink.onclick = function(event) { 
						event = event || window.event;
						var element = event.srcElement ? event.srcElement : event.target;
						var inputId = element.id.replace('_setPageLink_id', '_setPageNum_id');
						var inputStr = document.getElementById(inputId).value;
						var inputNum = parseInt(inputStr.trim());
						if (!inputNum || isNaN(inputNum)){
							warnDialog('请输入数字');
							return;
						}
						pageInfo.pageSize = parseInt(inputNum);
						/**重新加载表格**/
						var load = new LoadTable(obj.config);
						load.loadTable(); 
				    }; 
				}

				/**a链接添加点击事件**/
				var alinkObjs = getObjElementsByClassNameTagName(table, "pageNumLink", "a");
				for (var i in alinkObjs){
					alinkObjs[i].onclick = function(event) { 
						/**从a链接的class存储的次序值，用次序值从pageList中取得点击页数**/
						event = event || window.event;
						var element = event.srcElement ? event.srcElement : event.target;
						var classNames = element.className.split(" ");
						var index = -1;
						for (var j in classNames){
							if (classNames[j].startWith("__")){
								var valStr = classNames[j].replace("__", "");
								index = parseInt(valStr);
								break;								
							}
						}
						pageInfo.currentPage = pageInfo.pageList[index];
						/**重新加载表格**/
						var load = new LoadTable(obj.config);
						load.loadTable(); 
				    }; 
				}
				
				/**a链接添加点击事件**/
				var prelinkObj = document.getElementById(config.id + "_prePageLink_id");
				if (prelinkObj != null ){					
					prelinkObj.onclick = function() { 
						pageInfo.currentPage = pageInfo.currentPage - 1;
						/**重新加载表格**/
						var load = new LoadTable(obj.config);
						load.loadTable(); 
				    }; 
				}
				
				/**a链接添加点击事件**/
			    var nextlinkObj = document.getElementById(config.id + "_nextPageLink_id");
			    if (nextlinkObj != null){			    	
			    	nextlinkObj.onclick = function() { 
						pageInfo.currentPage = pageInfo.currentPage + 1;
						/**重新加载表格**/
						var load = new LoadTable(obj.config);
						load.loadTable(); 
				    }; 
			    }
				
				/**a链接添加点击事件**/
			    var lastlinkObj = document.getElementById(config.id + "_lastPageLink_id");
			    if (lastlinkObj != null){			    	
			    	lastlinkObj.onclick = function() { 
						pageInfo.currentPage = pageInfo.maxPage;
						/**重新加载表格**/
						var load = new LoadTable(obj.config);
						load.loadTable(); 
				    };
			    }
			}
			
			if (table.offsetHeight < tableDiv.offsetHeight)
				tableDiv.style.height = table.offsetHeight + 8 + 'px';
			if (table.offsetWidth < tableDiv.offsetWidth)
				tableDiv.style.width = table.offsetWidth + 8 + 'px';
			config.loadingPic.style.display = "none";
		};
		return obj;
	}
	
	function loadPage (pageInfo, length){
		length = length ? length : 7;
		var halfLen = Math.round(length / 2);
		var maxPage = pageInfo.maxPage;
		var currPage = pageInfo.currentPage;
		 
		 if (maxPage <= 0)
			 return;
		 
		 if (currPage <= halfLen)
		 	 pageInfo.prePage = false;
		 else
			 pageInfo.prePage = true;
		 
		 if (currPage > maxPage - halfLen)
			 pageInfo.nextPage = false;
		 else
			 pageInfo.nextPage = true;
		 
		 if (maxPage <= length){
			 var array = [];
			 for (var i = 1; i < maxPage; i++)
				 array.push(i);
			 pageInfo.pageList = array;
			 
		 } else {
			 pageInfo.pageList = [];
			 if (currPage > maxPage - halfLen)
				 for (var i = 0, j = length - 1; i < length; i++, j--)
					 pageInfo.pageList[j] = maxPage - i;
				 
			 else if (currPage <= halfLen)
				 for (var i = 0; i < length; i++)
					 pageInfo.pageList[i] = i + 1;
			 
			 else
				 for (var i = 0; i < length; i++)
					 pageInfo.pageList[i] = currPage - halfLen + 1 + i;
		 }
	}
	
	/**如果url中存在key字段，就换掉key对应的值，否则直接添加key=val**/
	function updateOrInsertUrl(url, key, val){
		var lastIndex = url.lastIndexOf(key);
		if (lastIndex > 0){
			var suburl = url.substring(lastIndex + key.length);
			var url1 = url.substring(0, lastIndex + key.length);
			var url2 = "=" + val;
			url = "";
			url += url1;
			url += url2;
			if (suburl.indexOf("&") >= 0){
				var url3 = suburl.substring(suburl.indexOf("&"));
				url += url3;
			}
		} else {
			if (url.indexOf("?") < 0)
				url = url + "?";
			else
				url = url + "&";
			url = url + key + "=" + val;
		}
		return url;
	}
 
	/**
	 * 遍历页面上引入的js标签，通过标签的src属性找到js文件路径
	 * 要求：			document页面范围内jsName是唯一的
	 * @param jsName	js文件名
	 * @returns			js文件路径
	 */
	function getJsPathByName(jsName){
		var scripts = document.scripts;
	    for (var i in scripts){
	    	if (!isBlank(scripts[i].src) && scripts[i].src.indexOf(jsName) >= 0){
	    		return scripts[i].src;
	    	}
	    }
	}
	
	/**
	 * 本对Element相对外层Element的横向居中
	 * @param outer
	 */
	Element.prototype.marginCenter = function(outer){
		var width = (outer.offsetWidth - this.offsetWidth)/2;
		this.style.marginLeft = width + "px";  
		this.style.marginRight = width + "px";   
	};
	
	/**
	 * 本对Element相对外层Element的纵向居中
	 * @param outer
	 */
	Element.prototype.marginMiddle = function(outer){
		var height = (outer.offsetHeight - this.offsetHeight)/2;
		this.style.marginTop = height + "px";  
		this.style.marginBottom = height + "px";   
	};

	function paddingCenter(outer, inner){
		var width = (outer.offsetWidth - inner.offsetWidth)/2;  
		outer.style.paddingLeft = width + "px";
		outer.style.paddingRight = width + "px";
	}


	function getPathRelativeJsFile(jsName, relativePath){		
		return getJsPathByName(jsName).replace(jsName, relativePath);
	}

	
	function getDomPath(){
		//总结：在css中出现的相对路径，是以css文件所在路径为基准的，而js中的路径则是以导入此js的网页文件所在的位置为基准的。
		//获取当前document网址，如： http://localhost:8083/web/share/meun.jsp
	    var domPath = window.document.location.href;
	    //获取主机地址之后的目录，如： web/share/meun.jsp
	    var pathName = window.document.location.pathname;
	}
	
	Date.prototype.format = function(format) {
		var o = {
			"M+" : this.getMonth()+1, // month
			"d+" : this.getDate(), // day
			"h+" : this.getHours(), // hour
			"m+" : this.getMinutes(), // minute
			"s+" : this.getSeconds(), // second
			"q+" : Math.floor((this.getMonth()+3)/3), // quarter
			"S" : this.getMilliseconds() // millisecond
		};
		if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
		(this.getFullYear()+"").substr(4- RegExp.$1.length));
		for(var k in o)if(new RegExp("("+ k +")").test(format))
		format = format.replace(RegExp.$1,
		RegExp.$1.length==1? o[k] :
		("00"+ o[k]).substr((""+ o[k]).length));
		return format;
	};
	
	/**计算时间差, 返回的是毫秒**/
	function diffTime(startTime, endTime){  
		if (!startTime || !endTime)
			return 0;
		if ((navigator.userAgent.indexOf('MSIE') >= 0) && (navigator.userAgent.indexOf('Opera') < 0)){
			startTime = startTime.replace("-", "/");
			endTime = endTime.replace("-", "/");
		} else if (navigator.userAgent.indexOf('Firefox') >= 0){
		    
		} else if (navigator.userAgent.indexOf('Opera') >= 0){
		    
		} else{
		    
		}
        var begindate = new Date(startTime);//开始日期  
        var enddate = new Date(endTime);//结束日期  
        return tempdays = Date.parse(enddate) - Date.parse(begindate);  
	}
    
	/**
	按属性名从json对象中，取出对应值
	**/
	function getValueByNameFromObj(obj, name){
		for (var key in obj) 
			if (key == name)
				return obj[key];
	}
	
	Element.prototype.remove = function(){
		try {
			this.remove();
		} catch (E) {			
			this.parentNode.removeChild(this);
		}
	};
	
	Element.prototype.removeChildren = function(){
		var nodelist = this.childNodes;
		for (var i = 0; i < nodelist.length; i++){
			this.removeChild(nodelist[i]);
		}
	};
	
	String.prototype.endWith = function(str){
		if(str==null||str==""||this.length==0||str.length>this.length)
		  	return false;
		if(this.substring(this.length-str.length)==str)
		 	return true;
		else
		  	return false;
		return true;
	};
	
	String.prototype.startWith = function(str){
		if(str==null||str==""||this.length==0||str.length>this.length)
		  	return false;
		if(this.substring(0, str.length)==str)
		  	return true;
		else
		  	return false;
		return true;
	};
	
	String.prototype.trim = function(){
		return this.replace(/(^\s*)|(\s*$)/g,"");
	};
	
	function arrayContain(array, element){
		for (i in array)
			if (array[i] == element)
				return true;
	}
	
	function isExist(s){
		if (s == 'undefined' || s == null)
		  	return false;
		else
		  	return true;
	};
	
	function isBlank (str){
		if (str == 'undefined' || str == null || str == "")
		  	return true;
		else
		  	return false;
	};
	
	function isEmpty (array){
		if (array == 'undefined' || array == null || array.length == 0)
			return true;
		else
			return false;
	};
