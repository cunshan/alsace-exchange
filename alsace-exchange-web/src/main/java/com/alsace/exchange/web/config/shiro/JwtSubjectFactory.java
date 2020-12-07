package com.alsace.exchange.web.config.shiro;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;

/**
 * @description:
 * @author: 01437768
 * @create: 2019/12/17 15:26
 */
public class JwtSubjectFactory extends DefaultWebSubjectFactory {

    public JwtSubjectFactory() {
        super();
    }

    @Override
    public Subject createSubject(SubjectContext context) {
        // 不创建 session
        context.setSessionCreationEnabled(false);
        return super.createSubject(context);
    }
}
