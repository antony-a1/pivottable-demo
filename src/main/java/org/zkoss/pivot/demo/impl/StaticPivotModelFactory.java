/* FlightTicketPivotModelBuilder.java

{{IS_NOTE
 Purpose:
  
 Description:
  
 History:
  Mar 23, 2011 12:34:01 PM , Created by simonpai
}}IS_NOTE

Copyright (C) 2011 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
}}IS_RIGHT
*/
package org.zkoss.pivot.demo.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.zkoss.pivot.GroupHandler;
import org.zkoss.pivot.PivotField;
import org.zkoss.pivot.PivotHeaderContext;
import org.zkoss.pivot.PivotRenderer;
import org.zkoss.pivot.Pivottable;
import org.zkoss.pivot.demo.PivotConfigurator;
import org.zkoss.pivot.demo.PivotModelFactory;
import org.zkoss.pivot.impl.SimplePivotRenderer;
import org.zkoss.pivot.impl.StandardCalculator;
import org.zkoss.pivot.impl.TabularPivotModel;
import org.zkoss.pivot.util.poi.CellStyleConfigurator;

/**
 * A sample static data source for TabularPivotModel
 * @author simonpai
 */
public class StaticPivotModelFactory implements PivotModelFactory {
	
	private static final long TODAY = new Date().getTime();
	private static final long DAY = 1000 * 60 * 60 * 24;
	
	public static final StaticPivotModelFactory INSTANCE = 
		new StaticPivotModelFactory();
	
	private StaticPivotModelFactory() {}
	
	@Override
	public String getDescriptionURI() {
		return "airline.desc.zul";
	}
	
	@Override
	public TabularPivotModel build() {
		return new TabularPivotModel(getData(), getColumns());
	}
	
	/**
	 * Return raw data (like in flat table).
	 */
	public static List<List<Object>> getData() {
		Object[][] objs = new Object[][] {
				{ "Carlene Valone", "Tameka Meserve",    "ATB Air", "AT15",  dt(-7), "Berlin",     "Paris",     186.6, 545  },
				{ "Antonio Mattos", "Sharon Roundy",     "Jasper",  "JS1",   dt(-5), "Frankfurt",  "Berlin",    139.5, 262  },
				{ "Russell Testa",  "Carl Whitmore",     "Epsilon", "EP2",   dt(-3), "Dublin",     "London",    108.0, 287  },
				{ "Antonio Mattos", "Velma Sutherland",  "Epsilon", "EP5",   dt(-1), "Berlin",     "London",    133.5, 578  },
				{ "Carlene Valone", "Cora Million",      "Jasper",  "JS30",  dt(-4), "Paris",      "Frankfurt", 175.4, 297  },
				{ "Richard Hung",   "Candace Marek",     "DTB Air", "BK201", dt(-5), "Manchester", "Paris",     168.5, 376  },
				{ "Antonio Mattos", "Albert Briseno",    "Fujito",  "FJ1",   dt(-7), "Berlin",     "Osaka",     886.9, 5486 },
				{ "Russell Testa",  "Louise Knutson",    "HST Air", "HT6",   dt(-2), "Prague",     "London",    240.6, 643  },
				{ "Antonio Mattos", "Jessica Lunsford",  "Jasper",  "JS9",   dt(-4), "Munich",     "Lisbon",    431.6, 1222 },
				{ "Becky Schafer",  "Lula Lundberg",     "Jasper",  "JS1",   dt(-3), "Frankfurt",  "Berlin",    160.5, 262  },
				{ "Carlene Valone", "Tameka Meserve",    "Epsilon", "EP5",   dt(-3), "Berlin",     "London",    104.6, 578  },
				{ "Antonio Mattos", "Yvonne Melendez",   "Epsilon", "EP5",   dt(-2), "Berlin",     "London",    150.5, 578  },
				{ "Antonio Mattos", "Josephine Whitley", "ATB Air", "AT15",  dt(-6), "Berlin",     "Paris",     192.6, 545  },
				{ "Antonio Mattos", "Velma Sutherland",  "DTB Air", "BK201", dt(-6), "Manchester", "Paris",     183.8, 376  },
				{ "Richard Hung",   "Blanca Samuel",     "Fujito",  "FJ2",   dt(-7), "Berlin",     "Osaka",     915.3, 5486 },
				{ "Russell Testa",  "Katherine Bennet",  "Epsilon", "EP23",  dt(-4), "Lisbon",     "London",    214.8, 987  },
				{ "Joann Cleaver",  "Alison Apodaca",    "Jasper",  "JS1",   dt(-5), "Frankfurt",  "Berlin",    166.3, 262  },
				{ "Antonio Mattos", "Tameka Meserve",    "Epsilon", "EP21",  dt(-1), "London",     "Lisbon",    153.8, 987  },
				{ "Carlene Valone", "Janie Harper",      "KST Air", "KT10",  dt(-2), "Prague",     "Paris",     187.9, 550  },
				{ "Russell Testa",  "Myrtle Fournier",   "Jasper",  "JS30",  dt(-4), "Paris",      "Frankfurt", 207.5, 297  },
				{ "Joann Cleaver",  "Victor Michalski",  "Jasper",  "JS2",   dt(-3), "Frankfurt",  "Amsterdam", 470.3, 224  },
				{ "Carlene Valone", "Renee Marrow",      "Epsilon", "EP19",  dt(-4), "London",     "Dublin",    133.6, 287  },
				{ "Carlene Valone", "Harold Fletcher",   "Jasper",  "JS2",   dt(-4), "Frankfurt",  "Amsterdam", 435.3, 224  },
				{ "Antonio Mattos", "Velma Sutherland",  "Jasper",  "JS7",   dt(-4), "Munich",     "Amsterdam", 421.1, 413  },
				{ "Becky Schafer",  "Dennis Labbe",      "Epsilon", "EP8",   dt(-6), "London",     "Paris",     134.4, 213  },
				{ "Joann Cleaver",  "Louis Brumfield",   "Epsilon", "EP4",   dt(-2), "London",     "Berlin",    132.3, 578  },
				{ "Antonio Mattos", "Eunice Alcala",     "Jasper",  "JS11",  dt(-1), "Munich",     "Frankfurt", 178.4, 189  },
				{ "Russell Testa",  "Velma Sutherland",  "Epsilon", "EP4",   dt(-7), "London",     "Berlin",    155.7, 578  }
		};
		
		List<List<Object>> list = new ArrayList<List<Object>>();
		for(Object[] a : objs)
			list.add(Arrays.asList(a));
		return list;
	}
	
