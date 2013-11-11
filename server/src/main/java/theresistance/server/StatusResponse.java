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
	public static StatusResponse success(String gameId)
	{
		return new StatusResponse(Status.SUCCESS, "success", gameId);
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
		return new StatusResponse(Status.FAILURE, message, gameId);
	}

	public enum Status
	{
		SUCCESS, FAILURE
	}

	private Status status;
	private String message;
	private String gameId;

	private StatusResponse(Status status, String message, String gameId)
	{
		this.status = status;
		this.message = message;
		this.gameId = gameId;
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
}
