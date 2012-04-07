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

#define MAXLINE 4000

int flag=0;             //debug flag
FILE *fp;

    char ACK[]="OK\n";


/* disable nagle on socket */
void
set_sockopt(int fd)
{
	int opt;
	socklen_t optlen;

	optlen = sizeof opt;
	if (getsockopt(fd, IPPROTO_TCP, TCP_NODELAY, &opt, &optlen) == -1) {
		printf("getsockopt TCP_NODELAY: %.100s", strerror(errno));
		return;
	}
	if (opt == 1) {
		printf("fd %d is TCP_NODELAY", fd);
		return;
	}
	opt = 1;
	printf("fd %d setting TCP_NODELAY", fd);
	if (setsockopt(fd, IPPROTO_TCP, TCP_NODELAY, &opt, sizeof opt) == -1)
		printf("setsockopt TCP_NODELAY: %.100s", strerror(errno));

    printf("fd %d setting SO_KEEPALIVE", fd);
    if (setsockopt(fd,  SOL_SOCKET, SO_KEEPALIVE, &opt, sizeof opt) == -1)
        printf("setsockopt SO_KEEPALIVE: %.100s", strerror(errno));

    opt = 3;
    printf("fd %d setting TCP_KEEPCNT", fd);
    if (setsockopt(fd,  SOL_TCP, TCP_KEEPCNT, &opt, sizeof opt) == -1)
        printf("setsockopt TCP_KEEPCNT: %.100s", strerror(errno));

    opt = 45;
    printf("fd %d setting TCP_KEEPIDLE", fd);
    if (setsockopt(fd,  SOL_TCP, TCP_KEEPIDLE, &opt, sizeof opt) == -1)
        printf("setsockopt TCP_KEEPIDLE: %.100s", strerror(errno));

    opt = 15;
    printf("fd %d setting TCP_KEEPINTVL", fd);
    if (setsockopt(fd,  SOL_TCP, TCP_KEEPINTVL, &opt, sizeof opt) == -1)
        printf("setsockopt TCP_KEEPINTVL: %.100s", strerror(errno));


}


int ChildProcess();
struct list_el
{
    char *val;
    struct list_el * next;
};

