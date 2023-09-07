package jx_review.java_fun_child.proj1_submodule;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GenerateJson {

    JSONObject obj = null;
    String currFile = "";
    String packagePath = ""; 
    String workingDir = null; 
    File javaSystemDir = new File("./");
    File tableFileJson = null; 
    File assetsDirPath = null; 
    File[] listOfFiles = null;
    String data, data_; 
    final Pattern regex = Pattern.compile("[a-zA-z0-9]*.(jpeg|png)"); 
    Matcher match = null;
    FileOutputStream fos;

    GenerateJson () {

        // Sytem directory 
        
        javaSystemDir = new File("./");
        
        // package path 
        packagePath = this.getClass().getPackage().getName().replace(".", "/");

        // working dir path
        workingDir = Paths.get(javaSystemDir.getAbsolutePath().toString(), packagePath).toAbsolutePath().normalize().toString(); 

        // "created" json file path
        tableFileJson = new File(workingDir + "/" + "config.json");

        assetsDirPath = new File(workingDir + "/assets");

        obj = new JSONObject();
        

        try {
            
            // create json file 
            if ( tableFileJson.exists() )  {
                if (tableFileJson.delete()) {
                    System.out.println("deleted json file");
                }
            }

            if (tableFileJson.createNewFile()) {
                System.out.println("Created json file: " + tableFileJson.getName());
            }

            // read img files 
            listOfFiles = assetsDirPath.listFiles();

            // update json object 
            // read img filenames and set key value pairs in object 
            for ( int i = 0; i < listOfFiles.length; i++) {
                
                data  = listOfFiles[i].toString();
                
                match = regex.matcher(data);

                if (match.find()) {
                    data_ = data.substring( match.start()); 
                    obj.put(data_.substring(0,  data_.indexOf('.')), data);
                }

            }

            // write to config file 
            fos = new FileOutputStream(tableFileJson.getPath().toString());
            fos.write(obj.toString().getBytes());
            fos.flush();
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

 public static void  main (String [] args) throws IOException {
    new GenerateJson();
 }

}
