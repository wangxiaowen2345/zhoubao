package com.demo.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseShenpi<M extends BaseShenpi<M>> extends Model<M> implements IBean {

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

	public M setFile(java.lang.String file) {
		set("file", file);
		return (M)this;
	}
	
	public java.lang.String getFile() {
		return getStr("file");
	}

	public M setPublish(java.lang.Integer publish) {
		set("publish", publish);
		return (M)this;
	}
	
	public java.lang.Integer getPublish() {
		return getInt("publish");
	}

	public M setState(java.lang.Integer state) {
		set("state", state);
		return (M)this;
	}
	
	public java.lang.Integer getState() {
		return getInt("state");
	}

}