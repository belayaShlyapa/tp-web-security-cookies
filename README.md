# TP - Cookies

## A propos
Dans ce TP, nous allons tenter de comprendre le fonctionnement des cookies lors d'une requête *HTTP*, à l'aide de *Spring Boot* afin d'avoir un *service web REST* rapidement fonctionnel.

**Le but sera de gérer les droits d'un utilisateur à accéder à certaines ressources selon l'identifiant de son cookie.**

## Introduction
Vous trouverez dans ce répertoire un début de projet *Maven* avec plusieurs classes :

- L'entité `User` qui possède un **identifiant**,  un **nom**, et un **rôle**.
- Un `Role` peut représenter un **administrateur**, un **étudiant**, ou un **invité**.
- L'interface `UserRepository` qui bénéficie des méthodes de la classe `JpaRepository` afin d'interroger la base de données locale.
- Le *nœud* `initDatabase` qui se trouve dans la classe `LoadUserDatabase` permettant l'initialisation de la base de données.
- `User Controller` quant à cette classe permet de traiter les requêtes du client. 

> C'est principalement dans cette dernière classe que nous allons implémenter la logique de notre application.

Pour rendre plus simple la relation entre le cookie utilisé et l'utilisateur, nous partirons du principe que **l'identifiant du cookie est le même que l'identifiant de l'utilisateur**.
> Par exemple, si l'identifiant du cookie est 1, l'identifiant de l'utilisateur correspondant est également 1.

Avant de passer à la suite, **vérifiez que le code compile et s'exécute correctement** sur votre machine. l'URL *http://localhost:8080/temp* devrait vous retourner la liste des auteurs de ce *splendide TP* :

![response /temp](https://zupimages.net/up/20/24/shnj.jpg)

## La théorie à la pratique
### Partie 1 - (8 points)
Dans un premier temps, nous souhaitons que seul les **administrateurs puissent récupérer la liste complète des utilisateurs** via l'URL *http://localhost:8080/get*.

1- Créez cette méthode. **(3 points)**
Voici un exemple de comment récupérer le cookie envoyé par l'utilisateur  :
```java
@RequestMapping(path = "get", method = RequestMethod.GET)  
public String method(HttpServletRequest request) {  
    Cookie cookieId = WebUtils.getCookie(request, "cookieId");  
    Optional<User> userFromCookie = repository.findById(Long.parseLong(cookieId.getValue()));  
  
    if (userFromCookie.filter(user -> user.getRole().equals(Role.ADMIN)).isPresent()) {  
        return "I am admin";  
    }  
    return "I am not admin";  
}
```
> Je vous conseille de créer une méthode *isAdmin(...)* dans une classe `UserUtils` pour bien séparer les tâches des classes entre elles.

2- Vérifiez si vous pouvez récupérer la liste des utilisateurs en tant qu'administrateur. **(1,5 points)**
Voici un exemple avec *curl* pour intégrer un cookie à votre requête *HTTP* :
`curl -v --cookie "cookieId=1" http://localhost:8080/get`

3- Vérifiez que vous ne pouvez pas accéder à cette liste si l'utilisateur n'est pas un administrateur (avec **cookieId=3** par exemple). **(1,5 points)**

4- Pensez à la gestion des erreurs. **(2 points)**

### Partie 2 - (6 points)
Un **étudiant** et un **invité** n'ont pas accès à la liste des utilisateurs au complet. Cependant, ils devraient tout de même avoir accès à leurs informations personnelles.

5- Créez une nouvelle méthode qui permet aux utilisateurs d'accéder aux informations **les concernant** via le chemin `get/{id}` et grâce au paramètre `@PathVariable Long id`. **(2,5 points)**
> Je vous conseille de créer une méthode *isHimSelf(...)* dans la classe `UserUtils` pour bien séparer les tâches des classes entre elles.
```java
return repository.findById(Long.parseLong(cookie.getValue()))  
        .filter(user -> user.getId().equals(id))  
        .isPresent();
```
> Cet exemple de code si dessus renvoie *vrai* **si l'identifiant du cookie correspond à l'identifiant de l'utilisateur**, en partant du principe que l'utilisateur existe en base; sinon le retour est *faux*

6- Vérifiez qu'un utilisateur ayant le **rôle** d'**étudiant** puisse accéder à ses propres informations. **(1,5 points)**

7- Pensez à nouveaux à la gestion des erreurs. **(2 points)**

### Partie 3 - (6 points)
Nos utilisateurs ont à présent le droit d'**accéder à certaines ressources selon leur rôle**.
Mais quant est il des nouveaux utilisateurs ?

8- Modifier la méthode de la [Partie 2](#partie-2) précédemment implémentée afin de créer un nouvel utilisateur (ayant le **rôle `Guest`**) **si aucun cookie n'est renseigné dans la requête**. **(2 point)**
> Pour enregistrer un utilisateur, utilisez la méthode **UserRepository.save(...)**

9- Toujours avec cette même méthode, faites en sorte de retourner un cookie dans la réponse. **(2,5 points)**
Voici une méthode qui vous permet de retourner un cookie :
```java
@RequestMapping(method = RequestMethod.POST)  
HttpServletResponse methode(@PathVariable Long id, HttpServletResponse response) {  
    response.addCookie(new Cookie("cookieId", User.getId().toString()));  
    return response;  
}
```

10- Vérifiez que la commande *curl* suivante créée bien un nouvel utilisateur; et que la réponse du service contienne bien l'attribut **set-cookie** :
`curl -v http://localhost:8080/get/9999`. **(1,5 points)**

## Pour conclure
Les cookies servent à stocker différentes informations côté client afin d'accélérer ses recherches par exemple. Par conséquent, les cookies sont faciles à manipuler en local.
Ils ne sont donc pas destinés à stocker des informations confidentielles. C'est ce que montre ce *TP* :  gérer les droits d'accès des utilisateurs via les cookies est une mauvaise pratique.

--------------
## Crédits

*Ce TP a été réalisé au sein du du cours Sécurité Web dans le cadre de notre Licence Professionnelle.*

Réalisateurs :
- VERLYNDE Audrey
- BAHAKI Eissam
- DALVAI Aimeric-Thomas
- POINT Jonathan
- **LEGEAY Loris**

