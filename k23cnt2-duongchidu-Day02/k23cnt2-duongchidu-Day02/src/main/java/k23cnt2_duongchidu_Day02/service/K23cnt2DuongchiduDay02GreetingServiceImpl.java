package k23cnt2_duongchidu_Day02.service;

import org.springframework.stereotype.Service;

@Service
public class K23cnt2DuongchiduDay02GreetingServiceImpl implements K23cnt2DuongchiduDay02GreetingService {

    @Override
    public String greet(String name) {
        return "<h2>Xin chào từ Spring Boot!</h2><h3>Chào " + name + "</h3>";
    }
}
