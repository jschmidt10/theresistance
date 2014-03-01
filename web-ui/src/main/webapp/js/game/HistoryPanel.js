Ext.define('js.game.HistoryPanel', {
	extend: 'Ext.panel.Panel',

	/** The game id this store is being used for. This should be set in the store config. */
	gameId: undefined,
	
	margin: '0',
	border: 0,
	flex: 2,
	defaults: { margin: '8' },
	
	layout: {
		type: 'hbox',
		align: 'stretch'
	},
	
	initComponent: function() {
		var me = this;
		me.callParent();
		
		me.loadDisplay();
		
		var proposalsGrid = me.down('#proposalGrid'); 
		thisisabigvariable = proposalsGrid;
		proposalsGrid.getSelectionModel().on('selectionchange', function(source, selected) {
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
	},
	
	loadDisplay: function() {
		var me = this;
		me.add([{
			xtype: 'grid',
			title: 'Proposal History',
			itemId: 'proposalGrid',
			flex: 1,
			columns: [{ text: 'Index', dataIndex: 'index', flex: 1 },
			          { text: 'Leader', dataIndex: 'leader', flex: 2 }, 
			          { text: 'Proposal', dataIndex: 'proposal', flex: 6 }, 
			          { text: 'Accepts', dataIndex: 'accepts', flex: 1 },
			          { text: 'Declines', dataIndex: 'declines', flex: 1 }],
			store: Ext.create('js.game.store.ProposalStore', { gameId: me.gameId })
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
		}]);
	}
	
});