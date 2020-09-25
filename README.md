# DemoActiveDirectoryAuthentication
Active Directory Authentication Example

Это пример аутентификации в Windows Active Directory

WebUI - Vaadin 14

В примере создаются 3 уровня доступа:

1. Все пользователи Active Directory  (доступы вьюшки без Vaadin аннотаций)
2. Пользователи, принадлежащие к определенной группе Active Directory. Эти пользователи определяются в CustomLdapAuthoritiesPopulator.class как "Portaluser". Им доступны вьюшки без Vaadin аннотаций и с Vaadin аннотацией "ROLE_Portaluser".
3. Пользователи, определенные в CustomLdapAuthoritiesPopulator.class как "Admin". Им доступны вьюшки без Vaadin аннотаций и с Vaadin аннотацией "ROLE_Admin". Обратите внимание, что вьюшки с Vaadin аннотацией "ROLE_Portaluser" для пользователя Admin не видны, если он не принадлежит к определенной группе Active Directory.

Резюме: чтобы разделить контент между пользователями нужно:
1. в CustomLdapAuthoritiesPopulator.class определить группу:

if (<var>.equals(<var1>) {
  
  authorities.add(new SimpleGrantedAuthority("role name - for example "Admin"));
  }
  
  
2. На нужную вьюшку повесить Vaadin аннотацию: 

  @Secured("ROLE_Admin")
  
  public class SomeView...
  
  
3. Если хотите скрыть ссылку на недоступную вьюшку нужно в MainView.class:
  
  Router link = new RouterLink("Эта ссылка будет видна только админам", SomePageView.class);
  
  Tab tab = createTab(link)
  
  if(SecurityUtils.isAccessGranted(SomePageView.class)) {
  
  addToDrawer(tab);
  }
  

