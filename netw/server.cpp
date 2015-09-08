#include <bits/stdc++.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>

using namespace std;

void error(const char *msg)
{
    perror(msg);
    exit(1);
}

int main(int argc, char *argv[])
{
    char buff_arr[100][100];
    int sockfd, newsockfd, portno;
    socklen_t clilen;
    char buffer[104];
    struct sockaddr_in serv_addr, cli_addr;
    int n;
    if (argc < 2) {
     fprintf(stderr,"ERROR, no port provided\n");
     exit(1);
    }
    sockfd = socket(AF_INET, SOCK_STREAM, 0);
    if (sockfd < 0)
    error("ERROR opening socket");
    bzero((char *) &serv_addr, sizeof(serv_addr));
    portno = atoi(argv[1]);
    serv_addr.sin_family = AF_INET;
    serv_addr.sin_addr.s_addr = INADDR_ANY;
    serv_addr.sin_port = htons(portno);
    if (bind(sockfd, (struct sockaddr *) &serv_addr,
          sizeof(serv_addr)) < 0)
          error("ERROR on binding");
    printf("connected\n");
    int count = 0;
    FILE * f = fopen("output.txt", "w");
    int no_of_packets = 0;
    while (count != 100) {
        listen(sockfd,5);
        clilen = sizeof(cli_addr);
        newsockfd = accept(sockfd,
             (struct sockaddr *) &cli_addr,
             &clilen);
        if (newsockfd < 0)
        error("ERROR on accept");
        bzero(buffer,104);
        n = read(newsockfd,buffer,104);
        if (n < 0) error("ERROR reading from socket");
        cout << buffer << endl;
        int temp = sizeof(buffer);
        int a = (buffer[0] - '0') * 1000;  
        a += (buffer[1] - '0') * 100;
        a += (buffer[2] - '0') * 10;
        a += (buffer[3] - '0');
        cout << a << endl;
        int temp2 = 4;
        while (fputc(buffer[temp2], f)) {
            cout << "sfndfg" << endl;
        }       
        close(newsockfd);
        count++;
        no_of_packets++;
    }
    fclose(f);
    /*n = write(newsockfd,"I got your message",18);
    if (n < 0) error("ERROR writing to socket");*/
    close(sockfd);
    return 0;
}
