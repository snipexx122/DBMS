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
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
public class Engine {
	private final String NS_PREFIX = "xs:";
	private ArrayList table;
	private ArrayList insert;
	private ArrayList read;
	private ArrayList adjusted;
	private ArrayList readread;
	private ArrayList bol;
	private ArrayList deleteinfo;
	private ArrayList select;
	private static String pathToFolder= "C:\\Users\\abdullah\\Desktop\\project2files";
	private ArrayList<File> xmlFiles;
	private ArrayList<File> xsdFiles;
	private File directoryFolder;
	private xmlValidator val;
	public Engine(){
		bol = new ArrayList();
		deleteinfo = new ArrayList();
		table=new ArrayList();
		adjusted=new ArrayList();
		read=new ArrayList();
		insert=new ArrayList();
		readread=new ArrayList();
		xmlFiles=new ArrayList<>();
		xsdFiles=new ArrayList<>();
		select =new ArrayList();
		directoryFolder = new File(pathToFolder);
		scanFiles();
		manipadjusted();
	}
	public void manipadjusted(){
		File file = new File("C:\\Users\\abdullah\\Desktop\\project2files\\info.txt");
		int i;
		String tokens;
		FileReader input = null;
		
		try{
			input = new FileReader(file);
			BufferedReader bf = new BufferedReader(new FileReader(file));
			i= 0 ;
			while(bf.ready()){
				tokens = bf.readLine();
				adjusted.add(i, tokens);
				++i;
				
			}
			}
			catch(Exception e){
				//System.out.println(e);
			}finally{
				try{
					input.close();
				}catch(Exception e){
					
				}
			}
		
	}
	public void updateadjusted(String str){
		File file = new File("C:\\Users\\abdullah\\Desktop\\project2files\\info.txt");
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
			
			bw.write(str);
			bw.newLine();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}
	public String[][] setselect(ArrayList arr){
		select.clear();
		select=(ArrayList)arr.clone();
		String str[][]=selectt();
		return str;
	}

