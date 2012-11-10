/* DistinctCount.java

{{IS_NOTE
 Purpose:
  
 Description:
  
 History:
  May 28, 2012 12:36:38 PM , Created by simonpai
}}IS_NOTE

Copyright (C) 2011 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
}}IS_RIGHT
*/
package org.zkoss.pivot.demo.calc;

import org.zkoss.pivot.impl.calc.ContextType;
import org.zkoss.pivot.impl.calc.ContextualCalculator;

/**
 * 
 * @author simonpai
 */
public class DistinctCount implements ContextualCalculator<SetContext> {
	
	/**
	 * 
	 */
	public static final DistinctCount INSTANCE = new DistinctCount();
	private DistinctCount() {}
	
	@Override
	public ContextType<SetContext> getContextType() {
		return SetContext.CONTEXT_TYPE;
	}
	
	@Override
	public Number getResult(SetContext context) {
		return context.getSet().size();
	}
	
	@Override
	public String getLabel() {
		return "Distinct Count";
	}
	
	@Override
	public String getLabelKey() {
		return "distinctCount";
	}
	
}
