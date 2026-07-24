package pl.lukasz.users;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import pl.lukasz.entity.User;
import pl.lukasz.entity.UserDao;

import java.io.IOException;

@WebServlet("/user/edit")
public class UserEdit extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        UserDao userDao = new UserDao();
        User user = userDao.read(id);

        request.setAttribute("user", user);
        getServletContext().getRequestDispatcher("/users/edit.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        String email =  request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserDao userDao = new UserDao();

        User userToUpdate = userDao.read(id);

        userToUpdate.setEmail(email);
        userToUpdate.setUserName(username);
        userDao.update(userToUpdate);
        userDao.updatePassword(id, password);
        response.sendRedirect("/user/list");
    }
}