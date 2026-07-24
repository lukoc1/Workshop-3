package pl.lukasz.users;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import pl.lukasz.entity.User;
import pl.lukasz.entity.UserDao;

import java.io.IOException;

@WebServlet("/user/add")
public class UserAdd extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        getServletContext().getRequestDispatcher("/users/add.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String email =  request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserDao userDao = new UserDao();
        User user = new User();

        user.setEmail(email);
        user.setUserName(username);
        user.setPassword(password);
        userDao.create(user);
        response.sendRedirect(request.getContextPath() + "/user/list");
    }
}