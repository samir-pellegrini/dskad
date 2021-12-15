import java.io.*;
import java.net.*;
import java.util.*;

public class Serverstr{
    ServerSocket server =null;
    Socket client =null;
    String stringaRicevuta= null;
    String stringaModificata=null;
    BufferedReader inDalClient;
    DataOutputStream outVersoClient;


    public Socket attendi(){
        try{
            System.out.println("SERVER partito in esecuzione...");
            server =new ServerSocket(6789);
            client = server.accept();
            server.close();
            inDalClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
            outVersoClient= new DataOutputStream(client.getOutputStream());
            outVersoClient.writeBytes("Connessione riuscita"+'\n'); //messaggio inviato per verificare la connessione

        }
        catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println("Errore durante l'istanza del server!");
        }
        return client;
    }

    public void comunica(){
        try{
            int c=5;
            for(;;){
            outVersoClient.writeBytes("inserisci D per vedere la disponibilità o A per acquistare"+'\n');
            //rimango in attesa della riga trasmessa dal client
            stringaRicevuta= inDalClient.readLine();
            if(stringaRicevuta.equals("D")){
                outVersoClient.writeBytes("i biglietti disponibili sono: "+ c + '\n');
            }
            if(stringaRicevuta.equals("A") && c>0)
                {
                    outVersoClient.writeBytes("i biglietto acquistato" + '\n');
                    c--;
                }
            if(c==0){
            outVersoClient.writeBytes("i biglietto esauriti e chiudo tutto" + '\n');
            client.close();
        }
    }
 } catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println("Errore durante la comunicazione col client!");
            System.exit(1);
        }
    }
    public static void main( String[] args )
    {
        Serverstr servente = new Serverstr();
        servente.attendi();
        servente.comunica();
    }

}