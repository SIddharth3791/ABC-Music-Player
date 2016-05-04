package Player;

import Sound.Pitch;
/**
 * This class reads Note based on Lenght, accidental, base and Octave.
 * @author siddharth 
 *
 */
public class ABCmusicNote {
	public float length;
	public int accidental;
	public char base;
	public int octave;
	
	public ABCmusicNote(float length, int acd,char base, int oct)
	{
		this.length = length;
		this.accidental = acd;
		this.base = base;
		this.octave = oct;
	}
	
	public ABCmusicNote(float length, char base)
	{
		this.length = length;
		this.base = base;
		this.octave = 0;
		this.accidental = 0;
	}
	
	  public boolean rest() 
	  {
		  if (this.base == 'z' || this.base == 'Z') {
			  return true;
		  }
		  return false;
	 }
	  public Pitch pitch() 
	  {
	    Pitch pitch = new Pitch(base);		   
	    pitch = pitch.octaveTranspose(octave).accidentalTranspose(accidental);
	    return pitch;
	 }
}
	

