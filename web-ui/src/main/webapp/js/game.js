
var clientGameConfig = {};

function loadDisplay(gameId, userName) {
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
					flex: 1
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