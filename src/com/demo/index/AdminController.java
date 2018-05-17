package com.demo.index;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.demo.common.Conts;
import com.demo.common.model.Admin;
import com.demo.common.model.Contract;
import com.demo.common.model.Dep;
import com.demo.common.model.Liucheng;
import com.demo.common.model.Ribao;
import com.demo.common.model.Shenpi;
import com.demo.common.model.User;
import com.demo.common.model.Zb;
import com.demo.interceptor.AdminInterceptor;
import com.demo.service.ContractService;
import com.demo.service.DepService;
import com.demo.service.RbService;
import com.demo.service.ShenpiService;
import com.demo.service.UserService;
import com.demo.service.ZbService;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.json.Json;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;


@Before(AdminInterceptor.class)
public class AdminController extends Controller {
	public void index() {
		setDate();
		
		this.setAttr("admin_user", this.getSessionAttr("admin_user"));
		
		this.render("index.html");
	}
	
	public void user() {
		setDate();
		
		List<Dep> dl = new ArrayList<Dep>();
		DepService ds = new DepService();
		dl = ds.selectAll();
		
		List<User> ul = new ArrayList<User>();
		UserService us = new UserService();
		ul = us.selectAll();

		this.setAttr("deplist", dl);	
		this.setAttr("userlist", ul);
		this.setAttr("list", us.paginate(getParaToInt(0, 1), Conts.PageSize));
		this.setAttr("admin_user", this.getSessionAttr("admin_user"));
		this.setAttr("navmsg", "user");
		this.render("userInfo.html");
	}
	
	public void zhoubaoinfo() {
		setDate();
		
		String weekofyear = this.getPara("weekofyear");
		
		User a = this.getSessionAttr("admin_user");
		List<Zb> zl = new ArrayList<Zb>();
		ZbService zs = new ZbService();
		zl = zs.selectAll();
		//Page<Zb> z =new Zb().dao().paginate(1, 5, "select * from zb order by concat(userid,time) asc ", "");

		this.setAttr("zblist",zl);
		this.setAttr("list", zs.paginate(getParaToInt(0, 1), Conts.PageSize,weekofyear));
		this.setAttr("admin_user", this.getSessionAttr("admin_user"));
		this.setAttr("navmsg", "zhoubaoinfo");
		this.setAttr("weekofyear", weekofyear);
		this.render("zhoubaoInfo.html");
	}
	
	public void zhoubaoinfonew() {
		setDate();
		
		String weekofyear = this.getPara("weekofyear");
		
		User a = this.getSessionAttr("admin_user");


		RbService zs = new RbService();
		String week = getFirstDayOfWeek(new Date().getYear()+1900, Integer.parseInt(weekofyear));
		System.out.println(zs.selectAllByUserid(week));
		//Page<Zb> z =new Zb().dao().paginate(1, 5, "select * from zb order by concat(userid,time) asc ", "");
		
		
		this.setAttr("list", zs.selectAllByUserid(week));
		this.setAttr("admin_user", this.getSessionAttr("admin_user"));
		this.setAttr("navmsg", "zhoubaoinfo");
		this.setAttr("weekofyear", weekofyear);
		this.render("zhoubaoInfonew.html");
	}
	
	  
	
	public void ribaoinfo() throws Exception {
		setDate();
		
		StringBuffer s = new StringBuffer();
		s.append(this.getPara("time"));
		s.insert(4, "-");
		s.insert(7, "-");

		String stime = s+" 00:00:00";
		String etime = s+" 23:59:59";
		
		User a = this.getSessionAttr("admin_user");
		List<Ribao> zl = new ArrayList<Ribao>();
		RbService zs = new RbService();
		//Page<Zb> z =new Zb().dao().paginate(1, 5, "select * from zb order by concat(userid,time) asc ", "");

		this.setAttr("list", zs.paginate(getParaToInt(0, 1), Conts.PageSize,stime,etime));
		this.setAttr("admin_user", this.getSessionAttr("admin_user"));
		this.setAttr("navmsg", "ribaoinfo");
		this.setAttr("time", this.getPara("time"));
		this.render("ribaoInfo.html");
	}
	

