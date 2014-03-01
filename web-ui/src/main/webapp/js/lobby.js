Ext.onReady(function() {
	
	var userName = false;

	var responseWrapperReader = {
		type: 'json',
		root: 'data'
	};
	
	function createGame_click() {
		Ext.Ajax.request({
			url: '/server/rules',
			success: function(response) {
				var wrapper = Ext.JSON.decode(response.responseText);;
				if (!isSuccessful("Create Game", wrapper)) {
					return;
				}
				var rules = wrapper.data;
				var rulesConfig = [];
				Ext.Array.each(rules, function(rule) {
					Ext.Array.push(rulesConfig, {
						boxLabel: rule.value,
						inputValue: rule.key
					});
				});

				Ext.create('Ext.window.Window', {
				    title: 'Create New Game',
				    itemId: 'createNewGameWindow',
				    modal: true,
				    width: 400,
				    defaults: { padding: '4' },
				    layout: { 
				    	type: 'vbox',
				    	align: 'stretch'
				    },
				    items: [{
				    	xtype: 'panel',
				    	layout: 'hbox',
				    	items: [{
					    	xtype: 'combo',
					    	margin: '0 0 0 4',
					    	fieldLabel: 'Roles:',
					    	itemId: 'roleCombo',
					    	displayField: 'value',
					    	valueField: 'key',
					    	store: {
					    		fields: ['key', 'value'],
					    		proxy: {
					    			type: 'ajax',
					    			url: '/server/roles',
						    		reader: responseWrapperReader
					    		}
					    	}
				    	}, {
				    		xtype: 'button',
				    		text: 'Add',
				    		handler: function() {
					    		var combo = Ext.ComponentQuery.query('#roleCombo')[0];
				    			var grid = Ext.ComponentQuery.query('#roleGrid')[0];
				    			var roleDisplay = combo.getRawValue();
				    			var roleName = combo.getValue();
				    			if (roleDisplay != "") {
				    				grid.getStore().loadData([{ display: roleDisplay, name: roleName }], true);
				    			}
				    		}
				    	}, {
				    		xtype: 'button',
				    		text: 'Remove',
				    		handler: function() {
				    			var grid = Ext.ComponentQuery.query('#roleGrid')[0];
				    			var selected = grid.getSelectionModel().getSelection();
				    			if (selected.length > 0) {
				    				grid.getStore().remove(selected);
				    			}
				    		} 
				    	}]
				    }, {
				    	xtype: 'grid',
				    	itemId: 'roleGrid',
				    	columns: [{ text: 'Role Name', dataIndex: 'display', flex: 1 }],
				    	store: { fields: ['display', 'name'] }
				    }, {
				    	xtype: 'label',
				    	text: 'Mission player counts:'
				    }, {
				    	xtype: 'panel',
				    	layout: 'hbox',
				    	itemId: 'missionPlayerCounts',
				    	defaults: { xtype: 'textfield', flex: 1 },
				    	items: [{},{},{},{},{}] // 5 missions
				    }, {
				    	xtype: 'label',
				    	text: 'Mission failure counts:'
				    }, {
				    	xtype: 'panel',
				    	layout: 'hbox',
				    	itemId: 'missionFailureCounts',
				    	defaults: { xtype: 'textfield', flex: 1, value: '1' },
				    	items: [{},{},{},{},{}] // 5 missions
				    }, {
				    	xtype: 'panel',
				    	itemId: 'rulesPanel',
				    	layout: {
				    		type: 'vbox',
				    		align: 'stretch'
				    	},
				    	title: 'Rules',
				    	flex: 1,
				    	defaults: {
				    		xtype: 'checkbox',
				    		margin: '0 0 0 4'
				    	},
				    	items: rulesConfig
				    }, {
				    	xtype: 'panel',
				    	defaults: { margin: '3' },
				    	items: [{
					    	xtype: 'button',
					    	text: 'Create Game',
					    	handler: function() {
					    		var window = Ext.ComponentQuery.query('#createNewGameWindow')[0];
					    		var params = {};
					    		params.role = [];
					    		window.down('#roleGrid').getStore().each(function(role) {
					    			Ext.Array.push(params.role, role.data.name);
					    		});
					    		params.rule = [];
					    		window.down('#rulesPanel').items.each(function(rule) {
					    			if (rule.getValue()) {
					    				Ext.Array.push(params.rule, rule.getSubmitValue());
					    			}
					    		});
					    		params.numPlayers = [];
					    		window.down('#missionPlayerCounts').items.each(function(count) {
					    			Ext.Array.push(params.numPlayers, count.getValue());
					    		});
					    		params.numFailures = [];
					    		window.down('#missionFailureCounts').items.each(function(count) {
					    			Ext.Array.push(params.numFailures, count.getValue());
					    		});
					    		params.owner = userName;
					    		Ext.Ajax.request({
					    			url: '/server/newgame',
					    			params: params,
					    			success: function(response) {
					    				var wrapper = Ext.JSON.decode(response.responseText);
					    				if (!isSuccessful("Create Game", wrapper)) {
					    					return;
					    				}
						    			window.close();
					    			},
					    			failure: function(response) {
					    				Ext.Msg.alert('Create Game Error', 'Could not create game: ' + response.responseText);
					    			}
					    		});
					    	}
					    }, {
					    	xtype: 'button',
					    	text: 'Cancel', 
					    	handler: function() {
					    		Ext.ComponentQuery.query('#createNewGameWindow')[0].close();
					    	}
					    }]
				    }] 
				}).show();
			}
		});
	}
	
	function disableAllButtons() {
		Ext.ComponentQuery.query('#joinGameButton')[0].setDisabled(true);
		Ext.ComponentQuery.query('#leaveGameButton')[0].setDisabled(true);
		Ext.ComponentQuery.query('#deleteGameButton')[0].setDisabled(true);
		Ext.ComponentQuery.query('#enterGameButton')[0].setDisabled(true);
		Ext.StoreManager.lookup('playersStore').removeAll();
	}
	
	if (!userName) {
		Ext.Msg.show({
			title: 'Missing User Name',
			msg: 'Enter User Name:',
			width: 250,
			buttons: Ext.Msg.OKCANCEL,
			modal: true,
			prompt: true,
			fn: function(button, text) {
				if (button == 'ok' && text != '') {
					userName = text;
				}
			}
		});
	}

	Ext.create('Ext.container.Viewport', {
		layout: {
			type: 'vbox',
			align: 'stretch'
		},
		items: [{
			xtype: 'panel',
			flex: 1,
			title: 'The Resistance - Lobby',
			layout: {
				type: 'vbox',
				align: 'stretch'
			},
			items: [{
				xtype: 'panel',
				height: '75',
				defaults: { 
					margin: '5',
					xtype: 'button'
				},
				layout: {
					type: 'hbox',
					align: 'stretch'
				},
				items: [{
					text: 'Create New Game',
					handler: createGame_click
				}, {
					text: 'Delete Game',
					itemId: 'deleteGameButton',
					disabled: true,
					handler: function() {
						Ext.Msg.confirm('Delete Game', 'Are you sure you want to delete this game?', function(button) {
							if (button == 'yes') {
								var newGamesGrid = Ext.ComponentQuery.query('#newGamesGrid')[0];
								var gameId = newGamesGrid.getSelectionModel().getSelection()[0].get('gameId');
								Ext.Ajax.request({
									url: '/server/delete',
									params: { gameId: gameId },
									success: errorCheckingHandler("Delete Game")
								});
							}
						});
					}
				}, {
					text: 'Join Game',
					itemId: 'joinGameButton',
					disabled: true,
					handler: function() {
						var newGamesGrid = Ext.ComponentQuery.query('#newGamesGrid')[0];
						var gameId = newGamesGrid.getSelectionModel().getSelection()[0].get('gameId');
						Ext.Ajax.request({
							url: '/server/join',
							params: { gameId: gameId, player: userName },
							success: errorCheckingHandler("Join Game")
						});
					}
				}, {
					text: 'Leave Game',
					itemId: 'leaveGameButton',
					disabled: true,
					handler: function() {
						var newGamesGrid = Ext.ComponentQuery.query('#newGamesGrid')[0];
						var gameId = newGamesGrid.getSelectionModel().getSelection()[0].get('gameId');
						Ext.Ajax.request({
							url: '/server/leave',
							params: { gameId: gameId, player: userName },
							success: errorCheckingHandler("Leave Game")
						});
					}
				}, {
					text: 'Enter Game',
					itemId: 'enterGameButton',
					disabled: true,
					href: '/web-ui/game.html',
					hrefTarget: '_blank',
					listeners: {
						click: function() {
							var startedGamesGrid = Ext.ComponentQuery.query('#startedGamesGrid')[0];
							var gameId = startedGamesGrid.getSelectionModel().getSelection()[0].get('gameId');
							Ext.ComponentQuery.query('#enterGameButton')[0].setParams({ gameId: gameId, userName: userName });
						}
					}
				}]
			}, {
				xtype: 'panel',
				flex: 1,
				title: 'Existing Games',
				layout: {
					type: 'hbox',
					align: 'stretch'
				},
				defaults: { 
					margin: '5', 
					flex: 1 
				},
				items: [{
					xtype: 'panel',
					border: 0,
					layout: {
						type: 'vbox',
						align: 'stretch'
					},
					items: [{
						xtype: 'grid',
						title: 'New Games',
						itemId: 'newGamesGrid',
						flex: 1,
						margin: '0 0 5 0',
						columns: [{ text: 'Owner', dataIndex: 'owner', flex: 1 },
						          { text: 'Current Players', dataIndex: 'currentPlayers', flex: 1 }, 
						          { text: 'Total Players', dataIndex: 'totalPlayers', flex: 1 }],
						viewConfig: { loadMask: false },
						store: {
							storeId: 'newGamesStore',
				    		fields: ['owner', 'gameId', 'currentPlayers', 'totalPlayers'],
				    		data: []
				    	}
					}, {
						xtype: 'grid',
						title: 'Started Games',
						itemId: 'startedGamesGrid',
						flex: 1,
						margin: '5 0 0 0',
						columns: [{ text: 'Owner', dataIndex: 'owner', flex: 1 },
						          { text: 'Number of Players', dataIndex: 'currentPlayers', flex: 1 }],
						viewConfig: { loadMask: false },
						store: {
							storeId: 'startedGamesStore',
				    		fields: ['owner', 'gameId', 'currentPlayers'],
				    		data: []
				    	}
					}]
				}, {
					xtype: 'grid',
					title: 'Players',
					itemId: 'playersGrid',
					columns: [{ text: 'Name', dataIndex: 'name', flex: 1 }],
					store: {
						storeId: 'playersStore',
						fields: ['name']
					}
				}]
			}]
		}]
	});
	
	var loadPlayersTask = {
		gameId: false,
		selectedStore: false,
		isStartedGameSelected: false,
		newSelection: true
	};
	loadPlayersTask.task = new Ext.util.DelayedTask(function() {
		var gameId = loadPlayersTask.gameId;
		var playersGrid = Ext.ComponentQuery.query('#playersGrid')[0];
		if (gameId != false) {
			Ext.Ajax.request({
				url: '/server/players',
				params: { gameId: gameId },
				success: function(response) {
					try {
						var wrapper = Ext.JSON.decode(response.responseText);
						if (!isSuccessful("Player Load", wrapper)) {
							return;
						}
						if (!gameId || loadPlayersTask.selectedStore.find('gameId', gameId) == -1) {
							disableAllButtons();
							return;
						} 
						
						var playersStore = playersGrid.getStore();
						var players = wrapper.data;
						if (loadPlayersTask.newSelection) {
							loadPlayersTask.newSelection = false;
							playersStore.loadData(players, false);
							playersGrid.setLoading(false);
						} else {
							var playersExist = {};
							Ext.Array.each(players, function(player) {
								playersExist[player.name] = true;
								if (playersStore.find('name', player.name) == -1) {
									playersStore.loadData([player], true);
								}
							});
							var remove = [];
							playersStore.each(function(player) {
								if (!(player.get('name') in playersExist)) {
									Ext.Array.push(remove, player);
								}
							});
							if (remove.length > 0) {
								playersStore.remove(remove);
							}
							if (!loadPlayersTask.isStartedGameSelected) {
								var isInGame = playersStore.find('name', userName) != -1;
								Ext.ComponentQuery.query('#joinGameButton')[0].setDisabled(isInGame);
								Ext.ComponentQuery.query('#leaveGameButton')[0].setDisabled(!isInGame);
							}
						}
						loadPlayersTask.task.delay(1000);
					} finally {
						if (loadPlayersTask.newSelection) {
							playersGrid.setLoading(false);
						}
					}
				},
				failure: function() {
					disableAllButtons();
					playersGrid.setLoading(false);
				}
			});
		} else {
			disableAllButtons();
		}
	});
	
	var newGamesGrid = Ext.ComponentQuery.query('#newGamesGrid')[0];
	var startedGamesGrid = Ext.ComponentQuery.query('#startedGamesGrid')[0];
	function gameSelected(source, selected) {
		var isOwner = false;
		if (selected.length > 0) {
			var playersGrid = Ext.ComponentQuery.query('#playersGrid')[0];
			loadPlayersTask.newSelection = true;
			playersGrid.setLoading(true);
			loadPlayersTask.gameId = selected[0].get('gameId');
			loadPlayersTask.selectedStore = this.getStore();
			if (selected[0].get('owner') == userName) {
				isOwner = true;
			}
			loadPlayersTask.isStartedGameSelected = (startedGamesGrid == this);
			if (loadPlayersTask.isStartedGameSelected) {
				newGamesGrid.getSelectionModel().deselectAll(true);
				Ext.ComponentQuery.query('#joinGameButton')[0].setDisabled(true);
				Ext.ComponentQuery.query('#leaveGameButton')[0].setDisabled(true);
				Ext.ComponentQuery.query('#deleteGameButton')[0].setDisabled(true);
				Ext.ComponentQuery.query('#enterGameButton')[0].setDisabled(false);
			} else {
				startedGamesGrid.getSelectionModel().deselectAll(true);
				Ext.ComponentQuery.query('#deleteGameButton')[0].setDisabled(!isOwner);
				Ext.ComponentQuery.query('#enterGameButton')[0].setDisabled(true);
			}
			loadPlayersTask.task.delay(0);
		} else {
			loadPlayersTask.gameId = false;
		}
	}
	newGamesGrid.getSelectionModel().on('selectionchange', gameSelected, newGamesGrid);
	startedGamesGrid.getSelectionModel().on('selectionchange', gameSelected, startedGamesGrid);
	
	var loadGamesTask = {};
	loadGamesTask.task = new Ext.util.DelayedTask(function() {
		var newGamesStore = newGamesGrid.getStore();
		var startedGamesStore = startedGamesGrid.getStore();
		Ext.Ajax.request({
			url: '/server/games',
			success: function(response) {
				var wrapper = Ext.JSON.decode(response.responseText);
				if (!isSuccessful("Load Games", wrapper)) {
					return;
				}
				var games = wrapper.data;
				var startedGamesExist = {};
				var newGamesExist = {};
				Ext.Array.each(games, function(game) {
					var useGamesExist;
					var useStore;
					if (game.started) {
						useStore = startedGamesStore;
						useGamesExist = startedGamesExist; 
					} else {
						useStore = newGamesStore;
						useGamesExist = newGamesExist;
					}
					var existing = useStore.findRecord('gameId', game.gameId);
					useGamesExist[game.gameId] = true;
					if (existing == null) {
						useStore.loadData([game], true);
					} else {
						if (existing.get('currentPlayers') != game.currentPlayers) {
							existing.set('currentPlayers', game.currentPlayers);
						}
					}
				});
				var removeNewGames = [];
				var removeStartedGames = [];
				newGamesStore.each(function(game) {
					if (!(game.get('gameId') in newGamesExist)) {
						Ext.Array.push(removeNewGames, game);
					}
				});
				startedGamesStore.each(function(game) {
					if (!(game.get('gameId') in startedGamesExist)) {
						Ext.Array.push(removeStartedGames, game);
					}
				});
				
				if (removeNewGames.length > 0) {
					newGamesStore.remove(removeNewGames);
				}
				if (removeStartedGames.length > 0) {
					startedGamesStore.remove(removeStartedGames);
				}
				if (newGamesGrid.getSelectionModel().getSelection().length == 0 &&
						startedGamesGrid.getSelectionModel().getSelection().length == 0) {
					loadPlayersTask.gameId = false;
				}
				loadGamesTask.task.delay(1000);
			}
		});
	});
	loadGamesTask.task.delay(1000);
});