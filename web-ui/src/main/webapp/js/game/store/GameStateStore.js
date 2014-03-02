Ext.define('js.game.store.GameStateStore', {
	extend: 'Ext.Component',
	
	/** The id of the game to update the state for. This should be set by the game state store config. */
	gameId: undefined,
	
	/** Name of the user to update state with. This should be set by the game state store config. */
	userName: undefined,
	
	/** This delay task will be used to update the game stats periodically. */
	updateGameTask: null,
	
	initComponent: function() {
		var me = this;
		me.callParent();
		me.assignGameStateTask();
		me.updateGameTask.delay(1);
	},
	
	assignGameStateTask: function() {
		var me = this;
		var actionOne = Ext.ComponentQuery.query('#actionOne')[0];
		var actionTwo = Ext.ComponentQuery.query('#actionTwo')[0];
		
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

		function setButtonState(thisButton, otherButton) {
			thisButton.setDisabled(true);
			otherButton.setDisabled(false);
		}
		
		function setButtonVisibility(visibilityOne, visibilityTwo) {
			actionOne.setVisible(visibilityOne);
			actionTwo.setVisible(visibilityTwo);
			actionOne.setDisabled(false);
			actionTwo.setDisabled(false);
		}
		function setButtonText(textOne, textTwo) {
			actionOne.setText(textOne);
			actionTwo.setText(textTwo);
		}
		
		function setProposeHandler(numberOfParticipants) {
			actionOne.setHandler(function() {
				showProposeMissionDialog(numberOfParticipants);
			});
			actionTwo.setHandler(function(){});
		}

		function setAssassinationHandler() {
			actionOne.setHandler(function() {
				showAssassinationDialog();
			});
			actionTwo.setHandler(function(){});
		}
		
		function setApprovalHandlers() {
			actionOne.setHandler(function() {
				submitApproval('SEND', actionOne, actionTwo);
			});
			actionTwo.setHandler(function() {
				submitApproval('DONT_SEND', actionOne, actionTwo);
			});
		}
		
		function setMissionResultHandlers() {
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
		
		function showAssassinationDialog() {
			Ext.create('js.game.AssassinationWindow', {
				gameId: me.gameId
			});
		}
		
		function showProposeMissionDialog(numberOfParticipants) {
			Ext.create('js.game.ProposalWindow', {
				numberOfParticipants: numberOfParticipants,
				gameId: me.gameId
			}).show();
		}
		
		function submitApproval(approval, thisButton, otherButton) {
			Ext.Ajax.request({
				url: '/server/vote',
				params: {
					gameId: me.gameId,
					player: me.userName,
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
					gameId: me.gameId,
					player: me.userName,
					result: result
				},
				success: function(response) {
					var wrapper = Ext.JSON.decode(response.responseText);
					isSuccessful("Submit Mission Result", wrapper);
					setButtonState(thisButton, otherButton);
				}
			});
		}
		
		function updateRoundResults() {
			Ext.Ajax.request({
				url: '/server/rounds',
				params: { gameId: me.gameId },
				success: function(response) {
					var wrapper = Ext.JSON.decode(response.responseText);
					console.log(JSON.stringify(wrapper));
					
					var missionButtons = Ext.ComponentQuery.query('#missions')[0].query('button');
					var results = wrapper.data;
					
					for (var i = 0; i < results.length; i++) {
						var index = results[i].index;
						var text = missionButtons[index].getText();
						if (!text.contains('Result')) {
							missionButtons[index].setText(text + '<br/>Result: ' + results[i].result + '<br/>Fails: ' + results[i].numFails);
						}
					}
				}
			});
		}
		
		me.updateGameTask = new Ext.util.DelayedTask(function() {
			Ext.Ajax.request({
				url: '/server/gameState',
				params: { gameId: me.gameId },
				success: function(response) {
					var wrapper = Ext.JSON.decode(response.responseText);
					if (!isSuccessful("Game State Load", wrapper)) {
						return;
					}
					var state = wrapper.data;
					updateRoundResults();
					if (state.name == 'ProposeState') {
						if (state.leader == me.userName) {
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
						if (Ext.Array.contains(state.participants, me.userName)) {
							setButtonVisibility(true, true);
							setButtonText('Pass Mission', 'Fail Mission');
							setMissionResultHandlers();
						} else {
							setButtonVisibility(false, false);
						}
						var leftToVote = getArrayString(state.playersLeftToVote);
						setStatusMessage('Waiting for mission result votes from: ' + leftToVote);
					} else if (state.name == 'AssassinationState') {
						setButtonText('Assassinate Player', 'Action Two');
						setButtonVisibility(true, false);
						setAssassinationHandler();
						setStatusMessage('Waiting for assassination from: ' + state.assassin);
					} else if (state.name == 'GameOverState') {
						setButtonVisibility(false, false);
						var winner = '';
						if (state.winner == 'GOOD') {
							winner = 'good';
						} else if (state.winner == 'EVIL') {
							winner = 'evil';
						}
						setStatusMessage('Game over, ' + winner + ' wins.');
					}
					me.updateGameTask.delay(1000);
				}
			});
		});
	}
});