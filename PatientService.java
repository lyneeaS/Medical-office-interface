package mon_cab_avec_moncabinet;

public interface PatientService {
    void connecter(String nom, String mp);
    void trouve_pation(String nom, String prenom);
    void justification();
    void ordonnance();
    void ajouterPatient(String nom, String prenom, int age, String adresse, String tel);
    void afficherPatients();
    void supprimerPatientParNom(String nom);
}