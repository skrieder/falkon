/*
**BGexec.c code is written by Zhao Zhang, it is used to run Falkon
**task on BG/P and SiCortex. This version of source code is tested
**with gcc 4.1.2 on BlueGene/P Login Node.
*/



#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <netdb.h>
#include <unistd.h>
#include <errno.h>
#include <netinet/tcp.h>

/*Size limit of command line using System() call*/
#define MAXLINE 1024*1024 

/*debug flag 0 means no debugging, 1 means debugging*/
int flag=0;             

/*link list to store arguments and environments*/   
struct list_el
{
    char *val;
    struct list_el * next;
};
typedef struct list_el item;

/*definition of task*/
struct task_el
{
    char *taskID;
    char *command;
    char **arg;
    char **env;
    char *directory;
    int exitcode;
};
typedef struct task_el Task;

item* addtolist(char[], item *);
int parseExec(char[], Task *);
char* getTaskID(char *);
void set_sockopt(int);
int recv_wrapper(int, int, char[], int);


int main(int argc, char**argv)
{
    int  sfd,ssfd;
    char num[10];
    char clientID[22];
    char filename[100];
    int result;
    int taskSize;
    int exitCode;
    int numBytes;
    char *msg;
    char *taskID;
    char ACK[]="OK\n";
    char lifetime[]="10\n";
    Task *task;
    char *back;

    if (argc!=5)
    {
	fprintf(stdout,"usage: client <IP> <port> <port> <debug>");
	exit(1);
    }

    if (strcmp(argv[4],"-debug")==0)
  	flag=1;
    
 recover:
    if(flag == 1)
	fprintf(stdout,"con_server() to %s on port %s\n", argv[1], argv[2]);
    sfd=con_server(atoi(argv[2]), argv[1]);
    set_sockopt(sfd);
    if(flag == 1)
	fprintf(stdout,"con_server() to %s on port %s\n", argv[1], argv[3]);
    ssfd=con_server(atoi(argv[3]), argv[1]);
    
    if (flag==1)
	fprintf(stdout,"connection established!\n");
    
    
    
    /*numBytes=send(sfd, lifetime, sizeof(lifetime), 0);
      if (numBytes<0)
      {
      fprintf(stdout, "main: send lifetime error\n");
      
      fprintf(stdout,"main: error description: EOF\n");
      recovery(sfd, ssfd);
      goto recover;
      }*/
    
    while (1)
    {
	task = (Task *)malloc(sizeof(Task));
	if (task==NULL)
	    printf("failed to allocate memory in main loop\n");
	memset(filename,'\0',sizeof(filename));
	memset(clientID,'\0',sizeof(clientID));
	memset(num, '\0', sizeof(num));
	
	/*receive clientID*/
	recv_wrapper(sfd, ssfd, clientID, 22);
	if (flag==1)
	{
	    fprintf(stdout,"main: clientID: %s\n", clientID);
	}
	
	/*receive size of task*/
	recv_wrapper(sfd, ssfd, num, 10);
	if (flag==1)
	{
	    fprintf(stdout,"main: size_of_task: %s\n", num);
	}
	
	/*allocate memory for task description*/
	taskSize=atoi(num);
	msg=malloc(sizeof(char)*(taskSize+1));
	if (msg==NULL)
	{
	    fprintf(stdout,"main: allocate memory failed\n");
	    exit (1);
	}
	memset(msg,'\0',taskSize+1);
    	
	/*receive task description*/
	recv_wrapper(sfd, ssfd, msg, taskSize);
	if (flag==1)
	{
	    fprintf(stdout,"main:received: %s\n", msg);
	}
    
	/*send acknoledgement for the received task*/    
	numBytes=send(sfd, ACK,strlen(ACK),0);
	if (numBytes < 0)
	{
	    fprintf(stdout,"main: Sent ACK %s failed\n", ACK);
	    fprintf(stdout,"main: error description: EOF\n");
	    recovery(sfd, ssfd);
	    goto recover;
	}
	else
	{   
	    if (flag == 1) 
		fprintf(stdout,"main: Sent ACK %s successful (%d)\n", ACK, numBytes);
	}
	
	parseExec(msg, task);
	exitCode = run_task(task);
	/*send results back to Falkon service*/    
	numBytes = sendResults(task, ssfd, sfd);
	if (numBytes < 0)
	{
	    fprintf(stdout,"main: Send results failed\n");
	    recovery(sfd, ssfd);
	    goto recover;
	}
    
	//free(task->taskID);
	//free(task->command);
	//free(task->directory);
	free(task);
	free(msg);
	//exit(1);
    }
}

