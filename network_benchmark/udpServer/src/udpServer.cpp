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
struct timeval tp;
using namespace std;

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

void error(const char *msg)
{
    perror(msg);
    exit(0);
}

void *handleRequest(void *argument) {
	disk_struct struc = *((disk_struct*) argument);
	int tid = struc.thread_id;
	long blocksize = struc.bsize;
	long portNo = tid +8081;

	int sock, length, n,m;
	   socklen_t fromlen;
	   struct sockaddr_in server;
	   struct sockaddr_in from;
	   char * buffer;
	   char * buffer2;
	   size_t lSize = 1*1E7;
		long iterations = lSize/blocksize;
		long i;
	   sock=socket(AF_INET, SOCK_DGRAM, 0);
	   if (sock < 0) error("Opening socket");
	   length = sizeof(server);
	   bzero(&server,length);
	   server.sin_family=AF_INET;
	   server.sin_addr.s_addr=INADDR_ANY;
	   cout<< portNo<<endl;
	   server.sin_port=htons(portNo);
	   if (bind(sock,(struct sockaddr *)&server,length)<0)
	       error("binding");
	   fromlen = sizeof(struct sockaddr_in);
	   buffer = (char*) malloc(sizeof(char)*blocksize);
	   bzero(buffer,blocksize);
	   buffer2 = (char*) malloc(sizeof(char)*lSize);
	   bzero(buffer2,lSize);
	   while (1) {

		   //confused: for loop didnt work. how to make sure sendto happens after all of block transfers, not after each
//		   for (i = 0; i < iterations; ++i) {
			   n = recvfrom(sock,buffer,blocksize,0,(struct sockaddr *)&from,&fromlen);
//			   cout<<strlen(buffer)<<endl;
			   if (n < 0) error("recvfrom");
//		       write(1, buffer,n);
//		   write(1,(char*)i,1);
//		   memcpy(buffer2+ (blocksize * i),buffer , blocksize);
//		   }
//	       write(1,"Received a datagram: ",21);
	       m = sendto(sock,"G",1, 0,(struct sockaddr *)&from,fromlen);
	       if (n  < 0) error("sendto");

//
//		   n = recvfrom(sock,buffer,1,0,(struct sockaddr *)&from,&fromlen);
//		   if (n < 0) error("recvfrom");
//		   n = sendto(sock,"22\n",17, 0,(struct sockaddr *)&from,fromlen);
//		   	       if (n  < 0) error("sendto");
	   }

	pthread_exit(NULL);
	return NULL;
}

int main(int argc, char *argv[]) {
	int	numThreads = atoi(argv[1]);
	long blocksize = atol(argv[2]);
	disk_struct struc[numThreads];

	long i; // base number used for port number
	pthread_t server_thread[numThreads];
	for(i = 0;i < numThreads;i++)
	{
		struc[i].thread_id = i;
		struc[i].bsize = blocksize;
		pthread_create(&server_thread[i], NULL, handleRequest, (void *) &struc[i]);
	}
	pthread_exit(NULL);

   return 0;
 }
