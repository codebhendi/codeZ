#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <bits/stdc++.h>

using namespace std;
void error(const char *msg)
{
    perror(msg);
    exit(0);
}

int main(int argc, char *argv[])
{
    int sockfd, portno, n;
    struct sockaddr_in serv_addr;
    struct hostent *server;

    char buffer[100];
    if (argc < 3) {
       fprintf(stderr,"usage %s hostname port\n", argv[0]);
       exit(0);
    }
    portno = atoi(argv[2]);
    sockfd = socket(AF_INET, SOCK_STREAM, 0);
    if (sockfd < 0)
        error("ERROR opening socket");
    server = gethostbyname(argv[1]);
    //printf("%s", server->h_name);
    if (server == NULL) {
        fprintf(stderr,"ERROR, no such host\n");
        exit(0);
    }
    bzero((char *) &serv_addr, sizeof(serv_addr));
    serv_addr.sin_family = AF_INET;
    bcopy((char *)server->h_addr,
         (char *)&serv_addr.sin_addr.s_addr,
         server->h_length);
    serv_addr.sin_port = htons(portno);
    if (connect(sockfd,(struct sockaddr *) &serv_addr,sizeof(serv_addr)) < 0)
        error("ERROR connecting");
    cout << "connected" << endl;

    bzero(buffer,104);
    int c;
    FILE * f = fopen("input.txt", "r");
    int counter = 0;
    int no_of_packets = 0;
    
    buffer[0] = '0';
    buffer[1] = '0';
    buffer[2] = '0';
    buffer[3] = '0';

    counter = 3;
    while ((c = fgetc(f)) != EOF) {
        counter++;
        if (counter == 103) {
            buffer[103] = '\0';
            counter =  4;
            int temp = no_of_packets++;
            n = write(sockfd,buffer,strlen(buffer));
            if (n < 0) {
                error("ERROR writing to socket");
            }
            bzero(buffer,104);
            buffer[0] = (char)(no_of_packets / 1000 + '0');
            no_of_packets %= 1000;
            buffer[1] = (char)(no_of_packets / 100 + '0');
            no_of_packets %= 100;
            buffer[2] = (char)(no_of_packets / 10 + '0');
            no_of_packets %= 10;
            buffer[3] = (char)(no_of_packets + '0');
            no_of_packets = temp;
        }
        buffer[counter] = (char)c;
        cout << buffer[counter] << endl;
    }

    if (counter != 4) {
        buffer[counter] = '\0';
        cout << buffer << endl;
        no_of_packets++;
        n = write(sockfd,buffer,strlen(buffer));
        if (n < 0) {
            error("ERROR writing to socket");
        }
    }

    close(sockfd);
    return 0;
}
