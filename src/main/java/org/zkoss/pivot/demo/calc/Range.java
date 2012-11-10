/* Range.java

{{IS_NOTE
 Purpose:
  
 Description:
  
 History:
  May 28, 2012 5:03:36 PM , Created by simonpai
}}IS_NOTE

Copyright (C) 2011 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
}}IS_RIGHT
*/
package org.zkoss.pivot.demo.calc;

import org.zkoss.pivot.impl.StandardContextType;
import org.zkoss.pivot.impl.calc.ContextType;
import org.zkoss.pivot.impl.calc.ContextualCalculator;
import org.zkoss.pivot.impl.calc.MinMaxContext;

/**
 *
 * @author simonpai
 */
public class Range implements ContextualCalculator<MinMaxContext> {
	
	public static final Range INSTANCE = new Range();
	private Range() {};
	
	@Override
	@SuppressWarnings("unchecked")
	public ContextType<MinMaxContext> getContextType() {
		return StandardContextType.MIN_MAX;
	}
	
	@Override
	public Number getResult(MinMaxContext context) {
		return context.getMax().doubleValue() - context.getMin().doubleValue();
	}
	
	@Override
	public String getLabel() {
		return "Range";
	}
	
	@Override
	public String getLabelKey() {
		return "range";
	}
	
}
