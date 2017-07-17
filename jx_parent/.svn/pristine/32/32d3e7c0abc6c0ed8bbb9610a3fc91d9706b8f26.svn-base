package cn.itcast.jx.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

import cn.itcast.jx.util.Encrypt;

public class CustomCredentialsMatcher extends SimpleCredentialsMatcher{
/*
 * 重写密码比较的方法
 * 
 */
	/**
	 * Authentication：认证
	 * 重写SimpleCredentialsMatcher类的doCredentialsMatch（密码比较的方法）
	 * 返回true：校验成功
	 * 返回false：校验失败
	 * 
	 * 第一个参数：AuthenticationToken：用户在界面上输入的用户名和密码,思考问题：页面如何传递过来？
	 * 第二个参数：AuthenticationInfo：数据库中用户的密文	 * 
	 */

	public boolean doCredentialsMatch(AuthenticationToken token,AuthenticationInfo info){
//		向下转型
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
//		2得到原始密码
//		错误的方式
//		String oldPwd = upToken.getPassword().toString();
//      正确的方式
		String oldPwd = new String(upToken.getPassword());
//		3.对密码进行加密
		String newPwd = Encrypt.md5(oldPwd, upToken.getUsername());
//		获取数据库中的当前用户的密文
		Object dbPwd = info.getCredentials();
//		将密码加密与系统加密后的密码效验,内容一致就返回true,不一致就返回false
		return super.equals(newPwd, dbPwd);
	}
}
