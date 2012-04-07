import java.io.*;

public class JSplit
{

    // Copies src file to dst file.
    // If the dst file does not exist, it is created
    public static void copy(String src, String dst) throws IOException {
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);

        // Transfer bytes from in to out
        byte[] buf = new byte[8192];
        int len;
        while ((len = in.read(buf)) > 0)
        {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }

    public static String intToString(int i, int width)
    {

        String str = new String();
        String temp = new String(""+i);

        if (temp.length() >= width)
        {
            return temp;
        }
        else
        {
            width = width - temp.length(); 
        }

        for (int j=0;j<width;j++)
        {
            str = str.concat("0");
        }
        str = str.concat(temp);

        return str;

    }


    public static void main(String args[])
    {
        if (args.length != 6)
        {
            System.out.println("usage: <input_file> <match_string> <output_dir> <num_dir> <template> <maxNumFiles>");
            System.out.println("usage: java JSplit million_database.mol2 '@<TRIPOS>MOLECULE' mol-1M-input 10000 1000000");
            System.exit(-1);
        }

        try
        {

            String inputFile = args[0];
            String matchString = args[1];
            String outputDir = args[2];
            int numDir = Integer.parseInt(args[3]);
            String templateFile = args[4];
            int maxNumFiles = Integer.parseInt(args[5]);


            String numDirString = new String("" + (numDir-1));

            int dirWidth = numDirString.length();


            String numFilesString = new String("" + (maxNumFiles-1));
            int fileWidth = numFilesString.length();


            BufferedReader in = new BufferedReader(new FileReader(inputFile));
            BufferedWriter out = null;
            BufferedWriter out2 = null;
            String str;
            int i = 0;
            int dir = 0;
            String curFileName = null;
            String curDirName = null;
            boolean firstTime = true;
            long tStart = System.currentTimeMillis();
            while ((str = in.readLine()) != null)
            {
                //NEW_FILE = false;
                if (str.startsWith(matchString))
                {
                    try
                    {
                        if (out != null)
                        {

                            out.close();
                        }
                        //NEW_FILE = true;

                        curFileName = intToString(i,fileWidth);

                        curDirName = intToString(dir,dirWidth);

                        if (firstTime)
                        {
                            // Create a directory; all non-existent ancestor directories are
    // automatically created
    boolean success = (new File(outputDir+"/"+curDirName)).mkdirs();
    if (!success) {
        System.out.println("Directory creation failed...");
        System.exit(-1);
        // Directory creation failed
    }


                        }

                        out = new BufferedWriter(new FileWriter(outputDir+"/"+curDirName+"/"+curFileName+".mol2"));

                        copy(templateFile, outputDir+"/"+curDirName+"/"+curFileName+".in");
                        out2 = new BufferedWriter(new FileWriter(outputDir+"/"+curDirName+"/"+curFileName+".in", true));
                        out2.write("ligand_atom_file                                             "+outputDir+"/"+curDirName+"/"+curFileName+".mol2\n");
                        out2.write("ligand_outfile_prefix                                        "+outputDir+"/"+curDirName+"/"+curFileName+"_lig_c1\n");

                        out2.close();


                        if (i%1000 == 0)
                        {
                            System.out.println((System.currentTimeMillis() - tStart)*1.0/1000.0 + " "+i);



                        }

                        i++;
                        dir++;

                        if (dir >= numDir)
                        {
                            dir = 0;
                            firstTime = false;
                        }


                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }


                }

                out.write(str+"\n");


            }
            out.close();
            in.close();
            System.out.println("Finished " + i + " splits in " + (System.currentTimeMillis() - tStart)*1.0/1000.0 + " seconds");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }





    }
}
