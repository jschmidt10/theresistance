package theresistance.server.view;

public class ResultsView
{
	private int index;
	private int numFails;
	private String result;

	public ResultsView(int index, int numFails, String result)
	{
		this.index = index;
		this.numFails = numFails;
		this.result = result;
	}

	public int getIndex()
	{
		return index;
	}

	public int getNumFails()
	{
		return numFails;
	}

	public String getResult()
	{
		return result;
	}
}
