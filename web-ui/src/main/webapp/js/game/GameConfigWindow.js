Ext.define('js.game.GameConfigWindow', {
	extend: 'Ext.window.Window',
	
	/** The game config object that contains all 
	 * the roles, rules, etc. This should be set on 
	 * the config of this window. */
	gameConfig: undefined,
	
	title: 'Game Config',
	modal: false,
	height: 400,
	width: 500,
	layout: {
		type: 'vbox',
		align: 'stretch'
	},
	
	initComponent: function() {
		var me = this;
		me.callParent();
		me.add([{
			xtype: 'grid',
			title: 'Roles',
			flex: 1,
			columns: [{ text: 'Name', dataIndex: 'name', flex: 1 }],
			store: {
				fields: ['name'],
				data: me.gameConfig.roles
			}
		}, {
			xtype: 'grid',
			title: 'Rules',
			flex: 1,
			columns: [{ text: 'Name', dataIndex: 'ruleName', flex: 1 }],
			store: {
				fields: ['ruleName'],
				data: me.gameConfig.handlers
			}
		}, {
			xtype: 'grid',
			title: 'Missions',
			flex: 1,
			columns: [
			    { text: '#', dataIndex: 'index', flex: 1 },
			    { text: '# Players', dataIndex: 'numParticipants', flex: 1 },
			    { text: '# Failures', dataIndex: 'requiredFails', flex: 1 }
			],
			store: {
				fields: ['index', 'numParticipants', 'requiredFails'],
				data: me.gameConfig.missions
			}
		}]);
	}
});