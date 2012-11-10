/* BaseCellStyleConfigurator.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		May 29, 2012 4:05:19 PM , Created by Sam
}}IS_NOTE

Copyright (C) 2012 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
}}IS_RIGHT
*/
package org.zkoss.pivot.demo.impl;

import org.zkoss.pivot.util.Exports.PivotExportCell.Type;
import org.zkoss.pivot.util.poi.CellStyleConfigurator;
import org.zkoss.pivot.util.poi.StyleFactory;
import org.zkoss.poi.ss.usermodel.Cell;
import org.zkoss.poi.ss.usermodel.CellStyle;
import org.zkoss.poi.ss.usermodel.Font;

/**
 * @author Sam
 *
 */
public abstract class BaseCellStyleConfigurator implements CellStyleConfigurator {

	@Override
	public void config(Type type, Cell cell, StyleFactory styleFactory) {
		CellStyle style;
		switch (type) {
		case TITLE_DATA:
		case TITLE_COLUMN:
		case TITLE_ROW:
			style = styleFactory.createCellStyle();
			style.setFont(styleFactory.getOrCreateFontWithBoldWeight(Font.BOLDWEIGHT_BOLD));
			cell.setCellStyle(style);
			break;
		case COLUMN_DATA:
		case ROW_DATA:
			style = styleFactory.createCellStyle();
			style.setFont(styleFactory.getOrCreateFontWithItalic(true));
			cell.setCellStyle(style);
			break;
		case COLUMN:
		case COLUMN_SUBTOTAL:
		case COLUMN_GRAND_TOTAL:
			break;
		case ROW:
		case ROW_SUBTOTAL:
		case ROW_GRAND_TOTAL:
			break;
		case DATA:
		default:
		}
	}

}
