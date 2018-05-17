package com.demo.interceptor;

import com.demo.common.model.User;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

public class AdminInterceptor implements Interceptor {

	@Override
	public void intercept(Invocation inv) {

		System.out.println("Before Admin invoking " + inv.getActionKey());

		User m = inv.getController().getSessionAttr("admin_user");

		if (m == null) {
			inv.getController().redirect("/login");
		} else {
			inv.invoke();
		}

		System.out.println("After Admin invoking " + inv.getActionKey());

	}

}