	public void zhoubaototal() {
		setDate();
		
		ZbService zs = new ZbService();
		List<Zb> z = zs.selectAll();
		List<Integer> weekofyearlist = new ArrayList<Integer>();
		
		for (Zb zb : z) {
			
			Integer weekofyear = zb.getWeekofyear();
			
			if(!weekofyearlist.contains(weekofyear))
				weekofyearlist.add(weekofyear);
		}
		
		Collections.sort(weekofyearlist);
		Collections.reverse(weekofyearlist);  
		//Page<Zb> q = Zb.dao.paginate(1, 1, "select *", " from zb order by concat(userid,time) asc ");
		
		this.setAttr("list", weekofyearlist);
		this.setAttr("admin_user", this.getSessionAttr("admin_user"));
		this.setAttr("navmsg", "zhoubaototal");
		this.render("zhoubaototal.html");
	}
	
	public void zhoubaototalnew() {
		setDate();
		
		RbService zs = new RbService();
		List<Ribao> z = zs.selectAll();
		List<Integer> weekofyearlist = new ArrayList<Integer>();
		
		for (Ribao rb : z) {
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String time = sdf.format(rb.getTime());
			
			int week = getWeek(time);

			if(!weekofyearlist.contains(week))
				weekofyearlist.add(week);
		}
		
		Collections.sort(weekofyearlist);
		Collections.reverse(weekofyearlist);  
		//Page<Zb> q = Zb.dao.paginate(1, 1, "select *", " from zb order by concat(userid,time) asc ");
		
		this.setAttr("list", weekofyearlist);
		this.setAttr("admin_user", this.getSessionAttr("admin_user"));
		this.setAttr("navmsg", "zhoubaototal");
		this.render("zhoubaototalnew.html");
	}
	
	 public int getWeek(String date) {
	        Calendar cal = Calendar.getInstance();
	        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	        try {
	            cal.setTime(format.parse(date));
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
	        int week = cal.get(Calendar.WEEK_OF_YEAR);
	        return week;
	    }
	 
	    public String getFirstDayOfWeek(int year,int week)  
	    {  
	        Calendar cal = Calendar.getInstance();  
	        //设置年份  
	        cal.set(Calendar.YEAR,year);  
	        //设置周  
	        cal.set(Calendar.WEEK_OF_YEAR, week);  
	        //设置该周第一天为星期一  
	        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);  
	        //格式化日期  
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
	        String firstDayOfWeek = sdf.format(cal.getTime());  
	          
	        return firstDayOfWeek;  
	    } 

	public void ribaototal() throws Exception {
		setDate();
		
		RbService zs = new RbService();
		List<Ribao> z = zs.selectAll();
		List<String> weekofyearlist = new ArrayList<String>();
		
		for (Ribao zb : z) {
			
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			String riqi = sdf.format(zb.getTime()).replace("-","");
			
			if(!weekofyearlist.contains(riqi))
				weekofyearlist.add(riqi);
		}
		
		Collections.sort(weekofyearlist);
		Collections.reverse(weekofyearlist);  
		//Page<Zb> q = Zb.dao.paginate(1, 1, "select *", " from zb order by concat(userid,time) asc ");
		
		this.setAttr("list", weekofyearlist);
		this.setAttr("admin_user", this.getSessionAttr("admin_user"));
		//this.setAttr("navmsg", "zhoubaototal");
		this.render("ribaototal.html");
	}
	public void addtodaycontent(){
		setDate();

		String todaycontent = this.getPara("todaycontent");
		String problem = this.getPara("problem");
		String beizhu = this.getPara("beizhu");
		
		
		
		Ribao r = new Ribao();
		if("".equals(todaycontent)){
			this.renderJson(new Record().set("code", 500).set("msg", "操作失败！"));
			return;
		}
		
		User a = this.getSessionAttr("admin_user");
		
		r.setBeizhu(beizhu);
		r.setProblem(problem);
		r.setContent(todaycontent);
		r.setTime(new Date());
		r.setFid("0");
		r.setUserid(a.getId().toString());
		r.setDepid(a.getDepid());
		r.save();
		
		this.renderJson(new Record().set("code", 200).set("msg", "操作成功！"));
	}

