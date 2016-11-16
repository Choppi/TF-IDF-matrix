/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task04;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author alexis
 */
public class Task04 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        
        if(args.length != 3)
        {
            System.out.println("Error usage : main input_file identifier_1 identifier_2");
            System.exit(-1);
        }
        
        Map<Integer,List<String>> elementsline = new HashMap<>();
        
        int i = 0;
        // read the file
        try (BufferedReader reader = new BufferedReader(new FileReader(args[0]))) {
            String line;
            // for each line
            while ((line = reader.readLine()) != null)
            {
                // as the first line is only here to display the doc_id
                // we skip this step
               if(i!=0)
               {
                    // here we retrieve all the words of the line
                    // that means the term and the tf_idf value for each document for this term
                    String[] words = line.split("\\s+");
                    List<String> elemnts = new ArrayList<>();
                    // for each word
                    // if the word is the term, we skip it
                    // else we add the value to the List
                    for (String word : words) {
                         if(!word.equals(words[0]))
                             elemnts.add(word);
                         }
                    // then we put the association (row,elements that populate this row)
                     elementsline.put(i-1, elemnts);
                     }
               i++;

            }

            reader.close();
        }
        catch (Exception e)
        {
          System.err.format("Exception occurred trying to read '%s'.", args[0]);
        }
        
        // we can then create the tf_idf_matrix in a correct java format
        int line_number = elementsline.size();
        int document_number = elementsline.get(line_number - 1).size();
        double [ ] [ ] tf_idf_matrix = new double [ line_number ] [ document_number ];
        
        // and we populate it from the map values
        // i.e 
        // the first element is the element with
        // the map key 0 and the first element of the value associated to this key
        // so the first element of the list
        for(int row = 0;row < line_number;row++)
        {
            for(int column = 0;column < document_number;column++)
            {
                tf_idf_matrix[row][column] = Double.valueOf(elementsline.get(row).get(column));
            }
        }
        // variables used for the cosine similarity formula
        double result;
        double numerator = 0.0;
        double den_1 = 0.0;
        double den_2 = 0.0;
        int column_1 = Integer.valueOf(args[1]) - 1;
        int column_2 = Integer.valueOf(args[2]) - 1;
        
        // we get the values of the documents passed in parameters 
        // and use the formula of the cosine similarity
        // cos(d1,d2) = d1.d2/||d1|| * ||d2||
        for(int row=0;row < line_number;row++)
        {
            numerator += tf_idf_matrix[row][column_1] * tf_idf_matrix[row][column_2];
            den_1 += tf_idf_matrix[row][column_1] * tf_idf_matrix[row][column_1];
            den_2 += tf_idf_matrix[row][column_2] * tf_idf_matrix[row][column_2];
        }
        
        result = numerator/(Math.sqrt(den_1) * Math.sqrt(den_2));
        
        //we print the result out
        System.out.println("The cosine similarity value of document "+args[1] + " and document "+args[2]+" is : "+result);

    }
    
}


