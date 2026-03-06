# Plat API

## Lancer le projet

1. Avoir **Java 17** et **MySQL** installe et en cours d'execution
2. Verifier les identifiants MySQL dans `src/main/resources/application.properties` si besoin
3. Lancer la commande :

```bash
./mvnw spring-boot:run
```

Sur Windows :

```bash
mvnw.cmd spring-boot:run
```

Le serveur demarre sur `http://localhost:8080`.

## Tester l'API

Importer le fichier `Plat_API.postman_collection.json` dans Postman pour tester tous les endpoints. Le token JWT se sauvegarde automatiquement apres l'inscription ou la connexion.
