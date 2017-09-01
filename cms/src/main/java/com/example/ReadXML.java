package com.example;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class ReadXML {
    public NodeList readXML(File xmlFile){
        try {
            //File xmlFile = new File(path);

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);

            document.getDocumentElement().normalize();;

            //System.out.println("Root element : " + document.getDocumentElement().getNodeName());

            NodeList nodeList = document.getElementsByTagName("sv:property");
            return nodeList;

           /* for(int i = 0; i < nodeList.getLength(); i++){
                System.out.println("Property name : " + nodeList.item(i).getAttributes().getNamedItem("sv:name").getNodeValue());
                Element element = (Element) nodeList.item(i);
                if(element.getElementsByTagName("sv:value").getLength() > 0){
                    System.out.println("Property value : " + element.getElementsByTagName("sv:value").item(0).getTextContent());
                } else{
                    System.out.println("null");
                }
                System.out.println("\n");
            }*/

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getRootName(File xmlFile){
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);

            document.getDocumentElement().normalize();;

            return document.getDocumentElement().getAttribute("sv:name").toString();

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args){
    }
}
