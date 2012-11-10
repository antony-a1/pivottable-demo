/* PivotModelBuilder.java

{{IS_NOTE
 Purpose:
  
 Description:
  
 History:
  Nov 16, 2011 9:45:23 AM , Created by simonpai
}}IS_NOTE

Copyright (C) 2011 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
}}IS_RIGHT
*/
package org.zkoss.pivot.demo;

import org.zkoss.pivot.impl.TabularPivotModel;

/**
 * An abstraction of PivotModel factory, merely for convenience of demonstration.
 * @author simonpai
 */
public interface PivotModelFactory {
	
	/**
	 * The text description of this model.
	 */
	public String getDescriptionURI();
	
	/**
	 * Return the pivot model
	 */
	public TabularPivotModel build();
	
	/**
	 * Return a default configurator for initial loading
	 */
	public PivotConfigurator getDefaultConfigurator();
	
	/**
	 * Return a set of predefined scenarios.
	 */
	public PivotConfigurator[] getConfigurators();
	
}
