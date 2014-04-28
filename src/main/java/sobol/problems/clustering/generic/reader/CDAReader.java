package sobol.problems.clustering.generic.reader;

import java.io.File;
import java.io.IOException;
import javax.management.modelmbean.XMLParseException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import sobol.problems.clustering.generic.model.DependencyType;
import sobol.problems.clustering.generic.model.ElementType;
import sobol.problems.clustering.generic.model.ElementVisibility;
import sobol.problems.clustering.generic.model.Project;
import sobol.problems.clustering.generic.model.ProjectClass;
import sobol.problems.clustering.generic.model.ProjectPackage;

public class CDAReader
{
	/**
	 * Carrega um arquivo XML para a memória
	 */
	private Document loadDocument(String filename) throws XMLParseException
	{
		File file = new File(filename);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try
		{
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();
			return doc;
		}
		catch (ParserConfigurationException e)
		{
			throw new XMLParseException ("invalid XML content in file '" + filename + "'");
		}
		catch (SAXException e)
		{
			throw new XMLParseException ("invalid XML content in file '" + filename + "'");
		}
		catch (IOException e)
		{
			throw new XMLParseException ("unable to load file '" + filename + "'");
		}
	}

	/**
	 * Retorna o valor de um atributo de um elemento
	 */
	private String getElementAttribute(Element element, String name) throws XMLParseException
	{
		String value = element.getAttribute(name);
		
		if (value == null)
			throw new XMLParseException("missing attribute '" + name + "' for element '" + element.getNodeName() + "'");
		
		return value;
	}

	/**
	 * Retorna o valor de um atributo de um elemento, usando um valor default em sua ausência
	 */
	private String getElementAttribute(Element element, String name, String defvalue)
	{
		String value = element.getAttribute(name);
		
		if (value == null)
			return defvalue;
		
		return value;
	}
	
	/**
	 * Carrega o primeiro filho de um elemento com uma determinada tag
	 */
	private Element getFirstElement(Element element, String tag) throws XMLParseException
	{
		NodeList nodeList = element.getElementsByTagName(tag);

		if (nodeList.getLength() == 0)
			throw new XMLParseException("missing child tag '" + tag + "' under '" + element.getNodeName() + "'");

		return (Element) nodeList.item(0);		
	}
	
	/**
	 * Carrega as dependëncias de uma classe
	 */
	private void loadDependencies(ProjectClass aClass, Element element) throws XMLParseException
	{
		Element dependencyRoot = getFirstElement(element, "dependencies");
		NodeList nodeList = dependencyRoot.getElementsByTagName("depends-on");

		for (int i = 0; i < nodeList.getLength(); i++)
		{
			Element child = (Element)nodeList.item(i);
			String name = getElementAttribute(child, "name");
			String sClassification = getElementAttribute(child, "classification");
			
			DependencyType classification = DependencyType.fromIdentifier(sClassification);
			
			if (classification == null)
				throw new XMLParseException("invalid classification '" + sClassification + "' for dependency from '" + aClass.getName() + "' to '" + name + "'");

			aClass.addDependency(name, classification);
		}
	}

	/**
	 * Carrega as classes de um pacote
	 */
	private void loadClasses(Project project, ProjectPackage apackage, Element element) throws XMLParseException
	{
		NodeList nodeList = element.getElementsByTagName("type");

		for (int i = 0; i < nodeList.getLength(); i++)
		{
			Element child = (Element)nodeList.item(i);
			String name = getElementAttribute(child, "name");
			String sClassification = getElementAttribute(child, "classification");
			String sVisibility = getElementAttribute(child, "visibility");
			String sAbstract = getElementAttribute(child, "isAbstract", "false");
			
			ElementType classification = ElementType.fromIdentifier(sClassification);
			
			if (classification == null)
				throw new XMLParseException("invalid classification '" + sClassification + "' for type '" + name + "'");
			
			ElementVisibility visibility = ElementVisibility.fromIdentifier(sVisibility);
			
			if (visibility == null)
				throw new XMLParseException("invalid visibility '" + sVisibility + "' for type '" + name + "'");
			
			ProjectClass aClass = new ProjectClass(name, classification, visibility, Boolean.parseBoolean(sAbstract));
			aClass.setPackage(apackage);
			project.addClass(aClass);
			loadDependencies(aClass, child);
		}
	}

	/**
	 * Carrega os pacotes da aplicação
	 */
	private void loadNamespaces(Project project, Element element) throws XMLParseException
	{
		NodeList nodeList = element.getElementsByTagName("namespace");

		for (int i = 0; i < nodeList.getLength(); i++)
		{
			Element child = (Element)nodeList.item(i);
			String name = getElementAttribute(child, "name");

			ProjectPackage apackage = project.addPackage(name);
			loadClasses(project, apackage, child);
		}
	}

	/**
	 * Carrega os containers da aplicação
	 */
	private void loadContainers(Project project, Element element) throws XMLParseException
	{
		NodeList nodeList = element.getElementsByTagName("container");

		for (int i = 0; i < nodeList.getLength(); i++)
			loadNamespaces(project, (Element)nodeList.item(i));
	}

	/**
	 * Carrega uma aplicação a partir do elemento raiz do arquivo
	 */
	private Project loadApplication(Element root) throws XMLParseException
	{
		Element element = getFirstElement(root, "context");		
		String name = getElementAttribute(element, "name");
		Project application = new Project(name);
		loadContainers(application, element);
		return application;
	}

	/**
	 * Carrega uma aplicação a partir de um arquivo no formato XML ODEM
	 */
	public Project execute(String filename) throws XMLParseException
	{
		Document doc = loadDocument(filename);

		if (doc == null)
			return null;

		return loadApplication(doc.getDocumentElement());
	}
}