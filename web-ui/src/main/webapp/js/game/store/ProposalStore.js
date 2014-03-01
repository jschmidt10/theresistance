Ext.define('js.game.store.ProposalStore', {
	extend: 'Ext.data.Store',
	
	/** The game id this store is being used for. This should be set in the store config. */
	gameId: undefined,
	
	storeId: 'proposalHistory',
	fields: ['index', 'leader', 'proposal', 'accepts', 'declines', 'votes'],
	
	loadProposals: function(mission) {
		var me = this;
		Ext.Ajax.request({
			url: '/server/proposals',
			params: {
				gameId: me.gameId,
				round: mission
			},
			success: function(response) {
				var wrapper = Ext.JSON.decode(response.responseText);
				if (!isSuccessful("Proposal Load", wrapper)) {
					return;
				}
				var proposals = wrapper.data;
				var proposalsToLoad = [];
				var index = 1;
				Ext.Array.each(proposals, function(proposal) {
					var accepts = 0;
					var declines = 0;
					for (var player in proposal.votes) {
						if (proposal.votes[player] == 'SEND') {
							accepts++;
						} else {
							declines++;
						}
					}
					Ext.Array.push(proposalsToLoad, {
						index: index,
						leader: proposal.leader,
						accepts: accepts,
						declines: declines,
						votes: proposal.votes,
						proposal: proposal.participants
					});
					index++;
				});
				me.loadData(proposalsToLoad);
			}
		});
	}
});