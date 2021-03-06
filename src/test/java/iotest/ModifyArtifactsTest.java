/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iotest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import org.apache.commons.io.FileUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.junit.Ignore;

/**
 *
 * @author johnson3yo
 */
public class ModifyArtifactsTest {

    private final VelocityContext context = new VelocityContext();
    private VelocityEngine ve;

    @Before
    public void initModifyPomFile() throws Exception {
        System.out.println(">>>>>>>>>>>>>Init ");
        Properties prop = new Properties();
        prop.put("file.resource.loader.path", "/home/jihadijohn/digitalch-maven-plugin/service-pom");
        ve = new VelocityEngine(prop);
    }

    @Ignore
    @Test
    public void modifyServicePom() {
        String tm = "pom.vm";
        String overiteFile = "/home/jihadijohn/digitalch-maven-plugin/service-pom/pom.xml";
        try {
            context.put("servicename", "minipayment");
            System.out.println("sevice name -is >>>>>>...." + context.get("servicename"));
            String result = resolveTemplate(tm, context);
            FileUtils.write(new File(overiteFile), result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void modifyAppServletXml() throws UnsupportedEncodingException, FileNotFoundException, IOException, JDOMException {

        System.out.println("please select app-servlet.xml file ");
        JFileChooser fc = new JFileChooser();
        int retValue = fc.showOpenDialog(new JPanel());
        if (retValue == JFileChooser.APPROVE_OPTION) {
            File f = fc.getSelectedFile();

            SAXBuilder builder = new SAXBuilder();

            Document document = (Document) builder.build(f);
            Element rootNode = document.getRootElement();

            List<Element> list = rootNode.getChildren("component-scan", Namespace.getNamespace("http://www.springframework.org/schema/context"));

            Element e = list.get(0);

            String cnt = "<context:exclude-filter type=\"regex\" expression=\"pegasus\\.module\\.jfilelooder\\..*\" />";

            e.addContent(cnt);

            XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat()) {
                @Override
                public String escapeElementEntities(String str) {
                    return str;
                }
            };

            Writer writer = new OutputStreamWriter(new FileOutputStream(f), "utf-8");
            outputter.output(document, writer);
            writer.close();
        } else {
            System.out.println("Next time select a file.");
            System.exit(1);
        }

    }

    private String resolveTemplate(String template, VelocityContext context) throws ParseErrorException, MethodInvocationException, ResourceNotFoundException, IOException, Exception {

        StringWriter writer = new StringWriter();
//        Velocity.evaluate(context, writer, "", template);
        ve.mergeTemplate(template, "UTF-8", context, writer);
        return writer.toString();

    }

    @After
    public void postModifyPom() {
        System.out.println("Done with pom.xml");
    }

}
