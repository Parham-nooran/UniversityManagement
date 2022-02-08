package ir.ac.kntu.universityManagement.auth.principals;

import javax.security.auth.Subject;
import java.io.Serializable;
import java.security.Principal;

public class _UserPrincipal implements Principal, Serializable {
    public _UserPrincipal(String username) {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean implies(Subject subject) {
        return false;
    }
}
