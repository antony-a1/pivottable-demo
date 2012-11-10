/* SetContext.java

{{IS_NOTE
 Purpose:
  
 Description:
  
 History:
  May 28, 2012 12:33:02 PM , Created by simonpai
}}IS_NOTE

Copyright (C) 2011 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
}}IS_RIGHT
*/
package org.zkoss.pivot.demo.calc;

import java.util.HashSet;
import java.util.Set;

import org.zkoss.pivot.impl.calc.Context;
import org.zkoss.pivot.impl.calc.ContextType;

/**
 * 
 * @author simonpai
 */
public class SetContext implements Context<SetContext> {
	
	private final Set<Object> _set = new HashSet<Object>();
	
	@Override
	public void add(Object item) {
		_set.add(item);
	}
	
	@Override
	public void merge(SetContext ctx) {
		_set.addAll(ctx._set);
	}
	
	public Set<Object> getSet() {
		return _set;
	}
	
	/**
	 * 
	 */
	public static final ContextType<SetContext> CONTEXT_TYPE = 
			new ContextType<SetContext>() {
		@Override
		public SetContext create() {
			return new SetContext();
		}
	};
	
}
