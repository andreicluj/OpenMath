package org.andrei.openmath;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import net.sourceforge.jeuclid.MathMLParserSupport;
import net.sourceforge.jeuclid.MutableLayoutContext;
import net.sourceforge.jeuclid.context.LayoutContextImpl;
import net.sourceforge.jeuclid.context.Parameter;
import net.sourceforge.jeuclid.converter.Converter;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import org.andrei.entity.AppUser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGDocument;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import net.sourceforge.jeuclid.elements.generic.DocumentElement;
import org.andrei.entity.Problem;

import org.junit.Assert;
//import org.junit.Test;

public class AppTest
        extends TestCase {

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(AppTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() throws ParseException {
        SessionFactory sessionFactory = new Configuration().configure()
                .buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        AppUser user = new AppUser("firstuser");
        session.save(user);
        
        Problem p = new Problem();
        p.setProblemtext("$$ x^3+y^5+2=3 $$");
        DateFormat df = new SimpleDateFormat("dd/MM/YYYY");
        java.util.Date currrentDate = (java.util.Date)df.parse("5/5/2015");
        p.setPublishdate(new Date(currrentDate.getTime()));
        session.save(p);

        session.getTransaction().commit();
        session.close();

    }

    public static final String TEST1 = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?><math mode=\"display\">"
            + "<mrow><munderover><mo>&#x0222B;</mo><mn>1</mn><mi>x</mi></munderover>"
            + "<mfrac><mi>dt</mi><mi>t</mi></mfrac></mrow></math>";

    public void testConverterXXX(final String ext, final String mimeext)
            throws Exception {
        final Document doc = MathMLParserSupport
                .parseString(TEST1);
        final File outFile = new File(this.getOutDir(), "test1." + ext);
        final MutableLayoutContext params = new LayoutContextImpl(
                LayoutContextImpl.getDefaultLayoutContext());
        params.setParameter(Parameter.MATHSIZE, 25f);

        Converter.getInstance().convert(doc, outFile, "image/" + mimeext,
                params);
        Assert.assertTrue(outFile.exists());
        Assert.assertTrue(outFile.length() > 0);
    }

    public File getOutDir() {
        final File outDir = new File("temp");
        if (!outDir.isDirectory()) {
            final boolean success = outDir.mkdirs();
            Assert
                    .assertTrue(
                            "Failed to create temp directory. Please delete all files / directories named temp",
                            success);
        }
        return outDir;
    }

    /**
     * Tests if JPEG converter is available.
     *
     * @throws Exception if the test fails.
     */

    public void ttestConverterJPG() throws Exception {
        this.testConverterXXX("jpg", "jpeg");
    }
    
    
    /**
         * Tests if rendered creates a buffered image for an empty document.
        *
        * @throws Exception
         *             if the test fails.
         */
      
        public void ttestConverterBufferedImageEmptyDoc() throws Exception {
            final Document doc = new DocumentElement();
    
            final MutableLayoutContext params = new LayoutContextImpl(
                    LayoutContextImpl.getDefaultLayoutContext());
            final BufferedImage bi = Converter.getInstance().render(doc, params);
            Assert.assertNotNull(bi);
        }
    
    
    
    
    
    

}
