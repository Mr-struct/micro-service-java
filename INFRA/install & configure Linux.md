# Install de base

## install iso

<https://cdimage.debian.org/cdimage/archive/9.12.0/amd64/iso-cd/debian-9.12.0-amd64-netinst.iso>

INSTALL EN ANGLAIS : (le francais fait chier avec les accents)

## Partitionement du disque et LVM

### Partitionement du disque

#### Disk 1 (7GB)

1) Manuel
2) 550 MB EFI System Partition (BOOTABLE FLAG)
3) 260 MB ext2 --> /boot (partition de boot OS)
4) 1536 MB --> SWAP
5) Reste --> LVM (root)

#### Disk 2 (10GB)

1. Manuel
1. LVM (/var)

### Partitionnement du LVM et groupe

#### creation de groupe

creer un groupe (en géneral "nom de machine"-vg) (dans mon cas : docker-1-vg)

#### creation des logical volumes du groupe

1. Tout --> ext4 --> /
1. Tout --> ext4 --> /var

pour le clavier et les parametres regionaux faire comme vous voulez (ca sera changé plus tard dans l'install)

uniquement les packages "base system et ssh"

dpkg-reconfigure tzdata (juste pour etre sûr)

## Passwords and shit:

par defaut, j'utilise root//root et user//user (on doit creer un compte utilisateur "de base")

### dans .bashrc de root

    export PS1='\[\033[1;31m\]\u\[\033[01;33m\] @ \[\033[1;32m\]\h\[\033[01;36m\] \w \[\033[01;32m\]\$\[\033[00m\] '

### decommenter les lignes suivantes

    export LS_OPTIONS='--color=auto'
    eval "`dircolors`"
    alias ls='ls $LS_OPTIONS'

## installer les trucs utils

### en root

    apt update && apt upgrade -y
    apt install -y vim atop htop iftop iotop mc sudo ntpdate neofetch tmux powerline

pas envie de tout detailler ...

# "Script" d'installation de Docker

Il est possible que tout ne puisse pas etre "collé" en une traite, faire attention.

    apt -y install apt-transport-https ca-certificates software-properties-common
    wget https://download.docker.com/linux/debian/gpg
    apt-key add gpg
    add-apt-repository "deb https://download.docker.com/linux/debian stretch    stable"
    apt update
    apt install -y docker-ce
    systemctl start docker
    systemctl enable docker
    service docker status

### ntp client

    ntpdate 0.fr.pool.npt.org

## parametre sudo pour un emmerdement minimum (pas secure du tout)

### Creer le(s)fichier(s) sudo

    echo "user     ALL=(ALL) NOPASSWD:ALL" > /etc/sudoers.d/user
    echo "Defaults 	insults" > /etc/sudoers.d/insults
    echo "docker  ALL=(ALL:ALL) NOPASSWD:ALL" > /etc/sudoers.d/docker

ca permet a l'utilisateur docker d'avoir les droits root sans mettre de mot de passe juste en mettant sudo devant la commande

dans

    /etc/sudoers.d/"username"

### Idealement

mettre les droits minumum necessaires au fonctionnement.

### ajout des droits docker pour l'utilisateur

    sudo usermod -aG docker "username"

## dans .bashrc de l'utilisateur (docker dans mon cas)

decommenter les lignes suivantes

    force_color_prompt=yes

et

    alias ls='ls --color=auto'
    alias dir='dir --color=auto'
    alias vdir='vdir --color=auto'
    alias grep='grep --color=auto'
    alias fgrep='fgrep --color=auto'
    alias egrep='egrep --color=auto'

et a la fin du fichier,

    # Some Personal Aliases
    alias fuck='sudo $(fc -ln -1)'
    alias ta='tmux attach'
    alias tl-'tmux ls'

    function test_existence
    {
        if [[ -z "$1" ]]
        then
        return 1
    fi

    if [[ -z "$(type -t "$1" || declare -F "$1")" ]]
    then
        return 1
    fi
    }

    if [[ -n "$TMUX" ]] && test_existence powerline-config
    then
        powerline-config tmux setup
    fi

### fichier host

Comme les vm sont sur mon portable, et que les vm peuvent changer d'IP, j'ai crée un reseau interne au portable et atrtribué les ip suivantes aux VM : dans un reseau /25 (255.255.255.0)

* 10.100.200.1  docker-1
* 10.100.200.2  docker-2
* 10.100.200.3  docker-3

renseigner chaque machine dans le hostfile de ses voisines
