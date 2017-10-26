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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;



public class partTwo implements Driver, Statement, resultSet, resultSetMetaData, Connection {

	Engine eng;
	ArrayList arraylist;
	String path;
	ArrayList read;
	ArrayList result;
	int rowcount;

	public partTwo() {
		eng = new Engine();
		arraylist = new ArrayList();
		read=new ArrayList();
		result=new ArrayList();
		rowcount =0;
	}
	
	@Override
	public void execute(String sql) {
		 Parser parser = new Parser();
	       DBMS dbms = new DBMS();
	       dbms.manipulateQuery(sql, parser);		
	}

	@Override
	public void executeQuery(String sql) {
		
		arraylist.clear();
		Pattern patt1 = Pattern.compile("(\\s*)(\\w+)(\\s*)(\\,|)(\\s*)");
		Matcher matcher= patt1.matcher(sql.substring(sql.indexOf('T')+1,sql.indexOf('F')-1));
		while(matcher.find()){
			arraylist.add(matcher.group(2));
		}
		patt1 = Pattern.compile("(\\s*)(SELECT)(\\s+)((\\s*)(\\w+)(\\s*)(\\,|)(\\s*))+(\\s+)(FROM)(\\s+)(\\w+)(\\s+)(WHERE)(\\s+)(\\w+)(\\s*)(<|>|=)(\\s*)(\\w+)(\\s*)(\\;)(\\s*)");
		matcher= patt1.matcher(sql);
		if(matcher.find()){
			arraylist.add(matcher.group(13));
			arraylist.add(matcher.group(17));
			arraylist.add(matcher.group(19));
			arraylist.add(matcher.group(21));
		}
		
		
		String str[][]=eng.setselect(arraylist);
		for(int i=0;i<str.length;i++){
			for(int j=0;j<str[i].length;j++){
				System.out.println(str[i][j]);
			}
		}
	
	}		
	

	@Override
	public void connect(String url) {
      path = url;
      getfillresult();
		 
	}

