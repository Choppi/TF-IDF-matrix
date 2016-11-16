/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.tartarus.snowball.ext.EnglishStemmer;

/**
 *
 * @author alexis
 */
public class Task01 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        if(args.length != 2)
        {
            System.out.println("Error usage : java input_file output_file");
            System.exit(-1);
        }
        
        // declaration of global variables :
        // enstemmer : the stemmer from lucene-snowball library
        // stop : the set of stopwords
        // records : the array in which the result is being printed
        
        EnglishStemmer enstemmer = new EnglishStemmer();
        Stopwords stopwords = new Stopwords();
        HashSet<String> stop = stopwords.m_Words;
        List<String> records = new ArrayList<>();
        
        // open and read the file
        try (BufferedReader reader = new BufferedReader(new FileReader(args[0]))) {
            String line;
            // read each document (line)
            while ((line = reader.readLine()) != null)
            {
                String result = new String();

                String[] words = line.replaceAll("\\p{P}", "").toLowerCase().split("\\s+");
                // for each word, check if not a stopwords
                // if not, stemming is done and the word is added to the records
                // else skip this word
                for (String word : words) {
                    if(!stop.contains(word))
                    {
                        enstemmer.setCurrent(word);
                        enstemmer.stem();
                        result += enstemmer.getCurrent() + " ";
                    }
                }
                records.add(result);
            } 
        // create and write in the file
        Path file = Paths.get(args[1]);
        Files.write(file, records);
        }
        catch (Exception e)
        {
            System.err.format("Exception occurred trying to read '%s'.", args[0]);      
        }
    }
}
    



