package org.example.components;

import java.util.List;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.hippoecm.frontend.plugin.IPluginContext;
import org.hippoecm.frontend.plugin.config.IPluginConfig;
import org.hippoecm.frontend.plugins.login.LoginHandler;
import org.hippoecm.frontend.plugins.login.LoginPanel;
import org.hippoecm.frontend.plugins.login.LoginPlugin;

public class CustomLoginPlugin extends LoginPlugin {

    private String companyName;

    public CustomLoginPlugin(final IPluginContext context, final IPluginConfig config) {
        super(context, config);
    }

    @Override
    protected LoginPanel createLoginPanel(final String id, final boolean autoComplete, final List<String> locales,
                                          final LoginHandler handler) {
        return new CustomLoginForm(id, autoComplete, locales, handler);
    }

    class CustomLoginForm extends LoginPanel {

        public CustomLoginForm(final String id, final boolean autoComplete, final List<String> locales, final LoginHandler handler) {
            super(id, autoComplete, locales, handler);

            final IModel<String> companyNameModel = PropertyModel.of(CustomLoginPlugin.this, "companyName");
            final TextField<String> companyName = new RequiredTextField<>("companyName", companyNameModel);
            form.add(companyName);
        }

        @Override
        protected void loginSuccess() {
            Session.get().setAttribute("companyName", companyName);
            super.loginSuccess();
        }
    }
}