	/**
	 * Return column labels
	 */
	public static List<String> getColumns() {
		return Arrays.asList(new String[]{
				"Agent", "Customer", 
				"Airline", "Flight", "Date", 
				"Origin", "Destination",
				"Price", "Mileage"
		});
	}
	
	private static Date dt(int i){
		return new Date(TODAY + i * DAY);
	}
	
	
	
	// configurator //
	@Override
	public PivotConfigurator getDefaultConfigurator() {
		return CONFIG_PRIMITIVE;
	}
	
	@Override
	public PivotConfigurator[] getConfigurators() {
		return new PivotConfigurator[] {
				CONFIG_PERFORMANCE, 
				CONFIG_CITY_SALES, 
				CONFIG_SALES_RACE
		};
	}
	
	public static final PivotConfigurator CONFIG_PRIMITIVE = 
			new BasePivotConfigurator("Primitive"){
		public void configure(TabularPivotModel model) {
			model.setFieldType("Airline", PivotField.Type.COLUMN);
			model.setFieldType("Agent", PivotField.Type.ROW);
			model.setFieldType("Price", PivotField.Type.DATA);
		}
	};
	
	public static final PivotConfigurator CONFIG_PERFORMANCE = 
			new BasePivotConfigurator("Performance"){
		public void configure(TabularPivotModel model) {
			model.setFieldType("Airline", PivotField.Type.COLUMN);
			model.setFieldType("Flight", PivotField.Type.COLUMN);
			model.setFieldType("Agent", PivotField.Type.ROW);
			model.setFieldType("Customer", PivotField.Type.ROW);
			model.setFieldType("Price", PivotField.Type.DATA);
			model.setFieldType("Mileage", PivotField.Type.DATA);
			
			model.setFieldSubtotals("Airline", new StandardCalculator[] {
					StandardCalculator.AVERAGE, StandardCalculator.COUNT
			});
			model.setFieldSubtotals("Agent", new StandardCalculator[] {
					StandardCalculator.AVERAGE, StandardCalculator.COUNT
			});
		}
		public void configure(Pivottable table) {
			table.setDataFieldOrient("column");
		}
	};
	
