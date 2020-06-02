package javaweb.remember.service;

import javaweb.remember.entity.User;

public interface UserService {
    User findByEmail(String email);

    void save(User user);
}
