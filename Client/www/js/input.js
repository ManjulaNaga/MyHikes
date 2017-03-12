$('#loginForm').submit(function(e){
    e.preventDefault();
    $.ajax({
        type:'post',
        data:$('#myForm').serialize(),
        success:function(){
            hikeController.validate();
        }
    });
});

document.addEventListener("deviceready", onDeviceReady, false);
function onDeviceReady() {
    console.log(cordova.file);
}