	@Clear
	   public void outlogin() {
	      this.removeSessionAttr("admin_user");
	      this.render("login.html");
	   }
	
	public void dep() {
		setDate();
		
		List<User> ul = new ArrayList<User>();
		UserService us = new UserService();
		ul = us.selectAll();
		
		List<Dep> dl = new ArrayList<Dep>();
		DepService ds = new DepService();
		dl = ds.selectAll();

		this.setAttr("deplist", dl);		
		this.setAttr("userlist", ul);
		this.setAttr("list", ds.paginate(getParaToInt(0, 1), Conts.PageSize));
		this.setAttr("admin_user", this.getSessionAttr("admin_user"));
		this.setAttr("navmsg", "dep");
		this.render("depInfo.html");
	}
	
	public void contract() {
		setDate();
		
		ContractService cs = new ContractService();
		
		this.setAttr("list", cs.paginate(getParaToInt(0, 1), Conts.PageSize));
		this.setAttr("admin_user", this.getSessionAttr("admin_user"));
		this.setAttr("navmsg", "contract");
		this.render("contractInfo.html");
	}
	
	public void shenpi() {
		setDate();
		
		List<User> ul = new ArrayList<User>();
		UserService us = new UserService();
		ul = us.selectAll();
		
		List<Shenpi> dl = new ArrayList<Shenpi>();
		ShenpiService ds = new ShenpiService();
		dl = ds.selectAll();

		this.setAttr("deplist", dl);		
		this.setAttr("userlist", ul);
		this.setAttr("list", ds.paginate(getParaToInt(0, 1), Conts.PageSize));
		this.setAttr("admin_user", this.getSessionAttr("admin_user"));
		this.render("shenpiInfo.html");
	}
	
	public void shenpitime() {
		setDate();
		String id = this.getPara("id");
		
		List<User> ul = new ArrayList<User>();
		UserService us = new UserService();
		ul = us.selectAll();
		
		List<Liucheng> dl = new ArrayList<Liucheng>();
		ShenpiService ds = new ShenpiService();
		dl = ds.selectLiucheng(id);
		System.out.println(dl);

		this.setAttr("list", dl);	
		this.setAttr("admin_user", this.getSessionAttr("admin_user"));
		this.render("shenpitime.html");
	}
	
	//同意
	public void shenpi1() {
		setDate();
		
		List<User> ul = new ArrayList<User>();
		UserService us = new UserService();
		ul = us.selectAll();
		
//		Liucheng l = new Liucheng();
//		l.setAgree(Integer.toString(0));
//		l.update();
		
		String id = this.getPara("shenpiid");
		this.setAttr("shenpiid",id);
		this.setAttr("userlist", ul);
		this.setAttr("admin_user", this.getSessionAttr("admin_user"));
		this.render("shenpiInfo1.html");
	}
	
	//不同意
	public void shenpi2() {
		setDate();
		
		List<User> ul = new ArrayList<User>();
		UserService us = new UserService();
		ul = us.selectAll();
		

		
		String id = this.getPara("shenpiid");
		this.setAttr("shenpiid",id);
		this.setAttr("userlist", ul);
		this.setAttr("admin_user", this.getSessionAttr("admin_user"));
		this.render("shenpiInfo2.html");
	}
	
	public void addshenpistep(){
		setDate();
		
		String con = this.getPara("con");
		String depid = this.getPara("depid");
		String shenpiid = this.getPara("shenpiid");
		
		
		Liucheng l = new Liucheng();
		User a1 = this.getSessionAttr("admin_user");
		
		
		l.setShenpiId(Integer.parseInt(shenpiid));
		l.setInstep(a1.getId());
		l.setAgree(Integer.toString(0));
		l.setNextstep(Integer.parseInt(depid));
		l.setTime(new Date());
		l.setContent(con);
		l.save();
		
		this.renderJson(new Record().set("code", 200).set("msg", "操作成功！"));
	}
	
