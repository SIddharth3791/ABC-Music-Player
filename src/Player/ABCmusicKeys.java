package Player;

import java.util.HashMap;
import java.util.Map;
/**
 * Mapping the Key with values. to know simple note of Key C
 * @author siddharth
 *
 */
public class ABCmusicKeys {
	
	public static Map<String,String> GetKeys (String key){
		
		Map<String, String> Keys = new HashMap<String, String>();
		Keys.put("A", "A");
		Keys.put("B", "B");
		Keys.put("C", "C");
		Keys.put("D", "D");
		Keys.put("E", "E");
		Keys.put("F", "F");
		Keys.put("G", "G");
		
		if (key.equals("C")|| key.equals("Am"))
		{
			return Keys;
		}else{
		
		return Keys;
		}
		
	}

}
