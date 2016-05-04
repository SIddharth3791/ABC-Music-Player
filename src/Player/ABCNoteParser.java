package Player;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class ABCNoteParser {
	String CurrentKey;
	Map<String, String> defaultAccidentals;
	
	public ABCNoteParser() //Marks:- Default constructor
	{
		this.CurrentKey = "C";
		resetDefaultAccidentals();
	}
	
	public void resetDefaultAccidentals() 
	{
		defaultAccidentals = ABCmusicKeys.GetKeys(this.CurrentKey);
	}
	 public void setKey(String Key) /**marks:- Sets the key of the music.
	 								 The notes are transposed to the keys default values */
	 {
		    this.CurrentKey = Key;
		    resetDefaultAccidentals();
	 }
	 
	  public ABCmusicNote parse(ABCmusicToken note)//marks:- Takes in note token as argument and parse details
	  {
		    int octave =0;
		    int accidentals = 0;
		    char baseNote = findBaseNote(note);
		    if(note.type == ABCmusicTokenType.NOTE){
		      octave = findOctave(note);
		      accidentals = findAccidentals(note, baseNote);
		    }
		    float length = findNoteLength(note);
		    return new ABCmusicNote(length, accidentals, baseNote, octave);
		  }
	  
	  int findOctave(ABCmusicToken note) //marks:- takes note as argument and parse number octave the token with middle C
	  {
		    Pattern octaveModifier = Pattern.compile("([(A-G)(a-g)])([,']*)");
		    Matcher matcher = octaveModifier.matcher(note.value);

		    int octave = 0;
		    if (matcher.find()){
		      String modifiers = matcher.group(2);
		      char letter = matcher.group(1).charAt(0);

		      if (letter >= 'a') octave++;
		      if (!modifiers.isEmpty()) {
		        int length = modifiers.length();
		        length *= modifiers.charAt(0) ==','? -1:1;
		        octave += length;
		      }
		    }
		    return octave;
		  }
	  
	  char findBaseNote(ABCmusicToken note) {
		    Pattern letter = Pattern.compile("[a-gA-GzZ]");
		    Matcher matcher = letter.matcher(note.value);
		    char baseNote = 'C';
		    if (matcher.find()) baseNote = matcher.group().toUpperCase().charAt(0);
		    return baseNote;
		  }
	  
	 int findAccidentals(ABCmusicToken noteToken, char baseNote){
		    String note = noteToken.value;
		    boolean specified = false;
		    Pattern accidentalsPattern = Pattern.compile("[=_^]");
		    Matcher matcher = accidentalsPattern.matcher(note);
		    int accidentals = 0;
		    while(matcher.find()){
		      specified = true;
		      char accidental = matcher.group().charAt(0);

		      if(accidental == '=') {
		        accidentals = 0;
		        break;
		      }
		      if(accidental == '_') accidentals -=1;
		      else accidentals +=1;
		    }

		    if(!specified) accidentals = getDefaultAccidental(baseNote); //unspecified, use known default!
		    else setDefaultAccidental(baseNote, accidentals);//specified! Reset default and return!
		    return accidentals;
		  }
	 
	  private int getDefaultAccidental(char baseNote) {
		    String note = "" + baseNote;
		    String defaultNote = defaultAccidentals.get(note);
		    if (defaultNote.equals(note)) return 0;

		    return defaultNote.charAt(0) == '_'? -1:1;
		  }

		  private void setDefaultAccidental(char baseNote, int value) {
		    String note = ""+baseNote;
		    String accidental = "";
		    if(value == 1) accidental = "^";
		    else if(value == -1) accidental = "_";
		    defaultAccidentals.put(note, accidental+note);
		  }
		  
		  float findNoteLength(ABCmusicToken note){
			    Pattern lengthPattern = Pattern.compile("[a-zA-Z][',]*([1-9]*)(/?([1-9]*))");
			    Matcher matcher = lengthPattern.matcher(note.value);
			    float length = 1;
			    if (matcher.find()){
			      if (!matcher.group(1).isEmpty()) length *= Integer.parseInt(matcher.group(1));
			      if (!matcher.group(2).isEmpty()) {
			        if (!matcher.group(3).isEmpty()) length /= Integer.parseInt(matcher.group(3));
			        else length /=2;
			      }
			    }
			    return length;
			  }
}
