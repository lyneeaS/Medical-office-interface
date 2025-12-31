package mon_cab_avec_moncabinet;

import java.sql.*;
import javax.swing.JOptionPane;

public class MonCabinet implements PatientService {
    private String role = "";

    @Override
    public void connecter(String nom, String mp) {
        try (Connection con = Connexion.getConnexion();
             PreparedStatement ps = con.prepareStatement("SELECT role FROM utilisateur WHERE nom_utilisateur = ? AND mot_de_passe = ?")) {
            ps.setString(1, nom);
            ps.setString(2, mp);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                role = rs.getString("role");
                JOptionPane.showMessageDialog(null, "Connexion réussie en tant que " + role);
                InterfaceGraphique.showMenu(role, this);
            } else {
                JOptionPane.showMessageDialog(null, "Nom d'utilisateur ou mot de passe incorrect.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void trouve_pation(String nom, String prenom) {
        try (Connection con = Connexion.getConnexion();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM patient WHERE nom = ? AND prenom = ?")) {
            ps.setString(1, nom);
            ps.setString(2, prenom);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Patient trouvé : " + rs.getString("nom") + " " + rs.getString("prenom"));
            } else {
                JOptionPane.showMessageDialog(null, "Aucun patient trouvé.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void justification() {
        if ("D".equals(role)) {
            JOptionPane.showMessageDialog(null, "Justification fournie.");
        } else {
            JOptionPane.showMessageDialog(null, "Accès refusé.");
        }
    }

    @Override
    public void ordonnance() {
        if ("D".equals(role)) {
            JOptionPane.showMessageDialog(null, "Ordonnance générée.");
        } else {
            JOptionPane.showMessageDialog(null, "Accès refusé.");
        }
    }

    @Override
    public void ajouterPatient(String nom, String prenom, int age, String adresse, String tel) {
        try (Connection con = Connexion.getConnexion();
             PreparedStatement ps = con.prepareStatement("INSERT INTO patient (nom, prenom, age, adresse, tel) VALUES (?, ?, ?, ?, ?)")) {
            ps.setString(1, nom);
            ps.setString(2, prenom);
            ps.setInt(3, age);
            ps.setString(4, adresse);
            ps.setString(5, tel);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Patient ajouté.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afficherPatients() {
        try (Connection con = Connexion.getConnexion();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM patient")) {
            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                sb.append(rs.getString("nom")).append(" ").append(rs.getString("prenom")).append("\n");
            }
            JOptionPane.showMessageDialog(null, sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void supprimerPatientParNom(String nom) {
        try (Connection con = Connexion.getConnexion();
             PreparedStatement ps = con.prepareStatement("DELETE FROM patient WHERE nom = ?")) {
            ps.setString(1, nom);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Patient supprimé.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}