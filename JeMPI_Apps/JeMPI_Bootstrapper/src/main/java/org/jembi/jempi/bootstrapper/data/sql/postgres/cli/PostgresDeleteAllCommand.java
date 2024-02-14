package org.jembi.jempi.bootstrapper.data.sql.postgres.cli;

import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "deleteAll", mixinStandardHelpOptions = true, description = "Delete all the data and schema used by"
        + " JeMPI Postgres instance.")
public class PostgresDeleteAllCommand extends BasePostgresCommand implements Callable<Integer> {
   @Override
   public Integer call() throws Exception {
      this.init();
      return this.execute(() -> this.bootstrapper.deleteData() && this.bootstrapper.deleteTables()
            ? 0
            : 1);
   }
}
