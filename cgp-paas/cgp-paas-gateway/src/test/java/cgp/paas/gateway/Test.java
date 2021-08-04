package cgp.paas.gateway;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

public class Test {
	public static void main(String[] args) {
		String str = "/login/**";
		PathMatcher p = new AntPathMatcher();
		boolean match = p.match(str, "/login/bbb");
		System.out.println(match);
	}
}
