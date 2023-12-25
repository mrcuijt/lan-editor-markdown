
var define = null;
document.body.innerHTML = "";
var app = document.createElement('div');
app.id = 'app';
app.innerHTML = '<el-img-capture :list="list"></el-img-capture>';
document.body.append(app);
var s1 = document.createElement('script');
document.body.append(s1);
s1.type = 'text/javascript';
s1.src = "http://localhost:8000/vue.js"

var s2 = document.createElement('script');
document.body.append(s2);
s2.type = 'text/javascript';
s2.src = "http://localhost:8000/jquery-3.5.1.min.js"