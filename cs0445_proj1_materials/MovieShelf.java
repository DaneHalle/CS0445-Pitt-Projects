// DMH148
// Hello grader, I hope you are having a good day. 
// Commented out code on line 84 is what the old version of code used. The new code that replaced it has formating. 

public class MovieShelf implements MovieShelfInterface {
	private Set<Movie> data;

	/**
	 * Constructor that makes an empty Set, 'data'
	 */
	public MovieShelf()
	{
		data=new Set();
	}

	/**
	 * Constructor that takes array of movies and puts them into the Set, 'data'
	 * 
	 * @param in The array of Movies to put into the shelf
	 */
	public MovieShelf(Movie[] in)
	{
		data=new Set((Object[])in);
	}

	/**
     * Adds a movie to the shelf, if possible.
     *
     * @param item the movie to add
     * @return true if the movie was added, and false if it's already on the shelf.
     */
    public boolean addItem(Movie item)
    {
        data.add(item);
    	return true;
    }

    /**
     * Removes a movie from the shelf, if it exists.
     *
     * @param item the movie to remove
     * @return true if the movie was removed, and false if it wasn't on the shelf.
     */
    public boolean removeItem(Movie item)
    {
    	if(data.contains(item)){
    		data.remove(item);
    		return true;
    	}
    	return false;
    }

    /**
     * Watches a movie. If a movie with the same description is on the shelf,
     * then its watch count is incremented, and the new watch count is returned.
     * If the movie is not on the shelf, this method makes no changes to the
     * shelf and returns -1.
     *
     * @param item the movie to watch
     * @return the new watch count, or -1 if the movie was not found
     */
    public int watchMovie(Movie item)
    {
    	if(data.contains(item)){
    		data.get(item).watch();
    		return data.get(item).getWatchCount();
    	}
    	return -1;
    }

    /**
     * Prints all movies. Prints each item on a separate line. Each item should
     * show the title, year, rating, and watch count.
     */
    public void printAll()
    {
    	Object[] hold=data.toArray();
        Movie temp=null;   String tStr="";
        int rate=0;        int ct=0;
        for(int i=0; i<hold.length; i++){
            temp=(Movie)hold[i];    tStr=temp.toString();
            rate=temp.getRating();  ct=temp.getWatchCount();
            System.out.printf("%-35s (rating: %d /5; watched %d times)\n",tStr,rate,ct);
    		//System.out.println(temp+"\t(rating: "+temp.getRating()+"/5; watched "+temp.getWatchCount()+" times)");
    	}
    }

    /**
     * Checks if a movie is already on this shelf
     * 
     * @param item Movie to check to see if it is in the shelf
     * @return True if it contains movie and false if it doens't
     */
    public boolean contains(Movie item)
    {
    	return data.contains(item);
    }

    /**
     * Borrows a movie from another movie shelf, if possible.
     *
     * If the given movie is not on the other shelf, this method does not
     * modify either shelf and returns false.
     *
     * If the given movie is already on this shelf, this method does not
     * modify either shelf and returns false.
     *
     * Otherwise, it will remove the movie from the other shelf, and
     * add it to this shelf, and return true.
     *
     * @param other the shelf to borrow a movie from.
     * @param item the movie to borrow.
     * @return true if it was borrowed successfully, false otherwise.
     */
    public boolean borrowMovie(MovieShelfInterface other, Movie item) 
    {
    	if(!other.removeItem(item)){
    		return false;
    	}else if(data.contains(item)){
            other.addItem(item); //Added because realized this would cause the item to be taken from other and not put in this
    		return false;
    	}
        this.addItem(item);
        return true;
    }
}