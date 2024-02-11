package serverGUI;

import clientGUI.ClientWindow;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ServerService {
    public static DateTimeFormatter formatDateTime = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    public static DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("HH:mm:ss");

    private ArrayList<ClientWindow> listClients;



    private boolean isServerUp;

    public ServerService() {
        listClients = new ArrayList<>();
    }
    public void setServerUp(boolean serverUp) {
        isServerUp = serverUp;
    }
    public void addClient(ClientWindow clientWindow){
        listClients.add(clientWindow);
    }

    public ArrayList<ClientWindow> getListClients() {
        return listClients;
    }

    public void loggingChat(String text){

        try(FileWriter fileWriter = new FileWriter(".\\src\\serverGUI\\logging.txt", true)){
            fileWriter.write(text);

        }catch (IOException e){
            System.out.println(e.getMessage());
        }


    }

    public void addNewMessage(String text) {

        for (ClientWindow client: listClients
             ) {
            if (client.isCreateLogin()) {
                client.getTextArea().append(text);
            }
        }


    }

    public void sendStatusMessage(){
        String text;
        if(!isServerUp) text = "You have been disconnected from the server!";
        else text ="You have been connected to the server!";
        for (ClientWindow client: listClients
             ) {
            client.addServerMessage(text);
        }
    }

    public String readLogging() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("----------HISTORY: -----------\n");

        try (BufferedReader reader = new BufferedReader(new FileReader(".\\src\\serverGUI\\logging.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        stringBuilder.append("----------End history--------\n");
        return stringBuilder.toString();
    }
}
