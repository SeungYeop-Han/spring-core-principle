package hello.core.beandefinition;

import hello.core.AppConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class BeanDefinitionTest {

    AnnotationConfigApplicationContext annoAc
            = new AnnotationConfigApplicationContext(AppConfig.class);
    GenericXmlApplicationContext xmlAc
            = new GenericXmlApplicationContext("appConfig.xml");

    @Test
    void 어노테이션_기반의_빈_구성_메타_데이터_확인() {
        String[] beanDefinitionNames = annoAc.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition
                    = annoAc.getBeanDefinition(beanDefinitionName);

            if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                System.out.println("[빈 이름: " + beanDefinitionName + "]");
                System.out.println("    >>> beanDefinition = " + beanDefinition);
            }
        }
    }

    @Test
    void xml_기반의_빈_구성_메타_데이터_확인() {
        String[] beanDefinitionNames = xmlAc.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition
                    = xmlAc.getBeanDefinition(beanDefinitionName);

            if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                System.out.println("[빈 이름: " + beanDefinitionName + "]");
                System.out.println("    >>> beanDefinition = " + beanDefinition);
            }
        }
    }
}
