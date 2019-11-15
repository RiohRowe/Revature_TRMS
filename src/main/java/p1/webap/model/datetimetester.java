package p1.webap.model;

import java.sql.Date;
import java.sql.Time;


class A
{
	public boolean b;
}

public class datetimetester 
{
	public static void maketrue(A c)
	{
		c.b = true;
	}
	public static void main(String[] args) 
	{
		A a = new A();
		a.b=false;
		System.out.println(a.b);
		maketrue(a);
		System.out.println(a.b);	
	}
}
