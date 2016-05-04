package Player;
/**
 * enum for the Header and Body of the ABC file
 * @author siddharth
 *
 */

public enum ABCmusicTokenType {
	
	//Header of the song
	COMPOSER,TEMPO,KEY,METER,LENGTH,TITLE,INDEX,VOICE,

	//body of the song
	BAR,REPEATNO,NOTE,REST,CHORD_ST,CHORD_END,TRIPLET,DUPLET,
	
	COMMENT
	
}