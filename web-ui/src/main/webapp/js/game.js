Ext.onReady(function() {
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
					columns: [{
						text: 'Pos.',
						dataIndex: 'position',
						flex: 1
					}, {
						text: 'Name',
						dataIndex: 'name',
						flex: 3
					}, {
						text: 'Role',
						dataIndex: 'role',
						flex: 2
					}]
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
});