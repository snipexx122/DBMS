/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.edu.alexu.csd.oop.db;

/**
 *
 * @author abdullah
 */
public interface resultSetMetaData {
    public int getColumnCount();
	
	public String getColumnLabel(int column);
	
	public String getTableName();
	
	public String getColumnName(int column);
}
