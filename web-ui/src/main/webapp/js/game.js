
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
		// TODO: add this
	}
	
	function submitApproval(approval, thisButotn, otherButton) {
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
				setButtonState(thisButotn, otherButton);
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
				setButtonState(thisButotn, otherButton);
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
			items: [{
				xtype: 'panel',
				title: 'Player List',
				flex: 1,
				layout: {
					type: 'vbox',
					align: 'stretch'
				},
				items: [{
					itemId: 'playersGrid',
					xtype: 'grid',
					columns: [{ text: 'Pos.', dataIndex: 'position', flex: 1}, 
					          { text: 'Name', dataIndex: 'name', flex: 3 }, 
					          { text: 'Role', dataIndex: 'role', flex: 2 }],
					store: {
						storeId: 'playersStore',
						fields: ['position', 'name', 'role']
					}
				}]
			}, {
				xtype: 'panel',
				title: 'Game',
				flex: 4,
				layout: {
					type: 'vbox',
					align: 'stretch'
				},
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
				}, {
					xtype: 'panel',
					margin: '0',
					border: 0,
					flex: 2,
					defaults: { margin: '8' },
					layout: {
						type: 'hbox',
						align: 'stretch'
					},
					items: [{
						xtype: 'grid',
						title: 'Proposal History',
						itemId: 'proposalGrid',
						flex: 1,
						columns: [{ text: 'Index', dataIndex: 'index', width: '30' },
						          { text: 'Leader', dataIndex: 'leader', flex: 1 }, 
						          { text: 'Proposal', dataIndex: 'proposal', flex: 3 }, 
						          { text: 'Accepts', dataIndex: 'accepts', width: '30' },
						          { text: 'Declines', dataIndex: 'declines', width: '30' }],
						store: {
							storeId: 'proposalHistory',
							fields: ['index', 'leader', 'proposal', 'accepts', 'declines', 'votes'],
							loadProposals: function(mission) {
								var me = this;
								Ext.Ajax.request({
									url: '/server/proposals',
									params: {
										gameId: gameId,
										round: mission
									},
									success: function(response) {
										var wrapper = Ext.JSON.decode(response.responseText);
										if (!isSuccessful("Proposal Load", wrapper)) {
											return;
										}
										var proposals = wrapper.data;
										var proposalsToLoad = [];
										var index = 1;
										Ext.Array.each(proposals, function(proposal) {
											var accepts = 0;
											var declines = 0;
											for (var player in proposal.votes) {
												if (proposal.votes[player] == 'SEND') {
													accepts++;
												} else {
													declines++;
												}
											}
											Ext.Array.push(proposalsToLoad, {
												index: index,
												leader: proposal.leader,
												accepts: accepts,
												declines: declines,
												votes: proposal.votes
											});
										});
										me.loadData(proposalsToLoad);
									}
								});
							}
						}
					}, {
						xtype: 'grid',
						title: 'Voting History',
						flex: 1,
						columns: [{ text: 'Name', dataIndex: 'name', flex: 2 },
						          { text: 'Vote', dataIndex: 'vote', flex: 1 }],
						store: {
							storeId: 'votingHistory',
							fields: ['name', 'vote']
						}
					}]
				}]
			}]
		}]
	});
	
	var proposalsGrid = Ext.ComponentQuery.query('#proposalGrid')[0]; 
	proposalsGrid.getSelectionModel().on('selectionchanged', function(source, selected) {
		var voteStore = Ext.StoreManager.lookup('votingHistory');
		var voteHistory = [];
		if (selected.length == 0) {
			voteStore.removeAll();
			return;
		}
		var votes = selected[0].data.votes;
		for (var player in votes) {
			var voteText = (votes[player] == 'SEND' ? 'Accept' : 'Reject');
			Ext.Array.push(voteHistory, { name: player, vote: voteText });
		}
		voteStore.loadData(voteHistory);
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
						setProposeHandler(state.numParticipants);
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
	
	var loadPlayersTask = {};
	loadPlayersTask.task = new Ext.util.DelayedTask(function() {
		Ext.Ajax.request({
			url: '/server/gamePlayers',
			params: {
				gameId: gameId,
				player: userName
			},
			success: function(response) {
				var wrapper = Ext.JSON.decode(response.responseText);
				if (!isSuccessful("Player Info Load", wrapper)) {
					return;
				}
				var playersStore = Ext.StoreManager.lookup('playersStore');
				if (playersStore.getCount() == 0) {
					var playersInfo = wrapper.data;
					var players = [];
					for (var i = 0; i < playersInfo.order.length; i++) {
						var name = playersInfo.order[i];
						Ext.Array.push(players, { 
							position: (i + 1), 
							name: name, 
							role: playersInfo.roles[name] 
						});
					}
					playersStore.loadData(players);
				}
				
				loadPlayersTask.task.delay(1000);
			},
			failure: function(response) {
				Ext.Msg.alert("Player Info Load Error", response.responseText);
			}
		});
	});
	loadPlayersTask.task.delay(1000);
	
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