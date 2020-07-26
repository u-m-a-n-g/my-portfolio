package com.google.sps.servlets;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    UserService userService = UserServiceFactory.getUserService();
    authInfo returnObj = new authInfo();

    if (userService.isUserLoggedIn()) {
      returnObj.loggedIn = true;
      returnObj.email = userService.getCurrentUser().getEmail();;
      returnObj.logoutUrl = userService.createLogoutURL("/");
    } else {
      returnObj.loggedIn = false;
      returnObj.loginUrl = userService.createLoginURL("/");
    }

    Gson gson = new Gson();
    String json = gson.toJson(returnObj);
    response.setContentType("application/json;");
    response.getWriter().println(json);
  }
}

class authInfo {
    Boolean loggedIn;
    String email;
    String loginUrl;
    String logoutUrl;
}