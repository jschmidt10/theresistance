var userName = false;

Ext.onReady(function() {
	
	var loadGamesWrapper = {};
	loadGamesWrapper.task = new Ext.util.DelayedTask(function() {
		Ext.Ajax.request({
			url: 'http://192.168.57.4:8080/server/newgames',
			success: function(response) {
				Ext.Msg.alert("Success", response.responseText);
				loadGamesWrapper.task.delay(1000);
			},
			failure: function(response) {
				Ext.Msg.alert("Failed", response);
			}
		});
	});
	
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
					text: 'Create New Game'
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
					columns: [{ text: 'Creator', dataItem: 'creatorName', flex: 1 }]
				}, {
					xtype: 'grid',
					title: 'Players',
					columns: [{ text: 'Name', dataItem: 'name', flex: 1 }]
				}]
			}]
		}]
	});
	
	loadGamesWrapper.task.delay(1);
});