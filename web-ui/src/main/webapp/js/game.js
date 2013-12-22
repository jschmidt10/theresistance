
var clientGameConfig = {};

function loadDisplay(gameId, userName) {
	
	function setButtonState(thisButton, otherButton) {
		thisButton.setDisabled(true);
		otherButton.setDisabled(false);
	}
	
	function setButtonVisibility(actionOne, actionTwo) {
		Ext.ComponentQuery.query('#actionOne')[0].setVisible(actionOne);
		Ext.ComponentQuery.query('#actionTwo')[0].setVisible(actionTwo);
		Ext.ComponentQuery.query('#actionOne')[0].setDisabled(false);
		Ext.ComponentQuery.query('#actionTwo')[0].setDisabled(false);
	}
	function setButtonText(actionOne, actionTwo) {
		Ext.ComponentQuery.query('#actionOne')[0].setText(actionOne);
		Ext.ComponentQuery.query('#actionTwo')[0].setText(actionTwo);
	}
	
	function setProposeHandler(numberOfParticipants) {
		Ext.ComponentQuery.query('#actionOne')[0].setHandler(function() {
			showProposeMissionDialog(numberOfParticipants);
		});
		Ext.ComponentQuery.query('#actionTwo')[0].setHandler(function(){});
	}
	
	function setApprovalHandlers() {
		var actionOne = Ext.ComponentQuery.query('#actionOne')[0];
		var actionTwo = Ext.ComponentQuery.query('#actionTwo')[0];
		actionOne.setHandler(function() {
			submitApproval('SEND', actionOne, actionTwo);
		});
		actionTwo.setHandler(function() {
			submitApproval('DONT_SEND', actionOne, actionTwo);
		});
	}
	
	function setMissionResultHandlers() {
		var actionOne = Ext.ComponentQuery.query('#actionOne')[0];
		var actionTwo = Ext.ComponentQuery.query('#actionTwo')[0];
		actionOne.setHandler(function() {
			submitMissionResult('PASS', actionOne, actionTwo);
		});
		actionTwo.setHandler(function() {
			submitMissionResult('FAIL', actionOne, actionTwo);
		});
	}
	
	function setStatusMessage(message) {
		var statusLabel = Ext.ComponentQuery.query('#gameStatusLabel')[0];
		statusLabel.setText(message);
	}
	
	function showProposeMissionDialog(numberOfParticipants) {
		Ext.create('js.game.ProposalWindow', {
			numberOfParticipants: numberOfParticipants,
			gameId: gameId
		}).show();
	}
	
	function submitApproval(approval, thisButton, otherButton) {
		Ext.Ajax.request({
			url: '/server/vote',
			params: {
				gameId: gameId,
				player: userName,
				vote: approval
			},
			success: function(response) {
				var wrapper = Ext.JSON.decode(response.responseText);
				isSuccessful("Submit Proposal Approval", wrapper);
				setButtonState(thisButton, otherButton);
			}
		});
	}
	
	function submitMissionResult(result, thisButton, otherButton) {
		Ext.Ajax.request({
			url: '/server/mission',
			params: {
				gameId: gameId,
				player: userName,
				result: result
			},
			success: function(response) {
				var wrapper = Ext.JSON.decode(response.responseText);
				isSuccessful("Submit Mission Result", wrapper);
				setButtonState(thisButton, otherButton);
			}
		});
	}
	
	Ext.create('Ext.container.Viewport', {
		layout: {
			type: 'hbox',
			align: 'stretch'
		},
		items: [{
			xtype: 'panel',
			title: 'The Resistance',
			flex: 1,
			layout: {
				type: 'hbox',
				align: 'stretch'
			},
			items: [
			Ext.create('js.game.PlayerListPanel', { gameId: gameId, userName: userName }), 
			{
				xtype: 'panel',
				title: 'Game',
				flex: 4,
				layout: {
					type: 'vbox',
					align: 'stretch'
				},
				tools: [{
					xtype: 'button',
					text: 'Config',
					handler: function() {
						Ext.create('js.game.GameConfigWindow', {
							gameConfig: clientGameConfig
						}).show();
					}
				}],
				defaults: { margin: '8' },
				items: [{
					xtype: 'panel',
					title: 'Actions',
					flex: 1,
					layout: {
						type: 'hbox',
						align: 'stretch'
					},
					defaults: {
						xtype: 'button',
						flex: 1,
						margins: '10'
					},
					items: [{
						text: 'Action One',
						itemId: 'actionOne'
					}, {
						text: 'Action Two',
						itemId: 'actionTwo'
					}]
				}, {
					xtype: 'panel',
					title: 'Game Status',
					layout: {
						type: 'hbox',
						align: 'middle'
					},
					items: [{	
				        xtype: 'label',
						text: 'Game status here...',
						itemId: 'gameStatusLabel',
						margin: '5 0 5 0',
						width: '100%',
						style: 'text-align: center'
					}]
				}, {
					xtype: 'panel',
					title: 'Missions',
					flex: 1,
					itemId: 'missions',
					layout: {
						type: 'hbox',
						align: 'stretch'
					},
					defaults: { 
						margin: '10', 
						xtype: 'button', 
						flex: 1,
						handler: function() {
							var mission = this.mission;
							Ext.StoreManager.lookup('proposalHistory').loadProposals(mission);
						}
					},
					items: [{ mission: 0, text: 'Mission 1' }, 
					        { mission: 1, text: 'Mission 2' }, 
					        { mission: 2, text: 'Mission 3' }, 
					        { mission: 3, text: 'Mission 4' }, 
					        { mission: 4, text: 'Mission 5' }]
				}, 
				Ext.create('js.game.HistoryPanel', { gameId: gameId })]
			}]
		}]
	});
	
	function getArrayString(players) {
		var leftToVote = '';
		Ext.Array.each(players, function(player) {
			if (leftToVote == '') {
				leftToVote = player;
			} else {
				leftToVote += ", " + player;
			}
		});
		if (leftToVote == '') {
			leftToVote = "(Nobody)";
		}
		return leftToVote;
	}
	
	var gameStateUpdater = {};
	gameStateUpdater.task = new Ext.util.DelayedTask(function() {
		Ext.Ajax.request({
			url: '/server/gameState',
			params: { gameId: gameId },
			success: function(response) {
				var wrapper = Ext.JSON.decode(response.responseText);
				if (!isSuccessful("Game State Load", wrapper)) {
					return;
				}
				var state = wrapper.data;
				if (state.name == 'ProposeState') {
					if (state.leader == userName) {
						setButtonText('Propose Mission', 'Action Two');
						setButtonVisibility(true, false);
						setProposeHandler(state.numberOfParticipants);
					} else {
						setButtonVisibility(false, false);
					}
					setStatusMessage('Waiting for mission proposal from ' + state.leader);
				} else if (state.name == 'VoteState') {
					setButtonVisibility(true, true);
					setButtonText('Accept Mission', 'Reject Mission');
					setApprovalHandlers();
					var proposal = getArrayString(state.proposal);
					var leftToVote = getArrayString(state.playersLeftToVote);
					setStatusMessage('Waiting for votes on mission: ' + proposal + '. from: ' + leftToVote);
				} else if (state.name == 'MissionState') {
					if (Ext.Array.contains(state.participants, userName)) {
						setButtonVisibility(true, true);
						setButtonText('Pass Mission', 'Fail Mission');
						setMissionResultHandlers();
					} else {
						setButtonVisibility(false, false);
					}
					var leftToVote = getArrayString(state.playersLeftToVote);
					setStatusMessage('Waiting for mission result votes from: ' + leftToVote);
				} else {
					setButtonVisibility(false, false);
					setStatusMessage('Game over');
				}
				gameStateUpdater.task.delay(1000);
			}
		});
	});
	gameStateUpdater.task.delay(1);
	
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
