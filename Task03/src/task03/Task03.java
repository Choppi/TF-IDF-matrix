/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task03;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 *
 * @author alexis
 */
public class Task03 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
         if(args.length != 2)
        {
            System.out.println("Error usage : main input_file output_file");
            System.exit(0);
        }
        
        // global variables
        // frequency_usage : the number of document each word occurs in
        // IDF : the idf table of the words
        
        List<Integer> frequency_usage = new ArrayList<>();
        List<Double> IDF = new ArrayList<>();
        int document_number;
        int line_number = 0;
        List<Integer> documents_id = new ArrayList<>();
        List<String> terms = new ArrayList<>();
        
        // read the file
        try (BufferedReader reader = new BufferedReader(new FileReader(args[0]))) {
                String line;
                // for each line
                while ((line = reader.readLine()) != null)
                {
                   line_number++;
                   // as in Task02 we create the string this way
                   // word : x,y,z 
                   // here we split the line to only get the word and add it to the list of words
                   String term = line.substring(0,line.lastIndexOf(' ')-1);                   
                   terms.add(term);
                   
                   // here we take the other part of the ":" symbol
                   // the list of documents id
                   String word = line.substring(line.lastIndexOf(' ') + 1);
                   String[] words = word.replaceAll(" ","").split(",");
                   int length = words.length;
                   // as we only provide the inverted index
                   // i decided to use the doublon value to know the number of
                   // documents the word occurs in
                   // but keep an eye of the occurences 
                   // i.e :
                   // inform : 1,1,2 -> 2 documents but 2 occurences of 
                   // inform in the document 1
                   List<Integer> doublonids = new ArrayList<>();
                   for(String id : words)
                   {
                       int docid = Integer.valueOf(id);
                       if(!doublonids.contains(docid))
                        doublonids.add(docid);
                       else
                        length--;
                           
                       if(!documents_id.contains(docid))
                           documents_id.add(docid);
                   }
                   frequency_usage.add(length); 
                }
                reader.close();
        }
        catch (Exception e)
        {
          System.err.format("Exception occurred trying to read '%s'.", args[0]);
        }
        // after reading the file
        // we know how many terms and documents there is
        // we can now create the occurences, tf and tf_idf matrix
        document_number = documents_id.size();
        int [ ] [ ] occurences = new int [ line_number ] [ document_number ];
        double [ ] [ ] tf_matrix = new double [ line_number ] [ document_number ];
        double [ ] [ ] tf_idf_matrix = new double [ line_number ] [ document_number ];
        
        // read the document again
        try (BufferedReader reader = new BufferedReader(new FileReader(args[0]))) {
                String line;
                int i = 0;
                  
                while ((line = reader.readLine()) != null)
                {

                    String word = line.substring(line.lastIndexOf(' ') + 1);
                    String[] words = word.replaceAll(" ", "").split(",");
                    // for each line (corresponding to a association of
                    // a term and the document it occurs in
                    // for each document id
                    // fill the occurences table depending on the id value
                    // i.e 
                    // if there is 5 documents and we have in the first line
                    // inform : 4,5,5
                    // then the first line and the fourth column of the occurences table will increase
                    // by 1 and the fifth column by 2
                    for(String id : words)
                    {
                        occurences[i][Integer.valueOf(id)-1]++;
                    }


                    i++;
                }
                    
                reader.close();
        }
        catch (Exception e)
        {
          System.err.format("Exception occurred trying to read '%s'.", args[0]);
        }
        
        List<Integer> max_frequency = new ArrayList<>();
        // fill the max_frequency List for each document
        for(int column = 0;column<document_number;column++)
            {
                int max = 0;
                for(int row = 0;row < line_number;row++)
                {
                    if(occurences[row][column] > max)
                        max = occurences[row][column];
                }
                max_frequency.add(max);
            }
         
        
        
        Iterator<Integer> it = frequency_usage.iterator();
        while(it.hasNext())
        {
            // Populate the Double arrays corresponding to the IDF table
            Integer current_element = it.next();
            double value = Math.log10(document_number/current_element.doubleValue());
            IDF.add(value);
        }

        // fill the tf_matrix using the values of occurences and max_frequencies
        for(int column = 0;column < document_number;column++)
        {
            for(int row = 0;row < line_number;row++)
            {
                tf_matrix[row][column] = (occurences[row][column]/max_frequency.get(column).doubleValue());
            }
        }
        
        // fill the tf_idf_matrix using the IDF and the tf_matrix formula
        for(int row = 0;row < line_number;row++)
        {
            for(int column = 0;column < document_number;column++)
            {
                tf_idf_matrix[row][column] = IDF.get(row)*tf_matrix[row][column];
            }
        }
  /*
        System.out.println("TF MATRIX");
        for(int row = 0;row < line_number;row++)
        {
            for(int column = 0;column < document_number;column++)
            {
                    System.out.print(tf_matrix[row][column]+ " ");
            }
            System.out.println("");
        }

        System.out.println("TF-IDF MATRIX");
        for(int row = 0;row < line_number;row++)
        {
            for(int column = 0;column < document_number;column++)
            {
                    System.out.print(tf_idf_matrix[row][column]+ " ");
            }
            System.out.println("");
        }
*/

        // write in the file to display the matrix 
        try {
        BufferedWriter bw = new BufferedWriter(new FileWriter(args[1]));

        for (int i = 0; i < line_number+1; i++) {
            for (int j = 0; j < document_number; j++) {
                if(i == 0)
                {
                    if(j == 0)
                        bw.write("      ");
                    int id = j+1;
                    bw.write(id+ "  ");
                }
                else
                {
                    if(j == 0)
                    {
                        bw.write(terms.get(i-1)+ "  ");
                    }
                    bw.write(String.valueOf(tf_idf_matrix[i-1][j]));
                    if(j < document_number - 1)
                        bw.write("  ");
                }
            }
            bw.newLine();
        }
        bw.flush();
    
    } catch (IOException e) {}
    
        
    }
    
    
    
}


 
    


