<!doctype html>
  <html>
      <head>
          <meta charset="UTF-8" />
	  <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1">	      

      </head>
      <body>
	    <a href="taobao://m.taobao.com" target="_self" id="taobao">连接到淘宝</a>
	    <a href="myscheme://myhost/mypath" target="_self" id="trip">连接到手工</a>

            <script type="text/javascript"> 
            function applink(){  
                return function(){  
                    var clickedAt = +new Date;  
                     setTimeout(function(){
                         !window.document.webkitHidden && setTimeout(function(){ 
                               if (+new Date - clickedAt < 2000){  
                                   window.location = 'http://www.baidu.com';  
                               }  
                         }, 500);       
                     }, 500)   
                };  
            }  
            document.getElementById("taobao").onclick = applink();  
            document.getElementById("trip").onclick = applink();  
            </script>   
	</body>
</html>