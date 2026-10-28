package fr.efrei.transfret;

import java.util.List;

public class ContexteApplication {

    private final ClientRepository clients;
    private final ExpeditionRepository expeditions;
    private final EmailService emailService;
    private final SmsService smsService;
    private final ServiceClient serviceClient;
    private final ServiceExpedition serviceExpedition;
    private final GenerateurFacture generateurFacture;

    public ContexteApplication() {
        clients = new DepotClientsMemoire();
        expeditions = new DepotExpeditionsMemoire();
        emailService = new EmailService();
        smsService = new SmsService();

        Notificateur notificateur = new Notificateur(emailService, smsService);
        serviceClient = new ServiceClient(clients, notificateur);
        serviceExpedition = new ServiceExpedition(
                expeditions,
                new CalculateurPrix(),
                new AffectationVehicule(new Flotte().creerVehicules()),
                new ValidateurExpedition(),
                new EstimateurDelai(),
                notificateur
        );
        generateurFacture = new GenerateurFacture();
    }

    public ServiceClient getServiceClient() {
        return serviceClient;
    }

    public ServiceExpedition getServiceExpedition() {
        return serviceExpedition;
    }

    public GenerateurFacture getGenerateurFacture() {
        return generateurFacture;
    }

    public Client trouverClient(String email) {
        return clients.trouver(email);
    }

    public Expedition trouverExpedition(String id) {
        return expeditions.trouver(id);
    }

    public List<String[]> emailsEnvoyes() {
        return emailService.journal();
    }

    public List<String[]> smsEnvoyes() {
        return smsService.journal();
    }
}
