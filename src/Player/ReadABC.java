/**
 * Terms for regular expression are taken from online Open Source 
 */
package Player;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Regular expression from header and body of ABC file.
 * @author siddharth
 *
 */

public class ReadABC {

	private String filename;
	private String songline;
	protected Matcher matcher;
	
	private static final String Musicnotes = "([\\_]*[\\^]*[\\=]*[A-Ga-g][',+]*[0-9]?\\/?[0-9]?)";

	private static final Pattern ABCMusicREGEX = Pattern.compile(
					"(X\\s*:\\s*[0-9]+)"+                       //field-index
                    "|"+
                    "(T\\s*:[^\n]+)"+                           //field-title
                    "|"+
                    "(C\\s*:[^\n]+)"+                           //field-composer
                    "|"+
                    "(L\\s*:\\s*[0-9]+/[0-9]+)"+                //field-length
                    "|"+
                    "(M\\s*:\\s*C\\||M:C|M:[0-9]+/[0-9]+)"+     //field-meter
                    "|"+
                    "(Q\\s*:\\s*[0-9]+)"+                       //field-tempo
                    "|"+
                    "(K\\s*:\\s*[a-gA-G][#b]?m?)"+              //field-key 
                    "|"+
                    Musicnotes +                                //basenote+accidental+octave+note length
                    "|"+                          
                    "(z[0-9]?\\/?[0-9]?)" +                     //rest+note length
                    "|"+
                    "(\\:?\\|\\]?\\:?\\|?)"+                    //barline
                    "|"+
                    "(\\[[12])" +                               //nth-repeat
                    "|"+
                    "(\\[)" +                                   //chord start
                    "|"+
                    "(\\])" +                                   //chord end
                    "|"+
                    "(\\(3)" +                                 //triplet
                    "|"+
                    "(\\(2)" +                                 //duplet
                    "|"+
                    "(\\%$|\\%\\s*[A-Za-z\\s*0-9\\,\\.\\!]+$|\\%\\s*m[0-9]+\\-[0-9]+$)"   //comment
                    ); 
	
	static final String[] ABCTOKEN =      {
		"INDEX", "TITLE", "COMPOSER", "LENGTH", "METER", "TEMPO", "KEY",
	        "NOTE", "REST", "BAR", "CHORD_ST", "CHORD_END", "TRIPLET", "DUPLET", "COMMENT"
		};
	public ReadABC(String filename) {
        this.filename= filename;
    }
	
	public String SongContent()throws IOException{
		
		String result = "";
		FileReader fileReader;
		try{
			fileReader = new FileReader(filename);
		}catch(FileNotFoundException e)
		{
			e.printStackTrace();
			throw new RuntimeException("File not found");
		}
		
		BufferedReader reader = new BufferedReader(fileReader);
		String line = "";
		while((line = reader.readLine())!= null){
			this.songline = line;
			matcher = ABCMusicREGEX.matcher(this.songline);
			
			while(matcher.find()){
				String val = matcher.group(0);
				val = val.replaceAll("[A-Z]+:\\s*", "").replace("\n", "");
				for(int i=1;i<=ABCTOKEN.length; i++){
					if(matcher.group(i)!= null)
					{
						String s = ABCTOKEN[i-1];
						result = result+ s+" "+val+"\n";
					}
				}
			}
		}
		fileReader.close();
		reader.close();
		return result.toString();
	}
}

