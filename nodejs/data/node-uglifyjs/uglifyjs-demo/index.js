var imgs = document.querySelectorAll("img");
for(var i = 0; i<imgs.length; i++){
  imgs[i].addEventListener("load", function(event){ onImageLoaded(this) }, false);
}

function onImageLoaded(img) {
  var canvas = img.nextElementSibling;
  var ctx = canvas.getContext('2d');
  var s_w = img.width;//�@ʾ�ߴ�
  var w = img.naturalWidth;//ԭʼ�ߴ�
  var h = img.naturalHeight;
  canvas.width = w;
  canvas.height = h;
  if (s_w > img.parentNode.offsetWidth || s_w == 0) {
    s_w = img.parentNode.offsetWidth;
  }
  canvas.style.width = s_w + "px";
  var num = 10;
  var remainder = parseInt(h % num);
  var copyW = w;
  for (var i = 0; i < num; i++) {
    var copyH = Math.floor(h / num);
    var py = copyH * (i);
    var y = h - (copyH * (i + 1)) - remainder;

    if (i == 0) {
      copyH = copyH + remainder;
    } else {
      py = py + remainder;
    }
    ctx.drawImage(img, 0, y, copyW, copyH, 0, py, copyW, copyH);
  }
  img.setAttribute("style", "display:none");
}