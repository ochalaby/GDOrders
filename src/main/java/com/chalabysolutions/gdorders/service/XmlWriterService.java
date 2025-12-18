package com.chalabysolutions.gdorders.service;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.OutputStream;
import java.util.Objects;

public class XmlWriterService {

    private final JAXBContext ctx;

    public XmlWriterService(Class<?>... jaxbClasses) throws Exception {
        this.ctx = JAXBContext.newInstance(jaxbClasses);
    }

    /**
     * Marshals the given JAXB object to the provided OutputStream as pretty-printed XML.
     * Produces self-closing tags for empty elements.
     */
    public void write(Object jaxbObject, OutputStream output) throws Exception {
        Objects.requireNonNull(jaxbObject, "jaxbObject");
        Objects.requireNonNull(output, "output");

        // 1) Marshal to DOM
        Document doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder().newDocument();

        Marshaller marshaller = ctx.createMarshaller();
        // We don't rely on Marshaller.JAXB_FORMATTED_OUTPUT here — we'll pretty-print the DOM later
        marshaller.marshal(jaxbObject, doc);

        // 2) Transform DOM to OutputStream with indentation
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();

        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        // Indent amount (implementation-specific, works for most processors)
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        DOMSource domSource = new DOMSource(doc);
        StreamResult result = new StreamResult(output);

        transformer.transform(domSource, result);
        output.flush();
    }
}
