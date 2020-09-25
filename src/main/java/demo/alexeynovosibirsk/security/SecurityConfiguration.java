package demo.alexeynovosibirsk.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		DefaultSpringSecurityContextSource contextSource = new DefaultSpringSecurityContextSource("ldap://domain.sub.ru:389/dc=domain,dc=nsk,dc=ru");
		contextSource.setUserDn("YourActiveDirectoryLogin@domain.nsk.ru");
		contextSource.setPassword("YourPassword");
		contextSource.setReferral("ignore"); //if authentication performs for a long time and you see the: o.s.s.l.SpringSecurityLdapTemplate - Ignoring PartialResultException. Parameter must be "false".
		contextSource.afterPropertiesSet();

		auth.ldapAuthentication()
				.userSearchBase("")
				.userSearchFilter("sAMAccountName={0}") // here we set filter search user by login
				.ldapAuthoritiesPopulator(new CustomLdapAuthoritiesPopulator())
				.contextSource(contextSource);
	}

	private static final String LOGIN_PROCESSING_URL = "/login";
	private static final String LOGIN_FAILURE_URL = "/login?error";
	private static final String LOGIN_URL = "/login";
	private static final String LOGOUT_SUCCESS_URL = "/login";

	/**
	 * Require login to access internal pages and configure login form.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Not using Spring CSRF here to be able to use plain HTML for the login page
		http.csrf().disable()

				// Register our CustomRequestCache, that saves unauthorized access attempts, so
				// the user is redirected after login.
				.requestCache().requestCache(new CustomRequestCache())

				// Restrict access to our application.
				.and().authorizeRequests()

				// Allow all Vaadin internal requests.
				.requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()

				// Allow all requests by logged in users.
				.anyRequest().authenticated()

				// Configure the login page.
				.and().formLogin()
                        .loginPage(LOGIN_URL).permitAll()
                        .loginProcessingUrl(LOGIN_PROCESSING_URL)
				        .failureUrl(LOGIN_FAILURE_URL)

				// Configure logout
				.and().logout().logoutSuccessUrl(LOGOUT_SUCCESS_URL);
	}

	/**
	 * Allows access to static resources, bypassing Spring security.
	 */
	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers(
				// Client-side JS
				"/VAADIN/**",

				// the standard favicon URI
				"/favicon.ico",

				// the robots exclusion standard
				"/robots.txt",

				// web application manifest
				"/manifest.webmanifest",
				"/sw.js",
				"/offline.html",

				// icons and images
				"/icons/**",
				"/images/**",
				"/styles/**",

				// (development mode) H2 debugging console
				"/h2-console/**");
	}
}
