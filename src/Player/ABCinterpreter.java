/**
 * Multiple Online Open Sources like google, youtube and Git has been referenced
 * 
 */

package Player;

import java.util.ArrayList;
/**
 * interpreter of the ABC file. identifying the note, chord, duplet etc.
 * @author sid
 *
 */

public class ABCinterpreter {
	
	private String title;
	private int tempo;
	private int initial_tempo;
	private float meter;
	private float length;
	private int duplet;
	private int triplet;
	private boolean chord;
	private int chord_offset;
	private int repeat_from;
	private byte repeat_num;
	private boolean is_repeating;
	private String key;
	private ArrayList<Integer> voice_channels;
	private ArrayList<N_Event> N_EventList;
	public ArrayList<ArrayList<Object>> N_EventStorage;
	private int current_channel;
	
	private ABCNoteParser np;
	

	public ABCinterpreter() {
		this.title = "none";
		this.tempo = 0;
		this.meter = 1;
		this.length = (float)1/8;
		this.voice_channels = new ArrayList<Integer>();
		this.N_EventList = new ArrayList<N_Event>();
		this.N_EventStorage = new ArrayList<ArrayList<Object>>();
		this.N_EventStorage.add(new ArrayList<Object>());
		this.voice_channels.add(0);
		this.current_channel = 0;
		this.chord = false;
		this.repeat_num = 0;
		this.repeat_from = 0;
		this.is_repeating = false;
		this.np = new ABCNoteParser();
		this.initial_tempo = 60;
	}
	
	/**
	 * get title()
	 * @return title() string
	 */
	public String Title() {
		return this.title;
	}

	/**
	 * get tempo
	 * @return return tempo int
	 */
	public int Tempo() {
		return this.initial_tempo;
	}
	
	/**
	 * get meter
	 * @return meter float
	 */
	public float Meter() {
		return this.meter;
	}
	
	/**
	 * get Length
	 * @return Length float
	 */
	public float Length() {
		return this.length;
	}
	
	/**
	 * get currentVoice
	 * @return int current_channel
	 */
	public int getCurrentVoice() {
		return this.current_channel;
	}
	
	/**
	 * get Arraylist of N_Event
	 * @return noteEvenList
	 */
	public ArrayList<N_Event> getNoteEvents() {
		return this.N_EventList;
	}
	
	/**
	 * get Arraylist of Interger
	 * @return voice_channels
	 */
	public ArrayList<Integer> getVoiceChannels() {
		return this.voice_channels;
	}
	
	/**
	 * get startTick
	 * @return current channels
	 */
	public int StartTick() {
		return this.voice_channels.get(current_channel);
	}
	
	/**
	 * set start tick
	 * @param start_time
	 * @return voiceChannel
	 */
	public void setStartTick(int start_time) {
		this.voice_channels.set(current_channel, start_time);
	}
	
	
	
	/**
	 * get RepeatNum()
	 * @return repeat_num
	 */
	public byte getRepeatNum() {
		return this.repeat_num;
	}
	

	public String getKey() {
		return this.key;
	}
	
	/**
	 * initiate start tick
	 */
	public void initiateStartTick() {
		for (int i=0;i<this.voice_channels.size();++i) {
			this.voice_channels.set(i, this.voice_channels.get(0));
		}
	}
	
	/**
	 * Manage tempo of the ABC file
	 * @param t
	 */
	private void manageTempo(ABCmusicToken t) {
		if (this.tempo == 0){ 
			this.initial_tempo = Integer.valueOf(t.value);
			}else
			{
		this.tempo = Integer.valueOf(t.value);
			}
		this.initiateStartTick();
	}
	
	
	
 	/** 
 	 * Manage meter using ABCmusicToken 
 	 * @param t
 	 */
	
	private void manageMeter(ABCmusicToken t) {
		if (t.value.length()>2) {
			String[] s = t.value.split("/");
			this.meter = Float.valueOf(s[0])/Float.valueOf(s[1]);
		}
		else {
			if (t.value.equals("C")); //this.meter = 1;
			else if (t.value.equalsIgnoreCase("C"))this.meter = (float)0.5;
			
		}
	}
	
	
	/**
	 * Manage Length of Tokens
	 * @param t
	 */
	private void manageLength(ABCmusicToken t) {
		if (t.value.length()>2) {
			String[] s = t.value.split("/");
			this.length = Float.valueOf(s[0])/Float.valueOf(s[1]);
		}
		else this.length = Float.valueOf(t.value);
	}
	
	
	
