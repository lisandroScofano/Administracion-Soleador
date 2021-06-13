importScripts('https://www.gstatic.com/firebasejs/8.0.1/firebase-app.js')
importScripts('https://www.gstatic.com/firebasejs/8.0.1/firebase-messaging.js')
importScripts('https://www.gstatic.com/firebasejs/8.0.1/firebase-analytics.js')

var firebaseConfig = {
	apiKey : "AIzaSyAqwml9iPXNExRqo85yqetVGAG_Fv0KRYg",
	authDomain : "iris-control-287102.firebaseapp.com",
	databaseURL : "https://iris-control-287102.firebaseio.com",
	projectId : "iris-control-287102",
	storageBucket : "iris-control-287102.appspot.com",
	messagingSenderId : "323998331774",
	appId : "1:323998331774:web:9e7584c0ad892010c1f92f",
	measurementId : "G-7ZMC0E1RCB"
};
firebase.initializeApp(firebaseConfig);

var messaging = firebase.messaging();

messaging.setBackgroundMessageHandler(function(payload) {
	var title = "";
	var options = {};

	switch (payload.data.tipo) {
	case "recibido-error":
		title = 'Remito recibido con error, obra: ' + payload.data.obra;
		options = {
			body : payload.data.status
		}
		console.log(payload.data.obra + " ");
		break;
	case "fletero-retiro":
		title = 'Obra: ' + payload.data.obra + ' El fletero: '
				+ payload.data.fletero + ' retiro pedido desde '
				+ payload.data.origen;
		options = {
			body : payload.data.status
		}
		console.log(payload.data.obra + " ");
		break;
	case "retiros-pendientes":
		title = 'Tiene retiros pendientes para realizar hoy';
		options = {
			body : payload.data.status
		}
		console.log(payload.data.obra + " ");
		break;
	case "pedido-nuevo":
		title = 'Pedido nuevo para: ' + payload.data.obra;
		options = {
			body : 'se genero un pedido nuevo para la obra: '
					+ payload.data.obra
		}
		console.log(payload.data.obra + " ");
		break;
	default:
	}

	return self.registration.showNotification(title, options);
});