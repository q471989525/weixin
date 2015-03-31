

package com.app.application.weix.listener;

import com.app.application.weix.utils.VALUE;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Web application lifecycle listener.
 *
 * @author fuq
 */
@WebListener()
public class InitletListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        new VALUE().init();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
       
    }
}
