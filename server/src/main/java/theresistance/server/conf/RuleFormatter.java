package theresistance.server.conf;

import theresistance.core.PostRoundEventHandler;

/**
 * OptionFormatter for Rules
 */
public class RuleFormatter implements OptionFormatter<PostRoundEventHandler>
{
	@Override
	public String getDescription(PostRoundEventHandler option)
	{
		return option.getRuleName();
	}
}
