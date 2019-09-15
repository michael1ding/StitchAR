var express  = require('express');
var app      = express();                              // create our app w/ express
var Firebase = require('firebase');
var morgan = require('morgan');      
var bodyParser = require('body-parser');    // pull information from HTML POST (express4)
var methodOverride = require('method-override');
var multer  =   require('multer');
var fs = require("fs");
var base64ToImage = require('base64-to-image');
var download = require('download-file');
var extract = require('extract-zip')
var unzip = require('unzip');
var fs = require('fs');

var inputFileName = 'sss2.zip';
var extractToDirectory = process.cwd();

app.use(function(req, res, next) { //allow cross origin requests

    res.setHeader("Access-Control-Allow-Origin", "*");

    res.header("Access-Control-Allow-Methods", "POST, PUT, OPTIONS, DELETE, GET");

    res.header("Access-Control-Max-Age", "3600");

    res.header("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

    next();

});

Firebase.initializeApp({

    databaseURL: "https://ar-app-5a94c.firebaseio.com/",

    serviceAccount: './testapp.json', //this is file that I downloaded from Firebase Console

});

var db = Firebase.database();

var usersRef = db.ref("users");

// var FirebaseRef = new Firebase("https://testapp-e5455.firebaseio.com/");

// configuration

app.use(express.static(__dirname + '/public'));                 // set the static files location /public/img will be /img for users

app.use('/public/uploads',express.static(__dirname + '/public/uploads'));

app.use(morgan('dev'));                                         // log every request to the console

app.use(bodyParser.urlencoded({'extended':'true'}));            // parse application/x-www-form-urlencoded

app.use(bodyParser.json());                                     // parse application/json

app.use(bodyParser.json({ type: 'application/vnd.api+json' })); // parse application/vnd.api+json as json

app.get('/', function (req, res) {

  res.sendfile('./index.html')

})

//extraction
function extractZIP(filename) {
    console.log(extract);
    fs.createReadStream(inputFileName)
	.pipe(unzip.Extract({
		path: extractToDirectory 
	}));
}

//download
function downloadAndExtractZIP(url) {
    //var urlw = "https://github.com/ManethKulatunge/GeoTabChallenge"
    console.log(url);
    var options = {
        directory: "./",
        filename: "sss2.zip"
    }
     
    download(url, options, function(err){
        if (err) throw err
        console.log(err);
    }) 

    extractZIP(options.filename);
}

function getJPGFromFirebase(picturedata) {
    for (i = 0; i < picturedata.length; i++) {
        var base64Str = picturedata.i;
        var path ='/Users/admin/Documents/HTN2019/Server/Pictures_jpg';
        var optionalObj = {'fileName': i , 'type':'jpg'};
        base64ToImage(base64Str,path,optionalObj); 
      } 
}
// create user

app.post('/api/createUser', function(req, res) {

   // var userEmail = req.body.user_email;

    var data = req.body;

usersRef.push(data, function(err) {

if (err) {

res.send(err)

} else {

// var key = Object.keys(snapshot.val())[0];

// console.log(key);

res.json({message: "Success: User Save.", result: true});

}

});

});

//get images 
app.get('/api/image', function(req,res) {
    if (req.body.id == '000001') {
        res.sendFile(__dirname + '/result.obj');
    }

    if (req.body.id == '000001a') {
        res.sendFile(__dirname + '/result.mtl');
    }
});
// sending images to firebase

app.post('/api/firebase', function(req, res) {

    // var userEmail = req.body.user_email;
 
     var data = req.body;
 
 usersRef.push(data, function(err) {

 if (err) {
 
 res.send(err)
 
 } else {

    //getJPGFromFirebase(data.picture || null);
    //console.log(data);
    var url = JSON.stringify(data.url);
    var url = "https://adsk-rc-photofly-prod.s3.amazonaws.com/3.0.0/OUT/nnaka01fSovKRzBooVbHzm8ncyHwY60UNWpxY8G7A7Q%3D-15FPBeFMseDPh7r4MDdDBad8qQThP8ef3HLNWyUK5us/100000000/testscene.obj.zip?response-content-disposition=attachment%3B%20filename%3D3.0.0%2FOUT%2Fnnaka01fSovKRzBooVbHzm8ncyHwY60UNWpxY8G7A7Q%3D-15FPBeFMseDPh7r4MDdDBad8qQThP8ef3HLNWyUK5us%2F100000000%2Ftestscene.obj.zip&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Security-Token=AgoJb3JpZ2luX2VjEG8aCXVzLWVhc3QtMSJGMEQCIFo3WPNrmWBMm5aOaO5Fd4pyo52AhXVkAwzxwsGxnKJnAiAL3BrSbW58dWXoH2%2F4D94yoNy7rINcCgjJadUy1BhTRCraAwg4EAAaDDQzNjkwNzQzODIzNiIMars3BhUMI%2By2JboSKrcDpWPYrVwmTncF0amMgHTM25L8XcuzpyaysVpduY%2BOFXFM4Kgbt9GhUBPra5FA4otsqUOEVhjOrDZUxLM6f3bgxuPO9QSx%2B47A7gtAPTq7OI%2FTUundUyBZN91G7XEl9ESpxEjY7QHIIJD%2BV5zWzTveGfd49Emuv8MgdUAZC46JJZbni%2BlPqt5tsVVnY1rUjTDvJrsgI6W6XWlkxNMsPx3q17WKEcU%2FisQ9%2Ff5b1Tetr5eZRPN%2FZiZTy2Lo7ky%2BBs2IsCi7TFpzvNgWv8KJ4UNMxGtABiUbUE0X6XvAa3caTKKJqOGPeIyc2Ki0SkWsrTqaXg8Kh%2BuU5bCju%2FsNnLwlOxqpA%2BjW82%2F8XSIpKONac8tkcluGl9LtXbU7pwqw2K6Jb834CHCYP4WdsP2PZGN0Mz5Fx7GjBCuxTCfQKAhBCXDmUTO%2B6tDVVwqghBeGKBbqvNJ4V%2B56BRlXdKhN4K788I3H1Q7K%2BcHHdUkvJYRCc9enunp2yeX4oOQ1SN5APZ9rXbI46i7dfFJ54bH4sk4u0nXbZIhUD7HTyLSUTsUKbZWn6qnBVuM4VXp6fpC%2BvaHUajhO4Lj4zjD60vXrBTq1AVkUuYbFCLixVElTRea%2B1q8vV2pUSWugFnY53bsjpoxvqDATSRZnkeddTH90MJcVeqho7lAAsApY%2Fnji7Xc5W5MFUwlneGpBw16y5n8ZRoYD4GcC3yRcfOqvnuPodi12V4QgjKXJi7%2FIs5zgVHgjwzj0BjOV68UW1aL2KNcLuY65%2BO%2B%2BgDph7jhCgo4IqPqUYx%2FhrpOvHFOUh7enn0EuqAf1QHsF5ModJFumx4yw2ZmTmabsG18%3D&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=ASIAWLONWOCOORHBAH7U%2F20190914%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20190914T222925Z&X-Amz-SignedHeaders=host&X-Amz-Expires=86400&X-Amz-Signature=86057a691b6d7c361a17da701099d919c37d3f406b2756bb436b3e746ca286ee"
    downloadAndExtractZIP(url || null);
    res.json({message: "Success: User Save.", result: true});
 
 }
 
 });
 
 });
 
// update user

app.put('/api/updateUser', function(req, res) {

    var uid = "-Loi5DYYibqnUWxGVxbd";

var data = req.body;

usersRef.child(uid).update(data, function(err) {

if (err) {

res.send(err);

} else {

usersRef.child("uid").once("value", function(snapshot) {

if (snapshot.val() == null) {

res.json({message: "Error: No user found", "result": false});

} else {

res.json({"message":"successfully update data", "result": true, "data": snapshot.val()});

}

});

}

});

});

// delete user

app.delete('/api/removeUser', function(req, res) {

var uid = "-Loi5DYYibqnUWxGVxbd";

usersRef.child(uid).remove(function(err) {

if (err) {

res.send(err);

} else {

res.json({message: "Success: User deleted.", result: true});

}

})

});

// get users

app.post('/api/getUsers', function(req, res) {

    var uid = "-Loi5DYYibqnUWxGVxbd";

if (uid.length != 20) {

res.json({message: "Error: uid must be 20 characters long."});

} else {

usersRef.once("value", function(snapshot) {

//console.log(snapshot);

if (snapshot.val() == null) {

res.json({message: "Error: No user found", "result": false});

} else {

res.json({"message":"successfully fetch data", "result": true, "data": snapshot.val()});

}

});

}

});


// login

app.post('/api/login', function(req, res) {


    User.findOne({ 'user_name' :  req.body.user_name, 'password' : req.body.password }, function(err, user) {

            // if there are any errors, return the error

            if (err)

                return res.send(err);

            // check to see if user exist

            if (user) {

                return res.json(user);

            } else {

                return res.json({"message":"Invalid Username or Password."});

            }


        });

});


app.listen(3000);

console.log("port is 3000");