var userName = false;

Ext.onReady(function() {
	
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
				defaults: { margin: '5' },
				layout: {
					type: 'hbox',
					align: 'stretch'
				},
				items: [{
					xtype: 'button',
					text: 'Create New Game',
					handler: function() {
						Ext.Ajax.request({
							url: '/server/rules',
							success: function(response) {
								var rules = Ext.JSON.decode(response.responseText);
								var rulesConfig = [];
								Ext.Array.each(rules, function(rule) {
									Ext.Array.push(rulesConfig, {
										boxLabel: rule.display,
										inputValue: rule.name
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
									    	displayField: 'display',
									    	valueField: 'name',
									    	store: {
									    		fields: ['name', 'display'],
									    		proxy: {
									    			type: 'ajax',
									    			url: '/server/roles'
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
								    	store: { fields: ['name', 'display'] }
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
				}, {
					xtype: 'button',
					text: 'Join Game'
				}, {
					xtype: 'button',
					text: 'Delete Game',
					disabled: true
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
					xtype: 'grid',
					title: 'Games',
					columns: [{ text: 'Owner', dataIndex: 'owner', flex: 1 },
					          { text: 'Current Players', dataIndex: 'currentPlayers', flex: 1 }, 
					          { text: 'Total Players', dataIndex: 'totalPlayers', flex: 1 }],
					viewConfig: { loadMask: false },
					store: {
						storeId: 'gamesStore',
			    		fields: ['owner', 'gameId', 'currentPlayers', 'totalPlayers'],
			    		data: [{ owner: 'andrew', gameId: 'abc', currentPlayers: 1, totalPlayers: 5  }]
			    	}
				}, {
					xtype: 'grid',
					title: 'Players',
					columns: [{ text: 'Name', dataItem: 'name', flex: 1 }]
				}]
			}]
		}]
	});
	
	var loadGamesTask = {};
	loadGamesTask.task = new Ext.util.DelayedTask(function() {
		var gamesStore = Ext.StoreManager.lookup('gamesStore');
		Ext.Ajax.request({
			url: '/server/newgames',
			success: function(response) {
				var games = Ext.JSON.decode(response.responseText);
				var gamesExist = {};
				Ext.Array.each(games, function(game) {
					var existing = gamesStore.findRecord('gameId', game.gameId);
					gamesExist[game.gameId] = true;
					if (existing == null) {
						gamesStore.loadData([game], true);
					} else {
						if (existing.get('currentPlayers') != game.currentPlayers) {
							existing.set('currentPlayers', game.currentPlayers);
						}
					}
				});
				var remove = [];
				gamesStore.each(function(game) {
					if (!(game.get('gameId') in gamesExist)) {
						Ext.Array.push(remove, game);
					}
				});
				if (remove.length > 0) {
					gamesStore.remove(remove);
				}
				loadGamesTask.task.delay(1000);
			}
		});
	});
	loadGamesTask.task.delay(1000);
});