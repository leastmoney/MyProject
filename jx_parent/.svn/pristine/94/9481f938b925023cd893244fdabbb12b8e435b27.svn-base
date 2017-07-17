package cn.itcast.jx.shiro;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import cn.itcast.jx.domain.Module;
import cn.itcast.jx.domain.Role;
import cn.itcast.jx.domain.User;
import cn.itcast.jx.service.UserService;

public class AuthRealm extends AuthorizingRealm{
	private UserService userService;
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	
	
	/**
	 * 授权的方法
	 * 使用的地方：当页面使用Shiro标签时，就会调用授权方法
	 * （怎么调用？谁调用？由shiro的核心控制器调用）
	 * 第一个参数：PrincipalCollection ：principals集合，对象主体集合
	 * 
	 * 如何授权？答：根据当前用户的信息来获取当前用户对应的角色，判断这些角色具备的访问某些模块的权限，从而给予当前用户特定的权限
	 * 所以授权第一步：获取当前用户
	 * 
	 * 
	 */
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("授权");
		//1 获取登录时存放在shiro中的User对象
		// 根据 realm的名称，找到PrincipalCollection中存入的用户的名称
		User user = (User)principals.fromRealm(getName()).iterator().next();
		
		// 2  通过对象导航，得到当前用户对应的角色列表
		List<Role> roles = user.getRoles();
		// 3.0 将所有的模块装在一起
		List<String> list = new ArrayList();
		// 3.1  由于每个角色下面都有自己的模块，所以遍历每个角色，然后统计出所有的模块
		for(Role role :roles){
			//4 获取当前角色对应的模块
			List<Module> modules = role.getModules();
			//5 遍历模块
			for(Module m:modules){
				//Ctype:0 顶侧菜单
				if(m.getCtype()==0){
					//说明是主菜单
					list.add(m.getCpermission());
				}
			}
		}
		//得到权限字符串
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//添加模块访问权限
		info.addStringPermissions(list);
		return info;
	}
	/**
	 * 认证  登录
	 * 当用户登录的时候，shiro真正会调用它，来实现登录操作
	 * 
	 * 当这个方法返回null时，就会出现异常
	 * 当认证成功后，返回的对象不为null时，就会自动进入密码比较器（在shiro的xml文件中，配置了密码比较器）
	 * 	<property name="credentialsMatcher" ref="passwordMatcher"/>
	 * 
	 * 第一个参数：AuthenticationToken token：用户在页面输入的用户名和密码
	 * 
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		System.out.println("认证");
		// 1 向下转型
		UsernamePasswordToken upToken = (UsernamePasswordToken)token;
		// 2 根据用户名到数据库查找用户是否存在
		User user = userService.findUserByName(upToken.getUsername());
		// 3 判断用户对象是否存在，
		if(user==null){
			//用户名不存在
			return null;
		}else{
			//用户名存在
			// 将用户信息封装到AuthenticationInfo对象中，回头再密码比较器中需要调用
			/**
			 * 第一个参数：Object principal：当前用户对象
			 * 第二个参数：Object credentials：数据库中的密文
			 * 第三个参数：String realmName：普通字符串，realm的名字，this.getName():当前类名对应的完整的包路径
			 * 
			 */
			AuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), getName());
			return info;
		}
	}	

}
