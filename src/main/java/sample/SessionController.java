package sample;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Rob Winch
 * @since 5.0
 */
@RestController
@RequestMapping("/session")
public class SessionController {
    @GetMapping
    public String session(HttpServletRequest request) {
        return request.getSession().getId();
    }

    @GetMapping("/change")
    public String sessionChangeId(HttpServletRequest request) {
        return request.changeSessionId();
    }
}
