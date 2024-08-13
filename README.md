# MDD (Monde de Dév) - MVP

Bienvenue dans le projet **MDD (Monde de Dév)**, un réseau social dédié aux développeurs, conçu pour faciliter la mise en relation, encourager les liens et la collaboration entre pairs partageant des intérêts communs. Ce projet est développé par ORION, une entreprise spécialisée dans le développement logiciel.

## Table des matières

- [Technologies utilisées](#technologies-utilisées)
- [Prérequis](#prérequis)
- [Installation](#installation)
- [Configuration de la base de données](#configuration-de-la-base-de-données)
- [Lancer l'application](#lancer-lapplication)

## Technologies utilisées

- **Frontend** : [Angular](https://angular.io/)
- **Backend** : [Spring Boot](https://spring.io/projects/spring-boot) avec [Java](https://www.java.com/)
- **Base de données** : [MySQL](https://www.mysql.com/)

## Prérequis

Assurez-vous que les éléments suivants sont installés sur votre machine :

- **Java Development Kit (JDK)** 17 ou supérieur : [Télécharger ici](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- **Node.js** et **npm** (pour Angular) : [Télécharger ici](https://nodejs.org/)
- **MySQL** : [Télécharger ici](https://dev.mysql.com/downloads/)

## Installation

1. Clonez le repository du projet :
    ```bash
    git clone https://github.com/J200W/MondeDeDev.git
    cd mdd
    ```

2. Installez les dépendances du frontend :
    ```bash
    cd frontend
    npm install
    ```

3. Exécutez le script SQL du fichier `back/src/main/ressources/mdd.sql`

4. Installez les dépendances du backend :
    ```bash
    cd ../backend
    ./mvnw install
    ```

## Configuration de la base de données

Avant de lancer l'application, assurez-vous de configurer les identifiants de connexion à la base de données MySQL. 

1. Créez une base de données MySQL nommée `mdd_db`.
2. Remplissez les identifiants et mots de passe de votre serveur MySQL dans le fichier `back/src/main/ressources/application.properties` du backend. 
   
   ```
   spring.datasource.url=jdbc:mysql://localhost:3306/votre_base_de_donnees
   spring.datasource.username=<votre_nom_utilisateur>
   spring.datasource.password=<votre_mot_de_passe>
   ```

## Lancer l'application

1. Lancer le backend :
    ```
    cd backend
    ./mvnw spring-boot:run
    ```

2. Lancer le frontend :
    ```
    cd ../frontend
    ng serve
    ```

3. Connectez-vous en tant qu'adminstateur avec les identifiants suivant : 
    ```
    Nom d'utilisateur : admin
    Mot de passe : Adm!n2024
    ```

4. Ou en tant qu'utilisateur avec les identifiants suivant : 
    ```
    Nom d'utilisateur : user
    Mot de passe : Us@R2024
    ```
