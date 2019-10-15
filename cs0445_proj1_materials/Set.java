// DMH148
// I hope you are having a great day grader.

import java.util.Arrays;

public class Set<E> implements SetInterface<E> {
	// TODO: everything
	private int size;	private E[] data;

	/**	
     *  Constructor that takes a variable of int and sets the array "data" to that length and size equal to 0
     * 
	 * 	@param in Size to make array.
	 *  @throws IllegalArgumentException If the int given causes the array to have an illegal capacity
	 */ 
	public Set(int in) throws IllegalArgumentException
	{	
        if(in<0){
            throw new IllegalArgumentException("Illegal Array Size");
        }
		data=(E[])new Object[in]; size=0;
	}

	/**	
     *  Constructor that sets array "data" to length 100 and sets size to 0
	 */ 
	public Set()
	{
		data=(E[])new Object[100]; size=0;
	}

	/**	
     *  Constructor that takes an array of data and sets "data" to that and sets size to 0
     *  Assumes that none of the data in array 'in' is null
     *  
 	 * 	@param in the Array of data to be transferred to "data" 
	 */ 
	public Set(E[] in)
	{
		data=(E[])new Object[in.length*2]; size=0;
        for(int i=0; i<in.length; i++){
            this.add(in[i]);
        }
	}

	/**	
     *  Outputs the current number of entries in this set
     * 
	 *  @return The integer number of entries in this set
	 */ 
	public int getSize()
	{
		return size;
	}

	/**	Outputs true if this set is empty
	 *  @return True if set is empty, false if not
	 */ 
	public boolean isEmpty()
	{
		return size==0;
	}

    /**
     *  Doubles the size of array data when there is no space.
     */
    private void resize()
    {
        data=Arrays.copyOf(data, data.length*2);
    }

	/**
     * Adds a new entry to this set, avoiding duplicates.
     *
     * <p> If newEntry is not null, this set does not contain newEntry, and this
     * set has available capacity (if applicable), then add modifies the set so
     * that it contains newEntry. All other entries remain unmodified.
     * Duplicates are determined using the .equals() method.
     *
     * <p> If newEntry is null, then add throws NullPointerException without
     * modifying the set. If this set already contains newEntry, then add
     * returns false without modifying the set. If this set has a capacity
     * limit, and does not have available capacity, then add throws
     * SetFullException without modifying the set. If this set does not have a
     * capacity limit (i.e., if it resizes as needed), then it will never throw
     * SetFullException.
     *
     * @param newEntry  The object to be added as a new entry
     * @return  true if the addition is successful; false if the item already is
     * in this set
     * @throws SetFullException  If this set has a fixed capacity and does not
     * have the capacity to store an additional entry
     * @throws NullPointerException  If newEntry is null
     */
    @Override
	public boolean add(E newEntry) throws NullPointerException
	{
		if(newEntry==null){
            throw new NullPointerException("newEntry is null");
        }
		if(size==data.length){
			resize();
		}
        if(size==0){
            size++; 
            data[0]=newEntry;
        }else{
    		for(int i=0; i<size; i++){
    			if(newEntry==data[i]){
    				return false;
    			}
    		}
    		data[size]=newEntry;
            size++;
        }
		return true;
	}

	/**
     * Removes a specific entry from this set, if possible.
     *
     * <p> If this set contains the entry, remove modifies the set so that it no
     * longer contains entry. All other entries remain unmodified. Identifying
     * this entry is accomplished using the .equals() method. The removed entry
     * will be returned.
     *
     * <p> If this set does not contain entry, remove will return null without
     * modifying the set. Because null cannot be added, a return value of null
     * will never indicate a successful removal.
     *
     * <p> If the specified entry is null, then remove throws
     * NullPointerException without modifying the set.
     *
     * @param entry  The entry to be removed
     * @return  The removed entry if removal was successful; null otherwise
     * @throws NullPointerException  If entry is null
     */
    public E remove(E entry) throws NullPointerException
    {
    	if(entry==null){
            throw new NullPointerException("entry is null");
        }
    	int hold=-1;
	    for(int i=0; i<size; i++){
	    	if(data[i]==entry){
	    		hold=i;
                data[hold]=data[size-1];
                data[size-1]=null;
                size--;
	    		return entry;
	    	}
	   	}
	    return null;
    }

    /**
     * Removes an arbitrary entry from this set, if possible.
     *
     * <p> If this set contains at least one entry, remove will modify the set
     * so that it no longer contains one of its entries. All other entries
     * remain unmodified. The removed entry will be returned.
     *
     * <p> If this set is empty, remove will return null without modifying the
     * set. Because null cannot be added, a return value of null will never
     * indicate a successful removal.
     *
     * @return  The removed entry if the removal was successful; null otherwise
     */
    public E remove()
    {
    	if(size>0){
    		E out=data[size-1];
            data[size-1]=null;
    		size--;
    		return out;
    	}
    	return null;
    }

    /**
     * Removes all entries from this set.
     *
     * <p> If this set is already empty, clear will not modify the set.
     * Otherwise, the set will be modified so that it contains no entries.
     */
    public void clear()
    {
        for(int i=size; i>=0; i--){
            data[i]=null;
            size--;
        }
        size=0;
    }

    /**
     * Tests whether this set contains a given entry. Equality is determined
     * using the .equals() method.
     *
     * <p> If this set contains entry, then contains returns true. Otherwise
     * (including if this set is empty), contains returns false. If entry is
     * null, then remove throws NullPointerException. The method never modifies
     * this set.
     *
     * @param entry  The entry to locate
     * @return  true if this set contains entry; false if not
     * @throws NullPointerException  If entry is null
     */
    public boolean contains(E entry) throws NullPointerException
    {
    	if(entry==null){
            throw new NullPointerException("entry is null");
        }
    	for(int i=0; i<size; i++){
           if(data[i]==entry){
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves all entries that are in this set.
     *
     * <p> An array is returned that contains a reference to each of the entries
     * in this set. The returned array's length will be equal to the number of
     * elements in this set, and thus the array will contain no null values.
     *
     * <p> If the implementation of set is array-backed, toArray will not return
     * the private backing array. Instead, a new array will be allocated with
     * exactly the appropriate capacity (including an array of size 0, if the
     * set is empty).
     *
     * @return  A newly-allocated array of all the entries in this set
     */
    public Object[] toArray()
    {
    	Object[] out=Arrays.copyOf(data,size);
    	return out;
    }

    /** 
     * Checks array within size contraint for entry and then replaces it
     * 
     * @param dupe Item to check for
     * @return Entry so it can be changed
     */
    public E get(E dupe) throws NullPointerException
    {
        if(dupe==null){
            throw new NullPointerException("dupe is null");
        }
        for(int i=0; i<size; i++){
            if(dupe==data[i]){
                return data[i];
            }
        }
        return null;
    } 

    /**
     *  Helper method for internal testing. 
     *  
     *  Prints out the entire array data as dfined by E's toString()
     */
    public void print()
    {
        Object[] hold=toArray();
        for(int i=0; i<hold.length; i++){
            System.out.print("\t"+hold[i]);
        }
    }

    /**
     *  Helper method for internal testing. 
     *  
     *  Gives the length of the array. Used for checking that resized works. 
     *  
     *  @return the length of array data 
     */
    public int length()
    {
        return data.length;
    }
}