# PGROUP — Application de Gestion des Projets P2T
Base de donnée et application web pour la prospection de P2T


Application web Java/Spring MVC pour la gestion des ** (P2T)** de l'École Centrale de Nantes.  
Elle permet de gérer les sujets, les élèves, les entreprises, les tuteurs et les correspondants, avec des fonctionnalités d'import/export.

---

## ⚠️ Prérequis indispensable — Connexion VPN

> **La base de données est hébergée sur les serveurs de Centrale Nantes.**  
> **Vous devez être connecté au VPN de l'école avant de lancer l'application.**

Sans VPN, l'application démarrera mais **toutes les requêtes en base échoueront**.

- Hôte BDD : `appli-pfe.ec-nantes.fr`
- Port : `5432`
- Base : `p2t`

Connectez-vous au VPN de Centrale Nantes, puis lancez le serveur.

---

## 🛠️ Stack technique

| Composant | Technologie |
|---|---|
| Langage | Java (Jakarta EE) |
| Framework Web | Spring MVC 4.x |
| Persistance | JPA 3.0 avec EclipseLink |
| Base de données | PostgreSQL (serveur Centrale Nantes) |
| Vues | JSP + jQuery 3.7.1 |
| Manipulation Word | Apache POI (XWPF) |
| Manipulation Excel | Apache POI (XSSF) |
| Serveur d'application | Apache Tomcat 10+ |

---

## 📋 Prérequis logiciels

- **JDK 17** ou supérieur
- **Apache Tomcat 10+** (compatible Jakarta EE 10 / Servlet 6.0)
- **NetBeans** (recommandé) ou tout autre IDE Java EE
- **Client VPN** de Centrale Nantes

---

## 🗂️ Structure du projet
```
pappl/
├── src/
│   └── fr/centrale/nantes/pappl/
│       ├── controllers/        ← Contrôleurs Spring MVC
│       │   ├── MainController.java          (gestion des sujets)
│       │   ├── EleveController.java         (gestion des élèves)
│       │   ├── EntrepriseController.java    (gestion des entreprises)
│       │   ├── TuteurController.java        (gestion des tuteurs)
│       │   ├── CorrespondantController.java (gestion des correspondants)
│       │   ├── ExportController.java        (export Excel)
│       │   ├── ExportWordController.java    (export Word)
│       │   └── ImportController.java        (import Word)
│       ├── items/              ← Entités JPA (modèle de données)
│       │   ├── Sujet.java / DescriptionSujet.java
│       │   ├── Eleve.java
│       │   ├── Entreprise.java
│       │   ├── Tuteur.java
│       │   ├── Correspondant.java
│       │   ├── Annee.java
│       │   ├── Domaine.java
│       │   └── TypologieMission.java
│       └── repositories/       ← Accès base de données
│           ├── *Repository.java            (interfaces Spring Data)
│           ├── *RepositoryCustom.java      (interface méthodes custom)
│           └── *RepositoryCustomImpl.java  (implémentation JPQL)
│
└── web/WEB-INF/
    ├── jsp/                    ← Vues JSP
    │   ├── main.jsp
    │   ├── liste_sujet.jsp / liste_eleve.jsp / liste_entreprise.jsp / liste_tuteurs.jsp
    │   ├── ajout-sujet.jsp / ajout_eleve.jsp / ajout-entreprise.jsp / ajout-tuteur.jsp
    │   └── navbar.jspf
    ├── applicationContext.xml  ← Config Spring (JPA, repositories)
    ├── dispatcher-servlet.xml  ← Config Spring MVC (résolution des vues)
    ├── web.xml                 ← Config déploiement
    └── META-INF/
        └── persistence.xml     ← Config JPA / connexion BDD
```

---

## ⚙️ Configuration

### Connexion à la base de données — `META-INF/persistence.xml`
```xml
<property name="jakarta.persistence.jdbc.url"      value="jdbc:postgresql://appli-pfe.ec-nantes.fr:5432/p2t"/>
<property name="jakarta.persistence.jdbc.user"     value="p2t"/>
<property name="jakarta.persistence.jdbc.password" value="hzuwy"/>
<property name="jakarta.persistence.jdbc.driver"   value="org.postgresql.Driver"/>
```

> ⚠️ Ne pas modifier ces valeurs. La connexion requiert le **VPN actif**.

### Résolution des vues — `dispatcher-servlet.xml`

Les JSP sont résolues depuis `/WEB-INF/jsp/` avec le suffixe `.jsp`.

### Routing HTTP — `web.xml`

Toutes les URLs en `*.do` sont routées vers le `DispatcherServlet` Spring.  
Taille maximale d'upload : **5 Mo**. Timeout de session : **30 minutes**.

---

