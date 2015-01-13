package com.googlecode.cchlib.xml;

import java.io.FileOutputStream;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class StaxWriter
{
    private String configFile;

    public void setFile(final String configFile) {
        this.configFile = configFile;
    }

    public void saveConfig() throws Exception {
        // Create a XMLOutputFactory
        final XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();

        try( final FileOutputStream os = new FileOutputStream(this.configFile) ) {
            // Create XMLEventWriter
            final XMLEventWriter eventWriter = outputFactory.createXMLEventWriter( os );
            // Create a EventFactory
            final XMLEventFactory eventFactory = XMLEventFactory.newInstance();
            final XMLEvent end = eventFactory.createDTD("\n");
            // Create and write Start Tag
            final StartDocument startDocument = eventFactory.createStartDocument();
            eventWriter.add(startDocument);

            // Create config open tag
            final StartElement configStartElement = eventFactory.createStartElement("",
                    "", "config");
            eventWriter.add(configStartElement);
            eventWriter.add(end);
            // Write the different nodes
            createNode(eventWriter, "mode", "1");
            createNode(eventWriter, "unit", "901");
            createNode(eventWriter, "current", "0");
            createNode(eventWriter, "interactive", "0");

            eventWriter.add(eventFactory.createEndElement("", "", "config"));
            eventWriter.add(end);
            eventWriter.add(eventFactory.createEndDocument());
            eventWriter.close();
            }
    }

    private void createNode(final XMLEventWriter eventWriter, final String name,
            final String value) throws XMLStreamException {

        final XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        final XMLEvent end = eventFactory.createDTD("\n");
        final XMLEvent tab = eventFactory.createDTD("\t");
        // Create Start node
        final StartElement sElement = eventFactory.createStartElement("", "", name);
        eventWriter.add(tab);
        eventWriter.add(sElement);
        // Create Content
        final Characters characters = eventFactory.createCharacters(value);
        eventWriter.add(characters);
        // Create End node
        final EndElement eElement = eventFactory.createEndElement("", "", name);
        eventWriter.add(eElement);
        eventWriter.add(end);

    }

    public static void main(final String[] args) {
        final StaxWriter configFile = new StaxWriter();
        configFile.setFile("config2.xml");
        try {
            configFile.saveConfig();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}
