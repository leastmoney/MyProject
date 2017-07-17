package cn.itcast.jx.action.stat;/**
 * Created by leastmoney on 2017/7/13.
 */

import cn.itcast.jx.action.BaseAction;
import cn.itcast.jx.dao.common.SqlDao;
import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 未来的程序员 Mr.钱
 */
public class StatChartAction extends BaseAction {
    private SqlDao sqlDao;
    public void setSqlDao(SqlDao sqlDao) {
        this.sqlDao = sqlDao;
    }
public String factorySale(){
String sql ="select factory_name ,sum(amount) samount from contract_product_c group by factory_name order by samount desc";
    List<String> list = sqlDao.executeSQL(sql);
    List<Map<String,String>> JSONlist = new ArrayList<Map<String,String>>();
    for (int i = 0 ; i<list.size();i++){
        Map<String, String> map = new HashMap<String, String>();
//        "country": "Lithuania",
//                "value": 260
        map.put("factory",list.get(i));
        map.put("value",list.get(++i));
        JSONlist.add(map);
    }
    String str = JSON.toJSONString(JSONlist);
    super.put("chartData",str);
    return "factorySale";
}

public String productSale(){
    String sql = "select * from (select product_no ,sum(cnumber) amount "
            + "from contract_product_c group by product_no "
            + "order by amount desc) where rownum<16";
    List<String> list = sqlDao.executeSQL(sql);
    List<Map<String,String>> JSONlist = new ArrayList<Map<String,String>>();
//    "name": "John",
//            "points": 35654,
//            "color": "#7F8DA9",
//            "bullet": "images/0.gif"
    String[] colors ={"#FF0F00","#FF6600","#FF9E01","#FCD202","#F8FF01","#B0DE09","#04D215","#0D8ECF","#0D52D1",
            "#2A0CD0","#8A0CCF","#CD0D74","#754DEB","#DDDDDD","#333333","#000000"};
    String[] bullets={"/js/amchart/images/0.gif","/js/amchart/images/1.gif","/js/amchart/images/2.gif","/js/amchart/images/3.gif",
            "/js/amchart/images/0.gif","/js/amchart/images/1.gif","/js/amchart/images/2.gif","/js/amchart/images/3.gif",
            "/js/amchart/images/0.gif","/js/amchart/images/1.gif","/js/amchart/images/2.gif","/js/amchart/images/3.gif",
            "/js/amchart/images/0.gif","/js/amchart/images/1.gif","/js/amchart/images/2.gif","/js/amchart/images/3.gif",
            "/js/amchart/images/0.gif","/js/amchart/images/1.gif","/js/amchart/images/2.gif","/js/amchart/images/3.gif"};
for(int i=0,j=0;i<list.size();i++,j++){
    Map<String, String> map = new HashMap<String, String>();
    map.put("name",list.get(i));
    map.put("points",list.get(++i));
    map.put("color",colors[j]);
    map.put("bullet",bullets[j]);
    JSONlist.add(map);
}
    String str = JSON.toJSONString(JSONlist);
    super.put("chartData",str);
    return "productSale";
}


public String onlineInfo(){
    String sql = "select  a.a1,nvl(b.b1,0) "
            + "from online_info_t a left join "
            + "(select to_char(login_time,'hh24') a1,count(1)b1 from login_log_p group by to_char(login_time,'hh24'))b "
            + "on a.a1 = b.a1 order by a.a1 asc";
    List<String> list = sqlDao.executeSQL(sql);
    List<Map<String,String>> JSONlist = new ArrayList<Map<String,String>>();
    for(int i = 0 ;i <list.size();i++){
        Map<String, String> map = new HashMap<String, String>();
//        "year": "1950",
//                "value": 0.22
        map.put("hour",list.get(i));
        map.put("value",list.get(++i));
        JSONlist.add(map);
    }
    String str = JSON.toJSONString(JSONlist);
    super.put("chartData",str);
    return "onlineInfo";
}

public String onlineInfoJson(){
    String sql = "select  a.a1,nvl(b.b1,0) "
            + "from online_info_t a left join "
            + "(select to_char(login_time,'hh24') a1,count(1)b1 from login_log_p group by to_char(login_time,'hh24'))b "
            + "on a.a1 = b.a1 order by a.a1 asc";
    List<String> list = sqlDao.executeSQL(sql);
    StringBuffer sb = new StringBuffer();
    sb.append("[{");
    sb.append("name:'人数',");
    sb.append("data:[");
    for (int i = 0;i<list.size();i++){
        sb.append(list.get(i)+",");
    }
//截取字符串
    sb.delete(sb.length()-1,sb.length());
    sb.append("],");
    sb.append("type:'line'");
    sb.append("}]");
    super.put("chartData",sb.toString());
    return "onlineInfoJson";
}

//[
//    {
//        name:'访问来源',
//                type:'pie',
//            radius : '55%',
//            center: ['50%', '60%'],
//        data:[
//        {value:335, name:'直接访问'},
//        {value:310, name:'邮件营销'},
//        {value:234, name:'联盟广告'},
//        {value:135, name:'视频广告'},
//        {value:1548, name:'搜索引擎'}
//                        ]
//    }
//                ]
public  String factorySaleJson(){
    String sql ="select factory_name ,sum(amount) samount from contract_product_c group by factory_name order by samount desc";
    List<String> list = sqlDao.executeSQL(sql);
    StringBuffer sb = new StringBuffer();
    sb.append("[{");
    sb.append("name:'工厂总和',");
    sb.append("type:'pie',");
    sb.append("radius:'55%',");
    sb.append(" center: ['50%', '60%'],");
    sb.append("data:[");
    for (int i = 1 ;i<list.size();i+=3){
        sb.append("{value:"+list.get(i)+",");
        sb.append("name:'"+list.get(--i)+"'},");
    }
    sb.delete(sb.length() - 1, sb.length());
    sb.append("]}]");
    System.out.println(sb.toString());
    super.put("chartData",sb.toString());
    return "factorySaleJson";
}

}
