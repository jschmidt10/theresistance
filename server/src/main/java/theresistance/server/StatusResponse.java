package theresistance.server;

/**
 * A generic response message to send to the front end
 */
public class StatusResponse
{
	/**
	 * A success response
	 * 
	 * @param gameId
	 * @return status
	 */
	public static StatusResponse success(String gameId, Object data)
	{
		return new StatusResponse(true, "success", gameId, data);
	}

	/**
	 * A failure reponse
	 * 
	 * @param message
	 * @param gameId
	 * @return status
	 */
	public static StatusResponse failure(String message, String gameId)
	{
		return new StatusResponse(false, message, gameId, null);
	}

	private boolean success;
	private String message;
	private String gameId;
	private Object data;

	private StatusResponse(boolean success, String message, String gameId, Object data)
	{
		this.success = success;
		this.message = message;
		this.gameId = gameId;
		this.data = data;
	}

	public boolean getSuccess()
	{
		return success;
	}

	public String getMessage()
	{
		return message;
	}

	public String getGameId()
	{
		return gameId;
	}

	public Object getData()
	{
		return data;
	}
}
