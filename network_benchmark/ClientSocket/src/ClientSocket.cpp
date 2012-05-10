#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <sys/time.h>
#include <assert.h>
#include <pthread.h>
# include <iostream>
using namespace std;


struct timeval tp;

double getTime_usec() {
	gettimeofday(&tp, NULL);
	return static_cast<double>(tp.tv_sec) * 1E6 + static_cast<double>(tp.tv_usec);
}

void error(const char *msg)
{
    perror(msg);
    exit(0);
}

void *sendRequest(void *argument) {
	long blocksize = *((long *) argument);
	double start, total, current,latency;
    char buffer[256];
	char * buffer2;
	long lSize = 1*1E7;

	//sockfd = *((int *) argument);

	// set port number manually
	int portno = 2001;
	long i,n;
    int sockfd;
    struct sockaddr_in serv_addr;
    struct hostent *server;
	long iterations = lSize/blocksize;

    sockfd = socket(AF_INET, SOCK_STREAM, 0);
    if (sockfd < 0)
        error("ERROR opening socket");

    // set server address manually. later u have to change this
    server = gethostbyname("localhost");
    if (server == NULL) {
        fprintf(stderr,"ERROR, no such host\n");
        exit(0);
    }
    bzero((char *) &serv_addr, sizeof(serv_addr));
    serv_addr.sin_family = AF_INET;
    bcopy((char *)server->h_addr, (char *)&serv_addr.sin_addr.s_addr, server->h_length);
    serv_addr.sin_port = htons(portno);

    if (connect(sockfd,(struct sockaddr *) &serv_addr,sizeof(serv_addr)) < 0)
        error("ERROR connecting");

    // set a buffer to send.
	buffer2 = (char*) malloc(sizeof(char)*blocksize);
	if (buffer2 == NULL) {fputs ("Memory error",stderr); exit (2);}
	memset(buffer2,'c',blocksize);
	cout << blocksize << endl;
	start = getTime_usec();
	for (i = 0; i < iterations; ++i) {
		n= send(sockfd, buffer2, blocksize, 0);
	}
    current = getTime_usec();
	total = lSize/(current - start)*8;
	printf("\ntotal:%f",total);

	  if (blocksize == 1){
		  latency = (current - start)/lSize;
		  printf("\nlatency: %f",latency);
	  }

    close(sockfd);

	pthread_exit(NULL);
	return NULL;
}

int main(int argc, char *argv[]) {
	int	numThreads = atoi(argv[1]);
	long blocksize = atol(argv[2]);
	printf("\nblocksize:%li",blocksize);
	cout << "\n"<< endl;
	int rc, i;
	pthread_t threads[numThreads];
	/* create all threads */
	for (i = 0; i < numThreads; ++i) {

		rc = pthread_create(&threads[i], NULL, sendRequest, (void *) &blocksize);
		assert(0 == rc);
	}

	pthread_exit(NULL);
	cout << "\n" << endl;
    return 0;
}