typedef struct list_el item;
item * addtolist(char[], item *);
char * IntToStr(int);
int parseExec(char[]);
char* getTaskID(char*);
main(int argc, char**argv)
{
    int  sfd,ssfd;
    char buffer[MAXLINE];

    if (argc!=5)
    {
        fprintf(stdout,"usage: client <IP> <port> <port> <debug>");
        exit(1);
    }

    if (strcmp(argv[4],"-debug")==0)
        flag=1;
    //commented out to hopefully make it work with static compiling
    /*
    char hostname[100];
    struct hostent *h;

    gethostname(hostname, 100);
    fprintf(stdout,"hostname: %s\n", hostname);

    if ((h = gethostbyname(hostname))!=NULL)
    {
        fprintf(stdout,"ip address: %s\n", inet_ntoa(*((struct in_addr *)h->h_addr)));
    }
    */
    recover:
        fprintf(stdout,"con_server() to %s on port %s\n", argv[1], argv[2]);
    sfd=con_server(atoi(argv[2]), argv[1]);
    fprintf(stdout,"con_server() to %s on port %s\n", argv[1], argv[3]);
    ssfd=con_server(atoi(argv[3]), argv[1]);


    if (flag==1)
    fprintf(stdout,"connection established!\n");


    char num[10];
    char clientID[22];
    char filename[100];
    int result;
    char *msg;
    char *taskID;
    
    pid_t pid;
    int n;

    while (1)
    {
        memset(filename,'\0',sizeof(filename));
        memset(clientID,'\0',sizeof(clientID));
        memset(num, '\0', sizeof(num));
        memset(buffer,0,MAXLINE);
        if (flag==1)
        {
            fprintf(stdout,"main: receiving from server\n");
            fprintf(stdout,"main: sizeof clientID: %d\n", sizeof(clientID));
        }

        int numBytes=0;

        int numBytesAll = 0;


        if (flag==1)
        {
            fprintf(stdout,"main:waiting to received %d bytes...\n", sizeof(clientID));
        }

        while (numBytesAll < sizeof(clientID))
        {
            numBytes = read(sfd,clientID,sizeof(clientID));
            if (numBytes<0)
            {
                fprintf(stdout,"main: Receive clientID error after %d bytes received, when it was expecting %d...\n", numBytesAll, sizeof(clientID));
                fprintf(stdout,"main: error description: %d\n", errno);
                recovery(sfd, ssfd);
                goto recover;
            }
            else if (numBytes==0)
            {
                fprintf(stdout,"main: Receive clientID error after %d bytes received, when it was expecting %d...\n", numBytesAll, sizeof(clientID));
                fprintf(stdout,"main: error description: EOF\n");
                recovery(sfd, ssfd);
                goto recover;
            }
            else
            {
            
                if (flag == 1) fprintf(stdout,"main: Received %d bytes...\n", numBytes);
            }
            numBytesAll += numBytes;

            if (numBytesAll == 3)
            {
               if (strncasecmp(clientID, "end", 3)==0)
               {
               
                   fprintf(stdout, "main: receive end notice, exit\n");
                   //should shut down gracefully... closing all sockets...
                   exit(0);
               }
            }
        }

        if (flag==1)
        {
            fprintf(stdout,"main:just received %d bytes...\n", numBytesAll);
        }

        
        if (flag==1)
        {
            fprintf(stdout,"main: clientID: %s\n", clientID);
        }

        numBytesAll = 0;


        if (flag==1)
        {
            fprintf(stdout,"main:waiting to received %d bytes...\n", sizeof(num));
        }


        while (numBytesAll < sizeof(num))
        {
            numBytes = read(sfd,num,sizeof(num));
            if (numBytes<0)
            {
                fprintf(stdout,"main: Receive task size error after %d bytes received, when it was expecting %d...\n", numBytesAll, sizeof(num));
                fprintf(stdout,"main: error description: %d\n", errno);
                recovery(sfd, ssfd);
                goto recover;
            }
            else if (numBytes==0)
            {
                fprintf(stdout,"main: Receive task size error after %d bytes received, when it was expecting %d...\n", numBytesAll, sizeof(clientID));
                fprintf(stdout,"main: error description: EOF\n");
                recovery(sfd, ssfd);
                goto recover;
            }
            else
            {
            
                if (flag == 1) fprintf(stdout,"main: Received %d bytes...\n", numBytes);
            }
            numBytesAll += numBytes;
        }
        if (flag==1)
        {
            fprintf(stdout,"main:just received %d bytes...\n", numBytesAll);
        }



        int taskSize=atoi(num);
        msg=malloc(sizeof(char)*(taskSize+1));
        memset(msg,'\0',sizeof(msg));
        if (msg==NULL)
        {
            fprintf(stdout,"main: allocate memory failed\n");
            exit (1);
        }


        if (flag==1)
        {
            fprintf(stdout,"main:waiting to receive task description of %d bytes...\n", taskSize);
            //fprintf(fp,"main:received: %s\n", msg);
        }

        numBytesAll = 0;
        while (numBytesAll < taskSize)
        {
            numBytes = read(sfd,msg,taskSize);
            if (numBytes<0)
            {
                fprintf(stdout,"main: Receive task failed after %d bytes received, when it was expecting %d...\n", numBytesAll, taskSize);
                fprintf(stdout,"main: error description: %d\n", errno);

                recovery(sfd, ssfd);
                goto recover;
            }
            else if (numBytes==0)
            {
                fprintf(stdout,"main: Receive task failed after %d bytes received, when it was expecting %d...\n", numBytesAll, sizeof(clientID));
                fprintf(stdout,"main: error description: EOF\n");
                recovery(sfd, ssfd);
                goto recover;
            }
            else
            {
            
                if (flag == 1) fprintf(stdout,"main: Received %d bytes...\n", numBytes);
            }
            numBytesAll += numBytes;
        }


        if (flag==1)
        {
            fprintf(stdout,"main:jst received %d bytes...\n", numBytesAll);
        }


        if (flag==1)
        {
            fprintf(stdout,"main:received: %s\n", msg);
        }

	
	//commented out ACK
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
            if (flag == 1) fprintf(stdout,"main: Sent ACK %s successful (%d)\n", ACK, numBytes);

        }
	

        fflush(stdout);

        taskID = getTaskID(msg);
        int exitCode;
        exitCode = parseExec(msg);


        free(msg);
        numBytes = sendResults(taskID, exitCode, argv, ssfd, sfd);

        if (numBytes < 0)
        {
            fprintf(stdout,"main: Send results failed\n");
            //fprintf(stdout,"main: error description: EOF\n");
            recovery(sfd, ssfd);
            goto recover;
        }

    }
}


int con_server(int port, char* argv1)
{
    int i=1; 
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

    set_sockopt(sfd);
    //setsockopt(sfd, IPPROTO_TCP, TCP_CORK, &i, sizeof(i)); 


    if (flag==1)
    fprintf(stdout,"connecting\n");
    if ( connect( sfd, (struct sockaddr *) &serv_addr, sizeof(serv_addr)) < 0)
    {
        fprintf(stdout,"client: can't connect to server\n");
        exit(2);
    }

    if (flag==1)
    fprintf(stdout,"connected!\n");
    set_sockopt(sfd);
    //setsockopt(sfd, IPPROTO_TCP, TCP_CORK, &i, sizeof(i)); 
    return sfd;
}

