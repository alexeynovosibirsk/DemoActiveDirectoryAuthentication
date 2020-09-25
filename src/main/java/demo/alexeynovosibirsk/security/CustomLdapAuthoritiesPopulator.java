package demo.alexeynovosibirsk.security;

import demo.alexeynovosibirsk.views.startPage.StartPageView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import java.util.*;

public class CustomLdapAuthoritiesPopulator  implements LdapAuthoritiesPopulator {

    Logger logger = LoggerFactory.getLogger(CustomLdapAuthoritiesPopulator.class);

    Set<String> setAdmins;

    @Override
    public Collection<? extends GrantedAuthority> getGrantedAuthorities(DirContextOperations userData, String username) {

        Collection<GrantedAuthority> authorities = new HashSet<>();
        setAdmins = SecurityConfigFileHandler.readConfig();

        for (String adminName : setAdmins) {
            if (adminName.equals(username)) {
                authorities.add(new SimpleGrantedAuthority("Admin"));
            }

            try {
                String[] groups = userData.getStringAttributes("memberOf");
                for (String s : groups) {
                    s = s.replace("CN=", "").split(",")[0];
                    if (s.equals("SomeGroup")) {
                        authorities.add(new SimpleGrantedAuthority("Portaluser"));
                    }
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        String name = userData.getStringAttribute("name");
        String firstName = name.split(" ")[1];
        String middleName =  name.split(" ")[2];
        StartPageView s = new StartPageView();
        s.setName(firstName + " " + middleName);

        logger.info("Login " + username + " Role: " + authorities);
        return authorities;
    }
}