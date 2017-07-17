package cn.itcast.jx.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import cn.itcast.jx.common.SysConstant;
import cn.itcast.jx.dao.BaseDao;
import cn.itcast.jx.domain.User;
import cn.itcast.jx.service.UserService;
import cn.itcast.jx.util.Encrypt;
import cn.itcast.jx.util.MailUtil;
import cn.itcast.jx.util.Page;

public class UserServiceImpl implements UserService {
	
	private BaseDao baseDao;
	
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	public Page<User> findPage(String hql, Page page, Class<User> entityClass,
			Object[] params) {
		return baseDao.findPage(hql, page, entityClass, params);
	}

	public User get(Class<User> entityClass, Serializable id) {
		return baseDao.get(entityClass, id);
	}

	public List<User> find(String hql, Class<User> entityClass, Object[] params) {
		return baseDao.find(hql, entityClass, params);
	}

	//注入邮件发送相关的对象
	private SimpleMailMessage mailMessage;
	private JavaMailSender mailSender;
	
	public void setMailMessage(SimpleMailMessage mailMessage) {
		this.mailMessage = mailMessage;
	}
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	public void saveOrUpdate(final User user) {
		if(StringUtils.isBlank(user.getId())){
			//说明是新增
			String id = UUID.randomUUID().toString();
			user.setId(id);
			user.getUserInfo().setId(id);
			user.setPassword(Encrypt.md5(SysConstant.DEFAULT_PASS, user.getUserName()));
			
			
			/**
			 * 随机产生6位密码，然后发送给用户
			 * 短信也可发送(需要使用第三方服务器，中国网建)
			 */
			final String pwd = randomPwd();
			//发送邮件,将用户的用户名和密码发出去
			new Thread(new Runnable(){
				
				@Override
				public void run() {
					try{
//					MailUtil.sendMail(user.getUserInfo().getEmail(), "欢迎加入百合网", "您的账号是"+user.getUserName()+",密码是"+pwd);
						mailMessage.setTo("1054740130@qq.com");
						mailMessage.setSubject("欢迎加入捷信商贸集团");
						mailMessage.setText("员工入职通知,账号是"+user.getUserName()+",密码:"+pwd);
						mailSender.send(mailMessage);
						System.out.println("发送成功");
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				
			}).start();
//			设置密码
			String dbpwd = Encrypt.md5(pwd, user.getUserName());
			user.setPassword(dbpwd);
			baseDao.saveOrUpdate(user);
		}else{
			//说明是修改
			User viewUser = baseDao.get(User.class, user.getId());
			viewUser.setDept(user.getDept());
			viewUser.setUserName(user.getUserName());
			viewUser.setState(user.getState());
			baseDao.saveOrUpdate(viewUser);
		}
	}

	public void deleteById(Class<User> entityClass, Serializable id) {
		User user = baseDao.get(entityClass, id);
		user.setState(SysConstant.DISABLED);
		baseDao.saveOrUpdate(user);
	}

	public void deleteById(Class<User> entityClass, Serializable[] ids) {
		for (Serializable id : ids) {
			deleteById(User.class, id);
		}
	}

	public User findUserByName(String username) {
		List<User> list = baseDao.find("from User where userName = ?", User.class, new Serializable[]{username});
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		else {
			return null;
		}


}
	
	
	public String randomPwd(){
		String pwd = "";
		String values="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVMXYZ0123456789.!@#$%^&*()_+";
		/**
		 * 产生随机数的方式：
		 * 1 Random
		 * 2 Math：随机性更高
		 */
//		Math.random();//0-0.999999999999999999
		
		Random random = new Random();
		for(int i = 0;i<6;i++){
			int index = random.nextInt(values.length());
			pwd+=values.charAt(index);
		}
		return pwd;
	}

	
}