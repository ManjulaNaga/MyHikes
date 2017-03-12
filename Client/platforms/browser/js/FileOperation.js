//create persistent file
function onDeviceReady() {  
    window.requestFileSystem(LocalFileSystem.PERSISTENT, 0, function (fs)
    {
        console.log('file system open: ' + fs.name);
        fs.root.getFile("SampleData.txt", { create: true, exclusive: false }, function (fileEntry) {
            console.log("fileEntry is file?" + fileEntry.isFile.toString());
                 // fileEntry.name === 'SampleData.txt';
                 // fileEntry.fullPath === '/SampleData.txt';
            // writeToFile(fileEntry, null);
            console.log("file fullPath" + fileEntry.fullPath);
        }, onErrorCreateFile);
    }, onErrorLoadFs);}

//data to be appended to existing file
/*window.resolveLocalFileSystemURL(cordova.file.dataDirectory, function (dirEntry) {
    console.log('file system open: ' + dirEntry.name);
    var isAppend = true;
    createFile(dirEntry, "SampleData.txt", isAppend);
    console.log("filename is "+fileEntry.name);
    console.log("file path is "+fileEntry.fullPath);
}, onErrorLoadFs);}*/

//create a file
function createFile(dirEntry, fileName, isAppend) {
    // Creates a new file or returns the file if it already exists.
    dirEntry.getFile(fileName, {create: true, exclusive: false}, function(fileEntry) {
        writeToFile(fileEntry, null, isAppend);
    }, onErrorCreateFile);
}

//write data to a file in cordova
    function writeToFile(fileName,data,isAppend) {
        //data = JSON.stringify(data, null, '\t');
        //window.resolveLocalFileSystemURL(cordova.file.dataDirectory, function (directoryEntry) {
          //  directoryEntry.getFile(fileName, { create: true }, function (fileEntry) {
                fileEntry.createWriter(function (fileWriter) {
                    fileWriter.onwriteend = function (e) {
                        // for real-world usage, you might consider passing a success callback
                        console.log('Write of file "' + fileName + '"" completed.');
                    };

                    fileWriter.onerror = function (e) {
                        // you could hook this up with our global error handler, or pass in an error callback
                        console.log('Write failed: ' + e.toString());
                    };
                     if (isAppend) {
                        try {
                            fileWriter.seek(fileWriter.length);
                        }
                        catch (e) {
                            console.log("file doesn't exist!");
                        }
                    }
                    var blob = new Blob([data], { type: 'text/plain' });
                    fileWriter.write(blob);
                }, errorHandler.bind(null, fileName));
           // }, errorHandler.bind(null, fileName));
       // }, errorHandler.bind(null, fileName));
    }

    //writeToFile('include/exampledata.json', { foo: 'bar' });

//read data from a file
/*document.addEventListener('deviceready', onDeviceReady, false);  
function onDeviceReady() {  
    function readFromFile(fileName, cb) {
        var pathToFile = cordova.file.dataDirectory + fileName;
        window.resolveLocalFileSystemURL(pathToFile, function (fileEntry) {
            fileEntry.file(function (file) {
                var reader = new FileReader();

                reader.onloadend = function (e) {
                    cb(JSON.parse(this.result));
                };

                reader.readAsText(file);
            }, errorHandler.bind(null, fileName));
        }, errorHandler.bind(null, fileName));
    }

    var fileData;
    readFromFile('include/exampledata.json', function (data) {
        fileData = data;
    });
}*/

//error handling fucntion
var errorHandler = function (fileName, e) {  
    var msg = '';
    switch (e.code) {
        case FileError.QUOTA_EXCEEDED_ERR:
            msg = 'Storage quota exceeded';
            break;
        case FileError.NOT_FOUND_ERR:
            msg = 'File not found';
            break;
        case FileError.SECURITY_ERR:
            msg = 'Security error';
            break;
        case FileError.INVALID_MODIFICATION_ERR:
            msg = 'Invalid modification';
            break;
        case FileError.INVALID_STATE_ERR:
            msg = 'Invalid state';
            break;
        default:
            msg = 'Unknown error';
            break;
    };

    console.log('Error (' + fileName + '): ' + msg);
}
