/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.centrale.nantes.pappl.controllers;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author agach
 */
public class Tools {
     
    public static int getIntFromString(String value){
        int intValue=-1;
        try{
            intValue = Integer.parseInt(value);
        }catch(NumberFormatException ex){
            Logger.getLogger(MainController.class.getName()).log(Level.WARNING,null,ex);
        }
        return intValue;
    }
}
