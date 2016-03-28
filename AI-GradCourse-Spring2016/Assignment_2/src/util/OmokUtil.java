/**
 * 
 */
package util;

import omok_client_package.GameBoard;

/**
 * @author addy
 *
 */
public class OmokUtil {
	public static GameBoard.DollType getOppositeDollType(GameBoard.DollType p_DollType){
		if(p_DollType == GameBoard.DollType.BLACK){
			return GameBoard.DollType.WHITE;
		} else {
			return GameBoard.DollType.BLACK;
		}
	}
}
