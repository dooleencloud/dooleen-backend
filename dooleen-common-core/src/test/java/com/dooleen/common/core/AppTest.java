package com.dooleen.common.core;

import com.dooleen.common.core.utils.DooleenMD5Util;
import com.dooleen.common.core.utils.GenerateNo;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
	public static void main(String[] agrs) throws Exception
	{
		String bodyMd5 = "GatewayMD5:"+DooleenMD5Util.md5("888813110", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkb29sZWVuIjoiaHR0cHM6Ly93d3cuZG9vbGVlbi5jb20vIiwidXNlcl9uYW1lIjoiODg4ODEzMTEwIiwic2NvcGUiOlsiYWxsIl0sImV4cCI6MTU2MzUzNDkyMSwiYXV0aG9yaXRpZXMiOlsicGFyZW50Il0sImp0aSI6IjJkZmFkMDYxLTEyNmUtNGExNi05MGNjLWQzMzgzODk0ZTU4NSIsImNsaWVudF9pZCI6ImRvb2xlZW4ifQ.Esew9P2C5R0RDYPd8lU0-AMi5IFCUXOAEa6NiMXCL34");
		System.out.println(bodyMd5);
	}
}