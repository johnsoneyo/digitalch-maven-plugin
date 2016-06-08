/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.johnson3yo;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 * @goal dca
 * @author jihadijohn
 * @parameter default-value="demo msg"
 */
public class DemoCreateArtifact extends AbstractMojo {

    /**
     * @parameter expression="${echo.parameter1}"
     */
    private String parameter1;

    public void execute() throws MojoExecutionException, MojoFailureException {
        //        getLog().info("Demo Artifact executing...........");
//        getLog().info("parameter 1 is "+parameter1);
        try {
            modifyArtifact();
        } catch (IOException ex) {
            Logger.getLogger(DemoCreateArtifact.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void modifyArtifact() throws IOException {

        System.out.printf("%20s\n", "Select the options listed");
        System.out.printf("%20s\n", "1. To create an xml file");
        System.out.printf("%20s\n", "2. To create an blank file");

        Scanner input1 = new Scanner(System.in);
        Scanner input2 = new Scanner(System.in);
        Scanner input3 = new Scanner(System.in);
        Scanner input4 = new Scanner(System.in);

        int val1 = input1.nextInt();

        System.out.printf("%20s\n", "Enter your full name .. ?");
        String val2 = input2.nextLine();
        System.out.printf("%20s\n", "Enter your age .. ?");
        String val3 = input3.nextLine();
        System.out.printf("%20s\n", "Enter  country .. ?");
        String val4 = input4.nextLine();

        if (val1 == 1) {
            Element dc2 = new Element("digitalCh");
            Document doc = new Document();
            doc.setRootElement(new Element("digitalChs"));
            dc2.setAttribute("fullName", val2);
            dc2.setAttribute("age", val3);
            dc2.setAttribute("country", val4);
            doc.getRootElement().addContent(dc2);

            XMLOutputter xmlOutput = new XMLOutputter();
            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(doc, new FileWriter("digitalChannel.xml"));

        } else if (val1 == 2) {

        } else {

        }
    }

}
