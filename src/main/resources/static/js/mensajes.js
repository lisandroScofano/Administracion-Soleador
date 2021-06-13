 	 	var firebaseConfig = {
		    apiKey: "AIzaSyAqwml9iPXNExRqo85yqetVGAG_Fv0KRYg",
		    authDomain: "iris-control-287102.firebaseapp.com",
		    databaseURL: "https://iris-control-287102.firebaseio.com",
		    projectId: "iris-control-287102",
		    storageBucket: "iris-control-287102.appspot.com",
		    messagingSenderId: "323998331774",
		    appId: "1:323998331774:web:9e7584c0ad892010c1f92f",
		    measurementId: "G-7ZMC0E1RCB"
		  };
		
		  firebase.initializeApp(firebaseConfig);
		
		  const messaging = firebase.messaging();
		  
		  $("#request").on("click", function(){
			  requestNotification(); 
		  });
		  

		  $("#request2").on("click", function(){
			  requestNotification(); 
		  });
		  
		  function requestNotification(){
			  messaging.requestPermission()
			  .then(function(){
				  console.log('have permission');
				  return messaging.getToken();
			  })
			  .then(function(token){
				  console.log(token);
				  
				  if(token != "" && token != null && token != undefined){
					  $.ajax({
							url : '/usuario/firebase/token',
							data : token,
							dataType : 'json',
							contentType : "application/json",
							type : 'POST',
							success : function(data) {
								console.log("success: " + JSON.stringify(data));
							},
							error : function(data) {
								console.log("error: " + JSON.stringify(data));
							}
						});
				  }
				  
				 
			  })
			  .catch(function(err){
				  console.log(err);
			  })
		  }
			 
		  requestNotification();
		
		  messaging.onMessage(function(payload){
			  if(payload.data.tipo == "fletero-retiro"){
				  toastr.warning('El fletero: ' + payload.data.fletero + ' retiro un pedido desde: ' + payload.data.origen + ' para la obra: ' + payload.data.obra , 'Envio en Camino');
			  }
			  
			  if(payload.data.tipo == "recibido-error"){
				  toastr.error('El envío perteneciente a la obra: ' +  payload.data.obra + ' fue recibido con errores.', "Error en Envío");
		      }
			  
			  if(payload.data.tipo == "pedido-nuevo"){
				  toastr.info('Se ha efectuado un nuevo pedido para la obra: ' +  payload.data.obra, 'Nuevo Pedido');
			  }

			  if(payload.data.tipo == "retiros-pendientes"){
				  toastr.info('Tiene traslados de materiales o herramientas pendientes.', 'Traslados');
			  }
			  
		  });