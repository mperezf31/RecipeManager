package controllers;

import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;
import play.mvc.Security;

public class ActionAuthenticator extends Security.Authenticator {

    @Override
    public String getUsername(Http.Context ctx) {
        String token = ctx.request().getHeaders().get("Authentication").orElse("");

        //Return null if the user is not authenticated
        if (!token.equals("VYpL501h7NNg4lzUmW54kXGgRamZh7FihnoRIa")) {
            return null;
        }

        return token;
    }

    @Override
    public Result onUnauthorized(Http.Context ctx) {
        String unauthorizedMsg = Http.Context.current().messages().at("unauthorized");
        return Results.forbidden(unauthorizedMsg);
    }
}
