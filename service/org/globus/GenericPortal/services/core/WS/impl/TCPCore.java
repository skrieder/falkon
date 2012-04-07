
package org.globus.GenericPortal.services.core.WS.impl;

import org.globus.GenericPortal.stubs.GPService_instance.*;
import org.globus.GenericPortal.common.*;

import java.io.*;
import java.util.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.*;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TCPCore extends Thread
{

    static final Log logger = LogFactory.getLog(TCPCore.class);

    //public boolean DEBUG = false;
    public TCPWorkQueue queue;
    public TCPWorkQueue resultQueue;
    public int threadPoolSize;
    public int portDispatchWork;
    public int portGetResults;
    public Worker workerInstance = null;
    public int TCPCore_WORKER_PROC;

    public TCPCore(TCPWorkQueue queue, TCPWorkQueue resultQueue, int threadPoolSize, int portDispatchWork, int portGetResults, int TCPCore_WORKER_PROC)
    {
        System.out.println("TCPCore starting...");
        this.queue = queue;
        this.resultQueue = resultQueue;
        this.threadPoolSize = threadPoolSize;
        this.portDispatchWork = portDispatchWork;
        this.portGetResults = portGetResults;
        this.TCPCore_WORKER_PROC = TCPCore_WORKER_PROC;

        //this.DEBUG = logger.isDebugEnabled();
    }

    public static void main(String[] args)
    {
        TCPWorkQueue tQueue = new TCPWorkQueue();
        tQueue.run();


        TCPWorkQueue tResultQueue = new TCPWorkQueue();

        TCPCore tcpCore = new TCPCore(tQueue, tResultQueue, 12, 50000, 60000, 1);


        tcpCore.run();

    }

    String getExecutorName(Socket s)
    {
        try
        {

            String machID = new String((s.getInetAddress()).getHostAddress() + ":" + s.getPort());
            return machID.trim();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;

        }

    }


    public void run()
    {
        //Create hash map
        SocketMap map = new SocketMap();
        Map mapTasks = Collections.synchronizedMap(new HashMap());
        Map mapResources = Collections.synchronizedMap(new HashMap());

        SocketMap sockqueue = new SocketMap();
        ServerSocket s;
        Socket incoming;
        int i=0;

        try
        {
            //BufferedWriter out = new BufferedWriter(new FileWriter("/home/zhaozhang/falkon/service/logs/tcp-server_result.txt"));
            //BufferedWriter out = new BufferedWriter(new FileWriter("tcp-server_result.txt"));
            //KillListener killer = new KillListener(out);
            KillListener killer = new KillListener();
            killer.start();

            Reader readers = new Reader(sockqueue,map, portGetResults, mapTasks, mapResources);
            readers.start();

            //logger.debug("Initializing server port on " + portDispatchWork);
            s = new ServerSocket(portDispatchWork);


            //logger.debug("Server: Server started on port: " + portDispatchWork);

            //Create a set of worker threads
            //final int num_of_workers = threadPoolSize;          
            //Worker[] workers = new Worker[num_of_workers];
            //for (int count=0; count<num_of_workers; count++)
            //{
            //logger.debug("Server: count: "+count);
            //    workers[count] = new Worker(queue, sockqueue,map, mapTasks, mapResources, DEBUG);
            //    workers[count].start();
            //}
            workerInstance = new Worker(queue, sockqueue,map, mapTasks, mapResources);


            // wait for client connection 
            while (true)
            {

                //logger.debug("Server: waiting for incoming connections...");
                incoming = s.accept();
                //may want to set this in the config file...
                incoming.setSoTimeout(10000);
                incoming.setKeepAlive(true);
                incoming.setReuseAddress(true);
                incoming.setSoLinger(false,0);
                incoming.setTcpNoDelay(true);


                //logger.debug("Server: connection accepted!");

                //commented out to remove lifetime
                /*
                String lifetime = getlifetime(incoming);
                */
                //commented out to remove lifetime

                //logger.debug("Server: lifetime: "+lifetime);
                //TODO
                //move this to the thread that actually receives the first message!
                //{
                //logger.debug("Server: open a socket");

                for (int procId = 0; procId<this.TCPCore_WORKER_PROC;procId++)
                {
                

                String executorName = getExecutorName(incoming);

                if (executorName == null)
                {
                    executorName = "" + i;
                }
                else
                {
                    executorName = executorName.concat(":" + procId);

                }


                WorkerRegistration wr = new WorkerRegistration();
                wr.setMachID(executorName);
                //should be set to whatever the BGexec says...
                long wallTime = 0;
                wr.setWallTime(wallTime);

                //logger.debug("Server: registering worker id " + i + " with the Falkon dispatcher...");
                GPResourceHome.gpResourceWorker.registerWorker(wr);

                //GPResourceHome.commonState.register(executorName, false);
                logger.debug("registered " + executorName);

                //logger.debug("Server: registered " + i + "!");
                //}


                //MySocket sock = new MySocket(incoming, executorName , out);
                MySocket sock = new MySocket(incoming, executorName);
                i++;
                sockqueue.addSocket(executorName, sock); 
                //logger.debug("Server: add a socket");
                }

            }
        }
        catch (IOException e)
        {
            System.err.println("Server: connection:"+e);
        }
    }
    public static String getlifetime(Socket incoming){     
        try
        {
            String str;
            InputStreamReader in =new InputStreamReader(incoming.getInputStream());
            BufferedReader innet = new BufferedReader(in);
            str = innet.readLine();

            return str;
        }
        catch (IOException e)
        {
            System.err.println("Server: lifetime:"+e.toString());
            return "null";
        }
    }
}

