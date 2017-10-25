var express = require('express');
var app = express();
var fs = require('fs');

var bodyParser = require('body-parser');
var multer  = require('multer');

app.use(express.static('public'));
app.use(bodyParser.urlencoded({ extended: false }));
app.use(multer({ dest: __dirname + '/temp'}).array('image'));

app.get('/',function(req,res){
    res.send('hello world');
});

//普通get请求
app.get('/v1/getTest',function(req,res){
    res.send('get test');
});

//带参数的get请求
app.get('/v1/getTestWith',function(req,res){
    var param = req.query;
    console.log('param:' + JSON.stringify(param));
    res.send('get test with param,params is : ' + JSON.stringify(param));
});

app.get('/v1/getListParam',function(req,res){
    var param = req.query;
    res.send('params:'+ JSON.stringify(param));
});

//普通post请求
app.post('/v1/postTest',function(req,res){
    res.send('post test');
});

//get请求访问html文件
app.get('/index.html',function(req,res){
    res.sendFile(__dirname + '/index.html');
});

//get请求访问图片
app.get('/image',function(req,res){
    res.sendFile(__dirname+"/img_emoji.jpg");
});

//通过表单的方式上传文件
app.post('/fileUpload',function(req,res){
    var firstFile = req.files[0];
    var filename = firstFile.originalname;
    var secondFile = req.files[1];
    var secondFileName = secondFile.originalname;
    var params = req.body;
    console.log('first:' + JSON.stringify(firstFile));
    console.log('second:' + JSON.stringify(secondFile));
    console.log('params:' + JSON.stringify(params));
    saveImage(firstFile,filename);
    saveImage(secondFile,secondFileName);
});

function print(){
    console.log('>>>>');
}

function saveImage(file1,filename){
    var dest = __dirname + '/' + filename;
    fs.readFile(file1.path,function(data,error){
        fs.writeFile(dest,data,function(error){
            if(error){
                console.log('write error,' + e);
            }else{
                // res.send(filename + ' upload successful!');
                console.log(filename + ' upload successful!');
            }
        });
    });
}

app.get('/createForm',function(req,res){
    res.sendFile(__dirname + '/uploadForm.html');
});


var server = app.listen(8081,function(){
    var host = server.address().address;
    var port = server.address().port;
    console.log('正在访问,http://%s:%s',host,port);
});
