import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PlayerListViewer extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private Map<String, DefaultTableModel> teamModels;

    public PlayerListViewer() {
        setTitle("Player List Viewer");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        tableModel = new DefaultTableModel() {
            @Override
            public Class<?> getColumnClass(int column) {
                return getValueAt(0, column).getClass();
            }
        };

        tableModel.addColumn("Team Names");

        table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 18));
        table.setRowHeight(30);

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(renderer);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);

        teamModels = new HashMap<>();

        loadTeamList();
        setupTableClickListener();

        // Add a button to delete selected team
        JButton deleteButton = new JButton("Delete Team");
        deleteButton.addActionListener(e -> deleteSelectedTeam());
        add(deleteButton, BorderLayout.SOUTH);

        setVisible(true);
        setLocationRelativeTo(null);
    }

    private void loadTeamList() {
        File playerListFolder = new File("Player_list");
        File[] playerListFiles = playerListFolder.listFiles();
        if (playerListFiles != null) {
            for (File file : playerListFiles) {
                if (file.getName().endsWith(".txt")) {
                    String teamName = file.getName().replace(".txt", "");
                    tableModel.addRow(new Object[]{teamName});
                    DefaultTableModel teamTableModel = createTeamTableModel();
                    teamModels.put(teamName, teamTableModel);
                }
            }
        }
    }

    private DefaultTableModel createTeamTableModel() {
        DefaultTableModel teamTableModel = new DefaultTableModel();
        teamTableModel.addColumn("Player Name");
        teamTableModel.addColumn("Position");
        teamTableModel.addColumn("Age");
        teamTableModel.addColumn("Jersey Number");
        return teamTableModel;
    }

    private void loadPlayerList(String teamName) {
        DefaultTableModel teamTableModel = teamModels.get(teamName);
        if (teamTableModel != null) {
            teamTableModel.setRowCount(0);

            File playerListFolder = new File("Player_list");
            File[] playerListFiles = playerListFolder.listFiles();
            if (playerListFiles != null) {
                for (File file : playerListFiles) {
                    if (file.getName().equals(teamName + ".txt")) {
                        String[] playerInfo = getPlayerInfoFromFile(file);
                        String[] rowData = {playerInfo[0], playerInfo[1], playerInfo[2], playerInfo[3]};
                        teamTableModel.addRow(rowData);
                    }
                }
            }
        }
    }

    private String[] getPlayerInfoFromFile(File file) {
        String[] playerInfo = new String[4];
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("Player Name: ")) {
                    playerInfo[0] = line.substring("Player Name: ".length());
                } else if (line.startsWith("Jersey Number: ")) {
                    playerInfo[3] = line.substring("Jersey Number: ".length());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return playerInfo;
    }

    private void setupTableClickListener() {
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    String teamName = table.getValueAt(selectedRow, 0).toString();
                    loadPlayerList(teamName);
                    showPlayerListDialog(teamName);
                }
            }
        });
    }

    private void showPlayerListDialog(String teamName) {
        DefaultTableModel teamTableModel = teamModels.get(teamName);
        if (teamTableModel != null) {
            JDialog dialog = new JDialog(this, "Player List: " + teamName, true);
            dialog.setSize(600, 400);
            dialog.setResizable(false);
            dialog.setLocationRelativeTo(this);

            JTable teamTable = new JTable(teamTableModel);
            teamTable.setFont(new Font("Arial", Font.PLAIN, 18));
            teamTable.setRowHeight(30);

            JScrollPane scrollPane = new JScrollPane(teamTable);
            scrollPane.setBorder(BorderFactory.createEmptyBorder());
            dialog.add(scrollPane, BorderLayout.CENTER);

            dialog.setVisible(true);
        }
    }

    private void deleteSelectedTeam() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String teamName = table.getValueAt(selectedRow, 0).toString();
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete the team '" + teamName + "'?",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                tableModel.removeRow(selectedRow);
                teamModels.remove(teamName);
                File playerListFolder = new File("Player_list");
                File teamFile = new File(playerListFolder, teamName + ".txt");
                if (teamFile.exists()) {
                    boolean deleted = teamFile.delete();
                    if (!deleted) {
                        JOptionPane.showMessageDialog(
                                this,
                                "Failed to delete the team file.",
                                "Deletion Failed",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PlayerListViewer::new);
    }
}
