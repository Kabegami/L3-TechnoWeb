define({ "api": [
  {
    "type": "post",
    "url": "/auth/login",
    "title": "Connexion",
    "version": "0.1.0",
    "name": "Login",
    "group": "Authentification",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "login",
            "description": "<p>Login de l'utilisateur</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "pwd",
            "description": "<p>Mot de passe de l'utilisateur</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "Succès:",
          "content": "{\"key\" : \"110e8400-e29b-11d4-a716-446655440000\"}",
          "type": "json"
        }
      ]
    },
    "error": {
      "fields": {
        "ErrorJSON": [
          {
            "group": "ErrorJSON",
            "optional": false,
            "field": "-1",
            "description": "<p>Mauvais arguments</p>"
          },
          {
            "group": "ErrorJSON",
            "optional": false,
            "field": "1",
            "description": "<p>Utilisateur non existant</p>"
          },
          {
            "group": "ErrorJSON",
            "optional": false,
            "field": "2",
            "description": "<p>Mot de passe incorrect</p>"
          },
          {
            "group": "ErrorJSON",
            "optional": false,
            "field": "3",
            "description": "<p>Utilisteur déjà connecté</p>"
          }
        ]
      }
    },
    "filename": "src/servlets/LoginServlet.java",
    "groupTitle": "Authentification"
  },
  {
    "type": "get",
    "url": "/auth/logout",
    "title": "Déconnexion",
    "version": "0.1.0",
    "name": "Logout",
    "group": "Authentification",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "key",
            "description": "<p>Clé de session de l'utilisateur courant</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "Succès:",
          "content": "{}",
          "type": "json"
        }
      ]
    },
    "error": {
      "fields": {
        "ErrorJSON": [
          {
            "group": "ErrorJSON",
            "optional": false,
            "field": "-1",
            "description": "<p>Mauvais arguments</p>"
          },
          {
            "group": "ErrorJSON",
            "optional": false,
            "field": "2",
            "description": "<p>Utilisateur non connecté</p>"
          }
        ]
      }
    },
    "filename": "src/servlets/LogoutServlet.java",
    "groupTitle": "Authentification"
  },
  {
    "type": "get",
    "url": "/friend/add",
    "title": "Ajout d'ami",
    "version": "0.1.0",
    "name": "AddFriend",
    "group": "Friends",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "key",
            "description": "<p>Clé de session de l'utilisateur courant</p>"
          },
          {
            "group": "Parameter",
            "type": "int",
            "optional": false,
            "field": "id_friend",
            "description": "<p>id de l'ami à ajouter</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "Succès:",
          "content": "{}",
          "type": "json"
        }
      ]
    },
    "error": {
      "fields": {
        "ErrorJSON": [
          {
            "group": "ErrorJSON",
            "optional": false,
            "field": "-1",
            "description": "<p>Mauvais arguments</p>"
          },
          {
            "group": "ErrorJSON",
            "optional": false,
            "field": "1",
            "description": "<p>Utilisateur non existant</p>"
          },
          {
            "group": "ErrorJSON",
            "optional": false,
            "field": "2",
            "description": "<p>Utilisateur non connecté</p>"
          },
          {
            "group": "ErrorJSON",
            "optional": false,
            "field": "3",
            "description": "<p>Utilisateurs déjà amis</p>"
          }
        ]
      }
    },
    "filename": "src/servlets/AddFriendServlet.java",
    "groupTitle": "Friends"
  },
  {
    "type": "get",
    "url": "/friend/list",
    "title": "Liste les amis",
    "version": "0.1.0",
    "name": "ListFriends",
    "group": "Friends",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "key",
            "description": "<p>Clé de session de l'utilisateur courant</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "Succès:",
          "content": "{\"friends\":[{\"id\":3,\"username\":\"jean\"},{\"id\":4,\"username\":\"raoul\"}]}",
          "type": "json"
        }
      ]
    },
    "error": {
      "fields": {
        "ErrorJSON": [
          {
            "group": "ErrorJSON",
            "optional": false,
            "field": "-1",
            "description": "<p>Mauvais argument</p>"
          },
          {
            "group": "ErrorJSON",
            "optional": false,
            "field": "2",
            "description": "<p>Utilisateur non connecté</p>"
          }
        ]
      }
    },
    "filename": "src/servlets/ListFriendsServlet.java",
    "groupTitle": "Friends"
  },
  {
    "type": "get",
    "url": "/friend/remove",
    "title": "Retrait d'ami",
    "version": "0.1.0",
    "name": "RemoveFriend",
    "group": "Friends",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "key",
            "description": "<p>Clé de session de l'utilisateur courant</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id_friend",
            "description": "<p>id de l'ami à supprimer</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "Succès:",
          "content": "{}",
          "type": "json"
        }
      ]
    },
    "error": {
      "fields": {
        "ErrorJSON": [
          {
            "group": "ErrorJSON",
            "optional": false,
            "field": "-1",
            "description": "<p>Mauvais arguments</p>"
          },
          {
            "group": "ErrorJSON",
            "optional": false,
            "field": "2",
            "description": "<p>Utilisateur non connecté</p>"
          },
          {
            "group": "ErrorJSON",
            "optional": false,
            "field": "3",
            "description": "<p>Utilisateurs non amis</p>"
          }
        ]
      }
    },
    "filename": "src/servlets/RemoveFriendServlet.java",
    "groupTitle": "Friends"
  },
  {
    "type": "post",
    "url": "/message/new",
    "title": "Nouveau message",
    "version": "0.1.0",
    "name": "CreateMessage",
    "group": "Message",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "key",
            "description": "<p>Clé de session de l'utilisateur courant</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>Message</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "Succès:",
          "content": "{}",
          "type": "json"
        }
      ]
    },
    "error": {
      "fields": {
        "ErrorJSON": [
          {
            "group": "ErrorJSON",
            "optional": false,
            "field": "-1",
            "description": "<p>Mauvais arguments</p>"
          },
          {
            "group": "ErrorJSON",
            "optional": false,
            "field": "2",
            "description": "<p>Utilisateur non connecté</p>"
          }
        ]
      }
    },
    "filename": "src/servlets/NewMessageServlet.java",
    "groupTitle": "Message"
  },
  {
    "type": "get",
    "url": "/message/list",
    "title": "Liste des messages",
    "version": "0.1.0",
    "name": "ListMessages",
    "group": "Message",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "key",
            "description": "<p>Clé de session de l'utilisateur courant</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "Succès:",
          "content": "{\"messages\":[{\"text\":\"test\",\"author_username\":\"toto\",\"_id\":{\"$oid\":\"589af353e4b02b8b69b540be\"},\"author_id\":2,\"date\":{\"$date\":\"2017-02-08T10:30:43.411Z\"}},\n{\"text\":\"deuxieme message\",\"author_username\":\"toto\",\"_id\":{\"$oid\":\"589afd29e4b0c2f81a2b7eb6\"},\"author_id\":2,\"date\":{\"$date\":\"2017-02-08T11:12:41.561Z\"}}]}",
          "type": "json"
        }
      ]
    },
    "error": {
      "fields": {
        "ErrorJSON": [
          {
            "group": "ErrorJSON",
            "optional": false,
            "field": "-1",
            "description": "<p>Mauvais arguments</p>"
          },
          {
            "group": "ErrorJSON",
            "optional": false,
            "field": "2",
            "description": "<p>Utilisateur non connecté</p>"
          }
        ]
      }
    },
    "filename": "src/servlets/ListMessagesServlet.java",
    "groupTitle": "Message"
  },
  {
    "type": "get",
    "url": "/message/search",
    "title": "Chercher un message dans ceux des amis",
    "version": "0.1.0",
    "name": "SearchMsg",
    "group": "Message",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "key",
            "description": "<p>Clé de session de l'utilisateur courant</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "query",
            "description": "<p>Motif de recherche</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "Succès:",
          "content": "\t\t\t{\"messages\": [\n    {\n        \"text\": \"second message\",\n        \"author_username\": \"raoul\",\n        \"_id\": {\"$oid\": \"58a35b83e4b052853c124fbc\"},\n        \"author_id\": 4,\n        \"date\": {\"$date\": \"2017-02-14T19:33:23.748Z\"}\n    },\n    {\n        \"text\": \"secondmessage\",\n        \"author_username\": \"jean\",\n        \"_id\": {\"$oid\": \"58a35b46e4b052853c124fba\"},\n        \"author_id\": 3,\n        \"date\": {\"$date\": \"2017-02-14T19:32:22.377Z\"}\n    }\n]}",
          "type": "json"
        }
      ]
    },
    "error": {
      "fields": {
        "ErrorJSON": [
          {
            "group": "ErrorJSON",
            "optional": false,
            "field": "-1",
            "description": "<p>Mauvais arguments</p>"
          },
          {
            "group": "ErrorJSON",
            "optional": false,
            "field": "2",
            "description": "<p>Utilisateur non connecté</p>"
          }
        ]
      }
    },
    "filename": "src/servlets/SearchMsgServlet.java",
    "groupTitle": "Message"
  },
  {
    "type": "post",
    "url": "/usr/create",
    "title": "Créer un nouvel utilisateur",
    "version": "0.1.0",
    "name": "NewUser",
    "group": "User",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "login",
            "description": "<p>Login de l'utilisateur</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "pwd",
            "description": "<p>Mot de passe de l'utilisateur</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "lname",
            "description": "<p>Nom</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "fname",
            "description": "<p>Prénom</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "Succès:",
          "content": "{}",
          "type": "json"
        }
      ]
    },
    "error": {
      "fields": {
        "ErrorJSON": [
          {
            "group": "ErrorJSON",
            "optional": false,
            "field": "-1",
            "description": "<p>Mauvais arguments</p>"
          },
          {
            "group": "ErrorJSON",
            "optional": false,
            "field": "1",
            "description": "<p>Utilisateur déjà existant</p>"
          }
        ]
      }
    },
    "filename": "src/servlets/NewUserServlet.java",
    "groupTitle": "User"
  }
] });