class MySocket
{

    static final Log logger = LogFactory.getLog(MySocket.class);

    Socket sock;
    String id;
    int exitcode;
    long starttime;
    long endtime;
    String taskID;
    //BufferedWriter out;


    //MySocket(Socket asock, String aid, BufferedWriter out){
    MySocket(Socket asock, String aid){
        this.sock=asock;
        //id=aid.hashCode();
        this.id = aid.trim();
       // this.out=out;
    }

    Socket getsocket(){
        return this.sock;
    }

    String getID(){
        return id;
    }

    void setstart(long start){
        this.starttime=start;
    }

    void setend(long end){
        this.endtime=end;
    }
    void settaskID(String id){
        this.taskID=id.trim();
    }
    void setexitcode(int code){
        this.exitcode=code;
    }

     /*
    void output() throws IOException{
        //if (taskID.equals("0000000000")||taskID.equals("0000009999"))
        //logger.debug((taskID+" "+id+" "+starttime+" "+endtime+" "+(endtime-starttime)+"\n"));

        //out.write(taskID+" "+id+" "+starttime+" "+endtime+" "+(endtime-starttime)+"\n");
        //out.flush();
    }  */
}

class SocketMap
{

    static final Log logger = LogFactory.getLog(SocketMap.class);
    //Map map = new HashMap();
    Map map = Collections.synchronizedMap(new HashMap());

    public synchronized void addSocket(Object k, Object v){
        map.put(k, v);
        notify();
    }

    public synchronized Object getSocket(Object k) throws InterruptedException{
        Object result = (Object)("NULL");
        while (!map.containsKey(k))
        {
            //logger.debug("Socket map: No such key exists: "+k.toString());
            wait();
        }

        //logger.debug("Socket map: Find a socket");
        result = map.get(k);
        map.remove(k);

        return result;
    }
}

/*
class SocketQueue
{
    //LinkedList queue = new LinkedList();
    List queue = Collections.synchronizedList(new LinkedList());

    // Add work to the work queue
    public synchronized void addSocket(Object o) {
        queue.add(queue.size(), o);
        notify();
    }

    // Retrieve work from the work queue; block if the queue is empty
    public synchronized Object getSocket() throws InterruptedException {
        while (queue.isEmpty())
        {
            wait();
        }
        return queue.remove(0);//.removeFirst();
    }
}
*/
class TCPWorkQueue extends Thread
{

    static final Log logger = LogFactory.getLog(TCPWorkQueue.class);
    //LinkedList queue = new LinkedList();
    List queue = Collections.synchronizedList(new LinkedList());

    // Add work to the work queue
    public synchronized void addWork(Object o) {
        queue.add(queue.size(), o);
        //queue.addLast(o);
        notify();
    }

    // Retrieve work from the work queue; block if the queue is empty
    public synchronized Object getWork() throws InterruptedException {
        while (queue.isEmpty())
        {
            wait();
        }
        return queue.remove(0);//.removeFirst();.removeFirst();
    }

    public int size()
    {
        return queue.size();
    }

    // Retrieve work from the work queue; block if the queue is empty
    public boolean isEmpty() throws InterruptedException {
        return queue.isEmpty();
    }

    // Retrieve work from the work queue; block if the queue is empty
    public synchronized Object getWorkNoWait() throws InterruptedException {
        return queue.remove(0);//.removeFirst();.removeFirst();
    }


    public void run(){
        try
        {
            //BufferedReader in = new BufferedReader(new FileReader("/home/zhaozhang/grid/java/task.txt"));
            BufferedReader in = new BufferedReader(new FileReader("task.txt"));
            String str;
            while ((str = in.readLine()) != null)
            {
                this.addWork(str.trim());
            }
            in.close();
        }
        catch (IOException e)
        {
        }

    }
}


//class Worker extends Thread
class Worker
{

    static final Log logger = LogFactory.getLog(Worker.class);
    // Special end-of-stream marker. If a worker retrieves
    // an Integer that equals this marker, the worker will terminate.
    TCPWorkQueue q;
    SocketMap clientsocketqueue;
    SocketMap map;
    Map mapTasks;
    //long start;
    Map mapResources;

    //boolean DEBUG;

    Worker(TCPWorkQueue q, SocketMap sq, SocketMap mp, Map mapTasks, Map mapResources) {
        this.q = q;
        this.clientsocketqueue=sq;
        this.map=mp;
        this.mapTasks = mapTasks;
        this.mapResources = mapResources;
        //this.DEBUG = DEBUG;
    }


