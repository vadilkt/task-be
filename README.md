Voici un README complet et bien structuré pour votre projet :

---

# Task Management API

## Description  
**Task Management API** est une application permettant de gérer des tâches via une API REST. Elle est construite avec Spring Boot et utilise une base de données PostgreSQL pour stocker les informations des tâches.

## Table des matières
- [Prérequis](#prérequis)
- [Installation](#installation)
- [Configuration](#configuration)
- [Démarrage de l'application](#démarrage-de-lapplication)
- [Utilisation de l'API](#utilisation-de-lapi)
- [Tests](#tests)
- [Technologies utilisées](#technologies-utilisées)

## Prérequis

Avant de commencer, assurez-vous que les éléments suivants sont installés sur votre machine :
- **Java 17** : [Télécharger ici](https://adoptium.net/)
- **Maven** : [Télécharger ici](https://maven.apache.org/download.cgi)
- **PostgreSQL** : [Télécharger ici](https://www.postgresql.org/download/)

## Installation

1. **Cloner le dépôt**
   ```bash
   git clone https://github.com/vadilkt/task-management.git
   cd task-management
   ```

2. **Configurer la base de données**
   - Connectez-vous à votre instance PostgreSQL et créez une base de données pour l'application :
     ```sql
     CREATE DATABASE task_management;
     ```
   - Modifiez les informations de connexion dans le fichier `src/main/resources/application.properties` pour refléter vos identifiants PostgreSQL :
     ```properties
     spring.datasource.url=jdbc:postgresql://localhost:5432/task_management
     spring.datasource.username=VOTRE_UTILISATEUR
     spring.datasource.password=VOTRE_MOT_DE_PASSE
     ```

3. **Construire le projet avec Maven**
   ```bash
   mvn clean install
   ```

## Démarrage de l'application

Pour lancer l'application, exécutez la commande suivante :

```bash
mvn spring-boot:run
```

L'application sera disponible à l'adresse [http://localhost:8080](http://localhost:8080).

## Utilisation de l'API

Vous pouvez interagir avec l'API via un outil comme [Postman](https://www.postman.com/) ou en utilisant des commandes `curl`. Voici quelques exemples d’endpoints :

- **Créer une nouvelle tâche**
  ```http
  POST /api/tasks
  ```
  - Exemple de payload JSON :
    ```json
    {
      "title": "Nouvelle tâche",
      "description": "Description de la tâche",
      "completed": false
    }
    ```

- **Obtenir toutes les tâches**
  ```http
  GET /api/tasks
  ```

- **Obtenir une tâche par ID**
  ```http
  GET /api/tasks/{id}
  ```

- **Mettre à jour une tâche**
  ```http
  PUT /api/tasks/{id}
  ```

  - **Compléter une tâche**
  ```http
  PATCH /api/tasks/{id}/complete
  ```

- **Supprimer une tâche**
  ```http
  DELETE /api/tasks/{id}
  ```

## Tests

_La section des tests sera ajoutée ultérieurement._

## Technologies utilisées

- **Spring Boot** - Framework pour le développement rapide d'applications Java
- **PostgreSQL** - Base de données relationnelle pour le stockage des tâches
- **Maven** - Outil de gestion de projet pour la gestion des dépendances et la construction du projet
- **JUnit** - Framework de tests pour les tests unitaires (à implémenter)

---

Ce README fournit toutes les informations nécessaires pour installer, configurer et lancer le projet. Remplacez `nom-utilisateur` par votre identifiant GitHub et personnalisez davantage si nécessaire.
