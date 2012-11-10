/* PersistentPivotModels.java

{{IS_NOTE
 Purpose:
  
 Description:
  
 History:
  Nov 15, 2011 6:56:08 PM , Created by simonpai
}}IS_NOTE

Copyright (C) 2011 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
}}IS_RIGHT
*/
package org.zkoss.pivot.demo.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.zkoss.pivot.GroupHandler;
import org.zkoss.pivot.PivotField;
import org.zkoss.pivot.PivotHeaderContext;
import org.zkoss.pivot.PivotRenderer;
import org.zkoss.pivot.Pivottable;
import org.zkoss.pivot.demo.PivotConfigurator;
import org.zkoss.pivot.demo.PivotModelFactory;
import org.zkoss.pivot.demo.util.H2DB;
import org.zkoss.pivot.demo.util.H2DB.ConnectionRunner;
import org.zkoss.pivot.impl.SimplePivotRenderer;
import org.zkoss.pivot.impl.StandardCalculator;
import org.zkoss.pivot.impl.TabularPivotModel;
import org.zkoss.pivot.util.poi.CellStyleConfigurator;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WebApp;

/**
 * A sample data source for TabularPivotModel, from database
 * @author simonpai
 */
public class PersistentPivotModelFactory implements PivotModelFactory {
	
	private static final String DB_NAME = "tarp";
	private static final String TABLE_NAME = "tarp_trans";
	private static final String[] COLUMNS = new String[] {
		"trans_date", "name", "price_paid", "inst_type", "total_assets",
		"regulator", "city", "state", "stock_symbol", "program", "stock_price"
	};
	private static final List<String> COLUMN_LBS = Arrays.asList(new String[] {
		"Date", "Name", "Price Paid", "Institution Type", "Total Assets",
		"Regulator", "City", "State", "Stock Symbol", "Program", "Stock Price"
	});
	
	private final WebApp _webapp;
	
	public PersistentPivotModelFactory() {
		this(Sessions.getCurrent().getWebApp());
	}
	
	public PersistentPivotModelFactory(WebApp webapp) {
		_webapp = webapp;
	}
	
	@Override
	public String getDescriptionURI() {
		return "tarp.desc.zul";
	}
	
