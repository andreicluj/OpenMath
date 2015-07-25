package org.andrei.openmath;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import net.sourceforge.jeuclid.MathMLParserSupport;
import net.sourceforge.jeuclid.MutableLayoutContext;
import net.sourceforge.jeuclid.context.LayoutContextImpl;
import net.sourceforge.jeuclid.context.Parameter;
import net.sourceforge.jeuclid.converter.Converter;
import net.sourceforge.jeuclid.converter.ConverterPlugin.DocumentWithDimension;
import net.sourceforge.jeuclid.converter.ConverterRegistry;
import net.sourceforge.jeuclid.elements.generic.DocumentElement;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGDocument;

import uk.ac.ed.ph.snuggletex.SnuggleInput;
import uk.ac.ed.ph.snuggletex.SnuggleEngine;
import uk.ac.ed.ph.snuggletex.SnuggleSession;
import java.io.IOException;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;


public class App {

    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("FrameDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel emptyLabel = new JLabel("");
        emptyLabel.setPreferredSize(new Dimension(355, 355));
        frame.getContentPane().add(emptyLabel, BorderLayout.CENTER);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });

        /* Create vanilla SnuggleEngine and new SnuggleSession */
        SnuggleEngine engine = new SnuggleEngine();
        SnuggleSession session = engine.createSession();

        /* Parse some very basic Math Mode input */
        SnuggleInput input = new SnuggleInput("$$ x+2=3 $$");
        session.parseInput(input);

        /* Convert the results to an XML String, which in this case will
         * be a single MathML <math>...</math> element. */
        String xmlString = session.buildXMLString();
        System.out.println("Input " + input.getString()
                + " was converted to:\n" + xmlString);

    }
    
    
    
    
}
