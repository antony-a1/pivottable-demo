/* RandomDataIterator.java

{{IS_NOTE
 Purpose:
  
 Description:
  
 History:
  May 10, 2012 1:09:31 PM , Created by simonpai
}}IS_NOTE

Copyright (C) 2011 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
}}IS_RIGHT
*/
package org.zkoss.pivot.demo.util;

import java.util.Iterator;
import java.util.Random;

/**
 * A skeleton implementation of an iterator which generates random data.
 * @author simonpai
 */
public abstract class RandomDataIterator<T> implements Iterator<T> {
	
	private final Random _rand;
	private long _count;
	
	public RandomDataIterator(long size, long seed) {
		this(size, new Random(seed));
	}
	
	public RandomDataIterator(long size, Random random) {
		_count = size;
		_rand = random;
	}
	
	/**
	 * Generate random data.
	 */
	protected abstract T getData(Random random);
	
	@Override
	public boolean hasNext() {
		return _count > 0;
	}
	
	@Override
	public T next() {
		if (!hasNext())
			throw new java.util.NoSuchElementException();
		_count --;
		return getData(_rand);
	}
	
	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
	
}