	public static final PivotConfigurator CONFIG_CITY_SALES = 
			new BasePivotConfigurator("Sales by City") {
		public void configure(TabularPivotModel model) {
			model.setFieldType("Origin", PivotField.Type.COLUMN);
			model.setFieldType("Destination", PivotField.Type.COLUMN);
			model.setFieldType("Airline", PivotField.Type.ROW);
			model.setFieldType("Flight", PivotField.Type.ROW);
			model.setFieldType("Customer", PivotField.Type.DATA);
			model.setFieldType("Price", PivotField.Type.DATA);
		}
		public void configure(Pivottable table) {
			table.setDataFieldOrient("row");
		}
	};
	
	public static final PivotConfigurator CONFIG_SALES_RACE = 
			new BasePivotConfigurator("Sales Race!"){
		public void configure(TabularPivotModel model) {
			model.setFieldType("Agent", PivotField.Type.COLUMN);
			model.setFieldType("Date", PivotField.Type.ROW);
			model.setFieldType("Customer", PivotField.Type.DATA);
			model.setFieldType("Price", PivotField.Type.DATA);
			
			// sort by last name, then first name
			model.setFieldKeyComparator("Agent", new Comparator<Object>() {
				public int compare(Object k1, Object k2) {
					String s1 = (String) k1;
					String s2 = (String) k2;
					int i1 = s1.lastIndexOf(' ');
					int i2 = s2.lastIndexOf(' ');
					int cmp = s1.substring(i1 + 1).compareTo(s2.substring(i2 + 1));
					if (cmp != 0)
						return cmp;
					String fname1 = i1 < 0 ? "" : s1.substring(0, i1).trim();
					String fname2 = i2 < 0 ? "" : s2.substring(0, i2).trim();
					return fname1.compareTo(fname2);
				}
			});
			
			// sort date by descending order
			model.getField("Date").setGroupHandler(new GroupHandler(){
				public Object getGroup(Object data) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
					return format.format((Date) data);
				}
			});
			model.setFieldKeyOrder("Date", false);
		}
		public void configure(Pivottable table) {
			table.setDataFieldOrient("column");
		}
		public PivotRenderer getRenderer() {
			return _SALES_RACE_RENDERER;
		}
	};
	
	private static final PivotRenderer _SALES_RACE_RENDERER = 
			new SimplePivotRenderer() {
		@Override
		public int getColumnSize(Pivottable table, PivotHeaderContext colc, 
				PivotField dataField) {
			if (dataField != null && "Price".equals(dataField.getFieldName()))
				return 200;
			return colc.isGrandTotal() && dataField != null ? 150 : 100;
		}
		@Override
		public String renderCellSClass(Number data, Pivottable table,
				PivotHeaderContext rowContext,
				PivotHeaderContext columnContext, PivotField dataField) {
			if (dataField != null && "Price".equals(dataField.getFieldName())) {
				String sclass = "highlight";
				if (!rowContext.isGrandTotal() && 
						!columnContext.isGrandTotal() && 
						data != null && data.doubleValue() > 300)
					sclass += " important";
				return sclass;
			}
			return null;
		}
		@Override
		public String renderCellStyle(Number data, Pivottable table,
				PivotHeaderContext rowContext,
				PivotHeaderContext columnContext, PivotField dataField) {
			if (columnContext.isGrandTotal())
				return "color: #11EE11; font-weight: bold";
			return null;
		}
	};
	
	private static class BasePivotConfigurator extends SimplePivotConfigurator {

		public BasePivotConfigurator(String title) {
			super(title);
		}

		@Override
		public CellStyleConfigurator getCellStyleConfigurator() {
			return _BASE_CELL_CONFIG;
		}
	}
	
	private static final CellStyleConfigurator _BASE_CELL_CONFIG = new BaseCellStyleConfigurator() {

		@Override
		public String getDateFormat(String field) {
			if ("Date".equalsIgnoreCase(field)) {
				return "yyyy-MM-dd";
			}
			return null;
		}
		
	};
}
