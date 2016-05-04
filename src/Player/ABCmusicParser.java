package Player;
/**
 * parsing the ReadABC file data
 * @author siddharth
 *
 */
public class ABCmusicParser {
	
	String[] splitted;
	int currentPosition;
	
	public ABCmusicParser(String lexed)
	{
		splitted = lexed.split("\n");
		currentPosition = 0;
	}
	public String[] getSplit() {
		return this.splitted;
	}
	public ABCmusicToken next()
	{
		if (currentPosition>=splitted.length) return null;
		String[] a = getSplit() [currentPosition].split(" ");
		ABCmusicTokenType token = ABCmusicTokenType.valueOf(a[0]);
		String value = a[1];
		ABCmusicToken t = new ABCmusicToken(token, value);
		++currentPosition;
		return t;
	}
	

}
