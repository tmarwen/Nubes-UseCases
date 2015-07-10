package treeptik.ldallen.controller.todolist;

import com.github.aesteve.vertx.nubes.annotations.Controller;
import com.github.aesteve.vertx.nubes.annotations.File;
import com.github.aesteve.vertx.nubes.annotations.auth.Auth;
import com.github.aesteve.vertx.nubes.annotations.cookies.Cookies;
import com.github.aesteve.vertx.nubes.annotations.params.RequestBody;
import com.github.aesteve.vertx.nubes.annotations.routing.http.GET;
import com.github.aesteve.vertx.nubes.annotations.routing.http.POST;
import com.github.aesteve.vertx.nubes.auth.AuthMethod;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.User;
import io.vertx.ext.auth.jwt.JWTOptions;
import io.vertx.ext.auth.jwt.impl.JWTUser;
import io.vertx.ext.web.RoutingContext;
import org.hsqldb.rights.UserManager;
import treeptik.ldallen.Server;

import java.util.ArrayList;
import java.util.List;

@Controller("/todolist")
public class TodolistController {

	@GET
	@File
	public String rootView() {
		return "web/assets/index.html";
	}

	@GET("/token")
	public void token(RoutingContext context){
		context.response().putHeader("Content-Type", "text/plain");
		List<String> authority = new ArrayList<String>();
		authority.add("secret");
		String token = Server.jwt.generateToken(new JsonObject(), new JWTOptions().setExpiresInSeconds(60).setPermissions(authority));
		context.response().end(token);
	}

	@GET("/list")
	@Auth(method = AuthMethod.JWT, authority = "secret")
	@File
	public String listView() {
		return "web/assets/todolist.html";
	}


}