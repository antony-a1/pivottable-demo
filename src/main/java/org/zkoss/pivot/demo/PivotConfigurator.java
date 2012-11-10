/* PivotConfigurator.java

{{IS_NOTE
 Purpose:
  
 Description:
  
 History:
  Mar 23, 2011 5:58:19 PM , Created by simonpai
}}IS_NOTE

Copyright (C) 2011 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
}}IS_RIGHT
*/
package org.zkoss.pivot.demo;

import org.zkoss.pivot.PivotRenderer;
import org.zkoss.pivot.Pivottable;
import org.zkoss.pivot.impl.TabularPivotModel;
import org.zkoss.pivot.util.poi.CellStyleConfigurator;

/**
 * The abstract representation of configuring PivotModel and Pivottable, which 
 * let you easily construct a predefined Pivottable scenario/schema.
 * @author simonpai
 */
public interface PivotConfigurator {
	
	/**
	 * The title of this configuratior
	 */
	public String getTitle();
	
	/**
	 * Define what fields to set on model.
	 */
	public void configure(TabularPivotModel model);
	
	/**
	 * Define what attribute to set to table. 
	 */
	public void configure(Pivottable table);
	
	/**
	 * A custom PivotRenderer to use with the Pivottable
	 * @return null if to use the default renderer.
	 */
	public PivotRenderer getRenderer();
	
	/**
	 * Returns CellStyleConfigurator
	 */
	public CellStyleConfigurator getCellStyleConfigurator();
	
}
