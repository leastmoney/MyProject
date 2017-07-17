package cn.itcast.jx.domain;
//这是对跨部门专用的实体类
public class FuBoss {
	private String id;//部门或者
	private String fubossId;//副总的id
	private String type;//类型,"部门",或者员工
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFubossId() {
		return fubossId;
	}
	public void setFubossId(String fubossId) {
		this.fubossId = fubossId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
