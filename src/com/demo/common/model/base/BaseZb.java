package com.demo.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseZb<M extends BaseZb<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setContent(java.lang.String content) {
		set("content", content);
		return (M)this;
	}
	
	public java.lang.String getContent() {
		return getStr("content");
	}

	public M setTime(java.util.Date time) {
		set("time", time);
		return (M)this;
	}
	
	public java.util.Date getTime() {
		return get("time");
	}

	public M setIsedit(java.lang.String isedit) {
		set("isedit", isedit);
		return (M)this;
	}
	
	public java.lang.String getIsedit() {
		return getStr("isedit");
	}

	public M setUserid(java.lang.String userid) {
		set("userid", userid);
		return (M)this;
	}
	
	public java.lang.String getUserid() {
		return getStr("userid");
	}

	public M setImg(java.lang.String img) {
		set("img", img);
		return (M)this;
	}
	
	public java.lang.String getImg() {
		return getStr("img");
	}

	public M setDepid(java.lang.String depid) {
		set("depid", depid);
		return (M)this;
	}
	
	public java.lang.String getDepid() {
		return getStr("depid");
	}

	public M setType(java.lang.String type) {
		set("type", type);
		return (M)this;
	}
	
	public java.lang.String getType() {
		return getStr("type");
	}

	public M setWeekinfo(java.lang.String weekinfo) {
		set("weekinfo", weekinfo);
		return (M)this;
	}
	
	public java.lang.String getWeekinfo() {
		return getStr("weekinfo");
	}

	public M setAdmin(java.lang.String admin) {
		set("admin", admin);
		return (M)this;
	}
	
	public java.lang.String getAdmin() {
		return getStr("admin");
	}

	public M setWeekofyear(java.lang.Integer weekofyear) {
		set("weekofyear", weekofyear);
		return (M)this;
	}
	
	public java.lang.Integer getWeekofyear() {
		return getInt("weekofyear");
	}

}
