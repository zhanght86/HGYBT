package com.sinosoft.midplat.spdb.bat;

import java.io.File;
import java.io.FileFilter;

import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;

public class MyFileFilter implements FileFilter, FTPFileFilter
{
	private int mFuncFlag = 0;

	public MyFileFilter(int cFuncFlag)
	{
		mFuncFlag = cFuncFlag;
	}

	@Override
	public boolean accept(File pathname)
	{
		if (mFuncFlag == 3007)
		{
			if (pathname.getName().startsWith("UBS"))
			{
				return true;
			}
			return false;
		}
		else if (mFuncFlag == 3008)
		{
			if (pathname.getName().startsWith("UBCS"))
			{
				return true;
			}
			return false;
		}
		else if (mFuncFlag == 3012)
		{
			if (pathname.getName().startsWith("UZLBL"))
			{
				return true;
			}
			return false;
		}
		else if (mFuncFlag == 3013)
		{
			if (pathname.getName().startsWith("UBP"))
			{
				return true;
			}
			return false;
		}
		else if (mFuncFlag == 3015)
		{
			if (pathname.getName().startsWith("UBD"))
			{
				return true;
			}
			return false;
		}
		else if (mFuncFlag == 3017)
		{
			if (pathname.getName().startsWith("FINISH"))
			{
				return true;
			}
			return false;
		}
		else if (mFuncFlag == 3011)
		{
			if (pathname.getName().startsWith("DBCS"))
			{
				return true;
			}
			return false;
		}
		if (mFuncFlag == 3010)
		{
			if (pathname.getName().startsWith("DBS"))
			{
				return true;
			}
			return false;
		}
		else if (mFuncFlag == 3009)
		{
			if (pathname.getName().startsWith("DZLBL"))
			{
				return true;
			}
			return false;
		}
		else if (mFuncFlag == 3014)
		{
			if (pathname.getName().startsWith("DBP"))
			{
				return true;
			}
			return false;
		}
		else if (mFuncFlag == 3016)
		{
			if (pathname.getName().startsWith("DBD"))
			{
				return true;
			}
			return false;
		}
		else
			return false;
	}

	@Override
	public boolean accept(FTPFile ftpFile)
	{
		
		if (mFuncFlag == 3010)
		{
			if (ftpFile.getName().startsWith("DBS"))
			{
				return true;
			}
			return false;
		}
		else if (mFuncFlag == 3012)
		{
			if (ftpFile.getName().startsWith("UZLBL"))
			{
				return true;
			}
			return false;
		}
		else if (mFuncFlag == 3011)
		{
			if (ftpFile.getName().startsWith("DBCS"))
			{
				return true;
			}
			return false;
		}
		else if (mFuncFlag == 3013)
		{
			if (ftpFile.getName().startsWith("UBP"))
			{
				return true;
			}
			return false;
		}
		else if (mFuncFlag == 3009)
		{
			if (ftpFile.getName().startsWith("DZLBL"))
			{
				return true;
			}
			return false;
		}
		else if (mFuncFlag == 3014)
		{
			if (ftpFile.getName().startsWith("DBP"))
			{
				return true;
			}
			return false;
		}
		else if (mFuncFlag == 3016)
		{
			if (ftpFile.getName().startsWith("DBD"))
			{
				return true;
			}
			return false;
		}

		else
			return false;
	}
}
