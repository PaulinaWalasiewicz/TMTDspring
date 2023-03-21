const link = document.createElement("link");
link.rel = "stylesheet";
link.href = "https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.css";
link.integrity = "sha512-5A8nwdMOWrSz20fDsjczgUidUBR8liPYU+WymTZP1lmY9G6Oc7HlZv156XqnsgNUzTyMefFTcsFH/tnJE/+xBg==";
link.crossOrigin = "anonymous";
link.referrerPolicy = "no-referrer";

document.head.appendChild(link);

// load jQuery and jQuery UI libraries
var script1 = document.createElement("script");
script1.src = "https://code.jquery.com/jquery-3.6.0.js";
document.getElementsByTagName("head")[0].appendChild(script1);

var script2 = document.createElement("script");
script2.src = "https://code.jquery.com/ui/1.13.2/jquery-ui.js";
document.head.appendChild(script2);


setTimeout(function() {
    $( ".navigation" ).draggable();
} ,1000);