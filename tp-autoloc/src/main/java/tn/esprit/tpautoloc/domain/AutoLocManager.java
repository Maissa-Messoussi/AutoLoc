package tn.esprit.tpautoloc.domain;

public class AutoLocManager {

    public void louerVehicule( String typeVehicule,int nombreJours,String clientEmail) {
        double prix = 0;
        if(typeVehicule.equals("LongDuree")) {
            prix = nombreJours * 120;
        }
        else if(typeVehicule.equals("Weekend")) {
            prix = nombreJours * 180;
        }
        else if(typeVehicule.equals("Standard")) {
            prix = nombreJours * 150;
        }
        System.out.println(  "Contrat créé"  );
        System.out.println(  "Prix total : " + prix );

        envoyerEmail(clientEmail);
        enregistrerPaiement();
        sauvegarderContrat();
    }

    private void enregistrerPaiement() {
    }

    private void envoyerEmail(String email){
        System.out.println(  "Email envoyé à " + email  );
    }

    private void envoyerSMS(String sms){
        System.out.println(  "SMS envoyé à " + sms  );
    }

    private void sauvegarderContrat(){
        System.out.println(  "Contrat sauvegardé"    );
    }

} 
