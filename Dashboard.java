import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

public class Dashboard {
    private JFrame frame;
    private JLabel lblTitle, lblTeamName, lblCaptain, lblCoach, lblImage, lblKitSponsor, lblMediaSponsor, lblStadium;
    private JTextField txtTeamName, txtCaptain, txtCoach, txtValue;
    private JButton btnSave, btnAddImage, btnAddPlayers, btnCalculateBudget;
    private JComboBox<String> cmbStadium, cmbKitSponsor;
    private JTable table;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;
    private ImageIcon teamImage;
    private ImageIcon logo = new ImageIcon("Pic_src/Bff_5 (1).png");

    public Dashboard() {
        frame = new JFrame();
        frame.setTitle("TEAM DETAILS");
        frame.setSize(900, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.decode("#AEE2FF")); // Set background color

        lblTitle = new JLabel("Enter Details:");
        lblTitle.setBounds(50, 10, 300, 30);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        frame.add(lblTitle);

        lblTeamName = new JLabel("Team Name:");
        lblTeamName.setBounds(50, 50, 100, 20);
        frame.add(lblTeamName);

        txtTeamName = new JTextField();
        txtTeamName.setBounds(160, 50, 200, 20);
        frame.add(txtTeamName);

        lblCaptain = new JLabel("Captain:");
        lblCaptain.setBounds(50, 90, 100, 20);
        frame.add(lblCaptain);

        txtCaptain = new JTextField();
        txtCaptain.setBounds(160, 90, 200, 20);
        frame.add(txtCaptain);

        lblCoach = new JLabel("Coach:");
        lblCoach.setBounds(50, 130, 100, 20);
        frame.add(lblCoach);

        txtCoach = new JTextField();
        txtCoach.setBounds(160, 130, 200, 20);
        frame.add(txtCoach);

        lblImage = new JLabel();
        lblImage.setBounds(400, 50, 150, 150);
        frame.add(lblImage);

        lblStadium = new JLabel("Stadium:");
        lblStadium.setBounds(50, 170, 100, 20);
        frame.add(lblStadium);

        cmbStadium = new JComboBox<>();
        cmbStadium.setBounds(160, 170, 200, 20);
        cmbStadium.addItem("Bangabandhu National Stadium");
        cmbStadium.addItem("Sylhet District Stadium");
        cmbStadium.addItem("Shaheed Bulbul Ahmed Hockey Stadium");
        cmbStadium.addItem("Bangabandhu International Cricket Stadium");
        frame.add(cmbStadium);

        lblKitSponsor = new JLabel("Kit Sponsor:");
        lblKitSponsor.setBounds(50, 210, 100, 20);
        frame.add(lblKitSponsor);

        cmbKitSponsor = new JComboBox<>();
        cmbKitSponsor.setBounds(160, 210, 200, 20);
        cmbKitSponsor.addItem("Nike");
        cmbKitSponsor.addItem("Adidas");
        cmbKitSponsor.addItem("Puma");
        cmbKitSponsor.addItem("Under Armour");
        frame.add(cmbKitSponsor);

        lblMediaSponsor = new JLabel("MediaSponsor:");
        lblMediaSponsor.setBounds(50, 250, 100, 20);
        frame.add(lblMediaSponsor);

        txtValue = new JTextField();
        txtValue.setBounds(160, 250, 200, 20);
        frame.add(txtValue);

        btnSave = new JButton("Save");
        btnSave.setBounds(160, 290, 80, 30);
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String teamName = txtTeamName.getText();
                String captain = txtCaptain.getText();
                String coach = txtCoach.getText();
                String stadium = cmbStadium.getSelectedItem().toString();
                String kitSponsor = cmbKitSponsor.getSelectedItem().toString();
                String mediaSponsor = txtValue.getText();

              
                String[] rowData = { teamName, captain, coach, stadium, kitSponsor, mediaSponsor };
                tableModel.addRow(rowData);

                
                txtTeamName.setText("");
                txtCaptain.setText("");
                txtCoach.setText("");
                txtValue.setText("");

                // Save data to text file
                saveDataToTextFile(teamName, captain, coach, stadium, kitSponsor, mediaSponsor);
            }
        });
        frame.add(btnSave);

        btnAddImage = new JButton("Add Image");
        btnAddImage.setBounds(250, 290, 110, 30);
        btnAddImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    String imagePath = fileChooser.getSelectedFile().getPath();
                    teamImage = new ImageIcon(imagePath);
                    lblImage.setIcon(teamImage);
                    JOptionPane.showMessageDialog(frame, "Image selected successfully!");
                }
            }
        });
        frame.add(btnAddImage);

        btnAddPlayers = new JButton("Add Players");
        btnAddPlayers.setBounds(350, 290, 110, 30);
        btnAddPlayers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String teamName = txtTeamName.getText();
                new Players(teamName);
            }
        });
        frame.add(btnAddPlayers);

        btnCalculateBudget = new JButton("Calculate Budget");
        btnCalculateBudget.setBounds(470, 290, 140, 30);
        btnCalculateBudget.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "The total budget of the team is $100 million.");
            }
        });
        frame.add(btnCalculateBudget);

        // Table setup
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Team Name");
        tableModel.addColumn("Captain");
        tableModel.addColumn("Coach");
        tableModel.addColumn("Stadium");
        tableModel.addColumn("Kit Sponsor");
        tableModel.addColumn("Media Sponsor");
        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 330, 800, 400);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        frame.add(scrollPane);
        frame.setVisible(true);
    }

    private void saveDataToTextFile(String teamName, String captain, String coach, String stadium, String kitSponsor,
            String mediaSponsor) {
        try {
            String fileName = "team_info/" + teamName + ".txt";
            FileWriter writer = new FileWriter(fileName);
            writer.write("Team Name: " + teamName + "\n");
            writer.write("Captain: " + captain + "\n");
            writer.write("Coach: " + coach + "\n");
            writer.write("Stadium: " + stadium + "\n");
            writer.write("Kit Sponsor: " + kitSponsor + "\n");
            writer.write("Media Sponsor: " + mediaSponsor + "\n");
            writer.close();
            JOptionPane.showMessageDialog(frame, "Data saved successfully!");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, "Error occurred while saving the data.");
        }
    }

    public static void main(String[] args) {
        new Dashboard();
    }
}