int con_server(int port, char* argv1)
{
    struct sockaddr_in  serv_addr;
    int sfd;
    bzero(( char *) &serv_addr, sizeof(serv_addr));
    serv_addr.sin_family   = AF_INET;
    serv_addr.sin_port     = htons(port);
    inet_pton(AF_INET, argv1, &serv_addr.sin_addr);
    if (flag==1)
	fprintf(stdout,"creating socket\n");
    
    if ( (sfd = socket(AF_INET, SOCK_STREAM, 0)) < 0)
    {
	fprintf(stdout,"client: can't open stream socket\n");
	exit(1);
    }
    
    if (flag==1)
	fprintf(stdout,"connecting\n");
    if ( connect( sfd, (struct sockaddr *) &serv_addr, sizeof(serv_addr)) < 0)
    {
	fprintf(stdout,"client: can't connect to server\n");
	exit(2);
    }
    
    if (flag==1)
	fprintf(stdout,"connected!\n");
    return sfd;
}

int sendResults(Task *task, int ssfd, int sfd)
{
    int num;
    char res[100];
    memset(res, 0, 100);
    strcpy(res, task->taskID);
    char exitC[10];
    memset(exitC, 0, 10);
    sprintf(exitC, "#%d", task->exitcode);
    strcpy(res,task->taskID);
    
    strcat(res, exitC);
    if (flag==1)
	fprintf(stdout, "sendResults: %s\n",res);

    num=send(ssfd, res,strlen(res),0);
    if (num < strlen(res))
    {
	fprintf(stdout, "sendResults error: %s, sent nly %d of the %d bytes...\n",res, num, strlen(res));
    }
    else
    { 
	if (flag == 1) 
	    fprintf(stdout, "sendResults OK: %s, sent %d bytes!\n",res, num);
    }
    
    if(flag == 1)
	printf("str: %s\n", res);
    //free(res);
    return num;
}


