package com.fixit.utility;

import org.scribe.builder.ServiceBuilder;
import org.scribe.oauth.OAuthService;

import com.google.inject.Scope;

public class OAuthServiceProvider {
	
	private OAuthServiceConfig config;
	
	private static final String SCOPE = "https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/plus.stream.read " +
            "https://www.googleapis.com/auth/plus.stream.write";

	
	public OAuthServiceProvider() {
	}
	
	public OAuthServiceProvider(OAuthServiceConfig config) {
		this.config = config;
	}

	public OAuthService getService() {
		
		if(config.getApiClass().getName().contains("FacebookApi"))
		{
		return new ServiceBuilder().provider(config.getApiClass())
							.apiKey(config.getApiKey())
						    .apiSecret(config.getApiSecret())
						    .callback(config.getCallback())
						    .scope("email")
						    .build();
		}
		else if(config.getApiClass().getName().contains("Google2Api"))
		{
		return new ServiceBuilder().provider(config.getApiClass())
							.apiKey(config.getApiKey())
						    .apiSecret(config.getApiSecret())
						    .callback(config.getCallback())
						    .scope(SCOPE)
						    .build();
		}
		else
		{
			return new ServiceBuilder().provider(config.getApiClass())
					.apiKey(config.getApiKey())
				    .apiSecret(config.getApiSecret())
				    .callback(config.getCallback())
				    .build();
		}
	}
	
}