    public boolean sendTasks(String workerID, GPResource gpResource, GPResourceHome gpHome) 
    {

        PrintWriter out = null;
        String clientID = new String(workerID);
        logger.debug("sendTasks(String workerID, GPResource gpResource, GPResourceHome gpHome) starting...");
        try
        {
            Socket clientsocket;
            Object my;
            OutputStream outStream;
            InputStream inStream;

            //logger.debug("Server: Worker: starting...");
            //while (true)
            //{
            try
            {

                //logger.debug("Worker: worker starts");
                logger.debug("Server: Worker: getSocket() from queue...");
                my=clientsocketqueue.getSocket(workerID);

                logger.debug("Server: Worker: getsocket()...");
                //logger.debug("Worker: Successfully get a socket");
                clientsocket=((MySocket)my).getsocket();

                if (checkSocket(clientsocket))
                {
                    //socket is ok

                }
                else
                {
                    if (logger.isDebugEnabled()) System.out.println("detected a dead socket for " + workerID);

                    logger.debug("Worker: Worker " + workerID + " has a dead socket... sending the terminate command... ");
                    //send BGexec the end command...
                    //TODO
                    sendend(out, clientID, null, null, gpHome);
                    return false;

                }

                logger.debug("Server: Worker: getOutputStream() & getInputStream()...");
                outStream = clientsocket.getOutputStream();

                out = new PrintWriter(outStream,true);
                inStream = clientsocket.getInputStream();

                logger.debug("Server: Worker: PrintWriter()...");





                logger.debug("Server: Worker: getID()...");
                clientID = new String(((MySocket)my).getID());
                logger.debug("Server: Worker: clientID = " + clientID);

                //double check if OK?
                if (!GPResourceHome.commonState.roundRobin.exists(clientID))
                //if (GPResourceHome.commonState.setPendWorker(clientID) == false)
                {
                    logger.debug("Worker: Worker " + clientID + " is not registered... sending the terminate command... ");
                    //send BGexec the end command...
                    //TODO
                    sendend(out, clientID, null, clientsocket, gpHome);
                    return false;

                }

                else
                {



                    int limit=22-clientID.length();
                    for (int i=0; i<limit;i++)
                        clientID=clientID.concat("\0");

                    logger.debug("Server: Worker: write to socket...");

                    //out.write(clientID, 0, clientID.length());
                    out.write(clientID);
                    if (out.checkError())
                    {
                        if (logger.isDebugEnabled()) System.out.println("encountered an error with the PrintWriter to " + clientID);
                        //sendend(out, clientID, exec.getId(), clientsocket);
                        sendend(out, clientID, null, null, gpHome);
                        return false;
                        //sendend(out, clientID, null, null);
                    }


                    //clientID.trim();
                    clientID = new String(((MySocket)my).getID());
                    //out.flush();
                    //logger.debug("Server: Worker: getWork()...");


                    // Retrieve some work; block if the queue is empty
                    //GPResource gpResource = (GPResource)q.getWork();
                    //GPResourceHome.commonState.setPendWorker(clientID);
                    /*
                    GPResource gpResource = null;
                    while (gpResource == null)
                    {

                        while (q.isEmpty())
                        {
                            Thread.sleep(1000);
                            //logger.debug("Queue size is " + q.size());
                        }
                        //logger.debug("Queue size is " + q.size());
                        gpResource = (GPResource)q.getWorkNoWait();
                        if (gpResource == null) logger.debug("resource retrieved but null, probably there was nothing in the queue...");
                        else logger.debug("resource retrieved!");

                    } */

                    logger.debug("Server: Worker: initializing dispatchWork...");
                    WorkerWork sourceWorker = new WorkerWork();
                    sourceWorker.setMachID(clientID);
                    sourceWorker.setValid(true);
                    logger.debug("Server: Worker: dispatchWork()...");
                    WorkerWorkResponse wwr = gpResource.dispatchWork(sourceWorker);
                    logger.debug("Server: Worker: dispatchWork() completed!");


                    if (wwr.isValid())
                    {

                        Task tasks[] = wwr.getTasks();

                        if (tasks.length > 1)
                        {

                            logger.debug("Worker: Work received " + tasks.length + " tasks from Falkon, it should not have received more than 1... ignoring any jobs more than 1... must fix this, this should never happen!!!");

                        }

                        if (tasks != null)
                        {

                            Executable exec = tasks[0].getExecutable();
                            if (exec != null)
                            {

                                logger.debug("Server: mapTasks.put() and mapResources.put()...");
                                mapTasks.put(exec.getId(), tasks[0]);
                                mapResources.put(exec.getId(), gpResource);

                                try
                                {


                                    String str = new String(exec.getId() + "#" + exec.getCommand() + "#");
                                    String args[] = exec.getArguements();
                                    if (args != null)
                                    {
                                        logger.debug("args.length = " + args.length);

                                        for (int argsIndex = 0;argsIndex<args.length;argsIndex++)
                                        {
                                            str = str.concat(args[argsIndex] + " ");
                                        }
                                    }
                                    else
                                    {
                                        logger.debug("args is null");
                                        str = str.concat(" ");
                                    }

                                    str = str.concat("#");
                                    String env[] = exec.getEnvironment();
                                    if (env != null)
                                    {

                                        logger.debug("env.length = " + env.length);
                                        for (int argsIndex = 0;argsIndex<env.length;argsIndex++)
                                        {
                                            str = str.concat(env[argsIndex] + " ");
                                        }
                                    }
                                    else
                                    {
                                        logger.debug("env is null");

                                        str = str.concat(" ");
                                    }

                                    if (exec.getDirectory() != null)
                                    {
                                        logger.debug("dir is " + exec.getDirectory());
                                        str = str.concat("#" + exec.getDirectory() + "# "); // MW: put #-space at end of dir, == end of message.
                                    }
                                    else
                                    {
                                        logger.debug("dir is null");

                                        str = str.concat("# ");
                                    }



                                    logger.debug("Worker: preparing to send task: "+clientID);
                                    //String test= x.toString();
                                    //String str = test;
                                    String num=String.valueOf(str.length());
                                    logger.debug("Worker: preparing to send task: "+ str);
                                    logger.debug("before num:"+num);
                                    limit=10-num.length();
                                    for (int i=0; i<limit;i++)
                                        num=num.concat("\0");


                                    //out.write(num, 0, num.length());
                                    out.write(num);
                                    if (out.checkError())
                                    {
                                        if (logger.isDebugEnabled()) System.out.println("encountered an error with the PrintWriter to " + clientID);
                                        //sendend(out, clientID, exec.getId(), clientsocket);
                                        sendend(out, clientID, null, null, gpHome);
                                        return false;
                                        //sendend(out, clientID, null, null);
                                    }

                                    //out.flush();

                                    //out.write(str, 0, str.length());
                                    out.write(str);
                                    if (out.checkError())
                                    {
                                        if (logger.isDebugEnabled()) System.out.println("encountered an error with the PrintWriter to " + clientID);
                                        //sendend(out, clientID, exec.getId(), clientsocket);
                                        sendend(out, clientID, null, null, gpHome);
                                        return false;
                                        //sendend(out, clientID, null, null);
                                    }

                                    out.flush();
                                    if (logger.isDebugEnabled()) System.out.println("Sent task to worker " + workerID + ": " + num + " " + str);

                                    logger.debug("task sent and stream flushed!");

                                    //commented out the ACK

                                    logger.debug("waiting for ACK to ensure task was delivered, and that worker is up...");


                                    byte ACK[] = new byte[3];

                                    int numBytes = 0;
                                    int numBytesTotal = 0;
                                    int numErrors = 0;
                                    int maxNumErrors = 1;
                                    while (numBytesTotal < 3)
                                    {
                                        try
                                        {

                                            numBytes = 0;


                                            if (logger.isDebugEnabled()) System.out.println("waiting for ACK from worker " + workerID + ", received " + numBytesTotal + " bytes so far...");
                                            numBytes = inStream.read(ACK, 0, 3);
                                        }
                                        catch (Exception e)
                                        {
                                            if (logger.isDebugEnabled()) System.out.println("Error in receiving ACK for task from worker " + workerID + ", only " + numBytesTotal + " bytes received when expecting 3 bytes... ACK = " + ACK);
                                            if (logger.isDebugEnabled()) e.printStackTrace();
                                            numErrors++;
                                            if (numErrors >= maxNumErrors)
                                            {
                                                numBytes = -1;
                                            }
                                        }
                                        logger.debug("Server: Receive task ACK (" + numBytes + ")...");
                                        if (numBytes < 0)
                                        {
                                            logger.debug("Server: Receive task ACK failed: " + numBytes + " ... shuting down worker...");
                                            if (logger.isDebugEnabled()) System.out.println("Receive task ACK failed from " + workerID + ", received " + numBytesTotal + " bytes...");

                                            sendend(out, clientID, exec.getId(), clientsocket, gpHome);

                                            //logger.debug("Server: mapTasks.remove() and mapResources.remove()...");
                                            //mapTasks.remove(exec.getId());
                                            //mapResources.remove(exec.getId());


                                            return false;


                                        }

                                        numBytesTotal += numBytes;
                                        logger.debug("Server: Receive numBytesTotal = " + numBytesTotal + "");



                                    }


                                    //String sACK;


                                    String sACK = new String(ACK);
                                    if (sACK.trim().startsWith("OK"))
                                    {

                                        logger.debug("Server: Receive task ACK successful!");
                                        logger.debug("Server: Sent task succesful: "+str);
                                        if (logger.isDebugEnabled()) System.out.println("Receive task ACK from " + workerID + ", received " + numBytesTotal + " bytes...");
                                    }
                                    else
                                    {
                                        if (logger.isDebugEnabled()) System.out.println("Receive task ACK failed from " + workerID + ", received '" + sACK + "'...");
                                        logger.debug("Server: Receive task ACK failed: " + sACK + "... shuting down worker...");
                                        sendend(out, clientID, exec.getId(), clientsocket, gpHome);

                                        //logger.debug("Server: mapTasks.remove() and mapResources.remove()...");
                                        //mapTasks.remove(exec.getId());
                                        //mapResources.remove(exec.getId());

                                        return false;


                                    }

                                    //commented out the ACK





                                    //String sACK = new String(ACK);




                                    //if (clientsocket.isConnected() && !out.checkError() && sACK.startsWith("OK") && clientsocket.isBound() && !clientsocket.isClosed() && !clientsocket.isInputShutdown() && !clientsocket.isOutputShutdown())
                                    //if (clientsocket.isConnected() && !out.checkError() && clientsocket.isBound() && !clientsocket.isClosed() && !clientsocket.isInputShutdown() && !clientsocket.isOutputShutdown())
                                    //{
                                    //    logger.debug("Worker: Sent task succesful: "+str);
                                    //}



                                    //else
                                    //{
                                    //sendend(out, clientID);
                                    //    logger.debug("Worker: Sent task failed: "+str);
                                    //    logger.debug("ERROR: " + clientsocket.isConnected() + " " + out.checkError() + " " + sACK.startsWith("OK") + " " + clientsocket.isBound() + " " + clientsocket.isClosed() + " " + clientsocket.isInputShutdown() + " " + clientsocket.isOutputShutdown());
                                    //logger.debug("ERROR: " + clientsocket.isConnected() + " " + out.checkError() + " " + clientsocket.isBound() + " " + clientsocket.isClosed() + " " + clientsocket.isInputShutdown() + " " + clientsocket.isOutputShutdown());
                                    //    logger.debug("Worker: Deregistering worker: "+clientID);
                                    //    sendend(out,clientID);

                                    //    return false;
                                    //GPResourceHome.commonState.deRegister(clientID, false);
                                    //}
                                    //out.flush();
                                    ((MySocket)my).setstart(System.currentTimeMillis());


                                    //if (test=="end")
                                    //{
                                    //    break;
                                    //}

                                    //String res[] = str.split("#");


                                    logger.debug("Server: settaskID() and map.addSocket()...");

                                    ((MySocket)my).settaskID(exec.getId());
                                    map.addSocket(exec.getId(), my);
                                    //logger.debug("Worker: map size: "+map.map.size());

                                    //logger.debug("Server: mapTasks.put() and mapResources.put()...");
                                    //mapTasks.put(exec.getId(), tasks[0]);
                                    //mapResources.put(exec.getId(), gpResource);

                                    //double check that worker is set to busy
                                    return true;
                                }
                                catch (Exception e)
                                {
                                    e.printStackTrace();
                                    if (logger.isDebugEnabled()) System.out.println("Receive task ACK failed from " + workerID + " ...");
                                    sendend(out, clientID, exec.getId(), clientsocket, gpHome);

                                    //logger.debug("Server: mapTasks.remove() and mapResources.remove()...");
                                    //mapTasks.remove(exec.getId());
                                    //mapResources.remove(exec.getId());

                                    return false;
                                    //TODO: for all sendend(), it should also put the task back into falkon... or try a different socket...
                                    //GPResourceHome.commonState.deRegister(clientID, false);
                                }


                            }
                        }
                    }
                    else
                    {                           
                        //double check that this logic is correct
                        if (gpHome.commonState.setFreeWorker(workerID))
                        {
                            //all is ok
                        }
                        else
                        {
                            System.out.println("Failed to set worker " + workerID + " to free...");

                        }
                        
                        return true;
                        //logger.debug("Worker: Work received from Falkon is not valid.... ");
                    }
                }
            }
            catch (Exception e)
            {
                logger.debug("failed to send task... trying again...");
                if (logger.isDebugEnabled()) System.out.println("Receive task ACK failed from " + workerID + " ...");

                e.printStackTrace();
                sendend(out, clientID, null, null, gpHome);

                //logger.debug("Server: mapTasks.remove() and mapResources.remove()...");
                //mapTasks.put(exec.getId(), tasks[0]);
                //mapResources.put(exec.getId(), gpResource);

                return false;

            }
            //}

        }
        catch (Exception e)
        {
            if (logger.isDebugEnabled()) System.out.println("Receive task ACK failed from " + workerID + " ...");

            logger.debug("Worker: "+e.getMessage());
            e.printStackTrace();
            sendend(out, clientID, null, null, gpHome);
            return false;
        }

        if (logger.isDebugEnabled()) System.out.println("Receive task ACK failed from " + workerID + " ...");
        sendend(out, clientID, null, null, gpHome);
        return false;
    }


