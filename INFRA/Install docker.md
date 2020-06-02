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