	public void refuseshenpistep(){
		setDate();
		
		String con = this.getPara("con");
		String shenpiid = this.getPara("shenpiid");
		
		
		Liucheng l = new Liucheng();
		User a1 = this.getSessionAttr("admin_user");
		
		Shenpi s = new Shenpi().findById(shenpiid);
		
		System.out.println(s);
		s.setState(1);
		s.update();
		
		
		l.setShenpiId(Integer.parseInt(shenpiid));
		l.setInstep(a1.getId());
		l.setNextstep(a1.getId());
		l.setTime(new Date());
		l.setAgree(Integer.toString(1));
		l.setContent(con);
		l.save();
		
		this.renderJson(new Record().set("code", 200).set("msg", "操作成功！"));
	}
	

	public User userById(String id) {

		User ul = new User();
		UserService us = new UserService();
		ul = us.selectById(id);
		
		return ul;
	}
	
	public Liucheng nextById(Integer id) {

		Liucheng ul = new Liucheng();
		ShenpiService us = new ShenpiService();
		ul = us.selectNew(id);
		
		return ul;
	}
	
	
	public User userById(Integer id) {

		User ul = new User();
		UserService us = new UserService();
		ul = us.selectById(Integer.toString(id));
		
		return ul;
	}
	
	public List<Ribao> replyById(int id) {

		List<Ribao> rl = new ArrayList<Ribao>();
		RbService us = new RbService();
		rl = us.selectByFid(Integer.toString(id));
		
		return rl;
	}
	
	public List<User> allUser(int id) {

		List<User> rl = new ArrayList<User>();
		UserService us = new UserService();
		rl = us.selectAll();
		
		return rl;
	}
	
	public List<String[]> zhoubaodata(String data) {
		//json 转List<String[]>
		List<String[]> l = new ArrayList<String[]>();
		List<Object> list = JSON.parseArray(data);
		for (int i = 0; i < list.size(); i++) {
			String a = list.get(i).toString().substring(1);
			String[] b = a.substring(1,a.length()-1).replace("\"", "").split(",");
			l.add(b);
		} 
		return l;
	}
	
	public Object jsonToData(String weekinfo) {

		JSONObject jsonObject = JSON.parseObject(weekinfo);
		return jsonObject;
	}
	
//	public List arrayToData(String content) {
//
//		JSONObject jsonObject = JSON.parseObject(content);
//		System.out.println(jsonObject);
//		return jsonObject;
//	}
	
	public void zhoubao() {
		setDate();
		
		List<User> ul = new ArrayList<User>();
		UserService us = new UserService();
		ul = us.selectAll();
		
		List<Dep> dl = new ArrayList<Dep>();
		DepService ds = new DepService();
		dl = ds.selectAll();
		
		Calendar cal = Calendar.getInstance();
		//取当前日期的年份里面的周数
		int currentWeekOfYear = cal.get(Calendar.WEEK_OF_YEAR);

		this.setAttr("weekofyear", currentWeekOfYear);
		this.setAttr("deplist", dl);		
		this.setAttr("userlist", ul);
		
		this.setAttr("admin_user", this.getSessionAttr("admin_user"));
		
		String id = this.getPara("id");
		Zb z = new Zb();
		ZbService zs = new ZbService();
		z = zs.selectById(id);
		
		if("".equals(id)||null==id){
			this.render("zhoubao.html");
		}
		else{
			this.setAttr("zbinfo", z);
			this.setAttr("id", id);
			this.render("editzhoubao.html");
		}
	}
	
	public void zhoubaonew() {
		setDate();
		
		String weekofyear = this.getPara("weekofyear");
		String userid = this.getPara("userid");
		
		List<User> ul = new ArrayList<User>();
		UserService us = new UserService();
		ul = us.selectAll();
		
		List<Dep> dl = new ArrayList<Dep>();
		DepService ds = new DepService();
		dl = ds.selectAll();
		
		Calendar cal = Calendar.getInstance();
		//取当前日期的年份里面的周数
		int currentWeekOfYear = cal.get(Calendar.WEEK_OF_YEAR);

		this.setAttr("weekofyear", currentWeekOfYear);
		this.setAttr("deplist", dl);		
		this.setAttr("userlist", ul);
		
		this.setAttr("admin_user", this.getSessionAttr("admin_user"));

		
		List<Ribao> z = new ArrayList<Ribao>();
		RbService zs = new RbService();
		
		String week = getFirstDayOfWeek(new Date().getYear()+1900, Integer.parseInt(weekofyear));
		z = zs.selecteffectByUserid(week, userid);
		
		
		for (Ribao ribao : z) {
			
		}
		System.out.println(z);
		this.setAttr("zbinfo", z);
		
		this.render("zhoubaonew.html");
		
	}
	
