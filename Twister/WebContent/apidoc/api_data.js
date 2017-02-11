define({ "api": [
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
    "filename": "./src/servlets/NewUserServlet.java",
    "groupTitle": "User"
  },
  {
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "optional": false,
            "field": "varname1",
            "description": "<p>No type.</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "varname2",
            "description": "<p>With type.</p>"
          }
        ]
      }
    },
    "type": "",
    "url": "",
    "version": "0.0.0",
    "filename": "./WebContent/apidoc/main.js",
    "group": "_home_huc_git_workspace_3I017_Twister_WebContent_apidoc_main_js",
    "groupTitle": "_home_huc_git_workspace_3I017_Twister_WebContent_apidoc_main_js",
    "name": ""
  }
] });