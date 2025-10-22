package k23cnt2_duongchidu_Day02.controller;

import k23cnt2_duongchidu_Day02.config.K23cnt2DuongchiduDay02AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class K23cnt2DuongchiduDay02BeanController {

    private final K23cnt2DuongchiduDay02AppConfig appConfig;

    @Autowired
    public K23cnt2DuongchiduDay02BeanController(K23cnt2DuongchiduDay02AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @GetMapping("/my-bean")
    public String myBean() {
        return appConfig.appName();
    }
}
