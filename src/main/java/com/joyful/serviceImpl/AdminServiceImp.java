package com.joyful.serviceImpl;
import org.springframework.stereotype.Service;
import com.joyful.service.AdminService;

@Service
public class AdminServiceImp implements AdminService {

    @Override
    public boolean login(String loginId, String password) {
        return loginId != null &&
               loginId.toString().equals("JoyFul") &&
               "JoyFul@654".equals(password);
    }
}
