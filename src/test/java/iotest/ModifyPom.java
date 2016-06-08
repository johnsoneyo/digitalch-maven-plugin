/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iotest;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import org.apache.commons.io.FileUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
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
public class ModifyPom {

    private VelocityContext context = new VelocityContext();

    @Before
    public void initModifyPomFile() {
        System.out.println(">>>>>>>>>>>>>Init ");
    }

    @Test
    public void modifyPom() {
        String tm = "/home/jihadijohn/digitalch-maven-plugin/service-pom/pom.vm";
        String overiteFile = "/home/jihadijohn/digitalch-maven-plugin/service-pom/pom.xml";
        try{
            context.put("servicename", "minipayment");
            String result = resolveTemplate(tm, context);
            FileUtils.write(new File(overiteFile), result);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static String resolveTemplate(String template, VelocityContext context) throws ParseErrorException, MethodInvocationException, ResourceNotFoundException, IOException {
        
        StringWriter writer = new StringWriter();
        Velocity.evaluate(context, writer, "", template);
        return writer.toString();
        
    }

    @After
    public void postModifyPom() {
        System.out.println("Done with pom.xml");
    }

}
