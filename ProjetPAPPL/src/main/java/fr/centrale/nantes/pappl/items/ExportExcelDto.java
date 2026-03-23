/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package fr.centrale.nantes.pappl.items;

/**
 * DTO pour l'export Excel des données P2T
 * @author selli
 */
public class ExportExcelDto {

    // --- Champs P2T / ENT ---
    private int p2tNum;
    private final String p2tNum2="P2T__"; 
    private final String p2tConf="";//BD : confidentiel ou non (voir feuilles d'export word)
    private String entNom;
    private final String p2tAmphi="";
    private final String p2tFiche="P2T1_Fiche_Synthétique.pdf";
    private final String p2tConvention="P2T1_Convention.pdf";
    private final String quiSuit="";
    private String p2tSujet; //Juste le titre du sujet
    private String entAdr1;    
    private String entCp;

    // --- Correspondants (COR) ---
    private String corNom;
    private String corTel;
    private String corEmail;

    // --- Enseignants (ENS) ---
    private String ensNom;
    private String ensEmail;

    // --- Pilote et Élèves ---
    private String ele1Titre;
    private String ele1Nom;
    private String ele1Email;
    private String ele2Nom;
    private String ele2Email;
    private String ele3Nom;
    private String ele3Email;
    private String ele4Nom;
    private String ele4Email;
    private String ele5Nom;
    private String ele5Email;
    private String ele6Nom;
    private String ele6Email;
    private String ele7Nom;
    private String ele7Email;

    // --- Rencontre et Inscriptions ---
    private final String dateRencontre=""; 
    private final String adresse="";
    private final String complement="";
    private final String convention=""; 
    private final String evaluation=""; 

    // --- Dates (1 à 15) ---
    private final String date1="";
    private final String date2="";
    private final String date3="";
    private final String date4="";
    private final String date5="";
    private final String date6="";
    private final String date7="";
    private final String date8="";
    private final String date9="";
    private final String date10="";
    private final String date11="";
    private final String date12="";
    private final String date13="";
    private final String date14="";
    private final String date15="";

    private final String p2tAnProchain="";

    public ExportExcelDto(
        int p2tNum, String p2tNum2, String p2tConf, String entNom, String p2tAmphi, 
        String p2tFiche, String p2tConvention, String quiSuit, String p2tSujet, 
        String entAdr1, String entAdr2, String entCp, String entLogo, String corTitre, 
        String corNom, String corTel, String corFax, String corEmail, String cor2Email, 
        String ensNom, String ensDept, String ensTel, String ensEmail, String pilNom, 
        String ele1Titre, String ele1Nom, String ele1Email, String ele2Nom, String ele2Email, 
        String ele3Nom, String ele3Email, String ele4Nom, String ele4Email, String ele5Nom, 
        String ele5Email, String ele6Nom, String ele6Email, String ele7Nom, String ele7Email, 
        String ele7Matric, String ele8Titre, String ele8Nom, String ele8Email, String ele8Matric, 
        String ele9Titre, String ele9Nom, String ele9Email, String ele9Matric, 
        String dateRencontre, String adresse, String complement
    ) {
        this.p2tNum = p2tNum;
        this.entNom = entNom;
        this.p2tSujet = p2tSujet;
        this.entAdr1 = entAdr1;
        this.entCp = entCp;
        this.corNom = corNom;
        this.corTel = corTel;
        this.corEmail = corEmail;
        this.ensNom = ensNom;
        this.ensEmail = ensEmail;
        this.ele1Titre = ele1Titre;
        this.ele1Nom = ele1Nom;
        this.ele1Email = ele1Email;
        this.ele2Nom = ele2Nom;
        this.ele2Email = ele2Email;
        this.ele3Nom = ele3Nom;
        this.ele3Email = ele3Email;
        this.ele4Nom = ele4Nom;
        this.ele4Email = ele4Email;
        this.ele5Nom = ele5Nom;
        this.ele5Email = ele5Email;
        this.ele6Nom = ele6Nom;
        this.ele6Email = ele6Email;
        this.ele7Nom = ele7Nom;
        this.ele7Email = ele7Email;
    }

    public int getP2tNum() { return p2tNum; }
    public void setP2tNum(int p2tNum) { this.p2tNum = p2tNum; }

    public String getP2tConf() {
        return p2tConf;
    }

    public String getEntNom() {
        return entNom;
    }

    public String getP2tAmphi() {
        return p2tAmphi;
    }

    public String getP2tFiche() {
        return p2tFiche;
    }

    public String getP2tConvention() {
        return p2tConvention;
    }

    public String getQuiSuit() {
        return quiSuit;
    }

    public String getP2tSujet() {
        return p2tSujet;
    }

    public String getEntAdr1() {
        return entAdr1;
    }

    public String getEntCp() {
        return entCp;
    }

    public String getCorNom() {
        return corNom;
    }

    public String getCorTel() {
        return corTel;
    }

    public String getCorEmail() {
        return corEmail;
    }

    public String getEnsNom() {
        return ensNom;
    }

    public String getEnsEmail() {
        return ensEmail;
    }

    public String getEle1Titre() {
        return ele1Titre;
    }

    public String getEle1Nom() {
        return ele1Nom;
    }

    public String getEle1Email() {
        return ele1Email;
    }

    public String getEle2Nom() {
        return ele2Nom;
    }

    public String getEle2Email() {
        return ele2Email;
    }

    public String getEle3Nom() {
        return ele3Nom;
    }

    public String getEle3Email() {
        return ele3Email;
    }

    public String getEle4Nom() {
        return ele4Nom;
    }

    public String getEle4Email() {
        return ele4Email;
    }

    public String getEle5Nom() {
        return ele5Nom;
    }

    public String getEle5Email() {
        return ele5Email;
    }

    public String getEle6Nom() {
        return ele6Nom;
    }

    public String getEle6Email() {
        return ele6Email;
    }

    public String getEle7Nom() {
        return ele7Nom;
    }

    public String getEle7Email() {
        return ele7Email;
    }

    public String getDateRencontre() {
        return dateRencontre;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getComplement() {
        return complement;
    }

    public String getConvention() {
        return convention;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public String getDate1() {
        return date1;
    }

    public String getDate2() {
        return date2;
    }

    public String getDate3() {
        return date3;
    }

    public String getDate4() {
        return date4;
    }

    public String getDate5() {
        return date5;
    }

    public String getDate6() {
        return date6;
    }

    public String getDate7() {
        return date7;
    }

    public String getDate8() {
        return date8;
    }

    public String getDate9() {
        return date9;
    }

    public String getDate10() {
        return date10;
    }

    public String getDate11() {
        return date11;
    }

    public String getDate12() {
        return date12;
    }

    public String getDate13() {
        return date13;
    }

    public String getDate14() {
        return date14;
    }

    public String getDate15() {
        return date15;
    }

    public String getP2tAnProchain() {
        return p2tAnProchain;
    }

    
}