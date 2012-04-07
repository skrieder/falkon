package org.globus.GenericPortal.common;


public class DiskSpace2
{
	private static final String AVAILABLE = "Available";

	//this should be passed in...
	public boolean DEBUG = false;

	public MonitorDiskSpace2 mds = null;

	private long safetyMarginMB = 0;
	public boolean shuttingDown = false;

	public long availableSpaceKB = 0;
	public String filePath = null;

	private final String DF_COMMAND = "df -P ";

	public DiskSpace2(long safetyMarginMB, String filePath)
	{
		this.DEBUG = false;
		this.safetyMarginMB = safetyMarginMB;
		this.filePath = filePath;

		setMetricLong();
		mds = new MonitorDiskSpace2(this, 10000);
		mds.start();
	}

	public DiskSpace2(long safetyMarginMB, String filePath, boolean DEBUG)
	{
		this.DEBUG = DEBUG;
		this.safetyMarginMB = safetyMarginMB;
		this.filePath = filePath;

		setMetricLong();
		mds = new MonitorDiskSpace2(this, 10000);
		mds.start();
	}

	public DiskSpace2(long safetyMarginMB, boolean DEBUG)
	{
		this.DEBUG = DEBUG;
		this.safetyMarginMB = safetyMarginMB;
	}
	
	public DiskSpace2(long safetyMarginMB)
	{
		this.DEBUG = false;
		this.safetyMarginMB = safetyMarginMB;
	}

	public void endMonitor()
	{
		shuttingDown = true;
	}

	private long getMetricLong(String s, int INDEX)
	{

		String token[] = s.split(" ");
		if (token.length > INDEX)
		{

			int index = 0;
			for (int i=0;i<token.length;i++)
			{
				if (token[i].length() > 0)
				{

					if (index == INDEX)
					{
						return Long.parseLong(token[i]);
					}
					index++;

				}
			}
		}
		return 0L;
	}

	private int getAvailableIndex(String s)
	{
		String token[] = s.split(" ");

		int index = 0;
		for (int i=0;i<token.length;i++)
		{
			if (token[i].length() > 0)
			{

				if (StringUtil.contains(token[i], AVAILABLE))
				{
					return index;

				}
				index++;

			}
		}
		return -1;
	}

	public long getAvailableSpaceMB(String path)
	{
		return getMetricLong(path)/1024;
	}

	public long getAvailableSpaceMB()
	{
		return getMetricLong()/1024;
	}

	public long getUsableSpaceMB(String path)
	{
		return Math.max(getMetricLong(path)/1024 - safetyMarginMB, 0);
	}

	public long getUsableSpaceMB()
	{
		return Math.max(getMetricLong()/1024 - safetyMarginMB, 0);
	}

	private long getMetricLong()
	{
		return availableSpaceKB;
	}

	private long getMetricLong(String path)
	{
		long tAvailableSpaceKB = 0;
		MyProcess myChild = new MyProcess(DEBUG, true);
		if (path != null)
		{

			try
			{
				String commands = new String(DF_COMMAND + path);
				int exitCode = myChild.exec(commands);

				if (exitCode == 0)
				{

					String buf3 = myChild.getStreamOut();

					if (DEBUG) System.out.println("setMetricLong(): " + buf3);

					String token[] = buf3.split("\n");

					long availableSpace = 0L;
					if (token.length >= 2)
					{
						int availableIndex = getAvailableIndex(token[0]);

						if (availableIndex >= 0)
						{
							availableSpace = getMetricLong(token[1],availableIndex); 

						}
					}
					else
					{
						if (DEBUG) System.out.println("setMetricLong(): not enough tokens, perhaps different OS: token.length = " + token.length); 
					}

					tAvailableSpaceKB = availableSpace;
					if (DEBUG) System.out.println(System.currentTimeMillis()+": setMetricLong(): availableSpaceKB = " + tAvailableSpaceKB); 

				}
				else
				{
					if (DEBUG) System.out.println("failed to execute 'df': exit code " + exitCode);
				}

			} catch (Exception e)
			{
				System.out.println("Error: " + e);
				e.printStackTrace();
			}
		}
		else
		{
			if (DEBUG)
			{
				System.out.println("filePath is null, the DiskSpace2 was not inialized properly...");
			}

		}

		myChild.freeState();
		return tAvailableSpaceKB;
	}

	public void setMetricLong()
	{
		availableSpaceKB = getMetricLong(filePath);
	}

	public void closeStreams(Process p)
	{
		try
		{
			p.getInputStream().close();
			p.getOutputStream().close();
			p.getErrorStream().close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
}

class MonitorDiskSpace2 extends Thread
{
	DiskSpace2 ds = null;
	long poll_ms = 10000;

	public MonitorDiskSpace2(DiskSpace2 ds, long poll_ms)
	{
		this.ds = ds;
		this.poll_ms = poll_ms;
	}

	public void run()
	{
		while (!ds.shuttingDown)
		{
			ds.setMetricLong();

			try
			{
				if (ds.DEBUG) System.out.println("sleeping " + this.poll_ms + " before the next check for the disk space usage...");
				Thread.sleep(this.poll_ms);
			}
			catch (Exception e)
			{

			}

		}
		if (ds.DEBUG) System.out.println("shutting down the monitor disk thread...");
	}
}
