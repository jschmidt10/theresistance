Ext.define('js.game.AssassinationWindow', {
	extend: 'Ext.window.Window',
	
	/** The master game id of the game. Should be initialized by the config. */
	gameId: '',
	
	width: 400,
	modal: true,
    defaults: { 
    	padding: '5',
    	xtype: 'radio',
    	name: 'player',
    	margin: '0 0 0 4'
    },
    layout: { 
    	type: 'vbox',
    	align: 'stretch'
    },
	
	initComponent: function() {
		var window = this;
		window.callParent();

		var playersStore = Ext.StoreManager.lookup('playersStore');
		var assassinationWindowItems = [];
		playersStore.each(function(player) {
			Ext.Array.push(assassinationWindowItems, { 
				boxLabel: player.data.name,
				inputValue: player.data.name
			});
		});
		
		Ext.Array.push(assassinationWindowItems, { 
			xtype: 'label',
			text: 'Select a player to assassinate.'
		});
		Ext.Array.push(assassinationWindowItems, { 
			xtype: 'button',
			text: 'Assassinate Player',
			scope: window,
			handler: window.assassinate_click
		});
		
		window.add(assassinationWindowItems);

		var options = window.query("radio");
		options[0].setValue(true);
	},
	
	assassinate_click: function() {
		
		var window = this;
		
		var options = window.query("radio");
		var selected = '';
		for (var i = 0; i < options.length; i++) {
			if (options[i].getValue()) {
				selected = options[i].getSubmitValue();
				break;
			}
		}
		
		Ext.Ajax.request({
			url: '/server/action',
			params: {
				gameId: window.gameId,
				action: Ext.JSON.encode({ assassinated: selected })
			},
			success: function(response) {
				var wrapper = Ext.JSON.decode(response.responseText);
				if (isSuccessful("Assassination", wrapper)) {
					window.close();
				}
			}
		});
	}
});