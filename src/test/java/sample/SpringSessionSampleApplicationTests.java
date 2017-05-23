package sample;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringSessionSampleApplicationTests {
	@Autowired
	TestRestTemplate rest;

	@Test
	public void tests() throws Exception {
		ResponseEntity<String> session1 = rest.exchange(session().build(), String.class);
		ResponseEntity<String> session2 = rest.exchange(addCookiesFromTo(session1, session()).build(), String.class);

		assertThat(session1.getBody()).isEqualTo(session2.getBody());

		ResponseEntity<String> change = rest.exchange(addCookiesFromTo(session1, sessionChangeId()).build(), String.class);
		assertThat(change.getBody()).isNotEqualTo(session1.getBody());

		String cookies = extractCookies(change);
		assertThat(cookies).contains(change.getBody());

		ResponseEntity<String> sessionAfterChange = rest.exchange(addCookiesFromTo(change, session()).build(), String.class);
		assertThat(sessionAfterChange.getBody()).isEqualTo(change.getBody());

	}

	private RequestEntity.HeadersBuilder<?> addCookiesFromTo(ResponseEntity<?> response, RequestEntity.HeadersBuilder<?> request) {
		return request.header("Cookie", extractCookies(response));
	}

	private String extractCookies(ResponseEntity<?> response) {
		return response.getHeaders().getFirst("Set-Cookie");
	}

	private RequestEntity.HeadersBuilder<?> session() throws URISyntaxException {
		return RequestEntity.get(new URI("/session"));
	}

	private RequestEntity.HeadersBuilder<?> sessionChangeId() throws URISyntaxException {
		return RequestEntity.get(new URI("/session/change"));
	}
}
