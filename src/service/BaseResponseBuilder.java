package service;

import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

public abstract class BaseResponseBuilder {

	private CacheControl cc = new CacheControl();
	
	private CacheControl getCacheControl() {
		cc.setNoCache(true);
		cc.setNoStore(true);
		return cc;
	}
	
	public Response buildOkResponse(Object o) {
		ResponseBuilder builder = Response.ok(o, "text/xml");
		builder.cacheControl(getCacheControl());
		return builder.build();		
	}
}
