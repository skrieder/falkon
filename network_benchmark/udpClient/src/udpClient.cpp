#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <netdb.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <pthread.h>
#include <iostream>
#include <sys/time.h>
using namespace std;
struct timeval tp;

typedef struct _disk_struct
{
	int thread_id;
	long bsize;
} disk_struct;
double getTime_msec() {
	gettimeofday(&tp, NULL);
	return static_cast<double>(tp.tv_sec) * 1E3
			+ static_cast<double>(tp.tv_usec) / 1E3;
}
double getTime_usec() {
	gettimeofday(&tp, NULL);
	return static_cast<double>(tp.tv_sec) * 1E6
			+ static_cast<double>(tp.tv_usec);
}

void error(const char *msg);
void *sendRequest(void* argument) {
	disk_struct struc = *((disk_struct*) argument);
	int tid = struc.thread_id;
	long blocksize = struc.bsize;
	long portNumber = tid +8081;
	double start,current,total,latency;
	int sock, n;
	unsigned int length;
	struct sockaddr_in server, from;
	struct hostent *hp;
	char* buffer;
	char * receive;
	long lSize = 1*1E8;
	long iterations = lSize/blocksize;
	long i;
	sock= socket(AF_INET, SOCK_DGRAM, 0);
	if (sock < 0) error("socket");
	server.sin_family = AF_INET;
	hp = gethostbyname("localhost");
	bcopy((char *)hp->h_addr,(char *)&server.sin_addr,hp->h_length);
	server.sin_port = htons(portNumber);
	length=sizeof(struct sockaddr_in);
	// allocate memory for buffer and fill it with '0' chars
	receive = (char*) malloc(sizeof(char)*1);
	memset(receive,'0',1);
	buffer = (char*) malloc(sizeof(char)*blocksize);
	memset(buffer,'s',blocksize);

	start = getTime_usec();
	for (i = 0; i < iterations; ++i) {
		n=sendto(sock,buffer, blocksize,0,(const struct sockaddr *)&server,length);
		if (n < 0) error("Sendto");
		n = recvfrom(sock,receive,1,0,(struct sockaddr *)&from, &length);
		if (n < 0) error("recvfrom");
	}
	current = getTime_usec();
	total = lSize/(current - start)*8;
	printf("\nthroughput:%f ",total);
	  if (blocksize == 1){
		  latency = (current - start)/lSize;
		  printf("\nlatency: %f",latency);
	  }

	   close(sock);

	return NULL;
}

int main(int argc, char *argv[]) {
	int	numThreads = atoi(argv[1]);
	long blocksize = atol(argv[2]);
	disk_struct struc[numThreads];
	printf("\nblocksize:%li",blocksize);
	cout << "\n"<< endl;
	long i =0; // base number used for port number
	pthread_t server_thread[numThreads];
	for(i = 0;i < numThreads;i++)
	{
		struc[i].thread_id = i;
		struc[i].bsize = blocksize;
		pthread_create(&server_thread[i], NULL, sendRequest, (void *) &struc[i]);
	}
	pthread_exit(NULL);

	cout << "\n" << endl;
   return 0;
}

void error(const char *msg)
{
    perror(msg);
    exit(0);
}
