package cn.itcast.jx.util;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.itcast.jx.common.SysConstant;


 
/**
 * 分页辅助类：对分页的基本数据进行一个简单的封装
 * 用来传递分页参数和查询参数params
 */
public class Page<T> {
    private int pageNo = 1;							//页码，默认是第一页
    private int pageSize = SysConstant.PAGE_SIZE;	//每页显示的记录数，默认是10
    private int totalRecord;						//总记录数
    private int totalPage;							//总页数
    private List<T> results;						//对应的当前页记录
    private Map<String, Object> params = new HashMap<String, Object>();		//其他的参数我们把它分装成一个Map对象
 
    public int getPageNo() {
       return pageNo;
    }
 
    public void setPageNo(int pageNo) {
       this.pageNo = pageNo;
    }
 
    public int getPageSize() {
       return pageSize;
    }
 
    public void setPageSize(int pageSize) {
       this.pageSize = pageSize;
    }
 
    public int getTotalRecord() {
       return totalRecord;
    }
 
    public void setTotalRecord(int totalRecord) {
       this.totalRecord = totalRecord;
       //在设置总页数的时候计算出对应的总页数，在下面的三目运算中加法拥有更高的优先级，所以最后可以不加括号。
       int totalPage = totalRecord%pageSize==0 ? totalRecord/pageSize : totalRecord/pageSize + 1;
       this.setTotalPage(totalPage);
    }
 
    public int getTotalPage() {
       return totalPage;
    }
 
    public void setTotalPage(int totalPage) {
       this.totalPage = totalPage;
    }
 
    public List<T> getResults() {
       return results;
    }
 
    public void setResults(List<T> results) {
       this.results = results;
    }
   
    public Map<String, Object> getParams() {
       return params;
    }
   
    public void setParams(Map<String, Object> params) {
       this.params = params;
    }
 
    public String toString() {
       StringBuilder builder = new StringBuilder();
       builder.append("Page [pageNo=").append(pageNo).append(", pageSize=").append(pageSize).append(", results=").append(results).append(", totalPage=").append(totalPage).append(", totalRecord=").append(totalRecord).append("]");
       return builder.toString();
    }
 
	/* 页面链接 */
    public String url;		//分页按钮中的转向链接
    public void setUrl(String url) {
    	this.url = url;
    }

    public String links;
	/*public String getLinks() {
		StringBuffer sBuf = new StringBuffer();
		int curPageNo = this.pageNo;		//当前页
		
		
		sBuf.append("<span class=\"noprint\" style=\"padding:5px;\">");
		
		//利用js动态设置分页页码
		sBuf.append("<script language=\"javascript\">");
		sBuf.append("	function setPageNo( value ){");
		sBuf.append("		document.getElementById(\"pageNo\").value = value;");
		//sBuf.append("		alert(document.getElementById(\"pageNo\").value);");
		sBuf.append("	}");
		sBuf.append("</script>");
		sBuf.append("<input type=\"hidden\" id=\"pageNo\" name=\"page.pageNo\" value=\"").append(curPageNo).append("\">");		//分页参数：当前页隐藏域

		
		sBuf.append("&nbsp;第").append(curPageNo).append("页 / 共").append(this.totalPage).append("页&nbsp;");
		sBuf.append("&nbsp;总共").append(this.totalRecord).append("条记录 每页").append(this.pageSize).append("条记录&nbsp;");
		
		sBuf.append("<a href=\"#").append("\" onclick=\"setPageNo(1);formSubmit('").append(url).append("','_self')");
		sBuf.append("\">[首页]");
		sBuf.append("</a>&nbsp;");
		
		if(this.pageNo<=1){
			curPageNo = 1;
		}else{
			curPageNo = this.pageNo - 1;
		}
		sBuf.append("<a href=\"#").append("\" onclick=\"setPageNo(").append(curPageNo).append(");formSubmit('").append(url).append("','_self')");
		sBuf.append("\">[上一页]");
		sBuf.append("</a>&nbsp;");
			
		
		if(this.pageNo>=this.totalPage){
			curPageNo = this.totalPage;
		}else{
			curPageNo = this.pageNo + 1;
		}	
		sBuf.append("<a href=\"#").append("\" onclick=\"setPageNo(").append(curPageNo).append(");formSubmit('").append(url).append("','_self')");
		sBuf.append("\">[下一页]");
		sBuf.append("</a>&nbsp;");
			
		sBuf.append("<a href=\"#").append("\" onclick=\"setPageNo(").append(this.totalPage).append(");formSubmit('").append(url).append("','_self')");
		sBuf.append("\">[末页]");
		sBuf.append("</a>&nbsp;");
		
		sBuf.append("</span>");
		
		return sBuf.toString();
	}
	*/
    public String getLinks() {
    	String str = "";
    	int curPageNo = this.pageNo;
//    	设置隐藏域,放idde
    	str += "<input type='hidden' name='page.pageNo' id='pageNo' value='"+curPageNo+"'>";
// 拼接     第4页 / 共4页  总共8条记录 每页2条记录 [首页] [上一页] [下一页] [末页] 	
    	str += "第"+curPageNo+"页 / 共"+totalPage+"页";
    	str+="总共"+totalRecord+"条记录 每页"+pageSize+"条记录";
    	str+="<a href='#' onclick=\"turnPage(1,'"+url+"')\">[首页]</a>";
    	str+="<a href='#' onclick=\"turnPage("+(curPageNo-1<1?1:curPageNo-1)+",'"+url+"')\">[上一页]</a>";
    	str+="<a href='#' onclick=\"turnPage("+(curPageNo+1>totalPage?totalPage:curPageNo+1)+",'"+url+"')\">[下一页]</a>";
    	str+="<a href='#' onclick=\"turnPage("+totalPage+",'"+url+"')\">[末页]</a>";
		return str;
    	
    }
}