int parseExec(char str[], Task *task)
{
    char *taskID;
    char *command;
    char execute[MAXLINE];
    memset(execute, '\0', sizeof(command));
    char *directory;
    item * head_arg;
    item * head_env;
    //item * p,q;
    char **argss;
    char **envss;

    char *preargs;
    char *preenvs;
    item *arg, *env;
    char *pt;
    char *delims ="#";



    //back=strdup(str);
    //if (flag==1)
    //fprintf(stdout, "back: %s\n", back);
    taskID=strtok(str,delims);
    if (flag==1)
	fprintf(stdout,"taskID: %s\n",taskID);
    
    command=strtok(NULL,delims);
    if (flag==1)
	fprintf(stdout,"command: %s\n",command);
    preargs=strtok(NULL,delims);
    if (flag==1)
	fprintf(stdout,"args: %s\n",preargs);
    preenvs=strtok(NULL,delims);
    directory=strtok(NULL,delims);
    if (flag==1)
	fprintf(stdout,"directory: %s\n",directory);

    if (strncmp(directory, "/", 1)==0)
    {
	strcat(execute, "/fuse");
    }
    
    strcat(execute, command);
    strcat(execute, " ");
    strcat(execute, preargs);
    if (flag==1)
	fprintf(stdout,"execute: %s\n",execute);
    //free(back);
    
    //link list thing
    /*char *args = malloc(sizeof(char)*(strlen(preargs)+1));
      strcpy(args,preargs);
      
      preenvs=strtok(NULL,delims);
      char *envs = malloc(sizeof(char)*(strlen(preenvs)+1));
      strcpy(envs,preenvs);
      
      directory=strtok(NULL,delims);
      
      int num_arg=0;
      int num_env=0;
      
      arg=addtolist(args,head_arg);
      p=arg;
      strcpy(execute, command);
      while(p) {
      //fprintf(stdout,"args: %s\n", p->val);
      strcat(execute, " ");
      strcat(execute, p->val);
      p = p->next ;
      num_arg++;
      }
      //fprintf(stdout, "num_arg: %d\n", num_arg);
      //fprintf(stdout, "command to be executed: %s\n", execute);
      argss = (char **) calloc((num_arg+1), sizeof(char **));
      int index=0;
      argss[index++]=(char *)malloc(sizeof(char)*(strlen(command)+1));
      strcpy(argss[0], command);
      fprintf(stdout, "argss[0]: %s\n", argss[0]);
      p=arg;
      while(index<num_arg+1)
      {
      argss[index]=(char *)malloc(sizeof(char)*(strlen(p->val)+1));
      strcpy(argss[index], p->val);
      index++;
      p=p->next;
      }
      argss[index] = (char*)0;
      int i;
      for(i=0;i<num_arg+1;i++)
      fprintf(stdout, "argss[%d]: %s\n", i, argss[i]);
      
      env=addtolist(envs,head_env);
      p=env;
      
      
      while(p) {
      fprintf(stdout,"envs: %s\n", p->val);
      p = p->next;
      num_env++;
      }
      fprintf(stdout, "num_env: %d\n", num_env);
      envss = (char **) calloc((num_env+1), sizeof(char **));
      index=0;
      p=env;
      while(index<num_env)
      {
      envss[index]=(char *)malloc(sizeof(char)*(strlen(p->val)+1));
      strcpy(envss[index], p->val);
      index++;
      p=p->next;
      }
      envss[index]=(char*)0;
      for(i=0;i<num_env;i++)
      fprintf(stdout, "envss[%d]: %s\n", i, envss[i]);*/
    
    //fprintf(stdout,"\n");
    
    //pid_t pid;
    //pid=fork();
    //if(pid==0)
    //exitCode=execve(command, argss, envss);
    //fprintf(stdout, "sleep over\n");
    task->taskID = taskID;
    task->command = execute;
    task->directory = directory;
    //return back;
}
int run_task(Task *task)
{
    int exitCode;
    struct timeval execStartTime;
    struct timeval execEndTime;
    
    if (strncmp(task->directory, "/", 1)==0)
    {
	if(flag == 1)
	    printf("changing dir\n");
	int rc=chdir(task->directory);
	
	if( rc == -1 ) 
	{
	    fprintf(stderr,"BGexec chdir failed to dir: %s\n", task->directory);
	    perror("BGexec chdir failed");
	    char cwd[PATH_MAX];
	    fprintf(stderr,"BGexec cur dir is: %s\n\n", getcwd(cwd,PATH_MAX));
	}
	else 
	{
	    if(flag == 1)
		fprintf(stderr, "change dir to %s succeeded\n\n",task->directory);
	}
    }
    
    gettimeofday(&execStartTime,NULL);
    if(flag == 1)
	fprintf(stdout, "%d.%d: executing taskID %s %s...", execStartTime.tv_sec,  execStartTime.tv_usec, task->taskID, task->command); 
    exitCode = system(task->command);
    gettimeofday(&execEndTime,NULL);
    
    float timeElapsedMS = ( execEndTime.tv_sec - execStartTime.tv_sec)*1000.0 + execEndTime.tv_usec/1000.0 - execStartTime.tv_usec/1000.0;
    if(flag == 1)
	fprintf(stdout, " completed with exit code %d in %f ms!\n",exitCode, timeElapsedMS); 
    fflush(stdout);
    
    task->exitcode = exitCode;
    return exitCode;	
}
item * addtolist(char str[], item *head)
{
    item * curr, *previous;
    head = NULL;
    previous = NULL;
    
    char* temp;
    char* local;
    
    temp=strtok(str, " ");
    while (temp!=NULL)
    {
	if (temp==NULL)
	    break;
	curr = (item *)malloc(sizeof(item));
	local=malloc(sizeof(char)*(strlen(temp)+1));
	strcpy(local,temp);
	curr->val = local;
	
	if (previous!=NULL)
	    previous->next=curr;
	else
	    head=curr;
	previous=curr;
	temp=strtok(NULL, " ");
    }
    
    curr = head;
    return head;
}

