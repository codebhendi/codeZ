
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import network.ChatMessage;
import com.esotericsoftware.minlog.Log;
import javax.swing.JPasswordField;

public class SingleClient {

    ChatFrame chatFrame;
    Client client;
    String name;
    String password;

    public SingleClient() {
        client = new Client();
        client.start();

        //Network.register is used to register files in both clients and server
        Network.register(client);

        client.addListener(new Listener() {
            //name of the client is send to server
            public void connected(Connection connection) {
               
            }
            //new list of conneceted clients is recieved by user
            public void received(Connection connection, Object object) {
                
                //new messsage recieved by client
                if (object instanceof ChatMessage) {
                    ChatMessage chatMessage = (ChatMessage) object;
                    chatFrame.addMessage(chatMessage.text);
                    return;
                }
            }

            //window closing operation by client
            public void disconnected(Connection connection) {
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        // Closing the frame calls the close listener which will stop the client's update thread.
                        chatFrame.dispose();
                    }
                });
            }
        });
        String host = "localhost";
        chatFrame = new ChatFrame("localhost");
        // This listener is called when the send button is clicked.
        chatFrame.setSendListener(new Runnable() {
            public void run() {
                ChatMessage chatMessage = new ChatMessage();
                chatMessage.text = chatFrame.getSendText();
                client.sendTCP(chatMessage);
            }
        });
        // This listener is called when the chat window is closed.
        chatFrame.setCloseListener(new Runnable() {
            public void run() {
                client.stop();
            }
        });

        // We'll do the connect on a new thread so the ChatFrame can show a progress bar.
        // Connecting to localhost is usually so fast you won't see the progress bar.
        new Thread("Connect") {
            public void run() {
                try {
                    client.connect(5000, host, Network.port);
                    // Server communication after connection can go here, or in Listener#connected().
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "unable to reach server");
                    System.exit(1);
                }
            }
        }.start();
    }

    static private class ChatFrame extends JFrame {

        CardLayout cardLayout;
        JProgressBar progressBar;
        JList messageList;
        JTextField sendText;
        JButton sendButton;
        JList nameList;

        public ChatFrame(String host) {
            super("Chat Client");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setSize(640, 200);
            setLocationRelativeTo(null);

            Container contentPane = getContentPane();
            cardLayout = new CardLayout();
            contentPane.setLayout(cardLayout);
            {
                JPanel panel = new JPanel(new BorderLayout());
                contentPane.add(panel, "chat");
                {
                    JPanel topPanel = new JPanel(new GridLayout(1, 2));
                    panel.add(topPanel);
                    {
                        topPanel.add(new JScrollPane(messageList = new JList()));
                        messageList.setModel(new DefaultListModel());
                    }
                    {
                        topPanel.add(new JScrollPane(nameList = new JList()));
                        nameList.setModel(new DefaultListModel());
                    }
                    DefaultListSelectionModel disableSelections = new DefaultListSelectionModel() {
                        public void setSelectionInterval(int index0, int index1) {
                        }
                    };
                    messageList.setSelectionModel(disableSelections);
                    nameList.setSelectionModel(disableSelections);
                }
                {
                    JPanel bottomPanel = new JPanel(new GridBagLayout());
                    panel.add(bottomPanel, BorderLayout.SOUTH);
                    bottomPanel.add(sendText = new JTextField(), new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.CENTER,
                            GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
                    bottomPanel.add(sendButton = new JButton("Send"), new GridBagConstraints(1, 0, 1, 1, 0, 0,
                            GridBagConstraints.CENTER, 0, new Insets(0, 0, 0, 0), 0, 0));
                }
            }

            sendText.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    sendButton.doClick();
                }
            });
        }

        public void setSendListener(final Runnable listener) {
            sendButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    if (getSendText().length() == 0) {
                        return;
                    }
                    listener.run();
                    sendText.setText("");
                    sendText.requestFocus();
                }
            });
        }

        public void setCloseListener(final Runnable listener) {
            addWindowListener(new WindowAdapter() {
                public void windowClosed(WindowEvent evt) {
                    listener.run();
                }

                public void windowActivated(WindowEvent evt) {
                    sendText.requestFocus();
                }
            });
        }

        public String getSendText() {
            return sendText.getText().trim();
        }

        public void setNames(final String[] names) {
            // This listener is run on the client's update thread, which was started by client.start().
            // We must be careful to only interact with Swing components on the Swing event thread.
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    cardLayout.show(getContentPane(), "chat");
                    DefaultListModel model = (DefaultListModel) nameList.getModel();
                    model.removeAllElements();
                    for (String name : names) {
                        model.addElement(name);
                    }
                }
            });
        }

        public void addMessage(final String message) {
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    DefaultListModel model = (DefaultListModel) messageList.getModel();
                    model.addElement(message);
                    messageList.ensureIndexIsVisible(model.size() - 1);
                }
            });
        }
    }

    public static void main(String[] args) {
        Log.set(Log.LEVEL_DEBUG);
        new ChatClient();
    }
}
