package cn.itcast.jx.action.sysadmin;

public class ZtreeData {
	private String id;
	private String pId;
	private String name;
	private String checked;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPId() {
		return pId;
	}
	public void setPId(String pId) {
		this.pId = pId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public ZtreeData() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ZtreeData(String id, String pId, String name, String checked) {
		super();
		this.id = id;
		this.pId = pId;
		this.name = name;
		this.checked = checked;
	}
	
}
