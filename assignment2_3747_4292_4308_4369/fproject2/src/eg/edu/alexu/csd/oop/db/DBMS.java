/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.edu.alexu.csd.oop.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 *
 * @author abdullah
 */
import eg.edu.alexu.csd.oop.db.Parser;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.omg.CORBA.portable.StreamableValue;

public class DBMS  {
	private static String pathToFolder= "C:\\Users\\abdullah\\Desktop\\projectfile";
	public static void main(String[] args){
		
		/*Scanner input=new Scanner(System.in);
		Parser parse=new Parser();
		String str;
		do{
		str=input.nextLine();
		parse.manipulateQuery(str);
		}while(!str.equals('0'));*/
		//CREATE TABLE Barcelona (name varchar, age int, titles int);
		//INSERT INTO Barcelona (name, age, titles) VALUES (messi, 29, 30);
		Scanner input=new Scanner(System.in);
		System.out.println("Welcom to our DBMS: ");
		System.out.println(" please enter your command or EXIT to exit the program");
		Parser parse =new Parser();
		String str;
		do{
			str=input.nextLine();
			manipulateQuery(str, parse);
			
			
		}while(!str.equalsIgnoreCase("exit"));
		
		//partTwo part = new partTwo();
		//part.connect("C:\\Users\\abdullah\\Desktop\\project2files\\players.xml");
		//part.execute("INSERT INTO players (name, age) VALUES (messi, 29);");
        //part.last();
         //part.next();
         //part.next();
       //System.out.println( part.getString("age"));
		
		
	}
	
	
	public static void manipulateQuery(String query, Parser parse){
    	//create or drop 
    	if(query.substring(0,12).equals("CREATE TABLE")){
    		if (validateSyntax(query, 1)){
    			try {
					boolean t=parse.executeStructureQuery(query);
					if(t){
						System.out.println("file created");
					}
					else{
						System.out.println("file not created");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    	}
    	else if(query.substring(0,10).equalsIgnoreCase("DROP TABLE")){
    		if (validateSyntax(query, 2)){
    			try {
					boolean t=parse.executeStructureQuery(query);
					if(t){
						System.out.println("file deleted");
					}
					else{
						System.out.println("could not delete file");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    	}
    	//INSERT INTO table_name (grade, id, name) VALUES (A, 4308, abdalla);
    	else if(query.substring(0,11).equalsIgnoreCase("INSERT INTO")){
    		if (validateSyntax(query, 3)){
    			//System.out.println("working");
    			try {
    				int x=parse.executeUpdateQuery(query);
    				System.out.println("numebr of rows "+x);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    	}
    	else if (query.substring(0,6).equalsIgnoreCase("SELECT")){
    		if (validateSyntax(query, 5)){
    			try {
    				ArrayList arr=new ArrayList();
    				Object[][] str=(parse.executeRetrievalQuery(query)).clone();
    				//System.out.println(str.length);
    				for(int i=0;i<str.length;i++){
    					for(int j=0;j<str[i].length;j++){
    						//if(!str[i][j].toString().isEmpty())
    						arr.add(str[i][j]);
    					}
    				}
    				for(int i=0;i<arr.size();i++){
    					//if(!arr.get(i).equals(" ")&&!arr.get(i).equals(null)){
    					System.out.println(arr.get(i));
    					//}
    				}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    	}
    	else if(query.substring(0, 11).equalsIgnoreCase("DELETE FROM")){
    		//System.out.println("working");
    		if (validateSyntax(query, 4)){
    			try {
    				int x=parse.executeUpdateQuery(query);
    				System.out.println("numebr of rows "+x);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    	}
    }
	 public static boolean validateSyntax(String query, int num){
	        switch(num){
	        case 1:
	        	if(Pattern.matches("(\\s+|)(CREATE)(\\s+)(TABLE)(\\s+)(\\w+)(\\s+)(\\(){1}((\\s*)(\\w+)(\\s+)(varchar|int)(\\s*\\,|\\s*))+(\\)){1}(\\s*)(\\;)(\\s*)", query)){
	        	
	        		return true;
	        	}
	        	return false;
	        case 2:
	        	if(Pattern.matches("(\\s+|)(DROP)(\\s+)(TABLE)(\\s+)(\\w+)(\\s*)(\\;)(\\s*)", query)){
	        		
	        	return true;	
	        	}
	        	return false;
	        case 3:
	        	if(Pattern.matches("(\\s+|)(INSERT)(\\s+)(INTO)(\\s+)(\\w+)(\\s*)(\\(){1}(\\s*)((\\w+)(\\s*)(\\,|)(\\s*))+(\\s*)(\\)){1}(\\s+)(VALUES)(\\s*)(\\(){1}((\\s*)(\\w+)(\\s*)(\\,|)(\\s*))+(\\s*)(\\)){1}(\\s*)(\\;)(\\s*)", query)){
	        		return true;	
	            	}
	        	return false;
	        case 4:
	        	if(Pattern.matches("(\\s+|)(DELETE)(\\s+)(FROM)(\\s+)(\\w+)(\\s+)(WHERE)(\\s+)(\\w+)(\\s*)(<|>|=)(\\s*)(\\w+)(\\s*)(\\;)(\\s*)", query)){
	        		return true;
	        	}
	        	return false;
	        case 5:
	        	if(Pattern.matches("(\\s*)(SELECT)(\\s+)((\\s*)(\\w+)(\\s*)(\\,|)(\\s*))+(\\s+)(FROM)(\\s+)(\\w+)(\\s+)(WHERE)(\\s+)(\\w+)(\\s*)(<|>|=)(\\s*)(\\w+)(\\s*)(\\;)(\\s*)",query)){
	        		
	        		return true;
	        	}
	        	return false;
	     
	        }
	    	return false;	
	    }

}
