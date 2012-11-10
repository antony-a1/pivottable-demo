/* RandomPivotModelFactory.java

{{IS_NOTE
 Purpose:
  
 Description:
  
 History:
  May 9, 2012 7:06:42 PM , Created by simonpai
}}IS_NOTE

Copyright (C) 2011 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
}}IS_RIGHT
*/
package org.zkoss.pivot.demo.impl;

import java.util.List;

import org.zkoss.pivot.PivotField;
import org.zkoss.pivot.PivotRenderer;
import org.zkoss.pivot.demo.calc.*;
import org.zkoss.pivot.demo.PivotConfigurator;
import org.zkoss.pivot.demo.PivotModelFactory;
import org.zkoss.pivot.demo.util.DummyData;
import org.zkoss.pivot.impl.SimplePivotRenderer;
import org.zkoss.pivot.impl.TabularPivotModel;

/**
 *
 * @author simonpai
 */
public class RandomPivotModelFactory implements PivotModelFactory {
	
	public static final long SEED = 1;
	
	public static final PivotModelFactory INSTANCE = new RandomPivotModelFactory();
	
	@Override
	public String getDescriptionURI() {
		return "random.desc.zul";
	}
	
	@Override
	public TabularPivotModel build() {
		Iterable<List<Object>> data = DummyData.iterable(10 * 100000, SEED);
		return new TabularPivotModel(data, DummyData.COLUMN_LABELS);
	}
	
	@Override
	public PivotConfigurator[] getConfigurators() {
		return new PivotConfigurator[] {
				// TODO
		};
	}
	
	@Override
	public PivotConfigurator getDefaultConfigurator() {
		return CONFIG_PRIMITIVE;
	}
	
	
	
	public static final PivotConfigurator CONFIG_PRIMITIVE = 
			new BasePivotConfigurator("Primitive") {
		@Override
		public void configure(TabularPivotModel model) {
			super.configure(model);
			model.setFieldType("Normal Data", PivotField.Type.ROW);
			model.setFieldType("Day", PivotField.Type.COLUMN);
			model.setFieldType("Int Data A", PivotField.Type.DATA);
			model.addSupportedCalculator(DistinctCount.INSTANCE);
		}
	};
	
	// skeleton //
	private static class BasePivotConfigurator extends SimplePivotConfigurator {
		public BasePivotConfigurator(String title) { super(title); }
		@Override
		public PivotRenderer getRenderer() {
			return _BASE_RENDERER;
		}
	}
	
	private static final PivotRenderer _BASE_RENDERER = new SimplePivotRenderer();
	
}
