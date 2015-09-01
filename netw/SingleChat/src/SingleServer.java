
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

import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import network.ChatMessage;
import com.esotericsoftware.minlog.Log;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SingleServer {

    ChatFrame chatFrame;
    Server server;
    int id;

    public SingleServer() throws IOException {

        server = new Server();

        Network.register(server);

        server.addListener(new Listener() {

            public void connected(Connection connection, Object object) {
                id = connection.getID();
            }

            public void recieved(Connection connection, Object object) {
                if (object instanceof ChatMessage) {
                    ChatMessage chatMessage = (ChatMessage) object;
                    chatFrame.addMessage(chatMessage.text);
                }
            }

            @Override
            public void disconnected(Connection connection) {
                //textArea.append("Client has disconnected\n");
            }

        });

        chatFrame = new ChatFrame("server");
        // This listener is called when the send button is clicked.
        chatFrame.setSendListener(new Runnable() {
            public void run() {
                ChatMessage chatMessage = new ChatMessage();
                chatMessage.text = chatFrame.getSendText();
                server.sendToTCP(id, chatMessage);
            }
        });
        // This listener is called when the chat window is closed.
        chatFrame.setCloseListener(new Runnable() {
            public void run() {
                server.stop();
            }
        });

        // We'll do the connect on a new thread so the ChatFrame can show a progress bar.
        // Connecting to localhost is usually so fast you won't see the progress bar.
        server.bind(Network.port);
        server.start();

    }

    static private class ChatFrame extends JFrame {

        CardLayout cardLayout;
        JProgressBar progressBar;
        JList messageList;
        JTextField sendText;
        JButton sendButton;
        JList nameList;

        public ChatFrame(String host) {
            super("Server");
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
        new SingleServer();
    }
}
