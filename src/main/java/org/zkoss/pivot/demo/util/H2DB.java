/* H2DB.java

{{IS_NOTE
 Purpose:
  
 Description:
  
 History:
  Nov 15, 2011 6:07:02 PM , Created by simonpai
}}IS_NOTE

Copyright (C) 2011 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
}}IS_RIGHT
*/
package org.zkoss.pivot.demo.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.zkoss.zk.ui.WebApp;

/**
 *
 * @author simonpai
 */
public class H2DB {
	
	static {
		try {
			Class.forName("org.h2.Driver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException(e);
		}
	}
	
	public static void runConnection(WebApp webapp, String dbName, 
			ConnectionRunner runner) throws SQLException {
		runConnection(getDBUrl(webapp, dbName), runner);
	}
	
	public static void runConnection(String path, 
			ConnectionRunner runner) throws SQLException {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(path, "sa", "");
			runner.run(conn);
		} finally {
			if (conn != null && !conn.isClosed())
				conn.close();
		}
	}
	
	public interface ConnectionRunner {
		public void run(Connection conn) throws SQLException;
	}
	
	private static String getDBUrl(WebApp webapp, String dbName) {
		return "jdbc:h2:" + webapp.getRealPath("/WEB-INF/h2db") + "/" + dbName;
	}
	
}
