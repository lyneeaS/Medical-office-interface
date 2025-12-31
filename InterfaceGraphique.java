package mon_cab_avec_moncabinet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InterfaceGraphique {

    public static void launchApp() {
        JFrame frame = new JFrame("Connexion");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel userLabel = new JLabel("Nom:");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(100, 20, 165, 25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("Mot de passe:");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 50, 165, 25);
        panel.add(passwordText);

        JButton loginButton = new JButton("Connexion");
        loginButton.setBounds(10, 80, 150, 25);
        panel.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PatientService cabinet = new MonCabinet();
                cabinet.connecter(userText.getText(), new String(passwordText.getPassword()));
            }
        });
    }

    public static void showMenu(String role, PatientService cabinet) {
        JFrame menuFrame = new JFrame("Menu - " + (role.equals("D") ? "Médecin" : "Secrétaire"));
        menuFrame.setSize(400, 200);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setLocationRelativeTo(null);
        menuFrame.setLayout(new GridLayout(2, 3));

        JButton btnAjouter = new JButton("Ajouter un patient");
        JButton btnAfficher = new JButton("Afficher les patients");
        JButton btnSupprimer = new JButton("Supprimer un patient");
        JButton btnJustif = new JButton("Justification");
        JButton btnOrdonnance = new JButton("Ordonnance");

        menuFrame.add(btnAjouter);
        menuFrame.add(btnAfficher);
        menuFrame.add(btnSupprimer);
        if ("D".equals(role)) {
            menuFrame.add(btnJustif);
            menuFrame.add(btnOrdonnance);
        }

        btnAjouter.addActionListener(e -> {
            String nom = JOptionPane.showInputDialog("Nom :");
            String prenom = JOptionPane.showInputDialog("Prénom :");
            int age = Integer.parseInt(JOptionPane.showInputDialog("Âge :"));
            String adresse = JOptionPane.showInputDialog("Adresse :");
            String tel = JOptionPane.showInputDialog("Téléphone :");
            cabinet.ajouterPatient(nom, prenom, age, adresse, tel);
        });

        btnAfficher.addActionListener(e -> cabinet.afficherPatients());

        btnSupprimer.addActionListener(e -> {
            String nom = JOptionPane.showInputDialog("Nom du patient à supprimer :");
            cabinet.supprimerPatientParNom(nom);
        });

        btnJustif.addActionListener(e -> cabinet.justification());
        btnOrdonnance.addActionListener(e -> cabinet.ordonnance());

        menuFrame.setVisible(true);
    }
}