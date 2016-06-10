/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iotest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author johnson3yo
 */
public class WriteToFileTest {

    @Before
    public void initIO() {
        System.out.println(">>>>>>>>>>>>>>>>> Test init running");
    }

    @Ignore
    @Test
    public void writeToFile() {
        System.out.println("Writing to file");
        try {
            File newFIle = new File("geekin.xml");
            FileUtils.writeStringToFile(newFIle, "Dubem is a geek ");
            FileInputStream fi = new FileInputStream(newFIle);
            File fo = new File("geekout.xml");
            FileUtils.copyInputStreamToFile(fi, fo);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(WriteToFileTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException io) {
            io.printStackTrace();
        }

    }

    @Test
    @Ignore
    public void printWorkingDIR() throws IOException {

        String[] cmd = {"/bin/bash", "-c", "cd /media/sf_FBDC_PROD; ls -l"};
        Process p = Runtime.getRuntime().exec(cmd);

        String output = IOUtils.toString(p.getInputStream());

        System.out.println(">>>>>>>>>>>" + output);
    }

}
