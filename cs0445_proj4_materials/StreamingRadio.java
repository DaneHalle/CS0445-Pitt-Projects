/**
 * This abstract data type represents the backend for a streaming radio service.
 * It stores the songs, stations, and users in the system, as well as the
 * ratings that users assign to songs.
 */
public interface StreamingRadio {

	/*
	 * The abstract methods below are declared as void methods with no
	 * parameters. You need to expand each declaration to specify a return type
	 * and parameters, as necessary.
	 *
	 * You also need to include a detailed comment for each abstract method describing
	 * its effect, its return value, any corner cases that the client may need to
	 * consider, any exceptions the method may throw (including a description of the
	 * circumstances under which this will happen), and so on.
	 *
	 * You should include enough details that a client could use this data structure
	 * without ever being surprised or not knowing what will happen, even though they
	 * haven't read the implementation.
	 */

	/**
	 * Will take a new Song and add it to the universal system if it can. If the Song is 
	 * null, it will output false and throw a NullPointerException. If the Song already 
	 * exists in the system, it will return false and throw a IllegalArgumentException. 
	 * If the Song can be added, it will be and method will return true. 
	 * 
	 * @param in Song to be added into the system. 
	 * @return true if Song was added into the system and false if the Song was already
	 * already in the system or if it was false.
	 * @throws IllegalArgumentException if Song is already in the system.
	 * @throws NullPointerException if Song is null.
	 */
	boolean addSong(Song in)
		throws IllegalArgumentException, NullPointerException;

	/**
	 * Will take a Song and check to see if it can be removed from the universal system. 
	 * If it can be removed, it will be and the method will return the Song that was removed.
	 * If Song is null, method will throw NullPointerException and method will return null.
	 * if Song isn't in the system, method will throw IllegalArgumentException and method
	 * will return null. 
	 * 
	 * @param out Song to be removed from the system.
	 * @return The song that was output and null if song was not found.
	 * @throws IllegalArgumentException if the Song isn't in the system.
	 * @throws NullPointerException if Song is null.
	 */
	Song removeSong(Song out)
		throws IllegalArgumentException, NullPointerException;

	/**
	 * Will attempt to add Song to a Station. If successful, method will return true. 
	 * However, if either the Song or Station are null, a NullPointerException will
	 * be thrown and method will return false. If the Song was already added to the
	 * station, or if the Song is not in the universal system yet, an IllegalArgumentException 
	 * will be thrown and method will return false. 
	 * 
	 * @param toAdd Song to be added to the system.
	 * @param addTo Station which the song will be added to if possible.
	 * @return true if Song was added to the Station. False if the Song was already 
	 * in the Station.
	 * @throws IllegalArgumentException if the Song is already in the Station or isn't in the
	 * system.
	 * @throws NullPointerException if either Song or Station are null
	 * in the system yet.
	 */
	boolean addToStation(Song toAdd, Station addTo)
		throws IllegalArgumentException, NullPointerException;

	/**
	 * Takes in a Song to remove from a Station. If it is able to remove the Song from
	 * the Station, method will return the Song removed. If Song or Station are null,
	 * a NullPointerException will be thrown and method will return null. If the Song
	 * is not in the Station, an IllegalArgumentException will be thrown and method 
	 * will return null. 
	 * 
	 * @param toRemove Song to be removed from the Station.
	 * @param removeFrom Station to have the song removed from.
	 * @return Song if it is removed from Station and false if Song is already not
	 * in the Station.
 	 * @throws IllegalArgumentException if Song is not in the Station.
	 * @throws NullPointerException if Song or Station are null.
	 */
	Song removeFromStation(Song toRemove, Station removeFrom)
		throws IllegalArgumentException, NullPointerException;

	/**
	 * Takes in a User, a Song, and a rating for the Song. If rating is able to be
	 * applied to the Song for User, method returns the rating. If any parameter are
	 * null, NullPointerException is thrown and method returns null. If Song is not 
	 * within the system, an IllegalArgumentException is thrown and method returns null. 
	 * If rating is less than 1 or greater than 5, an IndexOutOfBoundsException will be 
	 * thrown and method will return null. this method is able to overwrite a previously 
	 * rated song. 
	 * 
	 * @param user User to have the rating for a specific song applied.
	 * @param toRate Song to recieve the rating.
	 * @param rating Integer of stars from 1 to 5 to act as and be applied as a rating for
	 * Song.
	 * @return the rating given to the song for the User if it was able to do so. Null 
	 * if any data input are null or if the Song is not within the User's system.
	 * @throws IllegalArgumentException if Song is not within the system.
	 * @throws IndexOutOfBoundsException if rating is less than 1 or greater than 5.
	 * @throws NullPointerException if User, Song, or int are null.
	 */
	Integer rateSong(User user, Song toRate, Integer rating)
		throws IllegalArgumentException, IndexOutOfBoundsException, NullPointerException;

	/**
	 * Clears a user's rating on a song. If this user has rated this song and
	 * the rating has not already been cleared, then the rating is cleared and
	 * the state will appear as if the rating was never made. If there is no
	 * such rating on record (either because this user has not rated this song,
	 * or because the rating has already been cleared), then this method will
	 * throw an IllegalArgumentException.
	 *
	 * @param theUser User whose rating should be cleared.
	 * @param theSong Song from which the user's rating should be cleared.
	 * @throws IllegalArgumentException if the user does not currently have a
	 * rating on record for the song.
	 * @throws NullPointerException if either the user or the song is null.
	 */
	public void clearRating(User theUser, Song theSong)
		throws IllegalArgumentException, NullPointerException;

	/**
	 * Takes a User and Song and based on rating data for other songs, preditcts what
	 * the user will rate this Song. If the User has not already rated the song, a 
	 * prediction between 1 and 5 will be made and returned. If User or Song are null, 
	 * NullPointerException is thrown and null is returned. If Song has already been 
	 * rated, IllegalArgumentException is thrown and null is returned. If Song is not
	 * in the system already, IllegalArgumentException will be thrown and null will be
	 * returned.
	 * 
	 * @param theUser User to use data to make a perdiciton.
	 * @param theSong Song to make a perdiction on.
	 * @return Integer from 1 to 5 or null if user has already rated the Song.
	 * @throws IllegalArgumentException if Song is already rated by User or if
	 * Song isn't in the system.
	 * @throws NullPointerException if User or Song are null.
	 */
	Integer predictRating(User theUser, Song theSong)
		throws IllegalArgumentException, NullPointerException;

	/**
	 * Suggests a song for a user that they are predicted to like.
	 * 
	 * Takes a User and based off rating data, outputs a Song that the User could
	 * like. If User is null, NullPointerException is thrown and null is returned.
	 * If there is zero rating data for the User, a random song from the system
	 * will be output. If there are no songs in the system, IllegalArgumentException
	 * will be thrown and null will be returned. 
	 * 
	 * @param theUser User for who the song will be suggested.
	 * @return Song suggested by the program or null if User is null.
	 * @throws IllegalArgumentException if there are no Songs in the system.
	 * @throws NullPointerException if User is null.
	 */
	Song suggestSong(User theUser)
		throws IllegalArgumentException, NullPointerException;
}