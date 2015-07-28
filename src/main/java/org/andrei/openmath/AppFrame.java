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
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;

public class AppFrame extends JFrame {

    BufferedImage img = null;
    private Icon displayPhoto;
    private JLabel photographLabel = new JLabel();
    net.sourceforge.jeuclid.swing.JMathComponent mathComp;
    protected JLabel formulaLabel = new JLabel("Type formula bellow :");
    protected JTextArea textArea = new JTextArea(5, 20);
    protected JScrollPane scrollPanelForText = new JScrollPane(textArea);
    protected JButton  displayFormula = new JButton("Display formula");
    JPanel inputPanel= new JPanel();
    
    

    public AppFrame() throws Exception {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.PAGE_AXIS));
        JLabel emptyLabel = new JLabel("");
        emptyLabel.setPreferredSize(new Dimension(355, 355));
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel , BoxLayout.LINE_AXIS));
        northPanel.add(formulaLabel);
        northPanel.add(Box.createHorizontalGlue());
        inputPanel.add(northPanel);
        
        //formulaLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        
        formulaLabel.setLabelFor(scrollPanelForText);

        inputPanel.add(Box.createRigidArea(new Dimension(0,5)));
        inputPanel.add(scrollPanelForText);
        inputPanel.add(Box.createRigidArea(new Dimension(0,5)));
        displayFormula.setAlignmentX(Component.LEFT_ALIGNMENT);
        inputPanel.add(displayFormula);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        getContentPane().add(inputPanel ,BorderLayout.NORTH);
        getContentPane().add(emptyLabel, BorderLayout.CENTER);
        try {
            img = ImageIO.read(new File("test1.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //net.sourceforge.jeuclid.MathMLParserSupport
        //mathComp = net.sourceforge.jeuclid.swing.JMathComponent();
        /* Parse some very basic Math Mode input */
        SnuggleEngine engine = new SnuggleEngine();
        SnuggleSession session = engine.createSession();
        SnuggleInput input = new SnuggleInput("$$ x^3+y^5+2=3 $$");
        session.parseInput(input);

        /* Convert the results to an XML String, which in this case will
         * be a single MathML <math>...</math> element. */
        String xmlString = session.buildXMLString();
        Path p = Paths.get("ex1.txt");
        //InputStream in = Files.newInputStream(p);
        //BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        Path file = Paths.get("ex1.txt");
        String latexText ="";
        try (InputStream in = Files.newInputStream(file);
                BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                latexText+=line + "\n";
                System.out.println(line);
            }
        } catch (IOException x) {
            System.err.println(x);
        }

        Document doc = net.sourceforge.jeuclid.MathMLParserSupport.parseString(latexText);
        //System.out.println("Input " + input.getString() + " was converted to:\n" + xmlString);

        MutableLayoutContext params = new LayoutContextImpl(LayoutContextImpl.getDefaultLayoutContext());
        params.setParameter(Parameter.MATHSIZE, 50f);
        BufferedImage bi = Converter.getInstance().render(doc, params);

        displayPhoto = new ImageIcon(bi);
        emptyLabel.setIcon(displayPhoto);
        //Display the window.
        pack();
        setVisible(true);
    }

    public static void main(String[] args) throws IOException, Exception {
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
