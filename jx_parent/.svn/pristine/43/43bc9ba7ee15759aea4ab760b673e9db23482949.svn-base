package cn.itcast.jx.util;

import org.apache.shiro.crypto.hash.Md5Hash;

public class Encrypt {
	public static String md5(String password,String salt){
		/**
		 * 第一个参数：密码
		 * 第二个参数：盐打乱的次数

		 * 第三个参数：		 */

		return new Md5Hash(password,salt,2).toString();
	}
	
	
	public static void main(String[] args){
		//21232f297a57a5a743894a0e4a801fc3
		//b5d597aef738e9088766cc9989a63e10
		System.out.println(new Md5Hash("admin","admin",2).toString()); 
	}
}
