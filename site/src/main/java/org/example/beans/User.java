package org.example.beans;

import org.hippoecm.hst.content.beans.ContentNodeBinder;
import org.hippoecm.hst.content.beans.ContentNodeBindingException;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;

import javax.jcr.RepositoryException;

@HippoEssentialsGenerated(internalName = "myhippoproject:user")
@Node(jcrType = "myhippoproject:user")
public class User extends BaseDocument implements ContentNodeBinder{

    private String username;
    private String password;
    private String email;
    //private String subjectUuid;

    @HippoEssentialsGenerated(internalName = "myhippoproject:username")
    public String getUsername() {
        return (username == null) ? getProperty("myhippoproject:username") : username;
    }

    @HippoEssentialsGenerated(internalName = "myhippoproject:password")
    public String getPassword() {
        return (password == null) ? getProperty("myhippoproject:password") : password;
    }

    @HippoEssentialsGenerated(internalName = "myhippoproject:email")
    public String getEmail() {
        return (email == null) ? getProperty("myhippoproject:email") : email;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean bind(Object content, javax.jcr.Node node) throws ContentNodeBindingException {
        if(content instanceof User){
            User user = (User) content;
            try {
                node.setProperty("myhippoproject:username",user.getUsername());
                node.setProperty("myhippoproject:password",user.getPassword());
                node.setProperty("myhippoproject:email",user.getEmail());
            } catch (RepositoryException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
