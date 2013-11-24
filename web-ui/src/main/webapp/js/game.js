

Ext.onReady(function() {
	
	var gameId;
	var userName = "Andrew";
	
	function loadDisplay() {
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
						title: 'Game Progress',
						flex: 1
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
							          { text: 'Declines', dataIndex: 'declines', width: '30' }]
						}, {
							xtype: 'grid',
							title: 'Voting History',
							flex: 1,
							columns: [{ text: 'Name', dataIndex: 'name', flex: 2 },
							          { text: 'Vote', dataIndex: 'vote', flex: 1 }]
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
	}
	
	Ext.Ajax.request({
		url: '/server/newgames',
		success: function(response) {
			var game = Ext.JSON.decode(response.responseText).data[0];
			gameId = game.gameId;
			loadDisplay();
		}
	});
});