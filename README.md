# TF-IDF-matrix
Information Retrieval work


This is a project which consists of reading a file where a line is one document (simplified approach). 
Each Task you'll find here is defined below, and if you have any improvements to do, let me know (i'd like to learn about my mistakes)

Task01 : 
Input parameters : two text files -> First : file to read.
                                     Second : file to write out.
It returns the documents stemmed and stopworded.


Task02 : 
Input parameters : two text files -> First : file to read where documents are pre-processed.
                                     Second : file to write out.
It returns the inverted index of the words. (with doublon for the next task).

Task03 : 
Input parameters : two text files -> First : file to read (inverted index)
                                     Second : file to write out.
It returns the TF-IDF matrix of weights of the documents and words.


Task04 : 
Input parameters : one text file -> First : file to read. (TF-IDF matrix)
                   two identifiers -> the documents we want to compare
It returns the cosine of similarity of the two documents passed in parameters.
