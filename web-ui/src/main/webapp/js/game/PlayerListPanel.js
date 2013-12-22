Ext.define('js.game.PlayerListPanel', {
	
	extend: 'Ext.panel.Panel',
	title: 'Player List',
	
	/** The game id. This should be set on this panel's config. */
	gameId: undefined,
	
	/** The user name of player. This should be set on this panel's config. */
	userName: undefined,
	
	flex: 1,
	layout: {
		type: 'vbox',
		align: 'stretch'
	},
	
	loadPlayersTask: undefined,
	
	initComponent: function() {
		var me = this;
		me.callParent();
		me.loadPlayersTask = new Ext.util.DelayedTask(function() {
			Ext.Ajax.request({
				url: '/server/gamePlayers',
				params: {
					gameId: me.gameId,
					player: me.userName
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
					
					me.loadPlayersTask.delay(1000);
				},
				failure: function(response) { }
			});
		});
		me.loadPlayersTask.delay(1000);
	},
	
	items: [{
		itemId: 'playersGrid',
		xtype: 'grid',
		columns: [{ text: 'Pos.', dataIndex: 'position', flex: 1 }, 
		          { text: 'Name', dataIndex: 'name', flex: 3 }, 
		          { text: 'Role', dataIndex: 'role', flex: 2 }],
		store: {
			storeId: 'playersStore',
			fields: ['position', 'name', 'role']
		}
	}],
	
});