	public void adduser() {
		setDate();
		
		String username = this.getPara("username");
		String tel = this.getPara("tel");
		String pwd = this.getPara("pwd");
		String id = this.getPara("depid");
		
		
		User u = new User();
		if("无".equals(id)){
			u.setDepid("0");
		}
		else{
			u.setDepid(id);
			//u.setPower(id);
		}
		u.setIstrue("1");
		u.setUsername(username);
		u.setPwd(pwd);
		u.setTel(tel);
		u.save();
		
		this.renderJson(new Record().set("code", 200).set("msg", "操作成功！"));
	}
	public void adduserreply() {
		setDate();
		
		String fid = this.getPara("reply");
		String todaycontent = this.getPara("todaycontent");
		
		Ribao r = new Ribao();
		
		if(null==todaycontent||null==fid){
			this.renderJson(new Record().set("code", 500).set("msg", "操作失败！"));
			return;
		}
		
		User a = this.getSessionAttr("admin_user");
		
		r.setContent(todaycontent);
		r.setTime(new Date());
		r.setFid(fid);
		r.setUserid(a.getId().toString());
		r.setDepid(a.getDepid());
		r.save();
		
		this.renderJson(new Record().set("code", 200).set("msg", "操作成功！"));
	}
	

	public void adddep() {
		setDate();
		
		String depname = this.getPara("depname");
		String id = this.getPara("depid");
		
		
		Dep u = new Dep();
		
		u.setDepname(depname);
		u.setUserid(id);
		u.save();
		
		this.renderJson(new Record().set("code", 200).set("msg", "操作成功！"));
	}
	
	public void addshenpi() {
		setDate();
		
		String con = this.getPara("con");
		String depid = this.getPara("depid");
		
		
		Shenpi u = new Shenpi();
		Liucheng l = new Liucheng();
		User a1 = this.getSessionAttr("admin_user");
		
		u.setContent(con);
		u.setPublish(a1.getId());
		u.setState(0);
		u.save();
		
		l.setShenpiId(u.getId());
		l.setInstep(a1.getId());
		l.setNextstep(Integer.parseInt(depid));
		l.setTime(new Date());
		l.save();
		
		this.renderJson(new Record().set("code", 200).set("msg", "操作成功！"));
	}
	
	public void addcontract() throws Exception {
		setDate();

		String name = this.getPara("name");
		String partya = this.getPara("partya");
		String partyb = this.getPara("partyb");
		String partyatel = this.getPara("partyatel");
		String partybtel = this.getPara("partybtel");
		String starttime = this.getPara("starttime");
		String paytime = this.getPara("paytime");
		String beizhu = this.getPara("beizhu");
		User a1 = this.getSessionAttr("admin_user");
		
		Contract c = new Contract();
		
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		
		c.setBeizhu(beizhu);
		c.setConname(name);
		c.setPartya(partya);
		c.setPartyb(partyb);
		c.setPartyatel(partyatel);
		c.setPartybtel(partybtel);
		c.setStarttime(s.parse(starttime));
		c.setPaytime(s.parse(paytime));
		c.setUserid(Integer.toString(a1.getId()));
		c.save();
		
		this.renderJson(new Record().set("code", 200).set("msg", "操作成功！"));
	}
	

