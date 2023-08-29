import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Login extends JFrame {
    private JFrame j;
    private JLabel l1, l2;
    private JButton b1, b2, b3, adminButton;
    private JTextField t1;
    private JPasswordField pw;
    private int x = 600, y = 300;
    private ImageIcon logo = new ImageIcon("Pic_src/Bff_3_30.png");

    Login() {
        j = new JFrame();
        JOptionPane.showMessageDialog(null, "Welcome to Football Management System");
        JOptionPane.showMessageDialog(null, "Here User Name is your Mobile Number");

        // User id:
        l1 = new JLabel("User Id:");
        l1.setBounds(x, y, 100, 20);
        t1 = new JTextField();
        t1.setBounds(x + 60, y, 190, 20);

        // Password:
        l2 = new JLabel("Password:");
        l2.setBounds(x, y + 30, 100, 20);
        pw = new JPasswordField();
        pw.setBounds(x + 60, y + 30, 190, 20);

        // Sign in button:
        b1 = new JButton("Sign In");
        b1.setBounds(x, y + 60, 120, 30);
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == b1) {
                    String foundUserName = t1.getText();
                    String foundPassword = new String(pw.getPassword());

                    // Loop through files:
                    File user_infoFolder = new File("User_Info");
                    String[] userInfoFiles = user_infoFolder.list();
                    String userInfoFileName = null;

                    for (String fileName : userInfoFiles) {
                        if (fileName.equals(foundUserName + ".txt")) {
                            userInfoFileName = fileName;
                            break;
                        }
                    }

                    // Reading:
                    if (userInfoFileName != null) {
                        try {
                            FileReader fr = new FileReader("User_Info/" + userInfoFileName);
                            BufferedReader br = new BufferedReader(fr);
                            String line;

                            while ((line = br.readLine()) != null) {
                                // Read user information here
                                // line variable contains each line of the file
                            }

                            fr.close();

                            // Navigate to Dashboard
                            j.setVisible(false);
                            new Dashboard();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid Username and password!");
                    }
                }
            }
        });

        // Sign up button:
        b2 = new JButton("Sign Up");
        b2.setBounds(x + 150, y + 60, 100, 30);
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == b2) {
                    j.setVisible(false);
                    new SignUp();
                }
            }
        });

        // Exit Button:
        b3 = new JButton("Exit");
        b3.setBounds(x, y + 95, 120, 30);
        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == b3) {
                    j.setVisible(false);
                }
            }
        });

        // Admin Sign In button:
        adminButton = new JButton("Admin Sign In");
        adminButton.setBounds(x + 150, y + 95, 120, 30);
        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == adminButton) {
                    String adminId = "Sadid";
                    String adminPassword = "4321";
    
                    String enteredId = t1.getText();
                    String enteredPassword = new String(pw.getPassword());
    
                    if (enteredId.equals(adminId) && enteredPassword.equals(adminPassword)) {
                        j.setVisible(false);
                        new PlayerListViewer();
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid admin credentials!");
                    }
                }
            }
        });
    
        // Background image:
        try {
            j.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("Pic_src/Bff_3_30.png")))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        j.pack();
    
        // Frame setting:
        j.setVisible(true);
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        j.setTitle("Sign In");
        j.setSize(1920, 1080);
        j.setLayout(null);
        j.setIconImage(logo.getImage());
        // Component Add:
        j.add(l1);
        j.add(l2);
        j.add(b1);
        j.add(b2);
        j.add(b3);
        j.add(adminButton);
        j.add(t1);
        j.add(pw);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Login::new);
    }
    }
    