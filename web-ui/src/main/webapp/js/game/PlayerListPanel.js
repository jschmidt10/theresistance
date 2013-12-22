Ext.define('js.game.PlayerListPanel', {
	extend: 'Ext.panel.Panel',
	title: 'Player List',
	layout: {
		type: 'vbox',
		align: 'stretch'
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
	}]
});