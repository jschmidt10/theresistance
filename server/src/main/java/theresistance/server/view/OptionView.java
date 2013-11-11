package theresistance.server.view;

/**
 * View bean for game options
 */
public class OptionView
{
	private String name;
	private String display;

	public OptionView(String name, String display)
	{
		this.name = name;
		this.display = display;
	}

	public String getName()
	{
		return name;
	}

	public String getDisplay()
	{
		return display;
	}
}
