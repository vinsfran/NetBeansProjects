/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
window.onload = function(){
    var username = document.getElementById('login:username');
    showPlaceholder(username, 'Usuario');
    var password = document.getElementById('login:password');
    showPlaceholder(password, 'Password');
    var search = document.getElementById('form:buscador_input');
    showPlaceholder(search, 'Buscar Usuario...'); 
    inicializar(); 
}

function showPlaceholder(e,text){
    if(e.value == "Usuario" | e.value == "Password" | e.value == "" | e.value =="Buscar Usuario..." ){
        e.style.color = "#777";
        e.value = text;    
    }
}

function hidePlaceholder(e){
    if(e.value == "Usuario" | e.value == "Password" | e.value == "Buscar Usuario..."){
        e.style.color = "#000";
        e.value = "";
    }
  }  
 //cronometro para registro de Factores FODA  
   var timeLimit = 10; //tiempo en minutos
   var conteo = new Date(timeLimit * 60000);

   function inicializar(){
      document.getElementById('cuenta').childNodes[0].nodeValue = 
      conteo.getMinutes() + ":" + conteo.getSeconds();
   }

   function cuenta(){
      intervaloRegresivo = setInterval("regresiva()", 1000);
   }

   function regresiva(){
      if(conteo.getTime() > 0){
         conteo.setTime(conteo.getTime() - 1000);
      }else{
         clearInterval(intervaloRegresivo);
         //se activa la función Mensaje() luego de 10 minutos
         window.setTimeout(Mensaje,conteo.getTime()); 
    }
    function Mensaje(){
        alert("Su tiempo ha expirado!!");
        {parent.location='./index.xhtml';}
    }
   document.getElementById('cuenta').childNodes[0].nodeValue = 
   conteo.getMinutes() + ":" + conteo.getSeconds();
   }
  

