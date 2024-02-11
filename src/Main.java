import serverGUI.ServerWindow;
import clientGUI.ClientWindow;

public class Main {
    public static void main(String[] args) {
        ServerWindow server = new ServerWindow();
        ClientWindow client1 = new ClientWindow(server);
        ClientWindow client2 = new ClientWindow(server);
        ClientWindow client3 = new ClientWindow(server);
        server.getServerService().addClient(client1);
        server.getServerService().addClient(client2);
        server.getServerService().addClient(client3);
    }
}