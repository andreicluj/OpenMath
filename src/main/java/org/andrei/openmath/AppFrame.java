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

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class AppFrame extends JFrame {

    BufferedImage img = null;
    private Icon displayPhoto;
    private JLabel photographLabel = new JLabel();

    public AppFrame() throws Exception {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel emptyLabel = new JLabel("");
        emptyLabel.setPreferredSize(new Dimension(355, 355));
        
        getContentPane().add(emptyLabel, BorderLayout.CENTER);
        try {
            img = ImageIO.read(new File("test1.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //net.sourceforge.jeuclid.MathMLParserSupport
        
          /* Parse some very basic Math Mode input */
        
        SnuggleEngine engine = new SnuggleEngine();
        SnuggleSession session = engine.createSession();
        SnuggleInput input = new SnuggleInput("$$ x^3+y^5+2=3 $$");
        session.parseInput(input);

        /* Convert the results to an XML String, which in this case will
         * be a single MathML <math>...</math> element. */
        String xmlString = session.buildXMLString();
        Document doc = net.sourceforge.jeuclid.MathMLParserSupport.parseString(xmlString);
        //System.out.println("Input " + input.getString() + " was converted to:\n" + xmlString);
        
        MutableLayoutContext params = new LayoutContextImpl(LayoutContextImpl.getDefaultLayoutContext());
        BufferedImage bi = Converter.getInstance().render(doc, params);

        displayPhoto = new ImageIcon(bi);
        emptyLabel.setIcon(displayPhoto);
        //Display the window.
        pack();
        setVisible(true);
    }

    public static void main(String[] args) throws IOException , Exception{
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        AppFrame app = new AppFrame();

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

    protected ImageIcon createImageIcon(String path,
            String description) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

}
