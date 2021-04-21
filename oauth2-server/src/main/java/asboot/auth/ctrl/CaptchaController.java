package asboot.auth.ctrl;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class CaptchaController {

	@Autowired
	private DefaultKaptcha producer;

	@GetMapping("/captcha")
	public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String text = this.producer.createText();

		// Set to expire far in the past.
		response.setDateHeader("Expires", 0);
		// Set standard HTTP/1.1 no-cache headers.
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		// Set IE extended HTTP/1.1 no-cache headers (use addHeader).
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		// Set standard HTTP/1.0 no-cache header.
		response.setHeader("Pragma", "no-cache");

		response.setContentType(MediaType.IMAGE_JPEG_VALUE);

		WebUtils.setSessionAttribute(request, Constants.KAPTCHA_SESSION_KEY, text);
		WebUtils.setSessionAttribute(request, Constants.KAPTCHA_SESSION_DATE, System.currentTimeMillis());
		log.info("{}:{}", Constants.KAPTCHA_SESSION_KEY, text);

		ImageIO.write(this.producer.createImage(text), "jpg", response.getOutputStream());
	}

}
