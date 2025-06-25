## Questions & Answers

### ❓ Question 1: Is putting all functions in the same service recommended?

**Answer:**  
No, ce n’est pas recommandé pour les systèmes en production.  
La classe unique `Service` viole le principe de responsabilité unique (Single Responsibility Principle) en gérant à la fois les chambres, les utilisateurs, les réservations et les rapports. Cela complique énormément la maintenance à mesure que le système évolue — imaginez corriger un bug lié aux réservations et casser involontairement la gestion des chambres !

Une meilleure approche serait de séparer les responsabilités dans des classes distinctes :
- `RoomService`
- `UserService`
- `BookingService`
- `ReportService`

Cependant, dans le cadre de ce test technique, nous avons suivi les exigences données afin de démontrer la fonctionnalité principale dans les contraintes spécifiées.

---

### ❓ Question 2: Quelle alternative à `setRoom()` pour ne pas impacter les réservations passées ?

**Answer:**  
La versioning des chambres est l'alternative principale, mais une approche hybride est recommandée.

Au lieu de stocker un instantané des données de la chambre dans chaque réservation, on peut gérer des versions historiques pour chaque chambre :
- **v1**: STANDARD / 1000 MAD
- **v2**: SUITE / 5000 MAD

Chaque réservation ferait alors référence à une version spécifique. Cela permet une traçabilité complète, mais implique des requêtes complexes pour calculer les coûts.

✅ **Recommandation** :  
Utiliser une approche hybride :
- Stocker les données financières critiques directement dans la réservation (coût total, prix payé)
- Référencer aussi la version de la chambre pour assurer l'auditabilité

Cela garantit que les montants des réservations ne changent jamais tout en permettant un suivi historique fiable.

