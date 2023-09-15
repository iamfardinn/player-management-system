import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Players extends JFrame {
    private JFrame frame;
    private JLabel lblTeamName, lblPlayerName, lblPosition, lblAge, lblJerseyNumber;
    private JTextField txtTeamName, txtPlayerName, txtAge, txtJerseyNumber;
    private JComboBox<String> cmbPosition;
    private JButton btnAddPlayer, btnDeletePlayer, btnTransferPlayer;
    private JTable table;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;
    private double budget = 100.0;

    private static final String PLAYER_LIST_FOLDER = "Player_list";

    public Players(String teamName) {
        createPlayerListFolder();

        frame = new JFrame();
        frame.setTitle("Players");
        frame.setSize(900, 800);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.decode("#AEE2FF")); // Set background color

        lblTeamName = new JLabel("Team Name:");
        lblTeamName.setBounds(50, 50, 150, 30);
        frame.add(lblTeamName);

        txtTeamName = new JTextField();
        txtTeamName.setBounds(200, 50, 300, 30);
        frame.add(txtTeamName);

        lblPlayerName = new JLabel("Player Name:");
        lblPlayerName.setBounds(50, 100, 150, 30);
        frame.add(lblPlayerName);

        txtPlayerName = new JTextField();
        txtPlayerName.setBounds(200, 100, 300, 30);
        frame.add(txtPlayerName);

        lblPosition = new JLabel("Position:");
        lblPosition.setBounds(50, 150, 150, 30);
        frame.add(lblPosition);

        cmbPosition = new JComboBox<>();
        cmbPosition.setBounds(200, 150, 300, 30);
        cmbPosition.addItem("ST");
        cmbPosition.addItem("CM");
        cmbPosition.addItem("DEF");
        cmbPosition.addItem("GK");
        frame.add(cmbPosition);

        lblAge = new JLabel("Age:");
        lblAge.setBounds(50, 200, 150, 30);
        frame.add(lblAge);

        txtAge = new JTextField();
        txtAge.setBounds(200, 200, 300, 30);
        frame.add(txtAge);

        lblJerseyNumber = new JLabel("Jersey Number:");
        lblJerseyNumber.setBounds(50, 250, 150, 30);
        frame.add(lblJerseyNumber);

        txtJerseyNumber = new JTextField();
        txtJerseyNumber.setBounds(200, 250, 300, 30);
        frame.add(txtJerseyNumber);

        btnAddPlayer = new JButton("Add Player");
        btnAddPlayer.setBounds(200, 300, 150, 40);
        btnAddPlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String teamName = txtTeamName.getText();
                String playerName = txtPlayerName.getText();
                String position = cmbPosition.getSelectedItem().toString();
                String age = txtAge.getText();
                String jerseyNumber = txtJerseyNumber.getText();

                double positionCost = 0.0;

                switch (position) {
                    case "ST":
                        positionCost = 10;
                        break;
                    case "CM":
                        positionCost = 6;
                        break;
                    case "DEF":
                        positionCost = 5;
                        break;
                    case "GK":
                        positionCost = 4;
                        break;
                    default:
                        positionCost = 0;
                        break;
                }

                // Calculate the total budget
                double remainingBudget = budget - positionCost;

                if (remainingBudget < 0) {
                    JOptionPane.showMessageDialog(frame, "Insufficient budget for this position!");
                } else {
                    addPlayer(teamName, playerName, position, age, jerseyNumber);
                    txtPlayerName.setText("");
                    txtAge.setText("");
                    txtJerseyNumber.setText("");
                    budget = remainingBudget; // Update the remaining budget
                    JOptionPane.showMessageDialog(frame,
                            "Player added successfully!\nRemaining budget: $" + remainingBudget + " million");
                }
            }
        });
        frame.add(btnAddPlayer);

        btnDeletePlayer = new JButton("Delete Player");
        btnDeletePlayer.setBounds(380, 300, 150, 40);
        btnDeletePlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    String playerName = (String) table.getValueAt(selectedRow, 0);
                    deletePlayer(playerName);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a player to delete.");
                }
            }
        });
        frame.add(btnDeletePlayer);

        btnTransferPlayer = new JButton("Transfer Player");
        btnTransferPlayer.setBounds(560, 300, 150, 40);
        btnTransferPlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    String playerName = (String) table.getValueAt(selectedRow, 0);
                    transferPlayer(playerName);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a player to transfer.");
                }
            }
        });
        frame.add(btnTransferPlayer);

        // Table setup
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Player Name");
        tableModel.addColumn("Position");
        tableModel.addColumn("Age");
        tableModel.addColumn("Jersey Number");

        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 400, 800, 300);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        frame.add(scrollPane);

        frame.setVisible(true);
    }

    private void addPlayer(String teamName, String playerName, String position, String age, String jerseyNumber) {
        String[] rowData = { playerName, position, age, jerseyNumber };
        tableModel.addRow(rowData);
        savePlayerToFile(teamName, playerName, position, age, jerseyNumber);
    }

    private void deletePlayer(String playerName) {
        int rowCount = table.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            String currentPlayerName = (String) table.getValueAt(i, 0);
            if (currentPlayerName.equals(playerName)) {
                tableModel.removeRow(i);
                break;
            }
        }
    }

    private void transferPlayer(String playerName) {
        int rowCount = table.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            String currentPlayerName = (String) table.getValueAt(i, 0);
            if (currentPlayerName.equals(playerName)) {
                String newPosition = JOptionPane.showInputDialog(frame, "Enter new Team for " + playerName);
                if (newPosition != null) {
                    table.setValueAt(newPosition, i, 1);
                    savePlayerToFile(newPosition, playerName, (String) table.getValueAt(i, 1),
                            (String) table.getValueAt(i, 2), (String) table.getValueAt(i, 3));
                    JOptionPane.showMessageDialog(frame, playerName + " transferred to " + newPosition);
                }
                break;
            }
        }
    }

    private void savePlayerToFile(String teamName, String playerName, String position, String age,
            String jerseyNumber) {
        try {
            String fileName = PLAYER_LIST_FOLDER + "/" + teamName + ".txt";
            FileWriter writer = new FileWriter(fileName, true);
            writer.write("Team Name: " + teamName + "\n");
            writer.write("Player Name: " + playerName + "\n");
            writer.write("Position: " + position + "\n");
            writer.write("Age: " + age + "\n");
            writer.write("Jersey Number: " + jerseyNumber + "\n");
            writer.write("-----------------------------------\n");
            writer.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error occurred while saving player data.");
        }
    }

    private void createPlayerListFolder() {
        Path folderPath = Paths.get(PLAYER_LIST_FOLDER);
        if (!Files.exists(folderPath)) {
            try {
                Files.createDirectory(folderPath);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, "Error occurred while creating player list folder.");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Players(null);
            }
        });
    }
}
