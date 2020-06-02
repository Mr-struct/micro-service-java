# Install Glusterfs

    apt install -y glusterfs-server

unmount glusterfs partition to set the rights parameters in fstab.

    umount /mnt/glusterfs

## modifier le fstab (/etc/fstab)

comment the line in fstab for the /mnt/glusterfs mount point

replace with this one (mutatis mutandis) pour chaque node

    /dev/mapper/docker--1--vg-glusterfs     /mnt/glusterfs  ext4    auto,noatime,discard,acl        0       0

and then

    mount -a

it is possible to limit the ip addreses glusterfs is listening to by editing the /etc/glusterfs/glusterd.vol file

## restart glusterfs service and check

    service glusterfs-server restart
    service glusterfs-server status

## dicover other nodes and "probe them" (only on node 1 in this case)

    gluster peer probe docker-2
    gluster peer probe docker-3

then

    root @ docker-1 ~ # gluster peer status
    Number of Peers: 2

    Hostname: docker-2
    Uuid: 9b18dd4f-2210-40a6-9bc5-196850237120
    State: Peer in Cluster (Connected)

    Hostname: docker-3
    Uuid: 30a844cd-26e7-42b7-ba0e-071d316efb68
    State: Peer in Cluster (Connected)
    root @ docker-1 ~ #

now that gluterfs sees each other (each node sees both others)

## Let's add some Bricks, create volumes

The folder having already been creted, no need to do so again.
let's create a volume called "vol-gluster" for all three nodes (docker-1, docker-2 and docker-3) and set the volume type as replica.  (needs to be done only on one node)

    gluster volume create vol-gluster replica 3 transport tcp \
    docker-1:/mnt/glusterfs/vol-gluster \
    docker-2:/mnt/glusterfs/vol-gluster \
    docker-3:/mnt/glusterfs/vol-gluster

you can check the volumes with these :

    gluster volume info

and

    gluster volume status

## start the volume

    gluster volume start vol-gluster

## creer le repertoire de donnees et monter le volume gluster dedans

    mkdir /mnt/glusterfs/replica

### montage manuel pour le moment (respectively on each node)

    mount docker-1:/vol-gluster /mnt/glusterfs/replica/ -t glusterfs -o defaults

## check results

create file on one node, and check it exists on the others (file is to be created in /mnt/glusterfs/replica)
then remove the file and it should be removed everywhere.

## create startup script:

in the /root/script folder

    #!/bin/bash

    /bin/mount docker-1:/vol-gluster /mnt/glusterfs/replica -t glusterfs

Make sure to set it up for the correct node
make sure to render the script executable (chmod u+x /root/scripts/mount-gluster.sh)

### crontab the stuff

    crontab -e

add the following line :

    @reboot /root/scripts/mount-gluster.sh

## Add / remove bricks

gluster volume add-brick vol-gluster replica 3 docker-3:/mnt/glusterfs/vol-gluster