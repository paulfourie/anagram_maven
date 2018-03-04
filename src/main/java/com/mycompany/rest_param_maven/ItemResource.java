/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rest_param_maven;

import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;


/**
 * REST Web Service
 *
 * @author paulf
 */
public class ItemResource {

    private String anagram;

    /**
     * Creates a new instance of ItemResource
     */
    private ItemResource(String anagram) {
        this.anagram = anagram;
    }

    /**
     * Get instance of the ItemResource
     */
    public static ItemResource getInstance(String anagram) {
        // The user may use some kind of persistence mechanism
        // to store and restore instances of ItemResource class.
        return new ItemResource(anagram);
    }

    /**
     * Retrieves representation of an instance of com.mycompany.rest_param_maven.ItemResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson() {

        data_reader l_reader = new data_reader() ;    
        l_reader.read_data();   
        l_reader.build_json( this.anagram );
//        System.out.println( l_reader.p_answer ) ;
        return l_reader.p_answer  ;
         
    }

    /**
     * PUT method for updating or creating an instance of ItemResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }

    /**
     * DELETE method for resource ItemResource
     */
    @DELETE
    public void delete() {
    }
}
