$(function(){
	
	$.ajax({
		type:"get",
		url:"http://192.168.6.80/server/api/HANDS/CHANDWORKS/1.0/INFO",
		dataType:"json",
		data:{			
			"TIMESTAMP":0,
			"USERTOKEN":"ceb294013b5c4afda981bb83b1bbe2ad",
			"LNG":0,
			"LAT":0,
		},
		success:function(data){
			console.log(data);
			console.log(1);
		},
		error:function(){
			console.log(2);
		},
	});
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
})
