package serverGUI;

import clientGUI.ClientWindow;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ServerWindow extends JFrame {

    public static final int HEIGHT = 500;
    public static final int WIDTH = 500;
    private final String TITLE = "Chat Server";

    public boolean isServerUp() {
        return isServerUp;
    }

    private boolean isServerUp;

    //widets
    private JTextPane txtField;
    private JButton btnStart;
    private JButton btnStop;

    //services
    private ServerService serverService;

    public ServerService getServerService() {
        return serverService;
    }

    public ServerWindow() {
        initWindow();
        serverService = new ServerService();
    }

    private void initWindow(){
        setSize(WIDTH, HEIGHT);
        setTitle(TITLE);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel jPanel = createMainPanel();
        add(jPanel);

        setVisible(true);
    }

    private JPanel createMainPanel() {
        JPanel jPanel =new JPanel(new BorderLayout());
        txtField = new JTextPane();
        txtField.setEditable(false);
        jPanel.add(txtField, BorderLayout.CENTER);
        JScrollPane scrollPane = new JScrollPane(txtField);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        jPanel.add(scrollPane, BorderLayout.CENTER);


        JPanel jPanelBtn = new JPanel(new GridLayout(1,2));
        btnStart = new JButton("START SESSION");
        btnStop = new JButton("STOP SESSION");

        btnStart.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                String message = String.format("Server was started "+ LocalDateTime.now().format(ServerService.formatDateTime) +"!\n");
               appendText(message);

               isServerUp = true;
               serverService.setServerUp(isServerUp);
               appendText(serverService.readLogging());
                serverService.loggingChat(message);

                for (ClientWindow client: serverService.getListClients()
                     ) {
                    client.updateSession();

                }
                serverService.sendStatusMessage();

            }
        });

        btnStop.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                String message = String.format("Server was stopped "+ LocalDateTime.now().format(ServerService.formatDateTime) +"!\n");
                appendText(message);
                serverService.loggingChat(message);
                isServerUp = false;
                serverService.setServerUp(isServerUp);

                for (ClientWindow client: serverService.getListClients()
                ) {
                    client.updateSession();

                }
                serverService.sendStatusMessage();

            }
        });
        jPanelBtn.add(btnStart);
        jPanelBtn.add(btnStop);
        jPanelBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        jPanel.add(jPanelBtn, BorderLayout.SOUTH);


        return jPanel;
    }

    public void appendText(String text) {
        try {
            txtField.setText(txtField.getText() + text);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}
