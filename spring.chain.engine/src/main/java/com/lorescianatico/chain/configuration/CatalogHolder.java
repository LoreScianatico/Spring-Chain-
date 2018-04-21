package com.lorescianatico.chain.configuration;

import com.lorescianatico.chain.configuration.model.Catalog;
import com.lorescianatico.chain.fault.InvalidCatalogException;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * The class for holding chain catalog
 */
@Slf4j
public final class CatalogHolder {

    /**
     * Schema definition
     */
    private static final String SCHEMA = "/catalog.xsd";

    /**
     * Catalog holder instance
     */
    private static final CatalogHolder ourInstance = new CatalogHolder();

    /**
     * The read catalog
     */
    private Catalog catalog;

    /**
     * Gets the catalog holder instance
     * @return catalog holder
     */
    public static CatalogHolder getInstance() {
        return ourInstance;
    }

    /**
     * Private constructor
     */
    private CatalogHolder() {}

    /**
     * Get the catalog configuration
     *
     * @param sourceFile the source of the catalog configuration
     * @return the deserialized catalog object
     */
    @Synchronized
    public Catalog getCatalog(String sourceFile){
        if (catalog==null){
            verifyCatalog(sourceFile);
            catalog=loadCatalog(sourceFile);
        }
        return catalog;
    }

    /**
     * Drops the current catalog
     */
    @Synchronized
    public void dropCatalog(){
        catalog=null;
    }

    /**
     * Verify that catalog configuration is compliant with the catalog schema
     * @param sourceFile the catalog to be verified
     */
    private void verifyCatalog(String sourceFile) {
        logger.debug("Validating catalog file: " + sourceFile);
        try (InputStream xml = new FileInputStream(sourceFile)) {
            InputStream xsd = this.getClass().getResourceAsStream(SCHEMA);
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(xsd));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xml));
            logger.debug("Catalog file is valid.");
        } catch (SAXException | IOException e) {
            logger.error("Error while validating configuration: " + e.getMessage());
            throw new InvalidCatalogException("Error while validating configuration: " + e.getMessage());
        }

    }

    /**
     * Loads catalog from source fils
     * @param sourceFile the catalog source
     * @return Deserialized catalog
     */
    private Catalog loadCatalog(String sourceFile) {
        logger.debug("Reading catalog: " + sourceFile);
        try (InputStream xml = new FileInputStream(sourceFile)){
            JAXBContext jaxbContext = JAXBContext.newInstance(Catalog.class.getPackage().getName());
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(xml);
            JAXBElement<Catalog> catalogDeserialized = jaxbUnmarshaller.unmarshal(xmlEventReader, Catalog.class);
            return  catalogDeserialized.getValue();
        } catch (JAXBException | XMLStreamException e) {
            logger.error("Error while unmarshalling catalog: " + e.getMessage());
            throw new InvalidCatalogException("Error while unmarshalling catalog: " + e.getMessage());
        } catch (IOException e) {
            logger.error("Error while reading catalog file: " + e.getMessage());
            throw new InvalidCatalogException("Error while reading catalog file: : " + e.getMessage());
        }
    }

}