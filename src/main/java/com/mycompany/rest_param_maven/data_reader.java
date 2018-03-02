/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *
 */
package com.mycompany.rest_param_maven;


/**
 *
 * @author paulf
 */

import java.net.URL ;
import java.net.HttpURLConnection ;
import java.net.ConnectException ;

import java.io.IOException ;
import java.io.BufferedReader;
import java.io.InputStreamReader;


import java.util.Arrays;


import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonArray ;
import javax.json.JsonArrayBuilder ;


public class data_reader {
    
    
    
    String   p_wordlist_data ;
    String   p_answer = "" ;
    
   
        
    
    public void read_data(){
        
       
        
        String    l_url_string  = "http://static.abscond.org/wordlist.txt" ;        
        String    l_url_query   = "" ;        
               
        
        String    l_user_agent  = "Mozilla/5.0" ;
        int       l_responseCode ;
        String    l_inputLine;
        
        String    l_wordlist_data = null ;
        
        
        
        try 
        {
            
            
            URL url_obj = new URL( l_url_string + l_url_query );
            
            HttpURLConnection con_obj = ( HttpURLConnection ) url_obj.openConnection() ;
            
            con_obj.setRequestMethod( "GET" );

            con_obj.setRequestProperty( "User-Agent", l_user_agent );         
            
            l_responseCode = con_obj.getResponseCode();
 
            
            
            BufferedReader inBuffer = new BufferedReader( new InputStreamReader( con_obj.getInputStream() ) );
            
            StringBuffer http_response = new StringBuffer();

            
            // add cr to the file so it can be split
            while (( l_inputLine = inBuffer.readLine()) != null) {
                    http_response.append( l_inputLine + "\n" );
            }
            inBuffer.close();
             
            
            // successful response 
            l_wordlist_data = http_response.toString() ;
            
 //           System.out.println( l_wordlist_data ) ;
            
            
            
        }
        catch ( ConnectException  e )
        {
        
            l_url_string = e.getMessage() ;
            
            System.out.println( l_url_string ) ;         
            
        }    
        catch ( IOException  e )
        {
           
            l_url_string = e.getMessage() ;
            
            System.out.println( l_url_string ) ;
            
        }
        catch ( Exception  e )  
        {
            
            l_url_string = e.getMessage() ;
            
            System.out.println( l_url_string ) ;    
            
        }
               
        
        p_wordlist_data = l_wordlist_data ;
        
        
    }    
    

  
    public void build_json( String S_query ){
            

        String      s_sorted_data ;
        String      s_sorted_query ;
                     
       
        JsonObjectBuilder  output_jsonObjectBuilder = null;
        output_jsonObjectBuilder = Json.createObjectBuilder();            
            
        
        // split the potential multiple queries 
        for ( String S_query_split : S_query.split( "," ) ) {
            
                       
            char[] c_sort_query = S_query_split.toCharArray();

            Arrays.sort(c_sort_query) ;

            s_sorted_query = new String(c_sort_query).toLowerCase().trim() ;

            System.out.println( "query " + s_sorted_query ) ;

            
            // create an array for multiple answers
            JsonArrayBuilder anagram_Array = Json.createArrayBuilder();
            
            
            // find the anagrams for the query(s) in the wordlist
            // and build array
            for (String anagram_word : p_wordlist_data.split("\\n")){


                char[] c_anagram = anagram_word.toCharArray() ;

                Arrays.sort( c_anagram ) ;

                s_sorted_data = new String( c_anagram ).toLowerCase().trim() ;
                
                
                // found anagram
                if ( s_sorted_query.equals(s_sorted_data) ) {

                    System.out.println( s_sorted_query + "=" + anagram_word ) ;

                    anagram_Array.add( anagram_word ) ;
                    

                } 

            }
            
            
            // assign the array to the answer
            output_jsonObjectBuilder.add( s_sorted_query , anagram_Array ) ;       
                       
            
        }
        

        // pass back the whole json as a string
        p_answer = output_jsonObjectBuilder.build().toString() ;


    }
    
    

    
}

