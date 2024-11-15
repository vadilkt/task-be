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
   git clone https://github.com/vadilkt/task-be.git
   cd task-management
   ```

2. **Configurer la base de données**
   - Connectez-vous à votre instance PostgreSQL et créez une base de données pour l'application :
     ```sql
     CREATE DATABASE task_management;
     ```
   - Modifiez les informations de connexion dans le fichier `src/.env` pour refléter vos éléments de connexion à votre base de données :
     ```.env
     PORT=
     DB_URL=
     USER_NAME=
     DB_PASSWORD=
     
     MONGO_URI=
     MONGODB_NAME=
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

## Documentation Swagger

Accédez à la documentation Swagger ici: http://localhost:8070/api/documentation

## Technologies utilisées

- **Spring Boot** - Framework pour le développement rapide d'applications Java
- **PostgreSQL** - Base de données relationnelle pour le stockage des tâches
- **Maven** - Outil de gestion de projet pour la gestion des dépendances et la construction du projet
- **JUnit** - Framework de tests pour les tests unitaires (à implémenter)

---