int sendResults(char* str, int exitCode, char** argv, int ssfd, int sfd)
{
    int num;
    //int exitCode = 0;
    char* taskID=strcat(str,"#");
    if (exitCode==0)
        taskID=strcat(taskID, "0");
    else
        taskID=strcat(taskID, IntToStr(exitCode));
    if (flag==1)
        //fprintf(fp, "sendResults: %s\n",taskID);
        fprintf(stdout, "sendResults: %s\n",taskID);
        
    //fprintf(stdout, "sending result for task i2d %s with exit code %d\n",taskID, exitCode);

    num=send(ssfd, taskID,strlen(taskID),0);
    if (num < strlen(taskID))
    {
        fprintf(stdout, "sendResults error: %s, sent nly %d of the %d bytes...\n",taskID, num, strlen(taskID));


    }
    else
    {
    
        if (flag == 1) fprintf(stdout, "sendResults OK: %s, sent %d bytes!\n",taskID, num);
    }

    //wait for ack

    /*
    int numBytesAll = 0;
    int numBytes = 0;
    char bufACK[3];

    while (numBytesAll < sizeof(bufACK))
    {
        numBytes = read(sfd,bufACK,sizeof(bufACK));
        if (numBytes<0)
        {
            fprintf(stdout,"main: Receive task result ACK error after %d bytes received, when it was expecting %d...\n", numBytesAll, sizeof(bufACK));
            fprintf(stdout,"main: error description: %d\n", errno);
            recovery(sfd, ssfd);
            goto recover;
        }
        else if (numBytes==0)
        {
            fprintf(stdout,"main: Receive task size error after %d bytes received, when it was expecting %d...\n", numBytesAll, sizeof(bufACK));
            fprintf(stdout,"main: error description: EOF\n");
            recovery(sfd, ssfd);
            goto recover;
        }
        else
            fprintf(stdout,"main: Received %d bytes...\n", numBytes);

        numBytesAll += numBytes;
    }

    if (ACK[0] == bufACK[0] && ACK[1] == bufACK[1])
    {
        fprintf(stdout, "sendResults OK and ACKed: %s!\n",bufACK);
    }
    else
        fprintf(stdout, "sendResults sent OK, but ACK failed... %s!\n",bufACK);

                      */

    //fflush(sfd);
    return num;
    //fprintf(stdout, "sent %d bytes!\n",num);
}

char* getTaskID(char *str)
{
    char *copy = malloc(sizeof(char)*(strlen(str)+1));
    strcpy(copy, str);
    char *taskID;
    char *delims ="#";

    taskID=strtok(copy,delims);

    //fprintf(stdout, "task id %s \n",taskID);
    return taskID;
}

   struct timeval execStartTime;
   struct timeval execEndTime;


int parseExec(char str[])
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
    //fprintf(stdout,"parse: %s\n",str);

    char *preargs;
    char *preenvs;
    item *arg, *env;
    char *pt;
    char *delims ="#";

    char *back,p;

    back=strdup(str);
    if (flag==1)
        fprintf(stdout, "back: %s\n", back);
    taskID=strtok(back,delims);
    //fprintf(stdout,"%s\n",taskID);
    command=strtok(NULL,delims);
    if (flag==1)
        fprintf(stdout,"command: %s\n",command);
    //link list thing
    preargs=strtok(NULL,delims);
    if (flag==1)
        fprintf(stdout,"args: %s\n",preargs);

    preenvs=strtok(NULL,delims);
    directory=strtok(NULL,delims);
    if (flag==1)
        fprintf(stdout,"directory: %s\n",directory);

    if(strncmp(directory, "/", 1)==0)
    {
	printf("changing dir\n");
	chdir(directory);
    }
    strcat(execute, command);
    strcat(execute, " ");
    strcat(execute, preargs);
    if (flag==1)
        fprintf(stdout,"execute: %s\n",execute);
    //exit(1);

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
    int exitCode;
    //pid_t pid;
    //pid=fork();
    //if(pid==0)
    //exitCode=execve(command, argss, envss);
    //fprintf(stdout, "sleep over\n");

    gettimeofday(&execStartTime,NULL);

    //if (flag==1)
    //{

   fprintf(stdout, "%d.%d: executing taskID %s %s...", execStartTime.tv_sec,  execStartTime.tv_usec, taskID, execute); 
   fflush(stdout);

        //fprintf(stdout,"execute: %s\n",execute);
    //}
    exitCode = system(execute);
    gettimeofday(&execEndTime,NULL);

    //if (flag==1)
    //{

        float timeElapsedMS = ( execEndTime.tv_sec - execStartTime.tv_sec)*1000.0 + execEndTime.tv_usec/1000.0 - execStartTime.tv_usec/1000.0;
   fprintf(stdout, " completed with exit code %d in %f ms!\n",exitCode, timeElapsedMS); 
   fflush(stdout);

        //fprintf(stdout,"execute: %s\n",execute);
    //}


    //fprintf(stdout, "execl completed %d\n", exitCode); 
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
}
char * IntToStr(int Number)
{
    char ch,*str,*right,*left;
    unsigned int Value;
    str = (char *)malloc(12*sizeof(char));
    left = right = str;
    if (Number < 0)
    {
        Value = -Number;
        *str = '-';
        left++,right++;
    } else
        Value = (unsigned)Number;
    while (Value)
    {
        *right = (Value%10)+0x30;
        Value = Value/10;
        right++;
    }
    *right-- = '\0';
    while (right > left)
    {
        ch = *left;
        *left++ = *right;
        *right-- = ch;
    }
    return str;
} 



