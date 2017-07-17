package cn.itcast.jx.action;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import cn.itcast.jx.common.SysConstant;
import cn.itcast.jx.domain.User;

/**
 * @Description:
 * @author:     未来的程序员 :Mr.钱
 * @version:    1.0
 * @Company:    http://java.itcast.cn 
 * @date:       2017年6月25日
 */
public class LoginAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private String username;
	private String password;



	//SSH传统登录方式
	public String login()  throws Exception {
//		1 获取subject对象
		Subject subject = SecurityUtils.getSubject();
//		调用判断用户是否登录的方法
		boolean isLongin = subject.isAuthenticated();
		if(isLongin){
			return SUCCESS;
		}
		if(StringUtils.isBlank(username)){
			return LOGIN;
		}
		
		try{
//			shiro认证过程
//			2 实现登录过程:封装用户在页面上提交的用户名和密码
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
//			3将封装号的用户和密码交给shiro安全管理框架并实现登录
			subject.login(token);
//			4 当认证成功后，要将Shiro中保存的用户对象取出来
			User principal = (User)subject.getPrincipal();
			//5 将用户对象放入session域中
			session.put(SysConstant.CURRENT_USER_INFO, principal);
		}catch(Exception e){
			e.printStackTrace();
			request.put("errorInfo","对不起，登录失败，用户名或密码错误");
			return LOGIN;

		}
		return SUCCESS;
		
	}
	public String logout(){
//		注销登录
		session.remove(SysConstant.CURRENT_USER_INFO);
		SecurityUtils.getSubject().logout();
		return "logout";
	}
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

