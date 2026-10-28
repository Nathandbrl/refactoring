# Diagnostic avant refactoring

J’ai parcouru les classes copiées dans [`transport-refactored`](../transport-refactored/src/main/java/fr/efrei/transfret). Voici les principaux rôles et problèmes que j’ai relevés avant de commencer les changements.

## Rôle des classes

| Classe | Ce qu'elle fait |
|---|---|
| `AffectationVehicule` | Cherche un véhicule disponible selon le mode et les colis. |
| `Avion`, `Bateau`, `Camion`, `CamionFrigorifique`, `Train` | Modélisent les différents véhicules et leurs règles particulières. |
| `Vehicule`, `IVehicule` | Regroupent l'état commun des véhicules et leur interface. |
| `Client`, `Colis`, `Expedition` | Contiennent les informations métier. `Expedition` génère aussi sa facture. |
| `CalculateurPrix` | Calcule le tarif, les suppléments, les remises et l'assurance. |
| `ServiceClient` | Vérifie les informations du client, l'enregistre et envoie un email. |
| `ServiceExpedition` | Crée les expéditions, calcule les prix et les délais, gère leur état et envoie les notifications. |
| `Database` | Simule l'enregistrement des clients et des expéditions avec des collections en mémoire. |
| `EmailService`, `SmsService` | Simulent l'envoi des notifications et gardent un journal. |
| `Flotte` | Initialise la liste des véhicules. |
| `Utils` | Regroupe l'arrondi, le formatage, la validation des emails et les distances. |
| `Main` | Lance un exemple d'utilisation. |

L'adaptateur de tests est [`NouveauSystemeAdapter.java`](../transport-refactored/src/test/java/fr/efrei/transfret/NouveauSystemeAdapter.java). Il traduit les objets du contrat vers ceux du nouveau module.

## Problèmes repérés

| Problème | Où ? | Principe concerné |
|---|---|---|
| `ServiceExpedition` s'occupe de beaucoup de choses : création, validation, prix, délais, notifications et cycle de vie. | `ServiceExpedition` | Responsabilité unique |
| La facture est mise en forme par l'objet `Expedition`. | `Expedition.genererFacture()` | Responsabilité unique |
| Les tarifs et remises sont choisis avec des `if` dans un seul calculateur. | `CalculateurPrix` | Ouvert/fermé |
| Les véhicules ont des méthodes qu'ils ne peuvent pas utiliser et qui lancent des exceptions. | `IVehicule` et les classes de véhicules | Ségrégation des interfaces |
| `CamionFrigorifique` refuse les colis que son parent `Camion` accepte. | `CamionFrigorifique.charger()` | Substitution de Liskov |
| L'affectation dépend de nombreux `instanceof` et de la flotte statique. | `AffectationVehicule`, `Flotte` | Ouvert/fermé, dépendances |
| Les services accèdent directement au singleton, aux méthodes statiques et à des services créés en dur. | `ServiceClient`, `ServiceExpedition`, `Database` | Inversion des dépendances |
| Les modes et statuts sont des chaînes, et le type de colis est un entier. | `Expedition`, `Client`, `Colis`, services | Lisibilité et fiabilité |
| Certains nombres du calcul sont difficiles à comprendre sans relire les règles métier. | `CalculateurPrix`, `ServiceExpedition` | Lisibilité et maintenance |
| L'exception de `Train.faireLePlein()` est avalée par un `catch` vide. | `ServiceExpedition.demarrerExpedition()` | Gestion des erreurs |

## Si on ajoute le mode `FLUVIAL`

Il faudra probablement toucher aux endroits suivants :

- `CalculateurPrix` pour le tarif de 0,40 €/km et 0,02 €/kg, avec un minimum de 300 € ;
- `ServiceExpedition` pour refuser les colis réfrigérés et l'urgence, et calculer le délai ;
- `AffectationVehicule` et `Flotte` pour ajouter la péniche `PEN-0001` (2 500 t) ;
- le départ d'expédition et les méthodes des véhicules pour gérer le transport fluvial ;
- `Utils.getDistance()` pour ajouter Paris–Le Havre (365 km) ;
- les tests de tarif, délai, refus et affectation.

À noter pour l'étape 8 : le contrat fourni ne connaît que `ROUTE`, `RAIL`, `MER` et `AIR`. Comme on ne modifie pas `transport-contrat`, `FLUVIAL` est ajouté au modèle interne et testé directement dans `transport-refactored`. Le supplément hors gabarit est limité au mode fluvial pour ne pas changer les tarifs déjà vérifiés par les tests de contrat.

## Si on ajoute le supplément « hors gabarit »

Il faut vérifier les dimensions des colis et appliquer ×1,10 si au moins une dimension dépasse 200 cm. Le supplément ne doit être appliqué qu'une fois, même si plusieurs colis dépassent la limite.

À tester : une dimension égale à 200 cm, une dimension supérieure, plusieurs colis hors gabarit et aucun colis concerné.