	public void upload() {
		setDate();
		UploadFile file = getFile("file");
		HttpServletRequest request = getRequest();  
        String basePath = request.getContextPath();   
        //存储路径  
        String path = getSession().getServletContext().getRealPath(Conts._PATH);  
        String fileName1 = "";
        String dest = "";
        String realFile = "";
        if(file.getFile().length() > 200*1024*1024) {  
            System.err.println("文件长度超过限制，必须小于200M");  
        }else{  
            //上传文件  
            try {
				fileName1 =URLDecoder.decode(file.getFileName(),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}   
            dest = path + "\\" + fileName1;  
            file.getFile().renameTo(new File(dest));  
            realFile = basePath + "/" + Conts._PATH +  fileName1;          
            String fname="/"+fileName1;  
            setAttr("fname", fname);  
            setAttr("url", realFile);  
              
        }  
        this.renderJson(new Record().set("code", 200).set("msg", "操作成功！").set("img", dest));   
	}
	
	public void uploadshenpi() {
		setDate();
		String id = this.getPara("id");
		UploadFile file = getFile("file");
		HttpServletRequest request = getRequest();  
        String basePath = request.getContextPath();   
        //存储路径  
        String path = getSession().getServletContext().getRealPath(Conts._PATH);  
        String fileName1 = "";
        String dest = "";
        String realFile = "";
        if(file.getFile().length() > 200*1024*1024) {  
            System.err.println("文件长度超过限制，必须小于200M");  
        }else{  
            //上传文件  
            try {
				fileName1 =URLDecoder.decode(file.getFileName(),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}   
            dest = path + "\\" + fileName1;  
            file.getFile().renameTo(new File(dest));  
            realFile = basePath + "/" + Conts._PATH +  fileName1;          
            String fname="/"+fileName1;  
            setAttr("fname", fname);
            setAttr("url", realFile);  
              
        }
        
        
        
        Shenpi s = new Shenpi().findById(id);
        s.setFile(dest);
        s.update();
        
        
        this.renderJson(new Record().set("code", 200).set("msg", "操作成功！").set("img", dest));   
	}
	
	
	public void uploadcontract() {
		setDate();
		String id = this.getPara("id");
		UploadFile file = getFile("file");
		HttpServletRequest request = getRequest();  
        String basePath = request.getContextPath();   
        //存储路径  
        String path = getSession().getServletContext().getRealPath(Conts._PATH);  
        String fileName1 = "";
        String dest = "";
        String realFile = "";
        if(file.getFile().length() > 200*1024*1024) {  
            System.err.println("文件长度超过限制，必须小于200M");  
        }else{  
            //上传文件  
            try {
				fileName1 =URLDecoder.decode(file.getFileName(),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}   
            dest = path + "\\" + fileName1;  
            file.getFile().renameTo(new File(dest));  
            realFile = basePath + "/" + Conts._PATH +  fileName1;          
            String fname="/"+fileName1;  
            setAttr("fname", fname);
            setAttr("url", realFile);  
              
        }
        
        
        
        Contract s = new Contract().findById(id);
        s.setUrl(dest);
        s.update();
        
        
        this.renderJson(new Record().set("code", 200).set("msg", "操作成功！").set("img", dest));   
	}
	
	public void downfile(){ 
		String id = this.getPara("id");
		Zb z = new Zb().findById(id);  
        File file=new File(z.getImg());  
          
        if(file.exists()){  
        	renderFile(file);         
        }  
        else{  
            this.renderJson(new Record().set("code", 500).set("msg", "操作失败，请重试！"));  
        }  
    }  
	
	public void downfileshenpi(){ 
		String id = this.getPara("id");
		Shenpi z = new Shenpi().findById(id);  
        File file=new File(z.getFile());  
          
        if(file.exists()){  
        	renderFile(file);         
        }  
        else{  
            this.renderJson(new Record().set("code", 500).set("msg", "操作失败，请重试！"));  
        }  
    }  
	
	public void downfilecontract(){ 
		String id = this.getPara("id");
		Contract z = new Contract().findById(id);  
        File file=new File(z.getUrl());  
          
        if(file.exists()){  
        	renderFile(file);         
        }  
        else{  
            this.renderJson(new Record().set("code", 500).set("msg", "操作失败，请重试！"));  
        }  
    }  
	

	public void addzhoubao() {
		setDate();

		String typeid = this.getPara("typeid");
		String depid = this.getPara("depid");
		String userid = this.getPara("userid");
		String weekofyear = this.getPara("weekofyear");
		String workremark = this.getPara("workremark");
		String userremark = this.getPara("userremark");
		String img = this.getPara("img");
		String depremark = this.getPara("depremark");
		String workdata = this.getPara("workdata");      
		
		String a = workdata.replace("null,", "").replace("\",\"", "\".\"");
		String[] b =a.substring(1, a.length()-1).split(",");	
		
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < b.length; i++) {
			list.add(b[i].replace(".", ","));
		}

//		JSONArray jsonArray = JSON.parseArray(list);
		String content = JSON.toJSONString(list);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("weekofyear", weekofyear);
		map.put("workremark", workremark);
		map.put("userremark", userremark);
		map.put("depremark", depremark);
		
		String weekinfo = JSON.toJSONString(map);
		
		
		User a1 = this.getSessionAttr("admin_user");
		
		Zb u = new Zb();
		
		u.setDepid(depid);
		u.setUserid(userid);
		u.setWeekinfo(weekinfo);
		u.setImg(img);
		u.setWeekofyear(Integer.parseInt(weekofyear));
		u.setAdmin(a1.getId().toString());
		u.setContent(content);
		u.setType(typeid);
		u.setTime(new Date());
		u.save();
		
		this.renderJson(new Record().set("code", 200).set("msg", "操作成功！"));
	}
	
	public void delcontract() {
		setDate();
		
		String id = this.getPara("id");
		
		User a = this.getSessionAttr("admin_user");
		
		Contract c = new Contract().findById(id);
		c.setIstrue(Integer.toString(a.getId()));
		c.update();
		
		this.renderJson(new Record().set("code", 200).set("msg", "操作成功！"));
	}
	
	public void deleteDep() {
		setDate();
		
		String id = this.getPara("id");
		
		Dep c = new Dep();
		c.deleteById(id);
		
		this.renderJson(new Record().set("code", 200).set("msg", "操作成功！"));
	}
	
	public void deleteZb() {
		setDate();
		
		String id = this.getPara("id");
		
		
		Zb c = new Zb();
		c.deleteById(id);
		
		this.renderJson(new Record().set("code", 200).set("msg", "操作成功！"));
	}
	
	public void delribao() {
		setDate();
		
		String id = this.getPara("id");
		
		
		Ribao c = new Ribao();
		c.deleteById(id);
		
		this.renderJson(new Record().set("code", 200).set("msg", "操作成功！"));
	}
	
	public void editDepById() {
		setDate();
		
		String id = this.getPara("id");

		Record r =  Db.findById("dep", id);
		
		this.renderJson(r);
	}
	
	public void editUserById() {
		setDate();
		
		String id = this.getPara("id");

		Record r =  Db.findById("user", id);
		
		this.renderJson(r);
	}
	
	public void doeditzhubaoById() {

		
		setDate();
		
		String id = this.getPara("id");
		String depid = this.getPara("depid");
		String typeid = this.getPara("typeid");
		String userid = this.getPara("userid");
		String weekofyear = this.getPara("weekofyear");
		String workremark = this.getPara("workremark");
		String userremark = this.getPara("userremark");
		String depremark = this.getPara("depremark");
		String workdata = this.getPara("workdata"); 
		String img = this.getPara("img");      

		

		String a = workdata.replace(",[null,null,null,null,null,null]", "").replace("[null,null,null,null,null,null],", "").replace("[null,null,null,null,null,null]", "").replace("\t", "").replace("\n", "").replace("\r", "").replace(" ", "").replace("\",\"", "\".\"");
		
		String[] b =a.substring(1, a.length()-1).split(",");	
	
		
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < b.length; i++) {
			if(null!=b[i]){
				list.add(b[i].replace(".", ","));
			}
			
		}

//		JSONArray jsonArray = JSON.parseArray(list);
		String content = JSON.toJSONString(list);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("weekofyear", weekofyear);
		map.put("workremark", workremark);
		map.put("userremark", userremark);
		map.put("depremark", depremark);
		         
		String weekinfo = JSON.toJSONString(map);
		
		
		User a1 = this.getSessionAttr("admin_user");
		
		Zb u = new Zb().findById(id);
		
		u.setDepid(depid);
		u.setUserid(userid);
		u.setType(typeid);
		u.setImg(img);
		u.setWeekinfo(weekinfo);
		u.setAdmin(a1.getId().toString());
		u.setContent(content);
		u.setTime(new Date());
		u.update();
		
		this.renderJson(new Record().set("code", 200).set("msg", "操作成功！"));
	}
	
	public void doeditDep() {
		setDate();
		
		String id = this.getPara("id");
		String depname = this.getPara("depname");
		String depid = this.getPara("depid");
		
		
		Dep c = new Dep().findById(id);
		
		User u = new User().findById(depid);
		
		c.setDepname(depname);
		u.setPower(id);
		
		if("无".equals(depid)){
			c.setUserid("0");
		}
		else{
			c.setUserid(depid);
		}
		
		c.update();
		u.update();
		
		this.renderJson(new Record().set("code", 200).set("msg", "操作成功！"));
	}
	
	public void doeditUser() {
		setDate();
		
		String id = this.getPara("id");
		String username = this.getPara("username");
		String tel = this.getPara("tel");
		String pwd = this.getPara("pwd");
		String depid = this.getPara("depid");
		
		
		User c = new User().findById(id);
		
		c.setUsername(username);
		c.setPwd(pwd);
		c.setTel(tel);
		
		if("无".equals(depid)){
			c.setDepid("0");
		}
		else{
			c.setDepid(depid);
		}
		
		c.update();
		
		this.renderJson(new Record().set("code", 200).set("msg", "操作成功！"));
	}
	
	public void deleteUser() {
		setDate();
		
		String id = this.getPara("id");
		
		
		User c = new User();
		c.deleteById(id);
		
		this.renderJson(new Record().set("code", 200).set("msg", "操作成功！"));
	}
	
	
	
	@Clear
	public void login() {
		String admin = this.getSessionAttr("admin_user");
		
		this.render("login.html");
	}
	
	@Clear
	public void dologin() {
		setDate();
		
		String admin = this.getPara("username");
 		String pwd = this.getPara("pwd");
		User ad = new User().dao().findFirst("select * from user where tel=? and pwd=?", admin,pwd);
		if(ad==null){
			this.redirect("/login");
			return;
		}
		
		this.setSessionAttr("admin_user", ad);
		this.setAttr("admin_user", ad);
		
		this.render("index.html");
	}
	
	public User adminById(String id) {
		
		User r =  new User().dao().findById(id);
		
		return r;
	}
	
	public Dep depById(String id) {
		
		Dep r =  new Dep().dao().findById(id);
		
		return r;
	}
	
	public void setDate(){
		User a = this.getSessionAttr("admin_user");
		
		List<User> all = new UserService().selectAll();
		Integer userall = all.size()-3;
		System.out.println(userall);
		

		List<User> douser = new UserService().selectAll();

		List<Ribao> l = new RbService().selectAllByTime(new Date());
		List<Zb> l2 = new ZbService().selectAllByTime(new Date());
		
		Map<String, Integer> total = new HashMap<String, Integer>();
		Integer all1 = douser.size()-3;
		total.put("zongshu", all1);
		
		total.put("huibao", l.size());
		total.put("weihuibao", all1-l.size());
		System.out.println(total);
		
		total.put("zbhuibao", l2.size());
		total.put("zbweihuibao", all1-l2.size());
		System.out.println(total);
		
		
		
		Calendar cal = Calendar.getInstance();
		//取当前日期的年份里面的周数
		int currentWeekOfYear = cal.get(Calendar.WEEK_OF_YEAR);
		this.setAttr("week", currentWeekOfYear);
		this.setAttr("shuju", total);
		
		
		this.setSessionAttr("admin_user", a);
		this.setAttr("admin_user", a);
		

	}
	

	public List<Contract> contractById(int id) throws Exception {
		
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		
		ContractService c = new ContractService();
		List<Contract> cl = c.selectAllByUserid(id);
		for(Contract cc:cl){
			String fromDate = s.format(cc.getPaytime());  
			String toDate = s.format(new Date());  
			long from = s.parse(fromDate).getTime();  
			long to = s.parse(toDate).getTime();  
			int days = (int) ((from - to)/(1000 * 60 * 60 * 24));
			cc.put("days", days);
		}
		
		
		return cl;
	}
}





