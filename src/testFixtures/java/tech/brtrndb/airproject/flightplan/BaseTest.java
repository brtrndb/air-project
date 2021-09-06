package tech.brtrndb.airproject.flightplan;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.test.context.ActiveProfiles;

import tech.brtrndb.airproject.flightplan.logging.WithLog;

@Slf4j
@WithLog
@ActiveProfiles({"test"})
public abstract class BaseTest {

    protected static void notImplementedYet() {
        throw new RuntimeException("Not implemented yet");
    }

    protected static <T> void noBeanInContext(ApplicationContextRunner context, Class<T> beanClass) {
        Assertions.assertThatThrownBy(() -> context
                        .run(ctx -> {
                            // Given:
                            // When:
                            T bean = ctx.getBean(beanClass);
                            // Then:
                        }))
                .isExactlyInstanceOf(NoSuchBeanDefinitionException.class)
                .hasMessageContaining(beanClass.getName());
    }

    protected static <T> void beanInContext(ApplicationContextRunner context, Class<T> beanClass) {
        context.run(ctx -> {
            // Given:
            // When:
            T bean = ctx.getBean(beanClass);
            // Then:
            Assertions.assertThat(bean).isNotNull();
        });
    }

}
