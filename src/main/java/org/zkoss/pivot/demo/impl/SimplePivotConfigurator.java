/* SimplePivotConfigurator.java

{{IS_NOTE
 Purpose:
  
 Description:
  
 History:
  Mar 23, 2011 6:37:20 PM , Created by simonpai
}}IS_NOTE

Copyright (C) 2011 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
}}IS_RIGHT
*/
package org.zkoss.pivot.demo.impl;

import org.zkoss.pivot.PivotRenderer;
import org.zkoss.pivot.Pivottable;
import org.zkoss.pivot.demo.PivotConfigurator;
import org.zkoss.pivot.impl.TabularPivotModel;
import org.zkoss.pivot.util.poi.CellStyleConfigurator;

/**
 * The most primitive template implementation of PivotConfigurator. 
 * @author simonpai
 */
public class SimplePivotConfigurator implements PivotConfigurator {
	
	private final String _title;
	
	public SimplePivotConfigurator(String title) {
		_title = title;
	}
	
	@Override
	public void configure(TabularPivotModel model) {
		// do nothing
	}
	
	@Override
	public void configure(Pivottable table) {
		// do nothing
	}
	
	@Override
	public String getTitle() {
		return _title;
	}
	
	@Override
	public PivotRenderer getRenderer() {
		return null; // use default renderer
	}

	@Override
	public CellStyleConfigurator getCellStyleConfigurator() {
		return null;
	}
}
