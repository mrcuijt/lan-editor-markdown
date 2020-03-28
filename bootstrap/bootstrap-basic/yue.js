
$(function() {

	try {
		init();
		$(window).resize(function() {
			resizeMain();
			resizeStudyViewer();
		});
	} catch(err) {
	    console.info("Error name: " + err.name + "");
	    console.info("Error message: " + err.message);
	}
	
	// resizeMain();
});


function init(){
	
	initMenu();
	initRequestLevel();
	initCaseLibraryPanel();
//	countPatientAge(); // 病人年龄待优化，有错误
	initHistoryImage();
	downloadAndView();
	loadDICOMProgressMade();
	
}

// 病人姓名
function countYear(startDate,endDate){
	
	try {
		
        if (startDate) {
            if (endDate) {
            	return new Date(scannerTime).getYear() - new Date(birthday).getYear();
            } else {
                return startDate.getYear()+"年"+(startDate.getMonth()+1)+"月"+getDate()+"日";
            }
        }else{
        	return startDate.getYear()+"年"+(startDate.getMonth()+1)+"月"+getDate()+"日";
        }
    } catch (e) {
        return startDate.getYear()+"年"+(startDate.getMonth()+1)+"月"+getDate()+"日";
    }
}

function countPatientAge(){
	
	try{
		var age;
		
		var patientAge = countYear(birthday,scannerTime)
		
		if(!isNaN(patientAge)){// 如果是数字
			
			if(patientAge){
				if(patientAge<=0){
					age = 0+"岁";
				}else{
					age = patientAge+"岁"
				}
			}
		}
		$("#patientAge").html(age);
	}catch(e){
		$("#patientAge").html(patientAge);
	}
}


// 导航菜单 Menu
function initMenu(){
	
	// 设置菜单的 url 请求地址,跳转到已完成界面
	/*var menu_32 = $("#menu_32>a").attr("href",path+"/doctor/complateMain.action?processState=已完成");*/
	var menu_32 = $("#menu_32>a").attr("href",path+"/doctor/complateMain.action?processState="+Completed);
	
	$("#menu_28").attr("href","#");
	
//	if(doctorType == "诊断医生"){
	if(doctorType == OperatorDocor){
		 $("#menu_28").hide();
		 $("#menu_26").show();
		
	}
//	if(doctorType == "审核医生"){
	if(doctorType == AuditorDocor){
		 $("#menu_26").hide();
		 $("#menu_28").show();

		 $("#menu_28").attr("class","active");
		 $("#menu_26").prop("class","");
		 $("#menu_32").prop("class","");
	}

	//待诊断影像
	$('#menu_26').click(function() {
		
		// 待诊断列表
		try {
			$("#menu_26").attr("class","active");
			$("#menu_28").prop("class","");
			$("#menu_32").prop("class","");
			window.location.href="";
		} catch(err) {
		    console.info("Error name: " + err.name + "");
		    console.info("Error message: " + err.message);
		}finally{
			
		}
		
	})

	// 待审核影像
	$('#menu_28').click(function() {
		// 待审核列表
		$("#menu_28").attr("class","active");
		$("#menu_26").prop("class","");
		$("#menu_32").prop("class","");
	})
	
	// 已审核影像
	$('#menu_32').click(function() {
		// 已审核列表
		$("#menu_32").attr("class","active");
		$("#menu_28").prop("class","");
		$("#menu_26").prop("class","");
	})
	
}

// 生成请求上级的 input 输入框
function initRequestLevel(){

	/*if(processState && processState === "审核中" && doctorType === "审核医生"){*/
	if(processState && processState === Auditing && doctorType === AuditorDocor){
	
		$(".operation").before($("<input/>").attr("id","difficult").attr("name","difficult").attr("value","true").attr("type","checkbox"));
		$(".operation").before($("<label/>").attr("for","difficult").css("color","red").html("此片存疑建议请求上级"));
		
	}
}

/*******************************************************************************
 * 
 * @returns
 */
