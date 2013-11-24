function isSuccessful(name, json) { 
	if (json.success) {
		return true;
	} else {
		Ext.Msg.alert(name + ' Error', json.message);
		return false;
	}
}

function errorCheckingHandler(name) {
	return function(response) {
		var wrapper = Ext.JSON.decode(response.responseText);
		isSuccessful(name, wrapper);
	};
}