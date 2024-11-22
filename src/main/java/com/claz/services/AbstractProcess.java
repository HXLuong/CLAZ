package com.claz.services;

import com.claz.momoConfig.CustomerEnvironment;
import com.claz.momoConfig.PartnerInfo;
import com.claz.utils.Execute;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class AbstractProcess<T, V> {

	protected PartnerInfo partnerInfo;
	protected CustomerEnvironment environment;
	protected Execute execute = new Execute();

	public AbstractProcess(CustomerEnvironment environment) {
		this.environment = environment;
		this.partnerInfo = environment.getPartnerInfo();
	}

	public static Gson getGson() {
		return new GsonBuilder().disableHtmlEscaping().create();
	}

	public abstract V execute(T request) throws RuntimeException;
}
