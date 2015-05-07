$(document).ready(function(){
    $(".menu > li").click(function(e){
        var a = e.target.id;
        //desactivamos seccion y activamos elemento de menu
        $(".menu li.active").removeClass("active");
        $(".menu #"+a).addClass("active");
        //ocultamos divisiones, mostramos la seleccionada
        $(".content1").css("display", "none");
        $("."+a).fadeIn();
    });
});


function oculta(id){
   var elDiv = document.getElementById(id); //se define la variable "elDiv" igual a nuestro div
   elDiv.style.display='none'; //damos un atributo display:none que oculta el div     
  }
  
  function muestra(id){
  var elDiv = document.getElementById(id); //se define la variable "elDiv" igual a nuestro div
  elDiv.style.display='inline';//damos un atributo display:block que  el div     
  }
  
function guardarComoPDF() { 
var pURL = "http://savepageaspdf.pdfonline.com/pdfonline/pdfonline.asp?cURL=" + escape(document.location.href); 
window.open(pURL, "PDFOnline", "scrollbars=yes, resizable=yes,width=640, height=480,menubar,toolbar,location");
 }