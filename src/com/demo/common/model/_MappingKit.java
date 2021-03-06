package com.demo.common.model;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;

/**
 * Generated by JFinal, do not modify this file.
 * <pre>
 * Example:
 * public void configPlugin(Plugins me) {
 *     ActiveRecordPlugin arp = new ActiveRecordPlugin(...);
 *     _MappingKit.mapping(arp);
 *     me.add(arp);
 * }
 * </pre>
 */
public class _MappingKit {
	
	public static void mapping(ActiveRecordPlugin arp) {
		arp.addMapping("admin", "id", Admin.class);
		arp.addMapping("contract", "id", Contract.class);
		arp.addMapping("dep", "id", Dep.class);
		arp.addMapping("liucheng", "id", Liucheng.class);
		arp.addMapping("ribao", "id", Ribao.class);
		arp.addMapping("shenpi", "id", Shenpi.class);
		arp.addMapping("user", "id", User.class);
		arp.addMapping("zb", "id", Zb.class);
	}
}

