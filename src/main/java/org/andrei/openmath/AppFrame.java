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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

public class AppFrame extends JFrame implements ActionListener {

    BufferedImage img = null;
    private Icon displayPhoto;
    private JLabel photographLabel = new JLabel();
    net.sourceforge.jeuclid.swing.JMathComponent mathComp;
    protected JLabel formulaLabel = new JLabel("Type formula bellow :");
    protected JTextArea textArea = new JTextArea(5, 20);
    protected JScrollPane scrollPanelForText = new JScrollPane(textArea);
    protected JButton displayFormula = new JButton("Display formula");
    JLabel emptyLabel = new JLabel("");

    JPanel inputPanel = new JPanel();

    protected String xmlString;
    //private JPanel drawingArea = new JPanel();
    

    public AppFrame() throws Exception {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.PAGE_AXIS));
        emptyLabel.setPreferredSize(new Dimension(355, 355));
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.LINE_AXIS));
        northPanel.add(formulaLabel);
        northPanel.add(Box.createHorizontalGlue());
        inputPanel.add(northPanel);

        //formulaLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        formulaLabel.setLabelFor(scrollPanelForText);

        inputPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        inputPanel.add(scrollPanelForText);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.LINE_AXIS));
        southPanel.add(displayFormula);
        southPanel.add(Box.createHorizontalGlue());
        displayFormula.addActionListener(this);
        displayFormula.setActionCommand("display");
        //displayFormula.setAlignmentX(Component.LEFT_ALIGNMENT);

        inputPanel.add(southPanel);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        getContentPane().add(inputPanel, BorderLayout.NORTH);
        getContentPane().add(emptyLabel, BorderLayout.CENTER);

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

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("display".equals(e.getActionCommand())) {
            String latex = textArea.getText();
            TeXFormula formula = new TeXFormula(latex);
            TeXIcon icon = formula
                    .createTeXIcon(TeXConstants.STYLE_DISPLAY, 20);
            icon.setInsets(new Insets(5, 5, 5, 5));
            BufferedImage image = new BufferedImage(icon.getIconWidth(),
                    icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2 = image.createGraphics();
            g2.setColor(Color.white);
            g2.fillRect(0, 0, icon.getIconWidth(), icon.getIconHeight());
            JLabel jl = new JLabel();
            jl.setForeground(new Color(0, 0, 0));
            icon.paintIcon(jl, g2, 0, 0);
			// at this point the image is created, you could also save it with ImageIO

            // now draw it to the screen			
            Graphics g = this.emptyLabel.getGraphics();
            g.drawImage(image, 0, 0, null);

            //displayPhoto = new ImageIcon(image);
            //emptyLabel.setIcon(displayPhoto);
        }
    }

}
