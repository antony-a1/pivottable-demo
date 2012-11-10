/* PivotDemoController.java

{{IS_NOTE
 Purpose:
  
 Description:
  
 History:
  Mar 24, 2011 6:26:26 PM , Created by simonpai
}}IS_NOTE

Copyright (C) 2011 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
}}IS_RIGHT
*/
package org.zkoss.pivot.demo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.zkoss.pivot.Pivottable;
import org.zkoss.pivot.impl.TabularPivotModel;
import org.zkoss.pivot.ui.PivotFieldControl;
import org.zkoss.pivot.util.Exports;
import org.zkoss.pivot.util.Exports.PivotExportContext;
import org.zkoss.pivot.util.poi.CellStyleConfigurator;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.CheckEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.Radio;

/**
 * The main controller of Pivottable demo
 * @author simonpai
 */
public class PivotDemoBaseController extends GenericForwardComposer {
	
	private static final long serialVersionUID = -7531153593366258488L;
	
	private static final String[] TITLES = new String[] { "(Data Title)", "(Column Title)", "(Row Title)" };
	
	private Pivottable pivot;
	private TabularPivotModel _model;
	private PivotFieldControl pfc;
	
	private Button updateBtn;
	private Checkbox colGrandTotal, rowGrandTotal;
	private Radio colOrient, rowOrient;
	private Hlayout preDef;
	private Div descDiv;
	
	private CellStyleConfigurator styleConfigurator;
	
	public void onCheck$autoUpdate(CheckEvent event) {
		boolean deferred = !event.isChecked();
		pfc.setDeferredUpdate(deferred);
		if (!deferred)
			updateBtn.setDisabled(true);
	}
	
	public void onClick$updateBtn() {
		pfc.update();
	}
	
	public void onPivotFieldControlChange$pfc() {
		if (!pfc.isUpdated())
			updateBtn.setDisabled(false);
	}
	
	public void onCheck$colGrandTotal(CheckEvent event) {
		pivot.setGrandTotalForColumns(event.isChecked());
	}
	
	public void onCheck$rowGrandTotal(CheckEvent event) {
		pivot.setGrandTotalForRows(event.isChecked());
	}
	
	public void onCheck$dataOrient(CheckEvent event) {
		pivot.setDataFieldOrient(((Radio)event.getTarget()).getLabel());
	}
	
	public void onClick$exportCsvBtn() throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PivotExportContext context = Exports.getExportContext(pivot, true, TITLES);
		Exports.exportCSV(out, context);
		Filedownload.save(out.toByteArray(), "text/csv", "pivot.csv");
		try {
			out.close();
		} catch (IOException e) {}
	}
	
	public void onClick$exportXlsBtn() throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PivotExportContext context = Exports.getExportContext(pivot, true, TITLES);
		Exports.exportExcel(out, "xls", context, styleConfigurator);
		Filedownload.save(out.toByteArray(), "application/vnd.ms-excel", "pivot.xls");
		try {
			out.close();
		} catch (IOException e) {}
	}
	
	public void onClick$exportXlsxBtn() throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PivotExportContext context = Exports.getExportContext(pivot, true, TITLES);
		Exports.exportExcel(out, "xlsx", context, styleConfigurator);
		Filedownload.save(out.toByteArray(), "application/vnd.ms-excel", "pivot.xlsx");
		try {
			out.close();
		} catch (IOException e) {}
	}
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
		PivotModelFactory pmf = (PivotModelFactory) arg.get("factory");
		_model = pmf.build();
		pivot.setModel(_model);
		pfc.setModel(_model);
		Executions.createComponents(pmf.getDescriptionURI(), descDiv, null);
		
		loadConfiguration(pmf.getDefaultConfigurator());
		
		// load predefined scenario
		for(PivotConfigurator conf : pmf.getConfigurators())
			preDef.appendChild(getPreDefDiv(conf));
	}
	
	private void initControls() {
		// grand totals
		colGrandTotal.setChecked(pivot.isGrandTotalForColumns());
		rowGrandTotal.setChecked(pivot.isGrandTotalForRows());
		
		// data orientation
		("column".equals(pivot.getDataFieldOrient()) ? 
				colOrient : rowOrient).setChecked(true);
		
		pfc.syncModel(); // field control
	}
	
	private Component getPreDefDiv(final PivotConfigurator conf) {
		Div div = new Div();
		div.setHflex("1");
		div.setSclass("predef");
		div.appendChild(new Label(conf.getTitle()));
		div.addEventListener("onClick", new EventListener(){
			public void onEvent(Event event) throws Exception {
				loadConfiguration(conf);
			}
		});
		return div;
	}
	
	private void loadConfiguration(PivotConfigurator conf) {
		_model.clearAllFields(true);
		conf.configure(_model);
		conf.configure(pivot);
		pivot.setPivotRenderer(conf.getRenderer());
		styleConfigurator = conf.getCellStyleConfigurator();
		initControls();
	}
	
}
