/*
 * 根据Value格式化为带有换行、空格格式的HTML代码
 * @param strValue {String} 需要转换的值
 * @return  {String}转换后的HTML代码
 * @example  
 * getFormatCode("测\r\n\s试")  =>  “测<br/> 试”
 */
var getFormatCode=function(strValue){
    return strValue.replace(/\r\n/g, '<br/>').replace(/\n/g, '<br/>').replace(/\s/g, ' ');
}

      /*<!-- /photo/24085  -->*/
      $(".generate").click(function(){
        $("#output").html("");
        var datas = $("#datas").val();
        var lines = datas.replace(/\r\n/g, '@-').replace(/\n/g, '@-').split("@-");
        lines.forEach(function(item, index){
          var index = item.indexOf(" ");
          var link = item.substring(0, index);
          var temp = item.substring(index + 1);
          index = temp.lastIndexOf(" ");
          var title = temp.substring(0, index);
          var size = temp.substring(index + 1);
          $("#output").append($("<span/>").html(title)).append("<br/>");
          for(var i = 1; i <= size; i++){
            $("#output").append($("<span/>").html(link.replace("photo", "media/photos") + "/" + ((100000 + i) + "").substring(1) + ".jpg")).append("<br/>");
          }
        });
      });