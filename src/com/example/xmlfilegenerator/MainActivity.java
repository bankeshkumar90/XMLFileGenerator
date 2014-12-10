package com.example.xmlfilegenerator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.Writer;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	private Button btn;
	private EditText fname,mname,lname;
	private TextView textview;
	String f_name = "";
	String m_name = "";
	String l_name = "";
	
			
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btn = (Button)findViewById(R.id.btn);
		fname = (EditText)findViewById(R.id.firstname);
		mname = (EditText)findViewById(R.id.middlename);
		lname = (EditText)findViewById(R.id.lastname);
		textview = (TextView)findViewById(R.id.textview);
		
	
		
		
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				f_name = fname.getText().toString();
				m_name = mname.getText().toString();
				l_name = lname.getText().toString();//fname
			
			
				try {
					DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
							.newInstance();
					DocumentBuilder documentBuilder = documentBuilderFactory
							.newDocumentBuilder();
					Document document = documentBuilder.newDocument();

					Element rootElement = document.createElement("catalog");
					rootElement.setAttribute("journal", "Oracle Magazine");
					rootElement.setAttribute("publisher", "Oracle Publishing");
					document.appendChild(rootElement);

					Element articleElement = document.createElement("article");
					rootElement.appendChild(articleElement);

					Element editionElement = document.createElement("FirstName");
					articleElement.appendChild(editionElement);
					editionElement
							.appendChild(document.createTextNode(f_name));

					Element titleElement = document.createElement("MiddleName");
					articleElement.appendChild(titleElement);
					titleElement.appendChild(document
							.createTextNode(m_name));

					Element authorElement = document.createElement("Lastname");
					articleElement.appendChild(authorElement);
					authorElement.appendChild(document.createTextNode(l_name));
/*
					articleElement = document.createElement("article");
					rootElement.appendChild(articleElement);

					editionElement = document.createElement("edition");
					articleElement.appendChild(editionElement);
					editionElement.appendChild(document
							.createTextNode(f_name));

					titleElement = document.createElement("title");
					articleElement.appendChild(titleElement);
					titleElement.appendChild(document
							.createTextNode(m_name));

					authorElement = document.createElement("author");
					articleElement.appendChild(authorElement);
					authorElement.appendChild(document.createTextNode(l_name));*/

					TransformerFactory factory = TransformerFactory.newInstance();
					Transformer transformer = factory.newTransformer();
					Properties outFormat = new Properties();
					outFormat.setProperty(OutputKeys.INDENT, "yes");
					outFormat.setProperty(OutputKeys.METHOD, "xml");
					outFormat.setProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
					outFormat.setProperty(OutputKeys.VERSION, "1.0");
					outFormat.setProperty(OutputKeys.ENCODING, "UTF-8");
					transformer.setOutputProperties(outFormat);
					DOMSource domSource = new DOMSource(document.getDocumentElement());
					OutputStream output = new ByteArrayOutputStream();
					StreamResult result = new StreamResult(output);
					transformer.transform(domSource, result);
					String xmlString = output.toString();
					textview.setText(xmlString);
					
					
					
					
					 FileOutputStream fos = null;

				        try {
				            final File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/xmldoc/" );

				            if (!dir.exists())
				            {
				                dir.mkdirs(); 
				            }

				            String outFile =
				                    Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "/xmldoc/" + "doc.xml";

				            Writer writer = new FileWriter(outFile);
				            writer.write( xmlString.toString());
				            writer.close();
				            
				        } catch (IOException e) {
				            // TODO Auto-generated catch block
				            e.printStackTrace();
				        }
					
					
					
					
					
					

				} catch (ParserConfigurationException e) {
				} catch (TransformerConfigurationException e) {
				} catch (TransformerException e) {
				}

			}
				 
			});
		
	}
}
