package dwu.swcmop.trippacks.service;

import dwu.swcmop.trippacks.entity.User;
import dwu.swcmop.trippacks.repository.UserRepository;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void delete(User user) {
        userRepository.delete(user);
    }
}