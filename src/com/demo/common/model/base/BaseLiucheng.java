package com.demo.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseLiucheng<M extends BaseLiucheng<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setShenpiId(java.lang.Integer shenpiId) {
		set("shenpi_id", shenpiId);
		return (M)this;
	}
	
	public java.lang.Integer getShenpiId() {
		return getInt("shenpi_id");
	}

	public M setInstep(java.lang.Integer instep) {
		set("instep", instep);
		return (M)this;
	}
	
	public java.lang.Integer getInstep() {
		return getInt("instep");
	}

	public M setNextstep(java.lang.Integer nextstep) {
		set("nextstep", nextstep);
		return (M)this;
	}
	
	public java.lang.Integer getNextstep() {
		return getInt("nextstep");
	}

	public M setTime(java.util.Date time) {
		set("time", time);
		return (M)this;
	}
	
	public java.util.Date getTime() {
		return get("time");
	}

	public M setContent(java.lang.String content) {
		set("content", content);
		return (M)this;
	}
	
	public java.lang.String getContent() {
		return getStr("content");
	}

	public M setAgree(java.lang.String agree) {
		set("agree", agree);
		return (M)this;
	}
	
	public java.lang.String getAgree() {
		return getStr("agree");
	}

}