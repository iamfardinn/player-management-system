import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class SignUp extends JFrame {
    public static void main(String[] args) {
        new SignUp();
    }

    private JFrame f;
    private JButton b1, b2;
    private JLabel l1, l2, l3, l4, l5, l6, l7;
    private JTextField t1, t2, t3;
    private JRadioButton rb1, rb2;
    private JComboBox<String> cb1, cb2;
    private JPasswordField pw;
    private String gender;
    private ButtonGroup group;
    private int x = 10, y = 10;
    private String fileName, name, password, mobileNumber, age, ft, district;
    private String[] favTeamList = new String[] { "Abahani-Limited", "Bangladesh-Police", "Bashundhara-kings",
            "Chittagong-Abahani", "Mohamadan", "Muktijoddha-KC", "Sheikh-Jamal", "Sheikh-Kamal" };
    private String[] districtList = new String[] { "Dhaka", "Jashore", "Comilla", "chittagog", "Faridpur", "Gazipur",
            "Gopalgan", "Kishorega", "Madaripur", "Manikganj", "Munshiganj", "Narayanganj", "Narsingdi", "Rajbari",
            "hariatpur", "angail", "andarban", "Brahmanbari", "Chandpur", "Chattogram", "Cox’s Bazar", "Cumilla",
            "Feni", "Khagrachhar", "Lakshmipur", "Noakhali", "Rangamati", " Habiganj", "Moulvibazar", "Sunamganj",
            "Sylhet", "Barguna", " Barishal", "Bhola", "Jhalokati", "Patuakhali", "Pirojpur", "Bagerhat", "Chuadanga",
            "Jashore", "Jhenaidah", "Jhenaidah", "Khulna", "Kushtia", "Magura", "Meherpur", "Narail", "Satkhira",
            "Bogura", "Joypurhat", "Naogaon", "Natore", "Chapainawab", " Pabna", "Rajshahi", "Sirajganj", " Dinajpur",
            "Gaibandha", "Kurigram", "Lalmonirhat", "Nilphamari", "Panchagarh", "Rangpur", "Thakurgaon", "Jamalpur",
            "Mymensing", "Netrokona" };
    private ImageIcon logo = new ImageIcon("Pic_src/Bff_5 (1).png");

    SignUp() {
        f = new JFrame();

        // Name:
        l1 = new JLabel("Name:");
        l1.setBounds(x, y, 100, 20);

        t1 = new JTextField();
        t1.setBounds(x + 80, y, 190, 20);
        // Age:
        l2 = new JLabel("Age:");
        l2.setBounds(x, y + 30, 100, 20);

        t2 = new JTextField();
        t2.setBounds(x + 80, y + 30, 190, 20);
        // Mobile No:
        l3 = new JLabel("Mobile No:");
        l3.setBounds(x, y + 60, 100, 20);

        t3 = new JTextField();
        t3.setBounds(x + 80, y + 60, 190, 20);
        // District:
        l4 = new JLabel("District:");
        l4.setBounds(x, y + 90, 100, 20);

        cb2 = new JComboBox<>(districtList);
        cb2.setBounds(x + 80, y + 90, 190, 20);
        cb2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                district = cb2.getSelectedItem().toString();
            }
        });
        // Gender:
        l6 = new JLabel("Gender:");
        l6.setBounds(x, y + 120, 100, 20);

        rb1 = new JRadioButton("MALE");
        rb1.setBounds(x + 80, y + 120, 70, 20);
        rb1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == rb1) {
                    gender = "MALE";

                }
            }
        });

        rb2 = new JRadioButton("FEMALE");
        rb2.setBounds(x + 150, y + 120, 70, 20);
        rb2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == rb2) {
                    gender = "FEMALE";

                }
            }
        });

        group = new ButtonGroup();
        group.add(rb1);
        group.add(rb2);
        // Favourite Team:
        l5 = new JLabel("Fav Team:");
        l5.setBounds(x, y + 160, 100, 20);
        cb1 = new JComboBox<>(favTeamList);
        cb1.setBounds(x + 80, y + 160, 110, 30);
        cb1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ft = cb1.getSelectedItem().toString();
            }
        });
        // Password:
        l7 = new JLabel("Password:");
        l7.setBounds(x, y + 200, 100, 20);
        pw = new JPasswordField();
        pw.setBounds(x + 80, y + 200, 190, 20);
        // DONE Button:
        b1 = new JButton("DONE");
        b1.setBounds(x, y + 400, 90, 30);
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == b1) {
                    // User File create//
                    name = t1.getText();
                    password = new String(pw.getPassword());
                    mobileNumber = t3.getText();
                    age = t2.getText();
                    fileName = t3.getText();
                    System.out.println(fileName);

                    try {
                        FileWriter fw = new FileWriter("User_Info//" + fileName + ".txt");
                        fw.write(name + "\n" + ft + "\n" + gender + "\n" + age + "\n" + district + "\n"
                                + mobileNumber + "\n" + password);
                        fw.close();
                    } catch (IOException a) {
                        System.out.println("This file exists.");
                    }

                    f.dispose();
                    new Login();
                }
            }
        });
        // Sign in Button:
        b2 = new JButton("Sign In");
        b2.setBounds(x + 110, y + 400, 90, 30);

        // Component Add:
        f.add(b1);
        f.add(b2);

        f.add(l1);
        f.add(l2);
        f.add(l3);
        f.add(l4);
        f.add(l5);
        f.add(l6);
        f.add(l7);

        f.add(t1);
        f.add(t2);
        f.add(t3);

        f.add(cb1);
        f.add(cb2);

        f.add(rb1);
        f.add(rb2);

        f.add(pw);

        // Background image:

        // Frame settings:
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setTitle("Sign Up");
        f.setSize(400, 500);
        f.setIconImage(logo.getImage());
        f.setLocationRelativeTo(null);
    }
}
