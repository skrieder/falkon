package org.globus.GenericPortal.clients.GPService_instance;

import java.util.*;
import java.io.*;
import java.lang.*;
//import impl.*;
import java.util.logging.*;

import org.globus.GenericPortal.clients.GPService_instance.*;

public class Console extends Thread 
{
    Logger logger = Logger.getLogger("impl.Console");

    private UserRun user = null;
    String UserID = null;
    private Random rand = new Random();


    public Console(UserRun user)
    {
        this.user = user;
        this.UserID = new String("Console");
    }

    public void run()
    {


        System.out.println("Welcome to the GenericClient v0.1 console!");


        try
        {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String str = "";
            while (str != null)
            {
                System.out.print("Type in your command to execute: ");
                str = in.readLine();
                if (str != null)
                {
                    if (str.contentEquals(new StringBuffer("exit")))
                    {
                        break;
                    } else if (str.startsWith("file "))
                    {
                        String tokens[] = str.split(" ");
                        if (tokens.length == 2)
                        {
                            System.out.println("Processing executables from " + tokens[1] + "... ");
                            long sTime = System.currentTimeMillis();

                            int numExecs = user.readFileDescription(tokens[1]);
                            user.jobTime.start();

                            /*
                            System.out.println("Reading file: " + tokens[1] + "... ");
                            int numExecs = 0;
                            String execs[] = null;
                            try
                            {
                                numExecs = user.numTuples(tokens[1]);
                                execs = new String[numExecs];
                                int execsIndex = 0;

                                BufferedReader in2 = new BufferedReader(new FileReader(tokens[1]));
                                String exec;
                                while ((exec = in2.readLine()) != null)
                                {
                                    execs[execsIndex++] = new String(exec);
                                }
                                in2.close();
                            } catch (Exception e)
                            {
                                System.out.println("Error in reading file: " + tokens[1] + "... ");

                            }
                            */


                            if (numExecs < 1)
                            {
                                System.out.println("There were no commands to process... probably there was nothing in the file...");
                                //System.exit(0);
                            } else
                            {

                                int resultsReceived = 0;
                                while (resultsReceived < numExecs)
                                {
                                    try
                                    {
                                    
                                        String result = (String)user.resultQ.remove();


                                        if (user.DEBUG)
                                        {

                                            if (result != null)
                                            {
                                                System.out.println(resultsReceived + ": " + result);
                                                resultsReceived++;

                                            } else
                                                System.out.println("null");
                                        }
                                    }
                                    catch (Exception e)
                                    {
                                        System.out.println("Error: " + e);
                                        e.printStackTrace();


                                    }



                                }
                            } 

                            long eTime = System.currentTimeMillis();
                            user.jobTime.stop();
                            //System.out.println(numExecs + " executables completed in " + (eTime - sTime) + " ms");
                            System.out.println(numExecs + " executables completed in " + user.jobTime.getElapsedTime() + " ms");
                            


                        } else
                        {

                            System.out.println("File name not specified... try again...");
                            break;
                        }


                    } else
                    {



                        System.out.println("Processing command: " + str + "... ");
                        long sTime = System.currentTimeMillis();
                        user.jobTime.start();
                        int numExecs = 1;
                        
                        //String execs[] = new String[1];
                        //execs[0] = new String(str);
                        //String exec = new String(str);
                        //String result[] = user.doExecutables(execs);

                        user.execQ.insert(str);

                        //String result = null;
                        try
                        {

                            String result = (String)user.resultQ.remove();
                            long eTime = System.currentTimeMillis();
                            user.jobTime.stop();
                            System.out.println(numExecs + " executables completed in " + user.jobTime.getElapsedTime() + " ms");
                            //System.out.print("Result: ");
                            if (result != null)
                            {

                                System.out.println(result);
                            }

                            else
                                System.out.println("null");

                        }
                        catch (Exception e)
                        {
                            System.out.println("Error: " + e);
                            e.printStackTrace();


                        }



                    }

                } else
                {
                    System.out.println("Unsupported command '" + str + "', please try again...");
                }
            }
        } catch (IOException e)
        {
            logger.info("Error: " + e);
        }




    }

}
