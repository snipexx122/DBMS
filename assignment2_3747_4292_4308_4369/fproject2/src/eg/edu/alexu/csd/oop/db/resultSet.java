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
public interface resultSet {
    public int findColumn (String columnLabel);
	
	public void first();
	
	public void last();
	
	public boolean next();
	
	public boolean previous();
	
	public int getInt(String columnLabel);
	
	public int getInt(int columnIndex);

	public String getString(String columnLabel); 
	
	public void getMetaData();
	
	public String getString(int columnIndex);
}
