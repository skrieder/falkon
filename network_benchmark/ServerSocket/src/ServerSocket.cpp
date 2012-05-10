/* A simple server in the internet domain using TCP
   The port number is passed as an argument */
#include <iostream>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <sys/time.h>
#include <assert.h>
#include <pthread.h>
#include <fstream>

using namespace std;
struct timeval tp;
typedef struct _disk_struct
{
	int newsockfd;
	long bsize;
} disk_struct;
void error(const char *msg)
{
    perror(msg);
    exit(1);
}

double getTime_usec() {
	gettimeofday(&tp, NULL);
	return static_cast<double>(tp.tv_sec) * 1E6
			+ static_cast<double>(tp.tv_usec);
}
void *handleRequest(void *argument) {
	disk_struct struc = *((disk_struct*) argument);
	int newsockfd = struc.newsockfd;
	long blocksize = struc.bsize;
	int lSize = 1*1E7;
	long iterations = lSize/blocksize;
	long i;
	double start, total, result, current;
    char* buffer;
    char* buffer2;
    long n;


	buffer = (char*) malloc(sizeof(char)*blocksize);
	bzero(buffer,blocksize);
	buffer2 = (char*) malloc(sizeof(char)*lSize);
	bzero(buffer2,lSize);

	for (i = 0; i < iterations; ++i) {
		n = read(newsockfd,buffer,blocksize);
//		printf("\ni:%li ",i);
//		cout << n << endl;
//		cout<<buffer<<endl;
    if (n < 0) error("ERROR reading from socket");
//	memcpy(buffer2+ (blocksize * i),buffer , blocksize);
	}
 //   printf("Here is the message:\n");


    close(newsockfd);

	pthread_exit(NULL);
	return NULL;
}

int main(int argc, char *argv[]) {
	long blocksize = atol(argv[1]);
	disk_struct struc[100];
	pthread_t threads[100];
	int thread_args[100];
	int rc, i;

     int sockfd, newsockfd, portno;
     socklen_t clilen;
     struct sockaddr_in serv_addr, cli_addr;
     if (argc < 2) {
         fprintf(stderr,"ERROR, no port provided\n");
         exit(1);
     }

     sockfd = socket(AF_INET, SOCK_STREAM, 0);
     if (sockfd < 0)
        error("ERROR opening socket");
     bzero((char *) &serv_addr, sizeof(serv_addr));
     portno = atoi("2001");
     serv_addr.sin_family = AF_INET;
     serv_addr.sin_addr.s_addr = INADDR_ANY;
     serv_addr.sin_port = htons(portno);
     if (bind(sockfd, (struct sockaddr *) &serv_addr,sizeof(serv_addr)) < 0)
              error("ERROR on binding");
     listen(sockfd,5);
     i = 0;

     //
     while (1){
         clilen = sizeof(cli_addr);
         newsockfd = accept(sockfd, (struct sockaddr *) &cli_addr, &clilen);
         if (newsockfd < 0)
              error("ERROR on accept");
    	 struc[i].newsockfd = newsockfd;
    	 struc[i].bsize = blocksize;
 		rc = pthread_create(&threads[i], NULL, handleRequest, (void *) &struc[i]);
 		assert(0 == rc);
        i++;
     }
     printf("after while loop");
     close(sockfd);
     return 0;
}
