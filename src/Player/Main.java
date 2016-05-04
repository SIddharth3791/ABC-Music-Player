package Player;

import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import Sound.SequencePlayer;
/**
 * when running this project. out will will show all the header and body read by parser.
 * @author siddharth
 *
 */
public class Main {

	/**
	 * Plays the input file using Java MIDI API and displays
	 * header information to the standard output stream.
	 * 
	 * <p>Your code <b>should not</b> exit the application abnormally using
	 * System.exit()</p>
	 * 
	 * @param file the name of input abc file
	 */
	public static void play(String file) {
		
		String InputString;
		ReadABC ABCfile = new ReadABC(file);
		ABCinterpreter qm = new ABCinterpreter();
		try{
			InputString = ABCfile.SongContent();
			ABCmusicParser token = new ABCmusicParser(InputString);
			ABCmusicToken t = null;
			do {
				t = token.next();
            	if (t==null) break;
            	else {
            		qm.read(t);
            	}
				
			} while(t != null);
			try{
				System.out.println(ABCfile.SongContent());
            	SequencePlayer player = new SequencePlayer((int)(qm.Tempo()),24);
				
            for(N_Event ne:qm.getNoteEvents())
            	{
            		player.addNote(ne.pitch, ne.start_tick, ne.tick_length);
            	}
            
            	player.play();
			}catch(MidiUnavailableException e)
			{
				e.printStackTrace();
				
			}catch(InvalidMidiDataException e)
			{
				e.printStackTrace();
			}
			
		}catch(IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
	public static void main(String arg[]) {
		//String filename="sample_docs/fur_elise.abc";
		 String filename="sample_docs/invention.abc";
	    //String filename="sample_docs/debussy.abc";
	    //String filename="sample_docs/little_night_music.abc";
	    //String filename="sample_docs/paddy.abc";
	    //String filename="sample_docs/prelude.abc";
	    //String filename="sample_docs/sample2.abc";
	    //String filename="sample_docs/sample3.abc";
			Main.play(filename);
		}
}
