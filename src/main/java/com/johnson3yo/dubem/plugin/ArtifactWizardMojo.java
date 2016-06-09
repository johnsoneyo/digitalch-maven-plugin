/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnson3yo.dubem.plugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.codehaus.plexus.components.interactivity.Prompter;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 *
 * @author johnson3yo
 * @author dubem
 * @requiresProject false
 * @goal upgrade
 */
public class ArtifactWizardMojo extends AbstractMojo {

    private final VelocityContext context = new VelocityContext();
    private VelocityEngine ve;

    /**
     * @component @required
     */
    private Prompter prompter;

    /**
     * @parameter expression="${functionName}"
     *
     */
    private String functionName;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            Properties prop = new Properties();
            prop.setProperty("resource.loader", "class");
            prop.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

            ve = new VelocityEngine(prop);

            updateServicePom(functionName, context, ve);
            updateAppServletXml(functionName);
        } catch (Exception ex) {
            Logger.getLogger(ArtifactWizardMojo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void updateServicePom(String functionName, VelocityContext context, VelocityEngine ve) throws IOException, Exception {

        String service = functionName.toLowerCase().concat("-service");

        File f = new File(service + "/pom.xml");

        if (f.exists()) {

            context.put("servicename", functionName.toLowerCase());
            String result = resolveTemplate("template/service-pom.vm", context);
            FileUtils.write(f, result);
        } else {
            throw new IOException("Function name not found");
        }

    }

    private String resolveTemplate(String template, VelocityContext context) throws ParseErrorException, MethodInvocationException, ResourceNotFoundException, IOException, Exception {

        StringWriter writer = new StringWriter();
//        Velocity.evaluate(context, writer, "", template);
        ve.mergeTemplate(template, "UTF-8", context, writer);
        return writer.toString();

    }

    private void updateAppServletXml(String functionName) throws IOException, JDOMException {

        getLog().info("please select app-servlet.xml from foundation-guiwar  ");
        JFileChooser fc = new JFileChooser();
        int retValue = fc.showOpenDialog(new JPanel());
        if (retValue == JFileChooser.APPROVE_OPTION) {
            File f = fc.getSelectedFile();

            SAXBuilder builder = new SAXBuilder();

            Document document = (Document) builder.build(f);
            Element rootNode = document.getRootElement();

            List<Element> list = rootNode.getChildren("component-scan", Namespace.getNamespace("http://www.springframework.org/schema/context"));

            Element e = list.get(0);

            String cnt = "<context:exclude-filter type=\"regex\" expression=\"pegasus\\.module\\." + functionName.toLowerCase() + "\\..*\" />";

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
            getLog().info("Next time select a file.");
            System.exit(1);
        }

    }
}
