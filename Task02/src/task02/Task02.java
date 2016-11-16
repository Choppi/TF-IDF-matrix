/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task02;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alexis
 */
public class Task02 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        
        if(args.length != 2)
        {
            System.out.println("Error usage : main input_file output_file");
            System.exit(-1);
        }
        // Map to store the word (key) and all document id it occurs in (List<Integer>)
        Map<String,List<Integer>> wordsbydocument = new HashMap<>();
        
        // read the file
        try (BufferedReader reader = new BufferedReader(new FileReader(args[0]))) {
                String line;
                int i = 1;
                // for each line
                while ((line = reader.readLine()) != null)
                {
                   String[] words = line.split(" ");
                   // split the string (line)
                   // for each word in the line
                   // if the word is already in the list, you add the document id to the word
                   // else you create a new entry in the map with this word and reference the current document
                   for (String word : words) {
                        if(wordsbydocument.containsKey(word))
                        {
                            List<Integer> documentIDs = wordsbydocument.get(word);
                            documentIDs.add(i);
                        }
                        else 
                        {
                            List<Integer> documentIDs = new ArrayList<>();
                            documentIDs.add(i);
                            wordsbydocument.put(word,documentIDs);                   
                        }
                   }
                   i++;  
                }
                reader.close();
        }
        catch (Exception e)
        {
          System.err.format("Exception occurred trying to read '%s'.", args[0]);
        }
        
        List<String> records = new ArrayList<>();
        // for each entry in the map
        // loop over all the values for the key (word)
        // create a string that include
        // word : documents_ids
        wordsbydocument.entrySet().stream().map((entry) -> {
            List<Integer> test = entry.getValue();
            String values = new String();
            for(int i = 0;i<test.size();i++)
            {
                values += test.get(i);
                if(i!=test.size() - 1)
                    values += ",";
            }
            String s = entry.getKey() + "   " + values;
            return s;
        }).forEachOrdered((s) -> {
            records.add(s);
        });
        
        // create and write in the file
          Path file = Paths.get(args[1]);
        try {
            Files.write(file, records);
        } catch (IOException ex) {
            Logger.getLogger(Task02.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
}
    