    /*
    public void run() 
    {
        try
        {
            Socket clientsocket;
            Object my;
            OutputStream outStream;
            InputStream inStream;

            //logger.debug("Server: Worker: starting...");
            while (true)
            {
                try
                {

                    //logger.debug("Worker: worker starts");
                    //logger.debug("Server: Worker: getSocket() from queue...");
                    my=clientsocketqueue.getSocket();

                    //logger.debug("Server: Worker: getsocket()...");
                    //logger.debug("Worker: Successfully get a socket");
                    clientsocket=((MySocket)my).getsocket();
                    //logger.debug("Server: Worker: getOutputStream() & getInputStream()...");
                    outStream = clientsocket.getOutputStream();
                    inStream = clientsocket.getInputStream();

                    //logger.debug("Server: Worker: PrintWriter()...");

                    PrintWriter out = new PrintWriter(outStream,false);



                    //logger.debug("Server: Worker: getID()...");
                    String clientID = new String(((MySocket)my).getID());
                    //logger.debug("Server: Worker: clientID = " + clientID);

                    if (!GPResourceHome.commonState.roundRobin.exists(clientID))
                    //if (GPResourceHome.commonState.setPendWorker(clientID) == false)
                    {
                        //logger.debug("Worker: Worker " + clientID + " is not registered... sending the terminate command... ");
                        //send BGexec the end command...
                        //TODO
                        sendend(out, clientID);

                    }

                    else
                    {



                        int limit=22-clientID.length();
                        for (int i=0; i<limit;i++)
                            clientID=clientID.concat("\0");

                        //logger.debug("Server: Worker: write to socket...");

                        //out.write(clientID, 0, clientID.length());
                        //out.write(clientID);

                        //clientID.trim();
                        clientID = new String(((MySocket)my).getID());
                        //out.flush();
                        //logger.debug("Server: Worker: getWork()...");


                        // Retrieve some work; block if the queue is empty
                        GPResource gpResource = (GPResource)q.getWork();
                        //GPResourceHome.commonState.setPendWorker(clientID);
                        
                        //GPResource gpResource = null;
                        //while (gpResource == null)
                        //{
                        //
                        //    while (q.isEmpty())
                        //    {
                        //        Thread.sleep(1000);
                        //        //logger.debug("Queue size is " + q.size());
                        //    }
                        //    //logger.debug("Queue size is " + q.size());
                        //    gpResource = (GPResource)q.getWorkNoWait();
                        //    if (gpResource == null) logger.debug("resource retrieved but null, probably there was nothing in the queue...");
                        //    else logger.debug("resource retrieved!");
                        //
                        //}

                        //logger.debug("Server: Worker: initializing dispatchWork...");
                        WorkerWork sourceWorker = new WorkerWork();
                        sourceWorker.setMachID(clientID);
                        sourceWorker.setValid(true);
                        //logger.debug("Server: Worker: dispatchWork()...");
                        WorkerWorkResponse wwr = gpResource.dispatchWork(sourceWorker);
                        //logger.debug("Server: Worker: dispatchWork() completed!");


                        if (wwr.isValid())
                        {

                            Task tasks[] = wwr.getTasks();

                            if (tasks.length > 1)
                            {

                                //logger.debug("Worker: Work received " + tasks.length + " tasks from Falkon, it should not have received more than 1... ignoring any jobs more than 1... must fix this, this should never happen!!!");

                            }

                            if (tasks != null)
                            {

                                Executable exec = tasks[0].getExecutable();
                                if (exec != null)
                                {
                                    try
                                    {


                                        String str = new String(exec.getId() + "#" + exec.getCommand() + "#");
                                        String args[] = exec.getArguements();
                                        if (args != null)
                                        {
                                            //logger.debug("args.length = " + args.length);

                                            for (int argsIndex = 0;argsIndex<args.length;argsIndex++)
                                            {
                                                str = str.concat(args[argsIndex] + " ");
                                            }
                                        } else
                                        {
                                            //logger.debug("args is null");
                                            str = str.concat(" ");
                                        }

                                        str = str.concat("#");
                                        String env[] = exec.getEnvironment();
                                        if (env != null)
                                        {

                                            //logger.debug("env.length = " + env.length);
                                            for (int argsIndex = 0;argsIndex<env.length;argsIndex++)
                                            {
                                                str = str.concat(env[argsIndex] + " ");
                                            }
                                        } else
                                        {
                                            //logger.debug("env is null");

                                            str = str.concat(" ");
                                        }

                                        if (exec.getDirectory() != null)
                                        {
                                            //logger.debug("dir is " + exec.getDirectory());
                                            str = str.concat("#" + exec.getDirectory());
                                        } else
                                        {
                                            //logger.debug("dir is null");

                                            str = str.concat("# ");
                                        }



                                        //logger.debug("Worker: preparing to send task: "+x.toString());
                                        //String test= x.toString();
                                        //String str = test;
                                        String num=String.valueOf(str.length());
                                        //logger.debug("Worker: preparing to send task: "+ str);
                                        //logger.debug("before num:"+num);
                                        limit=10-num.length();
                                        for (int i=0; i<limit;i++)
                                            num=num.concat("\0");


                                        //out.write(num, 0, num.length());
                                        //out.write(num);
                                        //out.flush();

                                        //out.write(str, 0, str.length());
                                        //out.write(str);
                                        //out.flush();

                                        //byte ACK[] = new byte[3];
                                        //inStream.read(ACK, 0, 3);

                                        //String sACK = new String(ACK);




                                        //if (clientsocket.isConnected() && !out.checkError() && sACK.startsWith("OK") && clientsocket.isBound() && !clientsocket.isClosed() && !clientsocket.isInputShutdown() && !clientsocket.isOutputShutdown())
                                        if (clientsocket.isConnected() && !out.checkError() && clientsocket.isBound() && !clientsocket.isClosed() && !clientsocket.isInputShutdown() && !clientsocket.isOutputShutdown())
                                        {
                                            logger.debug("Worker: Sent task succesful: "+str);
                                        }



                                        else
                                        {
                                            //sendend(out, clientID);
                                            logger.debug("Worker: Sent task failed: "+str);
                                            //logger.debug("ERROR: " + clientsocket.isConnected() + " " + out.checkError() + " " + sACK.startsWith("OK") + " " + clientsocket.isBound() + " " + clientsocket.isClosed() + " " + clientsocket.isInputShutdown() + " " + clientsocket.isOutputShutdown());
                                            logger.debug("ERROR: " + clientsocket.isConnected() + " " + out.checkError() + " " + clientsocket.isBound() + " " + clientsocket.isClosed() + " " + clientsocket.isInputShutdown() + " " + clientsocket.isOutputShutdown());
                                            logger.debug("Worker: Deregistering worker: "+clientID);
                                            sendend(out,clientID);
                                            //GPResourceHome.commonState.deRegister(clientID, false);
                                        }
                                        //out.flush();
                                        ((MySocket)my).setstart(System.currentTimeMillis());


                                        //if (test=="end")
                                        //{
                                        //    break;
                                        //}

                                        //String res[] = str.split("#");
                                        ((MySocket)my).settaskID(exec.getId());
                                        map.addSocket(exec.getId(), my);
                                        //logger.debug("Worker: map size: "+map.map.size());

                                        mapTasks.put(exec.getId(), tasks[0]);
                                        mapResources.put(exec.getId(), gpResource);
                                    } catch (Exception e)
                                    {
                                        sendend(out, clientID);
                                        //TODO: for all sendend(), it should also put the task back into falkon... or try a different socket...
                                        //GPResourceHome.commonState.deRegister(clientID, false);
                                    }


                                }
                            }
                        } else
                        {
                            //logger.debug("Worker: Work received from Falkon is not valid.... ");
                        }
                    }
                } catch (Exception e)
                {
                    logger.debug("failed to send task... trying again...");
                    e.printStackTrace();

                }
            }

        } catch (Exception e)
        {
            logger.debug("Worker: "+e.getMessage());
            e.printStackTrace();
        }
    }
    */

