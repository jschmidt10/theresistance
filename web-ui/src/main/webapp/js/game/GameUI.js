Ext.define('js.game.GameUI', {
	extend: 'Ext.container.Viewport',
	
	/** Id of the game being displayed. This should be set by the ui config. */
	gameId: undefined,
	
	/** Name of the user using this display. This should be set by the ui config. */
	userName: undefined,
	
	/** Game configuration object that contains the roles/rules/mission  
	 * counts for this game. This should be set by the ui config. */
	gameConfig: undefined,
	
	layout: {
		type: 'hbox',
		align: 'stretch'
	},
	
	initComponent: function() {
		var me = this;
		me.callParent();
		me.loadDisplay();
		
		// this loads/manages the state of the game
		Ext.create('js.game.store.GameStateStore', {
			gameId: me.gameId,
			userName: me.userName
		});
	},
	
	loadDisplay: function() {
		var me = this;
		me.add([{
			xtype: 'panel',
			title: 'The Resistance',
			flex: 1,
			layout: {
				type: 'hbox',
				align: 'stretch'
			},
			items: [
			Ext.create('js.game.PlayerListPanel', { gameId: me.gameId, userName: me.userName }), 
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
							gameConfig: me.gameConfig
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
				Ext.create('js.game.HistoryPanel', { gameId: me.gameId })]
			}]
		}]);
	}
});