package Player;
/**
 * Get type of token and value of that token
 * @author siddharth
 *
 */
public class ABCmusicToken {
	ABCmusicTokenType type;
	String value;
	
	public ABCmusicToken(ABCmusicTokenType t, String s)
	{
		this.type = t;
		this.value = s.trim();
	}
	
	public String getvalue()
	{
		return this.value;
	}
}