    public boolean checkSocket(Socket clientsocket)
    {
        try
        {
            if (clientsocket != null && clientsocket.isConnected() == true && clientsocket.isClosed() == false && clientsocket.isBound() == true)
            {
                return true;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;

    }

    public void sendend(PrintWriter out, String clientID, String execID, Socket clientsocket, GPResourceHome gpHome)
    {

        if (clientID != null && execID != null)
        {
            System.out.println("***** sendend(): " + clientID + " " + execID);
        }
        else if(clientID != null)
        {
            System.out.println("***** sendend(): " + clientID + " null");
        }
        else if(execID != null)
        {
            System.out.println("***** sendend(): null " + execID);
        }
        else
        {
            System.out.println("***** sendend(): null null");

        }

        

        try
        {

            logger.debug("sendend(PrintWriter out, String clientID, GPResourceHome gpHome) starting...: " + clientID.trim());
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }

        try
        {

            if (out != null)
            {

                if (checkSocket(clientsocket))
                {
                    out.write("end");
                    if (out.checkError())
                    {
                        if (logger.isDebugEnabled()) System.out.println("encountered an error with the PrintWriter to " + clientID);
                    }

                    out.flush();
                }
                else
                {
                    if (logger.isDebugEnabled()) System.out.println("detected a dead socket for " + clientID);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        try
        {

            if (execID != null)
            {

                logger.debug("Server: mapTasks.remove() and mapResources.remove()...");
                mapTasks.remove(execID);
                mapResources.remove(execID);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        try
        {

            if (gpHome != null && clientID != null)
            {
                WorkerDeRegistration wr = new WorkerDeRegistration();
                wr.setMachID(clientID.trim());
                gpHome.gpResourceWorker.deregisterWorker(wr);//.commonState.deRegister(clientID, false);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

class Reader extends Thread
{

    static final Log logger = LogFactory.getLog(Reader.class);
    //Selector selector;
    //ServerSocketChannel ssChannel1;
    SocketMap queue;
    SocketMap map;
    int port;
    Map mapTasks;
    Map mapResources;
    //boolean DEBUG;
    //SocketChannel sChannel;
    Reader(SocketMap clientsocketqueue, SocketMap map, int port, Map mapTasks, Map mapResources){

        this.queue=clientsocketqueue;
        this.map=map;
        this.port = port;
        this.mapResources = mapResources;
        this.mapTasks = mapTasks;
        //this.DEBUG = DEBUG;
    }

    public void run(){
        try
        {
            Selector selector = Selector.open();
            ServerSocketChannel ssChannel = ServerSocketChannel.open();
            ssChannel.configureBlocking(false);
            logger.debug("Binding socket to port " + port);
            //might want to set this from the config file
            ssChannel.socket().bind(new InetSocketAddress(port), 100);       
            logger.debug("Socket binded to port " + port);
            ssChannel.register(selector, SelectionKey.OP_ACCEPT);

            int numSelectCalls = 0;
            int numKeys = 0;
            while (true)
            {
                logger.debug("Reader: "+"waiting for event");
                // Wait for an event
                try
                {
                    numSelectCalls++;
                    numKeys = selector.select();
                    if (logger.isDebugEnabled()) System.out.println("number of select calls: " + numSelectCalls);
                }
                catch (Exception e)
                {
                    // Defensive code, making sure that we have a valid selector
                    if (selector.isOpen())
                    {

                        if (logger.isDebugEnabled()) System.out.println("number of select calls: " + numSelectCalls);
                    
                        continue;
                    }
                    else
                    {
                        if (logger.isDebugEnabled()) System.out.println("number of select calls: " + numSelectCalls);
                        logger.debug("Reader: selector"+e.toString());
                        e.printStackTrace(); 
                        break; 
                    } 


                    //logger.debug("Reader: selector"+e.toString());
                    //e.printStackTrace();
                }

                if (numKeys == 0) 
                    continue;

                Iterator it = selector.selectedKeys().iterator();

                // Process each key
                while (it.hasNext())
                {
                    // Get the selection key

                    SelectionKey selKey = (SelectionKey)it.next();      
                    // Remove it from the list to indicate that it is being processed

                    if(!selKey.isValid()) { 
               it.remove( ); 
               continue; 
           } 

                    // Check if it's a connection request
                    if (selKey.isAcceptable())
                    {
                        // Get channel with connection request
                        ServerSocketChannel ssChannel1 = (ServerSocketChannel)selKey.channel();                     
                        // Accept the connection request.
                        // If serverSocketChannel is blocking, this method blocks.
                        // The returned channel is in blocking mode.
                        try
                        {
                            SocketChannel sChannel = ssChannel1.accept();
                            sChannel.configureBlocking(false);
                            sChannel.register(selector,SelectionKey.OP_READ);
                        }
                        catch (IOException e)
                        {
                            logger.debug("Reader: accept "+e.toString());
                            e.printStackTrace();
                        }
                    }
                    logger.debug("Reader: "+"some one is connecting");
                    // If serverSocketChannel is non-blocking, sChannel may be null
                    if (selKey.isReadable())
                    {
                        SocketChannel sChannel = (SocketChannel)selKey.channel();
                        // Use the socket channel to communicate with the client
                        ByteBuffer buf = ByteBuffer.allocateDirect(1024);                              
                        // Fill the buffer with the bytes to write;
                        buf.clear();
                        try
                        {
                            //int numBytesTotal = 0;
                            int numBytesRead = sChannel.read(buf);
                            if (numBytesRead < 0)
                            {
                                // No more bytes can be read from the channel
                                logger.debug("Error in sChannel.read(buf): (" + numBytesRead + ") ... closing socket!");
                                sChannel.close();
                            }
                            else
                            {
                                // To read the bytes, flip the buffer
                                logger.debug("Received task result (" + numBytesRead + " bytes)!");
                                buf.flip();
                            }
                            Charset charset = Charset.forName("ISO-8859-1");
                            CharsetDecoder decoder = charset.newDecoder();
                            CharBuffer cbuf = decoder.decode(buf);
                            String str = cbuf.toString();

                            logger.debug("Server: received " + numBytesRead + " bytes: " + str);


                            String s[] = str.split("#");

                            Task task = (Task)mapTasks.remove(s[0].trim());
                            /*
                            if (task == null)
                            {
                                logger.debug("Server: failed to retrieve the task from the map, trying again in 10ms");
                                //try again
                                try
                                {
                                
                                Thread.sleep(10);
                                }
                                catch (Exception e)
                                {
                                    e.printStackTrace();
                                }

                                task = (Task)mapTasks.remove(s[0].trim());

                            }
                            */

                            logger.debug("Server: ...1");
                            if (task != null)
                            {
                                logger.debug("Server: ...2");

                                GPResource gpResource = (GPResource)mapResources.remove(s[0].trim());

                                if (gpResource != null)
                                {
                                    logger.debug("Server: ...3");



                                    logger.debug("Reader: received: "+s);                       
                                    try
                                    {
                                        logger.debug("Server: ...4");
                                        MySocket my = (MySocket)(map.getSocket(s[0].trim()));
                                        task.setExitCode(Integer.parseInt(s[1]));
                                        my.setexitcode(Integer.parseInt(s[1]));
                                        my.setend(System.currentTimeMillis());
                                        //my.output();
                                        queue.addSocket(my.getID(), my);

                                        logger.debug("Server: ...5");

                                        WorkerResult result = new WorkerResult();
                                        result.setValid(true);
                                        result.setMachID(my.getID());
                                        result.setNumTasks(1);
                                        result.setShutingDown(false);
                                        Task tasks[] = new Task[1];
                                        tasks[0] = task;
                                        result.setTasks(tasks);
                                        logger.debug("Server: ...5.5");
                                        logger.debug("Reader: gpResource.receiveResults(result)...");                       
                                        gpResource.receiveResults(result);
                                        logger.debug("Reader: gpResource.receiveResults(result)!");                     

                                        logger.debug("Server: ...6");

                                    }
                                    catch (InterruptedException e)
                                    {
                                        logger.debug("Reader: Interrupted "+e.toString());
                                        e.printStackTrace();
                                    }
                                }
                            }
                            else
                            {
                                logger.debug("Server: failed to retrieve the task from the map, double check why???");

                            }
                            logger.debug("Server: ...7");

                        }
                        catch (IOException e)
                        {
                            logger.debug("Reader: accept "+e.toString());
                            selKey.cancel();
                            e.printStackTrace();

                        }
                        //sChannel.close();
                        //logger.debug("Reader: Size of socketqueue: "+queue.size());
                        logger.debug("Reader: Size of SocketMap  : "+map.map.size());
                    }
                    it.remove();                
                    logger.debug("Server: ...8");
                }
            }

            if (logger.isDebugEnabled()) System.out.println("TCPCore completed the Reader thread, something is wrong... this should not have happened!");
        }
        catch (Exception e)
        {
            e.printStackTrace(); 
        }
    }

}
class KillListener extends Thread
{

    static final Log logger = LogFactory.getLog(KillListener.class);
    boolean DIPERF = false;


    String str = null;
    //BufferedWriter buf;

    //boolean DEBUG;

    //KillListener(BufferedWriter buf) 
    KillListener() 
    {
        //this.buf = buf;
        //this.DEBUG = DEBUG;

    }

    public void run()
    {
        try
        {
            logger.debug("Waiting for shutdownHook to be triggered...");
            Runtime.getRuntime().addShutdownHook(new Thread()
                                                 {
                                                     public void run(){
                                                         /*
                                                         try
                                                         {
                                                             //logger.debug("ShutdownHook triggered, closing down stuf...");
                                                             //buf.close();
                                                         }
                                                         catch (IOException e)
                                                         {
                                                             logger.debug("KILLER: "+e.toString());
                                                         }
                                                         */
                                                         logger.debug("ShutdownHook triggered successfully!");
                                                     }
                                                 });



        }
        catch (Exception e)
        {

            logger.debug("KILLER: Error: " + e);

            //return;
        }
    }

}

