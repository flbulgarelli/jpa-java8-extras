package org.uqbarproject.jpa.java8.extras.export;

import java.util.HashMap;

import org.hibernate.cfg.Configuration;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.tool.hbm2ddl.SchemaExport;

/**
 * Tool for exporting JPA schema. This class depends on Hibernate classes
 * Run it as <code>DB_NAME SCHEMA_FILE [CREATE] [FORAMAT]</code>.
 * 
 * Examples: 
 * 
 * <code>
 * <pre>
 * db schema.sql
 * db schema.sql true true
 * </pre>
 * </code>
 */
public class JpaSchemaExport {

   public static void main(String[] args) throws Exception {
     boolean create =  args.length >= 3 ? Boolean.parseBoolean(args[2]) : true;
     boolean format =  args.length == 4 ? Boolean.parseBoolean(args[3]) : true;
     execute(args[0], args[1], create, format);
   }

   public static void execute(String unused, String destination, boolean create, boolean format)
         throws Exception {
      System.out.println("Starting schema export");

      new HibernatePersistenceProvider() {
         {
            EntityManagerFactoryBuilderImpl emfb = (EntityManagerFactoryBuilderImpl) this
                  .getEntityManagerFactoryBuilderOrNull(unused, new HashMap<>());
            Configuration hbmcfg = emfb.buildHibernateConfiguration(emfb.buildServiceRegistry());
            SchemaExport schemaExport = new SchemaExport(hbmcfg);
            schemaExport.setOutputFile(destination);
            schemaExport.setFormat(format);
            schemaExport.execute(true, false, false, create);
            System.out.println("Schema exported to " + destination);
         }
      };

   }
}