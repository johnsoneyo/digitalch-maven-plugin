/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnson3yo.dubem.plugin;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        } catch (Exception ex) {
            Logger.getLogger(ArtifactWizardMojo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        updateAppServletXml(functionName);
        
    }

    private void updateServicePom(String functionName, VelocityContext context, VelocityEngine ve) throws IOException, Exception {

        String service = functionName.toLowerCase().concat("-service");

        File f = new File(service + "/pom.xml");
        
        if(f.exists()){
        
        context.put("servicename", functionName.toLowerCase());
        String result = resolveTemplate("template/service-pom.vm", context);
        FileUtils.write(f, result);
        }
        else {
          throw new IOException("Function name not found");
        }
    
    }

    private String resolveTemplate(String template, VelocityContext context) throws ParseErrorException, MethodInvocationException, ResourceNotFoundException, IOException, Exception {

        StringWriter writer = new StringWriter();
//        Velocity.evaluate(context, writer, "", template);
        ve.mergeTemplate(template, "UTF-8", context, writer);
        return writer.toString();

    }

    private void updateAppServletXml(String functionName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
