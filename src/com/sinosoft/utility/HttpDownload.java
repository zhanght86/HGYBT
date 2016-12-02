package com.sinosoft.utility;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;
import java.util.Vector;

public class HttpDownload
{
  private Vector vRemoteHttpURL = new Vector();
  private Vector vLocalSaveFile = new Vector();

  public void setProxy(String sProxyHost, String sProxyPort)
  {
    if ((sProxyHost != null) && (!sProxyHost.trim().equals("")))
    {
      if ((sProxyPort == null) || (sProxyPort.trim().equals("")))
      {
        sProxyPort = "80";
      }
      System.getProperties().put("proxySet", "true");
      System.getProperties().put("proxyHost", sProxyHost);
      System.getProperties().put("proxyPort", sProxyPort);
    }
  }

  public boolean addOneTask(String sRemoteHttpURL, String sLocalSaveFile)
  {
    if ((sRemoteHttpURL == null) || (sRemoteHttpURL.trim().equals("")) || (!sRemoteHttpURL.trim().substring(0, 7).equalsIgnoreCase("http://")))
    {
      System.out.println("\t@> HttpDownload.addOneTask() : 源地址有误，不是一个有效的 http 地址！");
      return false;
    }

    if ((sLocalSaveFile == null) || (sLocalSaveFile.trim().equals("")))
    {
      sLocalSaveFile = "./" + sRemoteHttpURL.substring(sRemoteHttpURL.lastIndexOf("/") + 1);
    }
    this.vRemoteHttpURL.add(sRemoteHttpURL);
    this.vLocalSaveFile.add(sLocalSaveFile);

    return true;
  }

  public void clearAllTasks()
  {
    this.vRemoteHttpURL.clear();
    this.vLocalSaveFile.clear();
  }

  public boolean downLoadByList()
  {
    for (int i = 0; i < this.vRemoteHttpURL.size(); i++)
    {
      String sRemoteHttpURL = (String)this.vRemoteHttpURL.get(i);
      String sLocalSaveFile = (String)this.vLocalSaveFile.get(i);

      if (!saveToFile(sRemoteHttpURL, sLocalSaveFile))
      {
        System.out.println("\t@> HttpDownload.downLoadByList() : 下载远程资源时出现异常！");
      }

    }

    return true;
  }

  private boolean saveToFile(String sRemoteHttpURL, String sLocalSaveFile)
  {
    if ((sRemoteHttpURL == null) || (sRemoteHttpURL.trim().equals("")))
    {
      System.out.println("\t@> HttpDownload.saveToFile() : 要下载的远程资源地址不能为空！");
      return false;
    }

    try
    {
      URL tURL = new URL(sRemoteHttpURL);
      HttpURLConnection tHttpURLConnection = (HttpURLConnection)tURL.openConnection();
      tHttpURLConnection.connect();
      BufferedInputStream tBufferedInputStream = new BufferedInputStream(tHttpURLConnection.getInputStream());
      FileOutputStream tFileOutputStream = new FileOutputStream(sLocalSaveFile);

      int nBufferSize = 5120;
      byte[] bufContent = new byte[nBufferSize];
      int nContentSize = 0;
      while ((nContentSize = tBufferedInputStream.read(bufContent)) != -1)
      {
        tFileOutputStream.write(bufContent, 0, nContentSize);
      }

      tFileOutputStream.close();
      tBufferedInputStream.close();
      tHttpURLConnection.disconnect();

      tURL = null;
      tHttpURLConnection = null;
      tBufferedInputStream = null;
      tFileOutputStream = null;
    }
    catch (Exception ex)
    {
      System.out.println("\t@> HttpDownload.saveToFile() : 下载远程资源时出现异常！");
      System.out.println("\t   远程地址：" + sRemoteHttpURL);
      System.out.println("\t   本地路径：" + sLocalSaveFile);
      return false;
    }

    return true;
  }

  public static void main(String[] argv)
  {
    HttpDownload tHttpDownload = new HttpDownload();
    tHttpDownload.addOneTask("http://www.sinosoft.com.cn/TestDown.zip", "C:/TestDown.zip");
    tHttpDownload.downLoadByList();
    tHttpDownload = null;
  }
}