// DICOM阅片
function downloadAndView() {
	// http://localhost:8080/hyzx/doctor/queryPatientFileView.action?studyId=1
	// http://localhost:8080/hyzx/html/doctor/data/yue.json
	// http://localhost:8080/hyzx/doctor/queryPatientFileView.action?studyId=1

	if(!studyId){

		alert("网络异常，请刷新页面后重试。");
		
		return;
	}
	
	var url = path + "/doctor/queryPatientFileView.action?studyId=#studyId".replace(/#studyId/g,studyId);
	
	try {
		
//		$.getJSON(url, function(data) {
//			
//			var element = $(".viewport").get(0);
//			
//			var imageId = "wadouri:http://test-dicom-store.oss-cn-beijing.aliyuncs.com/"+data.seriesList[0].instanceList[0].imageId;
//				cornerstone.enable(element);
//				cornerstone.loadAndCacheImage(imageId).then(function(image) {
//				cornerstone.displayImage(element, image);
//				cornerstoneTools.mouseInput.enable(element);
//			});
//			
//		});
		
		
		$.getJSON(url, function(data) {
			var stacks = [];
			var currentStackIndex = 0;
			var stack = {
				imageIds : [],
				currentImageIdIndex : 0
			};

			data.seriesList[0].instanceList.forEach(function(images) {
//				/*var uri = "http://test-dicom-store.oss-cn-beijing.aliyuncs.com/";*/
//				
				var uri = "http://dicom-store.oss-cn-beijing.aliyuncs.com//";
				uri = 'wadouri:' + uri;
				uri = uri.substr(0, uri.length - 1);
				var imageId = uri + images.imageId;
//				
//				/*var address = uri.substring(8)+image.imageId;
//                
//    			$.ajax({
//                	url: address,
//                	type: 'GET',
//                	async: false,
//                	complete: function(response) {
//                    	if(response.status == 200) {
//                    		stack.imageIds.push(imageId);
//                    	}
//                	}
//                });*/
//				
//				// 去除注释时需要注意
				stack.imageIds.push(imageId);
			});
//			
			stacks.push(stack);
//
			var imageId = stacks[currentStackIndex].imageIds[0];
//
			var element = $(".viewport").get(0);
			
			var parent = $(element).parent();
			var childDivs = $(parent).find('.overlay');
	        var topLeft = $(childDivs[0]).find('div');
	        $(topLeft[0]).text(data.patientName);
	        $(topLeft[1]).text(data.patientId);
	        var topRight= $(childDivs[1]).find('div');
	        $(topRight[0]).text(data.studyDescription);
	        $(topRight[1]).text(data.studyDate);
	        var bottomLeft = $(childDivs[2]).find('div');
	        var bottomRight = $(childDivs[3]).find('div');
	        
	        
	        // 显示图像的 FPS
	        function onNewImage(e,detail) {
	            // if we are currently playing a clip then update the FPS
	            var playClipToolData = cornerstoneTools.getToolState(element, 'playClip');
	            if(playClipToolData !== undefined && playClipToolData.data.length > 0 && playClipToolData.data[0].intervalId !== undefined && detail.frameRate !== undefined) {
	                $(bottomLeft[0]).text("FPS: " + Math.round(detail.frameRate));
	                //console.log('frameRate: ' + e.detail.frameRate);
	            } else {
	                if($(bottomLeft[0]).text().length > 0) {
	                    $(bottomLeft[0]).text("");
	                }
	            }
	            $(bottomLeft[2]).text("Image #" + (stacks[currentStackIndex].currentImageIdIndex + 1) + "/" + stacks[currentStackIndex].imageIds.length);
	        }
	        $(element).bind("CornerstoneNewImage",onNewImage);
//	        element.addEventListener("CornerstoneNewImage", onNewImage, false);

	        // 显示图像的 放大倍数，窗宽窗位，渲染时间
	        function onImageRendered(e,detail) {
	            $(bottomRight[0]).text("Zoom:" + detail.viewport.scale.toFixed(2));
	            $(bottomRight[1]).text("WW/WL:" + Math.round(detail.viewport.voi.windowWidth) + "/" + Math.round(detail.viewport.voi.windowCenter));
	            $(bottomLeft[1]).text("Render Time:" + detail.renderTimeInMs + " ms");
	        }
	        
//	        element.addEventListener("CornerstoneImageRendered", onImageRendered, false);
//	        element.
	        $(element).bind("CornerstoneImageRendered", onImageRendered);
	        
	        // Register the dialogs using the HTML5 Dialog Polyfill
	        var annotationDialog = document.querySelector('.annotationDialog');
	        var relabelDialog = document.querySelector('.relabelDialog');
	        dialogPolyfill.registerDialog(annotationDialog);
	        dialogPolyfill.registerDialog(relabelDialog);
	        
	        // 文本标注配置
	        var config = {
	                getTextCallback : getTextCallback,
	                changeTextCallback : changeTextCallback,
	                drawHandles : false,
	                drawHandlesOnHover : true,
	                arrowFirst : true
	            }
	        cornerstoneTools.arrowAnnotate.setConfiguration(config);
	        
	        // 放大镜配置
	        var config = {
	                magnifySize: parseInt(300, 10), // 放大尺寸 2
	                magnificationLevel: parseInt(2, 10) // 放大倍数 300
	            };
	        cornerstoneTools.magnify.setConfiguration(config);
			cornerstone.enable(element);
			cornerstoneTools.touchInput.enable(element);
			cornerstone.loadAndCacheImage(imageId).then(function(image) {
				
				cornerstone.displayImage(element, image);
				
				// Activate mouse clicks, mouse wheel and touch
			    cornerstoneTools.mouseInput.enable(element);
			    // 鼠标滚轮切换图像
			    // cornerstoneTools.mouseWheelInput.enable(element);
			    cornerstoneTools.touchInput.enable(element);
				
				// Enable all tools we want to use with this element
			    cornerstoneTools.wwwc.activate(element, 1); // ww/wc is the default tool for left mouse button
			    cornerstoneTools.pan.activate(element, 2); // pan is the default tool for middle mouse button
			    cornerstoneTools.zoom.activate(element, 4); // zoom is the default tool for right mouse button
			    cornerstoneTools.probe.enable(element);
			    cornerstoneTools.length.enable(element);
			    cornerstoneTools.ellipticalRoi.enable(element);
			    cornerstoneTools.rectangleRoi.enable(element);
			    cornerstoneTools.wwwcTouchDrag.activate(element);
			    cornerstoneTools.zoomTouchPinch.activate(element);
				
				// stack tools
				cornerstoneTools.addStackStateManager(element, [ 'playClip' ]);
				cornerstoneTools.addToolState(element, 'stack', stacks[0]);
				cornerstoneTools.stackScrollWheel.activate(element);
				cornerstoneTools.stackPrefetch.enable(element);
				
				setButtons($(".tools"));
//
			});

		});

	} catch(err) {
	    console.info("Error name: " + err.name + "");
	    console.info("Error message: " + err.message);
	}
	
}

// DICOM阅片
function resizeMain() {
	try {
		var height = $(window).height();
		$('#wrap').height(height - 50);
		$('.patient-img').height($('#wrap').height());
	} catch(err) {
	    console.info("Error name: " + err.name + "");
	    console.info("Error message: " + err.message);
	}
}

// DICOM阅片
function setButtons(elmTool) {

	
	try {
		var buttons = $(elmTool).find('button');
		forEachViewport(function(element) {
			// Tool button event handlers that set the new active tool
			// 窗宽/窗位
			$(buttons[0]).on('click touchstart', function() {

				disableAllTools();
				cornerstoneTools.wwwc.activate(element, 1);
				cornerstoneTools.wwwcTouchDrag.activate(element);
			})
			
			//固定窗宽、窗位视图（若增加 固定的窗宽窗位，只需要在 下列集合中增加对应的窗宽窗位，然后在页面上增加<li><span即可）
		    var lis = $("#wwawc").find("span");//获取下拉表里的选项集合
		    //加载对应的窗宽窗位
		    var fixationWWWS =['90.0_35.0','85.0_40.0','1600.0_450.0',
		    	'1600.0_550.0','300.0_40.0','350.0_35.0',
		    	'2000.0_450.0','4000.0_650.0','350.0_40.0',
		    	'2000.0_450.0','1000.0_-650.0','350.0_40.0',
		    	'1500.0_-500.0','200.0_50.0','350.0_40.0'];
		    //为固定窗宽、窗位 的 下拉选 的 每一个选项绑定点击和触摸事件
		    $.each(lis,function(index,value){
		    	$(value).on('click touchstart', function () {
					//模仿的负片功能复制的代码
					disableAllTools();
		            forEachViewport(function (element) {
		        		var wcww = fixationWWWS[index].split("_");//得到每个下拉选项 对应的 窗宽 窗位 数组
		    			var viewport = cornerstone.getViewport(element);//获取该DICOM图属性对象viewport
		    			viewport.voi.windowWidth =parseFloat(wcww[0]);//修改窗宽
		    			viewport.voi.windowCenter =parseFloat(wcww[1]);//修改窗位
		    			cornerstone.setViewport(element, viewport);//使用修改后的DICOM图属性对象
		            });
		        });
		    });
			
			// 负片
			$(buttons[2]).on('click touchstart', function() {
				disableAllTools();
				var viewport = cornerstone.getViewport(element);
				if (viewport.invert === true) {
					viewport.invert = false;
				} else {
					viewport.invert = true;
				}
				cornerstone.setViewport(element, viewport);
			});
			
			// 缩/放
			$(buttons[3]).on('click touchstart', function() {
				disableAllTools();
				cornerstoneTools.zoom.activate(element, 5); // 5 is right mouse
				// button and left mouse
				// button
				cornerstoneTools.zoomTouchDrag.activate(element);
			});
			
			// 放大镜
			$(buttons[4]).on('click touchstart', function() {
				disableAllTools();
				
				// Enable all tools we want to use with this element
	            cornerstoneTools.magnify.activate(element, 1);
	            cornerstoneTools.magnifyTouchDrag.activate(element);
			});
			
			// 移动
			$(buttons[5]).on('click touchstart', function() {
				disableAllTools();
				cornerstoneTools.pan.activate(element, 1);
				cornerstoneTools.panTouchDrag.activate(element);
			});
			
			
			// 重置
			$(buttons[6]).on('click touchstart', function() {
				disableAllTools();
				
				cornerstoneTools.wwwc.activate(element, 1);
				cornerstoneTools.wwwcTouchDrag.activate(element);
				
				forEachViewport(function (element) {
		            var enabledElement = cornerstone.getEnabledElement(element);
		            var canvas = enabledElement["canvas"];
		            var viewport = cornerstone.getDefaultViewport(canvas, enabledElement.image)
		            cornerstone.setViewport(element, viewport);
		        });
			});

			
			// 像素测量
			$(buttons[7]).on('click touchstart', function() {
				disableAllTools();
				
				cornerstoneTools.probe.activate(element, 1);
				cornerstoneTools.probeTouch.activate(element);
			});

			// 长度测量
			$(buttons[8]).on('click touchstart', function() {
				disableAllTools();
				cornerstoneTools.length.activate(element, 1);
				cornerstoneTools.lengthTouch.activate(element);	
			});
			
			// 角度测量
			$(buttons[9]).on('click touchstart', function() {
				disableAllTools();
				cornerstoneTools.angleTouch.activate(element);
				forEachViewport(function(element) {
					cornerstoneTools.simpleAngle.activate(element, 1);
				});
			});
			
			// 区域测量(圆形)
			$(buttons[10]).on('click touchstart', function() {
				disableAllTools();
				cornerstoneTools.ellipticalRoi.activate(element, 1);
				cornerstoneTools.ellipticalRoiTouch.activate(element);
			});
			
			// 区域测量(矩形)
			$(buttons[11]).on('click touchstart', function() {
				disableAllTools();
				cornerstoneTools.rectangleRoi.activate(element, 1);
				cornerstoneTools.rectangleRoiTouch.activate(element);
			});
			
			// 清除工具操作
			$(buttons[12]).on('click touchstart', function() {
				disableAllTools();
				forEachViewport(function (element) {
		        	var toolStateManager = cornerstoneTools.globalImageIdSpecificToolStateManager;
		            // Note that this only works on ImageId-specific tool state managers (for now)
		            toolStateManager.clear(element);
		            cornerstone.updateImage(element);
		        });
			});
			
			// 上下翻转
			$(buttons[13]).on('click touchstart', function() {
				disableAllTools();
				forEachViewport(function (element) {
		            var  viewport = cornerstone.getViewport(element);
		            viewport.vflip = !viewport.vflip;
		            cornerstone.setViewport(element, viewport);
		        });
			});
			
			// 左右翻转
			$(buttons[14]).on('click touchstart', function() {
				disableAllTools();
				 forEachViewport(function (element) {
			        var  viewport = cornerstone.getViewport(element);
			        viewport.hflip = !viewport.hflip;
			        cornerstone.setViewport(element, viewport);
			     });
			});
			
			// 90°旋转
			$(buttons[15]).on('click touchstart', function() {
				disableAllTools();
				forEachViewport(function (element) {
		            var  viewport = cornerstone.getViewport(element);
		            viewport.rotation +=90;
		            cornerstone.setViewport(element, viewport);
		        });
			});
			
			// 任意旋转
			$(buttons[16]).on('click touchstart', function() {
				disableAllTools();
				// Enable all tools we want to use with this element
		        cornerstoneTools.rotate.activate(element, 1);
		        cornerstoneTools.rotateTouchDrag.activate(element);
			});
			
			// 逐帧查看
			$(buttons[17]).on('click touchstart', function() {
				disableAllTools();
				cornerstoneTools.stackScroll.activate(element, 1);
				cornerstoneTools.stackScrollTouchDrag.activate(element);
			});
			
			// 重置
			$(buttons[18]).on('click touchstart', function() {
				disableAllTools();
				
				cornerstoneTools.wwwc.activate(element, 1);
				cornerstoneTools.wwwcTouchDrag.activate(element);
				
				forEachViewport(function (element) {
		            var enabledElement = cornerstone.getEnabledElement(element);
		            var canvas = enabledElement["canvas"];
		            var viewport = cornerstone.getDefaultViewport(canvas, enabledElement.image)
		            cornerstone.setViewport(element, viewport);
		        });
			});
			
			// 播放
			$(buttons[19]).on('click touchstart',function() {
				var playClipToolData = cornerstoneTools.getToolState(
						element, "playClip");
				if (playClipToolData && playClipToolData.data
						&& playClipToolData.data.length > 0) {
					return;
				}
				cornerstoneTools.playClip(element, 5);
			});	
			
			// 暂停
			$(buttons[20]).on('click touchstart',function() {
				cornerstoneTools.stopClip(element);
				var playClipToolData = cornerstoneTools.getToolState(
						element, "playClip");
				if (playClipToolData && playClipToolData.data
						&& playClipToolData.data.length > 0) {
					playClipToolData.data.shift();
				}
			});	
			
			// 文本标注
			$(buttons[21]).on('click touchstart',function() {
				cornerstoneTools.stopClip(element);
				// Enable all tools we want to use with this element
		        cornerstoneTools.arrowAnnotate.activate(element, 1);
		        cornerstoneTools.arrowAnnotateTouch.activate(element);
			});	

			$(buttons[0]).tooltip();
			
			$(buttons[2]).tooltip();
			$(buttons[3]).tooltip();
			$(buttons[4]).tooltip();
			$(buttons[5]).tooltip();
			$(buttons[6]).tooltip();
			$(buttons[7]).tooltip();
			$(buttons[8]).tooltip();
			$(buttons[9]).tooltip();
			$(buttons[10]).tooltip();
			$(buttons[11]).tooltip();
			$(buttons[12]).tooltip();
			$(buttons[13]).tooltip();
			$(buttons[14]).tooltip();
			$(buttons[15]).tooltip();
			$(buttons[16]).tooltip();
			$(buttons[17]).tooltip();
			$(buttons[18]).tooltip();
			$(buttons[19]).tooltip();
			$(buttons[20]).tooltip();
			$(buttons[21]).tooltip();

		});
		
	} catch(err) {
	    console.info("Error name: " + err.name + "");
	    console.info("Error message: " + err.message);
	}
}

// DICOM阅片
function disableAllTools() {
	try {
		forEachViewport(function(element) {
			cornerstoneTools.wwwc.disable(element);
			cornerstoneTools.pan.activate(element, 2); // 2 is middle mouse button
			cornerstoneTools.zoom.activate(element, 4); // 4 is right mouse button
			cornerstoneTools.probe.deactivate(element, 1);
			cornerstoneTools.length.deactivate(element, 1);
			cornerstoneTools.simpleAngle.deactivate(element, 1);
			cornerstoneTools.ellipticalRoi.deactivate(element, 1);
			cornerstoneTools.rectangleRoi.deactivate(element, 1);
			cornerstoneTools.stackScroll.deactivate(element, 1);
			cornerstoneTools.wwwcTouchDrag.deactivate(element);
			cornerstoneTools.zoomTouchDrag.deactivate(element);
			cornerstoneTools.panTouchDrag.deactivate(element);
			cornerstoneTools.stackScrollTouchDrag.deactivate(element);
			cornerstoneTools.rotate.disable(element);
			cornerstoneTools.magnify.disable(element);
			
			
	        cornerstoneTools.magnifyTouchDrag.disable(element);
			cornerstoneTools.rotateTouchDrag.disable(element);
			cornerstoneTools.panTouchDrag.deactivate(element);
	        cornerstoneTools.rotateTouchDrag.deactivate(element);
	        cornerstoneTools.rotateTouch.disable(element);
	        cornerstoneTools.ellipticalRoiTouch.deactivate(element);
	        cornerstoneTools.angleTouch.deactivate(element);
	        cornerstoneTools.rectangleRoiTouch.deactivate(element);
	        cornerstoneTools.lengthTouch.deactivate(element);
	        cornerstoneTools.probeTouch.deactivate(element);
	        cornerstoneTools.zoomTouchDrag.deactivate(element);
	        cornerstoneTools.wwwcTouchDrag.deactivate(element);
	        cornerstoneTools.stackScrollTouchDrag.deactivate(element);
			
		});
	} catch(err) {
	    console.info("Error name: " + err.name + "");
	    console.info("Error message: " + err.message);
	}
	
}

// DICOM阅片
function resizeStudyViewer() {
	
	try {
		var viewer = $("#wrap").find('.viewer')[0];
		var viewportWrapper = $(".viewportWrapper");
		var height = $(viewer).height();
		var width = $(viewer).width();

		viewportWrapper.width(width);
		viewportWrapper.height(height);

		forEachViewport(function(element) {
			cornerstone.resize(element, true);
		})
	} catch(err) {
	    console.info("Error name: " + err.name + "");
	    console.info("Error message: " + err.message);
	}
	
	
}

// DICOM阅片
function forEachViewport(callback) {
	var element = $('.viewport').get(0);
	callback(element);
}

// 文件加载进度
function loadDICOMProgressMade(){
	
//	$(cornerstone).on("CornerstoneImageLoadProgress",function(event,data){
//		
////		for(tag in event){
////			console.info("event["+tag+"]:"+event[tag]);
////		}
//		
//		$("#progressMade").html();
//		
//	});
	
}

function initHistoryImage(){
	
	try {
		
		var tLen = 0,
	    vNum = 4,
	    mNum = 1,
	    mTime = 500,
	    iShow = $(".show .itemshow ul"),
	    iItems = $(".show .itemshow ul li"),
	    mLen = iItems.eq(0).width() * mNum,
	    cLen = (iItems.length - vNum) * iItems.eq(0).width();

		iShow.css({width: iItems.length * iItems.eq(0).width() + 'px'});
		
		//上一张
		$(".show .prev").on({
			click: function () {
			    if (tLen < cLen) {
			        if ((cLen - tLen) > mLen) {
			            iShow.animate({left: "-=" + mLen + "px"}, mTime);
			            tLen += mLen;
			        } else {
			            iShow.animate({left: "-=" + (cLen - tLen) + "px"}, mTime);
			            tLen += (cLen - tLen);
			        }
			    }
			}
		});
		
		//下一张
		$(".show .next").on({
			click: function () {
			    if (tLen > 0) {
			        if (tLen > mLen) {
			            iShow.animate({left: "+=" + mLen + "px"}, mTime);
			            tLen -= mLen;
			        } else {
			            iShow.animate({left: "+=" + tLen + "px"}, mTime);
			            tLen = 0;
			        }
			    }
			}
		});
		
		$("a.image-history").fancybox({
	        'fitToView': false,
	        helpers : {
	            title : {
	                type : 'inside'
	            }
	        },
	        afterLoad : function() {
	            this.title = 'Image ' + (this.index + 1) + ' of ' + this.group.length + (this.title ? ' - ' + this.title : '');
	        }
	    });
		
	} catch(err) {
	    console.info("Error name: " + err.name + "");
	    console.info("Error message: " + err.message);
	}
	
}

// 设置文本标注
function getTextCallback(doneChangingTextCallback) {
	try {
		  var annotationDialog  = $('.annotationDialog');
		    var getTextInput = annotationDialog .find('.annotationTextInput');
		    var confirm = annotationDialog .find('.annotationDialogConfirm');

		    annotationDialog .get(0).showModal();

		    confirm.off('click');
		    confirm.on('click', function() {
		        closeHandler();
		    });

		    annotationDialog .off("keydown");
		    annotationDialog .on('keydown', keyPressHandler);

		    function keyPressHandler(e) {
		        // If Enter is pressed, close the dialog
		        if (e.which === 13) {
		            closeHandler();
		        }
		    }

		    function closeHandler() {
		        annotationDialog .get(0).close();
		        doneChangingTextCallback(getTextInput.val());
		        // Reset the text value
		        getTextInput.val("");
		    }
		    
	} catch(err) {
	    console.info("Error name: " + err.name + "");
	    console.info("Error message: " + err.message);
	}
}

function changeTextCallback(data, eventData, doneChangingTextCallback) {
	try {
	    var relabelDialog = $('.relabelDialog');
	    var getTextInput = relabelDialog.find('.annotationTextInput');
	    var confirm = relabelDialog.find('.relabelConfirm');
	    var remove = relabelDialog.find('.relabelRemove');

	    getTextInput.val(data.annotationText);
	    relabelDialog.get(0).showModal();

	    confirm.off('click');
	    confirm.on('click', function() {
	        relabelDialog.get(0).close();
	        doneChangingTextCallback(data, getTextInput.val());
	    });

	    // If the remove button is clicked, delete this marker
	    remove.off('click');
	    remove.on('click', function() {
	        relabelDialog.get(0).close();
	        doneChangingTextCallback(data, undefined, true);
	    });

	    relabelDialog.off("keydown");
	    relabelDialog.on('keydown', keyPressHandler);

	    function keyPressHandler(e) {
	        // If Enter is pressed, close the dialog
	        if (e.which === 13) {
	            closeHandler();
	        }
	    }

	    function closeHandler() {
	        relabelDialog.get(0).close();
	        doneChangingTextCallback(data, getTextInput.val());
	        // Reset the text value
	        getTextInput.val("");
	    }
	} catch(err) {
	    console.info("Error name: " + err.name + "");
	    console.info("Error message: " + err.message);
	}
}

/*******************************************************************************
 * 
 * @returns 病史图像
 */
// 病史图像

// 切换图像
function switchImg() {
	try {
		var index = 0; // 记录当前第一张图像显示的位置

		var arry = $(".history>ul").find("li");

		$('.og_next').click(function() {

			var aryImage = $(".history>ul").find("img");

			var arySrc = [];

			var total = arry.length; // 所有的图像数量

			$(arry[index]).css("display", "none");

			index = index + 1;

			if (index >= arry.length - 4) {
				index = arry.length - 4;
			}

		});

		$('.og_prev').click(function() {

			$(arry[index]).css("display", "block");

			index = index - 1;

			if (index <= 0) {
				index = 0;
			}

		});
		
	} catch(err) {
	    console.info("Error name: " + err.name + "");
	    console.info("Error message: " + err.message);
	}
	

}

// 知识库 & 标签库 初版本 现在没有用
/**
 * 
 * @returns
 */
function initPopover() {
	try {
		$("#example").popover();

		$("#btntext")
				.popover(
						{
							placement : "right",
							trigger : "foucs",
							title : "胸部",
							html : true,
							content : "<div class=''>"
									+ "<h4>影像所见</h4>"
									+ "<span id='gettext'>两侧肺纹理增粗，气管及纵隔居中，心影及双侧肺门大小结构未见异常。双侧膈面光整，双侧肋膈角锐利。</span>"
									+ "</div>" + "<div class=''>" + "<h4>诊断结果</h4>"
									+ "<span id='resulttetx'>两肺、心膈未见明显异常征象。</span>"
									+ "</div>"
						});

		$("#btn1")
				.popover(
						{
							placement : "right",
							trigger : "foucs",
							title : "胸部",
							html : true,
							content : "<div class=''>"
									+ "<h4>影像所见</h4>"
									+ "<span id='gettext'>轮廓对称，骨质结构完整。两肺文理分布规则，走形自然，肺叶清晰，</span>"
									+ "</div>" + "<div class=''>" + "<h4>诊断结果</h4>"
									+ "<span id='resulttetx'>心，肺，隔未见明确异常</span>"
									+ "</div>"
						});

		$("#btn2")
				.popover(
						{
							placement : "right",
							trigger : "foucs",
							title : "胸部",
							html : true,
							content : "<div class=''>"
									+ "<h4>影像所见</h4>"
									+ "<span id='gettext'>两侧肺纹理增粗，气管及纵隔居中，心影及双侧肺门大小结构未见异常。双侧膈面光整，双侧肋膈角锐利。</span>"
									+ "</div>" + "<div class=''>" + "<h4>诊断结果</h4>"
									+ "<span id='resulttetx'>两肺、心膈未见明显异常征象。</span>"
									+ "</div>"
						});

	} catch(err) {
	    console.info("Error name: " + err.name + "");
	    console.info("Error message: " + err.message);
	}
	
}

function gettext(obj) {
	try {
		var cot = document.getElementById("gettext");
		var cou = document.getElementById("resulttetx");
		document.getElementById("inputimgval").value = "";
		document.getElementById("inputvalre").value = "";
		document.getElementById("inputimgval").value = cot.innerText;
		document.getElementById("inputvalre").value = cou.innerText;
		$('#myModal').modal('hide');
	} catch(err) {
	    console.info("Error name: " + err.name + "");
	    console.info("Error message: " + err.message);
	}
	
}

/**
 * 原版知识库 现在没用
 */
function knowledgeBase() {

	
	try {
		// 常用配置参数：

		// 知识库点击button将知识库内容赋给文本域
		function loadKnowledgeBase() {
			var tagName = "";
			var getName = "";
			var setResult = "";
			$.ajax({
				url : 'data/yuedata.json',
				type : 'get',
				dataType : 'json',
				cache : false,
				error : erryFunction,
				success : function(obj) {
					var rows = $(obj.rows);
					rows.each(function(index, value) {
						tagName = value.tagName;
						getName = value.getName;
						setResult = value.setResult;

						// 注意不要换行
						$("#myModal .list-group .list-group-item .nav.head li.page-header:eq(0)").after(
										$("<li/>").append("<button ondblclick='gettext(this);' type='button' class='btn btn-default btn-cont'><strong class='pull-left'>"+ tagName+ "</strong><strong class='pull-right'>正常</strong></button>"));

					});				
				}
			});		
			
		}

		function erryFunction() {
			alert("没有数据");
		}
		
	} catch(err) {
	    console.info("Error name: " + err.name + "");
	    console.info("Error message: " + err.message);
	}
}

/*******************************************************************************
 * 知识库
 * 
 * @returns
 */
// 初始化检查部位
function initBodyParts() {
	try {
		$.ajax({
			url : path+"/doctor/queryBodyPartsList.action",
			dataType : "json",
			method : "get"
		});
	} catch(err) {
	    console.info("Error name: " + err.name + "");
	    console.info("Error message: " + err.message);
	}
}

// 初始化诊断知识库
function initCase_Librarys() {
	try {
		var obj;

		$.ajax({
			url : "",
			dataType : "json",
			method : "get"
		}).done(function(data) {
			obj = data;
		});
		// 返回函数值
		return obj;
	} catch(err) {
	    console.info("Error name: " + err.name + "");
	    console.info("Error message: " + err.message);
	}
	
	
}

// 检查部位
var oBodyParts = [{"alias":"胸部","id":1},{"alias":"腹部","id":2},{"alias":"脊柱","id":3},{"alias":"头颈部","id":4},{"alias":"四肢","id":5},{"alias":"造影","id":6},{"alias":"其它","id":7}];

// 诊断知识库
var Case_Librarys = [];


// 初始化诊断知识库
function initCaseLibraryPanel() {


	try {
		var url = path+"/html/doctor/data/";
		
		switch(true){
			
			case "DR" === imageType:
				url += "study_library_"+imageType+".json";
			break;
			case "DX" === imageType:
				url += "study_library_DR.json";
				break;
			case "CR" === imageType:
				url += "study_library_"+imageType+".json";
				break;
			case "MR" === imageType:
				url += "study_library_"+imageType+".json";
				break;
			case "CT" === imageType:
				url += "study_library_"+imageType+".json";
				break;
			case "DSA" === imageType:
				url += "study_library_"+imageType+".json";
				break;
			case "NM" === imageType:
				url += "study_library_"+imageType+".json";
				break;
			default:
				url += "study_library_DR.json";
				break;
		}		
		
		
		$.ajax({
			url : url,
			dataType : "json",
			method : "post"
		}).done(function(data) { 
			
	 		oBodyParts = data; 
	 		
	 		// 内容模板
			var oPanel = $("#myModal .modal-body .list-group");

			for (var i = 0; i < oBodyParts.length; i++) {
				
				// 模板数数量
				var oCount = $("<span/>").html(oBodyParts[i].StudyLibrary.length).addClass("pull-right");

				// 每一项模板组标题项
				var caseLi = $("<li/>").addClass("list-group-item");

				// 模板组标题
				var caseTitle = $("<a/>").addClass("dropdown-toggle")
										 .attr("data-toggle", "collapse")
										 .attr("id","dropdownMenu" + getRound())
										 .attr("data-target",".head" + getRound())
										 .attr("href","javascript:void(0)");

				caseTitle.html(oBodyParts[i].alias + oCount[0].outerHTML);
				caseTitle.attr("isLoad", false);
				caseTitle.data("id", oBodyParts[i].id);
				
				// 添加到每一项中
				caseLi.append(caseTitle);
				
				// 身体部位的ID
				var bodyPartId = $(caseTitle).data("id");

				// 数据目标的类样式名称
				var oCls = caseTitle.attr("data-target").substr(1);
				
				var case_Librarys = oBodyParts[i].StudyLibrary
				
				if (case_Librarys && case_Librarys.length > 0) {

					var oList = $("<ul/>").addClass("nav collapse").addClass(oCls).attr("role", "menu");
					
					if (!oList.find("li")[0]) {
				
						var oListItemSuf = $("<li/>").addClass("page-header suf").html("正常");
						var oListItemErr = $("<li/>").addClass("page-header err").html("异常");
						oList.append(oListItemSuf).append(oListItemErr);

						for (var j = 0; j < case_Librarys.length; j++) {

							// 每一项病理模板
							var oListItem = $("<li/>");
							// 模板标题
							var oListItemContent = $("<a/>")
									.addClass("studyLibraryItem")
									.attr("study-library-id",case_Librarys[j].id)
									.attr("type", "button")
									.attr("data-toggle", "collapse")
									.attr("data-target","#StudyLibrary"+ getRound())
									.attr("aria-expanded", "true")
									.attr("aria-controls", "demo")
									.html("<strong class='pull-left'>"+ case_Librarys[j].alias+ "</strong><strong class='pull-right'>"+case_Librarys[j].templateType+"</strong>");
							
							oListItemContent.append("<div class='clearfix'></div>");
							oListItem.append(oListItemContent);
							
							// 模板内容
							var oListItemCase = $("<div/>").addClass("collapse").attr("id",oListItemContent.attr("data-target").substr(1));

							oListItem.append(oListItemCase);
							if (case_Librarys[j].templateType.trim() === "正常") {
								oListItemErr.before(oListItem);
							}else if (case_Librarys[j].templateType.trim() === "异常") {
								oList.append(oListItem);
							}

							oListItem = null;
						}
						
					}
					// 在标题后添加所有的模板
					caseTitle.after(oList);
				}
				// 添加到列表中
				oPanel.append(caseLi);

				caseTitle = null;
				caseLi = null;

			}
			
			$("#myModal .list-group-item a.studyLibraryItem").bind("click",loadCaseTemplate);

		});
	} catch(err) {
	    console.info("Error name: " + err.name + "");
	    console.info("Error message: " + err.message);
	}
}

function loadCaseTemplate(){


		try {
			 var dataTarget = $(this).attr("data-target");
			 var studyLibraryId = $(this).attr("study-library-id");
			 
			 console.info(dataTarget);
			 console.info(studyLibraryId);
			 $.getJSON(path+"/doctor/findStudyLibrary.action",{"studyLibraryId":studyLibraryId},function(data){
				 
				 $(dataTarget).html("<div class='item-case'><strong>影像所见</strong><br /><p><span class='image-content'>"
							+ data.imageDetails
							+ "</span></p>"
							+ "<strong>诊断结果</strong><br /><span class='diagnostic-result'>"
							+ data.content
							+ "</span><button class='select-studylibrary'>确认</button></div>");
				 
				 $(dataTarget).find("button.select-studylibrary").attr("studyLibraryId",studyLibraryId);
				 
				 $("button.select-studylibrary").on("click", getContent);
				 
			 });
			 
			 
			/*for(var tag in e){
				
				console.info(e[tag]);
				
			}*/
			 
		} catch(err) {
		    console.info("Error name: " + err.name + "");
		    console.info("Error message: " + err.message);
		}
	 
}

/**
 * 获取知识库模板内容
 * 
 * @param event
 * @returns
 */
function getContent(event) {
	try {
		var oDiv = $(event.target).parent("div");
		var oBtn = $(event.target).closest("div.collapse.in").prev()[0];

		$("#myModal").find("a.selected").removeClass("selected");
		var $btn = $(oBtn).addClass("selected");
		
		$("#studyLibraryId").val($btn.attr("study-library-id"));
		$("#imageContent").val($(oDiv).find("span.image-content").html());
		$("#diagnosticResult").val($(oDiv).find("span.diagnostic-result").html());

		$('#myModal').modal('hide');
	} catch(err) {
	    console.info("Error name: " + err.name + "");
	    console.info("Error message: " + err.message);
	}
	

}

function getRound() {
	var max = 9999999;
	return parseInt(Math.random() * max, 10) + 1;
}
/** *************************************************************************************** */
/*
 * 疾病标签库
 */

if (typeof tagLibrary === 'undefined') {

	var tagLibrary = {

        testBtn: null,
        tagItems: [],
        tagLibraryItem: []

    };

}

// 声明全局变量保存，选择的标签

(function ($, tagLibrary) {

    /**
     * 初始化疾病标签库
     *
     */
    function initTagLibraryPanel() {
    	try {
    		
            var deferred = getTagLibrary();
            
            deferred.done(function(tagLibraries){
            	
            	console.info(tagLibraries);
            	
            	// 获取显示标签内容的面板
                var oPanel = $("#tag-library .modal-body .list-group");
            	
                // 循环添加每一项标签组
                for (var i = 0; i < tagLibraries.length; i++) {

                	// 统计标签组的数量
                    var oCount = $("<span/>").html(tagLibraries[i].imageTags.length).addClass("pull-right");
                	
                    // 标签组
                    var caseLi = $("<li/>").addClass("list-group-item");

                    // 标题
                    var caseTitle = $("<a/>").addClass("dropdown-toggle")
                        .attr("data-toggle", "collapse")
                        .attr("id", "dropdownMenu" + getRound())
                        .attr("data-target", ".head" + getRound())
                        .attr("href", "javascript:void(0)");

                    // 填充标签组内容
                    caseTitle.html(tagLibraries[i].alias + oCount[0].outerHTML);

                    // 标识标签组下的标签是否已经加载完毕
                    caseTitle.attr("isLoad", false);

                    // 存储标签组的编号
                    caseTitle.data("id", tagLibraries[i].id);

                    // 添加标签组的标题
                    caseLi.append(caseTitle);

                    // 获取标签组标题的 data-target 属性(用于指定下拉菜单操作对应的内容)
                    var oCls = caseTitle.attr("data-target").substr(1);

                    // 创建标签组下标签内容的容器 , 并且绑定(下拉菜单按钮的事件) .addClass(oCls)
                    var oList = $("<ul/>").addClass("nav collapse").addClass(oCls).attr("role", "menu");

                    var oLi = $("<li/>").attr("tagGroup", tagLibraries[i].id);
                    
                    for(var j = 0; j<tagLibraries[i].imageTags.length; j++){
                    	
                    	var item = tagLibraries[i].imageTags[j];
                    	
                    	if(item.alias === "正常"){
                    		
                    		var btn = $("<button/>").addClass(" btn  btn-default ").attr("type", "button").attr("dataTag", item.id).attr("id", "tag" + item.id).html(item.alias);
                    		
                    		$(btn).bind("click",tagLibrary.clearOther);
                    		
                    		oLi.append(btn);
                    		
                    	}else{
                    		oLi.append($("<button/>").addClass(" btn  btn-default ").attr("type", "button").attr("dataTag", item.id).attr("id", "tag" + item.id).html(item.alias));
                    	}
                    }
                    
                    oList.append(oLi);
                    
                    // 添加到标签组集合中
                    caseLi.append(oList);

                    // 添加到标签内容的面板
                    oPanel.append(caseLi);

                    oList = null;
                    caseTitle = null;
                    caseLi = null;

                }
                
                console.info("疾病标签面板初始化完成！");
                console.info("开始进行按钮事件的绑定！");
        	    $("#tag-library .modal-body .list-group .list-group-item").on("click","button",tagLibrary.selectBtn);
        	    console.info("绑定完成！");
            	
            }).fail(function(){
            	
            	alert("标签库加载失败，请刷新后重试.");
            });
            
            
    	} catch(err) {
    	    console.info("Error name: " + err.name + "");
    	    console.info("Error message: " + err.message);
    	}
        
    }


    /**
     * 请求指定标签组中的标签(被 forEachTagGroup() 调用)
     *
     * @param elm
     * @param size
     */
    function getTagLibrary() {
    	
    	try {
    		
    		var deferred = $.Deferred();
    		
    		$.ajax({
                /*url: path+"/html/doctor/data/tagLibrary.json",*/
                url: path+"/doctor/queryTagLibrary.action",
                dataType: "json",
                method: "Get",
                success: function (data) {
                	deferred.resolve(data);
                },
                error: function (e) {
                	deferred.reject();
                    console.error(e.message);
                }
            });
    		
    		return deferred.promise();
    		
    	} catch(err) {
    	    console.info("Error name: " + err.name + "");
    	    console.info("Error message: " + err.message);
    	}

    }

    /**
     * 返回每一个标签组
     *
     * @param callBack
     */
    function forEachTagGroup(callBack) {
    	try {
    		 var tagGroup = $("#tag-library .modal-body .list-group").children("li");

    	        $.each(tagGroup, function (index, val) {

    	            try {
    	                callBack(val, index + 1);
    	            } catch (e) {
    	                console.info(e);
    	            }

    	        });
    	} catch(err) {
    	    console.info("Error name: " + err.name + "");
    	    console.info("Error message: " + err.message);
    	}
        
    }


    /**
     * 添加按钮选中样式
     *
     * @param event
     */
    function selectBtn(event) {
    	try {
    		 var oBtn = $(event.target);
    	        if (oBtn) {
    	            if (oBtn.hasClass("tag-selected")) {
    	                oBtn.removeClass("tag-selected");
    	            } else {
    	                oBtn.addClass("tag-selected");
    	            }
    	        }
    	} catch(err) {
    	    console.info("Error name: " + err.name + "");
    	    console.info("Error message: " + err.message);
    	}
    }


    /**
     * 标签模板
     *
     * @returns {*|jQuery}
     */
    function tagTemplate() {
    	try {
    		// 创建标签对象
            var oTag = $("<div/>").addClass(" alert alert-warning alert-dismissible ").attr("role", "alert");

            // 创建删除标签的按钮
            var oBtn = $("<button><span aria-hidden='true'>&times;</span><span class='sr-only'>Close</span></button>").addClass("close").attr("type", "button").attr("data-dismiss", "alert");

            // 创建标签显示的内容
            var oSpan = $("<span></span>").attr("id", "tag" + getRound());
            
            // 添加到标签对象中
            oTag.append(oBtn).append(oSpan);

            // 返回函数值
            return oTag;
    	} catch(err) {
    	    console.info("Error name: " + err.name + "");
    	    console.info("Error message: " + err.message);
    	}
        

    }


    /**
     * 删除已选择标签时，取消标签库中模板的选择状态
     *
     */
    function bindRemoveTag() {
    	try {
    		 // 获取标签库中所有选中的标签
            tagLibrary.getTag().then(function (obj) {
            	
                var btns = obj.find("button");
                
                $.each(btns, function (index, val) {

                    // 绑定控件事件，完成数据移除(div - Tag -> btn.close)
                    $(val).bind("click", {"btn": $(val)}, removeTag);

                });

            });

            $('#tag-library').modal("hide");
    	} catch(err) {
    	    console.info("Error name: " + err.name + "");
    	    console.info("Error message: " + err.message);
    	}
        
    }

    /**
     * 获取所有的选中的标签 & 存放标签的容器 调用 addTag 将标签添加到页面中
     *
     * @returns {*}
     */
    function getTag() {
    	try {
    		// 使用异步任务
            var deferred = $.Deferred();

            // 获取所有选中的标签
            var oBtns = $("#resure").closest(".modal-body").find(".panel").find("button.tag-selected");

            // 获取添加标签的标签组
            var oLi = $("div.tag .tag-library-content");

			// 过滤正常函数
			oBtns = tagLibrary.filterNormarlBtn(oBtns);

            if (!tagLibrary.testBtn) { // 第一次使用未选择按钮

                tagLibrary.testBtn = oBtns;

            } else if (tagLibrary.testBtn.length > 0) {

                $.each(tagLibrary.testBtn, function (index, val) {

                    var verifyResult = true;

                    for (var i = 0; i < oBtns.length; i++) {

                        // 一个标签循环 100 次有一次存在就不删除
                        // 有一次为真就退出循环
                        if ($(val).is($(oBtns[i]))) { // 如果标签已存在

                            verifyResult = false; // 不执行删除
                            break;
                        }

                    }

                    if (verifyResult) {
                        $("#TagID" + $(val).attr("datatag")).find("button").trigger("click");
                    }

                });

            }

            // 存储本次选择的标签
            tagLibrary.testBtn = oBtns;

            // 将本次选择的标签添加到页面中
            $.each(oBtns, function (index, val) {

                val = $(val);

                // 1、获取标签标识
                var dataTag = val.attr("dataTag");

                // 验证标签是否已存在
                var verifyResult = verifyTagLibrary(val);

                if (verifyResult) { // 如果选择的标签不存在

                    // 存储选择标签的标识
                    tagLibrary.tagLibraryItem.push(dataTag);

					if(!tagLibrary.verifyTagExitsInHtml("TagID"+dataTag)){
						// 将标签保存到页面
						tagLibrary.addTag(oLi, val, tagTemplate());
					}
                }

            });

            // 保存当前的标签的标签组
            deferred.resolve(oLi);
            
            // 返回函数值
            return deferred.promise();
    	} catch(err) {
    	    console.info("Error name: " + err.name + "");
    	    console.info("Error message: " + err.message);
    	}
        

    }

    /**
     * 保存标签到页面
     *
     * @param oContent 容器
     * @param oBtn 选择的标签内容
     * @param oTag 标签
     * @returns {string}
     */
    function addTag(oContent, oBtn, oTag) {
    	try {
    		if (oBtn === undefined || oTag === undefined) {
                return "请重新选择标签。";
            }


            var dataId = oBtn.attr("id");       // 标签按钮的 ID
            var id = oBtn.attr("dataTag");      // 标签编号
            var content = oBtn.attr("content"); // 标签内容
            var alias = oBtn.html();            // 标签名
            
            // name:diseaseLabel
            var oInput = $("#standardInput").clone().removeAttr("id").attr("name","tagLibrary").attr("type","text").val(id);
            oInput.hide();
            
            // 设置标签内容
            oTag.attr("id", "TagID" + id);      // 设置标签的 ID
            oTag.attr("dataId", dataId);        // 保存标签按钮的 ID
            oTag.attr("title", content);        // 保存标签的内容
            oTag.children("span").html(alias);  // 保存标签的标题
            oTag.append(oInput);                // input 输入框
            oTag.attr("dataTag", id);           // 保存标签的编号

            // 添加到页面中
            oContent.append(oTag);
    	} catch(err) {
    	    console.info("Error name: " + err.name + "");
    	    console.info("Error message: " + err.message);
    	}
        
    }


    /**
     * 验证标签
     *
     * @param oBtn 选择的标签项
     * @returns {boolean}
     */
    function verifyTagLibrary(oBtn) {
    	try {
    		var verify = true;

            console.info(tagLibrary.tagLibraryItem);

            // 循环标签项数组
            $.each(tagLibrary.tagLibraryItem, function (index, val) {

                // 判断选中标签是否已被选择
				if (val === $(oBtn).attr("dataTag") && tagLibrary.verifyTagExitsInHtml("TagID"+$(oBtn).attr("dataTag"))) {
					verify = false; // 标签已被选择返回 false
				}
            });

            // 返回函数值
            return verify;
    	} catch(err) {
    	    console.info("Error name: " + err.name + "");
    	    console.info("Error message: " + err.message);
    	}

    }

	/**
	 * 验证页面上是否已经存在该标签
	 *
	 * @param oId
	 * @returns {boolean} true 已存在， false 不存在
	 */
	function verifyTagExitsInHtml(oId){

		if(oId.split("")[0] === "#"){
			console.info(($(oId).length > 0));
			return ($(oId).length > 0);
		}else{
			return ($("#"+oId).length > 0);
			console.info(($("#"+oId).length > 0));
		}

	}


	function filterNormarlBtn(btns){

		if(btns){

			if(btns.length > 1){

				$.each(btns,function(index,val){

					if($(val).html() === "正常"){
						btns.splice(index,1);
						$(val).removeClass("tag-selected");
					}

				});

			}else if(btns.length == 1){

				if($(btns[0]).html() === "正常"){

					var oTags = $("div.tag .tag-library-content").children("div.alert");

					if(oTags.length > 0){

						$.each(oTags,function(index,val){
							$(val).children("button").trigger("click");
						});
					}
				}
			}

			var oTags = $("div.tag .tag-library-content").children("div.alert");

			if(oTags.length > 0){

				$.each(oTags,function(index,val){

					if($(val).children("span").prop("innerHTML") === "正常"){
						$(val).empty().remove();
					}
				});
			}
		}

		return btns;
	}

    /**
     * 删除指定的标签
     *
     * @param event
     */
    function removeTag(event) {
    	try {
    		// 标签
            var oAlert = $(event.data.btn).parent();

            // 获得标签的编号
            var dataTag = oAlert.attr("dataTag");

            // 获得标签标题
            var oSpan = oAlert.children("span");

            // 移除标签在标签组中选中的状态
            removeTagState(oAlert.attr("dataId"));

            console.info("正在准备删除的标签为：" + oSpan.html());

            if (dataTag === null || dataTag === undefined) {// 为空退出操作
                console.error("提示框标签未知。");
                return;
            }

            // 在已选择的标签列表中循环移除指定的标签项
            for (var i = 0; i < tagLibrary.tagLibraryItem.length; i++) {

                // 根据标签的编号进行判断
                if (dataTag === tagLibrary.tagLibraryItem[i]) {

                    // 移除指定的标签项
                    tagLibrary.tagLibraryItem.splice(i, 1);

                    break;
                }
            }
            
    	} catch(err) {
    	    console.info("Error name: " + err.name + "");
    	    console.info("Error message: " + err.message);
    	}
        

    }

    /**
     * 移除标签的选中状态
     *
     * @param dataId 标签编号
     */
    function removeTagState(dataId) {

        // 根据标签的 ID , 找到标签的jQuery对象 , 移除标签选中样式
        $("#" + dataId).removeClass("tag-selected");
    }

    /**
     * 清除所有选择的标签
     * 
     * @returns
     */
    function clearAll(){
    	
    	// 2 部分操作
    	//   1) 去除所有标签库中已选择的标签(btn)的样式
    	//   2) 去除所有在页面中已有的标签元素
    	
    	// 1)
    	$("#tag-library .modal-body .list-group button").removeClass("tag-selected");
    	tagLibrary.tagLibraryItem = []; // 置空已选择的标签
    	
    	// 2)
    	$(".tag .tag-library-content").html("");
    	
    }
    
    function clearOther(event){
    	
    	var btn = event.target;
    	
    	var cls = $(btn).attr("class");
    	
    	var btns = $("#tag-library .modal-body .list-group button.tag-selected");
    	
    	if(!$(btn).hasClass("tag-selected")){
    		$(btns).removeClass("tag-selected");
    	}
    	
    	$(btn).attr("class",cls);
    }

    tagLibrary.initTagLibraryPanel = initTagLibraryPanel;
    tagLibrary.forEachTagGroup = forEachTagGroup;
    tagLibrary.getTagLibrary = getTagLibrary;
    tagLibrary.getTag = getTag;
    tagLibrary.addTag = addTag;
    tagLibrary.tagTemplate = tagTemplate;
    tagLibrary.selectBtn = selectBtn;
    tagLibrary.bindRemoveTag = bindRemoveTag;
    tagLibrary.verifyTagLibrary = verifyTagLibrary;
	tagLibrary.verifyTagExitsInHtml = verifyTagExitsInHtml;
	tagLibrary.filterNormarlBtn = filterNormarlBtn;
    tagLibrary.removeTag = removeTag;
    tagLibrary.clearAll = clearAll;
    tagLibrary.clearOther = clearOther;
    tagLibrary.removeTagState = removeTagState;


})($, tagLibrary);

$(function () {

	try{
		
		tagLibrary.initTagLibraryPanel();

	    // 绑定保存标签
	    $("#resure").bind("click", tagLibrary.bindRemoveTag);

	    // 绑定清除按钮
	    $("#sure").bind("click", tagLibrary.clearAll);

	    tagLibrary.forEachTagGroup(tagLibrary.getTagLibrary);

	    $("#tag-library .modal-body .list-group .list-group-item").on("click","button",tagLibrary.selectBtn);

		
	}catch(e){
		console.log(e.message);
	}
	
});

/**
 * 更新诊断记录
 * 
 * @param btn
 * @returns
 */
function updateDiagnostic(btn){
	if(!iAndDRIsNull()){
		return false;
	}
	try {
		// 请求路径
		var diagnosticUrl = btn.attr("url");
		
		// 诊断记录编号
		var diagnosticId = $("#diagnosticId").val();
		
		// 检查记录编号
		var studyId = $("#studyId").val();
		
		// 影像所见
		var imageContent = $("#imageContent").val();
		
		// 诊断结果
		var diagnosticResult = $("#diagnosticResult").val();
		
		// 诊断模板编号
		var studyLibraryId = $("#studyLibraryId").val();
		
		var tagLibraries = [];

		var tags = $("input[name='tagLibrary']");

		$.each(tags,function(index,val){

			if(parseInt($(val).val()) != NaN && parseInt($(val).val())>0){
				tagLibraries.push(parseInt($(val).val()));
			}

		});

		console.info(tagLibraries);
		
		// 请求诊断路径
		console.info(diagnosticUrl);
		
		// 诊断记录编号
		console.info(diagnosticId);
		
		// 检查记录编号
		console.info(studyId);
		
//		console.info(diagnosticResult[0].outerHTML);
	//	
//		console.info(imageContent);
		
		$.ajax({
			
			url:diagnosticUrl,
			method:"post",
			traditional:true,
			data:{  "diagnosticId":diagnosticId,
					"studyId":studyId,
					"imageContent":imageContent,
					"diagnosticResult":diagnosticResult,
					"studyLibraryId":studyLibraryId,
					"tagLibrary":tagLibraries},
		}).done(function(data){
			
			var lastDiagnosticResult = $("div#historyDiagnostic>table tr:last-child");
			
		});
		alert("暂存成功！！");
	} catch(err) {
		alert("暂存失败！！");
	    console.info("Error name: " + err.name + "");
	    console.info("Error message: " + err.message);
	}
	
}


/**
 * 标签库
 * 
 * @returns
 */
function getaa() {
	var a = document.getElementById('#jaa');
	document.getElementById('#sp1').value = a.innerText;
}
/*function tagLibrary() {
	// 点击疾病标签库把标签显示在span上面

}*/


function check(obj){
	
	alert(a)
	
	return false;
	
}

/**
 * 判断字符串是否为空，或者全是空白字符
 * @param v	字符串
 * @returns {Boolean}
 */
function validateTxt(v){  
        if(v)  
        {  
            v=$.trim(v);  
            if(v==''||v==null)  
            {  
                return false;  
            }  
        }else{  
            return false;  
        }  
        return true;  
} 


/**
 * 判断传入的对象的标签体中是否为空字符串
 * @param o jq对象
 * @returns {Boolean}
 */
function labelIsNull($o){
	var v = $o.val();
	return validateTxt(v);
}

/**
 * 判断影像所见与诊断意见是否为空，若不为空再判断内容是否超过了1000个字
 */
function iAndDRIsNull(){
	/*imageContent 影像所见ID*/
	/*diagnosticResult 诊断意见ID*/
	/*获取jquery对象*/
	var $imageContent = $("#imageContent");
	var $diagnosticResult = $("#diagnosticResult");
	/*判断传入的对象的标签体中是否为空字符串，为空值为false，不为空为true*/
	var imageContent = labelIsNull($imageContent);
	var diagnosticResult = labelIsNull($diagnosticResult);
	/*判断“影像所见”*/
	if(!imageContent){
		alert("影像所见不能为空，请您添加您的影像所见！！");
		return imageContent;
	}else{
		if($imageContent.val().length>1000){
			alert("影像所见的内容超过了1000个字符，请您简化您的语言！！");
			return false;
		}
	}
	/*判断“诊断意见”*/
	if(!diagnosticResult){
		alert("诊断意见不能为空，请您添加您的诊断意见！！");
		return diagnosticResult;
	}else{
		if($diagnosticResult.val().length>1000){
			alert("诊断意见的内容超过了1000个字符，请您简化您的语言！！");
			return false;
		}
	}
	
	/* 判断  “是否选择疾病标签”  */
	try{
		
		var tagLibraries = [];

		var tags = $("input[name='tagLibrary']");

		$.each(tags,function(index,val){

			if(parseInt($(val).val()) != NaN && parseInt($(val).val())>0){
				tagLibraries.push(parseInt($(val).val()));
			}

		});
		
		if(tagLibraries.length  < 1){
			alert("请选择在选择一项疾病标签后，再完成诊断！！");
			$("#tagdisease").trigger("click");
			return false;
		}
		
	}catch(err){
		console.info("Error name: " + err.name);
	    console.info("Error message: " + err.message);
	}
	
	return true;
}