	@Override
	public int findColumn(String columnLabel) {
		File file = new File(path);
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLStreamReader streamReader;
		int numberOfColumn = 0;
		try {
			streamReader = factory.createXMLStreamReader(new FileReader(file));
			while(streamReader.hasNext()){
				streamReader.next();
			    if(streamReader.getEventType() == XMLStreamReader.START_ELEMENT&&!streamReader.getLocalName().equals(columnLabel)){
			    	++numberOfColumn;
			    	//read.add(streamReader.getLocalName());
			        if(streamReader.getAttributeCount()>0){
			        	for(int i=0;i<streamReader.getAttributeCount();i++){
			        	}
			        }
			    }
			    if(streamReader.getEventType()==XMLStreamReader.CHARACTERS){
			    	
			    }
			    else if(streamReader.getEventType() == XMLStreamReader.END_ELEMENT&&streamReader.getLocalName().equals("table")){
			    	numberOfColumn=0;
			        
			    }
			    else if(streamReader.getLocalName().equals(columnLabel)){
			    	break;
			    }
			}
			    streamReader.close();
			
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return numberOfColumn-1;
		
	}

	@Override
	public void first() {
		File file = new File(path);
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLStreamReader streamReader;
		try {
			streamReader = factory.createXMLStreamReader(new FileReader(file));
			ArrayList arr=new ArrayList();
			while(streamReader.hasNext()){
				streamReader.next();
			    if(streamReader.getEventType() == XMLStreamReader.START_ELEMENT&&!streamReader.getLocalName().equals("table")){
			    	arr.add(streamReader.getLocalName().trim());
			        if(streamReader.getAttributeCount()>0){
			        }
			    }
			    else if(streamReader.getEventType()==XMLStreamReader.CHARACTERS){
			    	if(!streamReader.getText().isEmpty()&&!streamReader.getText().equals("\n")&&!streamReader.getText().equals(" ")){
				    	arr.add(streamReader.getText().trim());
			    	}
				    	
			    }
			    else if(streamReader.getEventType() == XMLStreamReader.END_ELEMENT&&streamReader.getLocalName().equals("table")){
			    	break;
			        
			    }
			}
			 streamReader.close();
			 for(int i=1;i<arr.size();i+=2){
				 if(!(arr.get(i).toString().equals("\n"))){
				 System.out.println(arr.get(i).toString().trim()+":"+arr.get(i+1).toString().trim());
				 }
			 }
			}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void last() {
		File file = new File(path);
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLStreamReader streamReader;
		try {
			streamReader = factory.createXMLStreamReader(new FileReader(file));
			ArrayList arr=new ArrayList();
			while(streamReader.hasNext()){
				streamReader.next();
			    if(streamReader.getEventType() == XMLStreamReader.START_ELEMENT&&!streamReader.getLocalName().equals("table")){
			    	arr.add(streamReader.getLocalName().trim());
			        if(streamReader.getAttributeCount()>0){
			        }
			    }
			    else if(streamReader.getEventType() == XMLStreamReader.START_ELEMENT&&streamReader.getLocalName().equals("table")){
			    	arr.clear();
			        if(streamReader.getAttributeCount()>0){
			        }
			    }
			    else if(streamReader.getEventType()==XMLStreamReader.CHARACTERS){
			    	if(!streamReader.getText().isEmpty()&&!streamReader.getText().equals("\n")&&!streamReader.getText().equals(" ")){
				    	arr.add(streamReader.getText().trim());
			    	}
				    	
			    }
			  //  else if(streamReader.getEventType() == XMLStreamReader.END_ELEMENT&&streamReader.getLocalName().equals("table")){
			    	
			        
			    //}
			}
			 streamReader.close();
			 for(int i=0;i<arr.size();i+=2){
				 if(!(arr.get(i).toString().equals("\n"))){
				 System.out.println(arr.get(i).toString().trim()+":"+arr.get(i+1).toString().trim());
				 }
			 }
			 
			}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void reader(){
		read.clear();
		File file = new File(path);
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLStreamReader streamReader;
		try {
			streamReader = factory.createXMLStreamReader(new FileReader(file));
			while(streamReader.hasNext()){
				streamReader.next();
			    if(streamReader.getEventType() == XMLStreamReader.START_ELEMENT&&!streamReader.getLocalName().equals("table")){
			    	read.add(streamReader.getLocalName());
			        if(streamReader.getAttributeCount()>0){
			        	for(int i=0;i<streamReader.getAttributeCount();i++){
			        	}
			        }
			    }
			    else if(streamReader.getEventType() == XMLStreamReader.END_ELEMENT&&streamReader.getLocalName().equals("table")){
			    	read.add("seperator");
			        
			    }
			    else if(streamReader.getEventType()==XMLStreamReader.CHARACTERS){
			    	if(!streamReader.getText().isEmpty()&&!streamReader.getText().equals("\n")&&!streamReader.getText().equals(" ")){
			    	read.add(streamReader.getText().trim());
			    	}
			    }
			    streamReader.close();
			}
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean next() {
		
            rowcount++;
            System.out.println("row count is: "+rowcount);
            return true;
		
	}
	

	@Override
	public boolean previous() {
		if (rowcount - 1 >0) {
            rowcount--;
            System.out.println("row count is: "+rowcount);
            return true;
        }
        return false;
		
		
	}

	@Override
	public int getInt(String columnLabel) {
		int counter=0;
		int flag=0;
		for(int i=0;i<result.size();i++){
		if(result.get(i).equals("seperator")){
				counter++;
		}
		if(counter>rowcount){
			break;
		}
		if(counter==rowcount){
			flag=1;
		}
		if(result.get(i).equals(columnLabel)&&flag==1){
			try {
	            return Integer.parseInt(result.get(i+1).toString());
	        } catch (NumberFormatException ex) {
	            System.out.println("Eception: " + ex.getMessage());
	            return 0;
	        }
		}
		}
		
		return 0;
	}
	@Override
	public int getInt(int columnIndex) {
		int counter=0;
		int flag=0;
		int countercounter=0;
		for(int i=1;i<result.size();i+=2){
		if(result.get(i).equals("seperator")){
				counter++;
				i--;
		}
		else if(counter>rowcount){
			break;
		}
		else if(counter==rowcount){
			flag=1;
		}
		if(flag==1){
			countercounter++;
		}
		if(flag==1&&(countercounter)==columnIndex){
			try {
	            return Integer.parseInt(result.get(i+1).toString());
	        } catch (NumberFormatException ex) {
	            System.out.println("Eception: " + ex.getMessage());
	            return 0;
	        }
		}
		}
		
		return 0;
	}
	public void getMetaData() {
		for(int i=1;i<result.size();i+=2){
			System.out.println(result.get(i)+":"+result.get(i+1));
		}
	}

	
	public void getfillresult() {
		File file = new File(path);
		result.clear();
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLStreamReader streamReader;
		try {
			streamReader = factory.createXMLStreamReader(new FileReader(file));
			while(streamReader.hasNext()){
				streamReader.next();
			    if(streamReader.getEventType() == XMLStreamReader.START_ELEMENT&&!streamReader.getLocalName().equals("table")){
			    	result.add(streamReader.getLocalName());
			        if(streamReader.getAttributeCount()>0){
			        	for(int i=0;i<streamReader.getAttributeCount();i++){
			        	}
			        }
			    }
			    else if(streamReader.getEventType() == XMLStreamReader.END_ELEMENT&&streamReader.getLocalName().equals("table")){
			    	result.add("seperator");
			        
			    }
			    else if(streamReader.getEventType()==XMLStreamReader.CHARACTERS){
			    	if(!streamReader.getText().isEmpty()&&!streamReader.getText().equals("\n")&&!streamReader.getText().equals(" ")){
			    	result.add(streamReader.getText().trim());
			    	}
			    }
			    streamReader.close();
			}
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Override
	public String getString(int columnIndex) {
		int counter=0;
		int flag=0;
		int countercounter=0;
		for(int i=1;i<result.size();i+=2){
		if(result.get(i).equals("seperator")){
				counter++;
		}
		if(counter>rowcount){
			break;
		}
		else if(counter==rowcount){
			flag=1;
		}
		if(flag==1){
			countercounter++;
		}
		if(flag==1&&countercounter==columnIndex){
			try {
	            return result.get(i+1).toString();
	        } catch (NumberFormatException ex) {
	            System.out.println("Eception: " + ex.getMessage());
	            return null;
	        }
		}
		}
		
		return null;
	}
	@Override
	public String getString(String columnLabel) {
		int counter=0;
		int flag=0;
		for(int i=0;i<result.size();i++){
		if(result.get(i).equals("seperator")){
				counter++;
		}
		if(counter>rowcount){
			break;
		}
		if(counter==rowcount){
			flag=1;
		}
		if(result.get(i).equals(columnLabel)&&flag==1){
			try {
	            return result.get(i+1).toString();
	        } catch (NumberFormatException ex) {
	            System.out.println("Eception: " + ex.getMessage());
	            return null;
	        }
		}
		}
		
		return null;
	}

	@Override
	public int getColumnCount() {
		File file = new File(path);
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLStreamReader streamReader;
		int counter=0;
		try {
			streamReader = factory.createXMLStreamReader(new FileReader(file));
			while(streamReader.hasNext()){
				streamReader.next();
			    if(streamReader.getEventType() == XMLStreamReader.START_ELEMENT&&!streamReader.getLocalName().equals("table")){
			    	counter++;
			    	
			    	
			        if(streamReader.getAttributeCount()>0){
			        	for(int i=0;i<streamReader.getAttributeCount();i++){
			        	}
			        }
			    }
			    else if(streamReader.getEventType() == XMLStreamReader.END_ELEMENT&&streamReader.getLocalName().equals("table")){
			    	break;
			        
			    }
			}
			    streamReader.close();
			
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return counter-1;
	}

	@Override
	public String getColumnLabel(int column) {
		
		
		
		return null;
	}


	@Override
	public String getTableName() {
		File file = new File(path);
		String str=null;
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLStreamReader streamReader;
		try {
			streamReader = factory.createXMLStreamReader(new FileReader(file));
			while(streamReader.hasNext()){
				streamReader.next();
				str=streamReader.getLocalName();
			    streamReader.close();
			    break;
			}
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(!str.equals(null)){
			return str;
		}
		else{
			return null;
		}
	}

	@Override
	public void close() {
		path=null;
		result.clear();
		rowcount=0;
		
		
	}

	@Override
	public String getColumnName(int column) {
		int count=1;
		if(column>getColumnCount()){
			System.out.println("doesnt exist");
		}
		else{
		for(int i=1;i<result.size()&&!result.get(i).equals("seperator");i+=2){
			if(count==column){
				return result.get(i).toString();
			}
			else{
				count++;
			}
		}
		}
		
		
		return null;
	}

	

}

