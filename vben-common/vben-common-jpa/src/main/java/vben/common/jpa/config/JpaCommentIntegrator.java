package vben.common.jpa.config;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.boot.Metadata;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.hibernate.mapping.Selectable;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Iterator;

/**
 * Integrator used to process comment annotation.
 *
 * @author Elyar Adil
 * @since 1.0
 */
//@Component
public class JpaCommentIntegrator implements Integrator {
    public static final JpaCommentIntegrator INSTANCE = new JpaCommentIntegrator();

    public JpaCommentIntegrator() {
        super();
    }

    /**
     * Perform comment integration.
     *
     * @param metadata        The "compiled" representation of the mapping information
     * @param sessionFactory  The session factory being created
     * @param serviceRegistry The session factory's service registry
     */
    @Override
    public void integrate(Metadata metadata, SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {
        processComment(metadata);
    }

    /**
     * Not used.
     *
     * @param sessionFactoryImplementor     The session factory being closed.
     * @param sessionFactoryServiceRegistry That session factory's service registry
     */
    @Override
    public void disintegrate(SessionFactoryImplementor sessionFactoryImplementor, SessionFactoryServiceRegistry sessionFactoryServiceRegistry) {
    }

    /**
     * Process comment annotation.
     *
     * @param metadata process annotation of this {@code Metadata}.
     */
    private void processComment(Metadata metadata) {
        for (PersistentClass persistentClass : metadata.getEntityBindings()) {
            // Process the Comment annotation is applied to Class
            Class<?> clz = persistentClass.getMappedClass();
            if (clz.isAnnotationPresent(Schema.class)) {
                Schema comment = clz.getAnnotation(Schema.class);
                persistentClass.getTable().setComment(comment.description());
            }

            // Process Comment annotations of identifier.
            Property identifierProperty = persistentClass.getIdentifierProperty();
            if (identifierProperty != null) {
//                if ("供应商信息".equals(persistentClass.getTable().getComment())) {
//                    System.out.println("1:" + identifierProperty.getName());
//                }
                fieldComment(persistentClass, identifierProperty.getName());
            } else {
                org.hibernate.mapping.Component component = persistentClass.getIdentifierMapper();
                if (component != null) {
                    //noinspection unchecked
                    Iterator<Property> iterator = component.getPropertyIterator();
                    while (iterator.hasNext()) {
//                        if ("供应商信息".equals(persistentClass.getTable().getComment())) {
//                            System.out.println("2:" + iterator);
//                        }
                        fieldComment(persistentClass, iterator.next().getName());
                    }
                }
            }
            // Process fields with Comment annotation.
            //noinspection unchecked
            Iterator<Property> iterator = persistentClass.getProperties().iterator();;
            while (iterator.hasNext()) {
//                String nextField = iterator.next().getName();
//                if ("供应商信息".equals(persistentClass.getTable().getComment())) {
//                    System.out.println("3:" + nextField);
//                }
//                fieldComment(persistentClass, nextField);
                fieldComment(persistentClass, iterator.next().getName());
            }
        }
    }

    /**
     * Process @{code comment} annotation of field.
     *
     * @param persistentClass Hibernate {@code PersistentClass}
     * @param columnName      name of field
     */
    private void fieldComment(PersistentClass persistentClass, String columnName) {
//        if ("供应商信息".equals(persistentClass.getTable().getComment())) {
//            System.out.println("columnName=" + columnName);
//        }
        try {
            Field field = null;
            try {
                field = persistentClass.getMappedClass().getDeclaredField(columnName);
            } catch (Exception e) {

            }
            if (field == null) {
                field = persistentClass.getMappedClass().getSuperclass().getDeclaredField(columnName);
            }
            if (field.isAnnotationPresent(Schema.class)) {
                String comment = field.getAnnotation(Schema.class).description();
//                if ("供应商信息".equals(persistentClass.getTable().getComment())) {
//                    System.out.println("comment=" + comment);
//                }
                Iterator<Selectable> iter = persistentClass.getProperty(columnName).getValue().getSelectables().iterator();
                String sqlColumnName = iter.hasNext() ? iter.next().getText() : "";
                Iterator<org.hibernate.mapping.Column> columnIterator = persistentClass.getTable().getColumns().iterator();
                while (columnIterator.hasNext()) {
                    org.hibernate.mapping.Column column = columnIterator.next();
                    if (sqlColumnName.equalsIgnoreCase(column.getName())) {
                        column.setComment(comment);
                        break;
                    }
                }
            }
        } catch (NoSuchFieldException | SecurityException ignored) {
        }
    }
}