	/**
	 * manage Chord which are received from ABCToken 
	 * @param t
	 */
	private void manageChord(ABCmusicToken t) {
		if(repeat_num!=2) {
			if (chord) {
				this.setStartTick(this.StartTick()+chord_offset);
				this.chord_offset = 0;
			}
		}
	}
	
	 /**
	  *  Manage bar token.
	  *  which is called by Read method
	  * @param t
	  */

	private void manageBar(ABCmusicToken t) {
		np.resetDefaultAccidentals();
		if (t.value.equalsIgnoreCase(":|")) {
			is_repeating = true;
			this.repeat_num = 0;
			for (Object note:this.N_EventStorage.get(current_channel)) {
				if (note == null) continue;
				this.read((ABCmusicToken)note);
			}
			this.N_EventStorage.get(current_channel).clear();
			is_repeating = false;
		}
	  else if (t.value.equalsIgnoreCase("|:")) {
		  this.N_EventStorage.get(current_channel).clear();
		  this.repeat_num = 0;
		  this.repeat_from = this.StartTick();
	  }
	}
	
	
	
	/** 
	 *  Handles TRIPLET tokens
	 *  For the next 3 notes/chords
	 * @param t
	 */
	
	private void triplet(ABCmusicToken t) {
		if(repeat_num!=2) this.triplet = 3;
		if (!is_repeating&&repeat_num!=1) this.N_EventStorage.get(current_channel);//.add(t);
	}
	
	
	
	/** 
	 * Handles DUPLET tokens
	 *  For the next 2 notes/chords
	 * @param t
	 */
	private void duplet(ABCmusicToken t) {
		if(repeat_num!=2) this.duplet = 2;
		if (!is_repeating&&repeat_num!=1) this.N_EventStorage.get(current_channel);//.add(t);
	}
	
	
	public void NoteAndRest(ABCmusicToken t) {
		ABCmusicNote n = this.np.parse(t);
		if (repeat_num!=2) {
			N_Event ne = this.noteCreator(n);
			if (ne!=null) {
				this.N_EventList.add(ne);
			}
		}
		if (!is_repeating&&repeat_num!=1) {
			this.N_EventStorage.get(current_channel).add(t);
			return;
		}
	}
	
	public N_Event noteCreator(ABCmusicNote n) {
		if (this.initial_tempo == 0) throw new RuntimeException("Invalid tempo"); 
		int start_tick = this.StartTick();
		double length_modifier = 1;
		if (this.triplet!=0) {
			if (triplet > 3) throw new RuntimeException("Not a Triplet");
			length_modifier = 2.0/3;
			--this.triplet;
		}
		else if (this.duplet!=0) {
			if(duplet>2) throw new RuntimeException("Not a Duplet");
			length_modifier = 3.0/2;
			--this.duplet;
		}
	
		int tick_length = (int)(8*3*n.length*length_modifier);
		if (!chord) {
			voice_channels.set(current_channel, ( start_tick + tick_length));
		}
		if (chord) {
			chord_offset=Math.max(chord_offset, tick_length);
			if (this.triplet>0&&this.triplet<2) triplet++;
					}
		if (!n.rest()) {
			N_Event ne = new N_Event(n.pitch().toMidiNote(),start_tick,tick_length);
			return ne;
		}
		else  {
			return null;
		}
	}
	
	
	/** Reads a token 
	 * 
	 * It takes a token and decides what to do with them.
	 * @param Token t 
	 */
	public void read(ABCmusicToken t) {
		switch (t.type) {

			case TEMPO: 	this.manageTempo(t);
							break;
			case METER: 	this.manageMeter(t);
							break;
			case LENGTH: 	this.manageLength(t);
						 	break;
			case CHORD_END: this.manageChord(t);
						    break;
			case CHORD_ST: 	this.manageChord(t);
						   	break;
			case BAR: 		this.manageBar(t);
					  		break;
			case TRIPLET:   this.triplet(t);
							break;
			case DUPLET: 	this.duplet(t);
						 	break;
			case NOTE:		this.NoteAndRest(t);
							break;
			case REST:		this.NoteAndRest(t);
							break;
			case KEY:		np.setKey(t.getvalue());
							this.key = t.getvalue();
							break;
			case TITLE: 
				this.title = t.value;
				break;
			default: 		break;
		}
	}
	
}
