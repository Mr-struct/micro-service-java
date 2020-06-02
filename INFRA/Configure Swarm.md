# Sonfiguration de Swarm

## Initialiser docker swarm

### Sur le Manager

    docker swarm init

### dans le cas de mon test

"docker swarm join --token SWMTKN-1-5vw562yyl7bg7uk61iwk0osztnzmmbit4rsr4hdii2uikqsd0t-4i79sq0f66q4khnmxcen963rq 192.168.0.227:2377"

### Sur les workers

copier la ligne donnee par la commande du manager

### Quelques labels

* name :
* location :

    docker node update --label-add foo --label-add bar=baz node-1

### Donc dans notre cas

    docker node update --label-add name=docker-1 --label-add location=Paris docker-1
    docker node update --label-add name=docker-2 --label-add location=Lyon  docker-2
    docker node update --label-add name=docker-3 --label-add location=Bordeaux docker-3

### Capacite a heberger la "private registery"

Pour faciliter la gestion ajouter un label permetant a la registery d'aller sur le host :

    docker node update --label-add registry=true docker-1
    docker node update --label-add registry=true docker-2
    docker node update --label-add registry=true docker-3

pour supprimer un label, seul la clefs suffit:

    docker node update --label-rm registry docker-2
    docker node update --label-rm registry docker-3

### Inspecter un node

    docker node inspect docker-1 --pretty

le parametre --pretty permet de rendre les choses plus lisibles.