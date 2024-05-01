package com.example.gestionevents.test;

import com.example.gestionevents.models.Evenement;
import com.example.gestionevents.models.Participant;
import com.example.gestionevents.models.Publicite;
import com.example.gestionevents.services.ServiceEvenement;
import com.example.gestionevents.services.ServiceParticipant;
import com.example.gestionevents.services.ServicePublicite;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Creating instances of service classes
        ServiceEvenement evenementService = new ServiceEvenement();
        ServicePublicite publiciteService = new ServicePublicite();
        ServiceParticipant participantService = new ServiceParticipant();

        try {
            // Adding an event
            Evenement newEvent = new Evenement();
            newEvent.setId(14);
            newEvent.setPublicite_id(2);
            newEvent.setImage_evenement("wajdi");
            newEvent.setTheme_evenement("zekri");
            newEvent.setType_evenement("bnj");
            // Set the date and time of the event
            LocalDateTime eventDateTimeDebut = LocalDateTime.of(2024, 2, 12, 20, 30);
            newEvent.setDate_debut(eventDateTimeDebut);
            LocalDateTime eventDateTimeFin = LocalDateTime.of(2024, 2, 12, 20, 30);
            newEvent.setDate_fin(eventDateTimeFin);
            newEvent.setNbr_participant(2);
            // Display the date and time of the event
            // System.out.println("Event Date and Time: " + newEvent.getDate_Event());
           /* evenementService.ajouter(newEvent);
            System.out.println("Event added successfully.");*/

/*newEvent.setID_Event(40);
evenementService.modifier(newEvent);
 System.out.println("Event update successfully.");*/

           /* newEvent.setID_Event(41);
            evenementService.supprimer(newEvent);
             System.out.println("Event deleted successfully.");*/
// add participant
            Participant newParticipant = new Participant();
            //newParticipant.setId(55);
            newParticipant.setEvenement_id(3);
            newParticipant.setDescription("wajdi");
            LocalDateTime participantDateTimeDebut = LocalDateTime.of(2024, 2, 12, 20, 30);
            newParticipant.setDate_participation(participantDateTimeDebut);

        /*participantService.ajouter(newParticipant);
            System.out.println("participant added successfully.");*/

     /*  newParticipant.setId(2);
            participantService.modifier(newParticipant);
 System.out.println("participant update successfully.");*/

            newParticipant.setId(2);
            participantService.supprimer(newParticipant);
             System.out.println("participant deleted successfully.");

            //add publicite
            Publicite newPublicite = new Publicite();

          newPublicite.setDescription("hhhh");
            newPublicite.setType("hh");
          newPublicite.setSponsor("hhhh");

/*publiciteService.ajouter(newPublicite);
            System.out.println("publicite added successfully.");*/

           /* newPublicite.setId(2);
            publiciteService.modifier(newPublicite);
            System.out.println("publicite updated successfully.");*/

          /*  newPublicite.setId(2);
            publiciteService.supprimer(newPublicite);
            System.out.println("publicite deleted successfully.");*/
            // Displaying all events
         /*  System.out.println("Events:");
            List<Evenement> events = evenementService.afficher();
            for (Evenement event : events) {
                System.out.println("ID: " + event.getId());
                System.out.println("id publicite: " + event.getPublicite_id());
                System.out.println("image: " + event.getImage_evenement());
                System.out.println("theme: " + event.getTheme_evenement());
                System.out.println("type: " + event.getType_evenement());
                System.out.println("date debut: " + event.getDate_debut());
                System.out.println("date fin: " + event.getDate_fin());
                System.out.println("nbr participant: " + event.getNbr_participant());


                System.out.println();
            }*/
            // displaying participants
            /*System.out.println("Participants");
            List<Participant> participants = participantService.afficher();
            for(Participant participant : participants){
                System.out.println("ID: " + participant.getId());
                System.out.println("Evenement id" + participant.getEvenement_id());
                System.out.println("description" +participant.getDescription());
                System.out.println("date de participation" +participant.getDate_participation());
                System.out.println();
            }*/
//displaying publicite
            System.out.println("publicites");
            List<Publicite>publicites = publiciteService.afficher();
            for(Publicite publicite : publicites){
                System.out.println("ID:" + publicite.getId());
                System.out.println("Description:" + publicite.getDescription());
                System.out.println("type:" + publicite.getType());
                System.out.println("sponsor:" + publicite.getSponsor());
                System.out.println();

            }

        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }


    }
}
