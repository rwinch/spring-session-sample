package sample;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Rob Winch
 * @since 5.0
 */
@RestController
public class MyController {
    @RequestMapping("/session")
    public String sessionNew(HttpServletRequest request) {
        return request.getSession().getId();
    }

    @RequestMapping("/session/change")
    public String sessionChangeId(HttpServletRequest request) {
        return request.changeSessionId();
    }
}