int recovery(int sfd, int ssfd)
{
    if (flag==1)
	fprintf(stdout, "reconnecting\n");
    close(sfd);
    close(ssfd);
    exit(1);
}

/* disable nagle on socket */
void set_sockopt(int fd)
{
    int opt;
    socklen_t optlen;
    
    optlen = sizeof opt;
    if (getsockopt(fd, IPPROTO_TCP, TCP_NODELAY, &opt, &optlen) == -1) {
	printf("getsockopt TCP_NODELAY: %.100s", strerror(errno));
	return;
    }
    if (opt == 1) {
	//printf("fd %d is TCP_NODELAY", fd);
	return;
    }
    opt = 1;
    //printf("fd %d setting TCP_NODELAY", fd);
    if (setsockopt(fd, IPPROTO_TCP, TCP_NODELAY, &opt, sizeof opt) == -1)
	printf("setsockopt TCP_NODELAY: %.100s", strerror(errno));
    
    //printf("fd %d setting SO_KEEPALIVE", fd);
    if (setsockopt(fd,  SOL_SOCKET, SO_KEEPALIVE, &opt, sizeof opt) == -1)
        printf("setsockopt SO_KEEPALIVE: %.100s", strerror(errno));
    
    opt = 3;
    //printf("fd %d setting TCP_KEEPCNT", fd);
    if (setsockopt(fd,  SOL_TCP, TCP_KEEPCNT, &opt, sizeof opt) == -1)
        printf("setsockopt TCP_KEEPCNT: %.100s", strerror(errno));
    
    opt = 45;
    //printf("fd %d setting TCP_KEEPIDLE", fd);
    if (setsockopt(fd,  SOL_TCP, TCP_KEEPIDLE, &opt, sizeof opt) == -1)
        printf("setsockopt TCP_KEEPIDLE: %.100s", strerror(errno));
    
    opt = 15;
    //printf("fd %d setting TCP_KEEPINTVL", fd);
    if (setsockopt(fd,  SOL_TCP, TCP_KEEPINTVL, &opt, sizeof opt) == -1)
        printf("setsockopt TCP_KEEPINTVL: %.100s", strerror(errno));
    
}

int recv_wrapper(int sock, int send_sock, char buf[], int size)
{
    int numBytes=0;
    int numBytesAll = 0;
    
    if (flag==1)
    {
	fprintf(stdout,"main:waiting to received %d bytes...\n", size);
    }
    while (numBytesAll < size)
    {
	numBytes = recv(sock, buf, size,  MSG_WAITALL);
	if (numBytes<0)
	{
	    fprintf(stdout,"main: Receive clientID error after %d bytes received, when it was expecting %d...\n", numBytesAll, size);
	    fprintf(stdout,"main: error description: %d\n", errno);
	    recovery(sock, send_sock);
	    //goto recover;
	}
	else if (numBytes==0)
	{
	    fprintf(stdout,"main: Receive clientID error after %d bytes received, when it was expecting %d...\n", numBytesAll, size);
	    fprintf(stdout,"main: error description: EOF\n");
	    recovery(sock, send_sock);
	    //goto recover;
	}
	else
	{            
	    if (flag == 1) 
		fprintf(stdout,"main: Received %d bytes...\n", numBytes);
	}
	numBytesAll += numBytes;
	if (numBytesAll == 3)
	{
	    if (strncasecmp(buf, "end", 3)==0)
	    {
		fprintf(stdout, "main: receive end notice, exit\n");
		recovery(sock, send_sock);
		//goto recover;
	    }
	}
    }
    
    if (flag==1)
    {
	fprintf(stdout,"main:just received %d bytes...\n", numBytesAll);
	fprintf(stdout,"main: recv_wrapper: %s\n", buf);
    }
    fflush(stdout);
}