## 🚀 Lancement

### Étape 1 — Connexion VPN
Connectez-vous au VPN de Centrale Nantes **avant toute autre étape**.

### Étape 2 — Build et déploiement

**Avec NetBeans :**
1. Ouvrir le projet
2. Clic droit → **Clean and Build**
3. Clic droit → **Run**

**Avec Maven :**
```bash
mvn clean package
```
Puis déposer le `.war` dans `webapps/` de Tomcat.

### Étape 3 — Accès
```
http://localhost:8080/pappl/
```
Vous serez redirigé automatiquement vers `main.do`.

---

## 🧭 Fonctionnement général

L'application suit le pattern **Model-View-Controller** de Spring :
```
Navigateur → *.do → DispatcherServlet → Controller → Repository → BDD PostgreSQL
                                              ↓
                                         Vue JSP ← ModelAndView
```

1. L'utilisateur soumet un formulaire (URL en `.do`)
2. Spring route vers le bon `@Controller` via `@RequestMapping`
3. Le contrôleur appelle les repositories pour lire/écrire en base
4. Il retourne un `ModelAndView` avec les données
5. Le résolveur charge la JSP correspondante

> Un **filtre par année** est stocké en session (`anneeCourante`) et s'applique à tous les listages de sujets.

---

## 📌 Fonctionnalités

### Sujets (P2T) — `MainController`

| Action | URL | Méthode |
|---|---|---|
| Liste des sujets | `sujet.do` | POST |
| Nouveau sujet | `createsujet.do` | POST |
| Sauvegarder | `savesujet.do` | POST |
| Éditer | `editsujet.do` | POST |
| Supprimer | `deletesujet.do` | POST |
| Filtrer par année | `setAnnee.do` | POST |
| Supprimer le filtre | `clearAnnee.do` | POST |

### Élèves — `EleveController`
- CRUD complet, association à un sujet et une année
- Import CSV d'une liste d'élèves (`importEleve.do`)
- Gestion des choix de domaine et typologies de mission

### Entreprises — `EntrepriseController`
- CRUD complet (nom, adresse, CP, description)

### Tuteurs — `TuteurController`
- CRUD complet, lien avec les sujets

### Correspondants — `CorrespondantController`
- CRUD complet (nom, prénom, téléphone, email, entreprise)

---

## 📥 Import de sujets via fichier Word

**URL :** `ImportProjetServlet.do` (POST multipart)

Le fichier `.docx` doit contenir des **contrôles de contenu (SDT)** avec ces balises :

| Balise | Donnée |
|---|---|
| `entreprise` | Nom de l'entreprise |
| `titre` | Intitulé du sujet |
| `contexte` | Contexte du projet |
| `travail` | Travail demandé |
| `livrable` | Livrables attendus |
| `confidentiel` | Confidentialité |
| `nom` | Nom du correspondant |
| `prenom` | Prénom du correspondant |
| `telephone` | Téléphone du correspondant |
| `email` | Email du correspondant |
| `annee` | Année du projet |

> Si l'entreprise n'existe pas en base, elle est **créée automatiquement**.  
> Utiliser `modele_export.docx` comme modèle de référence.

---

## 📤 Export des données

### Export Excel — `downloadExcel.do` (GET)
Génère un fichier `.xlsx` avec toutes les données P2T consolidées (sujets, entreprises, correspondants, élèves) dans un format compatible Hippocampus.

### Export Word — `downloadWord.do`
Génère des fiches de sujets au format `.docx` en utilisant le modèle `modele_export.docx`.

---

## 🗄️ Modèle de données
```
Annee ──────────────────────── Sujet ──────── DescriptionSujet ── Entreprise
                                  │
                             Correspondant ── Entreprise

Eleve ── Sujet (via Appartient)
  ├── ChoixEleveDomaine ── Domaine
  └── ChoixEleveTypo ───── TypologieMission

Tuteur ── Sujet (via Contient)
```

---

## ❓ Problèmes fréquents

| Symptôme | Cause | Solution |
|---|---|---|
| `Connection refused` | Pas connecté au VPN | Se connecter au VPN Centrale Nantes |
| Erreur au démarrage | Mauvaise version Tomcat | Utiliser Tomcat 10+ (Jakarta EE) |
| Page blanche sur `/` | `redirect.jsp` manquant | Accéder directement à `main.do` |
| Erreur 404 sur `*.do` | Servlet non déployé | Vérifier le déploiement Tomcat |
| Import Word échoue | Mauvais format | Utiliser le modèle `modele_export.docx` |
| Upload rejeté | Fichier > 5 Mo | Limite définie dans `web.xml` |

---



---

*Application développée dans le cadre des Projets P2T de l'École Centrale de Nantes.*

