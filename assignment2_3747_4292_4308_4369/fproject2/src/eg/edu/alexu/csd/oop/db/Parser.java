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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class Parser implements Database{
    ArrayList arraylist;
	Engine eng;
    public Parser(){
    	arraylist = new ArrayList();
    	eng=new Engine();
    }
   
    //Create and Drop
	@Override
	public boolean executeStructureQuery(String query) throws SQLException {
		if(query.substring(0,12).equalsIgnoreCase("CREATE TABLE")){
    		arraylist.clear();
    		int counter=0;
    		for(int i = query.indexOf("("); i<query.indexOf(")");i++){
    		   if(query.charAt(i) == ',')
    			   ++counter;
    	   }
    		arraylist.add(query.substring(13,query.indexOf("(")-1).trim());
    		int startOfData = query.indexOf("(");
    		int endOfData   = query.indexOf(")");
    		String newString = query.substring(startOfData +1, endOfData  );
    		String tokens[] =newString.split(", ");
    		
    	  /* newString.split(",");
    		newString.split(" ");
    		newString.split(" ");
    		System.out.println(newString);*/

    		for(int j=0; j<tokens.length; j++){
    			String tokens1[]=tokens[j].split(" ");
    			
    			arraylist.add(tokens1[0].trim());
    			arraylist.add(tokens1[1].trim());
    		}
    		
    		System.out.println("working");
    		eng.setFileStatus(arraylist);
    	return true;
    	}
		else {
    		arraylist.clear();
    		arraylist.add(query.substring(11,query.length()-1));
    		System.out.println(arraylist.get(0));
    		eng.deleteFile(arraylist.get(0).toString().trim());
    		
    	return true;	
    	}
		
		
		// TODO Auto-generated method stub
	}
    
    // Select and return it
	@Override
	public Object[][] executeRetrievalQuery(String query) throws SQLException {
		Pattern patt1 = Pattern.compile("(\\s*)(\\w+)(\\s*)(\\,|)(\\s*)");
		Matcher matcher= patt1.matcher(query.substring(query.indexOf('T')+1,query.indexOf('F')-1));
		while(matcher.find()){
			arraylist.add(matcher.group(2));
		}
		patt1 = Pattern.compile("(\\s*)(SELECT)(\\s+)((\\s*)(\\w+)(\\s*)(\\,|)(\\s*))+(\\s+)(FROM)(\\s+)(\\w+)(\\s+)(WHERE)(\\s+)(\\w+)(\\s*)(<|>|=)(\\s*)(\\w+)(\\s*)(\\;)(\\s*)");
		matcher= patt1.matcher(query);
		if(matcher.find()){
			arraylist.add(matcher.group(13));
			arraylist.add(matcher.group(17));
			arraylist.add(matcher.group(19));
			arraylist.add(matcher.group(21));
		}
		
		
		String str[][]=eng.setselect(arraylist).clone();
		return str;
		// TODO Auto-generated method stub
	}

	// Update Table
	@Override
	public int executeUpdateQuery(String query) throws SQLException {
		if(query.substring(0,11).equalsIgnoreCase("INSERT INTO")){
			arraylist.clear();
    		query.indexOf("VALUES");
    		
    		String str=query.substring(12,query.indexOf("VALUES"));
    		String str2=query.substring(query.indexOf("VALUES"),query.length());
    		//divides the string to 2,one before keyword value and one after
    		
    		int counter=0;
    		for(int i = str.indexOf("("); i<str.indexOf(")");i++){
    		   if(str.charAt(i) == ',')
    			   ++counter;
    	   }
    		arraylist.add(str.substring(0,str.indexOf("(")-1));
    		String newString = str.substring((str.indexOf("(")) +1, str.indexOf(")") );
    		String newString2 = str2.substring((str2.indexOf("(")) +1, str2.indexOf(")") );
    		String tokens2[]=newString2.split(",");//split the string to words
    		String tokens[] =newString.split(",");//split the string to words
    		for(int j=0; j<tokens.length; j++){//add column name with its value on order 
    			arraylist.add(tokens[j].trim());
    			arraylist.add(tokens2[j].trim());
    		}
    		//System.out.println("lol"+arraylist.size());
    		return eng.setFileDetails(arraylist);
		}
		else {
			arraylist.clear();
			Pattern patt=Pattern.compile("(\\s*)(DELETE)(\\s+)(FROM)(\\s+)(\\w+)(\\s+)(WHERE)(\\s+)(\\w+)(\\s*)(<|>|=)(\\s*)(\\w+)(\\s*)(\\;)(\\s*)");
			Matcher matcher=patt.matcher(query);
			if(matcher.find()){
			arraylist.add(matcher.group(6));
			arraylist.add(matcher.group(10));
			arraylist.add(matcher.group(12));
			arraylist.add(matcher.group(14));
			}
			return eng.deleterowrow(arraylist);
		}
		
		// TODO Auto-generated method stub
		//return 0;
	}



}

