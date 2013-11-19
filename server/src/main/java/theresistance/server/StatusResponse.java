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
		return new StatusResponse(Status.SUCCESS, "success", gameId, data);
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
		return new StatusResponse(Status.FAILURE, message, gameId, null);
	}

	public enum Status
	{
		SUCCESS, FAILURE
	}

	private Status status;
	private String message;
	private String gameId;
	private Object data;

	private StatusResponse(Status status, String message, String gameId, Object data)
	{
		this.status = status;
		this.message = message;
		this.gameId = gameId;
		this.data = data;
	}

	public Status getStatus()
	{
		return status;
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
