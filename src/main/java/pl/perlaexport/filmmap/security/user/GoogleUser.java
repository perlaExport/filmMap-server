package pl.perlaexport.filmmap.security.user;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
public class GoogleUser {
        private Map<String, Object> attributes;

        public String getId() {
            return (String) attributes.get("sub");
        }

        public String getName() {
            return (String) attributes.get("name");
        }

        public String getEmail() {
            return (String) attributes.get("email");
        }
}
