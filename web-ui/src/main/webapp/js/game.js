Ext.onReady(function() {
	Ext.create('Ext.container.Viewport', {
		layout: {
			type: 'hbox',
			align: 'stetch'
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
					columns: [{
						text: 'Name',
						dataIndex: 'name'
					}, {
						text: 'Role',
						dataIndex: 'role'
					}]
				}]
			}, {
				xtype: 'panel',
				title: 'Game',
				flex: 6,
				layout: {
					type: 'vbox',
					align: 'stretch'
				}
			}]
		}]
	});
});