	public void setFileStatus(ArrayList arr){
		table.clear();
		table=(ArrayList) arr.clone();
		createFile();
	}
	public int setFileDetails(ArrayList arr){
		insert.clear();
		insert=(ArrayList) arr.clone();
		int x=insertInFile();
		return x;
	}
	public int deleterowrow(ArrayList arr){
		deleteinfo.clear();
		deleteinfo=(ArrayList) arr.clone();
		int x=deleterow();
		return x;
		
	}
	public void scanFiles(){
		xmlFiles.clear();
		xsdFiles.clear();
		for( File file : directoryFolder.listFiles()){
			if(file.isFile() && file.getName().endsWith(".xml")){
				xmlFiles.add(file);
			}
			if(file.isFile() && file.getName().endsWith(".xsd")){
				xsdFiles.add(file);	
			}
		}
	}
	private void createFile(){
		XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
		File newFile = new File(pathToFolder+"\\"+table.get(0).toString()+".xml");
		xmlFiles.add(newFile);
		//adjusted.add(table.get(0));	
		updateadjusted(table.get(0).toString());
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter(newFile);
			 XMLStreamWriter xmlStreamWriter;
			
				xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(fileWriter);
			
		        xmlStreamWriter.writeStartDocument();
		        xmlStreamWriter.writeCharacters(System.getProperty("line.separator"));
		        xmlStreamWriter.writeStartElement(table.get(0).toString());
		        xmlStreamWriter.writeCharacters(System.getProperty("line.separator"));
		        xmlStreamWriter.writeStartElement("table");
		        xmlStreamWriter.writeCharacters(System.getProperty("line.separator"));
		          
		        for (int i=1;i<table.size();i=i+2) {
		         xmlStreamWriter.writeStartElement(table.get(i).toString());
		         xmlStreamWriter.writeCharacters(System.getProperty("line.separator"));
		         xmlStreamWriter.writeEndElement();
		         xmlStreamWriter.writeCharacters(System.getProperty("line.separator"));
		        }
		        xmlStreamWriter.writeEndElement();
		        xmlStreamWriter.writeCharacters(System.getProperty("line.separator"));
		        xmlStreamWriter.writeEndElement();
		        xmlStreamWriter.writeCharacters(System.getProperty("line.separator"));
		        xmlStreamWriter.writeEndDocument();
		        xmlStreamWriter.flush();
		        xmlStreamWriter.close();
		        fileWriter.close();
		        newFile=null;
		        createXsdFile();
					//writing xsd file
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		 
	}
	public void deleteFile(String name){
		boolean t=false;
		for(int i=0;i<xmlFiles.size();i++){
			if((name+".xml").equals(xmlFiles.get(i).getName().trim())){
				File file=xmlFiles.get(i);
				xmlFiles.remove(i);
				System.gc();
				if (file.exists()){
					System.out.println("file exists");
					t=file.delete();
				}
				
				
			}
		}
		for(int i=0;i<xsdFiles.size();i++){
			if((name+".xsd").equals(xsdFiles.get(i).getName().trim())){
				File file=xsdFiles.get(i);
				xsdFiles.remove(i);
				System.gc();
				if (file.exists()){
					System.out.println("file exists");
					t=file.delete();
					//System.out.println();
				}
			}
		}
	        if(t){
	            System.out.println(" is deleted!");
	        }else{
	            System.out.println("Delete operation has failed.");
	        }
	}
	public void createXsdFile(){
		 try {
	            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
	            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();

	            Document doc = docBuilder.newDocument();

	            Element schemaRoot = doc.createElementNS(XMLConstants.W3C_XML_SCHEMA_NS_URI, NS_PREFIX+"schema");
	            doc.appendChild(schemaRoot);
	            Element tablee = doc.createElementNS(XMLConstants.W3C_XML_SCHEMA_NS_URI, NS_PREFIX+"element");
	            tablee.setAttribute("name", table.get(0).toString());
	            schemaRoot.appendChild(tablee);
	            //doc.createElementNS(XMLConstants.W3C_XML_SCHEMA_NS_URI, NS_PREFIX+"schema");
	            Element complexType = doc.createElementNS(XMLConstants.W3C_XML_SCHEMA_NS_URI, NS_PREFIX+"complexType");
	            tablee.appendChild(complexType);
	            Element sequence = doc.createElementNS(XMLConstants.W3C_XML_SCHEMA_NS_URI, NS_PREFIX+"sequence");
	            complexType.appendChild(sequence);
	            NameTypeElementMaker elMaker = new NameTypeElementMaker(NS_PREFIX, doc);
	            tablee =  doc.createElementNS(XMLConstants.W3C_XML_SCHEMA_NS_URI, NS_PREFIX + "element");
	            tablee.setAttribute("name", "table");
	            tablee.setAttribute("minOccurs", "0");
	            tablee.setAttribute("maxOccurs", "unbounded");
	            sequence.appendChild(tablee);
	            complexType = doc.createElementNS(XMLConstants.W3C_XML_SCHEMA_NS_URI, NS_PREFIX+"complexType");
	            tablee.appendChild(complexType);
	            
	            Element choice = doc.createElementNS(XMLConstants.W3C_XML_SCHEMA_NS_URI, NS_PREFIX + "choice");
	            choice.setAttribute("minOccurs", "0");
	            choice.setAttribute("maxOccurs", "unbounded");
	            complexType.appendChild(choice);
	            
	            for(int i=1;i<table.size();i+=2){
	            	Element x=elMaker.createElement("element",table.get(i).toString(),table.get(i+1).toString().equalsIgnoreCase("int")? "xs:int" : "xs:string" );
	            	x.setAttribute("minOccurs", "0");
	            	choice.appendChild(x);
	            }
	            
	            TransformerFactory tFactory = TransformerFactory.newInstance();
	            Transformer transformer = tFactory.newTransformer();
	            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
	            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	            DOMSource domSource = new DOMSource(doc);
	            //to create a file use something like this:
	            transformer.transform(domSource, new StreamResult(new File(pathToFolder+"\\"+table.get(0)+".xsd")));
	            scanFiles();
	            
	            //to print to console use this:
	            //transformer.transform(domSource, new StreamResult(System.out));
	        }
	        catch (FactoryConfigurationError | ParserConfigurationException | TransformerException e) {
	            //handle exception
	            e.printStackTrace();
	        }
	}
	public int insertInFile(){
		int count=0;
		read.clear();
		int flag=0;
		for(int i=0;i<adjusted.size();i++){
			if(insert.get(0).toString().equals(adjusted.get(i).toString())){
				flag=1;
				
			}
		}
		if(flag==0){
			adjusted.add(insert.get(0));
			read=(ArrayList) insert.clone();
			//count=read.size();
			count++;
		}
		
		else{
			
			read.clear();
			
		    readfile();
		for(int i=1;i<insert.size();i++){
			read.add(insert.get(i));
		}
		for(int i=0;i<read.size();i++){
			if(read.get(i).equals("seperator")){
				count++;
			}
		}
		count++;
		
		/*for(int i=0;i<read.size();i++){
			System.out.println(read.get(i));
		}*/
		}
		XMLOutputFactory xmloutputfactory=XMLOutputFactory.newFactory();
		try {
			File file=new File(pathToFolder+"\\"+read.get(0)+".xml");
			XMLStreamWriter xmlstreamwriter=xmloutputfactory.createXMLStreamWriter(new FileOutputStream(file));
			xmlstreamwriter.writeStartDocument("1.0");
			xmlstreamwriter.writeCharacters(System.getProperty("line.separator"));
			xmlstreamwriter.writeStartElement(read.get(0).toString());
			xmlstreamwriter.writeCharacters(System.getProperty("line.separator"));
			xmlstreamwriter.writeStartElement("table");
			for(int i=1;i<read.size();i=i+2){
				if(read.get(i).toString().equals("seperator")){
					xmlstreamwriter.writeCharacters(System.getProperty("line.separator"));
					xmlstreamwriter.writeEndElement();
					xmlstreamwriter.writeCharacters(System.getProperty("line.separator"));
					xmlstreamwriter.writeStartElement("table");
					i--;
				}
				else{
			xmlstreamwriter.writeCharacters(System.getProperty("line.separator"));
			xmlstreamwriter.writeStartElement(read.get(i).toString());
			xmlstreamwriter.writeCharacters(System.getProperty("line.separator"));
			xmlstreamwriter.writeCharacters(read.get(i+1).toString());
			xmlstreamwriter.writeCharacters(System.getProperty("line.separator"));
			xmlstreamwriter.writeEndElement();
				}
			}
			xmlstreamwriter.writeCharacters(System.getProperty("line.separator"));
			xmlstreamwriter.writeEndElement();
			xmlstreamwriter.writeCharacters(System.getProperty("line.separator"));
			xmlstreamwriter.writeEndElement();
			xmlstreamwriter.flush();
			xmlstreamwriter.close();
			 val=new xmlValidator(pathToFolder+"\\"+read.get(0)+".xml",pathToFolder+"\\"+read.get(0)+".xsd");
			 boolean t=val.validate();
			 if(t){
				 System.out.println("follows specification");
			 }
			 else{
				 System.out.println("not following sepcifications");
			 }
			 file=null;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
		return count;
	}
	public void readfile(){
			XMLInputFactory factory = XMLInputFactory.newInstance();
			XMLStreamReader streamReader;
			File file=new File(pathToFolder+"\\"+insert.get(0)+".xml");
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
				}
				    streamReader.close();
				    file=null;
				
			}
			catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XMLStreamException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	
	public int deleterow(){
		bol.clear();
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLStreamReader streamReader;
		File file=new File(pathToFolder+"\\"+deleteinfo.get(0)+".xml");
		try {
			streamReader = factory.createXMLStreamReader(new FileReader(file));
			int flag=0;
			while(streamReader.hasNext()){
				streamReader.next();
				while(streamReader.hasNext()){
					if(streamReader.getEventType() == XMLStreamReader.END_ELEMENT){
						if(streamReader.getLocalName().equals("table")){
							break;
						}
					}
					streamReader.next();
			    if(streamReader.getEventType() == XMLStreamReader.START_ELEMENT&&streamReader.getLocalName().equals(deleteinfo.get(1))){
			    	while(!streamReader.isCharacters()){
			    		streamReader.next();
			    	}
			    	switch(deleteinfo.get(2).toString()){
			    	case ">":
			    		if(streamReader.getEventType()==XMLStreamReader.CHARACTERS && Integer.parseInt(streamReader.getText().trim())> Integer.parseInt(deleteinfo.get(3).toString())){
				    		bol.add(true);
				    		flag=1;
				        }
			    		else{
			    			flag=1;
			    			bol.add(false);
			    		}
			    		
			    		break;
			    		
                    case "<":
                    	if(streamReader.getEventType()==XMLStreamReader.CHARACTERS && Integer.parseInt(streamReader.getText().trim())< Integer.parseInt(deleteinfo.get(3).toString())){
				    		bol.add(true);
					        flag=1;
				        }
			    		else{
			    			flag=1;
			    			bol.add(false);
			    		}
			    		break;
			    		
                    case "=":
                    	if(streamReader.getEventType()==XMLStreamReader.CHARACTERS && Integer.parseInt(streamReader.getText().trim())== Integer.parseInt(deleteinfo.get(3).toString())){
				    		bol.add(true);
					        flag=1;
				        }
			    		else{
			    			flag=1;
			    			bol.add(false);
			    		}
	
	                    break;
	 		    	}
			    }
				}
				if(flag==0){
					bol.add(false);
				}
				else{
					flag=0;
				}
			}
			bol.remove(bol.size()-1);
			streamReader.close();
			ArrayList arr=new ArrayList();
			read.clear();
			int j=0;
			try {
				streamReader = factory.createXMLStreamReader(new FileReader(file));
				while(streamReader.hasNext()){
					streamReader.next();
				    if(streamReader.getEventType() == XMLStreamReader.START_ELEMENT&&!streamReader.getLocalName().equals("table")){
				    	arr.add(streamReader.getLocalName());
				        if(streamReader.getAttributeCount()>0){
				        	for(int i=0;i<streamReader.getAttributeCount();i++){
				        	}
				        }
				    }
				    else if(streamReader.getEventType() == XMLStreamReader.END_ELEMENT&&streamReader.getLocalName().equals("table")){
				    	if(!(boolean)bol.get(j)){
				    		for(int i=0;i<arr.size();i++){
				    			//System.out.println(arr.get(i));
				    			read.add(arr.get(i));
				    		}
				    		read.add("seperator");
				    		arr.clear();
				    	}
				    	else{
				    		arr.clear();
				    	}
				    	j++;
				    }
				    else if(streamReader.getEventType()==XMLStreamReader.CHARACTERS){
				    	if(!streamReader.getText().isEmpty()&&!streamReader.getText().equals("\n")&&!streamReader.getText().equals(" ")){
				    	arr.add(streamReader.getText().trim());
				    	}
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
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		read.remove(read.size()-1);
		XMLOutputFactory xmloutputfactory=XMLOutputFactory.newFactory();
		try {
			XMLStreamWriter xmlstreamwriter=xmloutputfactory.createXMLStreamWriter(new FileOutputStream(file));
			xmlstreamwriter.writeStartDocument("1.0");
			xmlstreamwriter.writeCharacters(System.getProperty("line.separator"));
			xmlstreamwriter.writeStartElement(deleteinfo.get(0).toString());
			xmlstreamwriter.writeCharacters(System.getProperty("line.separator"));
			xmlstreamwriter.writeStartElement("table");
			if(read.get(0).equals(deleteinfo.get(0))){
			for(int i=1;i<read.size();i=i+2){
				//System.out.println(read.get(i));
				if(read.get(i).toString().equals("seperator")){
					xmlstreamwriter.writeCharacters(System.getProperty("line.separator"));
					xmlstreamwriter.writeEndElement();
					xmlstreamwriter.writeCharacters(System.getProperty("line.separator"));
					xmlstreamwriter.writeStartElement("table");
					i--;
				}
				else{
			xmlstreamwriter.writeCharacters(System.getProperty("line.separator"));
			xmlstreamwriter.writeStartElement(read.get(i).toString());
			xmlstreamwriter.writeCharacters(System.getProperty("line.separator"));
			xmlstreamwriter.writeCharacters(read.get(i+1).toString());
			xmlstreamwriter.writeCharacters(System.getProperty("line.separator"));
			xmlstreamwriter.writeEndElement();
				}
			}
			}
			else{
				for(int i=0;i<read.size();i=i+2){
					//System.out.println(read.get(i));
					if(read.get(i).toString().equals("seperator")){
						xmlstreamwriter.writeCharacters(System.getProperty("line.separator"));
						xmlstreamwriter.writeEndElement();
						xmlstreamwriter.writeCharacters(System.getProperty("line.separator"));
						xmlstreamwriter.writeStartElement("table");
						i--;
					}
					else{
				xmlstreamwriter.writeCharacters(System.getProperty("line.separator"));
				xmlstreamwriter.writeStartElement(read.get(i).toString());
				xmlstreamwriter.writeCharacters(System.getProperty("line.separator"));
				xmlstreamwriter.writeCharacters(read.get(i+1).toString());
				xmlstreamwriter.writeCharacters(System.getProperty("line.separator"));
				xmlstreamwriter.writeEndElement();
					}
				}
			}
			xmlstreamwriter.writeCharacters(System.getProperty("line.separator"));
			xmlstreamwriter.writeEndElement();
			xmlstreamwriter.writeCharacters(System.getProperty("line.separator"));
			xmlstreamwriter.writeEndElement();
			xmlstreamwriter.flush();
			xmlstreamwriter.close();
			 val=new xmlValidator(pathToFolder+"\\"+deleteinfo.get(0)+".xml",pathToFolder+"\\"+deleteinfo.get(0)+".xsd");
			 boolean t=val.validate();
			 file=null;
			 if(t){
				 System.out.println("follows specification");
			 }
			 else{
				 System.out.println("fuck");
			 }
			
		   
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
		int count=0;
		
		read.clear();
		for(int i=0;i<bol.size();i++){
			if(!(boolean)bol.get(i)){
				count++;
			}
		}
		return count;
		
	}
	private String[][] selectt(){
		File file=new File(pathToFolder+"\\"+select.get(select.size()-4)+".xml");
		bol.clear();
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLStreamReader streamReader;
		int flag=0;
		try {
			//System.out.println(select.get(select.size()-4).toString());
				streamReader = factory.createXMLStreamReader(new FileReader(file));
			
			while(streamReader.hasNext()){
				streamReader.next();
				while(streamReader.hasNext()){
					if(streamReader.getEventType() == XMLStreamReader.END_ELEMENT){
						if(streamReader.getLocalName().equals("table")){
							break;
						}
					}
					streamReader.next();
			    if(streamReader.getEventType() == XMLStreamReader.START_ELEMENT&&streamReader.getLocalName().equals(select.get(select.size()-3))){
			    	while(!streamReader.isCharacters()){
			    		streamReader.next();
			    	}
			    	switch(select.get(select.size()-2).toString()){
			    	case ">":
			    		if(streamReader.getEventType()==XMLStreamReader.CHARACTERS && Integer.parseInt(streamReader.getText().trim())> Integer.parseInt(select.get(select.size()-1).toString())){
				    		bol.add(true);
				    		flag=1;
					        
				        }
			    		else{
			    			flag=1;
			    			bol.add(false);
			    		}
			    		
			    		break;
			    		
			        case "<":
			        	if(streamReader.getEventType()==XMLStreamReader.CHARACTERS && Integer.parseInt(streamReader.getText().trim())< Integer.parseInt(select.get(select.size()-1).toString())){
				    		bol.add(true);
					        flag=1;
				        }
			    		else{
			    			flag=1;
			    			bol.add(false);
			    		}
			    		break;
			    		
			        case "=":
			        	if(streamReader.getEventType()==XMLStreamReader.CHARACTERS && Integer.parseInt(streamReader.getText().trim())== Integer.parseInt(select.get(select.size()-1).toString())){
				    		bol.add(true);
					        flag=1;
				        }
			    		else{
			    			flag=1;
			    			bol.add(false);
			    		}

			            break;
			    	}			    	
			    }
				}
				if(flag==0){
					bol.add(false);
				}
				else{
					flag=0;
				}
				streamReader.close();
			}
			bol.remove(bol.size()-1);
			streamReader.close();
		} catch (NumberFormatException | XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList arr=new ArrayList();
		ArrayList arrr=new ArrayList();
		read.clear();
		int count=0;
		for(int i=0;i<bol.size();i++){
			if((boolean)bol.get(i)){
				count++;
			}
		}
		String[][] str=new String[count][(select.size()-4)*2]; 
		int j=0;
		int k=0;
		try {
			
			streamReader = factory.createXMLStreamReader(new FileReader(file));
			for(int i=0;i<select.size()-4;i++){
				arr.add(select.get(i));
				//System.out.println(select.get(i));
			}
			while(streamReader.hasNext()){
				streamReader.next();
			    if(streamReader.getEventType() == XMLStreamReader.START_ELEMENT&&arr.contains(streamReader.getLocalName().trim())){
			    	arrr.add(streamReader.getLocalName());
			    	while(streamReader.getEventType()!=XMLStreamReader.CHARACTERS){
			    		streamReader.next();
			    	}
			    	arrr.add(streamReader.getText().trim());
			    }
			    else if(streamReader.getEventType() == XMLStreamReader.END_ELEMENT&&streamReader.getLocalName().equals("table")){
			    	if((boolean)bol.get(j)){
			    		//System.out.println(arr.size());
			    		for(int i=0;i<arrr.size();i++){
			    			//System.out.println(arrr.get(i));
			    			//read.add(arrr.get(i));
			    			str[k][i]=arrr.get(i).toString();
			    		}
			    		arrr.clear();
			    		k++;
			    		j++;
			    	}
			    	else{
			    		arrr.clear();
			    		j++;
			    	}
			    }
			}
			file=null;
			streamReader.close();
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return str;
	}
	}


