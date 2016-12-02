package test;

public class Problem
{
	public static void main(String[] args) 
	{

		DiningRoom diningRoom=new DiningRoom("student1","student2","student3","cook");
		diningRoom.student1.setName("student1");
		diningRoom.student2.setName("student2");
		diningRoom.student3.setName("student3");
		diningRoom.cook.setName("cook");
		diningRoom.student1.start();
		diningRoom.student2.start();
		diningRoom.student3.start();
		diningRoom.cook.start();
	}
}
class DiningRoom implements Runnable 
	{
		Thread student1,student2,student3,cook;
	
	
		int food=0,i=0;
		String stu1,stu2,stu3,co;
		public DiningRoom(String stu1,String stu2,String stu3,String co)
		{
			this.stu1=stu1;
			this.stu2=stu2;
			this.stu3=stu3;
			this.co=co;
			student2=new Thread(this);
			student1=new Thread(this);
			student3=new Thread(this);
			cook=new Thread(this);
		}
		
		public void run()
		{
			makeOrEat();
		}
		
		public synchronized void makeOrEat()
		{
			Thread thread=Thread.currentThread();
		if(thread==student1)
		{
			for(;i<=10;)
			{
				if(food==0)
				{
					try
					{
					System.out.println("学生:"+thread.getName()+"，现在没有馒头");
					cook.join();
					break;
					}
				catch (InterruptedException e)
				{}
				}//if
			else
			{
				try
				{
					food--;
					System.out.println("学生："+thread.getName()+"吃了一个,现在还有"+food+"个馒头");
					// i--;
					Thread.sleep(1000);
				}
				catch (InterruptedException e)
				{
					
				}
			}//else
		}//for
		return;
		}
		if(thread==student2)
		{
			for(;i<=10;)
			{
				if(food==0)
					{
					try
						{
						System.out.println("学生:"+thread.getName()+"，现在没有馒头");
						cook.join();
						break;
						}
					catch (InterruptedException e)
					{
						
					}
				}//if
				else
				{
					try
					{
						food--;
						System.out.println("学生："+thread.getName()+"吃了一个,现在还有"+food+"个馒头");
						// i--;
						Thread.sleep(1000);
					}
					catch (InterruptedException e)
					{
						
					}
				}//else
			}//for
			return;
		}
		if(thread==student3)
			{
			while(true)// for(;i<=10;)
				{
				if(food==0)
					{
					try
						{
						System.out.println("学生:"+thread.getName()+"，现在没有馒头");
						cook.join();
						break;
						}
					catch (InterruptedException e)
					{
						
					}
					}//if
					else
					{
						try
						{
							food--;
							System.out.println("学生："+thread.getName()+"吃了一个,现在还有"+food+"个馒头");
							//i--;
							Thread.sleep(1000);
						}
						catch (InterruptedException e)
						{
							
						}
					}//else
				}//for
			return;
			}
		if(thread==cook)
		{
			
			for(;i<=30;)
			{
				if(food<10)
				{
					try
						{
						notifyAll();
						food++;
						System.out.println(thread.getName()+"做了一个馒头,现在有"+food+"个馒头");
						i++;
						Thread.sleep(500);
						}
					catch (InterruptedException e)
					{
						
					}
				}
				else if(food>=10)
				{
					try
					{
						System.out.println("现在篮子满了");
						wait();//thread.sleep(500);
						//break;
					}
					catch (InterruptedException e)
					{
						
					}
				}//else
			}//for
			//if(i>30) return;
		}//else if
	}
}
