Ext.define('js.game.ProposalWindow', {
	extend: 'Ext.window.Window',
	
	/** Number of participants on the mission. Should be initialized by the config. */
	numberOfParticipants: undefined,

	/** The master game id of the game. Should be initialized by the config. */
	gameId: '',
	
	width: 400,
	modal: true,
    defaults: { 
    	padding: '5',
    	xtype: 'checkbox',
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
		var proposalWindowItems = [];
		playersStore.each(function(player) {
			Ext.Array.push(proposalWindowItems, { 
				boxLabel: player.data.name,
				inputValue: player.data.name,
		    	listeners: {
		    		change: window.playerSelectionChanged,
		    		scope: window
		    	}
			});
		});
		
		Ext.Array.push(proposalWindowItems, { 
			xtype: 'label',
			itemId: 'playersLeftLabel',
			text: 'Select ' + window.numberOfParticipants + ' more players.'
		});
		Ext.Array.push(proposalWindowItems, { 
			xtype: 'button',
			text: 'Propose Mission',
			disabled: true,
			scope: window,
			handler: window.proposeMission_click
		});
		
		window.add(proposalWindowItems);
	},
	
	getCheckBoxes: function() {
		return this.query('checkbox');
	},
	
	setSelectionDisabled: function(disabled) {
		var window = this;
		var checkboxes = window.getCheckBoxes();
		var proposalButton = window.down('button');
		Ext.Array.each(checkboxes, function (checkbox) {
			if (!checkbox.getValue()) {
				checkbox.setDisabled(disabled);
			}
		});
		proposalButton.setDisabled(!disabled);
	},
	
	proposeMission_click: function() {
		
		var window = this;
		var checkboxes = window.getCheckBoxes();
		var left = window.numberOfParticipants;
		var selected = [];
		
		Ext.Array.each(checkboxes, function(checkbox) {
			if (left > 0) {
				if (checkbox.getValue()) {
					left--;
					Ext.Array.push(selected, checkbox.getSubmitValue());
				}
			}
		});
		
		Ext.Ajax.request({
			url: '/server/propose',
			params: {
				gameId: window.gameId,
				players: selected
			},
			success: function(response) {
				var wrapper = Ext.JSON.decode(response.responseText);
				if (isSuccessful("Propose Mission", wrapper)) {
					window.close();
				}
			}
		});
	},
	
	playerSelectionChanged: function() {
		var window = this;
		var playersLeftLabel = window.down('#playersLeftLabel');
		var checkboxes = window.query('checkbox');
		var selected = 0;
		Ext.Array.each(checkboxes, function(checkbox) { 
			if (checkbox.getValue()) {
				selected++;
			}
		});
		var left = window.numberOfParticipants - selected;
		var message = '';
		if (left <= 0) {
			message = 'All players have been selected.';
			window.setSelectionDisabled(true);
		} else {
			message = 'Select ' + left + ' more players.';
			window.setSelectionDisabled(false);
		}
		playersLeftLabel.setText(message);
	}
});