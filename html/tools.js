setInterval(function(){
	var fileName = document.title;
	var content = document.querySelector("textarea").value;
	saveFile(fileName,content);
},1000);
window.Time = {
	GetTimestamp: function() {
		return Math.round(new Date().getTime() / 1000);
	},
	GetTimeString: function(timestamp) {
		var timestamp = new Date(timestamp * 1000);
		return timestamp.toLocaleString();
	}
};
function saveFile(fileName, content) {
	if ("undefined" == localStorage) {
		return "LocalStorage not support";
	}
	if (fileName == null || content == null) {
		return "param wrong";
	}
	var filename = Time.GetTimestamp() + "$" + fileName;
	for (varname in localStorage) {
		if (varname.split("$")[0].length == 10 && varname.split("$")[1] == fileName) {
			filename = varname.split("$")[0] + "$" + fileName;
		}
	}
	localStorage.setItem(filename, content);
	return "OK";
}

function save(filename, content){
	localStorage.setItem(filename,content);
}

function load(filename){
	$(document.querySelector("textarea")).val(localStorage.getItem(filename));
	//autosize.update(document.querySelector("textarea"));
	var evt = document.createEvent('Event');
	evt.initEvent('autosize:update', true, false);
	document.querySelector("textarea").dispatchEvent(evt);
}