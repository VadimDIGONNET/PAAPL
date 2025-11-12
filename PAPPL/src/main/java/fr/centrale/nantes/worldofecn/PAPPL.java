/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.centrale.nantes.worldofecn;

/**
 *
 * @author agach
 */
public class PAPPL {
    public static void main(String[] args) {
        DatabaseTools data=new DatabaseTools();   
        data.connect();
        data.insertSujet("test","centrale","gachet","antoine");
        data.insertSujet("test2","p2tinc","SAAL", "Téo");
    }
}
