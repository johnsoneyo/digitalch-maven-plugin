/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnson3yo;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.codehaus.plexus.components.interactivity.Prompter;
import org.codehaus.plexus.components.interactivity.PrompterException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 * @goal dca
 * @author jihadijohn
 * @requiresProject false
 * @parameter default-value="demo msg"
 */
public class DemoCreateArtifact extends AbstractMojo {

    /**
     * @parameter expression="${echo.parameter1}"
     */
    private String parameter1;

    /**
     * @component @required
     */
    private Prompter prompter;

    /**
     * @parameter default-value="monkey" expression="${fullname}"
     */
    private String fullname;

    /**
     * @parameter default-value="430" expression="${age}"
     *
     */
    private String age;

    /**
     * @parameter default-value="No nationality" expression="${country}"
     */
    private String country;

    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Demo Artifact executing...........");
        //       getLog().info("parameter 1 is "+parameter1);
        try {
            modifyArtifact();
        } catch (IOException ex) {
            Logger.getLogger(DemoCreateArtifact.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PrompterException ex) {
            Logger.getLogger(DemoCreateArtifact.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void modifyArtifact() throws IOException, PrompterException {

        fullname = prompter.prompt("Your fullname: ", fullname).trim();
        age = prompter.prompt("Your age: ", age).trim();
        country = prompter.prompt("Your country: ", country).trim();

        Element dc2 = new Element("digitalCh");
        Document doc = new Document();
        doc.setRootElement(new Element("digitalChs"));
        dc2.setAttribute("fullName", fullname);
        dc2.setAttribute("age", age);
        dc2.setAttribute("country", country);
        doc.getRootElement().addContent(dc2);

        XMLOutputter xmlOutput = new XMLOutputter();
        xmlOutput.setFormat(Format.getPrettyFormat());
        xmlOutput.output(doc, new FileWriter("digitalChannel.xml"));

    }

}
