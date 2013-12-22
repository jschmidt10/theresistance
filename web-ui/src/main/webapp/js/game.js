
var clientGameConfig = {};

function loadDisplay(gameId, userName) {
	
	Ext.create('js.game.GameUI', {
		gameId: gameId,
		userName: userName,
		gameConfig: clientGameConfig
	});
	
	Ext.Ajax.request({
		url: '/server/config',
		params: { gameId: gameId },
		success: function(response) {
			var wrapper = Ext.JSON.decode(response.responseText);
			if (!isSuccessful("Game Configuration Load", wrapper)) {
				return;
			}
			clientGameConfig = wrapper.data;
			setMissionButtonText();
		}
	});
}

function setMissionButtonText() {
	var missionButtons = Ext.ComponentQuery.query('#missions')[0].query('button');
	var missions = clientGameConfig.missions;
	for (var i = 0; i < missionButtons.length; i++) {
		missionButtons[i].setText(
				"Mission " + (i + 1) + "<br/>" + 
				"Players Needed: " + missions[i].numParticipants + "<br/>" +
				"Fails Needed: " + missions[i].requiredFails);
	}
}

Ext.onReady(function() {
	var game = Ext.Object.fromQueryString(location.search.substring(1));
	if (game.gameId && game.userName) {
		loadDisplay(game.gameId, game.userName);
	} else {
		Ext.Msg.alert('Enter Game Error', 'No game was selected. Navigating back to lobby...');
		location.href = 'index.html';
	}
});
