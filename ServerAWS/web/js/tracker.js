document.addEventListener("deviceready", function(){
   
  if(navigator.network.connection.type == Connection.NONE){
    $("#home_network_button").text('No Internet Access')
                 .attr("data-icon", "delete")
                 .button('refresh');
  }
 
});

var watch_id = null;    // ID of the geolocation
var tracking_data = []; // Array containing GPS position objects
 
$("#startTracking_start").live('click', function(){
     
    // Start tracking the User
    watch_id = navigator.geolocation.watchPosition(
     
        // Success
        function(position){
            tracking_data.push(position);
        },
         
        // Error
        function(error){
            console.log(error);
        },
         
        // Settings
        { frequency: 3000, enableHighAccuracy: true });
     
    // Tidy up the UI
    track_id = $("#track_id").val();
     
    $("#track_id").hide();
     
    $("#startTracking_status").html("Tracking workout: <strong>" + track_id + "</strong>");
});