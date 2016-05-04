package Player;

/**
 * This class is for get the tick length and start of it.
 * @author siddharth
 *
 */

public class N_Event {
	
	public int pitch;
	public int start_tick;
	public int tick_length;
	
	public N_Event(int pitch, int start_tick, int tick_length) {
		this.pitch = pitch;
		this.start_tick = start_tick;
		this.tick_length = tick_length;
	}

	public N_Event startTimeOffset(int offset) {
		return new N_Event(this.pitch,this.start_tick+offset,this.tick_length);
	}

}