	@Override
	public TabularPivotModel build() {
		try {
			return new TabularPivotModel(
					getData(_webapp, DB_NAME, TABLE_NAME, COLUMNS), COLUMN_LBS);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<List<Object>> getData(WebApp webapp, String dbName, 
			final String tableName, final String[] columns) throws SQLException {
		
		final boolean selectStar = columns == null || columns.length == 0;
		final List<List<Object>> list = new ArrayList<List<Object>>();
		
		H2DB.runConnection(webapp, dbName, new ConnectionRunner() {
			public void run(Connection conn) throws SQLException {
				Statement stmt = conn.createStatement();
				String query = "SELECT " + (selectStar ? "*" : join(columns)) + 
					" FROM " + tableName;
				//query += " WHERE stock_price IS NOT NULL";
				ResultSet res = stmt.executeQuery(query);
				while (res.next()) {
					List<Object> row = new ArrayList<Object>();
					for (String col : columns)
						row.add(res.getObject(col));
					list.add(row);
				}
				res.close();
			}
		});
		return list;
	}
	
	
	
	// configurator //
	@Override
	public PivotConfigurator getDefaultConfigurator() {
		return CONFIG_PRIMITIVE;
	}
	
	@Override
	public PivotConfigurator[] getConfigurators() {
		return new PivotConfigurator[] {
				CONFIG_BY_MONTH, CONFIG_GEOGRAPHY, CONFIG_STOCK
		};
	}
	
	public static final PivotConfigurator CONFIG_PRIMITIVE = 
			new BasePivotConfigurator("Primitive") {
		public void configure(TabularPivotModel model) {
			super.configure(model);
			model.setFieldType("Date", PivotField.Type.COLUMN);
			model.setFieldType("Name", PivotField.Type.ROW);
			model.setFieldType("Price Paid", PivotField.Type.DATA);
		}
	};
	
	public static final PivotConfigurator CONFIG_BY_MONTH = 
			new BasePivotConfigurator("By Month") {
		public void configure(TabularPivotModel model) {
			super.configure(model);
			model.setFieldType("Date", PivotField.Type.COLUMN);
			model.setFieldType("Name", PivotField.Type.ROW);
			model.setFieldType("Price Paid", PivotField.Type.DATA);
			
			model.getField("Date").setGroupHandler(new GroupHandler() {
				public Object getGroup(Object data) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy/MM");
					return format.format((Date) data);
				}
			});
		}

		//FIXME: when file use GroupHandler, not able to get correct value
		@Override
		public CellStyleConfigurator getCellStyleConfigurator() {
			return new BaseCellStyleConfigurator(){

				@Override
				public String getDateFormat(String field) {
					if ("Date".equalsIgnoreCase(field)) {
						return "yyyy/MM";
					}
					return null;
				}
				
			};
		}
	};
	
	public static final PivotConfigurator CONFIG_GEOGRAPHY = 
			new BasePivotConfigurator("Geography") {
		public void configure(TabularPivotModel model) {
			super.configure(model);
			model.setFieldType("Institution Type", PivotField.Type.COLUMN);
			model.setFieldType("Name", PivotField.Type.COLUMN);
			model.setFieldType("State", PivotField.Type.ROW);
			model.setFieldType("City", PivotField.Type.ROW);
			model.setFieldType("Price Paid", PivotField.Type.DATA);
		}
	};
	
	public static final PivotConfigurator CONFIG_STOCK = 
			new BasePivotConfigurator("Stock Price") {
		public void configure(TabularPivotModel model) {
			super.configure(model);
			//model.setFieldFilter("Stock Symbol", new NotNullFieldValueFilter());
			
			model.setFieldType("Institution Type", PivotField.Type.ROW);
			model.setFieldType("Stock Symbol", PivotField.Type.ROW);
			model.setFieldType("Stock Price", PivotField.Type.DATA);
			model.setFieldType("Price Paid", PivotField.Type.DATA);
			model.setFieldType("Total Assets", PivotField.Type.DATA);
			
			model.setFieldSummary("Stock Price", StandardCalculator.AVERAGE);
			model.setFieldSummary("Price Paid", StandardCalculator.AVERAGE);
			model.setFieldSummary("Total Assets", StandardCalculator.AVERAGE);
			model.setFieldSubtotals("Institution Type", new StandardCalculator[] { StandardCalculator.AVERAGE });
		}
		
		@Override
		public PivotRenderer getRenderer() {
			return new SimplePivotRenderer() {
				public String renderGrandTotalField(Pivottable table, PivotField field) {
					if (field == null) return "Average";
					return "Average of " + field.getTitle();
				}
			};
		}
		
	};
	
	private static class BasePivotConfigurator extends SimplePivotConfigurator {
		
		public BasePivotConfigurator(String title) {
			super(title);
		}
		
		@Override
		public PivotRenderer getRenderer() {
			return _TARP_BASE_RENDERER;
		}

		@Override
		public CellStyleConfigurator getCellStyleConfigurator() {
			return _TARP_BASE_CELL_CONFIG;
		}
	}
	
	private static final CellStyleConfigurator _TARP_BASE_CELL_CONFIG = new BaseCellStyleConfigurator() {

		@Override
		public String getDateFormat(String field) {
			if ("Date".equalsIgnoreCase(field)) {
				return "yyyy-MM-dd";
			}
			return null;
		}
		
	};
	
	private static final PivotRenderer _TARP_BASE_RENDERER = 
		new SimplePivotRenderer() {
		
		@Override
		public int getColumnSize(Pivottable table,
				PivotHeaderContext colc, PivotField field) {
			if (field != null) {
				String name = field.getFieldName();
				if ("Name".equals(name))
					return 200;
			}
			return super.getColumnSize(table, colc, field);
		}
		
	};
	
	
	
	// helper //
	private static String join(String ... strs) {
		if (strs.length == 0)
			return "";
		StringBuilder sb = new StringBuilder(strs[0]);
		for (int i = 1; i < strs.length; i++)
			sb.append(", ").append(strs[i]);
		return sb.toString();
	}
	
}
