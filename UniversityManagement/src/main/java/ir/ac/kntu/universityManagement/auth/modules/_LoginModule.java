package ir.ac.kntu.universityManagement.auth.modules;


import ir.ac.kntu.universityManagement.auth.principals._UserPrincipal;
import ir.ac.kntu.universityManagement.controllers.general.HomePageController;
import ir.ac.kntu.universityManagement.exceptions.InvalidLoginException;
import ir.ac.kntu.universityManagement.models.auth.UserInfo;
import ir.ac.kntu.universityManagement.models.repositories.UserInfoRepository;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.security.auth.Subject;
import javax.security.auth.callback.*;
import javax.security.auth.spi.LoginModule;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Setter
public class _LoginModule implements LoginModule {
    private CallbackHandler callbackHandler = null;
    private Subject subject;
    public static UserInfoRepository userInfoRepository;
    private _UserPrincipal userPrincipal;
    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState,
                           Map<String, ?> options) {
        this.callbackHandler = callbackHandler;
        this.subject = subject;
    }

    @Override
    public boolean login() throws InvalidLoginException {
        Callback[] callbacks = new Callback[2];
        callbacks[0] = new NameCallback("Username : ");
        callbacks[1] = new PasswordCallback("Password : ", false);
        try {
            callbackHandler.handle(callbacks);
        } catch (IOException | UnsupportedCallbackException e) {
            e.printStackTrace();
        }
        String username = ((NameCallback) callbacks[0]).getName();
        String password = new String(((PasswordCallback) callbacks[1]).getPassword());

        List<UserInfo> foundUsers = userInfoRepository.findByUsername(username);

        if (foundUsers.isEmpty()) {
            throw new InvalidLoginException("Username Is Invalid!");
        } else if (foundUsers.size() > 1) {
            throw new InvalidLoginException("More than one user was found!\nPlease Inform the administrator!");
        } else if (foundUsers.stream().noneMatch(userInfo -> BCrypt.checkpw(password, userInfo.getPassword()))) {
            throw new InvalidLoginException("Password Is Invalid!");
        } else{
            userPrincipal = new _UserPrincipal(username);
            HomePageController.user = foundUsers.get(0);
            HomePageController.fillHomePage();
        }
        return true;
    }

    @Override
    public boolean commit()  {
        boolean isAlright = false;
        if (subject!=null && !subject.getPrincipals().contains(userPrincipal)){
            subject.getPrincipals().add(userPrincipal);
            isAlright = true;
        }
        return isAlright;
    }

    @Override
    public boolean abort()  {
        return true;
    }

    @Override
    public boolean logout()  {
        if(subject != null && userPrincipal != null){
            subject.getPrincipals().remove(userPrincipal);
        }
        subject = null;
        userPrincipal = null;
        return true;
    }
    /*
    public boolean performAction(){
        return true;
    }
    */
}
