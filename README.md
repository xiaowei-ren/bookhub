# BookHub
Projet réalisé dans le cadre de notre cursus de [formation CDA (concepteur et développeur d'application)](https://www.eni-ecole.fr/formations/developpement/formation-concepteur-developpeur-applications-bac3/) à l'ENI.
Notre équipe :
- [Xiaowei-ren](https://github.com/xiaowei-ren)
- [Charles](https://github.com/Ortender)

BookHub est une application web permettant de consulter, gérer et partager des livres au sein d'une bibliothèque communautaire.

Le projet est composé de deux applications :

- [un backend REST développé avec **Spring Boot**](https://github.com/xiaowei-ren/bookhub)
- [un frontend développé avec **Angular**](https://github.com/Ortender/bookhub_frontend)

## Aperçu
![Catalogue]
![Inscription]<img width="1363" height="677" alt="image" src="https://github.com/user-attachments/assets/cd9eec57-8bae-4e66-ae52-905db7771b25" />
![Connexion]<img width="1362" height="678" alt="image" src="https://github.com/user-attachments/assets/bc90e212-b150-471f-8691-2cbed48f21e4" />


## Architecture

Le projet est composé de deux dépôts :

| Repository | Description |
|------------|------------|
| bookhub | API REST Spring Boot |
| bookhub_frontend | Application Angular |

Architecture :

Frontend (Angular)
⬇
API REST (Spring Boot)
⬇
Base de données (SQL Server)

## Technologies

Backend
- Java 25
- Spring Boot
- Spring Security
- JWT
- JPA / Hibernate

Frontend
- Angular
- TypeScript
- Signals
- RxJS

## Fonctionnalités

- Inscription utilisateur
- Authentification avec JWT
- Consultation des livres
- CRUD livres
- Gestion des rôles utilisateurs
- Interface sécurisée

# BookHub API

API REST permettant la gestion des utilisateurs et des livres.

## Stack technique

- Java
- Spring Boot
- Spring Security
- JWT Authentication
- JPA / Hibernate
- Gradle

## Authentification

L'API utilise un système d'authentification basé sur JWT.

Flux :

1. L'utilisateur s'authentifie via `/api/auth/login`
2. Le serveur retourne un JWT
3. Le client envoie le JWT dans l'en-tête :

Authorization: Bearer <token>

## API

### Auth

POST /api/auth/login  
POST /api/auth/register

### Books

GET /api/books  
POST /api/books  
PUT /api/books/{id}  
DELETE /api/books/{id}
