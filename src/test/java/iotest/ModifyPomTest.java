/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iotest;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Properties;
import org.apache.commons.io.FileUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author jihadijohn
 */
public class ModifyPomTest {

    private final VelocityContext context = new VelocityContext();
    private VelocityEngine ve;

    @Before
    public void initModifyPomFile() throws Exception {
        System.out.println(">>>>>>>>>>>>>Init ");
        Properties prop = new Properties();
        prop.put("file.resource.loader.path", "/home/jihadijohn/digitalch-maven-plugin/service-pom");
        ve = new VelocityEngine(prop);
    }

    @Test
    public void modifyPom() {
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
