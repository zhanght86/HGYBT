package com.newsky.xiangwei;

public class MyDigest
{

	public void testMyDigest()
	{
		try
		{
			String myinfo = "haha";
			java.security.MessageDigest alg = java.security.MessageDigest.getInstance("SHA-1");
			alg.update(myinfo.getBytes());
			byte[] digesta = alg.digest();
			System.out.println("本信息摘要是：" + digesta);
			
		}catch( Exception e)
		{
			e.printStackTrace();
		}
		
	}
	/**
	 * @param args
	 * @param 
	 * @return 	
	 */
	public static void main(String[] args)
	{
		System.out.println("本信息摘要是：" );
		MyDigest md = new MyDigest();
		md.testMyDigest();
	}

}
