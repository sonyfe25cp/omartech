$(document).ready(function(){
  var url = window.location.href;
  var path = url.substr(url.lastIndexOf('/')+1)
  if(path ==''){
    path = 'home';
  }
  $("#"+path).addClass('active');
});

